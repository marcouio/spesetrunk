package business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.Alert;
import view.GeneralFrame;
import business.aggiornatori.AggiornatoreManager;
import business.cache.CacheLookAndFeel;
import business.cache.CacheUtenti;
import business.comandi.AbstractCommand;
import business.comandi.CommandManager;
import business.internazionalizzazione.I18NManager;
import domain.Lookandfeel;
import domain.Utenti;
import domain.wrapper.WrapUtenti;

public class Controllore {

	private static GeneralFrame view;
	private static Utenti utenteLogin;
	private static CommandManager commandManager;
	private static AggiornatoreManager aggiornatoreManager;
	private InizializzatoreFinestre initFinestre;
	private static Controllore singleton;
	private static Logger log;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		Database.DB_URL = Database.DB_URL_WORKSPACE;
		verificaPresenzaDb();

		settaLookFeel();

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				DBUtil.closeConnection();
				Controllore.getSingleton();
				view = GeneralFrame.getSingleton();
				view.setResizable(false);
				setStartUtenteLogin();
				view.setTitle(I18NManager.getSingleton().getMessaggio("title"));
				view.setLocationByPlatform(true);
				view.setVisible(true);

			}
		});
	}

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
			} else {
				//se non c'era un look selezionato setto quello di sistema
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			SwingUtilities.updateComponentTreeUI(GeneralFrame.getSingleton());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static void verificaPresenzaDb() {
		try {
			final Connection cn = DBUtil.getConnection();
			final String sql = "SELECT * FROM " + Lookandfeel.NOME_TABELLA;
			final Statement st = cn.createStatement();
			@SuppressWarnings("unused")
			final
			ResultSet rs = st.executeQuery(sql);
		} catch (final SQLException e) {
			try {
				Database.getSingleton().generaDB();
				Alert.info("Database non presente: è stato rigenerato", "");
			} catch (final SQLException e1) {
				Controllore.getLog().severe("Database non creato: " + e.getMessage());
			}
		}
	}

	private Controllore() {
		setStartUtenteLogin();
	}

	public static boolean invocaComando(final AbstractCommand comando) {
		return Controllore.getSingleton().getCommandManager().invocaComando(comando);
	}

	/**
	 * Controlla se esiste sul db l'utente guest, altrimenti lo crea
	 */
	private static void setStartUtenteLogin() {
		final Utenti utenteGuest = CacheUtenti.getSingleton().getUtente("1");
		if (utenteGuest == null || utenteGuest.getnome() == null) {
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
		if (singleton == null) {
			synchronized (Controllore.class) {
				if (singleton == null) {
					singleton = new Controllore();
				}
			} // if
		} // if
		return singleton;
	}

	public Utenti getUtenteLogin() {
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

	public CommandManager getCommandManager() {
		if (commandManager == null) {
			commandManager = CommandManager.getIstance();
		}
		return commandManager;
	}

	public static void setView(final GeneralFrame view) {
		Controllore.view = view;
	}

	public GeneralFrame getView() {
		return view;
	}

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

	public static Logger getLog() {
		if (log == null) {
			log = LoggerOggetto.getLog();
		}
		return log;
	}
}