package com.molinari.gestionespese.domain;

import java.io.Serializable;

public interface IRisparmio extends Serializable{

	public int getidRisparmio();

	public void setidRisparmio(int idRisparmio);

	public double getPerSulTotale();

	public void setPerSulTotale(double perSulTotale);

	public String getnome();

}
