package com.molinari.gestionespese.view;

import java.awt.Container;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.view.font.TableF;

import grafica.componenti.contenitori.FrameBase;
import grafica.componenti.contenitori.PannelloBase;

public class FinestraListaComandi implements Finestra {

	private Container container;
	private TableF            table;
	private JScrollPane       scrollPane;

	public FinestraListaComandi(FrameBase cont) {
		container = new PannelloBase(cont.getContentPane());
		getContainer().setSize(250, 425);
		getContainer().setLayout(null);
		scrollPane = new JScrollPane();
		insertDati();



	}

	public void insertDati() {
		final String lista = I18NManager.getSingleton().getMessaggio("lista");
		table = new TableF();
		final Object[][] dati = generaDati();
		table.setModel(new DefaultTableModel(dati, new String[] { lista }));

		table.setBounds(12, 12, 254, 61);

		scrollPane.setViewportView(table);
		// Add the scroll pane to this panel.
		getContainer().add(scrollPane);
		scrollPane.setBounds(21, 23, 214, 337);
	}

	public Object[][] generaDati() {
		return Controllore.getSingleton().getCommandManager().generaDati();
	}

	public TableF getTable() {
		return table;
	}

	public void setTable(TableF table) {
		this.table = table;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
	
	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

}
