package view.impostazioni;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Alert;
import business.Controllore;
import business.Database;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import business.comandi.categorie.CommandUpdateCategoria;
import domain.CatSpese;
import domain.Gruppi;
import domain.ICatSpese;

public class AscoltatoreAggiornaCategoria implements ActionListener {

	CategorieView categorieView;

	public AscoltatoreAggiornaCategoria(final CategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

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
				if (Controllore.getSingleton().getCommandManager()
						.invocaComando(new CommandUpdateCategoria(oldCategoria, (ICatSpese) categorieView.getModelCatSpese().getentitaPadre()), "tutto")) {
					Database.aggiornaCategorie((CatSpese) categorieView.getModelCatSpese().getentitaPadre(), categorieView.getComboCategorie());
					Alert.operazioniSegnalazioneInfo("Aggiornata correttamente categoria: " + categorieView.getModelCatSpese().getnome());
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
