package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.business.comandi.gruppi.CommandUpdateGruppo;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;
import com.molinari.gestionespese.view.impostazioni.AbstractGruppiView;
import com.molinari.utility.graphic.component.alert.Alert;

public class AscoltatoreAggiornaGruppo extends AscoltatoreAggiornatoreTutto {

	private final AbstractGruppiView gruppiView;

	public AscoltatoreAggiornaGruppo(final AbstractGruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);

		final Gruppi gruppi = (Gruppi) gruppiView.getComboGruppi().getSelectedItem();
		final WrapGruppi modelGruppi = gruppiView.getModelGruppi();

		if (gruppi != null) {
			final IGruppi oldGruppo = CacheGruppi.getSingleton().getGruppo(Integer.toString(gruppi.getidGruppo()));
			gruppiView.setGruppo("Aggiorna");
			
			modelGruppi.setidGruppo(gruppi.getidGruppo());
			
			try {
				if (Controllore.invocaComando(new CommandUpdateGruppo(oldGruppo, modelGruppi.getEntitaPadre()))) {

					final List<IGruppi> vectorGruppi = CacheGruppi.getSingleton().getListCategoriePerCombo(CacheGruppi.getSingleton().getAllGruppi());
					final DefaultComboBoxModel<IGruppi> model = new DefaultComboBoxModel<>(new Vector<>(vectorGruppi));
					gruppiView.getComboGruppi().setModel(model);
					AggiornatoreManager.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
					modelGruppi.setChanged();
					modelGruppi.notifyObservers();
					gruppiView.getDialog().dispose();
				}
			} catch (final Exception e22) {
				Alert.segnalazioneEccezione(e22, "Inserisci i dati correttamente: " + e22.getMessage());
			}
		} else {
			Alert.segnalazioneErroreGrave("Impossibile aggiornare un gruppo inesistente!");
		}
	}

}
