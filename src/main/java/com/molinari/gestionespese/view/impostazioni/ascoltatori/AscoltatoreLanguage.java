package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.config.ConfiguratoreXml;
import com.molinari.utility.messages.I18NManager;

public class AscoltatoreLanguage extends AscoltatoreAggiornatoreTutto {

	private final JComboBox comboLanguage;

	public AscoltatoreLanguage(final JComboBox comboLanguage) {
		this.comboLanguage = comboLanguage;
	}

	@Override
	public void actionPerformedOverride(ActionEvent e) {
		final Node nodoLang = ConfiguratoreXml.getSingleton().getNodo("lang");
		final Element elementoLang = ConfiguratoreXml.getElement(nodoLang);
		if (elementoLang != null) {
			elementoLang.setAttribute("locale", (String) comboLanguage.getSelectedItem());
			final Document doc = ConfiguratoreXml.getSingleton().getDocument();
			ConfiguratoreXml.writeXmlFile2(doc, ConfiguratoreXml.XMLPOSITION);
			I18NManager.getSingleton().caricaMessaggi((String) comboLanguage.getSelectedItem(), null);
		}
	}

}
