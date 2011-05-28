package view.entrateuscite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import business.Controllore;
import business.Database;
import business.comandi.CommandInserisciEntrata;
import domain.Entrate;

public class AscoltaInserisciEntrate implements ActionListener {

	private EntrateView view;

	public AscoltaInserisciEntrate(final EntrateView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		view.setEntrate();

		if (view.nonEsistonoCampiNonValorizzati()) {
			if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandInserisciEntrata(view.getModelEntrate()), Entrate.NOME_TABELLA)) {
				JOptionPane.showMessageDialog(null, "Ok, entrata inserita correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
				// TODO log.fine("Entrata inserita, nome: " +
				// modelEntrate.getnome() + ", id: " +
				// modelEntrate.getidEntrate());
				view.dispose();
				try {
					Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "E' necessario riempire tutti i campi", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
			// TODO
			// log.severe("Entrata non inserita: e' necessario riempire tutti i campi");
		}

	}

}
