package com.molinari.gestionespese.domain;

import java.util.Set;

import command.javabeancommand.AbstractOggettoEntita;


public interface IUtenti extends AbstractOggettoEntita{

	public int getidUtente();

	public void setidUtente(int idUtente);

	public String getpassword();

	public void setpassword(String password);

	public String getusername();

	public void setusername(String username);

	public Set<Entrate> getEntrates();

	public void setEntrates(Set<Entrate> entrates);

	public Set<SingleSpesa> getSingleSpesas();

	public void setSingleSpesas(Set<SingleSpesa> singleSpesas);

	public void setNome(String nome);

	public String getnome();

	public void setCognome(String cognome);

	public String getCognome();
}
