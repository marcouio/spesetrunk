package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import business.AltreUtil;
import business.Controllore;
import business.GestioneSpeseException;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import business.internazionalizzazione.I18NManager;
import domain.wrapper.WrapEntrate;
import domain.wrapper.WrapSingleSpesa;
import grafica.componenti.contenitori.PannelloBase;
import view.bottoni.Bottone;
import view.bottoni.PannelloBottoni;
import view.bottoni.PannelloBottoniInterno;
import view.bottoni.ToggleBtn;
import view.componenti.movimenti.AbstractListaMov;
import view.componenti.movimenti.ListaMovimentiEntrate;
import view.componenti.movimenti.ListaMovimentiUscite;
import view.componenti.movimenti.Movimenti;
import view.entrateuscite.EntrateView;
import view.entrateuscite.UsciteView;
import view.mymenu.MyMenu;

public class GeneralFrame extends PannelloBase {

	private static final int MENU_HEIGHT = 15;
	private static final long serialVersionUID = 1L;
	private PannelTabs pannelTabs;
	
	
	public GeneralFrame(Container contenitore) {
		super(contenitore);
		setBounds(10, 10, contenitore.getWidth(), contenitore.getHeight());

		final MyMenu menu = createMenu(this);

		PannelloBottoni pannelloBottoni = createPannelloBottoni(menu);
		
		createTabsPanel(this, pannelloBottoni);
//		getPannelTabs().initConsollle();
//		setVisibleTrue(getPannelTabs().getConsolle());
		repaint();
	}

	private void createTabsPanel(Container contenitore, PannelloBottoni pannelloBottoni) {
		pannelTabs = new PannelTabs(contenitore);
		PannelloBase tabPanel = pannelTabs.getPanel();
		tabPanel.posizionaSottoA(pannelloBottoni, 0, 0);
		tabPanel.setSize(getContenitorePadre().getWidth(), getHeightSottoPannelli());
	}
	
	private int getHeightSottoPannelli() {
		return getContenitorePadre().getHeight() - getHeightBtnPanel() - MENU_HEIGHT;
	}

	private MyMenu createMenu(Container contenitore) {
		final MyMenu menu = new MyMenu();
		menu.setBounds(0, 0, this.getWidth(), MENU_HEIGHT);
		add(menu);
		menu.setVisible(true);
		return menu;
	}

	private PannelloBottoni createPannelloBottoni(MyMenu menu) {
		final PannelloBottoni pannelloBottoni = initPannelloBottoni(menu);
		
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
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setBounds(0, 0, 347, 407);
					dialog.setVisible(true);
					pannelloEntrateUscite.getGruppoBottoni().clearSelection();
				} catch (final Exception e1) {
					e1.printStackTrace();
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

		pannelloBottoni.addBottone(bottoneSql);
		pannelloBottoni.addBottone(bottoneMesi);
		pannelloBottoni.addBottone(bottoneMovimenti);
		pannelloBottoni.addBottone(bottoneEntrateUscite);

		add(pannelloBottoni);
		
		return pannelloBottoni;
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
				getPannelTabs().hidePanels();
				
				getPannelTabs().initTabMovimenti();
				
				Movimenti tabMovimenti = getPannelTabs().getTabMovimenti();
				ListaMovimentiEntrate tabMovEntrate = tabMovimenti.getTabMovEntrate();
				tabMovimenti.setLastView(tabMovEntrate);
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
				getPannelTabs().hidePanels();
				
				getPannelTabs().initTabMovimenti();
				
				Movimenti tabMovimenti = getPannelTabs().getTabMovimenti();
				ListaMovimentiUscite tabMovUscite = tabMovimenti.getTabMovUscite();
				tabMovimenti.setLastView(tabMovUscite);
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
				getPannelTabs().hidePanels();
				getPannelTabs().initTabMovimenti();
				AbstractListaMov lastView = getPannelTabs().getTabMovimenti().getLastView();
				if(lastView != null){
					lastView.setVisible(true);
				}
			}
		});
	}

	private PannelloBottoni initPannelloBottoni(MyMenu menu) {
		final PannelloBottoni pannelloBottoni = new PannelloBottoni(this);
		int heightBtnPanel = getHeightBtnPanel();
		pannelloBottoni.posizionaSottoA(menu, 0, 3);
		pannelloBottoni.setSize(getContenitorePadre().getWidth(), heightBtnPanel);
		pannelloBottoni.setBackground(Color.RED);
		pannelloBottoni.setVisible(true);
		add(pannelloBottoni);
		return pannelloBottoni;
	}

	private
	int getHeightBtnPanel() {
		return getContenitorePadre().getHeight() / 100 * 20;
	}
	
	public Movimenti getTabMovimenti(){
		return getPannelTabs().getTabMovimenti();
	}

	public void relocateFinestreLaterali() {
		if (Controllore.getSingleton().getInitFinestre().getFinestraVisibile() != null) {
			final Point p = getLocation();
			final Dimension d = getSize();
			p.setLocation(p.x + d.width + 5, p.y);
			try {
				final JFrame finestraVisibile = Controllore.getSingleton().getInitFinestre().getFinestraVisibile();
				finestraVisibile.setLocation(p);
			} catch (final Exception e) {
				throw new GestioneSpeseException(e);
			}
		}
	}

	public PannelTabs getPannelTabs() {
		return pannelTabs;
	}

	public void setPannelTabs(PannelTabs pannelTabs) {
		this.pannelTabs = pannelTabs;
	}
}
