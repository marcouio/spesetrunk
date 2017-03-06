package com.molinari.gestionespese.domain;

import command.javabeancommand.AbstractOggettoEntita;

public interface INote extends AbstractOggettoEntita{

	public String getData();

	public void setData(String data);

	public String getDataIns();

	public void setDataIns(String dataIns);

	public String getDescrizione();

	public void setDescrizione(String descrizione);

	public int getIdNote();

	public void setIdNote(int idNote);

	public int getIdUtente();

	public void setIdUtente(int idUtente);

	public String getNome();

	public void setNome(String nome);

	public void setUtenti(Utenti utenti);

	public Utenti getUtenti();
}
