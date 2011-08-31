package view.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import view.entrateuscite.EntrateView;

import domain.CatSpese;

import business.AltreUtil;
import business.Database;
import business.cache.CacheCategorie;

public class ReportData {

	private Date dataRegistrazione;
	private Date dataAggiornamento;
	private Double usciteAnnuali;
	private Double entrateAnnuali;
	private HashMap<String, Double> entrateMese = new HashMap<String, Double>();
	private HashMap<String, Double> usciteMese = new HashMap<String, Double>();
	private HashMap<CatSpese, Double> usciteCatAnnuali = new HashMap<CatSpese, Double>();
	private HashMap<String, Double> entrateCatAnnuali = new HashMap<String, Double>();
	private String[][] usciteCatMensili;
	private String[][] entrateCatMensili;
	private Double usciteVariabili;
	private Double usciteFutili;
	private Double avanzo;
	private Double mediaUscite;
	private Double mediaEntrate;
	Vector<CatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();
	
	
	public Date getDataRegistrazione() {
		dataRegistrazione = new Date();
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
	public Double getUsciteAnnuali() {
		usciteAnnuali = Database.Annuale();
		return usciteAnnuali;
	}
	public void setUsciteAnnuali(Double usciteAnnuali) {
		this.usciteAnnuali = usciteAnnuali;
	}
	public Double getEntrateAnnuali() {
		entrateAnnuali = Database.EAnnuale();
		return entrateAnnuali;
	}
	public void setEntrateAnnuali(Double entrateAnnuali) {
		this.entrateAnnuali = entrateAnnuali;
	}
	public HashMap<String, Double> getEntrateMese() {
		for (int i = 1; i <= 12; i++) {
			String mese = Integer.toString(i);
			entrateMese.put(mese, Database.getSingleton().totaleEntrateMese(i));
		}
		return entrateMese;
	}
	public void setEntrateMese(HashMap<String, Double> entrateMese) {
		this.entrateMese = entrateMese;
	}
	public HashMap<String, Double> getUsciteMese() {
		for (int i = 1; i <= 12; i++) {
			String mese = Integer.toString(i);
			usciteMese.put(mese, Database.getSingleton().totaleUsciteMese(i));
		}
		return usciteMese;
	}
	public void setUsciteMese(HashMap<String, Double> usciteMese) {
		this.usciteMese = usciteMese;
	}
	public HashMap<CatSpese, Double> getUsciteCatAnnuali() {
		for (int i = 0; i < categorie.size(); i++) {
			final CatSpese categoria = categorie.get(i);
			usciteCatAnnuali.put(categoria, Database.totaleUscitaAnnoCategoria(categoria.getidCategoria()));
		}
		return usciteCatAnnuali;
	}
	public void setUsciteCatAnnuali(HashMap<CatSpese, Double> usciteCatAnnuali) {
		this.usciteCatAnnuali = usciteCatAnnuali;
	}
	public HashMap<String, Double> getEntrateCatAnnuali() {
		ArrayList<String> nomiColonne = EntrateView.getLista();
		for (int i = 0; i < nomiColonne.size(); i++) {
			entrateCatAnnuali.put(nomiColonne.get(i), Database.totaleEntrateAnnoCategoria(nomiColonne.get(i))); 
		}
		return entrateCatAnnuali;
	}
	public void setEntrateCatAnnuali(HashMap<String, Double> entrateCatAnnuali) {
		this.entrateCatAnnuali = entrateCatAnnuali;
	}
	public String[][] getUsciteCatMensili() {
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
	public void setUsciteCatMensili(String[][] usciteCatMensili) {
		this.usciteCatMensili = usciteCatMensili;
	}
	public String[][] getEntrateCatMensili() {
		ArrayList<String> nomiColonne = EntrateView.getLista();
		entrateCatMensili = new String[12][2];
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
	public void setEntrateCatMensili(String[][] entrateCatMensili) {
		this.entrateCatMensili = entrateCatMensili;
	}
	public Double getUsciteVariabili() {
		usciteVariabili = Database.percentoUscite(CatSpese.IMPORTANZA_VARIABILE);
		return usciteVariabili;
	}
	public void setUsciteVariabili(Double usciteVariabili) {
		this.usciteVariabili = usciteVariabili;
	}
	public Double getUsciteFutili() {
		usciteFutili = Database.percentoUscite(CatSpese.IMPORTANZA_FUTILE);
		return usciteFutili;
	}
	public void setUsciteFutili(Double usciteFutili) {
		this.usciteFutili = usciteFutili;
	}
	public Double getAvanzo() {
		avanzo = AltreUtil.arrotondaDecimaliDouble((Database.EAnnuale()) - (Database.Annuale()));
		return avanzo;
	}
	public void setAvanzo(Double avanzo) {
		this.avanzo = avanzo;
	}
	public Double getMediaUscite() {
		mediaUscite = Database.Annuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
		return mediaUscite;
	}
	public void setMediaUscite(Double mediaUscite) {
		this.mediaUscite = mediaUscite;
	}
	public Double getMediaEntrate() {
		mediaEntrate = Database.EAnnuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
		return mediaEntrate;
	}
	public void setMediaEntrate(Double mediaEntrate) {
		this.mediaEntrate = mediaEntrate;
	}
}
