package com.molinari.gestionespese.view.componenti.movimenti;

import java.util.logging.Level;

import com.molinari.gestionespese.business.Controllore;
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
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
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
