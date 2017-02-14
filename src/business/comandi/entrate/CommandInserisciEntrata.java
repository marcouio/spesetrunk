package business.comandi.entrate;

import business.cache.CacheEntrate;
import command.ICommand;
import command.javabeancommand.AbstractCommandForJavaBean;
import db.dao.IDAO;
import domain.Entrate;
import domain.IEntrate;
import domain.wrapper.WrapEntrate;
import grafica.componenti.alert.Alert;

public class CommandInserisciEntrata extends AbstractCommandForJavaBean<Entrate> implements ICommand {

	public CommandInserisciEntrata(final IEntrate entita) throws Exception {
		final CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapEntrate();
		this.entita = (Entrate) ((IDAO) entita).getEntitaPadre();

	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof Entrate && wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof Entrate && wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Inserita Entrata " + ((Entrate) entita).getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Entrata " + entita.getNome() + " inserita correttamente");
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Eliminata entrata " + entita.getNome() + " inserita precedentemente");
		}
	}

}
