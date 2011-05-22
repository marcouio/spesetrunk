package business;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.FinestraListaComandi;
import view.GeneralFrame;
import view.Report;
import view.componenti.componentiPannello.PannelloAScomparsa2;
import view.note.MostraNoteView;
import business.cache.CacheGruppi;
import business.cache.CacheLookAndFeel;
import business.cache.CacheUtenti;
import business.comandi.CommandManager;
import domain.Gruppi;
import domain.Lookandfeel;
import domain.Utenti;
import domain.wrapper.WrapGruppi;
import domain.wrapper.WrapUtenti;

public class Controllore {

	private static GeneralFrame            view;
	protected static PannelloAScomparsa2   pannelloDati;
	protected static FinestraListaComandi  historyCommands;
	protected static MostraNoteView        pannelloNote;
	protected static Report                report;
	private static Utenti                  utenteLogin;
	private static CommandManager          commandManager;
	private static final ArrayList<JFrame> finestre = new ArrayList<JFrame>();

	private static Controllore             singleton;
	private static JFrame                  windowVisibile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CacheLookAndFeel cacheLook = CacheLookAndFeel.getSingleton();
			java.util.Vector<Lookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();

			Lookandfeel look = null;
			for (int i = 0; i < vettore.size(); i++) {
				look = vettore.get(i);
				if (look.getusato() == 1) {
					break;
				}
			}

			UIManager.setLookAndFeel(look.getvalore());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				DBUtil.closeConnection();
				Controllore.getSingleton();
				view = GeneralFrame.getSingleton();
				view.setResizable(false);
				setStartUtenteLogin();
				view.setTitle("Gestionale spese familiari");
				view.setLocationByPlatform(true);
				view.setVisible(true);

				pannelloNote = new MostraNoteView();
				pannelloNote.setBounds(view.getX() + view.getWidth(), view.getY(), 250, 425);
				pannelloNote.setVisible(false);
				finestre.add(pannelloNote);

				historyCommands = new FinestraListaComandi();
				historyCommands.setBounds(view.getX() + view.getWidth(), view.getY(), 250, 425);
				historyCommands.setVisible(false);
				finestre.add(historyCommands);

				pannelloDati = new PannelloAScomparsa2();
				pannelloDati.setBounds(view.getX() + view.getWidth(), view.getY(), 250, 425);
				pannelloDati.setVisible(false);
				finestre.add(pannelloDati);

				try {
					report = new Report();
					report.setBounds(view.getX() + view.getWidth(), view.getY(), 250, 425);
					report.setVisible(false);
					finestre.add(report);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}
		});
	}

	private Controllore() {
		setStartUtenteLogin();
		setStartGruppoZero();
	}

	private static void setStartUtenteLogin() {
		Utenti utenteGuest = CacheUtenti.getSingleton().getUtente("1");
		if (utenteGuest == null || utenteGuest.getnome() == null) {
			Utenti utente = new Utenti();
			utente.setidUtente(1);
			utente.setusername("guest");
			utente.setpassword("guest");
			utente.setNome("guest");
			utente.setCognome("guest");
			WrapUtenti wrap = new WrapUtenti();
			wrap.insert(utente);
		}

		utenteLogin = CacheUtenti.getSingleton().getUtente("1");
	}

	private static void setStartGruppoZero() {
		Gruppi gruppoZero = CacheGruppi.getSingleton().getGruppoPerNome("No Gruppo");
		if (gruppoZero == null)
			gruppoZero = CacheGruppi.getSingleton().getGruppo("0");
		if (gruppoZero == null || gruppoZero.getnome() == null) {
			Gruppi gruppo = new Gruppi();
			gruppo.setidGruppo(0);
			gruppo.setnome("No Gruppo");
			gruppo.setdescrizione("Impostare per le spese senza gruppo");
			WrapGruppi wrapG = new WrapGruppi();
			wrapG.insert(gruppo);
		}
	}

	public static void setVisibilitaFinestre(JFrame finestraVisibile, JMenu menu, JCheckBoxMenuItem menuItem) {
		boolean visibile = finestraVisibile.isVisible();
		for (JFrame finestra : finestre) {
			finestra.setVisible(false);
		}
		for (int i = 0; i < menu.getMenuComponents().length; i++) {
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) menu.getMenuComponents()[i];
			item.setSelected(false);
		}
		if (visibile) {
			menuItem.setSelected(false);
			finestraVisibile.setVisible(false);
		} else {
			menuItem.setSelected(true);
			finestraVisibile.setVisible(true);
			setWindowVisibile(finestraVisibile);
		}
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

	public static void setUtenteLogin(Utenti utenteLogin) {
		Controllore.utenteLogin = utenteLogin;
	}

	public CommandManager getCommandManager() {
		if (commandManager == null)
			commandManager = CommandManager.getIstance();
		return commandManager;
	}

	public static void setCommandManager(CommandManager commandManager) {
		Controllore.commandManager = commandManager;
	}

	public static void setView(GeneralFrame view) {
		Controllore.view = view;
	}

	public GeneralFrame getView() {
		return view;
	}

	public static MostraNoteView getNote() {
		return pannelloNote;
	}

	public static void setNote(MostraNoteView note) {
		Controllore.pannelloNote = note;
	}

	public static Report getReport() {
		return report;
	}

	public static void setReport(Report report) {
		Controllore.report = report;
	}

	public static PannelloAScomparsa2 getPannelloDati() {
		return pannelloDati;
	}

	public static FinestraListaComandi getFinestraHistory() {
		return historyCommands;
	}

	public static void setFinestraHistory(FinestraListaComandi flc) {
		Controllore.historyCommands = flc;
	}

	public static ArrayList<JFrame> getFinestre() {
		return finestre;
	}

	public void quit() {
		view.setVisible(false);
		view.dispose();
		pannelloDati.setVisible(false);
		pannelloDati.dispose();
		report.setVisible(false);
		report.dispose();
		historyCommands.setVisible(false);
		historyCommands.dispose();
		System.exit(0);
	}

	public static void setWindowVisibile(JFrame windowVisibile) {
	    Controllore.windowVisibile = windowVisibile;
    }

	public static JFrame getWindowVisibile() {
	    return windowVisibile;
    }
}
