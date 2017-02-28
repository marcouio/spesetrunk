package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.comandi.categorie.CommandDeleteCategoria;
import com.molinari.gestionespese.view.impostazioni.CategorieView;

import grafica.componenti.alert.Alert;

public class AscoltatoreCancellaCategoria extends AscoltatoreAggiornatoreTutto {

	CategorieView categorieView;

	public AscoltatoreCancellaCategoria(final CategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	protected void actionPerformedOverride(ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);
		if (categorieView.getComboCategorie().getSelectedItem() != null && categorieView.getCategoria() != null) {
			categorieView.aggiornaModelDaVista("Cancella");
			if (Controllore.invocaComando(new CommandDeleteCategoria(categorieView.getModelCatSpese()))) {
				categorieView.getComboCategorie().removeItem(categorieView.getCategoria());
				categorieView.dispose();
			}
		} else {
			Alert.segnalazioneErroreGrave("Impossibile cancellare una categoria inesistente!");
		}
	}
}
