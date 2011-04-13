package domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the GRUPPI database table.
 * 
 */
@Entity

@Table(name="GRUPPI")
public class Gruppi extends AbstractOggettoEntita implements Serializable, IGruppi {
	private static final long serialVersionUID = 1L;

	@Column(name="descrizione", nullable=false, length=2000000000)
	private String descrizione;

	@Id
	@Column(name="idGruppo", nullable=false)
	private int idGruppo;

	@Column(name="nome", nullable=false, length=2000000000)
	private String nome;

	//bi-directional many-to-one association to CatSpese
	@OneToMany(mappedBy="gruppi")
	private Set<CatSpese> catSpeses;

    public Gruppi() {
    	if(idGruppo!=0)
    		this.idEntita = Integer.toString(idGruppo);
    }

	public String getdescrizione() {
		return this.descrizione;
	}

	public void setdescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getidGruppo() {
		return this.idGruppo;
	}

	public void setidGruppo(int idGruppo) {
		this.idEntita = Integer.toString(idGruppo);
		this.idGruppo = idGruppo;
	}

	public String getnome() {
		return this.nome;
	}

	public void setnome(String nome) {
		this.nome = nome;
	}

	public Set<CatSpese> getCatSpeses() {
		return this.catSpeses;
	}

	public void setCatSpeses(Set<CatSpese> catSpeses) {
		this.catSpeses = catSpeses;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	public static final String NOME_TABELLA="GRUPPI";
	public static final String ID ="idGruppo";
	public static final String DESCRIZIONE ="descrizione";
	public static final String NOME ="nome";
}