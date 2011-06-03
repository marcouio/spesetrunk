package view.impostazioni;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Alert;
import business.Controllore;
import business.cache.CacheCategorie;
import business.comandi.categorie.CommandInserisciCategoria;
import domain.CatSpese;

public class AscoltatoreInserisciCategoria implements ActionListener {

	private CategorieView categorieView;
	private CatSpese categoria1;

	public AscoltatoreInserisciCategoria(final CategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		categorieView.setCategoria("Inserisci");
		if (categorieView.nonEsistonoCampiNonValorizzati()) {

			if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandInserisciCategoria(categorieView.getModelCatSpese()), "tutto")) {
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
