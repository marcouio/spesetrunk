package com.molinari.gestionespese.view;

import java.awt.Container;

import com.molinari.gestionespese.business.InizializzatoreFinestre;
import com.molinari.gestionespese.view.componenti.PannelloBottoniSpese;
import com.molinari.gestionespese.view.mymenu.ApplicationMenu;
import com.molinari.utility.graphic.component.buttonpanel.PannelloBottoni;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.math.UtilMath;

public class GeneralFrame extends PannelloBase {

	private static final int WIDTH_CONTENT = 80;
	private static final int MENU_HEIGHT = 18;
	private static final long serialVersionUID = 1L;
	private transient PannelTabs pannelTabs;
	private transient InizializzatoreFinestre initFinestre;
	private PannelloBottoniSpese pannelloBottoni;
	private ApplicationMenu menu;


	public GeneralFrame(Container contenitore) {
		super(contenitore);
		setBounds(10, 10, contenitore.getWidth(), contenitore.getHeight());

		final ApplicationMenu menuLoc = createMenu();
		pannelloBottoni = createPannelloBottoni(menuLoc);

		createTabsPanel(this, pannelloBottoni);
		
		getPannelTabs().initConsollle();
		getPannelTabs().getConsolle().setVisible(true);
		
		getInitFinestre();
		
		repaint();
		
	}
	
	public void setSelectedWindow(int index) {
		getMenu().setSelectedWindow(this, index);	
	}

	public void createTabsPanel(Container contenitore, PannelloBottoni pannelloBottoni) {
		pannelTabs = new PannelTabs(contenitore);
		final PannelloBase tabPanel = pannelTabs.getPanel();
		tabPanel.posizionaSottoA(pannelloBottoni, 0, 0);
		tabPanel.setSize((int) UtilMath.getPercentage(this.getWidth(), WIDTH_CONTENT), getHeightSottoPannelli());
	}

	private int getHeightSottoPannelli() {
		return getContenitorePadre().getHeight() - getHeightBtnPanel() - MENU_HEIGHT;
	}
	
	private int getHeightBtnPanel() {
		return getContenitorePadre().getHeight() / 100 * 13;
	}

	public ApplicationMenu createMenu() {
		setMenu(new ApplicationMenu());
		getMenu().setBounds(0, 0, this.getWidth(), MENU_HEIGHT);
		add(getMenu());
		getMenu().setVisible(true);
		return getMenu();
	}

	private PannelloBottoniSpese createPannelloBottoni(ApplicationMenu menu) {
		
		final PannelloBottoniSpese pannelloBottoniLoc = initPannelloBottoni(menu);
		add(pannelloBottoniLoc);		
		return pannelloBottoniLoc;
	}
	
	private PannelloBottoniSpese initPannelloBottoni(ApplicationMenu menu) {
		final PannelloBottoniSpese pannelloBottoniLoc = new PannelloBottoniSpese(this);
		final int heightBtnPanel = getHeightBtnPanel();
		pannelloBottoniLoc.posizionaSottoA(menu, 0, 3);
		pannelloBottoniLoc.setSize((int) UtilMath.getPercentage(this.getWidth(), WIDTH_CONTENT), heightBtnPanel);
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
			initFinestre = new InizializzatoreFinestre(this);
			initFinestre.getPannello().posizionaADestraDi(pannelloBottoni, 0, 0);
		}
		return initFinestre;
	}

	public ApplicationMenu getMenu() {
		return menu;
	}

	public void setMenu(ApplicationMenu menu) {
		this.menu = menu;
	}

	public PannelloBottoniSpese getPannelloBottoni() {
		return pannelloBottoni;
	}

	public void setPannelloBottoni(PannelloBottoniSpese pannelloBottoni) {
		this.pannelloBottoni = pannelloBottoni;
	}
}
