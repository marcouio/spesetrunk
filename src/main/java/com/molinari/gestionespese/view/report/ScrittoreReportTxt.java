package com.molinari.gestionespese.view.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.DBUtil;

public class ScrittoreReportTxt extends ScrittoreReportBase implements IScrittoreReport {

	private PrintStream stream = null;
	private Date dataRegistrazione = new Date();
	private Date dataAggiornamento;
	String trattini = "--------------------------------------------------------------------------";

	public ScrittoreReportTxt(final ReportData reportData) throws FileNotFoundException {
		super(reportData);
	}

	private PrintStream creaStream() throws FileNotFoundException {
		AltreUtil.deleteFileDaDirectory("./", "Rep");
		final String data = DBUtil.dataToString(new Date(), "dd_MM_yyyy_HH_mm_ss");
		FileOutputStream file = new FileOutputStream("Report" + data + ".txt");
		stream = new PrintStream(file);
		return stream;
	}

	@Override
	protected boolean operazioniPreliminari() throws Exception {
		creaStream();
		return true;
		
	}

	@Override
	protected boolean scriviCampoMatrice(final OggettoReport oggettoReport,final String dipendenza, final String dipendenza2,final String valore) {
		final StringBuilder sb = new StringBuilder();
		sb.append(oggettoReport.getNomeOggetto() + " per ");
		sb.append(oggettoReport.getNomeDipendenza2() + " '" + dipendenza2+"' e ");
		sb.append(oggettoReport.getNomeDipendenza() + " '" + dipendenza+"' ");
		sb.append("e': "+valore);

		chiudiSezione(stream, sb.toString());

		return true;
	}

	@Override
	public boolean scriviCampoMappa(final String chiave, final Double valoreDouble,
			final OggettoReport oggettoReport) {

		final StringBuilder sb = new StringBuilder();
		sb.append(oggettoReport.getNomeOggetto());
		sb.append(" per "+ oggettoReport.getNomeDipendenza()+ " '" + chiave);
		sb.append("' vale: " + valoreDouble);

		chiudiSezione(stream, sb.toString());

		return true;
	}

	@Override
	protected boolean scriviCampoDaDouble(OggettoReport oggettoReport) {
		final Double valore = (Double) oggettoReport.getOggettoReport();
		final StringBuilder sb = new StringBuilder();
		sb.append(oggettoReport.getNomeOggetto() + " vale: " + AltreUtil.arrotondaDecimaliDouble(valore));

		chiudiSezione(stream, sb.toString());
		return true;
	}

	private void chiudiSezione(PrintStream stream, final String forFile) {
		stream.print(forFile);
		stream.println(" ");
		stream.print(trattini);
		stream.println(" ");
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
