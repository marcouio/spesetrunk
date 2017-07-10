package com.molinari.gestionespese.domain;

import java.io.Serializable;
import java.util.Set;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;

public interface IGruppi extends AbstractOggettoEntita, Serializable{

	public String getdescrizione();

	public void setdescrizione(String descrizione);

	public int getidGruppo();

	public void setidGruppo(int idGruppo);

	public String getnome();

	public void setnome(String nome);

	public Set<ICatSpese> getCatSpeses();

	public void setCatSpeses(Set<ICatSpese> catSpeses);
	
	public IUtenti getUtenti();

	public void setUtenti(IUtenti utenti);

	@Override
	public String toString();

}
