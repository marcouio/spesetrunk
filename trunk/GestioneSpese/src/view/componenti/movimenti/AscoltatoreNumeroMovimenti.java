package view.componenti.movimenti;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import view.Alert;
import business.aggiornatori.AggiornatoreManager;
import business.internazionalizzazione.I18NManager;
import domain.Entrate;
import domain.SingleSpesa;

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
				Alert.operazioniSegnalazioneErroreGrave(I18NManager.getSingleton().getMessaggio("insertnumber")+": "+e1.getMessage());
			}
		} else if (tipo.equals(SingleSpesa.NOME_TABELLA)) {
			try {
				AggiornatoreManager.aggiornaMovimentiUsciteDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
			} catch (final Exception e1) {
				Alert.operazioniSegnalazioneErroreGrave(I18NManager.getSingleton().getMessaggio("insertnumber")+": "+e1.getMessage());
			}
		}
	}
}