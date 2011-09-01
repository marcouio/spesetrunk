package view.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import domain.CatSpese;

import business.AltreUtil;
import business.DBUtil;
import business.cache.CacheCategorie;

public class ScrittoreReportTxt implements IScrittoreReport {

	private FileOutputStream file = null;
	private Date dataRegistrazione = new Date();
	private Date dataAggiornamento;
	String trattini = "--------------------------------------------------------------------------";

	public ScrittoreReportTxt() throws FileNotFoundException {

	}

	private PrintStream creaStream() throws FileNotFoundException {
		AltreUtil.deleteFileDaDirectory("./", "Rep");
		final String data = DBUtil.dataToString(new Date(), "dd_MM_yyyy_HH_mm_ss");
		file = new FileOutputStream("Report" + data + ".txt");
		return new PrintStream(file);
	}

	@Override
	public boolean generaReport(final ReportData reportData) throws Exception {
		PrintStream stream = creaStream();
		stream.println("Report Entrate/Uscite realizzato il : "
				+ DBUtil.dataToString(dataRegistrazione, "dd/MM/yyyy HH:mm"));
		
		if(reportData.getAvanzo()!=null){
			
		}
		if(reportData.getUsciteAnnuali()!=null){
			
		}
		if(reportData.getEntrateAnnuali()!=null){
			
		}
		if(reportData.getMediaEntrate()!=null){
			
		}
		if(reportData.getMediaUscite()!=null){
			
		}
		if(reportData.getUsciteVariabili()!=null){
			
		}
		if(reportData.getUsciteFutili()!=null){
			
		}
		if(reportData.getUsciteMese()!=null){
			HashMap<String, Double> usciteMese = reportData.getUsciteMese();
			for (int i = 1; i <= 12; i++) {
				StringBuffer sb = new StringBuffer();
				sb.append("Le uscite per il mese " + Mesi.getMeseStringa(i));
				sb.append(" sono: " + usciteMese.get(Integer.toString(i)) + ". ");
				stream.print(sb);
				stream.println(" ");
			}
			stream.print(trattini);
			stream.println(" ");
		}
		
		if(reportData.getEntrateMese()!=null){
			HashMap<String, Double> entrateMese = reportData.getEntrateMese();
			for (int i = 1; i <= 12; i++) {
				StringBuffer sb = new StringBuffer();
				sb.append("Le entrate per il mese " + Mesi.getMeseStringa(i));
				sb.append(" sono: " + entrateMese.get(Integer.toString(i)) + ". ");
				stream.print(sb);
				stream.println(" ");
			}
			stream.print(trattini);
			stream.println(" ");
		}
		
		if(reportData.getUsciteCatAnnuali()!=null){
			
			Vector<CatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();
			HashMap<String, Double> usciteAnnoCat = reportData.getUsciteCatAnnuali();
			for (int i = 0; i < categorie.size(); i++) {
				final CatSpese categoria = categorie.get(i);
				
				StringBuffer sb = new StringBuffer();
				sb.append("Le uscite annuali delle categoria '" + categoria.getnome());
				sb.append("' sono: " + usciteAnnoCat.get(Integer.toString(categoria.getidCategoria()))+". ");
				stream.print(sb);
				stream.println(" ");
			}
			stream.print(trattini);
			stream.println(" ");

		}
		if(reportData.getEntrateCatAnnuali()!=null){
			String entrateAnnoCat = "";
			String[] nomiColonne = new String[2];
			nomiColonne[0] = "Fisse";
			nomiColonne[1] = "Variabili";
			HashMap<String, Double> entriesAnnoCat = reportData.getEntrateCatAnnuali();
			for (int i = 0; i < nomiColonne.length; i++) {
				StringBuffer sb = new StringBuffer();
				sb.append("Le entrate annuali delle categoria '"+ nomiColonne[i]);
				sb.append("' sono: " +entriesAnnoCat.get(nomiColonne[i])+".");
				stream.print(entrateAnnoCat);
				stream.println(" ");
			}
			stream.print(trattini);
			stream.println(" ");

		}
		if(reportData.getUsciteCatMensili()!=null){
			Vector<CatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();
			final int numColonne = categorie.size();
			final String[] nomiColonne = new String[numColonne];

			for (int i = 0; i < categorie.size(); i++) {
				nomiColonne[i] = categorie.get(i).getnome();
			}

			for (int i = 0; i < 12; i++) {
				for (int x = 0; x < categorie.size(); x++) {
					try {
						String[][] matrice = reportData.getUsciteCatMensili();
						final String stampa = "Le uscite per la categoria '" + nomiColonne[x] + "' ed il mese "
								+ Mesi.getMeseStringa(i + 1) + " sono: " + matrice[i][x] + ".";
						stream.print(stampa);
						stream.println(" ");
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			stream.print(trattini);
			stream.println(" ");
		}
		if(reportData.getEntrateCatMensili()!=null){
			String[] nomiColonne = new String[2];
			nomiColonne[0] = "Fisse";
			nomiColonne[1] = "Variabili";
			String[][] matrice = new String[12][2];
			for (int i = 0; i < 12; i++) {
				for (int x = 0; x < 2; x++) {
					try {
						matrice = reportData.getEntrateCatMensili();
						final String stampa = "Le entrate per la categoria '" + nomiColonne[x]
								+ "' ed il mese " + Mesi.getMeseStringa(i + 1) + " sono: " + matrice[i][x] + ".";
						stream.print(stampa);
						stream.println(" ");
						stream.println(" ");
					} catch (final Exception e2) {
						e2.printStackTrace();
					}
				}
			}
			stream.print(trattini);
			stream.println(" ");
		}
		
		return false;
	}

	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public Date getDataAggiornamento() {
		dataAggiornamento = new Date();
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
}
