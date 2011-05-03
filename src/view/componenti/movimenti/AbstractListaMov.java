package view.componenti.movimenti;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
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
	static int                numMovimenti     = 10;
	private JTable            table;
	private JTable            table1;
	private JScrollPane       scrollPane;
	private JTextField        campo;

	public static void main(String[] args) {

		JFrame frame = new JFrame();

		// frame.getContentPane().add(new AbstractListaMov());
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
			this.setSize(500, 250);
			this.setPreferredSize(new Dimension(800, 505));
			JLabel movim = new LabelTesto("Movimenti:");
			movim.setBounds(166, 20, 89, 30);
			this.add(movim);
			campo = new TextFieldF();
			campo.setBounds(255, 26, 60, 25);
			campo.setText("10");
			numMovimenti = Integer.parseInt(campo.getText());
			this.add(campo);
			ButtonF pulsanteNMovimenti = new ButtonF("Cambia");
			pulsanteNMovimenti.setBounds(317, 27, 90, 25);
			this.add(pulsanteNMovimenti);

			final String[] nomiColonne = createNomiColonne();

			// final String[] nomiColonne = (String[])
			// AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);

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
			// movimenti = Model.getSingleton().movimentiEntrate(numMovimenti,
			// Entrate.NOME_TABELLA);
			String[][] movimenti = createMovimenti();

			table = new TableF(movimenti, nomiColonne);
			impostaTable(table);

			// Create the scroll pane and add the table to it.
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(table);

			// Add the scroll pane to this panel.
			this.add(scrollPane);
			scrollPane.setBounds(21, 89, 700, 308);

			ButtonF updateButton = new ButtonF();
			this.add(updateButton);
			updateButton.setText("Aggiorna");
			updateButton.setBounds(193, 427, 95, 21);

			ButtonF deleteButton = new ButtonF();
			this.add(deleteButton);
			deleteButton.setText("Cancella");
			deleteButton.setBounds(299, 427, 82, 21);
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

	public abstract void impostaTable(JTable table);

	public abstract String[][] createMovimenti();

	public abstract String[] createNomiColonne();

	public abstract JDialog createDialog();

	public JTextField getCampo() {
		return campo;
	}

	public void setCampo(JTextField campo) {
		this.campo = campo;
	}

	public static int getNumEntry() {
		return AbstractListaMov.numMovimenti;
	}

	public void setNumEntry(int numEntry) {
		AbstractListaMov.numMovimenti = numEntry;
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