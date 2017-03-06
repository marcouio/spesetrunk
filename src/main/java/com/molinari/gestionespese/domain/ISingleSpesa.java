package com.molinari.gestionespese.domain;

import command.javabeancommand.AbstractOggettoEntita;

public interface ISingleSpesa extends AbstractOggettoEntita{

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

	public CatSpese getCatSpese();

	public void setCatSpese(CatSpese catSpese);

	public Utenti getUtenti();

	public void setUtenti(Utenti utenti);

	public void setDataIns(String dataIns);

	public String getDataIns();

	@Override
	public String toString();

}
