package view.note;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import view.font.ButtonF;
import business.Controllore;
import business.comandi.CommandDeleteNota;
import domain.Note;

public class PannelloNota extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Note        nota;

	public PannelloNota(final Note nota) {
		this.nota = nota;
		setLayout(null);

		JLabel lNome = new JLabel();
		lNome.setText(this.nota.getnome());
		lNome.setBounds(12, 12, 175, 15);
		add(lNome);

		JTextArea taDescrizione = new JTextArea();
		taDescrizione.setText(this.nota.getDescrizione());
		taDescrizione.setBounds(12, 63, 251, 85);
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
		lData.setBounds(12, 36, 175, 15);
		add(lData);

		JButton button = new ButtonF();
		button.setText("Canc");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteNota(nota), null);
			}
		});

		button.setBounds(186, 12, 77, 15);
		add(button);

		ButtonF btnfAggiorna = new ButtonF();
		btnfAggiorna.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				        // TODO creare pannello di inserimento e modifica
			        }
		});

		btnfAggiorna.setText("Cambia");
		btnfAggiorna.setBounds(186, 36, 77, 15);
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
				PannelloNota fe = new PannelloNota(nota);
				f.getContentPane().add(fe);
				f.setVisible(true);
				f.setSize(280, 195);
			}
		});
	}
}
