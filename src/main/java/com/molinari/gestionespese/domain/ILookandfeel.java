package com.molinari.gestionespese.domain;

import command.javabeancommand.AbstractOggettoEntita;

public interface ILookandfeel extends AbstractOggettoEntita{

	public int getidLook();

	public void setidLook(int idLook);

	public String getnome();

	public void setnome(String nome);

	public int getusato();

	public void setusato(int usato);

	public String getvalore();

	public void setvalore(String valore);

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString();
}
