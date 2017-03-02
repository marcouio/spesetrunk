package com.molinari.gestionespese.view.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.view.entrateuscite.EntrateView;

import controller.ControlloreBase;

public class ReportData {

	List<CatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();

	ArrayList<OggettoReport> datiReport = new ArrayList<>();

	public void inserisci(final OggettoReport  oggettoReport){
		datiReport.add(oggettoReport);
	}

	public ArrayList<OggettoReport> getListaOggetti(){
		return datiReport;
	}


	public Double generaUsciteAnnuali(){
		return Database.uAnnuale();
	}


	public Double generaEntrateAnnuali(){
		return Database.eAnnuale();
	}

	public HashMap<String, Double> generaEntrateMese(){

		final HashMap<String, Double> entrateMese = new HashMap<>();

		for (int i = 1; i <= 12; i++) {
			final String mese = Mesi.getMeseStringa(i);
			entrateMese.put(mese, Database.getSingleton().totaleEntrateMese(i));
		}
		return entrateMese;
	}

	public HashMap<String, Double> generaUsciteMese() {
		final HashMap<String, Double> usciteMese = new HashMap<>();
		for (int i = 1; i <= 12; i++) {
			final String mese = Mesi.getMeseStringa(i);
			usciteMese.put(mese, Database.getSingleton().totaleUsciteMese(i));
		}
		return usciteMese;
	}


	public HashMap<String, Double> generaUsciteCatAnnuali() {
		final HashMap<String, Double> usciteCatAnnuali = new HashMap<>();
		for (int i = 0; i < categorie.size(); i++) {
			final CatSpese categoria = categorie.get(i);
			final String nome = categoria.getnome();
			usciteCatAnnuali.put(nome, Database.totaleUscitaAnnoCategoria(categoria.getidCategoria()));
		}
		return usciteCatAnnuali;
	}

	public HashMap<String, Double> generaEntrateCatAnnuali() {
		final HashMap<String, Double> entrateCatAnnuali = new HashMap<>();
		final ArrayList<String> nomiColonne = EntrateView.getLista();
		for (int i = 0; i < nomiColonne.size(); i++) {
			entrateCatAnnuali.put(nomiColonne.get(i), Database.totaleEntrateAnnoCategoria(nomiColonne.get(i)));
		}
		return entrateCatAnnuali;
	}

	public String[][] generaUsciteCatMensili() {
		String[][] usciteCatMensili;
		final int numColonne = categorie.size();
		final String[] nomiColonne = new String[numColonne];

		for (int i = 0; i < categorie.size(); i++) {
			nomiColonne[i] = categorie.get(i).getnome();
		}

		usciteCatMensili = new String[12][categorie.size()];

		for (int i = 0; i < 12; i++) {
			for (int x = 0; x < categorie.size(); x++) {
				try {
					final int idCat = categorie.get(x).getidCategoria();
					final Double spesaMeseCategoria = Database.speseMeseCategoria(i + 1, idCat);
					usciteCatMensili[i][x] = spesaMeseCategoria.toString();
				}catch (final Exception e) {
					ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
		return usciteCatMensili;
	}

	public String[][] generaEntrateCatMensili() {
		final ArrayList<String> nomiColonne = EntrateView.getLista();
		final String [][] entrateCatMensili = new String[12][2];
		for (int i = 0; i < 12; i++) {
			for (int x = 0; x < 2; x++) {
				try {
					final Double entrataMeseTipo = Database.getSingleton().entrateMeseTipo(i + 1,
							nomiColonne.get(x));
					entrateCatMensili[i][x] = entrataMeseTipo.toString();
				} catch (final Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return entrateCatMensili;
	}

	public Double generaUsciteVariabili() {
		return Database.percentoUscite(CatSpese.IMPORTANZA_VARIABILE);
	}

	public Double generaUsciteFutili() {
		return Database.percentoUscite(CatSpese.IMPORTANZA_FUTILE);
	}

	public Double generaAvanzo() {
		return AltreUtil.arrotondaDecimaliDouble(Database.eAnnuale() - Database.uAnnuale());
	}

	public Double generaMediaUscite() {
		return Database.uAnnuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
	}

	public Double generaMediaEntrate() {
		return Database.eAnnuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
	}

}
