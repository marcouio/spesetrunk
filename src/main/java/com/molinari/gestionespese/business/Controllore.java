package com.molinari.gestionespese.business;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.cache.CacheLookAndFeel;
import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.ILookandfeel;
import com.molinari.gestionespese.domain.Lookandfeel;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapUtenti;
import com.molinari.gestionespese.view.GeneralFrame;
import com.molinari.utility.commands.AbstractCommand;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.controller.StarterBase;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.net.HttpDownloadUtility;
import com.molinari.utility.servicesloader.LoaderLevel;
import com.molinari.utility.xml.CoreXMLManager;

public class Controllore extends StarterBase{

	private static final String GUEST = "guest";
	private FrameBase view;
	private AggiornatoreManager aggiornatoreManager;
	private GeneralFrame genPan;
	private String lookUsato;

	public Controllore() {
		//do nothing
	}

	private void settaLookFeel() {
		try {
			final CacheLookAndFeel cacheLook = CacheLookAndFeel.getSingleton();
			final java.util.List<ILookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();
			ILookandfeel look;
			ILookandfeel lookDaUsare = null;
			for (int i = 0; i < vettore.size(); i++) {
				look = vettore.get(i);
				//verifico se sul database quale look era scelto
				if (look.getusato() == 1) {
					lookDaUsare = look;
					break;
				}
			}
			if (lookDaUsare != null && lookDaUsare.getvalore() != null) {
				UIManager.setLookAndFeel(lookDaUsare.getvalore());
				lookUsato = lookDaUsare.getvalore();
			} else {
				//se non c'era un look selezionato setto quello di sistema
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				lookUsato = UIManager.getSystemLookAndFeelClassName();
			}
			SwingUtilities.updateComponentTreeUI(view);
		} catch (final Exception e) {
			getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private static void verificaPresenzaDb() {
		final String sql = "SELECT * FROM " + Lookandfeel.NOME_TABELLA;

		try {
			new ExecuteResultSet<Boolean>() {

				@Override
				protected Boolean doWithResultSet(ResultSet resulSet) throws SQLException {
					return resulSet.next();
				}
			}.execute(sql);
		} catch (final SQLException e) {
			getLog().log(Level.SEVERE, "Il database non c'è ancora, è da creare!", e);
			try {
				Database.getSingleton().generaDB();
			} catch (final SQLException e1) {
				getLog().log(Level.SEVERE, "Error on Db creation: " + e1.getMessage(), e1);
				File file = new File(Database.getDburl());
				System.out.println(file.getAbsolutePath());
				file.deleteOnExit();
			}
			getLog().severe(e.getMessage());
		}
	}


	public static Logger getLog() {
		return ControlloreBase.getLog();
	}

	public static boolean invocaComando(final AbstractCommand comando) {
		return ControlloreBase.getSingleton().getCommandManager().invocaComando(comando);
	}

	/**
	 * Controlla se esiste sul db l'utente guest, altrimenti lo crea
	 */
	private void setStartUtenteLogin() {
		final boolean utenteGuest = CacheUtenti.getSingleton().checkUtentePerUsername(GUEST);
		if (!utenteGuest) {
			final Utenti utente = new Utenti();
			utente.setidUtente(1);
			utente.setusername(GUEST);
			utente.setpassword(GUEST);
			utente.setNome(GUEST);
			utente.setCognome(GUEST);
			final WrapUtenti wrap = new WrapUtenti();
			wrap.insert(utente);
			
			CacheUtenti.getSingleton().getCache().put("1", utente);
		}
		ControlloreBase.getSingleton().setUtenteLogin(CacheUtenti.getSingleton().getUtente("1"));
	}

	public AggiornatoreManager getAggiornatoreManagerInner() {
		if (aggiornatoreManager == null) {
			aggiornatoreManager = AggiornatoreManager.getSingleton();
		}
		return aggiornatoreManager;
	}
	
	public static AggiornatoreManager getAggiornatoreManager(){
		Controllore starter = (Controllore) ControlloreBase.getSingleton().getStarter();
		return starter.getAggiornatoreManagerInner();
	}

	public FrameBase getView() {
		return view;
	}

	@Override
	public void start(FrameBase frame) {
		
		setConnectionClassName();
		ControlloreBase.getLog().setLevel(CoreXMLManager.getSingleton().getLogLevel());
		Database.setDburl(getDatabaseUrl());
		verificaPresenzaDb();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame.setBounds(10, 20, (int)screenSize.getWidth(), (int)screenSize.getHeight());
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		genPan = new GeneralFrame(frame);
		view = frame;
		view.setVisible(true);
		setStartUtenteLogin();

		settaLookFeel();

		view.setTitle(I18NManager.getSingleton().getMessaggio("title"));

	}

	public String getDatabaseUrl() {
		String databaseUrl = CoreXMLManager.getSingleton().getDatabaseUrl();
		try {
			if(databaseUrl.startsWith("http")) {
				String absolutePath = new File("database.sqlite").getAbsolutePath();
				HttpDownloadUtility.downloadFile(databaseUrl, absolutePath);
				databaseUrl = absolutePath;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return databaseUrl;
	}

	public FrameBase getGenView(){
		return view;
	}

	public GeneralFrame getGeneralFrameInner(){
		return this.genPan;
	}
	
	public static GeneralFrame getGeneralFrame(){
		Controllore starter = (Controllore) ControlloreBase.getSingleton().getStarter();
		return starter.getGeneralFrameInner();
	}
	
	public static Object getUtenteLogin(){
		return ControlloreBase.getSingleton().getUtenteLogin();
	}

	public void setConnectionClassName() {
		ControlloreBase.getSingleton().setConnectionClassName(ConnectionPoolGGS.class.getName());
	}

	public String getLookUsato() {
		return lookUsato;
	}

	public void setLookUsato(String lookUsato) {
		this.lookUsato = lookUsato;
	}
	
	@Override
	public LoaderLevel getLevel() {
		return LoaderLevel.IMPLEMENTED;
	}

}