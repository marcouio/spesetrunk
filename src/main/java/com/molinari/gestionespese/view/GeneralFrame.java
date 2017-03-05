package com.molinari.gestionespese.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.InizializzatoreFinestre;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.gestionespese.view.bottoni.Bottone;
import com.molinari.gestionespese.view.bottoni.PannelloBottoni;
import com.molinari.gestionespese.view.bottoni.PannelloBottoniInterno;
import com.molinari.gestionespese.view.bottoni.ToggleBtn;
import com.molinari.gestionespese.view.componenti.movimenti.AbstractListaMov;
import com.molinari.gestionespese.view.componenti.movimenti.ListaMovimentiEntrate;
import com.molinari.gestionespese.view.componenti.movimenti.ListaMovimentiUscite;
import com.molinari.gestionespese.view.entrateuscite.EntrateView;
import com.molinari.gestionespese.view.entrateuscite.UsciteView;
import com.molinari.gestionespese.view.mymenu.MyMenu;

import grafica.componenti.alert.Alert;
import grafica.componenti.contenitori.PannelloBase;

public class GeneralFrame extends PannelloBase {

	private static final int MENU_HEIGHT = 18;
	private static final long serialVersionUID = 1L;
	private PannelTabs pannelTabs;
	private InizializzatoreFinestre initFinestre;
	private PannelloBottoni pannelloBottoni;


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

	public MyMenu createMenu(Container contenitore) {
		final MyMenu menu = new MyMenu();
		menu.setBounds(0, 0, this.getWidth(), MENU_HEIGHT);
		add(menu);
		menu.setVisible(true);
		return menu;
	}

