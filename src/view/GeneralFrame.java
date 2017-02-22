package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
import view.componenti.movimenti.Movimenti;
import view.entrateuscite.EntrateView;
import view.entrateuscite.UsciteView;
import view.mymenu.MyMenu;
import view.tabelleMesi.PerMesiF;

public class GeneralFrame extends PannelloBase {

	private static final long serialVersionUID = 1L;
	private PerMesiF tabPermesi;
	private Movimenti tabMovimenti;
	private NewSql consolle;
	private final ArrayList<JPanel> listaPannelli = new ArrayList<>();

	
	
	public GeneralFrame(Container contenitore) {
		super(contenitore);
		setBounds(10, 10, 1000, 550);

		final MyMenu menu = new MyMenu();
		add(menu);

		createPannelloBottoni();
		initConsollle();
		setVisibleTrue(consolle);

		repaint();
	}

	private void setVisibleTrue(OggettoVistaBase oggettoView) {
		oggettoView.setVisible(true);
	}

	private void initPerMesi() {
		// Divisione di spese e entrate per5 mese
		if(tabPermesi == null){
			tabPermesi = new PerMesiF();
			tabPermesi.setBounds(20, 58, 1000, 550);
	
			add(tabPermesi);
			listaPannelli.add(tabPermesi);
		
		}
	}
	
	private void initTabMovimenti() {
		if(tabMovimenti == null){
			tabMovimenti = new Movimenti();
			tabMovimenti.getTabMovUscite().setBounds(0, 110, 1000, 550);
			tabMovimenti.getTabMovEntrate().setBounds(0, 110, 1000, 550);
			add(tabMovimenti.getTabMovEntrate());
			add(tabMovimenti.getTabMovUscite());
			listaPannelli.add(tabMovimenti.getTabMovEntrate());
			listaPannelli.add(tabMovimenti.getTabMovUscite());
		}
	}

	private void createPannelloBottoni() {
		final PannelloBottoni pannelloBottoni = new PannelloBottoni();
		final ImageIcon iconaMovimenti = new ImageIcon(AltreUtil.IMGUTILPATH+"controlli.gif");
		final ImageIcon iconaMovimentiPic = new ImageIcon(AltreUtil.IMGUTILPATH+"controlli_pic.gif");
		final ToggleBtn toggleMovimenti = new ToggleBtn(I18NManager.getSingleton().getMessaggio("transactions"), iconaMovimenti);
		toggleMovimenti.settaggioBottoneStandard();
		final Bottone bottoneMovimenti = new Bottone(toggleMovimenti);
		toggleMovimenti.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				hidePanels();
				initTabMovimenti();
				tabMovimenti.getTabMovUscite().setVisible(true);
			}
		});

		final String uscite = I18NManager.getSingleton().getMessaggio("withdrawal");
		final ToggleBtn toggleMovimentiUscite = new ToggleBtn(uscite, iconaMovimentiPic, -1, 20);
		toggleMovimentiUscite.settaggioBottoneStandard();
		final Bottone bottoneMovimentiUscite = new Bottone(toggleMovimentiUscite);
		toggleMovimentiUscite.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				hidePanels();
				
				initTabMovimenti();
				
				tabMovimenti.getTabMovUscite().setVisible(true);
				toggleMovimentiUscite.setSelected(false);
				repaint();
			}
		});
		final String entrate = I18NManager.getSingleton().getMessaggio("income");
		final ToggleBtn toggleMovimentiEntrate = new ToggleBtn(entrate, iconaMovimentiPic, -1, 20);
		toggleMovimentiEntrate.settaggioBottoneStandard();
		final Bottone bottoneMovimentiEntrate = new Bottone(toggleMovimentiEntrate);
		toggleMovimentiEntrate.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				hidePanels();
				
				initTabMovimenti();
				
				tabMovimenti.getTabMovEntrate().setVisible(true);
				toggleMovimentiEntrate.setSelected(false);
				bottoneMovimentiEntrate.getContenuto().getGruppoBottoni().clearSelection();
				bottoneMovimentiEntrate.getBottone().setSelected(false);
				repaint();
			}

		});

		final PannelloBottoniInterno pp = new PannelloBottoniInterno();
		final ArrayList<Bottone> dueButton = new ArrayList<>();
		dueButton.add(bottoneMovimentiUscite);
		dueButton.add(bottoneMovimentiEntrate);
		pp.addDueBottoni(dueButton);
		bottoneMovimenti.setContenuto(pp);

		toggleMovimenti.setPadre(bottoneMovimenti);

		final ImageIcon iconaUscite = new ImageIcon(AltreUtil.IMGUTILPATH+"blocktable_32.png");
		final String mesi = I18NManager.getSingleton().getMessaggio("months");

		final ToggleBtn toggleMesi = new ToggleBtn(mesi, iconaUscite);
		toggleMesi.settaggioBottoneStandard();
		final Bottone bottoneMesi = new Bottone(toggleMesi);
		toggleMesi.setPadre(bottoneMesi);
		toggleMesi.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				initPerMesi();
				hidePanels();
				
				tabPermesi.setVisible(true);
			}
		});

		final ImageIcon iconaSQL = new ImageIcon(AltreUtil.IMGUTILPATH+"sql.gif");
		final ToggleBtn toggleSql = new ToggleBtn("ConsolleSQL", iconaSQL);
		toggleSql.settaggioBottoneStandard();
		final Bottone bottoneSql = new Bottone(toggleSql);
		toggleSql.setPadre(bottoneSql);
		toggleSql.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				initConsollle();
				hidePanels();
				consolle.setVisible(true);
				repaint();
			}
		});

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

		final PannelloBottoniInterno entrateUsciteContenuto = new PannelloBottoniInterno();
		final ArrayList<Bottone> dueBottoni = new ArrayList<Bottone>();
		dueBottoni.add(bottoneInsUscite);
		dueBottoni.add(bottoneInsEntrate);
		entrateUsciteContenuto.addDueBottoni(dueBottoni);
		bottoneEntrateUscite.setContenuto(entrateUsciteContenuto);

		pannelloBottoni.addBottone(bottoneSql);
		pannelloBottoni.addBottone(bottoneMesi);
		pannelloBottoni.addBottone(bottoneMovimenti);
		pannelloBottoni.addBottone(bottoneEntrateUscite);

		add(pannelloBottoni);
		pannelloBottoni.setBounds(0, 20, this.getWidth(), 94);
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

	public PerMesiF getTabPermesi() {
		return tabPermesi;
	}

	public void setTabPermesi(final PerMesiF tabPermesi) {
		this.tabPermesi = tabPermesi;
	}

	public Movimenti getTabMovimenti() {
		return tabMovimenti;
	}

	public void setTabMovimenti(final Movimenti tabMovimenti) {
		this.tabMovimenti = tabMovimenti;
	}

	public NewSql getConsolle() {
		return consolle;
	}

	public void setConsolle(final NewSql consolle) {
		this.consolle = consolle;
	}

	private void initConsollle() {
		if(consolle == null){
			consolle = new NewSql();
			consolle.setBounds(20, 58, 1000, 550);
			add(consolle);
			listaPannelli.add(consolle);
		}
	}

	private void hidePanels() {
		for (final JPanel pannello : listaPannelli) {
			pannello.setVisible(false);
		}
	}

}
