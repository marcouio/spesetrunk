package com.molinari.gestionespese.domain;

import java.util.Set;

public interface ICatSpese {

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

	public Gruppi getGruppi();

	public void setGruppi(Gruppi gruppi);

	public Set<SingleSpesa> getSingleSpesas();

	public void setSingleSpesas(Set<SingleSpesa> singleSpesas);
	@Override
	public String toString();
}
