package com.molinari.gestionespese.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import command.javabeancommand.AbstractOggettoEntita;

/**
 * The persistent class for the ENTRATE database table.
 * 
 */
@Entity
@Table(name = "ENTRATE")
public class Entrate implements AbstractOggettoEntita, Serializable, IEntrate {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELLA = "entrate";
	public static final String ID = "idEntrate";
	public static final String DATA = "data";
	public static final String DATAINS = "dataIns";
	public static final String NOME = "nome";
	public static final String INEURO = "inEuro";
	public static final String DESCRIZIONE = "descrizione";
	public static final String FISSEOVAR = "Fisse_o_Var";
	public static final String IDUTENTE = "idUtente";
	public static final String IDENTRATE = "idEntrate";

	public static final String IMPORTANZA_FISSE = "Fisse";
	public static final String IMPORTANZA_VARIABILI = "Variabili";

	@Column(name = "\"data\"", nullable = false, length = 2000000000)
	private String data;

	@Column(name = "\"dataIns\"", nullable = false, length = 2000000000)
	private String dataIns;

	@Column(name = "\"descrizione\"", nullable = false, length = 2000000000)
	private String descrizione;

	@Column(name = "\"Fisse_o_Var\"", nullable = false, length = 2000000000)
	private String FisseoVar;

	@Id
	@Column(name = "\"idEntrate\"", nullable = false)
	private int idEntrate;

	// @Column(name="idUtente", nullable=false)
	// private int idUtente;

	@Column(name = "\"inEuro\"", nullable = false)
	private double inEuro;

	@Column(name = "\"nome\"", nullable = false, length = 2000000000)
	private String nome;

	// bi-directional many-to-one association to Utenti
	@ManyToOne
	@JoinColumns({})
	private Utenti utenti;

	public Entrate() {
	}

	@Override
	public String getdata() {
		return this.data;
	}

	@Override
	public void setdata(final String data) {
		this.data = data;
	}

	@Override
	public String getdescrizione() {
		return this.descrizione;
	}

	@Override
	public void setdescrizione(final String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String getFisseoVar() {
		return this.FisseoVar;
	}

	@Override
	public void setFisseoVar(final String FisseoVar) {
		this.FisseoVar = FisseoVar;
	}

	@Override
	public int getidEntrate() {
		return this.idEntrate;
	}

	@Override
	public void setidEntrate(final int idEntrate) {
		this.idEntrate = idEntrate;
	}

	@Override
	public double getinEuro() {
		return this.inEuro;
	}

	@Override
	public void setinEuro(final double inEuro) {
		this.inEuro = inEuro;
	}

	@Override
	public String getnome() {
		return this.nome;
	}

	@Override
	public void setnome(final String nome) {
		this.nome = nome;
	}

	@Override
	public Utenti getUtenti() {
		return this.utenti;
	}

	@Override
	public void setUtenti(final Utenti utenti) {
		this.utenti = utenti;
	}

	@Override
	public void setDataIns(final String date) {
		this.dataIns = date;
	}

	@Override
	public String getDataIns() {
		return dataIns;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getidEntrate());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setidEntrate(Integer.parseInt(idEntita));
		
	}

	@Override
	public String getNome() {
		return nome;
	}

}