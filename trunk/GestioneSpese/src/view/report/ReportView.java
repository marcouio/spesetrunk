package view.report;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import view.font.ButtonF;
import view.font.CheckBoxF;
import view.font.LabelListaGruppi;
import business.AltreUtil;
import business.Controllore;
import business.DBUtil;
import business.Database;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import business.cache.CacheCategorie;
import business.internazionalizzazione.I18NManager;
import domain.CatSpese;

public class ReportView extends AbstractReportView {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private void settaValoriReportDati(final JCheckBox chckbxSpeseVariabili_1,
			final JCheckBox chckbxEntrateMensCategorie, final JCheckBox chckbxSpeseMensCat,
			final JCheckBox chckbxEntratePerCategorie, final JCheckBox chckbxSpesePerCategorie,
			final JCheckBox chckbxUsciteMensili, final JCheckBox chckbxEntrateMensili,
			final JCheckBox chckbxUsciteAnnuali, final JCheckBox chckbxEntrateAnnuali,
			final JCheckBox chckbxSpeseFutili_1, final JCheckBox chckbxAvanzo, final JCheckBox chckbxMedie) {

		setUsciteVariabili(chckbxSpeseVariabili_1.isSelected());
		setEntrateCatMensili(chckbxEntrateMensCategorie.isSelected());
		setUsciteCatMensili(chckbxSpeseMensCat.isSelected());
		setEntrateCatAnnuali(chckbxEntratePerCategorie.isSelected());
		setUsciteCatAnnuali(chckbxSpesePerCategorie.isSelected());
		setUsciteMensili(chckbxUsciteMensili.isSelected());
		setEntrateMensili(chckbxEntrateMensili.isSelected());
		setUsciteAnnuali(chckbxUsciteAnnuali.isSelected());
		setEntrateAnnuali(chckbxEntrateAnnuali.isSelected());
		setUsciteFutili(chckbxSpeseFutili_1.isSelected());
		setAvanzo(chckbxAvanzo.isSelected());
		setMediaEntrate(chckbxMedie.isSelected());
		setMediaUscite(chckbxMedie.isSelected());
	}

	/**
	 * Create the panel
	 * 
	 * @throws FileNotFoundException
	 */
	public ReportView() throws FileNotFoundException {
		setReportData(new ReportData());
		getContentPane().setLayout(null);
		this.setTitle("Report");
		this.setSize(250, 425);
		final JLabel Istruzioni = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("selectreport"));
		Istruzioni.setText(I18NManager.getSingleton().getMessaggio("select") + ":");
		Istruzioni.setBounds(12, 12, 207, 20);
		getContentPane().add(Istruzioni);

		final JCheckBox chckbxEntrateAnnuali = new CheckBoxF(I18NManager.getSingleton().getMessaggio("yearincome"));
		chckbxEntrateAnnuali.setBounds(22, 40, 197, 23);
		getContentPane().add(chckbxEntrateAnnuali);

		final JCheckBox chckbxUsciteAnnuali = new CheckBoxF(I18NManager.getSingleton().getMessaggio("yearoutcome"));
		chckbxUsciteAnnuali.setBounds(22, 67, 197, 23);
		getContentPane().add(chckbxUsciteAnnuali);

		final JCheckBox chckbxEntrateMensili = new CheckBoxF(I18NManager.getSingleton().getMessaggio("monthlyincome"));
		chckbxEntrateMensili.setBounds(22, 94, 197, 23);
		getContentPane().add(chckbxEntrateMensili);

		final JCheckBox chckbxUsciteMensili = new CheckBoxF(I18NManager.getSingleton().getMessaggio("monthlyoutcome"));
		chckbxUsciteMensili.setBounds(22, 121, 197, 23);
		getContentPane().add(chckbxUsciteMensili);

		final JCheckBox chckbxSpesePerCategorie = new CheckBoxF(I18NManager.getSingleton().getMessaggio("catspeseyear"));
		chckbxSpesePerCategorie.setBounds(22, 148, 197, 23);
		getContentPane().add(chckbxSpesePerCategorie);

		final JCheckBox chckbxEntratePerCategorie = new CheckBoxF(I18NManager.getSingleton().getMessaggio(
				"catentrateyear"));
		chckbxEntratePerCategorie.setBounds(22, 175, 197, 23);
		getContentPane().add(chckbxEntratePerCategorie);

		final JCheckBox chckbxSpeseMensCat = new CheckBoxF(I18NManager.getSingleton().getMessaggio("catspesemonth"));
		chckbxSpeseMensCat.setBounds(22, 229, 197, 23);
		getContentPane().add(chckbxSpeseMensCat);

		final JCheckBox chckbxEntrateMensCategorie = new CheckBoxF(I18NManager.getSingleton().getMessaggio(
				"catentratemonth"));
		chckbxEntrateMensCategorie.setBounds(22, 202, 197, 23);
		getContentPane().add(chckbxEntrateMensCategorie);

