package com.molinari.gestionespese.business.comandi.note;

import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.domain.wrapper.WrapNote;

import command.javabeancommand.AbstractCommandForJavaBean;
import grafica.componenti.alert.Alert;

public class CommandDeleteNota extends AbstractCommandForJavaBean<Note> {

	public CommandDeleteNota(INote entita) {
		final CacheNote cache = CacheNote.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapNote();
		this.entita = (Note) ((WrapNote) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Eliminata Nota " + entita.getNome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Cancellata correttamente nota " + entita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata nota " + entita.getNome() + " precedentemente cancellata");
		}
	}

}
