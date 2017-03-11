package com.molinari.gestionespese.view.report;

public class ScrittoreReportHtml extends ScrittoreReportBase implements
IScrittoreReport {

	public ScrittoreReportHtml(final ReportData reportData) {
		super(reportData);
	}

	@Override
	protected boolean operazioniPreliminari() {
		return false;
	}

	@Override
	protected boolean scriviCampoMatrice(final OggettoReport oggettoReport,
			final String dipendenza, final String dipendenza2, final String valore) {
		return false;
	}

	@Override
	public boolean scriviCampoMappa(final String chiave, final Double valoreDouble,
			final OggettoReport oggettoReport) {
		return false;
	}

	@Override
	protected boolean scriviCampoDaDouble(final OggettoReport oggettoReport) {
		return false;
	}

	@Override
	protected void terminate() {
		//do nothing yet
	}

}
