package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import view.font.LabelTesto;
import view.impostazioni.CategorieView;
import business.AltreUtil;
import business.DBUtil;
import business.Database;
import domain.CatSpese;

public class Report extends JFrame {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 * 
	 * @throws FileNotFoundException
	 */
	public Report() throws FileNotFoundException {
		getContentPane().setLayout(null);
		this.setTitle("Report");
		JLabel Istruzioni = new LabelTesto("Seleziona cio' che vuoi far comparire nel report");
		Istruzioni.setText("Seleziona:");
		Istruzioni.setBounds(12, 12, 207, 20);
		getContentPane().add(Istruzioni);

		final JCheckBox chckbxEntrateAnnuali = new CheckBoxF("Entrate Annuali");
		chckbxEntrateAnnuali.setBounds(22, 40, 197, 23);
		getContentPane().add(chckbxEntrateAnnuali);

		final JCheckBox chckbxUsciteAnnuali = new CheckBoxF("Uscite Annuali");
		chckbxUsciteAnnuali.setBounds(22, 67, 197, 23);
		getContentPane().add(chckbxUsciteAnnuali);

		final JCheckBox chckbxEntrateMensili = new CheckBoxF("Entrate Mensili");
		chckbxEntrateMensili.setBounds(22, 94, 197, 23);
		getContentPane().add(chckbxEntrateMensili);

		final JCheckBox chckbxUsciteMensili = new CheckBoxF("Uscite Mensili");
		chckbxUsciteMensili.setBounds(22, 121, 197, 23);
		getContentPane().add(chckbxUsciteMensili);

		final JCheckBox chckbxSpesePerCategorie = new CheckBoxF("Spese annuali di ogni categorie");
		chckbxSpesePerCategorie.setBounds(22, 148, 197, 23);
		getContentPane().add(chckbxSpesePerCategorie);

		final JCheckBox chckbxEntratePerCategorie = new CheckBoxF("Entrate annuali di ogni categorie");
		chckbxEntratePerCategorie.setBounds(22, 175, 197, 23);
		getContentPane().add(chckbxEntratePerCategorie);

		final JCheckBox chckbxSpeseMensCat = new CheckBoxF("Spese mensili di ogni categoria");
		chckbxSpeseMensCat.setBounds(22, 229, 197, 23);
		getContentPane().add(chckbxSpeseMensCat);

		final JCheckBox chckbxEntrateMensCategorie = new CheckBoxF("Entrate mensili di ogni categoria");
		chckbxEntrateMensCategorie.setBounds(22, 202, 197, 23);
		getContentPane().add(chckbxEntrateMensCategorie);

		final JCheckBox chckbxSpeseVariabili_1 = new CheckBoxF("% Spese variabili");
		chckbxSpeseVariabili_1.setBounds(22, 255, 197, 23);
		getContentPane().add(chckbxSpeseVariabili_1);

		final JCheckBox chckbxSpeseFutili_1 = new CheckBoxF("% Spese Futili");
		chckbxSpeseFutili_1.setBounds(22, 282, 197, 23);
		getContentPane().add(chckbxSpeseFutili_1);

		final JCheckBox chckbxMedie = new CheckBoxF("Medie annuali");
		chckbxMedie.setBounds(22, 336, 197, 23);
		getContentPane().add(chckbxMedie);

		final JCheckBox chckbxAvanzo = new CheckBoxF("Avanzo");
		chckbxAvanzo.setBounds(22, 309, 197, 23);
		getContentPane().add(chckbxAvanzo);

		JButton btnGeneraReport = new ButtonF("Genera Report");
		btnGeneraReport.setBounds(22, 366, 197, 25);
		getContentPane().add(btnGeneraReport);

		btnGeneraReport.addActionListener(new ActionListener() {

			String                         trattini  = "--------------------------------------------------------------------------";
			private double                 usciteCategorieAnnuali;
			private double                 totaleUsciteMese;
			private double                 totaleEntrateMese;
			private final Vector<CatSpese> categorie = CategorieView.getCategorieSpesa();
			private String[]               nomiColonne;
			private double                 entrateCategorieAnnuali;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AltreUtil.deleteFileDaDirectory("./", "Rep");
				String data = DBUtil.dataToString(new Date(), "dd_MM_yyyy_HH_mm_ss");
				FileOutputStream file = null;
				try {
					file = new FileOutputStream("Report" + data + ".txt");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				PrintStream Output = new PrintStream(file);

				Output.println("Report Entrate/Uscite realizzato il : " + data);
				Output.println(" ");
				if (chckbxUsciteAnnuali.isSelected()) {
					String forFile = "Le spese annuali sono: " + SottoPannelloDatiSpese.getAnnuale() + "�";
					Output.print(forFile);
					Output.println(" ");
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxEntrateAnnuali.isSelected()) {
					String forFile = "Le entrate annuali sono: " + SottoPannelloDatiEntrate.getEnAnCorso().getText() + "�";
					Output.print(forFile);
					Output.println(" ");
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxEntrateMensili.isSelected()) {
					String entrateMese = "";
					for (int i = 0; i < 12; i++) {
						totaleEntrateMese = Database.getSingleton().totaleEntrateMese(i + 1);
						entrateMese = "Le entrate per il mese " + (i + 1) + " sono: " + totaleEntrateMese + "�. \n";
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
						usciteMese = "Le uscite per il mese " + (i + 1) + " sono: " + totaleUsciteMese + "�. \n";
						Output.print(usciteMese);
						Output.println(" ");
					}
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxSpesePerCategorie.isSelected()) {
					String uscitaAnnoCat = "";
					for (int i = 0; i < categorie.size(); i++) {
						CatSpese categoria = categorie.get(i);
						usciteCategorieAnnuali = Database.totaleUscitaAnnoCategoria(categoria.getidCategoria());
						uscitaAnnoCat = "Le uscite annuali delle categoria '" + categoria.getnome() + "' sono: " + usciteCategorieAnnuali + "�. \n";
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
						entrateAnnoCat = "Le entrate annuali delle categoria '" + nomiColonne[i] + "' sono: " + entrateCategorieAnnuali + "�. \n";
						Output.print(entrateAnnoCat);
						Output.println(" ");
					}
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxSpeseMensCat.isSelected()) {
					int numColonne = categorie.size();
					String[] nomiColonne = new String[numColonne];

					for (int i = 0; i < categorie.size(); i++) {
						nomiColonne[i] = categorie.get(i).getnome();
					}

					String[][] primo = new String[12][categorie.size()];

					for (int i = 0; i < 12; i++) {
						for (int x = 0; x < categorie.size(); x++) {
							try {
								int idCat = categorie.get(x).getidCategoria();
								Double spesaMeseCategoria = Database.speseMeseCategoria(i + 1, idCat);
								primo[i][x] = spesaMeseCategoria.toString();
								String stampa = "Le uscite per la categoria '" + nomiColonne[x] + "' ed il mese " + (i + 1) + " sono: " + primo[i][x] + "�.";
								Output.print(stampa);
								Output.println(" ");
							} catch (Exception e) {
								e.printStackTrace();
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
					String[][] primo = new String[12][2];
					for (int i = 0; i < 12; i++) {
						for (int x = 1; x <= 2; x++) {
							try {
								Double entrataMeseTipo = Database.getSingleton().entrateMeseTipo((i + 1), nomiColonne[x]);
								primo[i][x] = entrataMeseTipo.toString();
								String stampa = "Le entrate per la categoria '" + nomiColonne[x] + "' ed il mese " + (i + 1) + " sono: " + primo[i][x] + "�.";
								Output.print(stampa);
								Output.print("\n");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxSpeseVariabili_1.isSelected()) {
					double speseVariabili = Double.parseDouble(SottoPannelloTotali.getPercentoVariabili().getText());
					String forFile = "La percentuale di spese variabili sul totale annuale e': " + speseVariabili + " %";
					Output.print(forFile);
					Output.println(" ");
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxSpeseFutili_1.isSelected()) {
					double speseFutili = Double.parseDouble(SottoPannelloTotali.getPercentoFutili().getText());
					String forFile = "La percentuale di spese futile sul totale annuale e': " + speseFutili + "%";
					Output.print(forFile);
					Output.println(" ");
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxAvanzo.isSelected()) {
					double avanzo = Double.parseDouble(SottoPannelloTotali.getAvanzo().getText());
					String forFile = "La differenza fra Entrate e Uscite totali sono: " + avanzo + " �";
					Output.print(forFile);
					Output.println(" ");
					Output.print(trattini);
					Output.println(" ");
				}
				if (chckbxMedie.isSelected()) {
					double mediaEntrate = Double.parseDouble(SottoPannelloDatiEntrate.getEnAnCorso().getText()) / new GregorianCalendar().get(Calendar.MONTH + 1);
					double mediaUscite = SottoPannelloDatiSpese.getAnnuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
					String forFileE = "La media mensile delle entrate e': " + mediaEntrate + "�";
					String forfile = "La media mensile delle uscite e': " + mediaUscite + "�";
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
		// log.info("Registrato Report: "+DBUtil.dataToString(new Date(),
		// "dd/MM/yyyy HH:mm"));

	}
}
