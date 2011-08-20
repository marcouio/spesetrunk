package domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the CATSPESE database table.
 * 
 */
@Entity
@Table(name = "cat_spese", schema = "DEFAULT")
public class CatSpese extends AbstractOggettoEntita implements Serializable, ICatSpese {
	private static final long serialVersionUID = 1L;

	@Column(name = "\"descrizione\"", nullable = false, length = 2000000000)
	private String descrizione;

	@Id
	@Column(name = "\"idCategoria\"", nullable = false)
	private int idCategoria;

	// @Column(name="idGruppo", nullable=false)
	// private int idGruppo;

	@Column(name = "\"importanza\"", nullable = false, length = 2000000000)
	private String importanza;

	@Column(name = "\"nome\"", nullable = false, length = 2000000000)
	private String nome;

	// bi-directional one-to-one association to Budget
	@OneToOne(mappedBy = "catSpese")
	private Budget budget;

	// bi-directional many-to-one association to Gruppi
	@ManyToOne
	@JoinColumns({})
	private Gruppi gruppi;

	// bi-directional many-to-one association to SingleSpesa
	@OneToMany(mappedBy = "catSpese")
	private Set<SingleSpesa> singleSpesas;

	public CatSpese() {
		if (idCategoria != 0) {
			this.idEntita = Integer.toString(idCategoria);
		}
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
	public int getidCategoria() {
		return this.idCategoria;
	}

	@Override
	public void setidCategoria(final int idCategoria) {
		this.idCategoria = idCategoria;
		this.idEntita = Integer.toString(idCategoria);
	}

	@Override
	public String getimportanza() {
		return this.importanza;
	}

	@Override
	public void setimportanza(final String importanza) {
		this.importanza = importanza;
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
	public Budget getBudget() {
		return this.budget;
	}

	@Override
	public void setBudget(final Budget budget) {
		this.budget = budget;
	}

	@Override
	public Gruppi getGruppi() {
		return this.gruppi;
	}

	@Override
	public void setGruppi(final Gruppi gruppi) {
		this.gruppi = gruppi;
	}

	@Override
	public Set<SingleSpesa> getSingleSpesas() {
		return this.singleSpesas;
	}

	@Override
	public void setSingleSpesas(final Set<SingleSpesa> singleSpesas) {
		this.singleSpesas = singleSpesas;
	}

	@Override
	public String toString() {
		return nome;
	}

	public static final String NOME_TABELLA = "cat_spese";
	public static final String ID = "idCategoria";
	public static final String DESCRIZIONE = "descrizione";
	public static final String IMPORTANZA = "importanza";
	public static final String NOME = "nome";
	public static final String IDGRUPPO = "idGruppo";

	public static final String IMPORTANZA_FUTILE = "Futili";
	public static final String IMPORTANZA_VARIABILE = "Variabili";
	public static final String IMPORTANZA_FISSO = "Fisse";
}