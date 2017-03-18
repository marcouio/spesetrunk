package com.molinari.gestionespese.domain;

import java.io.Serializable;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;

public interface ISingleSpesa extends AbstractOggettoEntita, Serializable{

	public String getData();

	public void setData(String data);

	public String getdescrizione();

	public void setdescrizione(String descrizione);

	public int getidSpesa();

	public void setidSpesa(int idSpesa);

	public double getinEuro();

	public void setinEuro(double d);

	public String getNome();

	public void setNome(String nome);

	public ICatSpese getCatSpese();

	public void setCatSpese(ICatSpese catSpese);

	public IUtenti getUtenti();

	public void setUtenti(IUtenti utenti);

	public void setDataIns(String dataIns);

	public String getDataIns();

	@Override
	public String toString();

}
