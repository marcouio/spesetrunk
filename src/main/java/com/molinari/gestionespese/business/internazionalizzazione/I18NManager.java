package com.molinari.gestionespese.business.internazionalizzazione;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.molinari.gestionespese.business.config.ConfiguratoreXml;

import controller.ControlloreBase;

public class I18NManager {

	private static I18NManager singleton;
	private Locale currentLocale;
	private ResourceBundle messages;

	private I18NManager() {
		//do nothing
	}
	
	/**
	 * @return the singleton
	 */
	public static I18NManager getSingleton() {
		if (singleton == null) {
			singleton = new I18NManager();
		}
		return singleton;
	}

	public String getMessaggio(final String key) {
		try {
			if(this.getMessages() == null){
				this.caricaMessaggi(ConfiguratoreXml.getSingleton().getLanguage(), null);
			}
			return this.getMessages().getString(key);
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(),e);
			return key;
		}
	}

	public String getMessaggio(final String key, final String[] params) {
		try {
			StringBuilder msgTot = new StringBuilder();
			final String messaggio = getMessaggio(key);
			final String[] msgSplit = messaggio.split("@");
			appendParams(params, msgTot, msgSplit);
			return msgTot.toString();
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(),e);
			return key;
		}
	}

	private void appendParams(final String[] params, StringBuilder msgTot, final String[] msgSplit) {
		if(msgSplit.length-1 == params.length){
			for (int i = 0; i < params.length; i++) {
				msgTot.append(msgSplit[i]);
				msgTot.append(params[i]);
				if(i==params.length-1){
					msgTot.append(msgSplit[msgSplit.length-1]);
				}
			}
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

	public void setLocale(final String language) {
		currentLocale = new Locale(language);
	}

	public void setLocale(final String language, final String country) {
		currentLocale = new Locale(language, country);
	}

	public void caricaMessaggi(final String language, final String country) {
		creaLocale(language, country);
		setMessages(ResourceBundle.getBundle("messaggi", currentLocale));
	}

	public void setMessages(final ResourceBundle messages) {
		this.messages = messages;
	}

	public ResourceBundle getMessages() {
		return messages;
	}
}
