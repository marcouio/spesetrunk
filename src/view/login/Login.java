package view.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import view.font.ButtonF;
import view.font.LabelListaGruppi;
import view.font.LabelTitolo;
import view.font.TextFieldF;
import view.impostazioni.Impostazioni;
import business.Controllore;
import business.Database;
import domain.Utenti;
import domain.wrapper.WrapUtenti;

public class Login extends JDialog {

	private static final long serialVersionUID = 1L;
	private final TextFieldF  user;
	private final TextFieldF  pass;

	public Login() {
		getContentPane().setLayout(null);
		this.setBounds(400, 300, 400, 220);
		this.setTitle("Login");
		LabelListaGruppi lblUsername = new LabelListaGruppi("Username");
		lblUsername.setBounds(83, 69, 88, 25);
		getContentPane().add(lblUsername);

		LabelListaGruppi lblPassword = new LabelListaGruppi("Password");
		lblPassword.setBounds(221, 68, 88, 25);
		getContentPane().add(lblPassword);

		user = new TextFieldF();
		user.setBounds(83, 94, 86, 25);
		getContentPane().add(user);
		user.setColumns(10);

		pass = new TextFieldF();
		pass.setColumns(10);
		pass.setBounds(221, 94, 86, 25);
		getContentPane().add(pass);

		LabelTitolo lblLogin = new LabelTitolo("LOGIN");
		lblLogin.setBounds(171, 25, 57, 32);
		getContentPane().add(lblLogin);

		ButtonF btnEntra = new ButtonF("Entra");
		btnEntra.setBounds(148, 148, 91, 23);
		getContentPane().add(btnEntra);

		btnEntra.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WrapUtenti utentiwrap = new WrapUtenti();
				Utenti utente = utentiwrap.selectByUserAndPass(user.getText(), pass.getText());
				if (utente != null) {
					Controllore.setUtenteLogin(utente);
					Impostazioni impostazioni = Impostazioni.getSingleton();
					impostazioni.getUtente().setText(utente.getusername());
					try {
						Database.aggiornamentoPerImpostazioni();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					impostazioni.repaint();
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Username o password non corretti", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				}

			}
		});

	}
}
