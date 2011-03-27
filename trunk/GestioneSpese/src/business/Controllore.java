package business;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.GeneralFrame;
import business.cache.CacheGruppi;
import business.cache.CacheLookAndFeel;
import business.cache.CacheUtenti;
import business.comandi.CommandManager;
import domain.Gruppi;
import domain.Lookandfeel;
import domain.Utenti;
import domain.wrapper.Model;
import domain.wrapper.WrapGruppi;
import domain.wrapper.WrapUtenti;


public class Controllore {

	private static GeneralFrame view;
	private Model model = Model.getSingleton();
	private static Utenti utenteLogin;
	private static CommandManager commandManager;
	
	private static Controllore singleton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CacheLookAndFeel cacheLook = CacheLookAndFeel.getSingleton();
			java.util.Vector<Lookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();
			
			Lookandfeel look=null;
			for(int i=0; i<vettore.size();i++){
				look = vettore.get(i);
				if(look.getusato()==1){
					break;
				}
			}

			UIManager.setLookAndFeel(look.getvalore());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DBUtil.closeConnection();
				Controllore.getSingleton(); 
				view = GeneralFrame.getSingleton();
				setStartUtenteLogin();
				view.setTitle("Gestionale spese familiari");
				view.setLocationRelativeTo(null);
				view.setVisible(true);
			}
		});
	}
	
	
	
	private Controllore(){
		setStartUtenteLogin();
		setStartGruppoZero();
	}
	
	private static void setStartUtenteLogin() {
		Utenti utenteGuest = CacheUtenti.getSingleton().getUtente("1");
		if(utenteGuest==null || utenteGuest.getNome()==null){
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
		if(gruppoZero == null)
			gruppoZero = CacheGruppi.getSingleton().getGruppo("0");
		if(gruppoZero==null || gruppoZero.getnome()==null){
			Gruppi gruppo = new Gruppi();
			gruppo.setidGruppo(0);
			gruppo.setnome("No Gruppo");
			gruppo.setdescrizione("Impostare per le spese senza gruppo");
			WrapGruppi wrapG = new WrapGruppi();
			wrapG.insert(gruppo);
		}
	}
	
	public static Controllore getSingleton(){
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
	public void setModel(Model model) {
		this.model = model;
	}
	public Model getModel() {
		return model;
	}
	public static void setUtenteLogin(Utenti utenteLogin) {
		Controllore.utenteLogin = utenteLogin;
	}
	
	public CommandManager getCommandManager() {
		if(commandManager==null)
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
}
