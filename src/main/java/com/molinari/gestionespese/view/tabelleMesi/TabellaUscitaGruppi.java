package com.molinari.gestionespese.view.tabelleMesi;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.molinari.gestionespese.business.generatori.TableModelUsciteGruppi;
import com.molinari.gestionespese.view.OggettoVistaBase;
import com.molinari.gestionespese.view.font.TableF;

import controller.ControlloreBase;
import grafica.componenti.table.TableModel.Riga;

public class TabellaUscitaGruppi extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;
	private static JTable table;

	private static String[][] primo;
	private static JScrollPane scrollPane;

	public TabellaUscitaGruppi() {
		super(new GridLayout(1, 0));

		try {
			getDatiPerTabella();
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		add(scrollPane);

	}

	public static JTable getDatiPerTabella() throws Exception {

		final TableModelUsciteGruppi model = new TableModelUsciteGruppi(null);

		final Riga nomiColonne = model.getNomiColonne();
		table = new TableF(model.getMatrice(), nomiColonne.getListaCelle().toArray());
		table.setRowHeight(27);
		table.setPreferredScrollableViewportSize(new Dimension(700, 300));
		table.setFillsViewportHeight(true);
		return table;
	}

	private static void createAndShowGUI() throws Exception {
		// Create and set up the window.
		final JFrame frame = new JFrame("TabellaUscita");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		final TabellaUscitaGruppi newContentPane = new TabellaUscitaGruppi();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
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

	public static void main(final String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(() -> {
			try {
				createAndShowGUI();
			} catch (final Exception e) {
				ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			}
		});
	}

	public static JScrollPane getScrollPane() {
		return scrollPane;
	}

	public static void setScrollPane(final JScrollPane scrollPane) {
		TabellaUscitaGruppi.scrollPane = scrollPane;
	}
}