package com.molinari.gestionespese.business;

import java.awt.Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.cache.CacheLookAndFeel;
import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.domain.Lookandfeel;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapUtenti;
import com.molinari.gestionespese.view.GeneralFrame;
import com.molinari.gestionespese.view.MyWindowListener;

import command.AbstractCommand;
import command.CommandManager;
import controller.ControlloreBase;
import db.ConnectionPool;
import grafica.componenti.contenitori.FrameBase;

public class Controllore extends ControlloreBase{

	private static final String GUEST = "guest";
	private FrameBase view;
	private AggiornatoreManager aggiornatoreManager;
	private InizializzatoreFinestre initFinestre;
	private GeneralFrame genPan;
	private static Controllore singleton;
	private String lookUsato;

	private Controllore() {
		//do nothing
	}

	private void settaLookFeel() {
		try {
			final CacheLookAndFeel cacheLook = CacheLookAndFeel.getSingleton();
			final java.util.List<Lookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();
			Lookandfeel look;
			Lookandfeel lookDaUsare = null;
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
			ConnectionPool.getSingleton().new ExecuteResultSet<Boolean>() {

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
			}
			getLog().severe(e.getMessage());
		}
	}


	public static boolean invocaComando(final AbstractCommand comando) {
		return Controllore.getSingleton().getCommandManager().invocaComando(comando);
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
		}

		utenteLogin = CacheUtenti.getSingleton().getUtente("1");
	}

	public static Controllore getSingleton() {
		synchronized (Controllore.class) {
			if (singleton == null) {
				singleton = new Controllore();
			}
		} // if
		return singleton;
	}

	@Override
	public Object getUtenteLogin() {
		return utenteLogin;
	}

	public AggiornatoreManager getAggiornatoreManager() {
		if (aggiornatoreManager == null) {
			aggiornatoreManager = AggiornatoreManager.getSingleton();
		}
		return aggiornatoreManager;
	}

	@Override
	public CommandManager getCommandManager() {
		if (commandManager == null) {
			commandManager = CommandManager.getIstance();
		}
		return commandManager;
	}

	public FrameBase getView() {
		return view;
	}

	@Override
	public void quit() {
		view.setVisible(false);
		view.dispose();
	}

	public InizializzatoreFinestre getInitFinestre() {
		if (initFinestre == null) {
			initFinestre = new InizializzatoreFinestre();
		}
		return initFinestre;
	}

	public void setInitFinestre(final InizializzatoreFinestre initFinestre) {
		this.initFinestre = initFinestre;
	}

	public static void main(final String[] args) {
		Controllore.getSingleton().myMain(Controllore.getSingleton(), false, "myApplication");
	}

	@Override
	public void mainOverridato(FrameBase frame) throws Exception {

		Database.setDburl(Database.DB_URL_WORKSPACE);
		verificaPresenzaDb();
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setBounds(10, 20, 1024, 648);
		genPan = new GeneralFrame(frame);
		view = frame;
		view.setVisible(true);
		setStartUtenteLogin();

		settaLookFeel();

		Controllore.getSingleton();

		view.setTitle(I18NManager.getSingleton().getMessaggio("title"));

		final MyWindowListener windowListener = new MyWindowListener(genPan);
		frame.addWindowListener(windowListener);
		frame.addComponentListener(windowListener);
		frame.addWindowFocusListener(windowListener);
		frame.addMouseListener(windowListener);

	}

	public FrameBase getGenView(){
		return view;
	}

	public GeneralFrame getGeneralFrame(){
		return this.genPan;
	}

	@Override
	public String getConnectionClassName() {
		return ConnectionPoolGGS.class.getName();
	}

	public String getLookUsato() {
		return lookUsato;
	}

	public void setLookUsato(String lookUsato) {
		this.lookUsato = lookUsato;
	}

}