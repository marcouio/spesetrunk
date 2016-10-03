package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import command.javabeancommand.AbstractOggettoEntita;

/**
 * The persistent class for the NOTE database table.
 * 
 */

@Entity
@Table(name = "NOTE")
public class Note implements AbstractOggettoEntita, Serializable, INote {
	private static final long serialVersionUID = 1L;
	public static final String NOME_TABELLA = "note";
	public static final String NOME = "nome";
	public static final String DATA = "data";
	public static final String DATAINS = "dataIns";
	public static final String DESCRIZIONE = "descrizione";
	public static final String ID = "idNote";
	public static final String IDUTENTE = "idUtente";

	@Column(name = "\"data\"")
	private String data;

	@Column(name = "\"dataIns\"")
	private String dataIns;

	@Column(name = "\"descrizione\"")
	private String descrizione;

	@Id
	@Column(name = "\"idNote\"")
	private int idNote;

	@Column(name = "\"idUtente\"")
	private int idUtente;

	@Column(name = "\"nome\"")
	private String nome;

	// bi-directional many-to-one association to Utenti
	@ManyToOne
	@JoinColumns({})
	private Utenti utenti;

	public Note() {
	}

	@Override
	public String getData() {
		return this.data;
	}

	@Override
	public void setData(final String _data_) {
		this.data = _data_;
	}

	@Override
	public String getDataIns() {
		return this.dataIns;
	}

	@Override
	public void setDataIns(final String _dataIns_) {
		this.dataIns = _dataIns_;
	}

	@Override
	public String getDescrizione() {
		return this.descrizione;
	}

	@Override
	public void setDescrizione(final String _descrizione_) {
		this.descrizione = _descrizione_;
	}

	@Override
	public int getIdNote() {
		return this.idNote;
	}

	@Override
	public void setIdNote(final int _idNote_) {
		this.idNote = _idNote_;
	}

	@Override
	public int getIdUtente() {
		return this.idUtente;
	}

	@Override
	public void setIdUtente(final int _idUtente_) {
		this.idUtente = _idUtente_;
	}

	@Override
	public String getnome() {
		return this.nome;
	}

	@Override
	public void setNome(final String _nome_) {
		this.nome = _nome_;
	}

	@Override
	public void setUtenti(final Utenti utenti) {
		this.utenti = utenti;
	}

	@Override
	public Utenti getUtenti() {
		return utenti;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getIdNote());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setIdNote(Integer.parseInt(idEntita));
		
	}

	@Override
	public String getNome() {
		return nome;
	}

}