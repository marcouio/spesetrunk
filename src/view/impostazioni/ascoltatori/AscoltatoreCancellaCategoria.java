package view.impostazioni.ascoltatori;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import view.impostazioni.CategorieView;
import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.comandi.categorie.CommandDeleteCategoria;

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
