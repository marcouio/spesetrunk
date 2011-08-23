package view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import view.bottoni.Bottone;
import view.bottoni.PannelloBottoni;
import view.bottoni.PannelloBottoniInterno;
import view.bottoni.ToggleBtn;
import view.componenti.componentiPannello.PannelloAScomparsa2;
import view.componenti.movimenti.Movimenti;
import view.entrateuscite.EntrateView;
import view.entrateuscite.UsciteView;
import view.impostazioni.Impostazioni;
import view.mymenu.MyMenu;
import view.note.MostraNoteView;
import view.tabelle.PerMesiF;
import business.AltreUtil;
import business.Controllore;
import business.DBUtil;
import business.InizializzatoreFinestre;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import business.internazionalizzazione.I18NManager;
import domain.wrapper.WrapEntrate;
import domain.wrapper.WrapSingleSpesa;

public class GeneralFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private static PerMesiF tabPermesi;
	private static Movimenti tabMovimenti;
	private static NewSql consolle;
	private static GeneralFrame singleton;
	private final ArrayList<JPanel> listaPannelli = new ArrayList<JPanel>();

	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DBUtil.closeConnection();
				final GeneralFrame inst = new GeneralFrame();
				inst.setTitle(I18NManager.getSingleton().getMessaggio("title"));
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public static final GeneralFrame getSingleton() {
		if (singleton == null) {
			synchronized (Impostazioni.class) {
				if (singleton == null) {
					singleton = new GeneralFrame();
				}
			} // if
		} // if
		return singleton;
	} // getSingleton()

	/**
	 * Create the frame.
	 */
	private GeneralFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1000, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		final MyMenu menu = new MyMenu();
		contentPane.add(menu);

		createPannelloBottoni();
		consolle = new NewSql();
		consolle.setBounds(20, 58, 970, 650);

		// movimenti
		tabMovimenti = new Movimenti();
		tabMovimenti.getTabMovUscite().setBounds(0, 110, 970, 650);
		tabMovimenti.getTabMovEntrate().setBounds(0, 110, 970, 650);

		// Divisione di spese e entrate per5 mese
		tabPermesi = new PerMesiF();
		tabPermesi.setBounds(20, 58, 1000, 750);

		// this.getContentPane().add(tabGenerale);

		contentPane.add(tabMovimenti.getTabMovEntrate());
		contentPane.add(tabMovimenti.getTabMovUscite());
		listaPannelli.add(tabMovimenti.getTabMovEntrate());
		listaPannelli.add(tabMovimenti.getTabMovUscite());
		contentPane.add(tabPermesi);
		listaPannelli.add(tabPermesi);
		contentPane.add(consolle);
		listaPannelli.add(consolle);

		for (final JPanel pannello : listaPannelli) {
			pannello.setVisible(false);
		}
		tabMovimenti.getTabMovUscite().setVisible(true);

		final MyWindowListener windowListener = new MyWindowListener(this);
		this.addWindowListener(windowListener);
		this.addComponentListener(windowListener);
		this.addWindowFocusListener(windowListener);
		this.addMouseListener(windowListener);

		repaint();
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
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				tabMovimenti.getTabMovUscite().setVisible(true);
			}
		});

		String uscite = I18NManager.getSingleton().getMessaggio("withdrawal");
		final ToggleBtn toggleMovimentiUscite = new ToggleBtn(uscite, iconaMovimentiPic, -1, 20);
		toggleMovimentiUscite.settaggioBottoneStandard();
		final Bottone bottoneMovimentiUscite = new Bottone(toggleMovimentiUscite);
		toggleMovimentiUscite.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				tabMovimenti.getTabMovUscite().setVisible(true);
				toggleMovimentiUscite.setSelected(false);
			}
		});
		String entrate = I18NManager.getSingleton().getMessaggio("income");
		final ToggleBtn toggleMovimentiEntrate = new ToggleBtn(entrate, iconaMovimentiPic, -1, 20);
		toggleMovimentiEntrate.settaggioBottoneStandard();
		final Bottone bottoneMovimentiEntrate = new Bottone(toggleMovimentiEntrate);
		toggleMovimentiEntrate.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				tabMovimenti.getTabMovEntrate().setVisible(true);
				toggleMovimentiEntrate.setSelected(false);
				bottoneMovimentiEntrate.getContenuto().getGruppoBottoni().clearSelection();
				bottoneMovimentiEntrate.getBottone().setSelected(false);
			}
		});

		final PannelloBottoniInterno pp = new PannelloBottoniInterno();
		final ArrayList<Bottone> dueButton = new ArrayList<Bottone>();
		dueButton.add(bottoneMovimentiUscite);
		dueButton.add(bottoneMovimentiEntrate);
		pp.addDueBottoni(dueButton);
		bottoneMovimenti.setContenuto(pp);

		toggleMovimenti.setPadre(bottoneMovimenti);

		final ImageIcon iconaUscite = new ImageIcon(AltreUtil.IMGUTILPATH+"blocktable_32.png");
		String mesi = I18NManager.getSingleton().getMessaggio("months");
		
		final ToggleBtn toggleMesi = new ToggleBtn(mesi, iconaUscite);
		toggleMesi.settaggioBottoneStandard();
		final Bottone bottoneMesi = new Bottone(toggleMesi);
		toggleMesi.setPadre(bottoneMesi);
		toggleMesi.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
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
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				consolle.setVisible(true);
			}
		});

		final ImageIcon iconaSoldi = new ImageIcon(AltreUtil.IMGUTILPATH+"soldi.gif");
		final ImageIcon iconaSoldiPic = new ImageIcon(AltreUtil.IMGUTILPATH+"soldi_pic.gif");

		String addtransaction = I18NManager.getSingleton().getMessaggio("addtransaction");
		final ToggleBtn toggleEntrateUscite = new ToggleBtn(addtransaction, iconaSoldi);
		
		toggleEntrateUscite.settaggioBottoneStandard();
		final Bottone bottoneEntrateUscite = new Bottone(toggleEntrateUscite);
		toggleEntrateUscite.setPadre(bottoneEntrateUscite);

		String charge = I18NManager.getSingleton().getMessaggio("charge");
		final ToggleBtn toggleInsUscite = new ToggleBtn(charge, iconaSoldiPic, -1, 20);
		
		toggleInsUscite.settaggioBottoneStandard();
		final Bottone bottoneInsUscite = new Bottone(toggleInsUscite);
		toggleInsUscite.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final PannelloBottoni pannelloEntrateUscite = bottoneEntrateUscite.getContenuto();
					final UsciteView dialog = new UsciteView(new WrapSingleSpesa());
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setBounds(0, 0, 347, 407);
					dialog.setVisible(true);
					pannelloEntrateUscite.getGruppoBottoni().clearSelection();
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		String income = I18NManager.getSingleton().getMessaggio("income");
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

		final PannelloBottoniInterno EntrateUsciteContenuto = new PannelloBottoniInterno();
		final ArrayList<Bottone> dueBottoni = new ArrayList<Bottone>();
		dueBottoni.add(bottoneInsUscite);
		dueBottoni.add(bottoneInsEntrate);
		EntrateUsciteContenuto.addDueBottoni(dueBottoni);
		bottoneEntrateUscite.setContenuto(EntrateUsciteContenuto);

		pannelloBottoni.addBottone(bottoneSql);
		pannelloBottoni.addBottone(bottoneMesi);
		pannelloBottoni.addBottone(bottoneMovimenti);
		pannelloBottoni.addBottone(bottoneEntrateUscite);

		contentPane.add(pannelloBottoni);
		pannelloBottoni.setBounds(0, 20, this.getWidth(), 94);
	}

	// TODO eliminare if else e rendere generale
	public void relocateFinestreLaterali() {
		if (Controllore.getSingleton().getInitFinestre().getFinestraVisibile() != null) {
			final Point p = GeneralFrame.getSingleton().getLocation();
			final Dimension d = GeneralFrame.getSingleton().getSize();
			p.setLocation(p.x + d.width + 5, p.y);
			try {
				final JFrame finestraVisibile = Controllore.getSingleton().getInitFinestre().getFinestraVisibile();
//				finestraVisibile.setLocation(p);
				 if (finestraVisibile instanceof PannelloAScomparsa2) {
				 ((PannelloAScomparsa2)
				 Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_PANNELLODATI,
				 this)).setLocation(p);
				 } else if (finestraVisibile instanceof FinestraListaComandi)
				 {
				 ((FinestraListaComandi)
				 Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_HISTORY,
				 this)).setLocation(p);
				 } else if (finestraVisibile instanceof MostraNoteView) {
				 ((MostraNoteView)
				 Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_NOTE,
				 this)).setLocation(p);
				 } else if (finestraVisibile instanceof Report) {
				 ((Report)
				 Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_REPORT,
				 this)).setLocation(p);
				 }
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public PerMesiF getTabPermesi() {
		return tabPermesi;
	}

	public void setTabPermesi(final PerMesiF tabPermesi) {
		GeneralFrame.tabPermesi = tabPermesi;
	}

	public Movimenti getTabMovimenti() {
		return tabMovimenti;
	}

	public void setTabMovimenti(final Movimenti tabMovimenti) {
		GeneralFrame.tabMovimenti = tabMovimenti;
	}

	public NewSql getConsolle() {
		return consolle;
	}

	public void setConsolle(final NewSql consolle) {
		GeneralFrame.consolle = consolle;
	}

}
