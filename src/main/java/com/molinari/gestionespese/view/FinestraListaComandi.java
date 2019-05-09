package com.molinari.gestionespese.view;

import java.awt.Container;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.molinari.gestionespese.business.Finestra;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.table.TableBase;
import com.molinari.utility.messages.I18NManager;

public class FinestraListaComandi implements Finestra {

	private Container container;
	private TableBase            table;
	private JScrollPane       scrollPane;

	public FinestraListaComandi(Container cont) {
		container = new PannelloBase(cont);
		PannelloBase padre = (PannelloBase) ((PannelloBase)getContainer()).getContenitorePadre();
		getContainer().setLayout(null);
		getContainer().setSize(padre.getWidth(), padre.getHeight());
		scrollPane = new JScrollPane();
		insertDati();

	}

	public void insertDati() {
		final String lista = I18NManager.getSingleton().getMessaggio("lista");
		table = new TableBase(container);
		final Object[][] dati = generaDati();
		table.setModel(new DefaultTableModel(dati, new String[] { lista }));

		table.setBounds(0, 0, getContainer().getWidth(), getContainer().getHeight());

		scrollPane.setViewportView(table);
		// Add the scroll pane to this panel.
		getContainer().add(scrollPane);
		scrollPane.setBounds(0, 0, getContainer().getWidth(), getContainer().getHeight());
	}

	public Object[][] generaDati() {
		return ControlloreBase.getSingleton().getCommandManager().generaDati();
	}

	public TableBase getTable() {
		return table;
	}

	public void setTable(TableBase table) {
		this.table = table;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
	
	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}

}
