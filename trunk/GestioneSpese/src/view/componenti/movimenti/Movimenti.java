package view.componenti.movimenti;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import view.OggettoVistaBase;

public class Movimenti extends OggettoVistaBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 900, 650);
		frame.getContentPane().add(new Movimenti());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private JTabbedPane           tabGenerale;
	// private ListaMovEntrat tabMovEntrate;
	// private ListaMovUscite tabMovUscite;
	private ListaMovimentiEntrate tabMovEntrate;
	private ListaMovimentiUscite  tabMovUscite;

	public Movimenti() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(900, 650));
			this.setLayout(null);

			tabMovEntrate = new ListaMovimentiEntrate();

			tabGenerale = new JTabbedPane();
			tabGenerale.setBounds(65, 65, 800, 600);
			tabGenerale.addTab("Movimenti Entrate", tabMovEntrate);

			tabMovEntrate.setBounds(20, 10, 700, 500);
			tabMovUscite = new ListaMovimentiUscite();
			tabGenerale.addTab("Movimenti Uscite", tabMovUscite);
			tabMovUscite.setBounds(20, 10, 700, 500);

			this.add(tabGenerale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
