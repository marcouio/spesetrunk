package com.molinari.gestionespese.view.report;

import java.awt.Container;
import java.util.Map;

import javax.swing.JPanel;

import com.molinari.gestionespese.business.cache.CacheCategorie;

import grafica.componenti.contenitori.FrameBase;
import grafica.componenti.contenitori.PannelloBase;

public abstract class AbstractReportView  {

	private Container container;
	private static final String CATEGORIA = "Categoria";
	protected ReportData reportData;

	public AbstractReportView(FrameBase cont) {
		container = new PannelloBase(cont.getContentPane());
	}

	public AbstractReportView(final ReportData reportData) {
		this.setReportData(reportData);
		container = new JPanel();
	}

	public void inserisciUsciteAnnuali(final boolean hasUsciteAnnuali) {
		if (hasUsciteAnnuali) {
			final Double usciteAnnuali = reportData.generaUsciteAnnuali();
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, usciteAnnuali, "Uscite Annuali");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciEntrateAnnuali(final boolean hasEntrateAnnuali) {
		if (hasEntrateAnnuali) {
			final Double entrateAnnuali = reportData.generaEntrateAnnuali();
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, entrateAnnuali, "Entrate Annuali");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciEntrateMensili(final boolean hasEntrateMensili) {
		if (hasEntrateMensili) {
			final Map<String, Double> entrateMese = reportData.generaEntrateMese();
			final String[]mesi = Mesi.getListaMesi().toArray(new String[Mesi.getListaMesi().size()]);
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MAPPA, entrateMese, "Entrate", "Mese", mesi);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciUsciteMensili(final boolean hasUsciteMensili) {
		if (hasUsciteMensili) {
			final Map<String, Double> usciteMese = reportData.generaUsciteMese();
			final String[]mesi = Mesi.getListaMesi().toArray(new String[Mesi.getListaMesi().size()]);
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MAPPA, usciteMese, "Uscite", "Mese",mesi);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciEntrateCatMensili(final boolean hasEntrateCatMensili) {
		if (hasEntrateCatMensili) {
			final String[][] entrateCatMensili = reportData.generaEntrateCatMensili();
			final String[] categorie = new String[]{"Fisse","Variabili"};
			final String[]mesi = Mesi.getListaMesi().toArray(new String[Mesi.getListaMesi().size()]);
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MATRICE, entrateCatMensili, "Entrate", "Mese", CATEGORIA, mesi, categorie);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciUsciteCatMensili(final boolean hasUsciteCatMensili) {
		if (hasUsciteCatMensili) {
			final String[][] usciteCatMensili = reportData.generaUsciteCatMensili();
			final String[]categorie = CacheCategorie.getSingleton().getArrayCategorie();
			final String[]mesi = Mesi.getListaMesi().toArray(new String[Mesi.getListaMesi().size()]);
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MATRICE, usciteCatMensili, "Uscite", "Mese", CATEGORIA, mesi, categorie);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciEntrateCatAnnuali(final boolean hasEntrateCatAnnuali) {
		if (hasEntrateCatAnnuali) {
			final Map<String, Double> entrateCatAnnuali = reportData.generaEntrateCatAnnuali();
			final String[] categorie = new String[]{"Fisse","Variabili"};
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MAPPA, entrateCatAnnuali, "Entrate Annuali", CATEGORIA, categorie);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciUsciteCatAnnuali(final boolean hasUsciteCatAnnuali) {
		if (hasUsciteCatAnnuali) {
			final Map<String, Double> usciteCatAnnuali = reportData.generaUsciteCatAnnuali();
			final String[]categorie = CacheCategorie.getSingleton().getArrayCategorie();
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MAPPA, usciteCatAnnuali, "Uscite Annuali", CATEGORIA,categorie);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciUsciteVariabili(final boolean hasUsciteVariabili) {
		if (hasUsciteVariabili) {
			final Double usciteVariabili = reportData.generaUsciteVariabili();
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, usciteVariabili, "Uscite Variabili");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciUsciteFutili(final boolean hasUsciteFutili) {
		if (hasUsciteFutili) {
			final Double usciteFutili = reportData.generaUsciteFutili();
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, usciteFutili, "Uscite Futili");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciAvanzo(final boolean hasAvanzo) {
		if (hasAvanzo) {
			final Double avanzo = reportData.generaAvanzo();
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, avanzo, "Saldo");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciMediaEntrate(final boolean hasMediaEntrate) {
		if (hasMediaEntrate) {
			final Double mediaEntrate = reportData.generaMediaEntrate();
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, mediaEntrate, "Media Mensile Entrate");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciMediaUscite(final boolean hasMediaUscite) {
		if (hasMediaUscite) {
			final Double mediaUscite = reportData.generaMediaUscite();
			final OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, mediaUscite, "Media Mensile Uscite");
			reportData.inserisci(oggettoReport);
		}
	}

	public void setReportData(ReportData reportData) {
		this.reportData = reportData;
	}

	public ReportData getReportData() {
		return reportData;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}
}
