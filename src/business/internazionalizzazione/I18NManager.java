package business.internazionalizzazione;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18NManager {
	
	public static void main(String[] args) {
		I18NManager i18n = new I18NManager(null, null);
		String prova = i18n.messages.getString("marco");
		System.out.println(prova);
	}

	private static I18NManager singleton;
	private Locale currentLocale;
	private ResourceBundle messages;
	
	/**
	 * @return the singleton
	 */
	public static I18NManager getSingleton(final String language, final String country) {
		if(singleton == null){
			singleton = new I18NManager(language, country);
		}
		return singleton;
	}

	private I18NManager(final String language, final String country) {
		caricaMessaggi(language, country);
	}
	
	public String getMessaggio(String key){
		return this.getMessages().getString(key);
	}
	
	
	
	private void creaLocale(final String language,final String country) {
		if(language!=null && country!=null){
			setLocale(language, country);
		}else if(language!=null){
			setLocale(language);
		}else{
			currentLocale = Locale.ITALIAN;
		}

	}
	
	public void setLocale(String language) {
		currentLocale = new Locale(language);
	}
	
	public void setLocale(String language, String country) {
		currentLocale = new Locale(language, country);
	}
	
	private void caricaMessaggi(String language, String country) {
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
