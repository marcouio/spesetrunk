package view.impostazioni;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Alert;
import business.Controllore;
import business.comandi.categorie.CommandDeleteCategoria;

public class AscoltatoreCancellaCategoria implements ActionListener {

	CategorieView categorieView;

	public AscoltatoreCancellaCategoria(final CategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (categorieView.getComboCategorie().getSelectedItem() != null && categorieView.getCategoria() != null) {
			categorieView.setCategoria("Cancella");
			if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteCategoria(categorieView.getModelCatSpese()), "tutto")) {
				Alert.operazioniSegnalazioneInfo("Cancellata la categoria: " + categorieView.getCategoria());
				categorieView.getComboCategorie().removeItem(categorieView.getCategoria());
				categorieView.dispose();
			}
		} else {
			Alert.operazioniSegnalazioneErroreGrave("Impossibile cancellare una categoria inesistente!");
		}
	}
}