		final JCheckBox chckbxSpeseVariabili_1 = new CheckBoxF("% "
				+ I18NManager.getSingleton().getMessaggio("spesevar"));
		chckbxSpeseVariabili_1.setBounds(22, 255, 197, 23);
		getContentPane().add(chckbxSpeseVariabili_1);

		final JCheckBox chckbxSpeseFutili_1 = new CheckBoxF("% " + I18NManager.getSingleton().getMessaggio("spesefut"));
		chckbxSpeseFutili_1.setBounds(22, 282, 197, 23);
		getContentPane().add(chckbxSpeseFutili_1);

		final JCheckBox chckbxMedie = new CheckBoxF(I18NManager.getSingleton().getMessaggio("annualaverages"));
		chckbxMedie.setBounds(22, 336, 197, 23);
		getContentPane().add(chckbxMedie);

		final JCheckBox chckbxAvanzo = new CheckBoxF(I18NManager.getSingleton().getMessaggio("avanzo"));
		chckbxAvanzo.setBounds(22, 309, 197, 23);
		getContentPane().add(chckbxAvanzo);

		final JButton btnGeneraReport = new ButtonF(I18NManager.getSingleton().getMessaggio("reports"));
		btnGeneraReport.setBounds(22, 366, 197, 25);
		getContentPane().add(btnGeneraReport);

