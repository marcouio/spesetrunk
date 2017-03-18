package com.molinari.gestionespese.view.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.logging.Level;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.DBUtil;

import com.molinari.utility.controller.ControlloreBase;

public class ScrittoreReportTxt extends ScrittoreReportBase implements IScrittoreReport {

	private PrintStream stream = null;
	private Date dataRegistrazione = new Date();
	private Date dataAggiornamento;
	private String trattini = "--------------------------------------------------------------------------";
	private FileOutputStream file;

	public ScrittoreReportTxt(final ReportData reportData) throws FileNotFoundException {
		super(reportData);
	}

	private PrintStream creaStream() {
		try {
			AltreUtil.deleteFileDaDirectory("./", "Rep");
			final String data = DBUtil.dataToString(new Date(), "dd_MM_yyyy_HH_mm_ss");
			file = creaFis(data);
			stream = new PrintStream(file);
		} catch (FileNotFoundException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return stream;
	}

	private FileOutputStream creaFis(final String data) throws FileNotFoundException {
		return new FileOutputStream("Report" + data + ".txt");
	}

	@Override
	protected boolean operazioniPreliminari() {
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

	@Override
	protected void terminate() {
		try {
			stream.close();
			file.close();
		} catch (IOException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
