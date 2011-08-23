package view;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import business.AltreUtil;
import business.Controllore;
import business.internazionalizzazione.I18NManager;

public class Alert {

	public static final String TITLE_OK    = I18NManager.getSingleton().getMessaggio("perfect");
	public static final String TITLE_ERROR = I18NManager.getSingleton().getMessaggio("error");

	public static void info(final String key, final String title) {
		String msg = I18NManager.getSingleton().getMessaggio(key);
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void errore(final String key, final String title) {
		String msg = I18NManager.getSingleton().getMessaggio(key);
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE, new ImageIcon(AltreUtil.IMGUTILPATH+"index.jpeg"));
	}

	public static void operazioniSegnalazioneErroreGrave(final String key) {
		String msg = I18NManager.getSingleton().getMessaggio(key);
		Alert.errore(msg, Alert.TITLE_ERROR);
		Controllore.getLog().severe(msg);
	}

	public static void operazioniSegnalazioneErroreWarning(final String key) {
		String msg = I18NManager.getSingleton().getMessaggio(key);
		Alert.errore(msg, Alert.TITLE_ERROR);
		Controllore.getLog().warning(msg);
	}

	public static void operazioniSegnalazioneInfo(final String key) {
		String msg = I18NManager.getSingleton().getMessaggio(key);
		Alert.info(msg, Alert.TITLE_OK);
		Controllore.getLog().info(msg);
	}

	public static String getMessaggioErrore(final String key) {
		String msg = I18NManager.getSingleton().getMessaggio(key);
		return I18NManager.getSingleton().getMessaggio("nooperation")+": " + msg;
	}

}
