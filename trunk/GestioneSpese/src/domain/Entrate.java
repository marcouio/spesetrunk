package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import domain.wrapper.IEntrate;


/**
 * The persistent class for the ENTRATE database table.
 * 
 */
@Entity
@Table(name="ENTRATE")
public class Entrate extends AbstractOggettoEntita implements Serializable, IEntrate {
	private static final long serialVersionUID = 1L;
	
	public static final String NOME_TABELLA = "entrate";
	public static final String ID = "idEntrate";
	public static final String DATA = "data";
	public static final String DATAINS = "dataIns";
	public static final String NOME = "nome";
	public static final String INEURO = "inEuro";
	public static final String DESCRIZIONE = "descrizione";
	public static final String FISSEOVAR = "Fisse_o_Var";
	public static final String IDUTENTE = "idUtente";
	public static final String IDENTRATE = "idEntrate";
	
	public static final String IMPORTANZA_FISSE = "Fisse";
	public static final String IMPORTANZA_VARIABILI = "Variabili";

	@Column(name="data", nullable=false, length=2000000000)
	private String data;
	
	@Column(name="dataIns", nullable=false, length=2000000000)
	private String dataIns;

	@Column(name="descrizione", nullable=false, length=2000000000)
	private String descrizione;

	@Column(name="Fisse_o_Var", nullable=false, length=2000000000)
	private String FisseoVar;

	@Id
	@Column(name="idEntrate", nullable=false)
	private int idEntrate;

//	@Column(name="idUtente", nullable=false)
//	private int idUtente;

	@Column(name="inEuro", nullable=false)
	private double inEuro;

	@Column(name="nome", nullable=false, length=2000000000)
	private String nome;

	//bi-directional many-to-one association to Utenti
    @ManyToOne
	@JoinColumns({
		})
	private Utenti utenti;

    public Entrate() {
    	if(idEntrate!=0)
    		this.idEntita = Integer.toString(idEntrate);
    }

	public String getdata() {
		return this.data;
	}

	public void setdata(String data) {
		this.data = data;
	}

	public String getdescrizione() {
		return this.descrizione;
	}

	public void setdescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFisseoVar() {
		return this.FisseoVar;
	}

	public void setFisseoVar(String FisseoVar) {
		this.FisseoVar = FisseoVar;
	}

	public int getidEntrate() {
		return this.idEntrate;
	}

	public void setidEntrate(int idEntrate) {
		this.idEntita = Integer.toString(idEntrate);
		this.idEntrate = idEntrate;
	}

	public double getinEuro() {
		return this.inEuro;
	}

	public void setinEuro(double inEuro) {
		this.inEuro = inEuro;
	}

	public String getnome() {
		return this.nome;
	}

	public void setnome(String nome) {
		this.nome = nome;
	}

	public Utenti getUtenti() {
		return this.utenti;
	}

	public void setUtenti(Utenti utenti) {
		this.utenti = utenti;
	}

	public void setDataIns(String date) {
		this.dataIns = date;
	}

	public String getDataIns() {
		return dataIns;
	}
	
}