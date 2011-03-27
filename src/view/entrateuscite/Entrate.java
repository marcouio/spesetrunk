package view.entrateuscite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JSeparator;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextAreaF;
import view.font.TextFieldF;
import javax.swing.JComboBox;

public class Entrate extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Entrate singleton;
	private TextFieldF textField;
	private JComboBox textField_1;
	private TextFieldF textField_2;
	private TextFieldF textField_3;

	
	public static final Entrate getSingleton() {
		if (singleton == null) {
			synchronized (Entrate.class) {
				if (singleton == null) {
					singleton = new Entrate();
				}
			} // if
		} // if
		return singleton;
	} // getSingleton()
	
	/**
	 * Create the panel.
	 */
	private Entrate() {
		setLayout(null);
		
		LabelTesto lblNomeSpesa = new LabelTesto("Nome Spesa");
		lblNomeSpesa.setText("Nome Entrata");
		lblNomeSpesa.setBounds(42, 71, 97, 14);
		add(lblNomeSpesa);
		
		LabelTesto lblEuro = new LabelTesto("Euro");
		lblEuro.setBounds(473, 71, 77, 14);
		add(lblEuro);
		
		LabelTesto lblCategorie = new LabelTesto("Categorie");
		lblCategorie.setText("Tipo");
		lblCategorie.setBounds(178, 71, 77, 14);
		add(lblCategorie);
		
		LabelTesto lblData = new LabelTesto("Data");
		lblData.setBounds(340, 71, 77, 14);
		add(lblData);
		
		LabelTesto lblDescrizione = new LabelTesto("Descrizione Spesa");
		lblDescrizione.setText("Descrizione Entrata");
		lblDescrizione.setBounds(43, 146, 123, 14);
		add(lblDescrizione);
		
		TextAreaF textArea = new TextAreaF();
		textArea.setBounds(43, 167, 260, 57);
		add(textArea);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(41, 278, 557, 11);
		add(separator);
		
		textField = new TextFieldF();
		textField.setBounds(41, 90, 125, 22);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JComboBox();
		textField_1.setBounds(176, 90, 125, 22);
		add(textField_1);
		
		textField_2 = new TextFieldF();
		textField_2.setColumns(10);
		textField_2.setBounds(338, 90, 125, 22);
		add(textField_2);
		
		textField_3 = new TextFieldF();
		textField_3.setColumns(10);
		textField_3.setBounds(471, 90, 125, 22);
		add(textField_3);
		
		LabelTitolo lblPannelloUscite = new LabelTitolo("Pannello Uscite");
		lblPannelloUscite.setText("Pannello Entrate");
		lblPannelloUscite.setBounds(42, 24, 136, 36);
		add(lblPannelloUscite);
		
		ButtonF button = new ButtonF("New button");
		button.setText("Inserisci");
		button.setBounds(627, 90, 126, 36);
		add(button);
		
		ButtonF buttonF = new ButtonF("New button");
		buttonF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		buttonF.setText("Elimina Ultima");
		buttonF.setBounds(627, 134, 126, 36);
		add(buttonF);
		
		TextAreaF textAreaF = new TextAreaF();
		textAreaF.setBounds(336, 167, 260, 57);
		add(textAreaF);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

	}
}
