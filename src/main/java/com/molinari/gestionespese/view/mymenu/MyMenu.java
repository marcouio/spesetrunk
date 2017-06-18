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
	private JMenuItem itemOtherDb;
	private JMenuItem itemRegistra;
	private JMenuItem itemChiudi;
	private JMenu menuModifica;
	private JMenuItem itemIndietro;
	private JMenuItem itemAvanti;
	private JMenu menuFinestre;
	private JCheckBoxMenuItem mntmNote;
	private JMenu menuStrumenti;
	private JMenu menuImpostazioni;
	private JMenuItem itemConfigurazione;
	private JMenuItem itemCategorie;
	private JMenuItem itemGrs;
	private JMenu menuGrafici;
	private JMenu menuEntrate;
	private JMenuItem itemEntratePerTipo;
	private JMenuItem itemEntrateMensili;
	private JMenu menuUscite;
	private JMenuItem itemMensiliPerCategoria;
	private JMenuItem itemPerCategorie;
	private JMenuItem itemManuale;
	private JMenuItem itemInfo;
	private JMenuItem itemUscite;
	private JMenuItem itemEntrate;
	private JMenu menuDati;
	private JMenuItem itemSaldo;
	private JMenu menuTotali;
	private JMenuItem itemPerMesi;

	public MyMenu() {
		init();
	}

	private void init() {
		// crea un menu
		final JMenu file = new JMenu("File");
		this.add(file);

		// item di un menu
		itemOtherDb = new JMenuItem();
		final ActionListener ascolto = new AscoltatoreCaricaDatabase();
		itemOtherDb.addActionListener(ascolto);
		
		file.add(itemOtherDb);
		

		// item Login
		final JMenuItem itemLogin = new JMenuItem("Login");
		final ActionListener login = new AscoltatoreCreaDialog(new Login().getDialog(), 380, 260);
		itemLogin.addActionListener(login);
		file.add(itemLogin);

		// item Login
		itemRegistra = new JMenuItem();
		final ActionListener registrazione = new AscoltatoreCreaDialog(new Registrazione());
		itemRegistra.addActionListener(registrazione);
		file.add(itemRegistra);

		itemChiudi = new JMenuItem();
		itemChiudi.addActionListener(getAscoltatoreClose());
		file.add(itemChiudi);


		menuModifica = new JMenu();
		add(menuModifica);


		itemIndietro = new JMenuItem();
		itemIndietro.addActionListener(new AscoltatoreIndietro());
		menuModifica.add(itemIndietro);


		itemAvanti = new JMenuItem();
		itemAvanti.addActionListener(new AscoltatoreAvanti());
		menuModifica.add(itemAvanti);

		

		menuFinestre = new JMenu();
		add(menuFinestre);

		final JCheckBoxMenuItem listaComandi = new JCheckBoxMenuItem(I18NManager.getSingleton().getMessaggio("commands"));
		menuFinestre.add(listaComandi);

		final JCheckBoxMenuItem mntmReport = new JCheckBoxMenuItem(I18NManager.getSingleton().getMessaggio("report"));
		menuFinestre.add(mntmReport);

		final JCheckBoxMenuItem chckbxmntmDati = new JCheckBoxMenuItem(I18NManager.getSingleton().getMessaggio("summarydata"));
		chckbxmntmDati.addActionListener(getAscoltatoreSummary(menuFinestre, chckbxmntmDati));
		menuFinestre.add(chckbxmntmDati);

		mntmNote = new JCheckBoxMenuItem();
		menuFinestre.add(mntmNote);
		mntmNote.addActionListener(getListenerNote(menuFinestre, mntmNote));
		mntmReport.addActionListener(getListenerReport(menuFinestre, mntmReport));
		listaComandi.addActionListener(getListenerComandi(menuFinestre, listaComandi));

		menuStrumenti = new JMenu();
		add(menuStrumenti);
		
		menuImpostazioni = new JMenu();
		menuStrumenti.add(menuImpostazioni);

		itemConfigurazione = new JMenuItem();
		itemConfigurazione.addActionListener(getListenerConfig());
		menuImpostazioni.add(itemConfigurazione);

		itemCategorie = new JMenuItem();
		itemCategorie.addActionListener(getListenerCategories());
		menuImpostazioni.add(itemCategorie);

		itemGrs = new JMenuItem();
		itemGrs.addActionListener(getListenerGroups());
		menuImpostazioni.add(itemGrs);

		menuGrafici = new JMenu();
		menuStrumenti.add(menuGrafici);

		menuEntrate = new JMenu();
		menuGrafici.add(menuEntrate);

		itemEntratePerTipo = new JMenuItem();
		menuEntrate.add(itemEntratePerTipo);

		itemEntrateMensili = new JMenuItem();
		menuEntrate.add(itemEntrateMensili);


		menuUscite = new JMenu();
		menuGrafici.add(menuUscite);

		itemMensiliPerCategoria = new JMenuItem();
		menuUscite.add(itemMensiliPerCategoria);
		
		itemPerCategorie = new JMenuItem();
		itemPerCategorie.addActionListener(new AscoltatoreAggiornatoreNiente() {
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
		menuUscite.add(itemPerCategorie);

		itemPerMesi = new JMenuItem();
		itemPerMesi.addActionListener(new AscoltatoreAggiornatoreNiente() {
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
		menuUscite.add(itemPerMesi);
		itemMensiliPerCategoria.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				AltreUtil.deleteFileDaDirectory2("./immagini/");
				final GrGenerale dialog = new GrGenerale();
				dialog.setSize(700, 700);
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});

		menuTotali = new JMenu();
		menuGrafici.add(menuTotali);

		itemSaldo = new JMenuItem();
		itemSaldo.addActionListener(getListenerBalance());
		menuTotali.add(itemSaldo);

		menuDati = new JMenu();
		menuStrumenti.add(menuDati);

		itemEntrate = new JMenuItem();
		itemEntrate.addActionListener(getListenerEntries());
		menuDati.add(itemEntrate);
		
		itemUscite = new JMenuItem();
		itemUscite.addActionListener(new AscoltatoreAggiornatoreNiente() {
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
		menuDati.add(itemUscite);
		itemEntrateMensili.addActionListener(new AscoltatoreAggiornatoreNiente() {

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
		itemEntratePerTipo.addActionListener(new AscoltatoreAggiornatoreNiente() {
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

		itemInfo = new JMenuItem("Info");
		itemInfo.addActionListener(new AscoltatoreInfo());
		help.add(itemInfo);

		itemManuale = new JMenuItem();
		help.add(itemManuale);

		addVoices();
		
	}

	public void addVoices() {
		itemEntrate.setText(I18NManager.getSingleton().getMessaggio("entries"));
		menuDati.setText(I18NManager.getSingleton().getMessaggio("dataentry"));
		itemSaldo.setText(I18NManager.getSingleton().getMessaggio("balance"));
		itemPerMesi.setText(I18NManager.getSingleton().getMessaggio("formonthly"));
		menuTotali.setText(I18NManager.getSingleton().getMessaggio("totals"));
		itemUscite.setText(I18NManager.getSingleton().getMessaggio("charge"));
		itemManuale.setText(I18NManager.getSingleton().getMessaggio("userguide"));
		itemMensiliPerCategoria.setText(I18NManager.getSingleton().getMessaggio("monthlycat"));
		itemPerCategorie.setText(I18NManager.getSingleton().getMessaggio("forcategories"));
		itemOtherDb.setText(I18NManager.getSingleton().getMessaggio("otherdatabase"));
		itemRegistra.setText(I18NManager.getSingleton().getMessaggio("register"));
		itemChiudi.setText(I18NManager.getSingleton().getMessaggio("close"));
		menuModifica.setText(I18NManager.getSingleton().getMessaggio("edit"));
		itemIndietro.setText(I18NManager.getSingleton().getMessaggio("undo"));
		itemAvanti.setText(I18NManager.getSingleton().getMessaggio("redo"));
		menuFinestre.setText(I18NManager.getSingleton().getMessaggio("windows"));
		menuStrumenti.setText(I18NManager.getSingleton().getMessaggio("tools"));
		mntmNote.setText(I18NManager.getSingleton().getMessaggio("notes"));
		menuImpostazioni.setText(I18NManager.getSingleton().getMessaggio("options"));
		itemConfigurazione.setText(I18NManager.getSingleton().getMessaggio("config"));
		itemCategorie.setText(I18NManager.getSingleton().getMessaggio("categories"));
		itemGrs.setText(I18NManager.getSingleton().getMessaggio("groups"));
		menuGrafici.setText(I18NManager.getSingleton().getMessaggio("charts"));
		menuEntrate.setText(I18NManager.getSingleton().getMessaggio("entries"));
		itemEntratePerTipo.setText(I18NManager.getSingleton().getMessaggio("fortype"));
		itemEntrateMensili.setText(I18NManager.getSingleton().getMessaggio("monthly"));
		menuUscite.setText(I18NManager.getSingleton().getMessaggio("charge"));

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
