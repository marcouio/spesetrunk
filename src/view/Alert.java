package view;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import business.Controllore;

public class Alert {

	public static final String TITLE_OK = "Perfetto!";
	public static final String TITLE_ERROR = "Non ci siamo!";

	public static void info(final String messaggio, final String title) {
		JOptionPane.showMessageDialog(null, messaggio, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void errore(final String messaggio, final String title) {
		JOptionPane.showMessageDialog(null, messaggio, title, JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
	}

	public static void operazioniSegnalazioneErrore(final String messaggio) {
		Alert.errore(messaggio, Alert.TITLE_ERROR);
		Controllore.getLog().severe(messaggio);
	}

	public static void operazioniSegnalazioneInfo(final String messaggio) {
		Alert.info(messaggio, Alert.TITLE_OK);
		Controllore.getLog().info(messaggio);
	}

}
