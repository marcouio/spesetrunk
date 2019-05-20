package com.molinari.gestionespese.view.tabellamesi;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.logging.Level;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.generatori.TableModelUsciteGruppi;
import com.molinari.gestionespese.view.OggettoVistaBase;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.table.TableBase;
import com.molinari.utility.graphic.component.table.TableModel.Riga;

public class TabellaUscitaGruppi {

	OggettoVistaBase panel;
	
	private static JTable table;

	private static String[][] primo;
	private JScrollPane scrollPane;

	public TabellaUscitaGruppi(Container container) {
		panel = new OggettoVistaBase(new GridLayout(1, 0));

		try {
			getDatiPerTabella(container);
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		getPanel().add(scrollPane);

	}

	public static JTable getDatiPerTabella(Container container) {

		final TableModelUsciteGruppi model = new TableModelUsciteGruppi(null);

		final Riga nomiColonne = model.getNomiColonne();
		String[][] dati = (String[][]) model.getMatrice();
		String[] colonne = nomiColonne.getListaCelle().toArray(new String[nomiColonne.getListaCelle().size()]);
		table = new TableBase(dati, colonne, container);
		final PannelloBase panelTabs = Controllore.getGeneralFrame().getPannelTabs().getPanel();
		table.setRowHeight(panelTabs.getHeight()/17);
		table.setPreferredScrollableViewportSize(new Dimension(700, 300));
		table.setFillsViewportHeight(true);
		return table;
	}

	public static String[][] getPrimo() {
		return primo;
	}

	public static void setPrimo(final String[][] primo) {
		TabellaUscitaGruppi.primo = primo;
	}

	public static JTable getTable() {
		return table;
	}

	public static void setTable(final JTable table) {
		TabellaUscitaGruppi.table = table;
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