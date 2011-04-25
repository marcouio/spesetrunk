package view.impostazioni;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import view.OggettoVistaBase;

public class RaccogliImpostazioni extends OggettoVistaBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CategorieView     categorie;
	private Impostazioni      impostazioni;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame inst = new JFrame();
				inst.setSize(950, 700);
				inst.getContentPane().add(new RaccogliImpostazioni());
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);

			}
		});
	}

	public RaccogliImpostazioni() {

		this.setPreferredSize(new Dimension(1000, 980));
		this.setLayout(null);
		// categorie = new CategorieView(new WrapCatSpese());
		// categorie.setBounds(10, 0, 355, 550);
		// this.add(categorie);
		// this.add(impostazioni);

	}

	/**
	 * @return the categorie
	 */
	public CategorieView getCategorie() {
		return categorie;
	}

	/**
	 * @param categorie
	 *            the categorie to set
	 */
	public void setCategorie(CategorieView categorie) {
		this.categorie = categorie;
	}

	/**
	 * @return the impostazioni
	 */
	public Impostazioni getImpostazioni() {
		return impostazioni;
	}

	/**
	 * @param impostazioni
	 *            the impostazioni to set
	 */
	public void setImpostazioni(Impostazioni impostazioni) {
		this.impostazioni = impostazioni;
	}

}
