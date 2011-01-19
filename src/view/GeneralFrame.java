package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import view.componenti.componentiPannello.PannelloDati2;
import view.componenti.movimenti.Movimenti;
import view.entrateuscite.EntryCharge;
import view.grafici.Grafici;
import view.impostazioni.RaccogliImpostazioni;
import view.tabelle.PerMesiF;
import business.DBUtil;
import business.ascoltatori.AscoltatoreCaricaDatabase;
import business.ascoltatori.AscoltatoreLogin;
import business.ascoltatori.AscoltatoreRegistrazione;

public class GeneralFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTabbedPane tabGenerale;
	private static JPanel tabSetting;
	private static PannelloDati2 tabDatiGenerali;
	private static PerMesiF tabPermesi;
	private static Movimenti tabMovimenti;
	private static Grafici tabGrafici;
	private static Report tabReport;
	private static Help tabHelp;
	private static EntryCharge iec;
	private static NewSql consolle;

	/**
	 * Launch the application.
	 */	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DBUtil.closeConnection();
				GeneralFrame inst = new GeneralFrame();
				inst.setTitle("Gestionale spese familiari");
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GeneralFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1000, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//crea il tab per il menu
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1000, 20);
		contentPane.add(menuBar);
		
		//crea un menu
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		
		//item di un menu
		JMenuItem menuItem = new JMenuItem("Altro database");
		ActionListener ascolto = new AscoltatoreCaricaDatabase();
		menuItem.addActionListener(ascolto);
		menu.add(menuItem);
		
		//item Login
		JMenuItem menuItem2 = new JMenuItem("Login");
		ActionListener login = new AscoltatoreLogin();
		menuItem2.addActionListener(login);
		menu.add(menuItem2);
		
		//item Login
		JMenuItem menuItem3 = new JMenuItem("Registrazione");
		ActionListener registrazione = new AscoltatoreRegistrazione();
		menuItem3.addActionListener(registrazione);
		menu.add(menuItem3);
		
		//tabGenerale
		tabGenerale = new JTabbedPane();
		tabGenerale.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
		tabGenerale.setBounds(0, 31, 970, 650);
			
		
		tabSetting = new RaccogliImpostazioni();
		tabSetting.setBounds(0, -50, 200, 550);
		

		//pannello consolle sql
		consolle = new NewSql();
				
		//pannello dati
		tabDatiGenerali = new PannelloDati2();
		
		//Divisione di spese e entrate per mese
		tabPermesi = new PerMesiF();
		
		//movimenti
		tabMovimenti = new Movimenti();
		
		//grafici
		tabGrafici = new Grafici();
		
		//report 
		try {
			tabReport = new Report();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//help
		tabHelp = new Help();
		
		//pannello di entrata e uscita 
		iec = new EntryCharge();
	
		this.getContentPane().add(tabGenerale);
		
		JLabel LabelProgramma = new JLabel();
		getContentPane().add(LabelProgramma);
		LabelProgramma.setText("Gestione Finanze Familiari");
		LabelProgramma.setBounds(790, 60, 268, 14);
		LabelProgramma.setForeground(Color.WHITE);
		LabelProgramma.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		tabGenerale.addTab("Setting", tabSetting);
		tabGenerale.addTab("Entrate/Uscite", iec);
		tabGenerale.addTab("Dati Generali", tabDatiGenerali);
		tabGenerale.addTab("Mesi", tabPermesi);
		tabGenerale.addTab("Movimenti", tabMovimenti);
		
		tabGenerale.addTab("Grafici", tabGrafici);
		tabGenerale.addTab("ConsolleSQL", consolle);
		tabGenerale.addTab("Report", tabReport);
		tabGenerale.addTab("Help", tabHelp);
		
	}
	
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public JTabbedPane getTabGenerale() {
		return tabGenerale;
	}

	public void setTabGenerale(JTabbedPane tabGenerale) {
		GeneralFrame.tabGenerale = tabGenerale;
	}

	public JPanel getTabSetting() {
		return tabSetting;
	}

	public void setTabSetting(JPanel tabSetting) {
		GeneralFrame.tabSetting = tabSetting;
	}

	public PannelloDati2 getTabDatiGenerali() {
		return tabDatiGenerali;
	}

	public void setTabDatiGenerali(PannelloDati2 tabDatiGenerali) {
		GeneralFrame.tabDatiGenerali = tabDatiGenerali;
	}

	public PerMesiF getTabPermesi() {
		return tabPermesi;
	}

	public void setTabPermesi(PerMesiF tabPermesi) {
		GeneralFrame.tabPermesi = tabPermesi;
	}

	public Movimenti getTabMovimenti() {
		return tabMovimenti;
	}

	public void setTabMovimenti(Movimenti tabMovimenti) {
		GeneralFrame.tabMovimenti = tabMovimenti;
	}

	public Grafici getTabGrafici() {
		return tabGrafici;
	}

	public void setTabGrafici(Grafici tabGrafici) {
		GeneralFrame.tabGrafici = tabGrafici;
	}

	public Report getTabReport() {
		return tabReport;
	}

	public void setTabReport(Report tabReport) {
		GeneralFrame.tabReport = tabReport;
	}

	public Help getTabHelp() {
		return tabHelp;
	}

	public void setTabHelp(Help tabHelp) {
		GeneralFrame.tabHelp = tabHelp;
	}

	public EntryCharge getIec() {
		return iec;
	}

	public void setIec(EntryCharge iec) {
		GeneralFrame.iec = iec;
	}

	public NewSql getConsolle() {
		return consolle;
	}

	public void setConsolle(NewSql consolle) {
		GeneralFrame.consolle = consolle;
	}
}
