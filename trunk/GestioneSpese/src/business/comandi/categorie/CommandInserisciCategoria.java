package business.comandi.categorie;

import java.util.HashMap;

import view.Alert;
import business.cache.CacheCategorie;
import business.comandi.AbstractCommand;
import business.comandi.ICommand;
import domain.AbstractOggettoEntita;
import domain.CatSpese;
import domain.ICatSpese;
import domain.wrapper.IWrapperEntity;
import domain.wrapper.WrapCatSpese;

public class CommandInserisciCategoria extends AbstractCommand implements ICommand {

	final private AbstractOggettoEntita entita;
	final private IWrapperEntity wrap;
	private HashMap<String, AbstractOggettoEntita> mappaCache;

	public CommandInserisciCategoria(final ICatSpese entita) {
		final CacheCategorie cache = CacheCategorie.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapCatSpese();
		this.entita = ((IWrapperEntity) entita).getentitaPadre();
	}

	@Override
	public boolean execute() {
		if (entita instanceof CatSpese) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
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
			Alert.operazioniSegnalazioneInfo("Categoria " + entita.getnome() + " inserita correttamente");
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Eliminata categoria " + entita.getnome() + " precedentemente inserita");
		}
	}

}
