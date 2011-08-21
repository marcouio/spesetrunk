package business.config;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfiguratoreXml {
	
	private NodeList listaNodi;
	
	private static ConfiguratoreXml singleton;
	
	/**
	 * @return the listaNodi
	 */
	public NodeList getListaNodi() {
		return listaNodi;
	}

	/**
	 * @param listaNodi the listaNodi to set
	 */
	public void setListaNodi(NodeList listaNodi) {
		this.listaNodi = listaNodi;
	}

	/**
	 * @return the singleton
	 */
	public static ConfiguratoreXml getSingleton() {
		if(singleton == null){
			singleton = new ConfiguratoreXml();
		}
		return singleton;
	}

	private ConfiguratoreXml() {
		try {
			listaNodi = getNodeList("./config.xml");
		} catch (Exception e1) {
			e1.printStackTrace();
		} 

	}
	
	public Node getNodo(String nodo) {
		for(int i = 0; i < listaNodi.getLength(); i++){
			Node nodoDaLista = listaNodi.item(i);
			if(nodoDaLista.getNodeName().equals(nodo)){
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
	public static Document createDocument(final File xml) throws ParserConfigurationException, SAXException, IOException {
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		final Document doc = dBuilder.parse(xml);
		return doc;
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
	public static NodeList getNodeList(final String path) throws ParserConfigurationException, SAXException, IOException {
		final Document doc = createDocument(new File(path));
		final Element root = doc.getDocumentElement();
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
		if (nodoComponente!=null && nodoComponente.getNodeType() == Node.ELEMENT_NODE) {
			elemento = (Element) nodoComponente;
		}
		return elemento;
	}
}
