

package com.molinari.gestionespese.view.tabellamesi;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JScrollPane;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.generatori.TableModelUscite;
import com.molinari.gestionespese.view.OggettoVistaBase;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.table.TableBase;
import com.molinari.utility.graphic.component.table.TableModel;

public class TabellaUscita {

	OggettoVistaBase panel;

	private static String[][] primo;
	private JScrollPane scrollPane;



	public TabellaUscita(Container container) {
		panel = new OggettoVistaBase(new GridLayout(1,0));

		try {
			TableModelUscite model = new TableModelUscite(null);
			final TableBase table = createTable(model, container);
			//Create the scroll pane and add the table to it.
			scrollPane = new JScrollPane(table);

			//Add the scroll pane to this panel.
			getPanel().add(scrollPane);
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

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
	 * @param container 
	 *
	 * @param primo
	 * @param nomiColonne
	 * @return TableBase
	 */
	public static TableBase createTable(TableModel model, Container container) {
		final List<String> nomiColonne = model.getNomiColonne().getListaCelle();
		String[][] dati = (String[][]) model.getMatrice();
		String[] colonne = nomiColonne.toArray(new String[nomiColonne.size()]);
		final TableBase table = new TableBase(dati, colonne, container);

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		final PannelloBase panelTabs = Controllore.getGeneralFrame().getPannelTabs().getPanel();
		table.setRowHeight(panelTabs.getHeight()/17);
		return table;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}


	public void setScrollPane(final JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public OggettoVistaBase getPanel() {
		return panel;
	}

	public void setPanel(OggettoVistaBase panel) {
		this.panel = panel;
	}
}