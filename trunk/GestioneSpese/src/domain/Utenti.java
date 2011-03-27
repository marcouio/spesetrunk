package domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the UTENTI database table.
 * 
 */
@Entity
@Table(name="UTENTI")
public class Utenti extends AbstractOggettoEntita implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELLA = "Utenti";
	public static final String ID = "idUtente";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String NOME = "nome";
	public static final String COGNOME = "cognome";

	@Id
	@Column(name="idUtente", nullable=false)
	private int idUtente;

	@Column(name="password", nullable=false, length=2000000000)
	private String password;

	@Column(name="username", nullable=false, length=2000000000)
	private String username;
	
	@Column(name="nome", nullable=false, length=2000000000)
	private String nome;
	
	@Column(name="cognome", nullable=false, length=2000000000)
	private String cognome;

	//bi-directional many-to-one association to Entrate
	@OneToMany(mappedBy="utenti")
	private Set<Entrate> entrates;

	//bi-directional many-to-one association to SingleSpesa
	@OneToMany(mappedBy="utenti")
	private Set<SingleSpesa> singleSpesas;

    public Utenti() {
    	if(idUtente!=0){
    		this.idEntita = Integer.toString(idUtente);
    	}
    }

	public int getidUtente() {
		return this.idUtente;
	}

	public void setidUtente(int idUtente) {
		this.idEntita = Integer.toString(idUtente);
		this.idUtente = idUtente;
	}

	public String getpassword() {
		return this.password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getusername() {
		return this.username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public Set<Entrate> getEntrates() {
		return this.entrates;
	}

	public void setEntrates(Set<Entrate> entrates) {
		this.entrates = entrates;
	}
	
	public Set<SingleSpesa> getSingleSpesas() {
		return this.singleSpesas;
	}

	public void setSingleSpesas(Set<SingleSpesa> singleSpesas) {
		this.singleSpesas = singleSpesas;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCognome() {
		return cognome;
	}
	
}