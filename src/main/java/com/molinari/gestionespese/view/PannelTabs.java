package com.molinari.gestionespese.view;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.molinari.gestionespese.view.componenti.movimenti.AbstractListaMov;
import com.molinari.gestionespese.view.componenti.movimenti.ListaMovimentiEntrate;
import com.molinari.gestionespese.view.componenti.movimenti.ListaMovimentiUscite;
import com.molinari.gestionespese.view.tabellamesi.PerMesiF;
import com.molinari.utility.graphic.component.container.PannelloBase;

public class PannelTabs {

	private Container contenitore;
	private PannelloBase panel;
	private PerMesiF tabPermesi;
	private ListaMovimentiEntrate tabMovEntrate;
	private ListaMovimentiUscite  tabMovUscite;
	private AbstractListaMov lastView;
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

	public void initTabMovimentiEntrate() {
		ListaMovimentiEntrate tabMovEntrateLoc = getTabMovEntrate();
		if(tabMovEntrateLoc == null){
			tabMovEntrateLoc = new ListaMovimentiEntrate(this.getPanel());
			setTabMovEntrate(tabMovEntrateLoc);
			setBoundsTab(tabMovEntrateLoc);
			panel.add(tabMovEntrateLoc);
			listaPannelli.add(tabMovEntrateLoc);
		}
	}

	public void initTabMovimentiUscite() {
		ListaMovimentiUscite tabMovUsciteLoc = getTabMovUscite();
		if(tabMovUsciteLoc == null){
			tabMovUsciteLoc = new ListaMovimentiUscite(this.getPanel());
			setTabMovUscite(tabMovUsciteLoc);
			setBoundsTab(tabMovUsciteLoc);
			panel.add(tabMovUsciteLoc);
			listaPannelli.add(tabMovUsciteLoc);
		}
	}

	public void initConsollle() {
		if(consolle == null){
			consolle = new NewSql(this.getPanel());
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

	public NewSql getConsolle() {
		return consolle;
	}

	public void setConsolle(NewSql consolle) {
		this.consolle = consolle;
	}

	public List<JPanel> getListaPannelli() {
		return listaPannelli;
	}

	public void setPanel(PannelloBase panel) {
		this.panel = panel;
	}

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

	public ListaMovimentiEntrate getTabMovEntrate() {
		return tabMovEntrate;
	}

	public void setTabMovEntrate(ListaMovimentiEntrate tabMovEntrate) {
		this.tabMovEntrate = tabMovEntrate;
	}

	public ListaMovimentiUscite getTabMovUscite() {
		return tabMovUscite;
	}

	public void setTabMovUscite(ListaMovimentiUscite tabMovUscite) {
		this.tabMovUscite = tabMovUscite;
	}

	public AbstractListaMov getLastView() {
		return lastView;
	}

	public void setLastView(AbstractListaMov lastView) {
		this.lastView = lastView;
	}
}
