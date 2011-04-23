package view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import view.font.TableF;
import business.Controllore;

public class FinestraListaComandi extends JFrame {

	private static final long serialVersionUID = 1L;
	private TableF            table;
	private JScrollPane       scrollPane;

	public FinestraListaComandi() {
		setResizable(false);
		getContentPane().setLayout(null);
		this.setTitle("Lista comandi");
		table = new TableF();
		Object[][] dati = generaDati();
		table.setModel(new DefaultTableModel(dati, new String[] { "Lista Comandi" }));

		table.setBounds(12, 12, 254, 61);

		scrollPane = new JScrollPane();
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
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame f = new JFrame();
				FinestraListaComandi fe = new FinestraListaComandi();
				f.getContentPane().add(fe);
				f.setVisible(true);
				f.setSize(280, 500);
			}
		});
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
}
