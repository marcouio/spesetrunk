

package com.molinari.gestionespese.view.tabellamesi;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JScrollPane;

import com.molinari.gestionespese.business.generatori.TableModelUscite;
import com.molinari.gestionespese.view.OggettoVistaBase;
import com.molinari.gestionespese.view.font.TableF;

import controller.ControlloreBase;
import grafica.componenti.table.TableModel;

public class TabellaUscita {

	OggettoVistaBase panel;

	private static String[][] primo;
	private static JScrollPane scrollPane;



	public TabellaUscita() {
		panel = new OggettoVistaBase(new GridLayout(1,0));

		TableModelUscite model = null;
		try {
			model = new TableModelUscite(null);
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		final TableF table = createTable(model);
		//Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);

		//Add the scroll pane to this panel.
		getPanel().add(scrollPane);

	}

	public static String[][] getPrimo() {
		return primo;
	}

	public static void setPrimo(final String[][] primo) {
		TabellaUscita.primo = primo;
	}

	/**
	 *
	 * Permette di generare una tabella
	 *
	 * @param primo
	 * @param nomiColonne
	 * @return TableF
	 */
	public static TableF createTable(TableModel model) {
		final ArrayList<String> nomiColonne = model.getNomiColonne().getListaCelle();
		final TableF table = new TableF(model.getMatrice(), nomiColonne.toArray(new String[nomiColonne.size()]));

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setRowHeight(27);
		return table;
	}

	public static JScrollPane getScrollPane() {
		return scrollPane;
	}


	public void setScrollPane(final JScrollPane scrollPane) {
		TabellaUscita.scrollPane = scrollPane;
	}

	public OggettoVistaBase getPanel() {
		return panel;
	}

	public void setPanel(OggettoVistaBase panel) {
		this.panel = panel;
	}
}