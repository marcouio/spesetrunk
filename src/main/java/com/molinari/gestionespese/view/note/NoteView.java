package com.molinari.gestionespese.view.note;

import java.awt.event.ActionEvent;
import java.util.Date;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.business.comandi.note.CommandInserisciNota;
import com.molinari.gestionespese.business.comandi.note.CommandUpdateNota;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapNote;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;

public class NoteView extends AbstractNoteView {

	private final TextFieldBase nota;
	private final TextAreaBase  descrizione;
	private final TextFieldBase data;
	private ButtonBase          btnInserisci;

	public NoteView(final WrapNote note, final Finestra padre) {
		super(note);
		getDialog().setTitle("Pannello Nota");
		getDialog().getContentPane().setLayout(null);

		final LabelTestoPiccolo lbltstNota = new LabelTestoPiccolo("Nome Spesa", getDialog().getContentPane());
		lbltstNota.setText("Nota");
		lbltstNota.setBounds(13, 12, 97, 27);
		getDialog().getContentPane().add(lbltstNota);

		nota = new TextFieldBase(getDialog().getContentPane());
		nota.setText("Nome della nota");
		nota.setColumns(10);
		nota.setBounds(12, 38, 150, 27);
		getDialog().getContentPane().add(nota);

		final LabelTestoPiccolo lbltstDa = new LabelTestoPiccolo("Categorie", getDialog().getContentPane());
		lbltstDa.setText("Data");
		lbltstDa.setBounds(181, 12, 77, 27);
		getDialog().getContentPane().add(lbltstDa);

		descrizione = new TextAreaBase(getDialog().getContentPane());
		descrizione.setText("Inserisci qui la descrizione della nota");
		descrizione.setWrapStyleWord(true);
		descrizione.setLineWrap(true);
		descrizione.setAutoscrolls(true);
		descrizione.setBounds(13, 89, 318, 75);
		getDialog().getContentPane().add(descrizione);

		final LabelTestoPiccolo lbltstDescrizioneNota = new LabelTestoPiccolo("Descrizione Spesa", getDialog().getContentPane());
		lbltstDescrizioneNota.setText("Descrizione Nota");
		lbltstDescrizioneNota.setBounds(14, 64, 123, 25);
		getDialog().getContentPane().add(lbltstDescrizioneNota);

		data = new TextFieldBase(getDialog().getContentPane());
		data.setColumns(10);
		data.setBounds(181, 38, 150, 27);
		data.setText(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
		getDialog().getContentPane().add(data);

		btnInserisci = new ButtonBase(getDialog().getContentPane());
		btnInserisci.setText("Inserisci");
		btnInserisci.setBounds(13, 175, 318, 25);
		getDialog().getContentPane().add(btnInserisci);
		btnInserisci.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(ActionEvent e) {
				final int id = CacheNote.getSingleton().getAllNoteForUtenteEAnno().size();
				if ("Aggiorna".equals(e.getActionCommand())) {
					aggiornaModelDaVista(null);
					if (nonEsistonoCampiNonValorizzati()) {
						Controllore.invocaComando(new CommandUpdateNota((Note) note.getEntitaPadre(), (INote) getWrapNote().getEntitaPadre()));
						((MostraNoteView) padre).aggiornaVista();
						getDialog().dispose();
					} else {
						Alert.segnalazioneErroreGrave("Nota non aggiornata: tutti i campi devono essere valorizzati");
					}
				} else {
					final WrapNote wNote = new WrapNote();
					aggiornaModelDaVista(wNote);
					if (nonEsistonoCampiNonValorizzati()) {
						wNote.setIdNote(id);
						wNote.getEntitaPadre().setIdEntita(Integer.toString(id));
						Controllore.invocaComando(new CommandInserisciNota(wNote));
						((MostraNoteView) padre).aggiornaVista();
						getDialog().dispose();
					} else {
						Alert.segnalazioneErroreGrave("Nota non inserita: tutti i campi devono essere valorizzati");
					}
				}
			}

		});
	}

	private boolean nonEsistonoCampiNonValorizzati() {
		boolean dateIsNotNull = getData() != null && getDataIns() != null;
		return getNome() != null && getDescrizione() != null && dateIsNotNull
				&& getUtenti() != null;
	}

	private void aggiornaModelDaVista(WrapNote wNote) {
		if (wNote != null) {
			setWrapNote(wNote);
		}
		setNome(nota.getText());

		if (AltreUtil.checkData(data.getText())) {
			setData(data.getText());
		} else {
			final String messaggio = "La data va inserita con il seguente formato: aaaa/mm/gg";
			Alert.errore(messaggio, Alert.TITLE_ERROR);
		}

		setDescrizione(descrizione.getText());
		setUtenti((Utenti) Controllore.getUtenteLogin());
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

	public TextFieldBase getNota() {
		return nota;
	}

	public TextAreaBase gettaDescrizione() {
		return descrizione;
	}

	public TextFieldBase getftData() {
		return data;
	}

	public ButtonBase getBtnInserisci() {
		return btnInserisci;
	}

	public void setBtnInserisci(ButtonBase btnInserisci) {
		this.btnInserisci = btnInserisci;
	}
}
