package view.mymenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import view.FinestraListaComandi;

import business.Controllore;
import business.ascoltatoriMenu.AscoltatoreAvanti;
import business.ascoltatoriMenu.AscoltatoreCaricaDatabase;
import business.ascoltatoriMenu.AscoltatoreIndietro;
import business.ascoltatoriMenu.AscoltatoreInfo;
import business.ascoltatoriMenu.AscoltatoreLogin;
import business.ascoltatoriMenu.AscoltatoreRegistrazione;

public class MyMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public MyMenu() {
		init();
	}

	private void init() {
		this.setBounds(0, 0, 1000, 20);

		// crea un menu
		JMenu file = new JMenu("File");
		this.add(file);

		// item di un menu
		JMenuItem menuItem = new JMenuItem("Altro database");
		ActionListener ascolto = new AscoltatoreCaricaDatabase();
		menuItem.addActionListener(ascolto);
		file.add(menuItem);

		// item Login
		JMenuItem menuItem2 = new JMenuItem("Login");
		ActionListener login = new AscoltatoreLogin();
		menuItem2.addActionListener(login);
		file.add(menuItem2);

		// item Login
		JMenuItem registra = new JMenuItem("Registrazione");
		ActionListener registrazione = new AscoltatoreRegistrazione();
		registra.addActionListener(registrazione);
		file.add(registra);
		
		JMenu modifica = new JMenu("Modifica");
		add(modifica);
		
		JMenuItem indietro = new JMenuItem("Indietro");
		indietro.addActionListener(new AscoltatoreIndietro());
		modifica.add(indietro);
		
		JMenuItem avanti = new JMenuItem("Avanti");
		avanti.addActionListener(new AscoltatoreAvanti());
		modifica.add(avanti);
		
		JMenu finestre = new JMenu("Finestre");
		add(finestre);
		
		JMenuItem listaComandi = new JMenuItem("Comandi");
		finestre.add(listaComandi);
		listaComandi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FinestraListaComandi history = Controllore.getFinestraHistory();
				if(history.isVisible()){
					history.setVisible(false);
				}else{
					history.setVisible(true);
				}
				
			}
		});
		
		JMenu help = new JMenu("Help");
		add(help);
		
		JMenuItem info = new JMenuItem("Info");
		info.addActionListener(new AscoltatoreInfo());
		help.add(info);
		
		JMenuItem manuale = new JMenuItem("Manuale");
		help.add(manuale);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setSize(1000, 50);
				frame.getContentPane().add(new MyMenu());
				frame.setVisible(true);
			}
		});
	}

}
