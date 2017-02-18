package business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import business.aggiornatori.AggiornatoreManager;
import business.cache.CacheLookAndFeel;
import business.cache.CacheUtenti;
import business.internazionalizzazione.I18NManager;
import command.AbstractCommand;
import command.CommandManager;
import controller.ControlloreBase;
import db.ConnectionPool;
import domain.Lookandfeel;
import domain.Utenti;
import domain.wrapper.WrapUtenti;
import grafica.componenti.contenitori.FrameBase;
import view.GeneralFrame;
import view.MyWindowListener;

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
		} catch (SQLException e) {
			getLog().log(Level.SEVERE, "Il database non c'è ancora, è da creare!", e);
			try {
				Database.getSingleton().generaDB();
			} catch (SQLException e1) {
				getLog().log(Level.SEVERE, "Error on Db creation: " + e1.getMessage(), e1);
			}
			getLog().severe(e.getMessage());
		}
	}


	public static boolean invocaComando(final AbstractCommand comando) throws Exception {
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
		System.exit(0);
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
		Controllore.getSingleton().myMain(Controllore.getSingleton(), true, "myApplication");
	}

	@Override
	public void mainOverridato(FrameBase frame) throws Exception {

		Database.DB_URL = Database.DB_URL_WORKSPACE;
		verificaPresenzaDb();
		
		genPan = new GeneralFrame(frame);
		view = frame;

		setStartUtenteLogin();

		settaLookFeel();
		
		Controllore.getSingleton();
		
//		view.setResizable(false);
		view.setTitle(I18NManager.getSingleton().getMessaggio("title"));
		view.setVisible(true);
		
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