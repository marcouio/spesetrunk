package com.molinari.gestionespese.domain;

public interface IBudget {
	public int getidBudget();

	public void setidBudget(int idBudget);

	public int getidCategorie();

	public void setidCategorie(int idCategorie);

	public double getpercSulTot();

	public void setpercSulTot(double percSulTot);

	public CatSpese getCatSpese();

	public void setCatSpese(CatSpese catSpese);

	public String getnome();

}
