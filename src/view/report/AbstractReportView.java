package view.report;

import javax.swing.JFrame;

public abstract class AbstractReportView extends JFrame {

	private ReportData reportData;
	private static final long serialVersionUID = 1L;

	public AbstractReportView() {
	}
	
	public AbstractReportView(ReportData reportData) {
		this.setReportData(reportData);
	}
	
	
	
	public void setReportData(ReportData reportData) {
		this.reportData = reportData;
	}

	public ReportData getReportData() {
		return reportData;
	}
}
