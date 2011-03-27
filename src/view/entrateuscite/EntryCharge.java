package view.entrateuscite;

import java.awt.Dimension;

import view.OggettoVistaBase;


public class EntryCharge extends OggettoVistaBase{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntryCharge() {
		
		this.setPreferredSize(new Dimension(950, 700));
		this.setLayout(null);
		Uscite uscita = Uscite.getSingleton();
		uscita.setBounds(10, 10, 930, 290);
		EntrateView entrata = EntrateView.getSingleton();
		entrata.setBounds(10, 280, 930, 305);
		this.add(uscita);
		this.add(entrata);	
	}

	
}
