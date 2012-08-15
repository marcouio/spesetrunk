package business.comandi.categorie;

import grafica.componenti.alert.Alert;

import java.util.HashMap;

import business.cache.CacheCategorie;

import command.ICommand;
import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;

import domain.CatSpese;
import domain.ICatSpese;
import domain.wrapper.WrapCatSpese;

public class CommandUpdateCategoria extends AbstractCommandForJavaBean implements ICommand {

	final private CatSpese newEntita;
	final private CatSpese oldEntita;
	final private WrapCatSpese wrap;
	private final HashMap<String, AbstractOggettoEntita> mappaCache;

	public CommandUpdateCategoria(final CatSpese oldEntita, final ICatSpese newEntita) {
		this.newEntita = (CatSpese) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapCatSpese();
		final CacheCategorie cache = CacheCategorie.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}

	@Override
	public boolean execute() {
		if (newEntita instanceof CatSpese) {
			if (wrap.update(newEntita)) {
				mappaCache.put(Integer.toString(newEntita.getidCategoria()), newEntita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (oldEntita instanceof CatSpese) {
			if (wrap.update(oldEntita)) {
				mappaCache.put(Integer.toString(oldEntita.getidCategoria()), oldEntita);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Categoria " + (newEntita).getnome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornata correttamente categoria " + newEntita.getnome());
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata categoria " + oldEntita.getnome() + " precedentemente aggiornata");
		}
	}
}
