package com.molinari.gestionespese.view.tabellamesi;

import java.awt.Container;
import java.awt.Dimension;
import java.util.logging.Level;

import javax.swing.JTabbedPane;

import com.molinari.gestionespese.view.OggettoVistaBase;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.messages.I18NManager;

public class PerMesiF extends PannelloBase {


	private static final long serialVersionUID = 1L;
	private transient TabellaEntrata      tabEntrate;
	private transient TabellaUscita       tabUscite;
	private transient TabellaUscitaGruppi tabUG;

	public PerMesiF(Container contenitore) {
		super(contenitore);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new Dimension(983, 545));
			this.setLayout(null);

			final JTabbedPane tabGenerale = new JTabbedPane();
			tabGenerale.addChangeListener(e -> {
				final JTabbedPane source = (JTabbedPane) e.getSource();
				final int selectedIndex = source.getSelectedIndex();
				if(selectedIndex == 0){
					tabEntrate = new TabellaEntrata(this);
					tabEntrate.getPanel().setBounds(0, 0, getContenitorePadre().getWidth(), getContenitorePadre().getHeight());
					source.setComponentAt(selectedIndex, tabEntrate.getPanel());
				}else if(selectedIndex == 1){
					tabUscite = new TabellaUscita(this);
					tabUscite.getPanel().setBounds(0, 0, getContenitorePadre().getWidth(), getContenitorePadre().getHeight());
					source.setComponentAt(selectedIndex, tabUscite.getPanel());
				}else if(selectedIndex == 2){
					tabUG = new TabellaUscitaGruppi(this);
					tabUG.getPanel().setBounds(0, 0, getContenitorePadre().getWidth(), getContenitorePadre().getHeight());
					source.setComponentAt(selectedIndex, tabUG.getPanel());
				}
			});
			OggettoVistaBase panelEntrate = tabEntrate != null ? tabEntrate.getPanel() : null;
			OggettoVistaBase panelUscite = tabUscite != null ? tabUscite.getPanel() : null;
			OggettoVistaBase panelGruppi = tabUG != null ? tabUG.getPanel() : null;
			tabGenerale.setBounds(0, 200, getContenitorePadre().getWidth(), getContenitorePadre().getHeight());
			tabGenerale.addTab(I18NManager.getSingleton().getMessaggio("income"), panelEntrate);
			tabGenerale.addTab(I18NManager.getSingleton().getMessaggio("withdrawal"), panelUscite);
			tabGenerale.addTab(I18NManager.getSingleton().getMessaggio("groupscharge"), panelGruppi);

			this.add(tabGenerale);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
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

	public TabellaUscitaGruppi getTabUG() {
		return tabUG;
	}

	public void setTabUG(TabellaUscitaGruppi tabUG) {
		this.tabUG = tabUG;
	}

}
