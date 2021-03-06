package com.molinari.gestionespese.view.componenti.movimenti;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JTextField;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.messages.I18NManager;

public class AscoltatoreNumeroMovimenti implements ActionListener {

	private final String tipo;
	private final String[] nomiColonne;
	JTextField campo;

	public AscoltatoreNumeroMovimenti(final String tipo, final String[] nomiColonne, final JTextField campo) {
		this.tipo = tipo;
		this.nomiColonne = nomiColonne;
		this.campo = campo;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (tipo.equals(Entrate.NOME_TABELLA)) {
			try {
				AggiornatoreManager.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
			} catch (final Exception e1) {
				ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
				Alert.segnalazioneErroreGrave(I18NManager.getSingleton().getMessaggio("insertnumber")+": "+e1.getMessage());
			}
		} else if (tipo.equals(SingleSpesa.NOME_TABELLA)) {
			try {
				AggiornatoreManager.aggiornaMovimentiUsciteDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
			} catch (final Exception e1) {
				ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
				Alert.segnalazioneErroreGrave(I18NManager.getSingleton().getMessaggio("insertnumber")+": "+e1.getMessage());
			}
		}
	}
}
