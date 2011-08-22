package view.impostazioni;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.config.ConfiguratoreXml;
import business.internazionalizzazione.I18NManager;

public class AscoltatoreLanguage extends AscoltatoreAggiornatoreTutto {

	private JComboBox comboLanguage;

	public AscoltatoreLanguage(final JComboBox comboLanguage) {
		this.comboLanguage = comboLanguage;
	}

	@Override
	public void actionPerformedOverride(ActionEvent e) {
		Node nodoLang = ConfiguratoreXml.getSingleton().getNodo("lang");
		Element elementoLang = ConfiguratoreXml.getElement(nodoLang);
		if (elementoLang != null) {
			elementoLang.setAttribute("locale", (String) comboLanguage.getSelectedItem());
			Document doc = ConfiguratoreXml.getSingleton().getDocument();
			ConfiguratoreXml.writeXmlFile2(doc, ConfiguratoreXml.XMLPOSITION);
			I18NManager.getSingleton().caricaMessaggi((String) comboLanguage.getSelectedItem(), null);
		}
	}

}
