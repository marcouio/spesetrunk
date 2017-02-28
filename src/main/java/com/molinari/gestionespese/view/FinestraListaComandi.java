package com.molinari.gestionespese.view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.view.font.TableF;

public class FinestraListaComandi extends JFrame {

	private static final long serialVersionUID = 1L;
	private TableF            table;
	private JScrollPane       scrollPane;

	public FinestraListaComandi() {
		setResizable(false);
		this.setSize(250, 425);
		getContentPane().setLayout(null);
		final String lista = I18NManager.getSingleton().getMessaggio("lista");
		this.setTitle(lista);
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
		getContentPane().add(scrollPane);
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

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			final JFrame f = new JFrame();
			final FinestraListaComandi fe = new FinestraListaComandi();
			f.getContentPane().add(fe);
			f.setVisible(true);
			f.setSize(280, 500);
		});
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
}
