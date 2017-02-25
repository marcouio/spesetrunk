package com.molinari.gestionespese.view.mymenu;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.InizializzatoreFinestre;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.ascoltatoriMenu.AscoltatoreAvanti;
import com.molinari.gestionespese.business.ascoltatoriMenu.AscoltatoreCaricaDatabase;
import com.molinari.gestionespese.business.ascoltatoriMenu.AscoltatoreCreaDialog;
import com.molinari.gestionespese.business.ascoltatoriMenu.AscoltatoreIndietro;
import com.molinari.gestionespese.business.ascoltatoriMenu.AscoltatoreInfo;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.gestionespese.view.FinestraListaComandi;
import com.molinari.gestionespese.view.componenti.componentiPannello.PannelloAScomparsa;
import com.molinari.gestionespese.view.entrateuscite.EntrateView;
import com.molinari.gestionespese.view.entrateuscite.UsciteView;
import com.molinari.gestionespese.view.grafici.dialogGraph.GrEntrate1;
import com.molinari.gestionespese.view.grafici.dialogGraph.GrEntrate2;
import com.molinari.gestionespese.view.grafici.dialogGraph.GrGenerale;
import com.molinari.gestionespese.view.grafici.dialogGraph.GrGenerale2;
import com.molinari.gestionespese.view.grafici.dialogGraph.GrUscite1;
import com.molinari.gestionespese.view.grafici.dialogGraph.GrUscite2;
import com.molinari.gestionespese.view.impostazioni.CategorieView;
import com.molinari.gestionespese.view.impostazioni.GruppiView;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;
import com.molinari.gestionespese.view.login.Login;
import com.molinari.gestionespese.view.login.Registrazione;
import com.molinari.gestionespese.view.note.MostraNoteView;
import com.molinari.gestionespese.view.report.ReportView;

