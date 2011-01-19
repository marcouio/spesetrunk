package business;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import business.cache.CacheUtenti;

import view.GeneralFrame;
import domain.Utenti;
import domain.wrapper.Model;


public class Controllore {

	private static GeneralFrame view = new GeneralFrame();
	private Model model = Model.getSingleton();
	private static Utenti utenteLogin;
	
	private static Controllore singleton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DBUtil.closeConnection();
				Controllore controllore = Controllore.getSingleton(); 
				view = controllore.getView();
				JFrame inst = view;
				inst.setTitle("Gestionale spese familiari");
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Controllore(){
		utenteLogin = CacheUtenti.getSingleton().getUtente("1");
		
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


	public static void setView(GeneralFrame view) {
		Controllore.view = view;
	}


	public GeneralFrame getView() {
		return view;
	}
}
