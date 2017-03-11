package com.molinari.gestionespese.view.report;

import java.util.HashMap;
import java.util.List;

public abstract class ScrittoreReportBase implements IScrittoreReport{

	private final ReportData reportData;

	public ScrittoreReportBase(final ReportData reportData) {
		this.reportData = reportData;
	}

	/**
	 * In base al tipo di oggettoReport passato decide quale strada prendere per registrare l'oggetto sul report.
	 *
	 * @param oggettoReport
	 * @return
	 */
	public boolean smista(final OggettoReport oggettoReport){
		if(oggettoReport.getTipo().equals(OggettoReport.TIPO_DOUBLE)){
			return scriviCampoDaDouble(oggettoReport);
		}else if(oggettoReport.getTipo().equals(OggettoReport.TIPO_MAPPA)){
			return scorriMappa(oggettoReport);
		}else if(oggettoReport.getTipo().equals(OggettoReport.TIPO_MATRICE)){
			return scorriMatrice(oggettoReport);
		}
		return false;
	}

	@Override
	public boolean generaReport(){
		boolean ok = operazioniPreliminari();
		final List<OggettoReport> listaOggetti = reportData.getListaOggetti();
		for (OggettoReport oggettoReport : listaOggetti) {
			if(!smista(oggettoReport)){
				ok = false;
			}
		}
		
		terminate();
		return ok;
	}

	protected abstract void terminate();

	protected abstract boolean operazioniPreliminari();


	/**
	 * Scorro gli elementi della matrice e li passa al metodo specifico di scrittura per registrarli sul report
	 *
	 * @param oggettoReport
	 * @return
	 */
	protected boolean scorriMatrice(final OggettoReport oggettoReport){
		final String[][] matrice = (String[][]) oggettoReport.getOggettoReport();
		for (int i = 0; i < matrice.length; i++) {
			for (int x = 0; x < matrice[0].length; x++) {
				final String dipendenza1 = oggettoReport.getListaDipendenza1()[i];
				final String dipendenza2 = oggettoReport.getListaDipendenza2()[x];
				final String valore = ((String[][])oggettoReport.getOggettoReport())[i][x];
				scriviCampoMatrice(oggettoReport,dipendenza1,dipendenza2, valore);
			}
		}
		return false;
	}

	/**
	 * Operazione che viene eseguita su un singolo campo della matrice per generare un campo sul report.
	 * Da implementare sullo specifico scrittore di report.
	 *
	 * @param oggettoReport
	 * @param dipendenza
	 * @param dipendenza2
	 * @param valore
	 * @return
	 */
	protected abstract boolean scriviCampoMatrice(final OggettoReport oggettoReport, final String dipendenza, final String dipendenza2,final String valore);

	/**
	 * Operazione che viene eseguita su un singolo campo della mappa per generare un campo sul report.
	 * Da implementare sullo specifico scrittore di report.
	 *
	 * @param chiave
	 * @param valoreDouble
	 * @param oggettoReport
	 * @return
	 */
	public abstract boolean scriviCampoMappa(String chiave, Double valoreDouble, OggettoReport oggettoReport);

	/**
	 * Scorre gli elementi di una mappa e li passa al metodo specifico di scrittura per registrarli sul report.
	 *
	 * @param oggettoReport
	 * @return
	 */
	protected boolean scorriMappa(final OggettoReport oggettoReport){
		@SuppressWarnings("unchecked")
		final
		HashMap<String, Double> mappa = (HashMap<String, Double>) oggettoReport.getOggettoReport();
		final String[] keys = mappa.keySet().toArray(new String[mappa.keySet().size()]);
		for (final String chiave : keys) {
			final Double valoreDouble = mappa.get(chiave);
			scriviCampoMappa(chiave, valoreDouble, oggettoReport);
		}
		return true;
	}

	protected abstract boolean scriviCampoDaDouble(final OggettoReport oggettoReport);
}
