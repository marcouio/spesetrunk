package view.componenti.movimenti;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import domain.wrapper.Model;

public abstract class AbstractListaMov extends view.OggettoVistaBase {

	private static final long serialVersionUID = 1L;
	int                       numMovimenti     = 10;
	private JTable            table;
	private JTable            table1;
	private JScrollPane       scrollPane;
	private JTextField        campo;

	public static void main(String[] args) {

		JFrame frame = new JFrame();

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

			final JDialog dialog = createDialog();
			this.setLayout(null);
			this.setPreferredSize(new Dimension(1000, 605));
			JLabel movim = new LabelTesto("Movimenti:");
			movim.setBounds(143, 22, 89, 30);
			this.add(movim);
			campo = new TextFieldF();
			campo.setBounds(94, 25, 43, 25);
			campo.setText("10");
			numMovimenti = Integer.parseInt(campo.getText());
			this.add(campo);
			ButtonF pulsanteNMovimenti = new ButtonF("Cambia");
			pulsanteNMovimenti.setBounds(219, 26, 72, 25);
			this.add(pulsanteNMovimenti);

			final String[] nomiColonne = createNomiColonne();

			// TODO aggiungere listener all'interno delle classi figlie
			// modifica movimenti visibili
			pulsanteNMovimenti.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Model.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Inserire un valore numerico: " + e1.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
				}
			});
			String[][] movimenti = createMovimenti();

			table = new TableF(movimenti, nomiColonne);
			impostaTable(table);

			// Create the scroll pane and add the table to it.
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(table);

			// Add the scroll pane to this panel.
			this.add(scrollPane);
			scrollPane.setBounds(21, 89, 850, 308);

			ButtonF updateButton = new ButtonF();
			this.add(updateButton);
			updateButton.setText("Aggiorna");
			updateButton.setBounds(684, 403, 95, 21);

			ButtonF deleteButton = new ButtonF();
			this.add(deleteButton);
			deleteButton.setText("Cancella");
			deleteButton.setBounds(790, 403, 82, 21);

			JButton btnFiltraMovimenti = new JButton("Filtra Movimenti");
			btnFiltraMovimenti.setBounds(334, 25, 179, 25);
			btnFiltraMovimenti.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						FiltraDialog dialog = new FiltraDialog();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			});
			add(btnFiltraMovimenti);

			// TODO implementare actionlistener all'interno delle sottoclassi
			deleteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						dialog.setSize(400, 220);
						dialog.setVisible(true);
						dialog.setModalityType(ModalityType.APPLICATION_MODAL);
						Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});

			updateButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.setSize(400, 220);
					dialog.setVisible(true);
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					try {
						Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void impostaTable(JTable table2) {
		table2.setPreferredScrollableViewportSize(new Dimension(900, 550));
		table2.setFillsViewportHeight(true);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table2.setRowHeight(26);
		table2.setBounds(30, 100, 900, 300);
		impostaTableSpecifico(table2);
	}

	public abstract void impostaTableSpecifico(JTable table);

	public abstract String[][] createMovimenti();

	public abstract String[] createNomiColonne();

	public abstract JDialog createDialog();

	public JTextField getCampo() {
		return campo;
	}

	public void setCampo(JTextField campo) {
		this.campo = campo;
	}

	public int getNumEntry() {
		return this.numMovimenti;
	}

	public void setNumEntry(int numEntry) {
		this.numMovimenti = numEntry;
	}

	public JTable getTable1() {
		return table1;
	}

	public void setTable1(JTable table1) {
		this.table1 = table1;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
}