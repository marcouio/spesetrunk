package com.molinari.gestionespese.view.note;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.JFrame;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.business.comandi.note.CommandInserisciNota;
import com.molinari.gestionespese.business.comandi.note.CommandUpdateNota;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapNote;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.TextAreaF;
import com.molinari.gestionespese.view.font.TextFieldF;

import grafica.componenti.alert.Alert;

public class NoteView extends AbstractNoteView {

	private static final long serialVersionUID = 1L;
	private final TextFieldF nota;
	private final TextAreaF  descrizione;
	private final TextFieldF data;
	private ButtonF          btnInserisci;

	public NoteView(final WrapNote note, final JFrame padre) {
		super(note);
		setTitle("Pannello Nota");
		getContentPane().setLayout(null);

		final LabelListaGruppi lbltstNota = new LabelListaGruppi("Nome Spesa");
		lbltstNota.setText("Nota");
		lbltstNota.setBounds(13, 12, 97, 27);
		getContentPane().add(lbltstNota);

		nota = new TextFieldF();
		nota.setText("Nome della nota");
		nota.setColumns(10);
		nota.setBounds(12, 38, 150, 27);
		getContentPane().add(nota);

		final LabelListaGruppi lbltstDa = new LabelListaGruppi("Categorie");
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

		final LabelListaGruppi lbltstDescrizioneNota = new LabelListaGruppi("Descrizione Spesa");
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
			public void actionPerformedOverride(ActionEvent e) {
				final int id = CacheNote.getSingleton().getAllNoteForUtenteEAnno().size();
				if ("Aggiorna".equals(e.getActionCommand())) {
					aggiornaModelDaVista(null);
					if (nonEsistonoCampiNonValorizzati()) {
						Controllore.invocaComando(new CommandUpdateNota((Note) note.getEntitaPadre(), (INote) getWrapNote().getEntitaPadre()));
						((MostraNoteView) padre).aggiornaVista();
						dispose();
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
						dispose();
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
