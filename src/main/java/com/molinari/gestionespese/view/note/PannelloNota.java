package com.molinari.gestionespese.view.note;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.domain.wrapper.WrapNote;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.TextAreaF;

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

		final JLabel lNome = new JLabel();
		lNome.setText(this.nota.getnome());
		lNome.setBounds(4, 16, 150, 15);
		add(lNome);

		final JTextArea taDescrizione = new TextAreaF();
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

		final JLabel lData = new JLabel();
		lData.setText(this.nota.getData());
		lData.setBounds(6, 36, 148, 15);
		add(lData);

		final JButton eliminaNota = new ButtonF();
		eliminaNota.setText("-");
		eliminaNota.addActionListener(new AscoltatoreEliminaNota(this, nota));

		eliminaNota.setBounds(149, 17, 44, 15);
		add(eliminaNota);

		final ButtonF btnfAggiorna = new ButtonF();
		btnfAggiorna.addActionListener(new AscoltatoreApriPannelloUpdateNote(this.padre, new WrapNote(nota)));

		btnfAggiorna.setText("!=");
		btnfAggiorna.setBounds(147, 36, 46, 15);
		add(btnfAggiorna);
	}

	public JFrame getPadre() {
		return padre;

	}
}


