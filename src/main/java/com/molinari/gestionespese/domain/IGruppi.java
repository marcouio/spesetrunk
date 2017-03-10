package com.molinari.gestionespese.domain;

import java.io.Serializable;
import java.util.Set;

import command.javabeancommand.AbstractOggettoEntita;

public interface IGruppi extends AbstractOggettoEntita, Serializable{

	public String getdescrizione();

	public void setdescrizione(String descrizione);

	public int getidGruppo();

	public void setidGruppo(int idGruppo);

	public String getnome();

	public void setnome(String nome);

	public Set<ICatSpese> getCatSpeses();

	public void setCatSpeses(Set<ICatSpese> catSpeses);

	@Override
	public String toString();

}
