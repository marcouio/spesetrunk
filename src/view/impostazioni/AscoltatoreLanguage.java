package view.impostazioni;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import business.config.ConfiguratoreXml;

public class AscoltatoreLanguage implements ActionListener {

	private JComboBox comboLanguage;
	
	public AscoltatoreLanguage(final JComboBox comboLanguage) {	
		this.comboLanguage = comboLanguage;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Node nodoLang = ConfiguratoreXml.getSingleton().getNodo("lang");
		Element elementoLang = ConfiguratoreXml.getElement(nodoLang);
		if(elementoLang!=null){
			elementoLang.setAttribute("locale", (String) comboLanguage.getSelectedItem());
		}
	}

}
