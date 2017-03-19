package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.xml.CoreXMLManager;

public class AscoltatoreLanguage extends AscoltatoreAggiornatoreTutto {

	private final JComboBox<String> comboLanguage;

	public AscoltatoreLanguage(final JComboBox<String> comboLanguage) {
		this.comboLanguage = comboLanguage;
	}

	@Override
	public void actionPerformedOverride(ActionEvent e) {
		CoreXMLManager corexml = CoreXMLManager.getSingleton();
		final Node nodoLang = corexml.getNode("lang");
		final Element elementoLang = corexml.getElement(nodoLang);
		if (elementoLang != null) {
			elementoLang.setAttribute("locale", (String) comboLanguage.getSelectedItem());
			final Document doc = corexml.getDoc();
			CoreXMLManager.writeXmlFile2(doc,CoreXMLManager.XMLCOREPATH);
			I18NManager.getSingleton().caricaMessaggi((String) comboLanguage.getSelectedItem(), null);
		}
	}

}