	private PannelloBottoni createPannelloBottoni(MyMenu menu) {
		final PannelloBottoni pannelloBottoniLoc = initPannelloBottoni(menu);

		final ImageIcon iconaMovimenti = new ImageIcon(AltreUtil.IMGUTILPATH+"controlli.gif");
		final ImageIcon iconaMovimentiPic = new ImageIcon(AltreUtil.IMGUTILPATH+"controlli_pic.gif");
		final ToggleBtn toggleMovimenti = new ToggleBtn(I18NManager.getSingleton().getMessaggio("transactions"), iconaMovimenti);
		toggleMovimenti.settaggioBottoneStandard();
		final Bottone bottoneMovimenti = new Bottone(toggleMovimenti);
		addListenerMovimenti(toggleMovimenti);

		final String uscite = I18NManager.getSingleton().getMessaggio("withdrawal");
		final ToggleBtn toggleMovimentiUscite = new ToggleBtn(uscite, iconaMovimentiPic, -1, 20);
		toggleMovimentiUscite.settaggioBottoneStandard();
		final Bottone bottoneMovimentiUscite = new Bottone(toggleMovimentiUscite);
		addListenerMovimentiUscite(toggleMovimentiUscite);

		final String entrate = I18NManager.getSingleton().getMessaggio("income");
		final ToggleBtn toggleMovimentiEntrate = new ToggleBtn(entrate, iconaMovimentiPic, -1, 20);
		toggleMovimentiEntrate.settaggioBottoneStandard();
		final Bottone bottoneMovimentiEntrate = new Bottone(toggleMovimentiEntrate);

		final PannelloBottoniInterno pp = new PannelloBottoniInterno(bottoneMovimenti);
		final ArrayList<Bottone> dueButton = new ArrayList<>();
		dueButton.add(bottoneMovimentiUscite);
		dueButton.add(bottoneMovimentiEntrate);
		bottoneMovimenti.setContenuto(pp);
		addListenerMovimentiEntrate(toggleMovimentiEntrate);
		pp.addDueBottoni(dueButton);

		toggleMovimenti.setPadre(bottoneMovimenti);

		final ImageIcon iconaUscite = new ImageIcon(AltreUtil.IMGUTILPATH+"blocktable_32.png");
		final String mesi = I18NManager.getSingleton().getMessaggio("months");

		final ToggleBtn toggleMesi = new ToggleBtn(mesi, iconaUscite);
		toggleMesi.settaggioBottoneStandard();
		final Bottone bottoneMesi = new Bottone(toggleMesi);
		toggleMesi.setPadre(bottoneMesi);
		addListenerMesi(toggleMesi);

		final ImageIcon iconaSQL = new ImageIcon(AltreUtil.IMGUTILPATH+"sql.gif");
		final ToggleBtn toggleSql = new ToggleBtn("ConsolleSQL", iconaSQL);
		toggleSql.settaggioBottoneStandard();
		final Bottone bottoneSql = new Bottone(toggleSql);
		toggleSql.setPadre(bottoneSql);
		addListenerConsolle(toggleSql);

		final ImageIcon iconaSoldi = new ImageIcon(AltreUtil.IMGUTILPATH+"soldi.gif");
		final ImageIcon iconaSoldiPic = new ImageIcon(AltreUtil.IMGUTILPATH+"soldi_pic.gif");

		final String addtransaction = I18NManager.getSingleton().getMessaggio("addtransaction");
		final ToggleBtn toggleEntrateUscite = new ToggleBtn(addtransaction, iconaSoldi);

		toggleEntrateUscite.settaggioBottoneStandard();
		final Bottone bottoneEntrateUscite = new Bottone(toggleEntrateUscite);
		toggleEntrateUscite.setPadre(bottoneEntrateUscite);

		final String charge = I18NManager.getSingleton().getMessaggio("charge");
		final ToggleBtn toggleInsUscite = new ToggleBtn(charge, iconaSoldiPic, -1, 20);

		toggleInsUscite.settaggioBottoneStandard();
		final Bottone bottoneInsUscite = new Bottone(toggleInsUscite);
		toggleInsUscite.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final PannelloBottoni pannelloEntrateUscite = bottoneEntrateUscite.getContenuto();
					final UsciteView dialog = new UsciteView(new WrapSingleSpesa());
					dialog.getDialog().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.getDialog().setBounds(0, 0, 347, 407);
					dialog.getDialog().setVisible(true);
					pannelloEntrateUscite.getGruppoBottoni().clearSelection();
				} catch (final Exception e1) {
					Alert.segnalazioneEccezione(e1, null);
				}
			}
		});

		final String income = I18NManager.getSingleton().getMessaggio("income");
		final ToggleBtn toggleInsEntrate = new ToggleBtn(income, iconaSoldiPic, -1, 20);

		toggleInsEntrate.settaggioBottoneStandard();
		final Bottone bottoneInsEntrate = new Bottone(toggleInsEntrate);
		toggleInsEntrate.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final PannelloBottoni pannelloEntrateUscite = bottoneEntrateUscite.getContenuto();
				final EntrateView dialog = new EntrateView(new WrapEntrate());
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(0, 0, 347, 318);
				dialog.setVisible(true);
				pannelloEntrateUscite.getGruppoBottoni().clearSelection();
			}
		});

		final PannelloBottoniInterno entrateUsciteContenuto = new PannelloBottoniInterno(bottoneEntrateUscite);
		final ArrayList<Bottone> dueBottoni = new ArrayList<>();
		dueBottoni.add(bottoneInsUscite);
		dueBottoni.add(bottoneInsEntrate);
		entrateUsciteContenuto.addDueBottoni(dueBottoni);
		bottoneEntrateUscite.setContenuto(entrateUsciteContenuto);

		pannelloBottoniLoc.addBottone(bottoneSql);
		pannelloBottoniLoc.addBottone(bottoneMesi);
		pannelloBottoniLoc.addBottone(bottoneMovimenti);
		pannelloBottoniLoc.addBottone(bottoneEntrateUscite);

		add(pannelloBottoniLoc);

		return pannelloBottoniLoc;
	}

	private void addListenerConsolle(final ToggleBtn toggleSql) {
		toggleSql.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				getPannelTabs().initConsollle();
				getPannelTabs().hidePanels();
				getPannelTabs().getConsolle().setVisible(true);
				repaint();
			}
		});
	}

	private void addListenerMesi(final ToggleBtn toggleMesi) {
		toggleMesi.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				getPannelTabs().initPerMesi();
				getPannelTabs().hidePanels();

				getPannelTabs().getTabPermesi().setVisible(true);
			}
		});
	}

	private void addListenerMovimentiEntrate(final ToggleBtn toggleMovimentiEntrate) {
		toggleMovimentiEntrate.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final PannelTabs pannelTabsLoc = getPannelTabs();
				pannelTabsLoc.hidePanels();

				pannelTabsLoc.initTabMovimentiEntrate();
				final ListaMovimentiEntrate tabMovEntrate = pannelTabsLoc.getTabMovEntrate();
				pannelTabsLoc.setLastView(tabMovEntrate);
				tabMovEntrate.setVisible(true);
				toggleMovimentiEntrate.setSelected(false);
				repaint();
			}

		});
	}

	private void addListenerMovimentiUscite(final ToggleBtn toggleMovimentiUscite) {
		toggleMovimentiUscite.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final PannelTabs pannelTabsLoc = getPannelTabs();
				pannelTabsLoc.hidePanels();

				pannelTabsLoc.initTabMovimentiUscite();
				final ListaMovimentiUscite tabMovUscite = pannelTabsLoc.getTabMovUscite();
				pannelTabsLoc.setLastView(tabMovUscite);
				tabMovUscite.setVisible(true);
				toggleMovimentiUscite.setSelected(false);
				repaint();
			}
		});
	}

	private void addListenerMovimenti(final ToggleBtn toggleMovimenti) {
		toggleMovimenti.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {

				final AbstractListaMov lastView = getPannelTabs().getLastView();
				if(lastView != null){
					getPannelTabs().hidePanels();
					lastView.setVisible(true);
				}
			}
		});
	}

	private PannelloBottoni initPannelloBottoni(MyMenu menu) {
		final PannelloBottoni pannelloBottoniLoc = new PannelloBottoni(this);
		final int heightBtnPanel = getHeightBtnPanel();
		pannelloBottoniLoc.posizionaSottoA(menu, 0, 3);
		pannelloBottoniLoc.setSize(getContenitorePadre().getWidth(), heightBtnPanel);
		pannelloBottoniLoc.setBackground(Color.RED);
		pannelloBottoniLoc.setVisible(true);
		add(pannelloBottoniLoc);
		return pannelloBottoniLoc;
	}

	private int getHeightBtnPanel() {
		return getContenitorePadre().getHeight() / 100 * 16;
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
}
