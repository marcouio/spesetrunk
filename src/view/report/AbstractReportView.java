package view.report;

import java.util.HashMap;

import javax.swing.JFrame;

public abstract class AbstractReportView extends JFrame {

	protected ReportData reportData;
	private static final long serialVersionUID = 1L;

	public AbstractReportView() {
	}

	public AbstractReportView(final ReportData reportData) {
		this.setReportData(reportData);
	}

	public void setUsciteAnnuali(final boolean hasUsciteAnnuali) {
		if (!hasUsciteAnnuali) {
			reportData.setUsciteAnnuali(null);
		} else {
			reportData.setUsciteAnnuali(reportData.generaUsciteAnnuali());
		}
	}

	public Double getUsciteAnnuali() {
		return reportData.getUsciteAnnuali();
	}

	public void setEntrateAnnuali(final boolean hasEntrateAnnuali) {
		if (!hasEntrateAnnuali) {
			reportData.setEntrateAnnuali(null);
		} else {
			reportData.setEntrateAnnuali(reportData.generaEntrateAnnuali());
		}
	}

	public Double getEntrateAnnuali() {
		return reportData.getEntrateAnnuali();
	}

	public void setEntrateMensili(final boolean hasEntrateMensili) {
		if (!hasEntrateMensili) {
			reportData.setEntrateMese(null);
		} else {
			reportData.setEntrateMese(reportData.generaEntrateMese());
		}
	}

	public HashMap<String, Double> getEntrateMensili() {
		return reportData.getEntrateMese();
	}

	public void setUsciteMensili(final boolean hasUsciteMensili) {
		if (!hasUsciteMensili) {
			reportData.setUsciteMese(null);
		} else {
			reportData.setUsciteMese(reportData.generaUsciteMese());
		}
	}

	public HashMap<String, Double> getUsciteMensili() {
		return reportData.getUsciteMese();
	}

	public void setEntrateCatMensili(final boolean hasEntrateCatMensili) {
		if (!hasEntrateCatMensili) {
			reportData.setEntrateCatMensili(null);
		} else {
			reportData.setEntrateCatMensili(reportData.generaEntrateCatMensili());
		}
	}

	public String[][] getEntrateCatMensili() {
		return reportData.getEntrateCatMensili();
	}

	public void setUsciteCatMensili(final boolean hasUsciteCatMensili) {
		if (!hasUsciteCatMensili) {
			reportData.setUsciteCatMensili(null);
		} else {
			reportData.setUsciteCatMensili(reportData.generaUsciteCatMensili());
		}
	}

	public String[][] getUsciteCatMensili() {
		return reportData.getUsciteCatMensili();
	}

	public void setEntrateCatAnnuali(final boolean hasEntrateCatAnnuali) {
		if (!hasEntrateCatAnnuali) {
			reportData.setEntrateCatAnnuali(null);
		} else {
			reportData.setEntrateCatAnnuali(reportData.generaEntrateCatAnnuali());
		}
	}

	public HashMap<String, Double> getEntrateCatAnnuali() {
		return reportData.getEntrateCatAnnuali();
	}

	public void setUsciteCatAnnuali(final boolean hasUsciteCatAnnuali) {
		if (!hasUsciteCatAnnuali) {
			reportData.setUsciteCatAnnuali(null);
		} else {
			reportData.setUsciteCatAnnuali(reportData.generaUsciteCatAnnuali());
		}
	}

	public HashMap<String, Double> getUsciteCatAnnuali() {
		return reportData.getUsciteCatAnnuali();
	}

	public void setUsciteVariabili(final boolean hasUsciteVariabili) {
		if (!hasUsciteVariabili) {
			reportData.setUsciteVariabili(null);
		} else {
			reportData.setUsciteVariabili(reportData.generaUsciteVariabili());
		}
	}

	public Double getUsciteVariabili() {
		return reportData.getUsciteVariabili();
	}

	public void setUsciteFutili(final boolean hasUsciteFutili) {
		if (!hasUsciteFutili) {
			reportData.setUsciteFutili(null);
		} else {
			reportData.setUsciteFutili(reportData.generaUsciteFutili());
		}
	}

	public Double getUsciteFutili() {
		return reportData.getUsciteFutili();
	}

	public void setAvanzo(final boolean hasAvanzo) {
		if (!hasAvanzo) {
			reportData.setAvanzo(null);
		} else {
			reportData.generaAvanzo();
		}
	}

	public Double getAvanzo() {
		return reportData.getAvanzo();
	}

	public void setMediaEntrate(final boolean hasMediaEntrate) {
		if (!hasMediaEntrate) {
			reportData.setMediaEntrate(null);
		} else {
			reportData.setMediaEntrate(reportData.generaMediaEntrate());
		}
	}

	public Double getMediaEntrate() {
		return reportData.getMediaEntrate();
	}

	public void setMediaUscite(final boolean hasMediaUscite) {
		if (!hasMediaUscite) {
			reportData.setMediaUscite(null);
		} else {
			reportData.setMediaUscite(reportData.generaMediaUscite());
		}
	}

	public Double getMediaUscite() {
		return reportData.getMediaUscite();
	}

	public void setReportData(ReportData reportData) {
		this.reportData = reportData;
	}

	public ReportData getReportData() {
		return reportData;
	}
}
