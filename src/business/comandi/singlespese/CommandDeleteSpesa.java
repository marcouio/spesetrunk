package business.comandi.singlespese;

import grafica.componenti.alert.Alert;

import business.cache.CacheUscite;

import command.javabeancommand.AbstractCommandForJavaBean;
import db.dao.IDAO;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.wrapper.WrapSingleSpesa;

public class CommandDeleteSpesa extends AbstractCommandForJavaBean<SingleSpesa> {

	public CommandDeleteSpesa(ISingleSpesa entita) {
		CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapSingleSpesa();
		this.entita = (SingleSpesa) ((IDAO) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof SingleSpesa && wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof SingleSpesa && wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Eliminata Spesa " + ((SingleSpesa) entita).getnome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Cancellata correttamente spesa " + entita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata spesa " + entita.getNome() + " precedentemente cancellata");
		}
	}

}
