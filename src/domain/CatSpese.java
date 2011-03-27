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
@Table(name="cat_spese", schema="DEFAULT")
public class CatSpese extends AbstractOggettoEntita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="descrizione", nullable=false, length=2000000000)
	private String descrizione;

	@Id
	@Column(name="idCategoria", nullable=false)
	private int idCategoria;

	@Column(name="idGruppo", nullable=false)
	private int idGruppo;

	@Column(name="importanza", nullable=false, length=2000000000)
	private String importanza;

	@Column(name="nome", nullable=false, length=2000000000)
	private String nome;

	//bi-directional one-to-one association to Budget
	@OneToOne(mappedBy="catSpese")
	private Budget budget;

	//bi-directional many-to-one association to Gruppi
    @ManyToOne
	@JoinColumns({
		})
	private Gruppi gruppi;

	//bi-directional many-to-one association to SingleSpesa
	@OneToMany(mappedBy="catSpese")
	private Set<SingleSpesa> singleSpesas;

    public CatSpese() {
    	if(idCategoria!=0){
    		this.idEntita = Integer.toString(idCategoria);
    	}
    }

	public String getdescrizione() {
		return this.descrizione;
	}

	public void setdescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getidCategoria() {
		return this.idCategoria;
	}

	public void setidCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
		this.idEntita = Integer.toString(idCategoria);
	}

	public int getidGruppo() {
		return this.idGruppo;
	}

	public void setidGruppo(int idGruppo) {
		this.idGruppo = idGruppo;
	}

	public String getimportanza() {
		return this.importanza;
	}

	public void setimportanza(String importanza) {
		this.importanza = importanza;
	}

	public String getnome() {
		return this.nome;
	}

	public void setnome(String nome) {
		this.nome = nome;
	}

	public Budget getBudget() {
		return this.budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	
	public Gruppi getGruppi() {
		return this.gruppi;
	}

	public void setGruppi(Gruppi gruppi) {
		this.gruppi = gruppi;
	}
	
	public Set<SingleSpesa> getSingleSpesas() {
		return this.singleSpesas;
	}

	public void setSingleSpesas(Set<SingleSpesa> singleSpesas) {
		this.singleSpesas = singleSpesas;
	}
	@Override
	public String toString() {
		return nome;
	}
	
	public static final String NOME_TABELLA="cat_spese";
	public static final String ID ="idCategoria";
	public static final String DESCRIZIONE ="descrizione";
	public static final String IMPORTANZA="importanza";
	public static final String NOME ="nome";
	public static final String IDGRUPPO ="idGruppo";
	
	public static final String IMPORTANZA_FUTILE="Futili";
	public static final String IMPORTANZA_VARIABILE="Variabili";
	public static final String IMPORTANZA_FISSO="Fisse";
}