package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import view.componenti.movimenti.Movimenti;
import view.impostazioni.Impostazioni;
import view.mymenu.MyMenu;
import view.tabelle.PerMesiF;
import business.Controllore;
import business.DBUtil;

public class GeneralFrame extends JFrame {

	private static final long   serialVersionUID = 1L;
	private final JPanel        contentPane;
	private static JTabbedPane  tabGenerale;
	private static PerMesiF     tabPermesi;
	private static Movimenti    tabMovimenti;
	private static NewSql       consolle;
	private static GeneralFrame singleton;

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

		// tabGenerale
		tabGenerale = new JTabbedPane();
		tabGenerale.setFont(new Font("Eras Light ITC", Font.BOLD, 14));
		tabGenerale.setBounds(0, 31, 970, 650);

		// pannello consolle sql
		consolle = new NewSql();

		// Divisione di spese e entrate per mese
		tabPermesi = new PerMesiF();

		// movimenti
		tabMovimenti = new Movimenti();

		this.getContentPane().add(tabGenerale);

		tabGenerale.addTab("Mesi", tabPermesi);
		tabGenerale.addTab("Movimenti", tabMovimenti);
		tabGenerale.addTab("ConsolleSQL", consolle);

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

	public JTabbedPane getTabGenerale() {
		return tabGenerale;
	}

	public void setTabGenerale(JTabbedPane tabGenerale) {
		GeneralFrame.tabGenerale = tabGenerale;
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

	public NewSql getConsolle() {
		return consolle;
	}

	public void setConsolle(NewSql consolle) {
		GeneralFrame.consolle = consolle;
	}

}
