package view.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

import business.AltreUtil;
import business.DBUtil;

public class ScrittoreReportTxt implements IScrittoreReport{

	private FileOutputStream file = null;
	private Date dataRegistrazione = new Date();
	private Date dataAggiornamento;
	
	public ScrittoreReportTxt() throws FileNotFoundException {
		
	}
	
	private PrintStream creaStream()
			throws FileNotFoundException {
		AltreUtil.deleteFileDaDirectory("./", "Rep");
		final String data = DBUtil.dataToString(new Date(), "dd_MM_yyyy_HH_mm_ss");
		file = new FileOutputStream("Report" + data + ".txt");
		return new PrintStream(file);
	}
	
	@Override
	public boolean generaReport(final ReportData reportData) throws Exception {
		PrintStream stream = creaStream();
		stream.println("Report Entrate/Uscite realizzato il : "
				+ DBUtil.dataToString(dataRegistrazione, "dd/MM/yyyy HH:mm"));
		
		
		
		return false;
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
