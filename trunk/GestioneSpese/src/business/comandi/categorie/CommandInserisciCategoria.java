package business.comandi.categorie;

import grafica.componenti.alert.Alert;

import java.util.HashMap;

import business.cache.CacheCategorie;

import command.ICommand;
import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;

import db.dao.IDAO;
import domain.CatSpese;
import domain.ICatSpese;
import domain.wrapper.WrapCatSpese;

public class CommandInserisciCategoria extends AbstractCommandForJavaBean implements ICommand {

	final private AbstractOggettoEntita entita;
	private HashMap<String, AbstractOggettoEntita> mappaCache;

	public CommandInserisciCategoria(final ICatSpese entita) throws Exception {
		final CacheCategorie cache = CacheCategorie.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapCatSpese();
		this.entita = ((IDAO) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof CatSpese) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof CatSpese) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Inserita Categoria " + ((CatSpese) entita).getnome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Categoria " + entita.getnome() + " inserita correttamente");
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Eliminata categoria " + entita.getnome() + " precedentemente inserita");
		}
	}

}
