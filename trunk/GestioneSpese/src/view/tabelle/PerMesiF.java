package view.tabelle;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import view.OggettoVistaBase;

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

	private static TabellaEntrata      tabEntrate = new TabellaEntrata();
	private static TabellaUscita       tabUscite  = new TabellaUscita();
	// private static TabellaEntrataGruppi tabEG = new TabellaEntrataGruppi();
	private static TabellaUscitaGruppi tabUG      = new TabellaUscitaGruppi();

	private JTabbedPane                tabGenerale;

	public PerMesiF() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new Dimension(983, 545));
			this.setLayout(null);

			tabGenerale = new JTabbedPane();
			tabGenerale.setBounds(12, 65, 930, 468);
			tabGenerale.addTab("Entrate", tabEntrate);
			tabGenerale.addTab("Uscite", tabUscite);
			tabGenerale.addTab("Gruppi di uscite", tabUG);

			tabUscite.setBounds(26, 10, 400, 400);
			// tabGenerale.addTab("Entrate Gruppi", tabEG);
			// TabellaUscita.getTable().setRowHeight(27);
			// TabellaEntrata.getTable().setRowHeight(27);
			tabEntrate.setBounds(26, 10, 400, 400);
			this.add(tabGenerale);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the tabEntrate
	 */
	public static TabellaEntrata getTabEntrate() {
		return tabEntrate;
	}

	/**
	 * @param tabEntrate
	 *            the tabEntrate to set
	 */
	public static void setTabEntrate(TabellaEntrata tabEntrate) {
		PerMesiF.tabEntrate = tabEntrate;
	}

	/**
	 * @return the tabUscite
	 */
	public static TabellaUscita getTabUscite() {
		return tabUscite;
	}

	/**
	 * @param tabUscite
	 *            the tabUscite to set
	 */
	public static void setTabUscite(TabellaUscita tabUscite) {
		PerMesiF.tabUscite = tabUscite;
	}

}
