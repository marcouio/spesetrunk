package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.comandi.categorie.CommandDeleteCategoria;
import com.molinari.gestionespese.view.impostazioni.AbstractCategorieView;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.messages.I18NManager;

public class AscoltatoreCancellaCategoria extends AscoltatoreAggiornatoreTutto {

	AbstractCategorieView categorieView;

	public AscoltatoreCancellaCategoria(final AbstractCategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	protected void actionPerformedOverride(ActionEvent e) {
		super.actionPerformedOverride(e);
		if (categorieView.getComboCategorie().getSelectedItem() != null && categorieView.getCategoria() != null) {
			categorieView.aggiornaModelDaVista("Cancella");
			if (Controllore.invocaComando(new CommandDeleteCategoria(categorieView.getModelCatSpese()))) {
				categorieView.getComboCategorie().removeItem(categorieView.getCategoria());
				categorieView.getDialog().dispose();
				return;
			}
		} 
			
		Alert.segnalazioneErroreGrave(I18NManager.getSingleton().getMessaggio("anycatsel"));
		
	}
}
