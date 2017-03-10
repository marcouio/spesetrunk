package com.molinari.gestionespese.domain;

import java.io.Serializable;

import command.javabeancommand.AbstractOggettoEntita;

public interface IEntrate extends AbstractOggettoEntita, Serializable{

	public String getdata();

	public void setdata(String data);

	public String getdescrizione();

	public void setdescrizione(String descrizione);

	public String getFisseoVar();

	public void setFisseoVar(String fisseoVar);

	public int getidEntrate();

	public void setidEntrate(int idEntrate);

	public double getinEuro();

	public void setinEuro(double inEuro);

	public String getnome();

	public void setnome(String nome);

	public IUtenti getUtenti();

	public void setUtenti(IUtenti utenti);

	public void setDataIns(String date);

	public String getDataIns();
}
