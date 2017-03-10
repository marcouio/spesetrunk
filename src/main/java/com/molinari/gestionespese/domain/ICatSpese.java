package com.molinari.gestionespese.domain;

import java.util.Set;

import command.javabeancommand.AbstractOggettoEntita;

public interface ICatSpese extends AbstractOggettoEntita {

	public String getdescrizione();

	public void setdescrizione(String descrizione);

	public int getidCategoria();

	public void setidCategoria(int idCategoria);

	public String getimportanza();

	public void setimportanza(String importanza);

	public String getnome();

	public void setnome(String nome);

	public Budget getBudget();

	public void setBudget(Budget budget);

	public IGruppi getGruppi();

	public void setGruppi(IGruppi gruppi);

	public Set<SingleSpesa> getSingleSpesas();

	public void setSingleSpesas(Set<SingleSpesa> singleSpesas);
	@Override
	public String toString();
}
