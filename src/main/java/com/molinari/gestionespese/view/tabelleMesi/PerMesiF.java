package com.molinari.gestionespese.view.tabelleMesi;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;

import grafica.componenti.contenitori.PannelloBase;

public class PerMesiF extends PannelloBase {


	private static final long serialVersionUID = 1L;
	private TabellaEntrata      tabEntrate;
	private TabellaUscita       tabUscite;
	private TabellaUscitaGruppi tabUG;

	public PerMesiF(Container contenitore) {
		super(contenitore);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new Dimension(983, 545));
			this.setLayout(null);

			JTabbedPane tabGenerale = new JTabbedPane();
			tabGenerale.addChangeListener(new ChangeListener() {
				
				@Override	
				public void stateChanged(ChangeEvent e) {
					JTabbedPane source = (JTabbedPane) e.getSource();
					int selectedIndex = source.getSelectedIndex();
					if(selectedIndex == 0){
						tabEntrate = new TabellaEntrata();
						tabEntrate.setBounds(0, 0, getContenitorePadre().getWidth(), getContenitorePadre().getHeight());
						source.setComponentAt(selectedIndex, tabEntrate);
					}else if(selectedIndex == 1){
						tabUscite = new TabellaUscita();
						tabUscite.setBounds(0, 0, getContenitorePadre().getWidth(), getContenitorePadre().getHeight());
						source.setComponentAt(selectedIndex, tabUscite);
					}else if(selectedIndex == 2){
						tabUG = new TabellaUscitaGruppi();
						tabUG.setBounds(0, 0, getContenitorePadre().getWidth(), getContenitorePadre().getHeight());
						source.setComponentAt(selectedIndex, tabUG);
					}
				}
			});
			tabGenerale.setBounds(0, 0, getContenitorePadre().getWidth(), getContenitorePadre().getHeight());
			tabGenerale.addTab(I18NManager.getSingleton().getMessaggio("income"), tabEntrate);
			tabGenerale.addTab(I18NManager.getSingleton().getMessaggio("withdrawal"), tabUscite);
			tabGenerale.addTab(I18NManager.getSingleton().getMessaggio("groupscharge"), tabUG);
			
			this.add(tabGenerale);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the tabEntrate
	 */
	public TabellaEntrata getTabEntrate() {
		return tabEntrate;
	}

	/**
	 * @param tabEntrate
	 *            the tabEntrate to set
	 */
	public void setTabEntrate(final TabellaEntrata tabEntrate) {
		this.tabEntrate = tabEntrate;
	}

	/**
	 * @return the tabUscite
	 */
	public TabellaUscita getTabUscite() {
		return tabUscite;
	}

	/**
	 * @param tabUscite
	 *            the tabUscite to set
	 */
	public void setTabUscite(final TabellaUscita tabUscite) {
		this.tabUscite = tabUscite;
	}

}
