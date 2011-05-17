package view.note;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Controllore;
import business.DBUtil;
import business.comandi.CommandInserisciNota;
import domain.wrapper.WrapNote;

public class NoteView extends AbstractNoteView {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				NoteView dialog = new NoteView(new WrapNote(), new MostraNoteWiew());
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(0, 0, 346, 250);
				dialog.setVisible(true);
			}
		});
	}

	private final TextFieldF nota;
	private final TextAreaF  descrizione;
	private final TextFieldF data;

	public NoteView(WrapNote note, final MostraNoteWiew padre) {
		super(note);
		setTitle("Pannello Nota");
		getContentPane().setLayout(null);

		LabelTesto lbltstNota = new LabelTesto("Nome Spesa");
		lbltstNota.setText("Nota");
		lbltstNota.setBounds(13, 12, 97, 27);
		getContentPane().add(lbltstNota);

		nota = new TextFieldF();
		nota.setColumns(10);
		nota.setBounds(12, 38, 150, 27);
		getContentPane().add(nota);

		LabelTesto lbltstDa = new LabelTesto("Categorie");
		lbltstDa.setText("Data");
		lbltstDa.setBounds(181, 12, 77, 27);
		getContentPane().add(lbltstDa);

		descrizione = new TextAreaF();
		descrizione.setText("Inserisci qui la descrizione della nota");
		descrizione.setWrapStyleWord(true);
		descrizione.setLineWrap(true);
		descrizione.setAutoscrolls(true);
		descrizione.setBounds(13, 89, 318, 75);
		getContentPane().add(descrizione);

		LabelTesto lbltstDescrizioneNota = new LabelTesto("Descrizione Spesa");
		lbltstDescrizioneNota.setText("Descrizione Nota");
		lbltstDescrizioneNota.setBounds(14, 64, 123, 25);
		getContentPane().add(lbltstDescrizioneNota);

		data = new TextFieldF();
		data.setColumns(10);
		data.setBounds(181, 38, 150, 27);
		getContentPane().add(data);
		JButton btnInserisci = new ButtonF();
		btnInserisci.setText("Inserisci");
		btnInserisci.setBounds(13, 175, 318, 25);
		getContentPane().add(btnInserisci);
		btnInserisci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateNote();
				Controllore.getSingleton().getCommandManager().invocaComando(new CommandInserisciNota(wrapNote), null);
				padre.aggiornaVista();
			}

		});
	}

	private void updateNote() {
		setNome(nota.getText());

		if (AltreUtil.checkData(data.getText())) {
			setData(data.getText());
		} else {
			String messaggio = "La data va inserita con il seguente formato: aaaa/mm/gg";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}

		setDescrizione(descrizione.getText());
		setUtenti(Controllore.getSingleton().getUtenteLogin());
		setDataIns(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
	}
}
