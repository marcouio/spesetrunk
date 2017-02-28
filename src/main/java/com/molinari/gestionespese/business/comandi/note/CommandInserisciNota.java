package com.molinari.gestionespese.business.comandi.note;

import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.domain.wrapper.WrapNote;

import command.javabeancommand.AbstractCommandForJavaBean;
import db.dao.IDAO;
import grafica.componenti.alert.Alert;

public class CommandInserisciNota extends AbstractCommandForJavaBean<Note> {

	public CommandInserisciNota(final INote entita){
		final CacheNote cache = CacheNote.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapNote();
		this.entita = (Note) ((IDAO) entita).getEntitaPadre();

	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof Note && wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof Note && wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Inserita Nota " + entita.getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Inserita correttamente nota " + entita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Cancellata nota " + entita.getNome() + " precedentemente inserita");
		}
	}
}
