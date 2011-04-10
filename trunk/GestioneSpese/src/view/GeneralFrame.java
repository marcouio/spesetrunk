package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import view.componenti.componentiPannello.PannelloDati2;
import view.componenti.movimenti.Movimenti;
import view.entrateuscite.EntryCharge;
import view.grafici.Grafici;
import view.impostazioni.Impostazioni;
import view.impostazioni.RaccogliImpostazioni;
import view.mymenu.MyMenu;
import view.tabelle.PerMesiF;
import business.Controllore;
import business.DBUtil;

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
	private static GeneralFrame singleton;
	
	public static void main(String[] args){

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
	

	public static final GeneralFrame getSingleton() {
        if (singleton == null) {
            synchronized (Impostazioni.class) {
                if (singleton == null) {
                    singleton = new GeneralFrame();
                }
            } // if
        } // if
        return singleton;
    } // getSingleton()
	
	/**
	 * Create the frame.
	 */
	private GeneralFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1000, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		MyMenu menu = new MyMenu();
		contentPane.add(menu);		

		//tabGenerale
		tabGenerale = new JTabbedPane();
		tabGenerale.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
		tabGenerale.setBounds(0, 31, 970, 650);
			
		
		tabSetting = new RaccogliImpostazioni();
		tabSetting.setBounds(0, 0, 200, 550);
		

		//pannello consolle sql
		consolle = new NewSql();
				
		//pannello dati
		tabDatiGenerali = new PannelloDati2();
		
		//Divisione di spese e entrate per mese
		tabPermesi = new PerMesiF();
//		TabellaUscita.getTable().setRowHeight(27);
//		TabellaEntrata.getTable().setRowHeight(27);
		
		
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
		
		tabGenerale.addTab("Setting", tabSetting);
		tabGenerale.addTab("Entrate/Uscite", iec);
		tabGenerale.addTab("Dati Generali", tabDatiGenerali);
		tabGenerale.addTab("Mesi", tabPermesi);
		tabGenerale.addTab("Movimenti", tabMovimenti);
		
		tabGenerale.addTab("Grafici", tabGrafici);
		tabGenerale.addTab("ConsolleSQL", consolle);
		tabGenerale.addTab("Report", tabReport);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeiconified(WindowEvent e) {
				Controllore.getFinestraHistory().setVisible(true);
				relocateFinestraHistory();
				super.windowDeiconified(e);
			}
			@Override
			public void windowClosed(WindowEvent e) {
				Controllore.getSingleton().quit();
				super.windowClosed(e);
			}
			@Override
			public void windowIconified(WindowEvent e) {
				Controllore.getFinestraHistory().setVisible(false);
				super.windowIconified(e);
			}
			
		});
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resizeView();
				relocateFinestraHistory();
				super.componentResized(e);
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				relocateFinestraHistory();
				super.componentMoved(e);
			}
		});
		
		repaint();
	}
	
	private void relocateFinestraHistory() {
		Point p = getLocation();
		Dimension d = getSize();
		p.setLocation(p.x+d.width+5, p.y);
		Controllore.getFinestraHistory().setLocation(p);
	}

	private void resizeView() {
		// TODO Auto-generated method stub
		
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
