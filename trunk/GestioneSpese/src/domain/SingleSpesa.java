package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the SINGLESPESA database table.
 * 
 */
@Entity
@Table(name="SINGLESPESA")
public class SingleSpesa extends AbstractOggettoEntita implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELLA = "single_spesa";
	public static final String ID = "idSpesa";
	public static final String DATA = "Data";
	public static final String DATAINS = "dataIns";
	public static final String IDCATEGORIE = "idCategorie";
	public static final String INEURO = "inEuro";
	public static final String DESCRIZIONE = "descrizione";
	public static final String NOME = "nome";
	public static final String IDUTENTE = "idUtente";

	@Column(name="Data", nullable=false, length=2000000000)
	private Date Data;

	@Column(name="descrizione", nullable=false, length=2000000000)
	private String descrizione;

	@Column(name="idCategorie", nullable=false)
	private int idCategorie;

	@Id
	@Column(name="idSpesa", nullable=false)
	private int idSpesa;

	@Column(name="idUtente", nullable=false)
	private int idUtente;

	@Column(name="inEuro", nullable=false)
	private double inEuro;

	@Column(name="nome", nullable=false, length=2000000000)
	private String nome;
	
	@Column(name="dataIns", nullable=false, length=2000000000)
	private Date dataIns;

	//bi-directional many-to-one association to CatSpese
    @ManyToOne
	@JoinColumns({
		})
	private CatSpese catSpese;

	//bi-directional many-to-one association to Utenti
    @ManyToOne
	@JoinColumns({
		})
	private Utenti utenti;

    public SingleSpesa() {
    }

	public Date getData() {
		return this.Data;
	}

	public void setData(Date Data) {
		this.Data = Data;
	}

	public String getdescrizione() {
		return this.descrizione;
	}

	public void setdescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getidCategorie() {
		return this.idCategorie;
	}

	public void setidCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public int getidSpesa() {
		return this.idSpesa;
	}

	public void setidSpesa(int idSpesa) {
		this.idSpesa = idSpesa;
	}

	public int getidUtente() {
		return this.idUtente;
	}

	public void setidUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public double getinEuro() {
		return this.inEuro;
	}

	public void setinEuro(double d) {
		this.inEuro = d;
	}

	public String getnome() {
		return this.nome;
	}

	public void setnome(String nome) {
		this.nome = nome;
	}

	public CatSpese getCatSpese() {
		return this.catSpese;
	}

	public void setCatSpese(CatSpese catSpese) {
		this.catSpese = catSpese;
	}
	
	public Utenti getUtenti() {
		return this.utenti;
	}

	public void setUtenti(Utenti utenti) {
		this.utenti = utenti;
	}

	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	public Date getDataIns() {
		return dataIns;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
}