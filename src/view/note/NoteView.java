package view.note;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import view.font.ButtonF;
import view.font.LabelListaGruppi;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Controllore;
import business.DBUtil;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import business.cache.CacheNote;
import business.comandi.note.CommandInserisciNota;
import business.comandi.note.CommandUpdateNota;
import domain.INote;
import domain.Note;
import domain.Utenti;
import domain.wrapper.WrapNote;

public class NoteView extends AbstractNoteView {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				NoteView dialog = new NoteView(new WrapNote(), new MostraNoteView());
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(0, 0, 346, 250);
				dialog.setVisible(true);
			}
		});
	}

	private final TextFieldF nota;
	private final TextAreaF  descrizione;
	private final TextFieldF data;
	private ButtonF          btnInserisci;

	public NoteView(final WrapNote note, final JFrame padre) {
		super(note);
		setTitle("Pannello Nota");
		getContentPane().setLayout(null);

		LabelListaGruppi lbltstNota = new LabelListaGruppi("Nome Spesa");
		lbltstNota.setText("Nota");
		lbltstNota.setBounds(13, 12, 97, 27);
		getContentPane().add(lbltstNota);

		nota = new TextFieldF();
		nota.setText("Nome della nota");
		nota.setColumns(10);
		nota.setBounds(12, 38, 150, 27);
		getContentPane().add(nota);

		LabelListaGruppi lbltstDa = new LabelListaGruppi("Categorie");
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

		LabelListaGruppi lbltstDescrizioneNota = new LabelListaGruppi("Descrizione Spesa");
		lbltstDescrizioneNota.setText("Descrizione Nota");
		lbltstDescrizioneNota.setBounds(14, 64, 123, 25);
		getContentPane().add(lbltstDescrizioneNota);

		data = new TextFieldF();
		data.setColumns(10);
		data.setBounds(181, 38, 150, 27);
		data.setText(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
		getContentPane().add(data);

		btnInserisci = new ButtonF();
		btnInserisci.setText("Inserisci");
		btnInserisci.setBounds(13, 175, 318, 25);
		getContentPane().add(btnInserisci);
		btnInserisci.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(ActionEvent e) throws Exception {
				int id = CacheNote.getSingleton().getAllNoteForUtenteEAnno().size();
				if (e.getActionCommand().equals("Aggiorna")) {
					aggiornaModelDaVista(null);
					if (nonEsistonoCampiNonValorizzati()) {
						Controllore.invocaComando(new CommandUpdateNota((Note) note.getEntitaPadre(), (INote) wrapNote.getEntitaPadre()));
						((MostraNoteView) padre).aggiornaVista();
						dispose();
					} else {
						Alert.segnalazioneErroreGrave("Nota non aggiornata: tutti i campi devono essere valorizzati");
					}
				} else {
					WrapNote wNote = new WrapNote();
					aggiornaModelDaVista(wNote);
					if (nonEsistonoCampiNonValorizzati()) {
						wNote.setIdNote(id);
						wNote.getEntitaPadre().setIdEntita(Integer.toString(id));
						Controllore.invocaComando(new CommandInserisciNota(wNote));
						((MostraNoteView) padre).aggiornaVista();
						dispose();
					} else {
						Alert.segnalazioneErroreGrave("Nota non inserita: tutti i campi devono essere valorizzati");
					}
				}
			}

		});
	}

	private boolean nonEsistonoCampiNonValorizzati() {
		return getNome() != null && getDescrizione() != null && getData() != null && getDataIns() != null
		                && getUtenti() != null;
	}

	private void aggiornaModelDaVista(WrapNote wNote) {
		if (wNote != null) {
			wrapNote = wNote;
		}
		setNome(nota.getText());

		if (AltreUtil.checkData(data.getText())) {
			setData(data.getText());
		} else {
			String messaggio = "La data va inserita con il seguente formato: aaaa/mm/gg";
			Alert.errore(messaggio, Alert.TITLE_ERROR);
		}

		setDescrizione(descrizione.getText());
		setUtenti((Utenti) Controllore.getSingleton().getUtenteLogin());
		setDataIns(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
	}

	public void setNota(String stringNota) {
		nota.setText(stringNota);
	}

	public void setTaDescrizione(String stringaDescrizione) {
		descrizione.setText(stringaDescrizione);
	}

	public void settfData(String stringaData) {
		data.setText(stringaData);
	}

	public TextFieldF getNota() {
		return nota;
	}

	public TextAreaF gettaDescrizione() {
		return descrizione;
	}

	public TextFieldF getftData() {
		return data;
	}

	public ButtonF getBtnInserisci() {
		return btnInserisci;
	}

	public void setBtnInserisci(ButtonF btnInserisci) {
		this.btnInserisci = btnInserisci;
	}
}
