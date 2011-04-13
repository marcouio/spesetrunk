package domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the LOOKANDFEEL database table.
 * 
 */
@Entity
@Table(name="LOOKANDFEEL")
public class Lookandfeel extends AbstractOggettoEntita implements Serializable,ILookandfeel {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELLA = "lookAndFeel";
	public static final String ID = "idLook";
	public static final String NOME = "nome";
	public static final String VALORE = "valore";
	public static final String USATO = "usato";

	@Id
	@Column(name="idLook", nullable=false)
	private int idLook;

	@Column(name="nome", nullable=false)
	private String nome;

	@Column(name="usato", nullable=false)
	private int usato;

	@Column(name="valore", nullable=false)
	private String valore;

    public Lookandfeel() {
    	if(idLook!=0)
    		this.idEntita = Integer.toString(idLook);
    }

	public int getidLook() {
		return this.idLook;
	}

	public void setidLook(int idLook) {
		this.idEntita = Integer.toString(idLook);
		this.idLook = idLook;
	}

	public String getnome() {
		return this.nome;
	}

	public void setnome(String nome) {
		this.nome = nome;
	}

	public int getusato() {
		return this.usato;
	}

	public void setusato(int usato) {
		this.usato = usato;
	}

	public String getvalore() {
		return this.valore;
	}

	public void setvalore(String valore) {
		this.valore = valore;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nome;
	}

}