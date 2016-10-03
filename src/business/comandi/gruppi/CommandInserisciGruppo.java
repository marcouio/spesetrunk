package business.comandi.gruppi;

import grafica.componenti.alert.Alert;

import business.cache.CacheGruppi;

import command.javabeancommand.AbstractCommandForJavaBean;
import db.dao.IDAO;
import domain.Gruppi;
import domain.IGruppi;
import domain.wrapper.WrapGruppi;

public class CommandInserisciGruppo extends AbstractCommandForJavaBean {

	public CommandInserisciGruppo(final IGruppi entita) throws Exception {
		final CacheGruppi cache = CacheGruppi.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapGruppi();
		this.entita = ((IDAO) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof Gruppi) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof Gruppi) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Inserito Gruppo " + ((Gruppi) entita).getNome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Inserito correttamente gruppo " + entita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinato gruppo " + entita.getNome() + " precedentemente cancellato");
		}
	}

}
