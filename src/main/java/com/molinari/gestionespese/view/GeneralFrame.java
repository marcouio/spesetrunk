package com.molinari.gestionespese.view;

import java.awt.Color;
import java.awt.Container;

import com.molinari.gestionespese.business.InizializzatoreFinestre;
import com.molinari.gestionespese.view.componenti.PannelloBottoniSpese;
import com.molinari.gestionespese.view.mymenu.MyMenu;
import com.molinari.utility.graphic.component.buttonpanel.PannelloBottoni;
import com.molinari.utility.graphic.component.container.PannelloBase;

public class GeneralFrame extends PannelloBase {

	private static final int MENU_HEIGHT = 18;
	private static final long serialVersionUID = 1L;
	private PannelTabs pannelTabs;
	private InizializzatoreFinestre initFinestre;
	private PannelloBottoniSpese pannelloBottoni;
	private MyMenu menu;


	public GeneralFrame(Container contenitore) {
		super(contenitore);
		setBounds(10, 10, contenitore.getWidth(), contenitore.getHeight());

		final MyMenu menu = createMenu(this);

		pannelloBottoni = createPannelloBottoni(menu);

		createTabsPanel(this, pannelloBottoni);
		
		getPannelTabs().initConsollle();
		getPannelTabs().getConsolle().setVisible(true);
		repaint();
	}

	public void createTabsPanel(Container contenitore, PannelloBottoni pannelloBottoni) {
		pannelTabs = new PannelTabs(contenitore);
		final PannelloBase tabPanel = pannelTabs.getPanel();
		tabPanel.posizionaSottoA(pannelloBottoni, 0, 0);
		tabPanel.setSize(getContenitorePadre().getWidth(), getHeightSottoPannelli());
	}

	private int getHeightSottoPannelli() {
		return getContenitorePadre().getHeight() - getHeightBtnPanel() - MENU_HEIGHT;
	}
	
	private int getHeightBtnPanel() {
		return getContenitorePadre().getHeight() / 100 * 16;
	}

	public MyMenu createMenu(Container contenitore) {
		setMenu(new MyMenu());
		getMenu().setBounds(0, 0, this.getWidth(), MENU_HEIGHT);
		add(getMenu());
		getMenu().setVisible(true);
		return getMenu();
	}

	private PannelloBottoniSpese createPannelloBottoni(MyMenu menu) {
		
		final PannelloBottoniSpese pannelloBottoniLoc = initPannelloBottoni(menu);
		add(pannelloBottoniLoc);		
		return pannelloBottoniLoc;
	}
	
	private PannelloBottoniSpese initPannelloBottoni(MyMenu menu) {
		final PannelloBottoniSpese pannelloBottoniLoc = new PannelloBottoniSpese(this);
		final int heightBtnPanel = getHeightBtnPanel();
		pannelloBottoniLoc.posizionaSottoA(menu, 0, 3);
		pannelloBottoniLoc.setSize(getContenitorePadre().getWidth(), heightBtnPanel);
		pannelloBottoniLoc.setBackground(Color.RED);
		pannelloBottoniLoc.setVisible(true);
		add(pannelloBottoniLoc);
		return pannelloBottoniLoc;
	}

	public void relocateFinestreLaterali() {
		//Do nothing
	}

	public PannelTabs getPannelTabs() {
		return pannelTabs;
	}

	public void setPannelTabs(PannelTabs pannelTabs) {
		this.pannelTabs = pannelTabs;
	}

	public InizializzatoreFinestre getInitFinestre() {
		if (initFinestre == null) {
			initFinestre = new InizializzatoreFinestre();
			initFinestre.getPannello().posizionaADestraDi(pannelloBottoni, 0, 0);
			initFinestre.getPannello().setBackground(Color.RED);
		}
		return initFinestre;
	}

	public MyMenu getMenu() {
		return menu;
	}

	public void setMenu(MyMenu menu) {
		this.menu = menu;
	}

	public PannelloBottoniSpese getPannelloBottoni() {
		return pannelloBottoni;
	}

	public void setPannelloBottoni(PannelloBottoniSpese pannelloBottoni) {
		this.pannelloBottoni = pannelloBottoni;
	}
}
