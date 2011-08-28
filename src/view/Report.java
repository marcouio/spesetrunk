package view;

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
import javax.swing.JFrame;
import javax.swing.JLabel;

import view.componenti.componentiPannello.SottoPannelloDatiEntrate;
import view.componenti.componentiPannello.SottoPannelloDatiSpese;
import view.componenti.componentiPannello.SottoPannelloTotali;
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

public class Report extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 * 
	 * @throws FileNotFoundException
	 */
	public Report() throws FileNotFoundException {
		getContentPane().setLayout(null);
		this.setTitle("Report");
		final JLabel Istruzioni = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("selectreport"));
		Istruzioni.setText(I18NManager.getSingleton().getMessaggio("select")+":");
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

		final JCheckBox chckbxEntratePerCategorie = new CheckBoxF(I18NManager.getSingleton().getMessaggio("catentrateyear"));
		chckbxEntratePerCategorie.setBounds(22, 175, 197, 23);
		getContentPane().add(chckbxEntratePerCategorie);

		final JCheckBox chckbxSpeseMensCat = new CheckBoxF(I18NManager.getSingleton().getMessaggio("catspesemonth"));
		chckbxSpeseMensCat.setBounds(22, 229, 197, 23);
		getContentPane().add(chckbxSpeseMensCat);

		final JCheckBox chckbxEntrateMensCategorie = new CheckBoxF(I18NManager.getSingleton().getMessaggio("catentratemonth"));
		chckbxEntrateMensCategorie.setBounds(22, 202, 197, 23);
		getContentPane().add(chckbxEntrateMensCategorie);

		final JCheckBox chckbxSpeseVariabili_1 = new CheckBoxF("% " + I18NManager.getSingleton().getMessaggio("spesevar"));
		chckbxSpeseVariabili_1.setBounds(22, 255, 197, 23);
		getContentPane().add(chckbxSpeseVariabili_1);

		final JCheckBox chckbxSpeseFutili_1 = new CheckBoxF("% "+ I18NManager.getSingleton().getMessaggio("spesefut"));
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

			String                         trattini  = "--------------------------------------------------------------------------";
			private double                 usciteCategorieAnnuali;
			private double                 totaleUsciteMese;
			private double                 totaleEntrateMese;
			private final Vector<CatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();
			private String[]               nomiColonne;
			private double                 entrateCategorieAnnuali;

			@Override
			protected void actionPerformedOverride(ActionEvent e) {
				super.actionPerformedOverride(e);
				AltreUtil.deleteFileDaDirectory("./", "Rep");
				final String data = DBUtil.dataToString(new Date(), "dd_MM_yyyy_HH_mm_ss");
				FileOutputStream file = null;
				try {
					file = new FileOutputStream("Report" + data + ".txt");
				} catch (final FileNotFoundException e1) {
					e1.printStackTrace();
				}
				final PrintStream Output = new PrintStream(file);

				Output.println("Report Entrate/Uscite realizzato il : " + data);
				Output.println(" ");
				if (chckbxUsciteAnnuali.isSelected()) {
					final String forFile = "Le spese annuali sono: " + SottoPannelloDatiSpese.getAnnuale() + "";
					Output.print(forFile);
					Output.println(" ");
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxEntrateAnnuali.isSelected()) {
					final String forFile = "Le entrate annuali sono: " + SottoPannelloDatiEntrate.getEnAnCorso().getText() + "";
					Output.print(forFile);
					Output.println(" ");
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxEntrateMensili.isSelected()) {
					String entrateMese = "";
					for (int i = 0; i < 12; i++) {
						totaleEntrateMese = Database.getSingleton().totaleEntrateMese(i + 1);
						entrateMese = "Le entrate per il mese " + (i + 1) + " sono: " + totaleEntrateMese + ". \n";
						Output.print(entrateMese);
						Output.println(" ");
					}
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxUsciteMensili.isSelected()) {
					String usciteMese = "";
					for (int i = 0; i < 12; i++) {
						totaleUsciteMese = Database.getSingleton().totaleUsciteMese(i + 1);
						usciteMese = "Le uscite per il mese " + (i + 1) + " sono: " + totaleUsciteMese + ". \n";
						Output.print(usciteMese);
						Output.println(" ");
					}
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxSpesePerCategorie.isSelected()) {
					String uscitaAnnoCat = "";
					for (int i = 0; i < categorie.size(); i++) {
						final CatSpese categoria = categorie.get(i);
						usciteCategorieAnnuali = Database.totaleUscitaAnnoCategoria(categoria.getidCategoria());
						uscitaAnnoCat = "Le uscite annuali delle categoria '" + categoria.getnome() + "' sono: " + usciteCategorieAnnuali + ". \n";
						Output.print(uscitaAnnoCat);
						Output.println(" ");
					}
					Output.print(trattini);
					Output.println(" ");

				}
				if (chckbxEntratePerCategorie.isSelected()) {
					String entrateAnnoCat = "";
					nomiColonne = new String[2];
					nomiColonne[0] = "Fisse";
					nomiColonne[1] = "Variabili";
					for (int i = 0; i < nomiColonne.length; i++) {
						entrateCategorieAnnuali = Database.totaleEntrateAnnoCategoria(nomiColonne[i]);
						entrateAnnoCat = "Le entrate annuali delle categoria '" + nomiColonne[i] + "' sono: " + entrateCategorieAnnuali + ". \n";
						Output.print(entrateAnnoCat);
						Output.println(" ");
					}
					Output.print(trattini);
					Output.println(" ");
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
								final String stampa = "Le uscite per la categoria '" + nomiColonne[x] + "' ed il mese " + (i + 1) + " sono: " + primo[i][x] + ".";
								Output.print(stampa);
								Output.println(" ");
							} catch (final Exception e1) {
								e1.printStackTrace();
							}
						}
					}
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxEntrateMensCategorie.isSelected()) {
					nomiColonne = new String[2];
					nomiColonne[0] = "Fisse";
					nomiColonne[1] = "Variabili";
					final String[][] primo = new String[12][2];
					for (int i = 0; i < 12; i++) {
						for (int x = 1; x <= 2; x++) {
							try {
								final Double entrataMeseTipo = Database.getSingleton().entrateMeseTipo((i + 1), nomiColonne[x]);
								primo[i][x] = entrataMeseTipo.toString();
								final String stampa = "Le entrate per la categoria '" + nomiColonne[x] + "' ed il mese " + (i + 1) + " sono: " + primo[i][x] + ".";
								Output.print(stampa);
								Output.print("\n");
							} catch (final Exception e2) {
								e2.printStackTrace();
							}
						}
					}
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxSpeseVariabili_1.isSelected()) {
					final double speseVariabili = Double.parseDouble(SottoPannelloTotali.getPercentoVariabili().getText());
					final String forFile = "La percentuale di spese variabili sul totale annuale e': " + speseVariabili + " %";
					Output.print(forFile);
					Output.println(" ");
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxSpeseFutili_1.isSelected()) {
					final double speseFutili = Double.parseDouble(SottoPannelloTotali.getPercentoFutili().getText());
					final String forFile = "La percentuale di spese futile sul totale annuale e': " + speseFutili + "%";
					Output.print(forFile);
					Output.println(" ");
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxAvanzo.isSelected()) {
					final double avanzo = Double.parseDouble(SottoPannelloTotali.getAvanzo().getText());
					final String forFile = "La differenza fra Entrate e Uscite totali sono: " + avanzo + " ";
					Output.print(forFile);
					Output.println(" ");
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxMedie.isSelected()) {
					final double mediaEntrate = Double.parseDouble(SottoPannelloDatiEntrate.getEnAnCorso().getText()) / new GregorianCalendar().get(Calendar.MONTH + 1);
					final double mediaUscite = SottoPannelloDatiSpese.getAnnuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
					final String forFileE = "La media mensile delle entrate e': " + mediaEntrate + "";
					final String forfile = "La media mensile delle uscite e': " + mediaUscite + "";
					Output.print(forFileE);
					Output.println(" ");
					Output.print(forfile);
					Output.println(" ");
					Output.print(trattini);
					Output.println(" ");
				}
				Output.println();

			}
		});
		Controllore.getLog().info("Registrato Report: " + DBUtil.dataToString(new Date(), "dd/MM/yyyy HH:mm"));

	}
}
