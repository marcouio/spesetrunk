package view.entrateuscite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import business.Controllore;
import business.comandi.singlespese.CommandInserisciSpesa;
import domain.SingleSpesa;

public class AscoltaInserisciUscite implements ActionListener {

	private UsciteView view;

	public AscoltaInserisciUscite(final UsciteView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		view.setUscite();
		if (view.nonEsistonoCampiNonValorizzati()) {
			if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandInserisciSpesa(view.getModelUscita()), SingleSpesa.NOME_TABELLA)) {
				JOptionPane.showMessageDialog(null, "Ok, uscita inserita correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
				// TODO log.fine("Uscita inserita, nome: "
				// + modelUscita.getnome() + ", id: " +
				// modelUscita.getidSpesa());
			}
		} else {
			JOptionPane.showMessageDialog(null, "E' necessario riempire tutti i campi", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
			// TODO
			// log.severe("Uscita non inserita: e' necessario riempire tutti i campi");
		}

	}

}
