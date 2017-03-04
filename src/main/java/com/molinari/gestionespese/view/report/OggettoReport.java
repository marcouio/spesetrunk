package com.molinari.gestionespese.view.report;

public class OggettoReport {

	public static final String TIPO_MATRICE = "matrice";
	public static final String TIPO_DOUBLE = "double";
	public static final String TIPO_MAPPA = "mappa";

	private String tipo;
	private Object fieldObjReport;
	private String nomeOggetto;
	private String nomeDipendenza1;
	private String nomeDipendenza2;
	private String[] listaDipendenza1;
	private String[] listaDipendenza2;

	public OggettoReport(final String tipo,final Object oggettoReport, final String nomeOggetto) {
		this(tipo, oggettoReport,nomeOggetto,null,null,null,null);
	}

	public OggettoReport(final String tipo,final Object oggettoReport, final String nomeOggetto, final String nomeDipendenza, final String[] listaDipendenza1) {
		this(tipo, oggettoReport,nomeOggetto,nomeDipendenza,null,listaDipendenza1,null);
	}

	public OggettoReport(final String tipo,final Object oggettoReport, final String nomeOggetto,
			final String nomeDipendenza, final String nomeDipendenza2, final String[] listaDipendenza1,
			final String[] listaDipendenza2) {
		this.tipo = tipo;
		this.fieldObjReport = oggettoReport;
		this.setNomeDipendenza(nomeDipendenza);
		this.setNomeDipendenza2(nomeDipendenza2);
		this.setNomeOggetto(nomeOggetto);
		this.setListaDipendenza1(listaDipendenza1);
		this.setListaDipendenza2(listaDipendenza2);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Object getOggettoReport() {
		return fieldObjReport;
	}

	public void setOggettoReport(Object oggettoReport) {
		this.fieldObjReport = oggettoReport;
	}

	public void setNomeOggetto(String nomeOggetto) {
		this.nomeOggetto = nomeOggetto;
	}

	public String getNomeOggetto() {
		return nomeOggetto;
	}

	public void setNomeDipendenza(String nomeDipendenza) {
		this.nomeDipendenza1 = nomeDipendenza;
	}

	public String getNomeDipendenza() {
		return nomeDipendenza1;
	}

	public void setNomeDipendenza2(String nomeDipendenza2) {
		this.nomeDipendenza2 = nomeDipendenza2;
	}

	public String getNomeDipendenza2() {
		return nomeDipendenza2;
	}

	public void setListaDipendenza1(String[] listaDipendenza1) {
		this.listaDipendenza1 = listaDipendenza1;
	}

	public String[] getListaDipendenza1() {
		return listaDipendenza1;
	}

	public void setListaDipendenza2(String[] listaDipendenza2) {
		this.listaDipendenza2 = listaDipendenza2;
	}

	public String[] getListaDipendenza2() {
		return listaDipendenza2;
	}
}
