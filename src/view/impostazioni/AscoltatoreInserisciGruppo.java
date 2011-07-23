package view.impostazioni;

import java.awt.event.ActionEvent;

import view.Alert;
import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.cache.CacheGruppi;
import business.comandi.gruppi.CommandInserisciGruppo;
import domain.Gruppi;
import domain.wrapper.WrapGruppi;

public class AscoltatoreInserisciGruppo extends AscoltatoreAggiornatoreTutto {

	private GruppiView gruppiView;

	public AscoltatoreInserisciGruppo(final GruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	private Gruppi gruppo1;

	@Override
	protected void actionPerformedOverride(ActionEvent e) {
		super.actionPerformedOverride(e);

		gruppiView.setGruppo("Inserisci");
		final WrapGruppi modelGruppi = gruppiView.getModelGruppi();

		if (gruppiView.nonEsistonoCampiNonValorizzati()) {

			if (Controllore.invocaComando(new CommandInserisciGruppo(modelGruppi))) {
				gruppo1 = CacheGruppi.getSingleton().getGruppo(Integer.toString(modelGruppi.getidGruppo()));
				if (gruppo1 != null) {
					gruppiView.getComboGruppi().addItem(gruppo1);
				}
				final String messaggio = "Gruppo inserito correttamente";
				Alert.operazioniSegnalazioneInfo(messaggio);

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
