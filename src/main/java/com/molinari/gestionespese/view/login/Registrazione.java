package com.molinari.gestionespese.view.login;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;

import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.label.Label;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;

public class Registrazione extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final TextFieldBase username;
	private final JPasswordField password;

	public Registrazione() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		this.setBounds(400, 300, 385, 300);
		getContentPane().setLayout(null);
		final LabelTestoPiccolo lblUsername = new LabelTestoPiccolo("Username", getContentPane());
		lblUsername.setBounds(58, 125, 88, 25);
		getContentPane().add(lblUsername);

		final LabelTestoPiccolo lblPassword = new LabelTestoPiccolo("Password", getContentPane());
		lblPassword.setBounds(228, 125, 88, 25);
		getContentPane().add(lblPassword);

		username = new TextFieldBase(getContentPane());
		username.setBounds(58, 152, 100, 25);
		getContentPane().add(username);
		username.setColumns(10);

		password = new JPasswordField();
		password.setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
		password.setBackground(Color.GRAY);
		password.setForeground(Color.white);
		password.setBounds(228, 151, 100, 25);
		password.setColumns(10);
		getContentPane().add(password);

		final Label lblLogin = new Label("LOGIN", getContentPane());
		lblLogin.setBounds(138, 13, 115, 32);
		lblLogin.setText("REGISTRATI");
		getContentPane().add(lblLogin);

		final ButtonBase btnEntra = new ButtonBase("Entra", getContentPane());
		btnEntra.setBounds(138, 203, 102, 23);
		btnEntra.setText("Registrati");
		getContentPane().add(btnEntra);

		final TextFieldBase cognome = new TextFieldBase(getContentPane());
		cognome.setBounds(228, 82, 100, 25);
		cognome.setColumns(10);
		getContentPane().add(cognome);

		final LabelTestoPiccolo lbltstCognome = new LabelTestoPiccolo("Password", getContentPane());
		lbltstCognome.setBounds(228, 56, 88, 25);
		lbltstCognome.setText("Cognome");
		getContentPane().add(lbltstCognome);

		final TextFieldBase nome = new TextFieldBase(getContentPane());
		nome.setBounds(58, 82, 100, 25);
		nome.setColumns(10);
		getContentPane().add(nome);

		final LabelTestoPiccolo lbltstNome = new LabelTestoPiccolo("Username", getContentPane());
		lbltstNome.setBounds(58, 55, 88, 25);
		lbltstNome.setText("Nome");
		getContentPane().add(lbltstNome);

		btnEntra.addActionListener(new RegisterListener(nome, cognome, username, password){
		
		@Override
		protected void endOperation() {
			dispose();
		}});
	}
}
