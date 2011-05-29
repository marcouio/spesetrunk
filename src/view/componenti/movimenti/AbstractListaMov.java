package view.componenti.movimenti;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TableF;
import view.font.TextFieldF;
import business.Database;
import domain.Entrate;

public abstract class AbstractListaMov extends view.OggettoVistaBase {

	private static final long serialVersionUID = 1L;
	int                       numMovimenti     = 10;
	private JTable            table;
	private JTable            table1;
	private JScrollPane       scrollPane;
	protected JTextField      campo;
	String[][]                movimenti;
	protected ButtonF         pulsanteNMovimenti;
	protected JDialog         dialog;

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
		this.dialog = createDialog();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(null);
			this.setPreferredSize(new Dimension(1000, 605));
			final JLabel movim = new LabelTesto("Movimenti:");
			movim.setBounds(24, 5, 89, 30);
			this.add(movim);
			campo = new TextFieldF();
			campo.setBounds(105, 8, 43, 25);
			campo.setText("20");
			numMovimenti = Integer.parseInt(campo.getText());
			this.add(campo);
			pulsanteNMovimenti = new ButtonF();
			pulsanteNMovimenti.setText("Cambia");
			pulsanteNMovimenti.setBounds(164, 9, 89, 25);
			this.add(pulsanteNMovimenti);

			final String[] nomiColonne = createNomiColonne();

			movimenti = createMovimenti();

			table = new TableF(movimenti, nomiColonne);
			impostaTable(table);

			// Create the scroll pane and add the table to it.
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(table);

			// Add the scroll pane to this panel.
			this.add(scrollPane);
			scrollPane.setBounds(21, 38, 948, 386);

			final ButtonF updateButton = new ButtonF();
			this.add(updateButton);
			updateButton.setText("Aggiorna");
			updateButton.setBounds(780, 438, 95, 21);

			final ButtonF deleteButton = new ButtonF();
			this.add(deleteButton);
			deleteButton.setText("Cancella");
			deleteButton.setBounds(887, 438, 82, 21);

			final ButtonF btnFiltraMovimenti = new ButtonF();
			btnFiltraMovimenti.setText("Filtra Movimenti");
			btnFiltraMovimenti.setBounds(277, 8, 179, 25);
			btnFiltraMovimenti.addActionListener(getListener());

			add(btnFiltraMovimenti);

			// TODO implementare actionlistener all'interno delle sottoclassi
			deleteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						dialog = getDialog();
						dialog.setSize(400, 220);
						dialog.setVisible(true);
						dialog.setModalityType(ModalityType.APPLICATION_MODAL);
						Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			});

			updateButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					dialog.setSize(400, 220);
					dialog.setVisible(true);
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					try {
						Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			});

		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	public abstract JDialog getDialog();

	public abstract ActionListener getListener();

	private void impostaTable(final JTable table2) {
		table2.setPreferredScrollableViewportSize(new Dimension(900, 550));
		table2.setFillsViewportHeight(true);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table2.setRowHeight(26);
		table2.setBounds(0, 100, 900, 300);
		impostaTableSpecifico(table2);
	}

	public abstract void impostaTableSpecifico(JTable table);

	public abstract String[][] createMovimenti();

	public abstract String[] createNomiColonne();

	public abstract JDialog createDialog();

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

	public JTable getTable1() {
		return table1;
	}

	public void setTable1(final JTable table1) {
		this.table1 = table1;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(final JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(final JTable table) {
		this.table = table;
	}
}