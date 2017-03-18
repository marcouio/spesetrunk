package com.molinari.gestionespese.domain;

import java.io.Serializable;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;

public interface ILookandfeel extends AbstractOggettoEntita, Serializable{

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
