package com.molinari.gestionespese.view.componenti.movimenti;

import com.molinari.gestionespese.view.OggettoVistaBase;

public class Movimenti extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;
	private ListaMovimentiEntrate tabMovEntrate;
	private ListaMovimentiUscite  tabMovUscite;
	private AbstractListaMov lastView;

	public Movimenti() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
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
