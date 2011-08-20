package view.componenti.movimenti;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;

import business.ascoltatori.AscoltatoreAggiornatoreNiente;

public class DialogHandler extends AscoltatoreAggiornatoreNiente {
	JDialog dia;

	public DialogHandler(final JDialog dialog) {
		dia = dialog;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);
		dia.dispose();
	}

}