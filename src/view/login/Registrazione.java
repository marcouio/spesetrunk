package view.login;

import grafica.componenti.alert.Alert;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

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
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		this.setBounds(400, 300, 385, 300);
		getContentPane().setLayout(null);
		final LabelListaGruppi lblUsername = new LabelListaGruppi("Username");
		lblUsername.setBounds(58, 125, 88, 25);
		getContentPane().add(lblUsername);

		final LabelListaGruppi lblPassword = new LabelListaGruppi("Password");
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

		final LabelTitolo lblLogin = new LabelTitolo("LOGIN");
		lblLogin.setBounds(138, 13, 115, 32);
		lblLogin.setText("REGISTRATI");
		getContentPane().add(lblLogin);

		final ButtonF btnEntra = new ButtonF("Entra");
		btnEntra.setBounds(138, 203, 102, 23);
		btnEntra.setText("Registrati");
		getContentPane().add(btnEntra);

		final TextFieldF cognome = new TextFieldF();
		cognome.setBounds(228, 82, 100, 25);
		cognome.setColumns(10);
		getContentPane().add(cognome);

		final LabelListaGruppi lbltstCognome = new LabelListaGruppi("Password");
		lbltstCognome.setBounds(228, 56, 88, 25);
		lbltstCognome.setText("Cognome");
		getContentPane().add(lbltstCognome);

		final TextFieldF nome = new TextFieldF();
		nome.setBounds(58, 82, 100, 25);
		nome.setColumns(10);
		getContentPane().add(nome);

		final LabelListaGruppi lbltstNome = new LabelListaGruppi("Username");
		lbltstNome.setBounds(58, 55, 88, 25);
		lbltstNome.setText("Nome");
		getContentPane().add(lbltstNome);

		btnEntra.addActionListener(e -> {

			final String sNome = nome.getText();
			final String sCognome = cognome.getText();
			final String sPass = password.getText();
			final String sUser = username.getText();
			final WrapUtenti utentiwrap = new WrapUtenti();
			if (!"".equals(sNome) && !"".equals(sCognome) && !"".equals(sPass) && !"".equals(sUser)) {
				final Utenti utente = new Utenti();
				utente.setNome(sNome);
				utente.setCognome(sCognome);
				utente.setpassword(sPass);
				utente.setusername(sUser);
				final boolean ok = CacheUtenti.getSingleton().checkUtentePerUsername(sUser);
				// TODO creare i comandi anche per gli utenti registrati:
				// cancella, inserisci, aggiorna.
				if (!ok) {
					utentiwrap.insert(utente);
					dispose();
				} else {
					Alert.segnalazioneErroreGrave("Username già presente, sceglierne un altro");
				}
			} else {
				Alert.segnalazioneErroreGrave("Utente non creato: Tutti i campi devono essere valorizzati");
			}

		});
	}
}
