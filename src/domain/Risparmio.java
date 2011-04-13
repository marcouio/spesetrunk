package domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RISPARMIO database table.
 * 
 */
@Entity
@Table(name="RISPARMIO")
public class Risparmio extends AbstractOggettoEntita implements Serializable, IRisparmio {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELLA = "risparmio";
	public static final String ID = "idRisparmio";
	public static final String PERCSULTOT = "PercSulTotale";

	@Id
	@Column(name="idRisparmio", nullable=false)
	private int idRisparmio;

	@Column(name="PerSulTotale", nullable=false)
	private double PerSulTotale;

    public Risparmio() {
    	if(idRisparmio!=0)
    		this.idEntita = Integer.toString(idRisparmio);
    }

	public int getidRisparmio() {
		return this.idRisparmio;
	}

	public void setidRisparmio(int idRisparmio) {
		this.idEntita = Integer.toString(idRisparmio);
		this.idRisparmio = idRisparmio;
	}

	public double getPerSulTotale() {
		return this.PerSulTotale;
	}

	public void setPerSulTotale(double PerSulTotale) {
		this.PerSulTotale = PerSulTotale;
	}

	@Override
	public String getnome() {
		// TODO Auto-generated method stub
		return null;
	}

}