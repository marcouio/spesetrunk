package view.mymenu;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import view.FinestraListaComandi;
import view.Report;
import view.componenti.componentiPannello.PannelloAScomparsa2;
import view.entrateuscite.EntrateView;
import view.entrateuscite.UsciteView;
import view.grafici.dialogGraph.GrEntrate1;
import view.grafici.dialogGraph.GrEntrate2;
import view.grafici.dialogGraph.GrGenerale;
import view.grafici.dialogGraph.GrGenerale2;
import view.grafici.dialogGraph.GrUscite1;
import view.grafici.dialogGraph.GrUscite2;
import view.impostazioni.CategorieView;
import view.impostazioni.Impostazioni;
import view.impostazioni.SettingGruppi;
import view.note.MostraNoteView;
import business.AltreUtil;
import business.Controllore;
import business.ascoltatoriMenu.AscoltatoreAvanti;
import business.ascoltatoriMenu.AscoltatoreCaricaDatabase;
import business.ascoltatoriMenu.AscoltatoreIndietro;
import business.ascoltatoriMenu.AscoltatoreInfo;
import business.ascoltatoriMenu.AscoltatoreLogin;
import business.ascoltatoriMenu.AscoltatoreRegistrazione;
import domain.wrapper.WrapCatSpese;
import domain.wrapper.WrapEntrate;
import domain.wrapper.WrapSingleSpesa;

