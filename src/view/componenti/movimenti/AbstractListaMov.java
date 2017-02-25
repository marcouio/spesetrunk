package view.componenti.movimenti;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import business.internazionalizzazione.I18NManager;

import view.font.ButtonF;
import view.font.LabelListaGruppi;
import view.font.TableF;
import view.font.TextFieldF;

public abstract class AbstractListaMov extends view.OggettoVistaBase {
	private static final long serialVersionUID = 1L;
	int numMovimenti = 10;
	TableF table;
	private JScrollPane scrollPane;
	protected JTextField campo;
	String[][] movimenti;
	protected ButtonF pulsanteNMovimenti;
	protected JDialog dialog;
	protected ButtonF updateButton;
	protected ButtonF deleteButton;

	private AscoltatoreBottoniEntrata ascoltatore;

	protected void setMovimenti(final String[][] movimenti) {
		this.movimenti = movimenti;
	}

	public static void main(final String[] args) {

		final JFrame frame = new JFrame();

		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public AbstractListaMov() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(null);
			this.setPreferredSize(new Dimension(1000, 605));
			final JLabel movim = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("transactions")+":");
			movim.setBounds(24, 5, 89, 30);
			this.add(movim);
			campo = new TextFieldF();
			campo.setBounds(116, 7, 43, 25);
			campo.setText("20");
			numMovimenti = Integer.parseInt(campo.getText());
			this.add(campo);
			pulsanteNMovimenti = new ButtonF();
			pulsanteNMovimenti.setText(I18NManager.getSingleton().getMessaggio("change"));
			pulsanteNMovimenti.setBounds(178, 7, 89, 25);
			this.add(pulsanteNMovimenti);

			final String[] nomiColonne = createNomiColonne();

			movimenti = createMovimenti();

			table = new TableF(movimenti, nomiColonne);
			impostaTable(table);
			if (this instanceof ListaMovimentiEntrate) {
				table.addMouseListener(new AscoltatoreBottoniEntrata(this.getTable()));
			} else if (this instanceof ListaMovimentiUscite) {
				table.addMouseListener(new AscoltatoreBottoniUscita(this.getTable()));
			}

			// Create the scroll pane and add the table to it.
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(table);

			// Add the scroll pane to this panel.
			this.add(scrollPane);
			scrollPane.setBounds(21, 38, 948, 386);

			final ButtonF btnFiltraMovimenti = new ButtonF();
			btnFiltraMovimenti.setText(I18NManager.getSingleton().getMessaggio("filtertrans"));
			btnFiltraMovimenti.setBounds(292, 6, 179, 25);
			btnFiltraMovimenti.addActionListener(getListener());

			add(btnFiltraMovimenti);

		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	protected abstract String getTipo();

	public abstract ActionListener getListener();

	private void impostaTable(final JTable table2) {
		table2.setPreferredScrollableViewportSize(new Dimension(900, 550));
		table2.setFillsViewportHeight(true);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table2.setRowHeight(56);
		table2.setBounds(0, 100, 900, 300);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table2.addMouseListener(ascoltatore);
		impostaTableSpecifico();
	}

	public abstract void impostaTableSpecifico();

	public abstract String[][] createMovimenti();

	public abstract String[] createNomiColonne();

	public JTextField getCampo() {
		return campo;
	}

	public void setCampo(final JTextField campo) {
		this.campo = campo;
	}

	public int getNumEntry() {
		return this.numMovimenti;
	}

	public void setNumEntry(final int numEntry) {
		this.numMovimenti = numEntry;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(final JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public TableF getTable() {
		return table;
	}

	public void setTable(final TableF table) {
		this.table = table;
	}

}