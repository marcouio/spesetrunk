package view.impostazioni;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Alert;
import business.Controllore;
import business.cache.CacheGruppi;
import business.comandi.gruppi.CommandInserisciGruppo;
import domain.Gruppi;
import domain.wrapper.WrapGruppi;

public class AscoltatoreInserisciGruppo implements ActionListener {

	private GruppiView gruppiView;

	public AscoltatoreInserisciGruppo(final GruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	private Gruppi gruppo1;

	@Override
	public void actionPerformed(final ActionEvent e) {

		gruppiView.setGruppo("Inserisci");
		final WrapGruppi modelGruppi = gruppiView.getModelGruppi();

		if (gruppiView.nonEsistonoCampiNonValorizzati()) {

			if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandInserisciGruppo(modelGruppi), "tutto")) {
				gruppo1 = CacheGruppi.getSingleton().getGruppo(Integer.toString(modelGruppi.getidGruppo()));
				if (gruppo1 != null) {
					gruppiView.getComboGruppi().addItem(gruppo1);
				}
				final String messaggio = "Gruppo inserito correttamente";
				Alert.info(messaggio, Alert.TITLE_OK);
				Controllore.getLog().info(messaggio);
				modelGruppi.setChanged();
				modelGruppi.notifyObservers();
				gruppiView.dispose();
			}
		} else {
			final String messaggio = "E' necessario riempire tutti i campi";
			Alert.operazioniSegnalazioneErroreGrave(messaggio);
		}
	}

}
