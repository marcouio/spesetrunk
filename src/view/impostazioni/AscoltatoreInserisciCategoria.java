package view.impostazioni;

import java.awt.event.ActionEvent;

import view.Alert;
import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.cache.CacheCategorie;
import business.comandi.categorie.CommandInserisciCategoria;
import domain.CatSpese;

public class AscoltatoreInserisciCategoria extends AscoltatoreAggiornatoreTutto {

	private CategorieView categorieView;
	private CatSpese      categoria1;

	public AscoltatoreInserisciCategoria(final CategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	protected void actionPerformedOverride(ActionEvent e) {
		super.actionPerformedOverride(e);
		categorieView.setCategoria("Inserisci");
		if (categorieView.nonEsistonoCampiNonValorizzati()) {

			if (Controllore.invocaComando(new CommandInserisciCategoria(categorieView.getModelCatSpese()))) {
				categoria1 = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categorieView.getModelCatSpese().getidCategoria()));
				if (categoria1 != null) {
					categorieView.getComboCategorie().addItem(categoria1);
				}
				Alert.operazioniSegnalazioneInfo("Inserita correttamente categoria: " + categorieView.getModelCatSpese().getnome());
				categorieView.getModelCatSpese().setChanged();
				categorieView.getModelCatSpese().notifyObservers();
				categorieView.dispose();
			}
		} else {
			Alert.operazioniSegnalazioneErroreGrave("E' necessario riempire tutti i campi");
		}
	}
}