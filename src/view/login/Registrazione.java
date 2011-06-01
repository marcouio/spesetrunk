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
import business.cache.CacheUtenti;
import domain.Utenti;
import domain.wrapper.WrapUtenti;

public class Registrazione extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextFieldF username;
	private TextFieldF password;
	
	public Registrazione() {
		this.setBounds(400, 300, 385, 300);
		getContentPane().setLayout(null);
		LabelListaGruppi lblUsername = new LabelListaGruppi("Username");
		lblUsername.setBounds(58, 125, 88, 25);
		getContentPane().add(lblUsername);
		
		LabelListaGruppi lblPassword = new LabelListaGruppi("Password");
		lblPassword.setBounds(228, 125, 88, 25);
		getContentPane().add(lblPassword);
		
		username = new TextFieldF();
		username.setBounds(58, 152, 100, 25);
		getContentPane().add(username);
		username.setColumns(10);
		
		password = new TextFieldF();
		password.setBounds(228, 151, 100, 25);
		password.setColumns(10);
		getContentPane().add(password);
		
		LabelTitolo lblLogin = new LabelTitolo("LOGIN");
		lblLogin.setBounds(138, 13, 115, 32);
		lblLogin.setText("REGISTRATI");
		getContentPane().add(lblLogin);
		
		ButtonF btnEntra = new ButtonF("Entra");
		btnEntra.setBounds(138, 203, 102, 23);
		btnEntra.setText("Registrati");
		getContentPane().add(btnEntra);
		
		final TextFieldF cognome = new TextFieldF();
		cognome.setBounds(228, 82, 100, 25);
		cognome.setColumns(10);
		getContentPane().add(cognome);
		
		LabelListaGruppi lbltstCognome = new LabelListaGruppi("Password");
		lbltstCognome.setBounds(228, 56, 88, 25);
		lbltstCognome.setText("Cognome");
		getContentPane().add(lbltstCognome);
		
		final TextFieldF nome = new TextFieldF();
		nome.setBounds(58, 82, 100, 25);
		nome.setColumns(10);
		getContentPane().add(nome);
		
		LabelListaGruppi lbltstNome = new LabelListaGruppi("Username");
		lbltstNome.setBounds(58, 55, 88, 25);
		lbltstNome.setText("Nome");
		getContentPane().add(lbltstNome);
		
		btnEntra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String sNome = nome.getText();
				String sCognome = cognome.getText();
				String sPass = password.getText();
				String sUser = username.getText();
				WrapUtenti utentiwrap = new WrapUtenti();
				if(!sNome.equals("") && !sCognome.equals("") && !sPass.equals("") && !sUser.equals("")){
					Utenti utente = new Utenti();
					utente.setNome(sNome);
					utente.setCognome(sCognome);
					utente.setpassword(sPass);
					utente.setusername(sUser);
					boolean ok = CacheUtenti.getSingleton().checkUtentePerUsername(sUser);
					if(ok==false){
						utentiwrap.insert(utente);
						dispose();
					}else{
						JOptionPane.showMessageDialog(null, "Username già presente, sceglierne un altro", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
				}else{
					JOptionPane.showMessageDialog(null, "Tutti i campi devono essere valorizzati", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				}
				
			}
		});
	}
}
