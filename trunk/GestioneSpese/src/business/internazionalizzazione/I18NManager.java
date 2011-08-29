package business.internazionalizzazione;

import java.util.Locale;
import java.util.ResourceBundle;

import business.config.ConfiguratoreXml;

public class I18NManager {

	public static void main(String[] args) {
		I18NManager i18n = new I18NManager();
		System.out.println(i18n.getMessaggio("io"));
	}

	private static I18NManager singleton;
	private Locale currentLocale;
	private ResourceBundle messages;

	/**
	 * @return the singleton
	 */
	public static I18NManager getSingleton() {
		if (singleton == null) {
			singleton = new I18NManager();
		}
		return singleton;
	}

	private I18NManager() {

	}

	public String getMessaggio(String key) {
		try {
			if(this.getMessages() == null){
				this.caricaMessaggi(ConfiguratoreXml.getSingleton().getLanguage(), null);
			}
			return this.getMessages().getString(key);
		} catch (Exception e) {
			return key;
		}
	}

	private void creaLocale(final String language, final String country) {
		if (language != null && country != null) {
			setLocale(language, country);
		} else if (language != null) {
			setLocale(language);
		} else {
			currentLocale = Locale.ITALIAN;
		}

	}

	public void setLocale(String language) {
		currentLocale = new Locale(language);
	}

	public void setLocale(String language, String country) {
		currentLocale = new Locale(language, country);
	}

	public void caricaMessaggi(String language, String country) {
		creaLocale(language, country);
		setMessages(ResourceBundle.getBundle("messaggi", currentLocale));
	}

	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}

	public ResourceBundle getMessages() {
		return messages;
	}
}
