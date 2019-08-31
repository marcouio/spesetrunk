package com.molinari.gestionespese.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;

/**
 * The persistent class for the SINGLESPESA database table.
 *
 */
@Entity
@Table(name = "\"single_spesa\"", schema="DEFAULT")
public class SingleSpesa implements AbstractOggettoEntita, Serializable, ISingleSpesa {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELLA = "single_spesa";
	public static final String ID = "idSpesa";
	public static final String COL_DATA = "Data";
	public static final String COL_DATAINS = "dataIns";
	public static final String COL_IDCATEGORIE = "idCategorie";
	public static final String COL_INEURO = "inEuro";
	public static final String COL_DESCRIZIONE = "descrizione";
	public static final String COL_NOME = "nome";
	public static final String COL_IDUTENTE = "idUtente";

	@Column(name = "\"Data\"", nullable = false, length = 2000000000)
	private String data;

	@Column(name = "\"descrizione\"", nullable = false, length = 2000000000)
	private String descrizione;

	@Id
	@Column(name = "\"idSpesa\"", nullable = false)
	private int idSpesa;

	@Column(name = "\"inEuro\"", nullable = false)
	private double inEuro;

	@Column(name = "\"nome\"", nullable = false, length = 2000000000)
	private String nome;

	@Column(name = "\"dataIns\"", nullable = false, length = 2000000000)
	private String dataIns;

	// bi-directional many-to-one association to CatSpese
	@ManyToOne(targetEntity=CatSpese.class)
	@JoinColumn(name="\"idCategorie\"")
	private ICatSpese catSpese;

	// bi-directional many-to-one association to Utenti
	@ManyToOne(targetEntity=Utenti.class)
	@JoinColumn(name="\"idUtente\"")
	private IUtenti utenti;

	@Override
	public String getData() {
		return this.data;
	}

	@Override
	public void setData(final String data) {
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
	public int getidSpesa() {
		return this.idSpesa;
	}

	@Override
	public void setidSpesa(final int idSpesa) {
		this.idSpesa = idSpesa;
	}

	@Override
	public double getinEuro() {
		return this.inEuro;
	}

	@Override
	public void setinEuro(final double d) {
		this.inEuro = d;
	}

	@Override
	public String getNome() {
		return this.nome;
	}

	@Override
	public void setNome(final String nome) {
		this.nome = nome;
	}

	@Override
	public ICatSpese getCatSpese() {
		return this.catSpese;
	}

	@Override
	public void setCatSpese(final ICatSpese catSpese) {
		this.catSpese = catSpese;
	}

	@Override
	public IUtenti getUtenti() {
		return this.utenti;
	}

	@Override
	public void setUtenti(final IUtenti utenti) {
		this.utenti = utenti;
	}

	@Override
	public void setDataIns(final String dataIns) {
		this.dataIns = dataIns;
	}

	@Override
	public String getDataIns() {
		return dataIns;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getidSpesa());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setidSpesa(Integer.parseInt(idEntita));
	}
}