package business;

import grafica.componenti.alert.Alert;
import grafica.componenti.contenitori.FrameBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.GeneralFrame;
import business.aggiornatori.AggiornatoreManager;
import business.cache.CacheLookAndFeel;
import business.cache.CacheUtenti;
import business.internazionalizzazione.I18NManager;
import command.AbstractCommand;
import command.CommandManager;
import controller.ControlloreBase;
import db.ConnectionPool;
import domain.IUtenti;
import domain.Lookandfeel;
import domain.Utenti;
import domain.wrapper.WrapUtenti;

public class Controllore extends ControlloreBase{

	private static FrameBase view;
	private static IUtenti utenteLogin;
	private static CommandManager commandManager;
	private static AggiornatoreManager aggiornatoreManager;
	private InizializzatoreFinestre initFinestre;
	private GeneralFrame genPan;
	private static Controllore singleton;
	private static Logger log;
	public static String lookUsato;

	private static void settaLookFeel() {
		try {
			final CacheLookAndFeel cacheLook = CacheLookAndFeel.getSingleton();
			final java.util.Vector<Lookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();
			Lookandfeel look = null;
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
			e.printStackTrace();
		}
	}

	private static void verificaPresenzaDb() {
		final String sql = "SELECT * FROM " + Lookandfeel.NOME_TABELLA;
		
		try {
			new ConnectionPoolGGS().new ExecuteResultSet<Boolean>() {

				@Override
				protected Boolean doWithResultSet(ResultSet resulSet) throws SQLException {
					return resulSet.next();
				}
			}.execute(sql);
		} catch (SQLException e) {
			try {
				Database.getSingleton().generaDB();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			getLog().severe(e.getMessage());
		}
	}

	private Controllore() {
		
	}

	public static boolean invocaComando(final AbstractCommand comando) throws Exception {
		return Controllore.getSingleton().getCommandManager().invocaComando(comando);
	}

	/**
	 * Controlla se esiste sul db l'utente guest, altrimenti lo crea
	 */
	private static void setStartUtenteLogin() {
		final boolean utenteGuest = CacheUtenti.getSingleton().checkUtentePerUsername("guest");
		if (!utenteGuest) {
			final Utenti utente = new Utenti();
			utente.setidUtente(1);
			utente.setusername("guest");
			utente.setpassword("guest");
			utente.setNome("guest");
			utente.setCognome("guest");
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

	public static void setUtenteLogin(final Utenti utenteLogin) {
		Controllore.utenteLogin = utenteLogin;
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

	public static void setLog(final Logger log) {
		Controllore.log = log;
	}
	
	public static void main(final String[] args) {
		Controllore.getSingleton().myMain(Controllore.getSingleton(), true, "myApplication");
	}

	@Override
	public void mainOverridato(FrameBase frame) throws Exception {
		
		view = GeneralFrame.getSingleton();
		frame = view;
		
		Database.DB_URL = Database.DB_URL_WORKSPACE;
		verificaPresenzaDb();
		
		
		setStartUtenteLogin();

		settaLookFeel();

		
		Controllore.getSingleton();
		
		
		view.setResizable(false);
		view.setTitle(I18NManager.getSingleton().getMessaggio("title"));
		view.setLocationByPlatform(true);
		view.setVisible(true);

		
	}
	
	public static GeneralFrame getGenView(){
		return (GeneralFrame) view;
	}

	@Override
	public String getConnectionClassName() {
		return ConnectionPoolGGS.class.getName();
	}

}