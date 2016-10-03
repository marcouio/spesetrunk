package business.comandi.categorie;

import grafica.componenti.alert.Alert;

import business.cache.CacheCategorie;

import command.ICommand;
import command.javabeancommand.AbstractCommandForJavaBean;
import db.dao.IDAO;
import domain.CatSpese;
import domain.ICatSpese;
import domain.wrapper.WrapCatSpese;

public class CommandDeleteCategoria extends AbstractCommandForJavaBean implements ICommand {

	public CommandDeleteCategoria(ICatSpese entita) throws Exception {
		CacheCategorie cache = CacheCategorie.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapCatSpese();
		this.entita = ((IDAO) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof CatSpese) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
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
		return "Eliminata Categoria " + ((CatSpese) entita).getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Cancellata correttamente la categoria: " + entita.getNome());
		}
	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristina la categoria: " + entita.getNome() + " precedentemente cancellata");
		}
	}
}
