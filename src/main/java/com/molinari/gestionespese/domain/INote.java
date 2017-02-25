package com.molinari.gestionespese.domain;

public interface INote {

	public String getData();

	public void setData(String _data_);

	public String getDataIns();

	public void setDataIns(String _dataIns_);

	public String getDescrizione();

	public void setDescrizione(String _descrizione_);

	public int getIdNote();

	public void setIdNote(int _idNote_);

	public int getIdUtente();

	public void setIdUtente(int _idUtente_);

	public String getnome();

	public void setNome(String _nome_);

	public void setUtenti(Utenti utenti);

	public Utenti getUtenti();
}