		btnGeneraReport.addActionListener(new AscoltatoreAggiornatoreNiente() {

			String trattini = "--------------------------------------------------------------------------";
			private double usciteCategorieAnnuali;
			private double totaleUsciteMese;
			private double totaleEntrateMese;
			private final Vector<CatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();
			private String[] nomiColonne;
			private double entrateCategorieAnnuali;

			@Override
			protected void actionPerformedOverride(ActionEvent e) {
				super.actionPerformedOverride(e);

				settaValoriReportDati(chckbxSpeseVariabili_1, chckbxEntrateMensCategorie, chckbxSpeseMensCat,
						chckbxEntratePerCategorie, chckbxSpesePerCategorie, chckbxUsciteMensili, chckbxEntrateMensili,
						chckbxUsciteAnnuali, chckbxEntrateAnnuali, chckbxSpeseFutili_1, chckbxAvanzo, chckbxMedie);

				try {
					IScrittoreReport scrittoreReport = new ScrittoreReportTxt();
					scrittoreReport.generaReport(reportData);
				} catch (Exception e11) {
					e11.printStackTrace();
				}

				AltreUtil.deleteFileDaDirectory("./", "Rep");
				final String data = DBUtil.dataToString(new Date(), "dd_MM_yyyy_HH_mm_ss");
				FileOutputStream file = null;
				try {
					file = new FileOutputStream("Report" + data + ".txt");
				} catch (final FileNotFoundException e1) {
					e1.printStackTrace();
				}
				final PrintStream output = new PrintStream(file);

				output.println("Report Entrate/Uscite realizzato il : "
						+ DBUtil.dataToString(new Date(), "dd/MM/yyyy HH:mm"));
				output.println(" ");
				if (chckbxUsciteAnnuali.isSelected()) {
					double UAnnuale = Database.Annuale();
					final String forFile = "Le spese annuali sono: "
							+ Double.toString(AltreUtil.arrotondaDecimaliDouble(UAnnuale)) + "";
					output.print(forFile);
					output.println(" ");
					output.print(trattini);
					output.println(" ");
				}
				if (chckbxEntrateAnnuali.isSelected()) {
					double EAnnuale = Database.EAnnuale();
					final String forFile = "Le entrate annuali sono: "
							+ Double.toString(AltreUtil.arrotondaDecimaliDouble(EAnnuale)) + "";
					output.print(forFile);
					output.println(" ");
					output.print(trattini);
					output.println(" ");
				}
				if (chckbxEntrateMensili.isSelected()) {
					String entrateMese = "";
					for (int i = 0; i < 12; i++) {
						totaleEntrateMese = Database.getSingleton().totaleEntrateMese(i + 1);
						entrateMese = "Le entrate per il mese " + (i + 1) + " sono: " + totaleEntrateMese + ". \n";
						output.print(entrateMese);
						output.println(" ");
					}
					output.print(trattini);
					output.println(" ");
				}
				if (chckbxUsciteMensili.isSelected()) {
					String usciteMese = "";
					for (int i = 0; i < 12; i++) {
						totaleUsciteMese = Database.getSingleton().totaleUsciteMese(i + 1);
						usciteMese = "Le uscite per il mese " + (i + 1) + " sono: " + totaleUsciteMese + ". \n";
						output.print(usciteMese);
						output.println(" ");
					}
					output.print(trattini);
					output.println(" ");
				}
				if (chckbxSpesePerCategorie.isSelected()) {
					String uscitaAnnoCat = "";
					for (int i = 0; i < categorie.size(); i++) {
						final CatSpese categoria = categorie.get(i);
						usciteCategorieAnnuali = Database.totaleUscitaAnnoCategoria(categoria.getidCategoria());
						uscitaAnnoCat = "Le uscite annuali delle categoria '" + categoria.getnome() + "' sono: "
								+ usciteCategorieAnnuali + ". \n";
						output.print(uscitaAnnoCat);
						output.println(" ");
					}
					output.print(trattini);
					output.println(" ");

				}
				if (chckbxEntratePerCategorie.isSelected()) {
					String entrateAnnoCat = "";
					nomiColonne = new String[2];
					nomiColonne[0] = "Fisse";
					nomiColonne[1] = "Variabili";
					for (int i = 0; i < nomiColonne.length; i++) {
						entrateCategorieAnnuali = Database.totaleEntrateAnnoCategoria(nomiColonne[i]);
						entrateAnnoCat = "Le entrate annuali delle categoria '" + nomiColonne[i] + "' sono: "
								+ entrateCategorieAnnuali + ". \n";
						output.print(entrateAnnoCat);
						output.println(" ");
					}
					output.print(trattini);
					output.println(" ");
				}
				if (chckbxSpeseMensCat.isSelected()) {
					final int numColonne = categorie.size();
					final String[] nomiColonne = new String[numColonne];

					for (int i = 0; i < categorie.size(); i++) {
						nomiColonne[i] = categorie.get(i).getnome();
					}

					final String[][] primo = new String[12][categorie.size()];

					for (int i = 0; i < 12; i++) {
						for (int x = 0; x < categorie.size(); x++) {
							try {
								final int idCat = categorie.get(x).getidCategoria();
								final Double spesaMeseCategoria = Database.speseMeseCategoria(i + 1, idCat);
								primo[i][x] = spesaMeseCategoria.toString();
								final String stampa = "Le uscite per la categoria '" + nomiColonne[x] + "' ed il mese "
										+ (i + 1) + " sono: " + primo[i][x] + ".";
								output.print(stampa);
								output.println(" ");
							} catch (final Exception e1) {
								e1.printStackTrace();
							}
						}
					}
					output.print(trattini);
					output.println(" ");
				}
				if (chckbxEntrateMensCategorie.isSelected()) {
					nomiColonne = new String[2];
					nomiColonne[0] = "Fisse";
					nomiColonne[1] = "Variabili";
					final String[][] primo = new String[12][2];
					for (int i = 0; i < 12; i++) {
						for (int x = 0; x < 2; x++) {
							try {
								final Double entrataMeseTipo = Database.getSingleton().entrateMeseTipo((i + 1),
										nomiColonne[x]);
								primo[i][x] = entrataMeseTipo.toString();
								final String stampa = "Le entrate per la categoria '" + nomiColonne[x]
										+ "' ed il mese " + (i + 1) + " sono: " + primo[i][x] + ".";
								output.print(stampa);
								output.print("\n");
								output.println("\n");
							} catch (final Exception e2) {
								e2.printStackTrace();
							}
						}
					}
					output.print(trattini);
					output.println(" ");
				}
				if (chckbxSpeseVariabili_1.isSelected()) {
					final double speseVariabili = Database.percentoUscite(CatSpese.IMPORTANZA_VARIABILE);
					final String forFile = "La percentuale di spese variabili sul totale annuale e': "
							+ AltreUtil.arrotondaDecimaliDouble(speseVariabili) + " %";
					output.print(forFile);
					output.println(" ");
					output.print(trattini);
					output.println(" ");
				}
				if (chckbxSpeseFutili_1.isSelected()) {
					final double speseFutili = Database.percentoUscite(CatSpese.IMPORTANZA_FUTILE);
					final String forFile = "La percentuale di spese futile sul totale annuale e': " + speseFutili + "%";
					output.print(forFile);
					output.println(" ");
					output.print(trattini);
					output.println(" ");
				}
				if (chckbxAvanzo.isSelected()) {
					double differenza = AltreUtil.arrotondaDecimaliDouble((Database.EAnnuale()) - (Database.Annuale()));
					final String forFile = "La differenza fra Entrate e Uscite totali sono: "
							+ Double.toString(AltreUtil.arrotondaDecimaliDouble(differenza)) + " ";
					output.print(forFile);
					output.println(" ");
					output.print(trattini);
					output.println(" ");
				}
				if (chckbxMedie.isSelected()) {
					final double mediaEntrate = Database.EAnnuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
					final double mediaUscite = Database.Annuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
					final String forFileE = "La media mensile delle entrate e': "
							+ AltreUtil.arrotondaDecimaliDouble(mediaEntrate) + "";
					final String forfile = "La media mensile delle uscite e': "
							+ AltreUtil.arrotondaDecimaliDouble(mediaUscite) + "";
					output.print(forFileE);
					output.println(" ");
					output.print(forfile);
					output.println(" ");
					output.print(trattini);
					output.println(" ");
				}
				output.println();
				Controllore.getLog().info("Aggiorntato Report: " + DBUtil.dataToString(new Date(), "dd/MM/yyyy HH:mm"));
			}

		});
		Controllore.getLog().info("Registrato Report: " + DBUtil.dataToString(new Date(), "dd/MM/yyyy HH:mm"));

	}
}
