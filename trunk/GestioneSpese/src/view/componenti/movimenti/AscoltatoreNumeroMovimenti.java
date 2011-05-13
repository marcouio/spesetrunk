package view.componenti.movimenti;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domain.Entrate;
import domain.SingleSpesa;
import domain.wrapper.Model;

public class AscoltatoreNumeroMovimenti implements ActionListener {

	private final String   tipo;
	private final String[] nomiColonne;
	JTextField             campo;

	public AscoltatoreNumeroMovimenti(String tipo, String[] nomiColonne, JTextField campo) {
		this.tipo = tipo;
		this.nomiColonne = nomiColonne;
		this.campo = campo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (tipo.equals(Entrate.NOME_TABELLA)) {
			try {
				Model.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Inserire un valore numerico: " + e1.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
			}
		} else if (tipo.equals(SingleSpesa.NOME_TABELLA)) {
			try {
				Model.aggiornaMovimentiUsciteDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Inserire un valore numerico: " + e1.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
			}
		}
	}
}
