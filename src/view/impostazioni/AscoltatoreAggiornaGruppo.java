package view.impostazioni;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import view.Alert;
import business.Controllore;
import business.Database;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import business.comandi.gruppi.CommandUpdateGruppo;
import domain.Gruppi;
import domain.IGruppi;
import domain.wrapper.WrapGruppi;

public class AscoltatoreAggiornaGruppo implements ActionListener {

	private GruppiView gruppiView;

	public AscoltatoreAggiornaGruppo(final GruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		final Gruppi gruppi = (Gruppi) gruppiView.getComboGruppi().getSelectedItem();
		final WrapGruppi modelGruppi = gruppiView.getModelGruppi();

		if (gruppi != null) {
			final Gruppi oldGruppo = CacheGruppi.getSingleton().getGruppo(Integer.toString(gruppi.getidGruppo()));
			gruppiView.setGruppo("Aggiorna");
			if (gruppi != null) {
				modelGruppi.setidGruppo(gruppi.getidGruppo());
			}
			try {
				if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandUpdateGruppo(oldGruppo, (IGruppi) modelGruppi.getentitaPadre()), "tutto")) {

					final Vector<Gruppi> vectorGruppi = CacheGruppi.getSingleton().getVettoreCategoriePerCombo(CacheGruppi.getSingleton().getAllGruppi());
					final DefaultComboBoxModel model = new DefaultComboBoxModel(vectorGruppi);
					gruppiView.getComboGruppi().setModel(model);

					Database.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
					Alert.operazioniSegnalazioneInfo("Aggiornato correttamente gruppo " + modelGruppi.getnome());
					modelGruppi.setChanged();
					modelGruppi.notifyObservers();
					gruppiView.dispose();
				}
			} catch (final Exception e22) {
				e22.printStackTrace();
				Alert.operazioniSegnalazioneErroreGrave("Inserisci i dati correttamente: " + e22.getMessage());
			}
		} else {
			Alert.operazioniSegnalazioneErroreGrave("Impossibile aggiornare un gruppo inesistente!");
		}
	}

}