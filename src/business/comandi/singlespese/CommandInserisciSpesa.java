package business.comandi.singlespese;

import grafica.componenti.alert.Alert;

import java.util.HashMap;

import business.cache.CacheUscite;

import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;

import db.dao.IDAO;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.wrapper.WrapSingleSpesa;

public class CommandInserisciSpesa extends AbstractCommandForJavaBean {

	final private AbstractOggettoEntita            entita;
	private HashMap<String, AbstractOggettoEntita> mappaCache;

	public CommandInserisciSpesa(ISingleSpesa entita) throws Exception {
		CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapSingleSpesa();
		this.entita = ((IDAO) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof SingleSpesa) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof SingleSpesa) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
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
