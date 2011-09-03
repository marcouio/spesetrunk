package view.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

import business.AltreUtil;
import business.DBUtil;

public class ScrittoreReportTxt extends ScrittoreReportBase implements IScrittoreReport {

	private PrintStream stream = null;
	private FileOutputStream file = null;
	private Date dataRegistrazione = new Date();
	private Date dataAggiornamento;
	String trattini = "--------------------------------------------------------------------------";

	public ScrittoreReportTxt(final ReportData reportData) throws FileNotFoundException {
		super(reportData);
	}

	private PrintStream creaStream() throws FileNotFoundException {
		AltreUtil.deleteFileDaDirectory("./", "Rep");
		final String data = DBUtil.dataToString(new Date(), "dd_MM_yyyy_HH_mm_ss");
		file = new FileOutputStream("Report" + data + ".txt");
		stream = new PrintStream(file);
		return stream;
	}
	
	@Override
	protected boolean operazioniPreliminari() throws Exception {
		if(creaStream()!=null)return true;
		return false;
	}

	@Override
	protected boolean operazioneInternaMatrice(final OggettoReport oggettoReport,final String dipendenza, final String dipendenza2,final String valore) {
		final StringBuffer sb = new StringBuffer();
		sb.append(oggettoReport.getNomeOggetto() + " per ");
		sb.append(oggettoReport.getNomeDipendenza2() + " '" + dipendenza2+"' e ");
		sb.append(oggettoReport.getNomeDipendenza() + " '" + dipendenza+"' ");
		sb.append("e': "+valore);
		
		chiudiSezione(stream, sb.toString());
		
		return true;
	}

	@Override
	public boolean operazioneInternaMappa(final String chiave, final Double valoreDouble,
			final OggettoReport oggettoReport) {
		
		StringBuffer sb = new StringBuffer();
		sb.append(oggettoReport.getNomeOggetto());
		sb.append(" per "+ oggettoReport.getNomeDipendenza()+ " '" + chiave);
		sb.append("' vale: " + valoreDouble);
		
		chiudiSezione(stream, sb.toString());
		
		return true;
	}

	@Override
	protected boolean scriviCampoDaDouble(OggettoReport oggettoReport) {
		Double valore = (Double) oggettoReport.getOggettoReport();
		final StringBuffer sb = new StringBuffer();
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
