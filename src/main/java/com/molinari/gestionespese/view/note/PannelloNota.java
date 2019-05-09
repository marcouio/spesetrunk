package com.molinari.gestionespese.view.note;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.wrapper.WrapNote;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;

/**
 * Questa classe è il pannello per rappresentare una singola nota
 *
 */
public class PannelloNota extends JPanel {

	private static final long serialVersionUID = 1L;

	private final transient INote        nota;
	private final transient Finestra      padre;

	public PannelloNota(final INote note2, Finestra padre) {
		this.nota = note2;
		this.padre = padre;
		setLayout(null);

		final JLabel lNome = new JLabel();
		lNome.setText(this.nota.getNome());
		lNome.setBounds(4, 16, 150, 15);
		add(lNome);

		final JTextArea taDescrizione = new TextAreaBase(this);
		taDescrizione.setText(this.nota.getDescrizione());
		taDescrizione.setBounds(6, 63, 190, 85);
		add(taDescrizione);

		// specifica se andare a capo automaticamente a fine riga
		taDescrizione.setLineWrap(true);
		// va a capo con la parola se col singolo carattere
		taDescrizione.setWrapStyleWord(true);
		taDescrizione.setAutoscrolls(true);
		taDescrizione.setEditable(false);

		final JLabel lData = new JLabel();
		lData.setText(this.nota.getData());
		lData.setBounds(6, 36, 148, 15);
		add(lData);

		final JButton eliminaNota = new ButtonBase(this);
		eliminaNota.setText("-");
		eliminaNota.addActionListener(new AscoltatoreEliminaNota(this, nota));

		eliminaNota.setBounds(149, 17, 44, 15);
		add(eliminaNota);

		final ButtonBase btnfAggiorna = new ButtonBase(this);
		btnfAggiorna.addActionListener(new AscoltatoreApriPannelloUpdateNote(this.padre, new WrapNote(nota)));

		btnfAggiorna.setText("!=");
		btnfAggiorna.setBounds(147, 36, 46, 15);
		add(btnfAggiorna);
	}

	public Finestra getPadre() {
		return padre;

	}
}


