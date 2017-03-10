package com.molinari.gestionespese.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the BUDGET database table.
 *
 */

@Entity
@Table(name = "budget")
public class Budget implements IBudget, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELLA = "budget";
	public static final String ID = "idBudget";
	public static final String COL_IDCATEGORIE = "idCategorie";
	public static final String COL_PERCSULTOT = "percSulTot";

	@Id
	@Column(name = "\"idBudget\"", nullable = false)
	private int idBudget;

	@Column(name = "\"idCategorie\"", nullable = false)
	private int idCategorie;

	@Column(name = "\"percSulTot\"", nullable = false)
	private double percSulTot;

	// bi-directional one-to-one association to CatSpese
	@OneToOne
	@JoinColumns({})
	private ICatSpese catSpese;

	public Budget() {
		//do nothing
	}

	public int getidBudget() {
		return this.idBudget;
	}

	public void setidBudget(final int idBudget) {
		this.idBudget = idBudget;
	}

	public int getidCategorie() {
		return this.idCategorie;
	}

	public void setidCategorie(final int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public double getpercSulTot() {
		return this.percSulTot;
	}

	public void setpercSulTot(final double percSulTot) {
		this.percSulTot = percSulTot;
	}

	public ICatSpese getCatSpese() {
		return this.catSpese;
	}

	public void setCatSpese(final ICatSpese catSpese) {
		this.catSpese = catSpese;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getidBudget());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setidBudget(Integer.parseInt(idEntita));
	}

	@Override
	public String getNome() {
		return getnome();
	}

	@Override
	public String getnome() {
		return null;
	}

}