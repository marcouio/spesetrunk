package com.molinari.gestionespese.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import command.javabeancommand.AbstractOggettoEntita;

/**
 * The persistent class for the RISPARMIO database table.
 * 
 */
@Entity
@Table(name = "RISPARMIO")
public class Risparmio implements AbstractOggettoEntita, Serializable, IRisparmio {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELLA = "risparmio";
	public static final String ID = "idRisparmio";
	public static final String PERCSULTOT = "PercSulTotale";

	@Id
	@Column(name = "\"idRisparmio\"", nullable = false)
	private int idRisparmio;

	@Column(name = "\"PerSulTotale\"", nullable = false)
	private double PerSulTotale;

	public Risparmio() {
	}

	@Override
	public int getidRisparmio() {
		return this.idRisparmio;
	}

	@Override
	public void setidRisparmio(final int idRisparmio) {
		this.idRisparmio = idRisparmio;
	}

	@Override
	public double getPerSulTotale() {
		return this.PerSulTotale;
	}

	@Override
	public void setPerSulTotale(final double PerSulTotale) {
		this.PerSulTotale = PerSulTotale;
	}

	@Override
	public String getnome() {
		return null;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getidRisparmio());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setidRisparmio(Integer.parseInt(idEntita));
	}

	@Override
	public String getNome() {
		return null;
	}

}