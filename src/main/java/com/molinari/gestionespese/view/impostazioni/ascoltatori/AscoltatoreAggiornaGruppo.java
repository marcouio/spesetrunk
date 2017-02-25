package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import grafica.componenti.alert.Alert;

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
import com.molinari.gestionespese.view.impostazioni.GruppiView;

public class AscoltatoreAggiornaGruppo extends AscoltatoreAggiornatoreTutto {

	private GruppiView gruppiView;

	public AscoltatoreAggiornaGruppo(final GruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);

		final Gruppi gruppi = (Gruppi) gruppiView.getComboGruppi().getSelectedItem();
		final WrapGruppi modelGruppi = gruppiView.getModelGruppi();

		if (gruppi != null) {
			final Gruppi oldGruppo = CacheGruppi.getSingleton().getGruppo(Integer.toString(gruppi.getidGruppo()));
			gruppiView.setGruppo("Aggiorna");
			if (gruppi != null) {
				modelGruppi.setidGruppo(gruppi.getidGruppo());
			}
			try {
				if (Controllore.invocaComando(new CommandUpdateGruppo(oldGruppo, (IGruppi) modelGruppi.getEntitaPadre()))) {

					final List<Gruppi> vectorGruppi = CacheGruppi.getSingleton().getListCategoriePerCombo(CacheGruppi.getSingleton().getAllGruppi());
					final DefaultComboBoxModel<Gruppi> model = new DefaultComboBoxModel<Gruppi>(new Vector<Gruppi>(vectorGruppi));
					gruppiView.getComboGruppi().setModel(model);
					AggiornatoreManager.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
					modelGruppi.setChanged();
					modelGruppi.notifyObservers();
					gruppiView.dispose();
				}
			} catch (final Exception e22) {
				e22.printStackTrace();
				Alert.segnalazioneErroreGrave("Inserisci i dati correttamente: " + e22.getMessage());
			}
		} else {
			Alert.segnalazioneErroreGrave("Impossibile aggiornare un gruppo inesistente!");
		}
	}

}
