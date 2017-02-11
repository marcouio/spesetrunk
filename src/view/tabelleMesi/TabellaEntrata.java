

package view.tabelleMesi;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import view.OggettoVistaBase;
import view.font.TableF;
import business.generatori.TableModelEntrate;
import business.internazionalizzazione.I18NManager;

public class TabellaEntrata extends OggettoVistaBase {
	
	private static final long serialVersionUID = 1L;

	private static String[][] primo;
	private static String[] nomiColonne = {I18NManager.getSingleton().getMessaggio("fixity"),I18NManager.getSingleton().getMessaggio("variables")};


	public static String[] getNomiColonne() {
		return nomiColonne;
	}

	public static void setNomiColonne(final String[] nomiColonne) {
		TabellaEntrata.nomiColonne = nomiColonne;
	}

	private static JScrollPane scrollPane;

	public TabellaEntrata() {
		super(new GridLayout(1,0));
		TableF table = null;
		try{
			TableModelEntrate model = new TableModelEntrate(null);
			table = createTable(model);
		}catch (final Exception e) {
			e.printStackTrace();
		}

		//Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);

		//Add the scroll pane to this panel.
		add(scrollPane);
	}
	
	/**
	 * 
	 * Permette di generare una tabella
	 * 
	 * @param primo
	 * @param nomiColonne
	 * @return TableF
	 */
	public static TableF createTable(TableModelEntrate model) {
		ArrayList<String> listaCelle = model.getNomiColonne().getListaCelle();
		TableF table = new TableF(model.getMatrice(), listaCelle.toArray(new String[listaCelle.size()]));

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setRowHeight(27);
		return table;
	}

	private static void createAndShowGUI() throws Exception {
		//Create and set up the window.
		final JFrame frame = new JFrame("Tabella Entrata");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		final TabellaEntrata newContentPane = new TabellaEntrata();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	public static String[][] getPrimo() {
		return primo;
	}

	public static void setPrimo(final String[][] primo) {
		TabellaEntrata.primo = primo;
	}

	public static JScrollPane getScrollPane() {
		return scrollPane;
	}

	protected void setScrollPane(final JScrollPane scrollPane) {
		TabellaEntrata.scrollPane = scrollPane;
	}
}