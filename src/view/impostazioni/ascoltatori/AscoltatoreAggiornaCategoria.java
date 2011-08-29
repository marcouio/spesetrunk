package view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import view.Alert;
import view.impostazioni.CategorieView;
import business.Controllore;
import business.aggiornatori.AggiornatoreManager;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import business.comandi.categorie.CommandUpdateCategoria;
import domain.CatSpese;
import domain.Gruppi;
import domain.ICatSpese;

public class AscoltatoreAggiornaCategoria extends AscoltatoreAggiornatoreTutto {

	CategorieView categorieView;

	public AscoltatoreAggiornaCategoria(final CategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);
		final CatSpese oldCategoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categorieView.getCategoria().getidCategoria()));

		if (categorieView.getComboCategorie().getSelectedItem() != null) {
			categorieView.setCategoria("Aggiorna");
			if (categorieView.getCategoria() != null) {
				categorieView.getModelCatSpese().setidCategoria(categorieView.getCategoria().getidCategoria());
			}

			if (categorieView.getGruppo() == null) {
				final Gruppi gruppo = CacheGruppi.getSingleton().getGruppoPerNome("No Gruppo");
				categorieView.getModelCatSpese().setGruppi(gruppo);
			}
			try {
				if (Controllore.invocaComando(new CommandUpdateCategoria(oldCategoria, (ICatSpese) categorieView.getModelCatSpese().getentitaPadre()))) {
					AggiornatoreManager.aggiornaCategorie((CatSpese) categorieView.getModelCatSpese().getentitaPadre(), categorieView.getComboCategorie());
					categorieView.getModelCatSpese().setChanged();
					categorieView.getModelCatSpese().notifyObservers();
					categorieView.dispose();
				}
			} catch (final Exception e22) {
				e22.printStackTrace();
				Alert.operazioniSegnalazioneErroreGrave("Inserisci i dati correttamente: " + e22.getMessage());
			}
		} else {
			Alert.operazioniSegnalazioneErroreGrave("Impossibile aggiornare una categoria inesistente!");
		}
	}

}