public class MyMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public MyMenu() {
		init();
	}

	private void init() {
		// crea un menu
		final JMenu file = new JMenu("File");
		this.add(file);

		// item di un menu
		final JMenuItem menuItem = new JMenuItem(I18NManager.getSingleton().getMessaggio("otherdatabase"));
		final ActionListener ascolto = new AscoltatoreCaricaDatabase();
		menuItem.addActionListener(ascolto);
		file.add(menuItem);

		// item Login
		final JMenuItem menuItem2 = new JMenuItem("Login");
		final ActionListener login = new AscoltatoreCreaDialog(new Login(), 380, 260);
		menuItem2.addActionListener(login);
		file.add(menuItem2);

		// item Login
		final JMenuItem registra = new JMenuItem(I18NManager.getSingleton().getMessaggio("register"));
		final ActionListener registrazione = new AscoltatoreCreaDialog(new Registrazione());
		registra.addActionListener(registrazione);
		file.add(registra);

		final JMenuItem chiudi = new JMenuItem(I18NManager.getSingleton().getMessaggio("close"));
		chiudi.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				System.exit(0);

			}
		});
		file.add(chiudi);

		final JMenu modifica = new JMenu(I18NManager.getSingleton().getMessaggio("edit"));
		add(modifica);

		final JMenuItem indietro = new JMenuItem(I18NManager.getSingleton().getMessaggio("undo"));
		indietro.addActionListener(new AscoltatoreIndietro());
		modifica.add(indietro);

		final JMenuItem avanti = new JMenuItem(I18NManager.getSingleton().getMessaggio("redo"));
		avanti.addActionListener(new AscoltatoreAvanti());
		modifica.add(avanti);

		final JMenu finestre = new JMenu(I18NManager.getSingleton().getMessaggio("windows"));
		add(finestre);

		final JCheckBoxMenuItem listaComandi = new JCheckBoxMenuItem(I18NManager.getSingleton().getMessaggio("commands"));
		finestre.add(listaComandi);

		final JCheckBoxMenuItem mntmReport = new JCheckBoxMenuItem(I18NManager.getSingleton().getMessaggio("report"));
		finestre.add(mntmReport);

		final JCheckBoxMenuItem chckbxmntmDati = new JCheckBoxMenuItem(I18NManager.getSingleton().getMessaggio("summarydata"));
		chckbxmntmDati.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				PannelloAScomparsa pas;
				try {
					pas = ((PannelloAScomparsa) Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_PANNELLODATI, null));
					Controllore.getSingleton().getInitFinestre().setVisibilitaFinestre(pas, finestre, chckbxmntmDati);
					Controllore.getSingleton().getGeneralFrame().relocateFinestreLaterali();
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		finestre.add(chckbxmntmDati);

		final JCheckBoxMenuItem mntmNote = new JCheckBoxMenuItem(I18NManager.getSingleton().getMessaggio("notes"));
		finestre.add(mntmNote);
		mntmNote.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				MostraNoteView note;
				try {
					note = ((MostraNoteView) Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_NOTE, null));
					Controllore.getSingleton().getInitFinestre().setVisibilitaFinestre(note, finestre, mntmNote);
					Controllore.getSingleton().getGeneralFrame().relocateFinestreLaterali();
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mntmReport.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				ReportView report;
				try {
					report = ((ReportView) Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_REPORT, null));
					Controllore.getSingleton().getInitFinestre().setVisibilitaFinestre(report, finestre, mntmReport);
					Controllore.getSingleton().getGeneralFrame().relocateFinestreLaterali();
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		listaComandi.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				FinestraListaComandi history;
				try {
					history = ((FinestraListaComandi) Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_HISTORY, null));
					Controllore.getSingleton().getInitFinestre().setVisibilitaFinestre(history, finestre, listaComandi);
					Controllore.getSingleton().getGeneralFrame().relocateFinestreLaterali();
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		final JMenu mnStrumenti = new JMenu(I18NManager.getSingleton().getMessaggio("tools"));
		add(mnStrumenti);

		final JMenu mnImpostazioni = new JMenu(I18NManager.getSingleton().getMessaggio("options"));
		mnStrumenti.add(mnImpostazioni);

		final JMenuItem mntmConfigurazione = new JMenuItem(I18NManager.getSingleton().getMessaggio("config"));
		mntmConfigurazione.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final Impostazioni dialog = new Impostazioni();
				dialog.pack();
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});
		mnImpostazioni.add(mntmConfigurazione);

		final JMenuItem mntmCategorie = new JMenuItem(I18NManager.getSingleton().getMessaggio("categories"));
		mntmCategorie.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final CategorieView dialog2 = new CategorieView(new WrapCatSpese());
				dialog2.pack();
				dialog2.setVisible(true);
				dialog2.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});
		mnImpostazioni.add(mntmCategorie);

		final JMenuItem mntmGr = new JMenuItem(I18NManager.getSingleton().getMessaggio("groups"));
		mntmGr.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final GruppiView dialog = new GruppiView(new WrapGruppi());
				dialog.pack();
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});
		mnImpostazioni.add(mntmGr);

		final JMenu mnGrafici = new JMenu(I18NManager.getSingleton().getMessaggio("charts"));
		mnStrumenti.add(mnGrafici);

		final JMenu mnEntrate = new JMenu(I18NManager.getSingleton().getMessaggio("entries"));
		mnGrafici.add(mnEntrate);

		final JMenuItem mntmEntratePerTipo = new JMenuItem(I18NManager.getSingleton().getMessaggio("fortype"));
		mnEntrate.add(mntmEntratePerTipo);

		final JMenuItem mntmEntrateMensili = new JMenuItem(I18NManager.getSingleton().getMessaggio("monthly"));
		mnEntrate.add(mntmEntrateMensili);

		final JMenu mnUscite = new JMenu(I18NManager.getSingleton().getMessaggio("charge"));
		mnGrafici.add(mnUscite);

		final JMenuItem mntmMensiliPerCategoria = new JMenuItem(I18NManager.getSingleton().getMessaggio("monthlycat"));
		mnUscite.add(mntmMensiliPerCategoria);

		final JMenuItem mntmPerCategorie = new JMenuItem(I18NManager.getSingleton().getMessaggio("forcategories"));
		mntmPerCategorie.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {	
					final GrUscite1 dialog = new GrUscite1();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnUscite.add(mntmPerCategorie);

		final JMenuItem mntmPerMesi = new JMenuItem(I18NManager.getSingleton().getMessaggio("formonthly"));
		mntmPerMesi.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final GrUscite2 dialog = new GrUscite2();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (final Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		mnUscite.add(mntmPerMesi);
		mntmMensiliPerCategoria.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				AltreUtil.deleteFileDaDirectory2("./immagini/");
				final GrGenerale dialog = new GrGenerale();
				dialog.setSize(700, 700);
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});

		final JMenu mnTotali = new JMenu(I18NManager.getSingleton().getMessaggio("totals"));
		mnGrafici.add(mnTotali);

		final JMenuItem mntmSaldo = new JMenuItem(I18NManager.getSingleton().getMessaggio("balance"));
		mntmSaldo.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final GrGenerale2 dialog = new GrGenerale2();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setSize(700, 700);
					dialog.setVisible(true);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnTotali.add(mntmSaldo);

		final JMenu mnDati = new JMenu(I18NManager.getSingleton().getMessaggio("dataentry"));
		mnStrumenti.add(mnDati);

		final JMenuItem mntmEntrate = new JMenuItem(I18NManager.getSingleton().getMessaggio("entries"));
		mntmEntrate.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final EntrateView dialog = new EntrateView(new WrapEntrate());
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(0, 0, 347, 318);
				dialog.setVisible(true);
			}
		});
		mnDati.add(mntmEntrate);

		final JMenuItem mntmUscite = new JMenuItem(I18NManager.getSingleton().getMessaggio("charge"));
		mntmUscite.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final UsciteView dialog = new UsciteView(new WrapSingleSpesa());
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setBounds(0, 0, 347, 407);
					dialog.setVisible(true);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnDati.add(mntmUscite);
		mntmEntrateMensili.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final GrEntrate2 dialog = new GrEntrate2();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		mntmEntratePerTipo.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final JFrame f = new JFrame();
				try {
					AltreUtil.deleteFileDaDirectory2("./immagini/");
					final GrEntrate1 dialog = new GrEntrate1(f, null, true);
					dialog.setSize(700, 700);
					dialog.setVisible(true);
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				} catch (final Exception e1) {
					e1.printStackTrace();
				} finally {
					f.dispose();
				}
			}
		});

		final JMenu help = new JMenu("Help");
		add(help);

		final JMenuItem info = new JMenuItem("Info");
		info.addActionListener(new AscoltatoreInfo());
		help.add(info);

		final JMenuItem manuale = new JMenuItem(I18NManager.getSingleton().getMessaggio("userguide"));
		help.add(manuale);

	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				final JFrame frame = new JFrame();
				frame.setSize(1000, 50);
				frame.getContentPane().add(new MyMenu());
				frame.setVisible(true);
			}
		});
	}

}
