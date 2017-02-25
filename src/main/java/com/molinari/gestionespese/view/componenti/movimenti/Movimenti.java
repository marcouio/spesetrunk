package com.molinari.gestionespese.view.componenti.movimenti;

import javax.swing.JTabbedPane;

import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.view.OggettoVistaBase;

public class Movimenti extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;

	private JTabbedPane           tabGenerale;
	private ListaMovimentiEntrate tabMovEntrate;
	private ListaMovimentiUscite  tabMovUscite;
	private AbstractListaMov lastView;

	public Movimenti() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(900, 650));
			this.setLayout(null);

			tabMovEntrate = new ListaMovimentiEntrate();

			tabGenerale = new JTabbedPane();
			tabGenerale.setBounds(65, 65, 800, 600);
			tabGenerale.addTab(I18NManager.getSingleton().getMessaggio("income")+" "+I18NManager.getSingleton().getMessaggio("transactions"), tabMovEntrate);

			tabMovEntrate.setBounds(20, 10, 700, 500);
			tabMovUscite = new ListaMovimentiUscite();
			tabGenerale.addTab(I18NManager.getSingleton().getMessaggio("withdrawal")+" "+I18NManager.getSingleton().getMessaggio("transactions"), tabMovUscite);
			tabMovUscite.setBounds(20, 10, 700, 500);

			this.add(tabGenerale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListaMovimentiEntrate getTabMovEntrate() {
		return tabMovEntrate;
	}

	protected void setTabMovEntrate(ListaMovimentiEntrate tabMovEntrate) {
		this.tabMovEntrate = tabMovEntrate;
	}

	public ListaMovimentiUscite getTabMovUscite() {
		return tabMovUscite;
	}

	protected void setTabMovUscite(ListaMovimentiUscite tabMovUscite) {
		this.tabMovUscite = tabMovUscite;
	}

	public AbstractListaMov getLastView() {
		return lastView;
	}

	public void setLastView(AbstractListaMov lastView) {
		this.lastView = lastView;
	}

}
