package domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the GRUPPI database table.
 * 
 */
@Entity
@Table(name = "GRUPPI")
public class Gruppi extends AbstractOggettoEntita implements Serializable, IGruppi {
	private static final long serialVersionUID = 1L;

	@Column(name = "descrizione", nullable = false, length = 2000000000)
	private String            descrizione;

	@Id
	@Column(name = "idGruppo", nullable = false)
	private int               idGruppo;

	@Column(name = "nome", nullable = false, length = 2000000000)
	private String            nome;

	// bi-directional many-to-one association to CatSpese
	@OneToMany(mappedBy = "gruppi")
	private Set<CatSpese>     catSpeses;

	public Gruppi() {
		if (idGruppo != 0)
			this.idEntita = Integer.toString(idGruppo);
	}

	@Override
	public String getdescrizione() {
		return this.descrizione;
	}

	@Override
	public void setdescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public int getidGruppo() {
		return this.idGruppo;
	}

	@Override
	public void setidGruppo(int idGruppo) {
		this.idEntita = Integer.toString(idGruppo);
		this.idGruppo = idGruppo;
	}

	@Override
	public String getnome() {
		return this.nome;
	}

	@Override
	public void setnome(String nome) {
		this.nome = nome;
	}

	@Override
	public Set<CatSpese> getCatSpeses() {
		return this.catSpeses;
	}

	@Override
	public void setCatSpeses(Set<CatSpese> catSpeses) {
		this.catSpeses = catSpeses;
	}

	@Override
	public String toString() {
		return nome;
	}

	public static final String NOME_TABELLA = "gruppi";
	public static final String ID           = "idGruppo";
	public static final String DESCRIZIONE  = "descrizione";
	public static final String NOME         = "nome";
}