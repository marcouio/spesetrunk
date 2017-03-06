package com.molinari.gestionespese.view.note;

import javax.swing.JDialog;

import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapNote;

public class AbstractNoteView {
	
	private JDialog dialog = new JDialog();
	private WrapNote           wrapNote         = null;

	public AbstractNoteView(WrapNote note) {
		this.wrapNote = note;
	}

	public void setNome(String nome) {
		wrapNote.setNome(nome);
	}

	public String getNome() {
		return wrapNote.getNome();
	}

	public void setDescrizione(String descrizione) {
		wrapNote.setDescrizione(descrizione);
	}

	public String getDescrizione() {
		return wrapNote.getDescrizione();
	}

	public void setData(String data) {
		wrapNote.setData(data);
	}

	public String getData() {
		return wrapNote.getData();
	}

	public void setDataIns(String dataIns) {
		wrapNote.setDataIns(dataIns);
	}

	public String getDataIns() {
		return wrapNote.getDataIns();
	}

	public Utenti getUtenti() {
		return wrapNote.getUtenti();
	}

	public void setUtenti(Utenti utente) {
		wrapNote.setUtenti(utente);
	}

	public WrapNote getWrapNote() {
		return wrapNote;
	}

	public void setWrapNote(WrapNote wrapNote) {
		this.wrapNote = wrapNote;
	}

	public JDialog getDialog() {
		return dialog;
	}

	public void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}

}
