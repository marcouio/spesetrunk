package view.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import view.entrateuscite.EntrateView;
import business.AltreUtil;
import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;

public class ReportData {
	
	private Double usciteAnnuali;
	private Double entrateAnnuali;
	private HashMap<String, Double> entrateMese = new HashMap<String, Double>();
	private HashMap<String, Double> usciteMese = new HashMap<String, Double>();
	private HashMap<String, Double> usciteCatAnnuali = new HashMap<String, Double>();
	private HashMap<String, Double> entrateCatAnnuali = new HashMap<String, Double>();
	private String[][] usciteCatMensili;
	private String[][] entrateCatMensili;
	private Double usciteVariabili;
	private Double usciteFutili;
	private Double avanzo;
	private Double mediaUscite;
	private Double mediaEntrate;
	Vector<CatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();
	
	
	public Double getUsciteAnnuali() {
		return usciteAnnuali;
	}
	
	public Double generaUsciteAnnuali(){
		return Database.Annuale();
	}
	
	public void setUsciteAnnuali(Double usciteAnnuali) {
		this.usciteAnnuali = usciteAnnuali;
	}
	public Double getEntrateAnnuali() {
		return entrateAnnuali;
	}
	
	public Double generaEntrateAnnuali(){
		return Database.EAnnuale();
	}
	
	public void setEntrateAnnuali(Double entrateAnnuali) {
		this.entrateAnnuali = entrateAnnuali;
	}
	
	public HashMap<String, Double> generaEntrateMese(){
		
		HashMap<String, Double> entrateMese = new HashMap<String, Double>();
		
		for (int i = 1; i <= 12; i++) {
			String mese = Integer.toString(i);
			entrateMese.put(mese, Database.getSingleton().totaleEntrateMese(i));
		}
		return entrateMese;
	}
	
	public HashMap<String, Double> getEntrateMese() {
		return entrateMese;
	}
	public void setEntrateMese(HashMap<String, Double> entrateMese) {
		this.entrateMese = entrateMese;
	}
	
	public HashMap<String, Double> generaUsciteMese() {
		HashMap<String, Double> usciteMese = new HashMap<String, Double>();
		for (int i = 1; i <= 12; i++) {
			String mese = Integer.toString(i);
			usciteMese.put(mese, Database.getSingleton().totaleUsciteMese(i));
		}
		return usciteMese;
	}
	
	public HashMap<String, Double> getUsciteMese() {
		return usciteMese;
	}
	
	public void setUsciteMese(HashMap<String, Double> usciteMese) {
		this.usciteMese = usciteMese;
	}
	
	public HashMap<String, Double> generaUsciteCatAnnuali() {
		HashMap<String, Double> usciteCatAnnuali = new HashMap<String, Double>();
		for (int i = 0; i < categorie.size(); i++) {
			final CatSpese categoria = categorie.get(i);
			String id = Integer.toString(categoria.getidCategoria());
			usciteCatAnnuali.put(id, Database.totaleUscitaAnnoCategoria(categoria.getidCategoria()));
		}
		return usciteCatAnnuali;
	}
	
	public HashMap<String, Double> getUsciteCatAnnuali() {
		return usciteCatAnnuali;
	}
	
	public void setUsciteCatAnnuali(HashMap<String, Double> usciteCatAnnuali) {
		this.usciteCatAnnuali = usciteCatAnnuali;
	}
	
	public HashMap<String, Double> generaEntrateCatAnnuali() {
		HashMap<String, Double> entrateCatAnnuali = new HashMap<String, Double>(); 
		ArrayList<String> nomiColonne = EntrateView.getLista();
		for (int i = 0; i < nomiColonne.size(); i++) {
			entrateCatAnnuali.put(nomiColonne.get(i), Database.totaleEntrateAnnoCategoria(nomiColonne.get(i))); 
		}
		return entrateCatAnnuali;
	}
	
	public HashMap<String, Double> getEntrateCatAnnuali() {
		return entrateCatAnnuali;
	}
	
	public void setEntrateCatAnnuali(HashMap<String, Double> entrateCatAnnuali) {
		this.entrateCatAnnuali = entrateCatAnnuali;
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
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return usciteCatMensili;
	}
	
	public String[][] getUsciteCatMensili() {
		return usciteCatMensili;
	}
	
	public void setUsciteCatMensili(String[][] usciteCatMensili) {
		this.usciteCatMensili = usciteCatMensili;
	}
	public String[][] generaEntrateCatMensili() {
		ArrayList<String> nomiColonne = EntrateView.getLista();
		String [][] entrateCatMensili = new String[12][2];
		for (int i = 0; i < 12; i++) {
			for (int x = 0; x < 2; x++) {
				try {
					final Double entrataMeseTipo = Database.getSingleton().entrateMeseTipo((i + 1),
							nomiColonne.get(x));
					entrateCatMensili[i][x] = entrataMeseTipo.toString();
				} catch (final Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return entrateCatMensili;
	}
	
	public String[][] getEntrateCatMensili() {
		return entrateCatMensili;
	}
	
	public void setEntrateCatMensili(String[][] entrateCatMensili) {
		this.entrateCatMensili = entrateCatMensili;
	}
	
	public Double generaUsciteVariabili() {
		return Database.percentoUscite(CatSpese.IMPORTANZA_VARIABILE);
	}
	
	public Double getUsciteVariabili() {
		return usciteVariabili;
	}
	
	public void setUsciteVariabili(Double usciteVariabili) {
		this.usciteVariabili = usciteVariabili;
	}
	
	public Double generaUsciteFutili() {
		return Database.percentoUscite(CatSpese.IMPORTANZA_FUTILE);
	}
	
	public Double getUsciteFutili() {
		return usciteFutili;
	}
	public void setUsciteFutili(Double usciteFutili) {
		this.usciteFutili = usciteFutili;
	}
	
	public Double generaAvanzo() {
		return AltreUtil.arrotondaDecimaliDouble((Database.EAnnuale()) - (Database.Annuale()));
	}
	
	public Double getAvanzo() {
		return avanzo;
	}
	public void setAvanzo(Double avanzo) {
		this.avanzo = avanzo;
	}
	
	public Double generaMediaUscite() {
		return Database.Annuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
	}
	
	public Double getMediaUscite() {
		return mediaUscite;
	}
	public void setMediaUscite(Double mediaUscite) {
		this.mediaUscite = mediaUscite;
	}
	
	public Double generaMediaEntrate() {
		return Database.EAnnuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
	}
	
	public Double getMediaEntrate() {
		return mediaEntrate;
	}
	public void setMediaEntrate(Double mediaEntrate) {
		this.mediaEntrate = mediaEntrate;
	}
}
