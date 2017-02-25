package com.molinari.gestionespese.business.config;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfiguratoreXml {
	
	private static ConfiguratoreXml singleton;
	
	public static final String      XMLPOSITION = "./config.xml";
	private Document                document;
	private NodeList                listaNodi;

	/**
	 * @return the singleton
	 */
	public static ConfiguratoreXml getSingleton() {
		if (singleton == null) {
			singleton = new ConfiguratoreXml();
		}
		return singleton;
	}

	private ConfiguratoreXml() {
		try {
			listaNodi = getNodeList(XMLPOSITION);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public Node getNodo(String nodo) {
		for (int i = 0; i < listaNodi.getLength(); i++) {
			Node nodoDaLista = listaNodi.item(i);
			if (nodoDaLista.getNodeName().equals(nodo)) {
				return nodoDaLista;
			}
		}
		return null;
	}

	/**
	 * Carica tutte le informazioni del parametro xml in un document
	 * 
	 * @param xml
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private Document createDocument(final File xml) throws ParserConfigurationException, SAXException, IOException {
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		final Document doc = dBuilder.parse(xml);
		return doc;
	}

	// This method writes a DOM document to a file
	public static void writeXmlFile(Document doc, String filename) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			// initialize StreamResult with File object to save to file
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
//			String xmlString = result.getWriter().toString();

		} catch (Exception e) {}

	}

	// This method writes a DOM document to a file
	public static void writeXmlFile2(Document doc, String filename) {
		try {
			// save the result
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(new DOMSource(doc), new StreamResult(new File(filename)));

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getLanguage(){
		Node nodo = ConfiguratoreXml.getSingleton().getNodo("lang");
		Element elemento = ConfiguratoreXml.getElement(nodo);
		return elemento.getAttribute("locale");
	}
	
	/**
	 * Restituisce la lista di nodi interni ad un document creato dall'xml
	 * passato come parametro
	 * 
	 * @param path
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private NodeList getNodeList(final String path)
	    throws ParserConfigurationException, SAXException, IOException {
		document = createDocument(new File(path));
		final Element root = document.getDocumentElement();
		final NodeList listaNodi = (root.hasChildNodes()) ? root.getChildNodes() : null;
		return listaNodi;
	}

	/**
	 * Trasforma il "Node" in "Element" se è di tipo "Element"
	 * 
	 * @param nodoComponente
	 * @return Element
	 */
	public static Element getElement(final Node nodoComponente) {
		Element elemento = null;
		if (nodoComponente != null && nodoComponente.getNodeType() == Node.ELEMENT_NODE) {
			elemento = (Element) nodoComponente;
		}
		return elemento;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}
	
	/**
	 * @return the listaNodi
	 */
	public NodeList getListaNodi() {
		return listaNodi;
	}

	/**
	 * @param listaNodi
	 *            the listaNodi to set
	 */
	public void setListaNodi(NodeList listaNodi) {
		this.listaNodi = listaNodi;
	}

}
