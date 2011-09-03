package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the SINGLESPESA database table.
 * 
 */
@Entity
@Table(name = "SINGLE_SPESA")
public class SingleSpesa extends AbstractOggettoEntita implements Serializable, ISingleSpesa {
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

	@Column(name = "\"Data\"", nullable = false, length = 2000000000)
	private String Data;

	@Column(name = "\"descrizione\"", nullable = false, length = 2000000000)
	private String descrizione;

	@Id
	@Column(name = "\"idSpesa\"", nullable = false)
	private int idSpesa;

	@Column(name = "\"inEuro\"", nullable = false)
	private double inEuro;

	@Column(name = "\"nome\"", nullable = false, length = 2000000000)
	private String nome;

	@Column(name = "\"dataIns\"", nullable = false, length = 2000000000)
	private String dataIns;

	// bi-directional many-to-one association to CatSpese
	@ManyToOne
	@JoinColumns({})
	private CatSpese catSpese;

	// bi-directional many-to-one association to Utenti
	@ManyToOne
	@JoinColumns({})
	private Utenti utenti;

	public SingleSpesa() {
		if (idSpesa != 0) {
			this.idEntita = Integer.toString(idSpesa);
		}
	}

	@Override
	public String getData() {
		return this.Data;
	}

	@Override
	public void setData(final String Data) {
		this.Data = Data;
	}

	@Override
	public String getdescrizione() {
		return this.descrizione;
	}

	@Override
	public void setdescrizione(final String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public int getidSpesa() {
		return this.idSpesa;
	}

	@Override
	public void setidSpesa(final int idSpesa) {
		this.idEntita = Integer.toString(idSpesa);
		this.idSpesa = idSpesa;
	}

	@Override
	public double getinEuro() {
		return this.inEuro;
	}

	@Override
	public void setinEuro(final double d) {
		this.inEuro = d;
	}

	@Override
	public String getnome() {
		return this.nome;
	}

	@Override
	public void setnome(final String nome) {
		this.nome = nome;
	}

	@Override
	public CatSpese getCatSpese() {
		return this.catSpese;
	}

	@Override
	public void setCatSpese(final CatSpese catSpese) {
		this.catSpese = catSpese;
	}

	@Override
	public Utenti getUtenti() {
		return this.utenti;
	}

	@Override
	public void setUtenti(final Utenti utenti) {
		this.utenti = utenti;
	}

	@Override
	public void setDataIns(final String dataIns) {
		this.dataIns = dataIns;
	}

	@Override
	public String getDataIns() {
		return dataIns;
	}

	@Override
	public String toString() {
		return nome;
	}

}