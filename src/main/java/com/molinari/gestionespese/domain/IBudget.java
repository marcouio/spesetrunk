package com.molinari.gestionespese.domain;

import command.javabeancommand.AbstractOggettoEntita;

public interface IBudget extends AbstractOggettoEntita{
	public int getidBudget();

	public void setidBudget(int idBudget);

	public int getidCategorie();

	public void setidCategorie(int idCategorie);

	public double getpercSulTot();

	public void setpercSulTot(double percSulTot);

	public ICatSpese getCatSpese();

	public void setCatSpese(ICatSpese catSpese);

	public String getnome();

}
