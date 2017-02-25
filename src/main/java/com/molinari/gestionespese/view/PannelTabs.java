package com.molinari.gestionespese.view;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.molinari.gestionespese.view.componenti.movimenti.ListaMovimentiEntrate;
import com.molinari.gestionespese.view.componenti.movimenti.ListaMovimentiUscite;
import com.molinari.gestionespese.view.componenti.movimenti.Movimenti;
import com.molinari.gestionespese.view.tabelleMesi.PerMesiF;

import grafica.componenti.contenitori.PannelloBase;

public class PannelTabs {

	private Container contenitore;
	private PannelloBase panel;
	private PerMesiF tabPermesi;
	private Movimenti tabMovimenti;
	private NewSql consolle;
	
	private final ArrayList<JPanel> listaPannelli = new ArrayList<>();

	public PannelTabs(Container contenitore) {
		this.contenitore = contenitore;
	}
	
	protected PannelloBase createPanelTabs() {
		panel = new PannelloBase(contenitore);
		return panel;
	}
	
	public PannelloBase getPanel(){
		if(panel == null){
			panel = createPanelTabs();
		}
		return panel;
	}
	
	public void initTabMovimenti() {
		if(tabMovimenti == null){
			tabMovimenti = new Movimenti();
		}
	}
	public void initTabMovimentiEntrate() {
		if(tabMovimenti.getTabMovEntrate() == null){
			tabMovimenti.setTabMovEntrate(new ListaMovimentiEntrate());
			setBoundsTab(tabMovimenti.getTabMovEntrate());
			panel.add(tabMovimenti.getTabMovEntrate());
			listaPannelli.add(tabMovimenti.getTabMovEntrate());
		}
	}

	public void initTabMovimentiUscite() {
		if(tabMovimenti.getTabMovUscite() == null){
			tabMovimenti.setTabMovUscite(new ListaMovimentiUscite());
			setBoundsTab(tabMovimenti.getTabMovUscite());
			panel.add(tabMovimenti.getTabMovUscite());
			listaPannelli.add(tabMovimenti.getTabMovUscite());
		}
	}
	
	public void initConsollle() {
		if(consolle == null){
			consolle = new NewSql();
			setBoundsTab(consolle);
			panel.add(consolle);
			listaPannelli.add(consolle);
		}
	}

	public void hidePanels() {
		for (final JPanel pannello : listaPannelli) {
			pannello.setVisible(false);
		}
	}
	
	public void initPerMesi() {
		// Divisione di spese e entrate per5 mese
		if(tabPermesi == null){
			tabPermesi = new PerMesiF(this.getPanel());
			setBoundsTab(tabPermesi);
	
			panel.add(tabPermesi);
			listaPannelli.add(tabPermesi);
		
		}
	}
	
	public PerMesiF getTabPermesi() {
		return tabPermesi;
	}

	public void setTabPermesi(PerMesiF tabPermesi) {
		this.tabPermesi = tabPermesi;
	}

	public Movimenti getTabMovimenti() {
		return tabMovimenti;
	}

	public void setTabMovimenti(Movimenti tabMovimenti) {
		this.tabMovimenti = tabMovimenti;
	}

	public NewSql getConsolle() {
		return consolle;
	}

	public void setConsolle(NewSql consolle) {
		this.consolle = consolle;
	}

	public ArrayList<JPanel> getListaPannelli() {
		return listaPannelli;
	}

	public void setPanel(PannelloBase panel) {
		this.panel = panel;
	}

//	public void setBoundsTab(OggettoVistaBase ovb){
//		ovb.setBounds(0, getHeightBtnPanel() + MENU_HEIGHT, getContenitorePadre().getWidth(), getHeightSottoPannelli());
//	}
	public void setBoundsTab(JPanel ovb){
		ovb.setBounds(0, 0, getContenitore().getWidth(), getHeightSottoPannelli());
	}
	
	private int getHeightSottoPannelli() {
		return getContenitore().getHeight();
	}

	public Container getContenitore() {
		return contenitore;
	}

	public void setContenitore(Container contenitore) {
		this.contenitore = contenitore;
	}
}
