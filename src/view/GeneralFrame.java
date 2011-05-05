package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import view.bottoni.Bottone;
import view.bottoni.PannelloBottoni;
import view.componenti.movimenti.Movimenti;
import view.impostazioni.Impostazioni;
import view.mymenu.MyMenu;
import view.sidebar.ToggleBtn;
import view.tabelle.PerMesiF;
import business.Controllore;
import business.DBUtil;

public class GeneralFrame extends JFrame {

	private static final long       serialVersionUID = 1L;
	private final JPanel            contentPane;
	// private static JTabbedPane tabGenerale;
	private static PerMesiF         tabPermesi;
	private static Movimenti        tabMovimenti;
	private static NewSql           consolle;
	private static GeneralFrame     singleton;
	private final ArrayList<JPanel> listaPannelli    = new ArrayList<JPanel>();

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
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

		createPannelloBottoni();

		// tabGenerale
		// tabGenerale = new JTabbedPane();
		// tabGenerale.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
		// tabGenerale.setBounds(0, 70, 970, 650);

		// pannello consolle sql
		consolle = new NewSql();
		consolle.setBounds(0, 70, 970, 650);
		// movimenti
		tabMovimenti = new Movimenti();
		tabMovimenti.setBounds(0, 70, 970, 650);
		// Divisione di spese e entrate per mese
		tabPermesi = new PerMesiF();
		tabPermesi.setBounds(0, 70, 970, 650);

		// this.getContentPane().add(tabGenerale);

		contentPane.add(tabMovimenti);
		listaPannelli.add(tabMovimenti);
		contentPane.add(tabPermesi);
		listaPannelli.add(tabPermesi);
		contentPane.add(consolle);
		listaPannelli.add(consolle);
		// tabGenerale.addTab("Mesi", tabPermesi);
		// tabGenerale.addTab("Movimenti", tabMovimenti);
		// tabGenerale.addTab("ConsolleSQL", consolle);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeiconified(WindowEvent e) {
				Controllore.getFinestraHistory().setVisible(true);
				relocateFinestreLaterali();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				Controllore.getSingleton().quit();
			}

			@Override
			public void windowIconified(WindowEvent e) {
				Controllore.getFinestraHistory().setVisible(false);
			}

		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resizeView();
				relocateFinestreLaterali();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				relocateFinestreLaterali();
			}
		});

		repaint();
	}

	private void createPannelloBottoni() {
		PannelloBottoni pannelloBottoni = new PannelloBottoni();
		ToggleBtn bottoni3 = new ToggleBtn("Movimenti", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		bottoni3.settaggioBottoneStandard();
		Bottone b3 = new Bottone(bottoni3);
		bottoni3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				tabPermesi.setVisible(true);
			}
		});

		// **************************************
		JPanel pp = new JPanel();
		pp.add(new JLabel("Ciao"));
		pp.setLayout(new GridLayout(1, 1));
		b3.setContent(pp);
		// *****************************************

		bottoni3.setPadre(b3);

		ToggleBtn bottoni2 = new ToggleBtn("Mesi", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		bottoni2.settaggioBottoneStandard();
		Bottone b2 = new Bottone(bottoni2);
		bottoni2.setPadre(b2);

		ToggleBtn bottoni = new ToggleBtn("ConsolleSQL", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		bottoni.settaggioBottoneStandard();
		Bottone b = new Bottone(bottoni);
		bottoni.setPadre(b);

		pannelloBottoni.addBottone(b);
		pannelloBottoni.addBottone(b2);
		pannelloBottoni.addBottone(b3);
		contentPane.add(pannelloBottoni);
		pannelloBottoni.setBounds(0, 20, this.getWidth(), 90);
	}

	private void relocateFinestreLaterali() {
		Point p = getLocation();
		Dimension d = getSize();
		p.setLocation(p.x + d.width + 5, p.y);
		Controllore.getFinestraHistory().setLocation(p);
		Controllore.getReport().setLocation(p);
		Controllore.getPannelloDati().setLocation(p);
	}

	private void resizeView() {
		// TODO Auto-generated method stub

	}

	// public JTabbedPane getTabGenerale() {
	// return tabGenerale;
	// }
	//
	// public void setTabGenerale(JTabbedPane tabGenerale) {
	// GeneralFrame.tabGenerale = tabGenerale;
	// }

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

	public NewSql getConsolle() {
		return consolle;
	}

	public void setConsolle(NewSql consolle) {
		GeneralFrame.consolle = consolle;
	}

}
