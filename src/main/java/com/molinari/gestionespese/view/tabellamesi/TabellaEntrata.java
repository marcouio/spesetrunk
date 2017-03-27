

package com.molinari.gestionespese.view.tabellamesi;


import java.awt.GridLayout;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JScrollPane;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.generatori.TableModelEntrate;
import com.molinari.gestionespese.view.OggettoVistaBase;
import com.molinari.gestionespese.view.font.TableF;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.messages.I18NManager;

public class TabellaEntrata {

	private OggettoVistaBase panel;

	private static String[][] primo;
	private static String[] nomiColonne = {I18NManager.getSingleton().getMessaggio("fixity"),I18NManager.getSingleton().getMessaggio("variables")};

	private JScrollPane scrollPane;

	public TabellaEntrata() {
		panel = new OggettoVistaBase(new GridLayout(1,0));
		TableF table = null;
		try{
			final TableModelEntrate model = new TableModelEntrate(null);
			table = createTable(model);
		}catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		//Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);

		//Add the scroll pane to this panel.
		getPanel().add(scrollPane);
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
		final List<String> listaCelle = model.getNomiColonne().getListaCelle();
		final TableF table = new TableF(model.getMatrice(), listaCelle.toArray(new String[listaCelle.size()]));

		table.setFillsViewportHeight(true);
		final PannelloBase panelTabs = Controllore.getSingleton().getGeneralFrame().getPannelTabs().getPanel();
		table.setRowHeight(panelTabs.getHeight()/12);
		return table;
	}

	public static String[][] getPrimo() {
		return primo;
	}

	public static void setPrimo(final String[][] primo) {
		TabellaEntrata.primo = primo;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	protected void setScrollPane(final JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public static String[] getNomiColonne() {
		return nomiColonne;
	}

	public static void setNomiColonne(final String[] nomiColonne) {
		TabellaEntrata.nomiColonne = nomiColonne;
	}

	public OggettoVistaBase getPanel() {
		return panel;
	}

	public void setPanel(OggettoVistaBase panel) {
		this.panel = panel;
	}
}