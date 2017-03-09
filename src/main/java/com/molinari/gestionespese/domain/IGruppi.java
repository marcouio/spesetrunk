package com.molinari.gestionespese.domain;

import java.util.Set;

import command.javabeancommand.AbstractOggettoEntita;

public interface IGruppi extends AbstractOggettoEntita{

	public String getdescrizione();

	public void setdescrizione(String descrizione);

	public int getidGruppo();

	public void setidGruppo(int idGruppo);

	public String getnome();

	public void setnome(String nome);

	public Set<CatSpese> getCatSpeses();

	public void setCatSpeses(Set<CatSpese> catSpeses);

	@Override
	public String toString();

}