public class MyMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public MyMenu() {
		init();
	}

	private void init() {
		this.setBounds(0, 0, 1000, 20);

		// crea un menu
		JMenu file = new JMenu("File");
		this.add(file);

		// item di un menu
		JMenuItem menuItem = new JMenuItem("Altro database");
		ActionListener ascolto = new AscoltatoreCaricaDatabase();
		menuItem.addActionListener(ascolto);
		file.add(menuItem);

		// item Login
		JMenuItem menuItem2 = new JMenuItem("Login");
		ActionListener login = new AscoltatoreLogin();
		menuItem2.addActionListener(login);
		file.add(menuItem2);

		// item Login
		JMenuItem registra = new JMenuItem("Registrazione");
		ActionListener registrazione = new AscoltatoreRegistrazione();
		registra.addActionListener(registrazione);
		file.add(registra);

		JMenuItem chiudi = new JMenuItem("Chiudi");
		chiudi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		file.add(chiudi);

		JMenu modifica = new JMenu("Modifica");
		add(modifica);

		JMenuItem indietro = new JMenuItem("Indietro");
		indietro.addActionListener(new AscoltatoreIndietro());
		modifica.add(indietro);

		JMenuItem avanti = new JMenuItem("Avanti");
		avanti.addActionListener(new AscoltatoreAvanti());
		modifica.add(avanti);

		final JMenu finestre = new JMenu("Finestre");
		add(finestre);

		final JCheckBoxMenuItem listaComandi = new JCheckBoxMenuItem("Comandi");
		finestre.add(listaComandi);

		final JCheckBoxMenuItem mntmReport = new JCheckBoxMenuItem("Report");
		finestre.add(mntmReport);

		final JCheckBoxMenuItem chckbxmntmDati = new JCheckBoxMenuItem("Riepilogo dati");
		chckbxmntmDati.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PannelloAScomparsa2 pas = Controllore.getPannelloDati();
				Controllore.setVisibilitaFinestre(pas, finestre, chckbxmntmDati);
			}
		});
		finestre.add(chckbxmntmDati);

		final JCheckBoxMenuItem mntmNote = new JCheckBoxMenuItem("Note");
		finestre.add(mntmNote);
		mntmNote.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MostraNoteView note = Controllore.getNote();
				Controllore.setVisibilitaFinestre(note, finestre, mntmNote);
			}
		});
		mntmReport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Report report = Controllore.getReport();
				Controllore.setVisibilitaFinestre(report, finestre, mntmReport);
			}
		});
		listaComandi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FinestraListaComandi history = Controllore.getFinestraHistory();
				Controllore.setVisibilitaFinestre(history, finestre, listaComandi);
			}
		});

		JMenu mnStrumenti = new JMenu("Strumenti");
		add(mnStrumenti);

		JMenu mnImpostazioni = new JMenu("Impostazioni");
		mnStrumenti.add(mnImpostazioni);

		JMenuItem mntmConfigurazione = new JMenuItem("Configurazioni");
		mntmConfigurazione.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final Impostazioni dialog = new Impostazioni();
				dialog.pack();
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});
		mnImpostazioni.add(mntmConfigurazione);

		JMenuItem mntmCategorie = new JMenuItem("Categorie");
		mntmCategorie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final CategorieView dialog2 = new CategorieView(new
				                WrapCatSpese());
				dialog2.pack();
				dialog2.setVisible(true);
				dialog2.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});
		mnImpostazioni.add(mntmCategorie);

		JMenuItem mntmGr = new JMenuItem("Gruppi");
		mntmGr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final SettingGruppi dialog = new SettingGruppi();
				dialog.pack();
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});
		mnImpostazioni.add(mntmGr);

		JMenu mnGrafici = new JMenu("Grafici");
		mnStrumenti.add(mnGrafici);

		JMenu mnEntrate = new JMenu("Entrate");
		mnGrafici.add(mnEntrate);

		JMenuItem mntmEntratePerTipo = new JMenuItem("Per tipo");
		mnEntrate.add(mntmEntratePerTipo);

		JMenuItem mntmEntrateMensili = new JMenuItem("Mensili");
		mnEntrate.add(mntmEntrateMensili);

		JMenu mnUscite = new JMenu("Uscite");
		mnGrafici.add(mnUscite);

		JMenuItem mntmMensiliPerCategoria = new JMenuItem("Mensili per categoria");
		mnUscite.add(mntmMensiliPerCategoria);

		JMenuItem mntmPerCategorie = new JMenuItem("Per categorie");
		mntmPerCategorie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GrUscite1 dialog = new GrUscite1();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnUscite.add(mntmPerCategorie);

		JMenuItem mntmPerMesi = new JMenuItem("Per mesi");
		mntmPerMesi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GrUscite2 dialog = new GrUscite2();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		mnUscite.add(mntmPerMesi);
		mntmMensiliPerCategoria.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AltreUtil.deleteFileDaDirectory2("./immagini/");
				final GrGenerale dialog = new GrGenerale();
				dialog.setSize(700, 700);
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});

		JMenu mnTotali = new JMenu("Totali");
		mnGrafici.add(mnTotali);

		JMenuItem mntmSaldo = new JMenuItem("Saldo");
		mntmSaldo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GrGenerale2 dialog = new GrGenerale2();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setSize(700, 700);
					dialog.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnTotali.add(mntmSaldo);

		JMenu mnDati = new JMenu("Inserimento dati");
		mnStrumenti.add(mnDati);

		JMenuItem mntmEntrate = new JMenuItem("Entrate");
		mntmEntrate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EntrateView dialog = new EntrateView(new WrapEntrate());
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(0, 0, 347, 318);
				dialog.setVisible(true);
			}
		});
		mnDati.add(mntmEntrate);

		JMenuItem mntmUscite = new JMenuItem("Uscite");
		mntmUscite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UsciteView dialog = new UsciteView(new WrapSingleSpesa());
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setBounds(0, 0, 347, 407);
					dialog.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnDati.add(mntmUscite);
		mntmEntrateMensili.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GrEntrate2 dialog = new GrEntrate2();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		mntmEntratePerTipo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame();
				try {
					AltreUtil.deleteFileDaDirectory2("./immagini/");
					final GrEntrate1 dialog = new GrEntrate1(f, null, true);
					dialog.setSize(700, 700);
					dialog.setVisible(true);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					f.dispose();
				}
			}
		});

		JMenu help = new JMenu("Help");
		add(help);

		JMenuItem info = new JMenuItem("Info");
		info.addActionListener(new AscoltatoreInfo());
		help.add(info);

		JMenuItem manuale = new JMenuItem("Manuale");
		help.add(manuale);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setSize(1000, 50);
				frame.getContentPane().add(new MyMenu());
				frame.setVisible(true);
			}
		});
	}

}
