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

public class CommandDeleteSpesa extends AbstractCommandForJavaBean {

	public CommandDeleteSpesa(ISingleSpesa entita) throws Exception {
		CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapSingleSpesa();
		this.entita = ((IDAO) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws NumberFormatException, Exception {
		if (entita instanceof SingleSpesa) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof SingleSpesa) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
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
