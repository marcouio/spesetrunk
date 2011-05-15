package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the NOTE database table.
 * 
 */

@Entity
@Table(name = "NOTE")
public class Note implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "data")
	private String            data;

	@Column(name = "dataIns")
	private String            dataIns;

	@Column(name = "descrizione")
	private String            descrizione;

	@Column(name = "idNote")
	private int               idNote;

	@Column(name = "idUtente")
	private int               idUtente;

	@Column(name = "nome")
	private String            nome;

	// bi-directional many-to-one association to Utenti
	@ManyToOne
	@JoinColumns({})
	private Utenti            utenti;

	public Note() {}

	public String getData() {
		return this.data;
	}

	public void setData(String _data_) {
		this.data = _data_;
	}

	public String getDataIns() {
		return this.dataIns;
	}

	public void setDataIns(String _dataIns_) {
		this.dataIns = _dataIns_;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String _descrizione_) {
		this.descrizione = _descrizione_;
	}

	public int getIdNote() {
		return this.idNote;
	}

	public void setIdNote(int _idNote_) {
		this.idNote = _idNote_;
	}

	public int getIdUtente() {
		return this.idUtente;
	}

	public void setIdUtente(int _idUtente_) {
		this.idUtente = _idUtente_;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String _nome_) {
		this.nome = _nome_;
	}

	public void setUtenti(Utenti utenti) {
		this.utenti = utenti;
	}

	public Utenti getUtenti() {
		return utenti;
	}

}