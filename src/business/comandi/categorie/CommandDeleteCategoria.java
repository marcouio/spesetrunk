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

public class CommandDeleteCategoria extends AbstractCommand implements ICommand {

	public CommandDeleteCategoria(ICatSpese entita) {
		CacheCategorie cache = CacheCategorie.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapCatSpese();
		this.entita = ((IWrapperEntity) entita).getentitaPadre();
	}

	@Override
	public boolean execute() {
		if (entita instanceof CatSpese) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (entita instanceof CatSpese) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Eliminata Categoria " + ((CatSpese) entita).getnome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Cancellata correttamente la categoria: " + entita.getnome());
		}
	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Ripristina la categoria: " + entita.getnome() + " precedentemente cancellata");
		}
	}
}
