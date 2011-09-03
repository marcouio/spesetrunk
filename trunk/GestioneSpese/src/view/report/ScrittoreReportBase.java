package view.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public abstract class ScrittoreReportBase implements IScrittoreReport{

	private ReportData reportData;
	
	public ScrittoreReportBase(final ReportData reportData) {
		this.reportData = reportData;
	}
	
	public boolean smista(final OggettoReport oggettoReport){
		if(oggettoReport.getTipo().equals(OggettoReport.TIPO_DOUBLE)){
			return scriviCampoDaDouble(oggettoReport);
		}else if(oggettoReport.getTipo().equals(OggettoReport.TIPO_MAPPA)){
			return scriviCampiDaMappa(oggettoReport);
		}else if(oggettoReport.getTipo().equals(OggettoReport.TIPO_MATRICE)){
			return scriviCampoDaMatrice(oggettoReport);
		}
		return false;
	}
	
	public boolean generaReport() throws Exception{
		boolean ok = operazioniPreliminari();
		ArrayList<OggettoReport> listaOggetti = reportData.getListaOggetti();
		for (Iterator<OggettoReport> iterator = listaOggetti.iterator(); iterator.hasNext();) {
			OggettoReport oggettoReport = (OggettoReport) iterator.next();
			if(!smista(oggettoReport)){
				ok = false;
			}
		}
		return ok;
	}
	
	protected abstract boolean operazioniPreliminari() throws Exception;

	public static void main(String[] args) {
		String[][] matrice = new String[12][2];
		System.out.println(matrice.length);
		System.out.println(matrice[0].length);
	}
	

	protected boolean scriviCampoDaMatrice(final OggettoReport oggettoReport){
		String[][] matrice = (String[][]) oggettoReport.getOggettoReport();
		for (int i = 0; i < matrice.length; i++) {
			for (int x = 0; x < matrice[0].length; x++) {
				String dipendenza1 = oggettoReport.getListaDipendenza1()[i];
				String dipendenza2 = oggettoReport.getListaDipendenza2()[x];
				String valore = ((String[][])oggettoReport.getOggettoReport())[i][x];
				operazioneInternaMatrice(oggettoReport,dipendenza1,dipendenza2, valore);
			}
		}
		return false;
	}

	protected abstract boolean operazioneInternaMatrice(final OggettoReport oggettoReport, final String dipendenza, final String dipendenza2,final String valore);

	public abstract boolean operazioneInternaMappa(String chiave, Double valoreDouble, OggettoReport oggettoReport);

	protected boolean scriviCampiDaMappa(final OggettoReport oggettoReport){
		@SuppressWarnings("unchecked")
		HashMap<String, Double> mappa = (HashMap<String, Double>) oggettoReport.getOggettoReport();
		String[] keys = (String[]) mappa.keySet().toArray(new String[mappa.keySet().size()]);
		for (int i = 0; i < keys.length; i++) {
			String chiave = (String) keys[i];
			Double valoreDouble = mappa.get(chiave);
			operazioneInternaMappa(chiave, valoreDouble, oggettoReport);
		}
		return true;
	}

	protected abstract boolean scriviCampoDaDouble(final OggettoReport oggettoReport);
}
