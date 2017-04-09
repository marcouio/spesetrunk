package com.molinari.gestionespese.view.mymenu;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.business.InizializzatoreFinestre;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.menulistener.AscoltatoreAvanti;
import com.molinari.gestionespese.business.menulistener.AscoltatoreCaricaDatabase;
import com.molinari.gestionespese.business.menulistener.AscoltatoreCreaDialog;
import com.molinari.gestionespese.business.menulistener.AscoltatoreIndietro;
import com.molinari.gestionespese.business.menulistener.AscoltatoreInfo;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.gestionespese.view.GeneralFrame;
import com.molinari.gestionespese.view.entrateuscite.EntrateView;
import com.molinari.gestionespese.view.entrateuscite.UsciteView;
import com.molinari.gestionespese.view.grafici.dialog.GrEntrate1;
import com.molinari.gestionespese.view.grafici.dialog.GrEntrate2;
import com.molinari.gestionespese.view.grafici.dialog.GrGenerale;
import com.molinari.gestionespese.view.grafici.dialog.GrGenerale2;
import com.molinari.gestionespese.view.grafici.dialog.GrUscite1;
import com.molinari.gestionespese.view.grafici.dialog.GrUscite2;
import com.molinari.gestionespese.view.impostazioni.CategorieView;
import com.molinari.gestionespese.view.impostazioni.GruppiView;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;
import com.molinari.gestionespese.view.login.Login;
import com.molinari.gestionespese.view.login.Registrazione;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.messages.I18NManager;

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
		final ActionListener login = new AscoltatoreCreaDialog(new Login().getDialog(), 380, 260);
		menuItem2.addActionListener(login);
		file.add(menuItem2);

		// item Login
		final JMenuItem registra = new JMenuItem(I18NManager.getSingleton().getMessaggio("register"));
		final ActionListener registrazione = new AscoltatoreCreaDialog(new Registrazione());
		registra.addActionListener(registrazione);
		file.add(registra);

		final JMenuItem chiudi = new JMenuItem(I18NManager.getSingleton().getMessaggio("close"));
		chiudi.addActionListener(getAscoltatoreClose());
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
		chckbxmntmDati.addActionListener(getAscoltatoreSummary(finestre, chckbxmntmDati));
		finestre.add(chckbxmntmDati);

		final JCheckBoxMenuItem mntmNote = new JCheckBoxMenuItem(I18NManager.getSingleton().getMessaggio("notes"));
		finestre.add(mntmNote);
		mntmNote.addActionListener(getListenerNote(finestre, mntmNote));
		mntmReport.addActionListener(getListenerReport(finestre, mntmReport));
		listaComandi.addActionListener(getListenerComandi(finestre, listaComandi));

		final JMenu mnStrumenti = new JMenu(I18NManager.getSingleton().getMessaggio("tools"));
		add(mnStrumenti);

		final JMenu mnImpostazioni = new JMenu(I18NManager.getSingleton().getMessaggio("options"));
		mnStrumenti.add(mnImpostazioni);

		final JMenuItem mntmConfigurazione = new JMenuItem(I18NManager.getSingleton().getMessaggio("config"));
		mntmConfigurazione.addActionListener(getListenerConfig());
		mnImpostazioni.add(mntmConfigurazione);

		final JMenuItem mntmCategorie = new JMenuItem(I18NManager.getSingleton().getMessaggio("categories"));
		mntmCategorie.addActionListener(getListenerCategories());
		mnImpostazioni.add(mntmCategorie);

		final JMenuItem mntmGr = new JMenuItem(I18NManager.getSingleton().getMessaggio("groups"));
		mntmGr.addActionListener(getListenerGroups());
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
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
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
					ControlloreBase.getLog().log(Level.SEVERE, ex.getMessage(), ex);
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
		mntmSaldo.addActionListener(getListenerBalance());
		mnTotali.add(mntmSaldo);

		final JMenu mnDati = new JMenu(I18NManager.getSingleton().getMessaggio("dataentry"));
		mnStrumenti.add(mnDati);

		final JMenuItem mntmEntrate = new JMenuItem(I18NManager.getSingleton().getMessaggio("entries"));
		mntmEntrate.addActionListener(getListenerEntries());
		mnDati.add(mntmEntrate);

		final JMenuItem mntmUscite = new JMenuItem(I18NManager.getSingleton().getMessaggio("charge"));
		mntmUscite.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final UsciteView dialog = new UsciteView(new WrapSingleSpesa());
					dialog.getDialog().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.getDialog().setBounds(0, 0, 347, 407);
					dialog.getDialog().setVisible(true);
				} catch (final Exception e1) {
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
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
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
				}

			}
		});
		mntmEntratePerTipo.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final JFrame f = new JFrame();
				try {
					AltreUtil.deleteFileDaDirectory2("./immagini/");
					final GrEntrate1 dialog = new GrEntrate1();
					dialog.setSize(700, 700);
					dialog.setVisible(true);
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				} catch (final Exception e1) {
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
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

	private AscoltatoreAggiornatoreNiente getListenerBalance() {
		return new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final GrGenerale2 dialog = new GrGenerale2();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setSize(700, 700);
					dialog.setVisible(true);
				} catch (final Exception e1) {
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
				}
			}
		};
	}

	private AscoltatoreAggiornatoreNiente getListenerEntries() {
		return new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final EntrateView dialog = new EntrateView(new WrapEntrate());
				dialog.getDialog().setLocationRelativeTo(null);
				dialog.getDialog().setBounds(0, 0, 347, 318);
				dialog.getDialog().setVisible(true);
			}
		};
	}

	private AscoltatoreAggiornatoreNiente getListenerGroups() {
		return new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final GruppiView dialog = new GruppiView(new WrapGruppi());
				dialog.getDialog().pack();
				dialog.getDialog().setVisible(true);
				dialog.getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
			}
		};
	}

	private AscoltatoreAggiornatoreNiente getListenerCategories() {
		return new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final CategorieView dialog2 = new CategorieView(new WrapCatSpese());
				dialog2.getDialog().pack();
				dialog2.getDialog().setVisible(true);
				dialog2.getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
			}
		};
	}

	private AscoltatoreAggiornatoreNiente getListenerConfig() {
		return new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final Impostazioni dialog = new Impostazioni();
				dialog.pack();
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		};
	}

	private AscoltatoreAggiornatoreNiente getListenerComandi(final JMenu finestre,
			final JCheckBoxMenuItem listaComandi) {
		return new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				Finestra history;
				try {
					GeneralFrame generalFrame = Controllore.getGeneralFrame();
					history = generalFrame.getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_HISTORY, generalFrame);
					generalFrame.getInitFinestre().setVisibilitaFinestre(history, finestre, listaComandi);
				} catch (final Exception e1) {
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
				}
			}
		};
	}

	private AscoltatoreAggiornatoreNiente getListenerReport(final JMenu finestre, final JCheckBoxMenuItem mntmReport) {
		return new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					GeneralFrame generalFrame = Controllore.getGeneralFrame();
					Finestra report = generalFrame.getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_REPORT, generalFrame);
					generalFrame.getInitFinestre().setVisibilitaFinestre(report, finestre, mntmReport);
				} catch (final Exception e1) {
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
				}
			}
		};
	}

	private AscoltatoreAggiornatoreNiente getListenerNote(final JMenu finestre, final JCheckBoxMenuItem mntmNote) {
		return new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				Finestra note;
				try {
					GeneralFrame generalFrame = Controllore.getGeneralFrame();
					note = generalFrame.getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_NOTE, generalFrame);
					generalFrame.getInitFinestre().setVisibilitaFinestre(note, finestre, mntmNote);
				} catch (final Exception e1) {
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
				}
			}
		};
	}

	private AscoltatoreAggiornatoreNiente getAscoltatoreSummary(final JMenu finestre,
			final JCheckBoxMenuItem chckbxmntmDati) {
		return new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				Finestra pas;
				try {
					GeneralFrame generalFrame = Controllore.getGeneralFrame();
					pas = generalFrame.getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_PANNELLODATI, generalFrame);
					generalFrame.getInitFinestre().setVisibilitaFinestre(pas, finestre, chckbxmntmDati);
				} catch (final Exception e1) {
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
				}
			}
		};
	}

	private AscoltatoreAggiornatoreNiente getAscoltatoreClose() {
		return new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				ControlloreBase.getSingleton().getApplicationframe().dispose();

			}
		};
	}

}
