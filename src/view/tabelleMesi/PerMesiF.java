package view.tabelleMesi;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JTabbedPane;

import business.internazionalizzazione.I18NManager;
import grafica.componenti.contenitori.PannelloBase;

public class PerMesiF extends PannelloBase {


	private static final long serialVersionUID = 1L;
	private TabellaEntrata      tabEntrate = new TabellaEntrata();
	private TabellaUscita       tabUscite  = new TabellaUscita();
	private TabellaUscitaGruppi tabUG      = new TabellaUscitaGruppi();

	public PerMesiF(Container contenitore) {
		super(contenitore);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new Dimension(983, 545));
			this.setLayout(null);

			JTabbedPane tabGenerale = new JTabbedPane();
			tabGenerale.setBounds(0, 0, getContenitorePadre().getWidth(), getContenitorePadre().getHeight());
			tabGenerale.addTab(I18NManager.getSingleton().getMessaggio("income"), tabEntrate);
			tabGenerale.addTab(I18NManager.getSingleton().getMessaggio("withdrawal"), tabUscite);
			tabGenerale.addTab(I18NManager.getSingleton().getMessaggio("groupscharge"), tabUG);
			
			tabUscite.setBounds(0, 0, getContenitorePadre().getWidth(), getContenitorePadre().getHeight());
			tabEntrate.setBounds(0, 0, getContenitorePadre().getWidth(), getContenitorePadre().getHeight());
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
