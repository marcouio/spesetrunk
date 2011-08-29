package view.note;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import view.font.ButtonF;
import view.font.TextAreaF;
import domain.Note;
import domain.wrapper.WrapNote;

/**
 * Questa classe è il pannello per rappresentare una singola nota
 *
 */
public class PannelloNota extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Note        nota;
	private final JFrame      padre;

	public PannelloNota(final Note note2, JFrame padre) {
		this.nota = note2;
		this.padre = padre;
		setLayout(null);

		JLabel lNome = new JLabel();
		lNome.setText(this.nota.getnome());
		lNome.setBounds(4, 16, 150, 15);
		add(lNome);

		JTextArea taDescrizione = new TextAreaF();
		taDescrizione.setText(this.nota.getDescrizione());
		taDescrizione.setBounds(6, 63, 190, 85);
		add(taDescrizione);

		// specifica se �true� di andare a capo automaticamente a
		// fine riga
		taDescrizione.setLineWrap(true);
		// va a capo con la parola se �true� o col singolo carattere
		// se �false�
		taDescrizione.setWrapStyleWord(true);
		taDescrizione.setAutoscrolls(true);
		taDescrizione.setEditable(false);

		JLabel lData = new JLabel();
		lData.setText(this.nota.getData());
		lData.setBounds(6, 36, 148, 15);
		add(lData);

		JButton eliminaNota = new ButtonF();
		eliminaNota.setText("-");
		eliminaNota.addActionListener(new AscoltatoreEliminaNota(this, nota));

		eliminaNota.setBounds(149, 17, 44, 15);
		add(eliminaNota);

		ButtonF btnfAggiorna = new ButtonF();
		btnfAggiorna.addActionListener(new AscoltatoreApriPannelloUpdateNote(this.padre, new WrapNote(nota)));

		btnfAggiorna.setText("!=");
		btnfAggiorna.setBounds(147, 36, 46, 15);
		add(btnfAggiorna);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame f = new JFrame();
				Note nota = new Note();
				nota.setNome("nome");
				nota.setDescrizione("descrizione");
				nota.setData("2011/11/11");
				PannelloNota fe = new PannelloNota(nota, f);
				f.getContentPane().add(fe);
				f.setVisible(true);
				f.setSize(280, 195);
			}
		});
	}

	public JFrame getPadre() {
		return padre;

	}
}
