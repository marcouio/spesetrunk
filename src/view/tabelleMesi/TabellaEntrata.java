

package view.tabelleMesi;


import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import business.Controllore;
import business.generatori.TableModelEntrate;
import business.internazionalizzazione.I18NManager;
import grafica.componenti.ExceptionGraphics;
import grafica.componenti.contenitori.PannelloBase;
import view.OggettoVistaBase;
import view.font.TableF;

public class TabellaEntrata extends OggettoVistaBase {
	
	private static final long serialVersionUID = 1L;

	private static String[][] primo;
	private static String[] nomiColonne = {I18NManager.getSingleton().getMessaggio("fixity"),I18NManager.getSingleton().getMessaggio("variables")};

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
	public TableF createTable(TableModelEntrate model) {
		ArrayList<String> listaCelle = model.getNomiColonne().getListaCelle();
		TableF table = new TableF(model.getMatrice(), listaCelle.toArray(new String[listaCelle.size()]));

//		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		PannelloBase panel = Controllore.getSingleton().getGeneralFrame().getPannelTabs().getPanel();
		table.setRowHeight(panel.getHeight()/12);
//		table.setRowHeight(27);
		return table;
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
	
	public static String[] getNomiColonne() {
		return nomiColonne;
	}

	public static void setNomiColonne(final String[] nomiColonne) {
		TabellaEntrata.nomiColonne = nomiColonne;
	}
}