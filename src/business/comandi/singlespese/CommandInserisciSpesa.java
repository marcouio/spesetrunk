package business.comandi.singlespese;

import business.cache.CacheUscite;
import command.javabeancommand.AbstractCommandForJavaBean;
import db.dao.IDAO;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.wrapper.WrapSingleSpesa;
import grafica.componenti.alert.Alert;

public class CommandInserisciSpesa extends AbstractCommandForJavaBean<SingleSpesa> {

	public CommandInserisciSpesa(ISingleSpesa entita) {
		CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapSingleSpesa();
		this.entita = (SingleSpesa) ((IDAO) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof SingleSpesa && wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof SingleSpesa && wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Inserita Spesa " + ((SingleSpesa) entita).getnome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Inserita correttamente spesa " + entita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Cancellata spesa " + entita.getNome() + " precedentemente inserita");
		}
	}

}
