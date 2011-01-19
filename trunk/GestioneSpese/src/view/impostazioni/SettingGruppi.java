package view.impostazioni;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import view.OggettoVistaBase;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextFieldF;
import view.font.TextAreaF;
import java.awt.Color;
import view.font.ButtonF;
import javax.swing.JLabel;

public class SettingGruppi extends OggettoVistaBase {
	public SettingGruppi() {
		setLayout(null);
		
		LabelTesto lbltstGruppo = new LabelTesto();
		lbltstGruppo.setText("Gruppo");
		lbltstGruppo.setBounds(29, 85, 100, 25);
		add(lbltstGruppo);
		
		TextFieldF textFieldF = new TextFieldF();
		textFieldF.setBounds(29, 110, 178, 26);
		add(textFieldF);
		
		LabelTesto labelTesto_1 = new LabelTesto();
		labelTesto_1.setText("Descrizione");
		labelTesto_1.setBounds(29, 138, 90, 25);
		add(labelTesto_1);
		
		TextAreaF textAreaF = new TextAreaF("Inserisci la descrizione della spesa", 50, 25);
		textAreaF.setWrapStyleWord(true);
		textAreaF.setLineWrap(true);
		textAreaF.setBackground(Color.LIGHT_GRAY);
		textAreaF.setAutoscrolls(true);
		textAreaF.setBounds(29, 164, 206, 88);
		add(textAreaF);
		
		ButtonF btnfInserisciGruppo = new ButtonF();
		btnfInserisciGruppo.setText("Inserisci Gruppo");
		btnfInserisciGruppo.setBounds(30, 275, 206, 25);
		add(btnfInserisciGruppo);
		
		LabelTitolo label = new LabelTitolo("GRUPPI");
		label.setBounds(82, 58, 70, 15);
		add(label);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new SettingGruppi());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
