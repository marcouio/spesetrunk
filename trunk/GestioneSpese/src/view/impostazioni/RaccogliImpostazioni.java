package view.impostazioni;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import view.OggettoVistaBase;

public class RaccogliImpostazioni extends OggettoVistaBase{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Categorie categorie;
	private Impostazioni impostazioni;
	private SettingGruppi settingGruppi; 
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame inst = new JFrame();	
				inst.setSize(950, 700);
				inst.getContentPane().add(new RaccogliImpostazioni());
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				
			}
		});
	}
	
	
	public RaccogliImpostazioni(){
		
		this.setPreferredSize(new Dimension(1000, 980));
		this.setLayout(null);
		settingGruppi = SettingGruppi.getSingleton();
		settingGruppi.setBounds(350, 250, 626, 300);
		categorie = Categorie.getSingleton();
		categorie.setBounds(10, 0, 355, 550);
		impostazioni = Impostazioni.getSingleton();
		impostazioni.setBounds(350, 0, 626, 250);
		this.add(settingGruppi);
		this.add(categorie);
		this.add(impostazioni);	
		
	}


	/**
	 * @return the categorie
	 */
	public Categorie getCategorie() {
		return categorie;
	}


	/**
	 * @param categorie the categorie to set
	 */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}


	/**
	 * @return the impostazioni
	 */
	public Impostazioni getImpostazioni() {
		return impostazioni;
	}


	/**
	 * @param impostazioni the impostazioni to set
	 */
	public void setImpostazioni(Impostazioni impostazioni) {
		this.impostazioni = impostazioni;
	}
	

	

}
