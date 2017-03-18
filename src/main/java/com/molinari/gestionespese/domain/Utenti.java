package com.molinari.gestionespese.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;

/**
 * The persistent class for the UTENTI database table.
 *
 */
@Entity
@Table(name = "UTENTI")
public class Utenti implements AbstractOggettoEntita, Serializable, IUtenti {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELLA = "utenti";
	public static final String ID = "idUtente";
	public static final String COL_USERNAME = "username";
	public static final String COL_PASSWOR = "password";
	public static final String COL_NOME = "nome";
	public static final String COL_COGNOME = "cognome";

	@Id
	@Column(name = "\"idUtente\"", nullable = false)
	private int idUtente;

	@Column(name = "\"password\"", nullable = false, length = 2000000000)
	private String password;

	@Column(name = "\"username\"", nullable = false, length = 2000000000)
	private String username;

	@Column(name = "\"nome\"", nullable = false, length = 2000000000)
	private String nome;

	@Column(name = "\"cognome\"", nullable = false, length = 2000000000)
	private String cognome;

	// bi-directional many-to-one association to Entrate
	@OneToMany(mappedBy = "utenti")
	private Set<Entrate> entrates;

	// bi-directional many-to-one association to SingleSpesa
	@OneToMany(mappedBy = "utenti")
	private Set<SingleSpesa> singleSpesas;

	public Utenti() {
		//do nothing
	}

	@Override
	public int getidUtente() {
		return this.idUtente;
	}

	@Override
	public void setidUtente(final int idUtente) {
		this.idUtente = idUtente;
	}

	@Override
	public String getpassword() {
		return this.password;
	}

	@Override
	public void setpassword(final String password) {
		this.password = password;
	}

	@Override
	public String getusername() {
		return this.username;
	}

	@Override
	public void setusername(final String username) {
		this.username = username;
	}

	@Override
	public Set<Entrate> getEntrates() {
		return this.entrates;
	}

	@Override
	public void setEntrates(final Set<Entrate> entrates) {
		this.entrates = entrates;
	}

	@Override
	public Set<SingleSpesa> getSingleSpesas() {
		return this.singleSpesas;
	}

	@Override
	public void setSingleSpesas(final Set<SingleSpesa> singleSpesas) {
		this.singleSpesas = singleSpesas;
	}

	@Override
	public void setNome(final String nome) {
		this.nome = nome;
	}

	@Override
	public String getnome() {
		return nome;
	}

	@Override
	public void setCognome(final String cognome) {
		this.cognome = cognome;
	}

	@Override
	public String getCognome() {
		return cognome;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getidUtente());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setidUtente(Integer.parseInt(idEntita));
	}

	@Override
	public String getNome() {
		return nome;
	}

}