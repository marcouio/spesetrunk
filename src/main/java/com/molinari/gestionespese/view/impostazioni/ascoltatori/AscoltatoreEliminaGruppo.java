package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.JComboBox;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.comandi.gruppi.CommandDeleteGruppo;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;
import com.molinari.gestionespese.view.impostazioni.AbstractGruppiView;
import com.molinari.gestionespese.view.impostazioni.CategorieView;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.messages.I18NManager;

public class AscoltatoreEliminaGruppo extends AscoltatoreAggiornatoreTutto {

	private final AbstractGruppiView gruppiView;

	public AscoltatoreEliminaGruppo(final AbstractGruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);
		final JComboBox<IGruppi> comboGruppi = gruppiView.getComboGruppi();
		final Gruppi gruppi = (Gruppi) comboGruppi.getSelectedItem();

		if (comboGruppi.getSelectedIndex() != 0 && gruppi != null) {
			
			if(existCatWithSelectedGroup(gruppiView.getModelGruppi().getidGruppo())) {
				Alert.segnalazioneErroreGrave(I18NManager.getSingleton().getMessaggio("groupused"));
				return;
			}
			
			gruppiView.setGruppo("Cancella");
			
			if (Controllore.invocaComando(new CommandDeleteGruppo(gruppiView.getModelGruppi()))) {
				comboGruppi.removeItem(gruppi);
				gruppiView.getDialog().dispose();
				AggiornatoreManager.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
				return;
			}
		}
		
		Alert.segnalazioneErroreGrave(I18NManager.getSingleton().getMessaggio("selectgroup"));
		
	}

	private boolean existCatWithSelectedGroup(int idGroup) {
		Collection<ICatSpese> values = CacheCategorie.getSingleton().getAllCategorie().values();
		return values.stream().anyMatch(cat ->
								cat.getGruppi() != null && cat.getGruppi().getidGruppo() == idGroup
								);
		
		
	}
}
