package com.molinari.gestionespese.view.componenti;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.gestionespese.view.PannelTabs;
import com.molinari.gestionespese.view.componenti.movimenti.AbstractListaMov;
import com.molinari.gestionespese.view.componenti.movimenti.ListaMovimentiEntrate;
import com.molinari.gestionespese.view.componenti.movimenti.ListaMovimentiUscite;
import com.molinari.gestionespese.view.entrateuscite.EntrateView;
import com.molinari.gestionespese.view.entrateuscite.UsciteView;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.button.ToggleBtn;
import com.molinari.utility.graphic.component.buttonpanel.Bottone;
import com.molinari.utility.graphic.component.buttonpanel.PannelloBottoni;
import com.molinari.utility.graphic.component.buttonpanel.PannelloBottoniInterno;
import com.molinari.utility.messages.I18NManager;

public class PannelloBottoniSpese extends PannelloBottoni{

	private static final long serialVersionUID = 1L;
	private ToggleBtn toggleMovimenti;
	private ToggleBtn toggleMovimentiUscite;
	private ToggleBtn toggleMovimentiEntrate;
	private ToggleBtn toggleMesi;
	private ToggleBtn toggleEntrateUscite;
	private ToggleBtn toggleInsUscite;
	private ToggleBtn toggleInsEntrate;

	public PannelloBottoniSpese(Container contenitore) {
		super(contenitore);
	
		final ImageIcon iconaMovimenti = new ImageIcon(AltreUtil.IMGUTILPATH+"controlli.gif");
		final ImageIcon iconaMovimentiPic = new ImageIcon(AltreUtil.IMGUTILPATH+"controlli_pic.gif");
		toggleMovimenti = new ToggleBtn(iconaMovimenti);
		toggleMovimenti.settaggioBottoneStandard();
		final Bottone bottoneMovimenti = new Bottone(toggleMovimenti);
		addListenerMovimenti(toggleMovimenti);

		toggleMovimentiUscite = new ToggleBtn(iconaMovimentiPic, -1, 20);
		toggleMovimentiUscite.settaggioBottoneStandard();
		final Bottone bottoneMovimentiUscite = new Bottone(toggleMovimentiUscite);
		addListenerMovimentiUscite(toggleMovimentiUscite);

		toggleMovimentiEntrate = new ToggleBtn(iconaMovimentiPic, -1, 20);
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

		toggleMesi = new ToggleBtn(iconaUscite);
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

		toggleEntrateUscite = new ToggleBtn(iconaSoldi);
		
		toggleEntrateUscite.settaggioBottoneStandard();
		final Bottone bottoneEntrateUscite = new Bottone(toggleEntrateUscite);
		toggleEntrateUscite.setPadre(bottoneEntrateUscite);

		toggleInsUscite = new ToggleBtn(iconaSoldiPic, -1, 20);
		
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

		toggleInsEntrate = new ToggleBtn(iconaSoldiPic, -1, 20);
		
		toggleInsEntrate.settaggioBottoneStandard();
		final Bottone bottoneInsEntrate = new Bottone(toggleInsEntrate);
		toggleInsEntrate.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final PannelloBottoni pannelloEntrateUscite = bottoneEntrateUscite.getContenuto();
				final EntrateView dialog = new EntrateView(new WrapEntrate());
				dialog.getDialog().setLocationRelativeTo(null);
				dialog.getDialog().setBounds(0, 0, 347, 318);
				dialog.getDialog().setVisible(true);
				pannelloEntrateUscite.getGruppoBottoni().clearSelection();
			}
		});

		final PannelloBottoniInterno entrateUsciteContenuto = new PannelloBottoniInterno(bottoneEntrateUscite);
		final ArrayList<Bottone> dueBottoni = new ArrayList<>();
		dueBottoni.add(bottoneInsUscite);
		dueBottoni.add(bottoneInsEntrate);
		entrateUsciteContenuto.addDueBottoni(dueBottoni);
		bottoneEntrateUscite.setContenuto(entrateUsciteContenuto);

		this.addBottone(bottoneSql);
		this.addBottone(bottoneMesi);
		this.addBottone(bottoneMovimenti);
		this.addBottone(bottoneEntrateUscite);

		initTexts();
		
	}
	
	public void initTexts() {
		final String transactionsMsg = I18NManager.getSingleton().getMessaggio("transactions");
		final String uscite = I18NManager.getSingleton().getMessaggio("withdrawal");
		final String entrate = I18NManager.getSingleton().getMessaggio("income");
		final String mesi = I18NManager.getSingleton().getMessaggio("months");
		final String addtransaction = I18NManager.getSingleton().getMessaggio("addtransaction");
		final String charge = I18NManager.getSingleton().getMessaggio("charge");
		final String income = I18NManager.getSingleton().getMessaggio("income");

		toggleMovimenti.setText(transactionsMsg);
		toggleMovimentiUscite.setText(uscite);
		toggleMovimentiEntrate.setText(entrate);
		toggleMesi.setText(mesi);
		toggleEntrateUscite.setText(addtransaction);
		toggleInsUscite.setText(charge);
		toggleInsEntrate.setText(income);
		
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

	protected PannelTabs getPannelTabs() {
		return Controllore.getGeneralFrame().getPannelTabs();
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


}
