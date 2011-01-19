package view.tabelle;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import view.OggettoVistaBase;

import view.tabelle.TabellaEntrata;
import view.tabelle.TabellaUscita;

import java.awt.Dimension;


public class PerMesiF extends OggettoVistaBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new PerMesiF());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	private JTabbedPane tabGenerale;
	private JLabel labelGennaio;
	private static TabellaEntrata tabEntrate;
	private static TabellaUscita tabUscite;
	private JLabel labelFebbraio;
	private JLabel labelMarzo;
	private JLabel labelAprile;
	private JLabel labelMaggio;
	private JLabel labelGiugno;
	private JLabel labelLuglio;
	private JLabel labelAgosto;
	private JLabel labelSettembre;
	private JLabel labelOttobre;
	private JLabel labelNovembre;
	private JLabel labelDicembre;
	private JPanel pannello;
	
	public PerMesiF() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new Dimension(918, 545));
			this.setLayout(null);
			
			pannello = new JPanel();
			this.add(pannello);
			pannello.setLayout(new GridLayout(12, 1));
			pannello.setBounds(9, 118, 55, 327);
			
			labelGennaio = new JLabel();
			pannello.add(labelGennaio);
			labelGennaio.setText("Gennaio");
			labelGennaio.setBounds(7, 110, 52, 14);
			labelGennaio.setFont(new Font("Tahoma", Font.BOLD, 9));
			labelFebbraio = new JLabel();
			pannello.add(labelFebbraio);
			labelFebbraio.setText("Febbraio");
			labelFebbraio.setBounds(7, 126, 58, 14);
			labelFebbraio.setFont(new Font("Tahoma", Font.BOLD, 9));
			labelMarzo = new JLabel();
			pannello.add(labelMarzo);
			labelMarzo.setText("Marzo");
			labelMarzo.setBounds(7, 142, 58, 14);
			labelMarzo.setFont(new Font("Tahoma", Font.BOLD, 9));
			labelAprile = new JLabel();
			pannello.add(labelAprile);
			labelAprile.setText("Aprile");
			labelAprile.setBounds(7, 158, 58, 14);
			labelAprile.setFont(new Font("Tahoma", Font.BOLD, 9));
			labelMaggio = new JLabel();
			pannello.add(labelMaggio);
			labelMaggio.setText("Maggio");
			labelMaggio.setBounds(7, 174, 58, 14);
			labelMaggio.setFont(new Font("Tahoma", Font.BOLD, 9));
			labelGiugno = new JLabel();
			pannello.add(labelGiugno);
			labelGiugno.setText("Giugno");
			labelGiugno.setBounds(7, 190, 58, 14);
			labelGiugno.setFont(new Font("Tahoma", Font.BOLD, 9));
			labelLuglio = new JLabel();
			pannello.add(labelLuglio);
			labelLuglio.setText("Luglio");
			labelLuglio.setBounds(7, 206, 58, 14);
			labelLuglio.setFont(new Font("Tahoma", Font.BOLD, 9));
			labelAgosto = new JLabel();
			pannello.add(labelAgosto);
			labelAgosto.setText("Agosto");
			labelAgosto.setBounds(7, 222, 58, 14);
			labelAgosto.setFont(new Font("Tahoma", Font.BOLD, 9));
			labelSettembre = new JLabel();
			pannello.add(labelSettembre);
			labelSettembre.setText("Settembre");
			labelSettembre.setBounds(7, 238, 58, 14);
			labelSettembre.setFont(new Font("Tahoma", Font.BOLD, 9));
			labelOttobre = new JLabel();
			pannello.add(labelOttobre);
			labelOttobre.setText("Ottobre");
			labelOttobre.setBounds(7, 254, 58, 14);
			labelOttobre.setFont(new Font("Tahoma", Font.BOLD, 9));
			labelNovembre = new JLabel();
			pannello.add(labelNovembre);
			labelNovembre.setText("Novembre");
			labelNovembre.setBounds(7, 270, 58, 14);
			labelNovembre.setFont(new Font("Tahoma", Font.BOLD, 9));
			labelDicembre = new JLabel();
			pannello.add(labelDicembre);
			labelDicembre.setText("Dicembre");
			labelDicembre.setFont(new Font("Tahoma", Font.BOLD, 9));
			
			tabEntrate = new TabellaEntrata();
			tabUscite = new TabellaUscita();
			
			tabGenerale = new JTabbedPane();
			tabGenerale.setBounds(65, 65, 750, 423);
			tabGenerale.addTab("Uscite", tabUscite);
			tabGenerale.addTab("Entrate", tabEntrate);
			
			
			tabUscite.setBounds(26, 10, 400, 400);
			tabEntrate.setBounds(26, 10, 400, 400);
			

			this.add(tabGenerale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
