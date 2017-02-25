package com.molinari.gestionespese.view.report;

public class ScrittoreReportHtml extends ScrittoreReportBase implements
IScrittoreReport {

	public ScrittoreReportHtml(final ReportData reportData) {
		super(reportData);
	}

	@Override
	protected boolean operazioniPreliminari() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean scriviCampoMatrice(final OggettoReport oggettoReport,
			final String dipendenza, final String dipendenza2, final String valore) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scriviCampoMappa(final String chiave, final Double valoreDouble,
			final OggettoReport oggettoReport) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean scriviCampoDaDouble(final OggettoReport oggettoReport) {
		// TODO Auto-generated method stub
		return false;
	}

}
