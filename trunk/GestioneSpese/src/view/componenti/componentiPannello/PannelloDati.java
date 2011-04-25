package view.componenti.componentiPannello;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import view.OggettoVistaBase;

public class PannelloDati extends OggettoVistaBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setBounds(0, 0, 500, 500);
		frame.setVisible(true);
		frame.getContentPane().add(new PannelloDati());
	}

	private static SottoPannelloDatiSpese   pannelloSpese;
	private static SottoPannelloDatiEntrate pannelloEntrate;
	private static SottoPannelloMesi        pannelloMesi;
	private static CostruttoreSottoPannello pannelloCategorie;
	private static SottoPannelloTotali      pannelloTotali;

	public PannelloDati() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setSize(new Dimension(400, 600));
			setLayout(new GridLayout(1, 0, 0, 0));

			pannelloSpese = new SottoPannelloDatiSpese();
			pannelloEntrate = new SottoPannelloDatiEntrate();
			pannelloMesi = new SottoPannelloMesi();
			pannelloCategorie = new SottoPannelloCategorie().getPannello();
			pannelloTotali = new SottoPannelloTotali();

			// this.add(pannelloSpese);
			// this.add(pannelloTotali);
			// pannelloTotali.setLayout(new GridLayout(1, 0, 0, 0));
			this.add(pannelloCategorie);
			// this.add(pannelloMesi);
			// this.add(pannelloEntrate);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the pannelloSpese
	 */
	public static SottoPannelloDatiSpese getPannelloSpese() {
		return pannelloSpese;
	}

	/**
	 * @param pannelloSpese
	 *            the pannelloSpese to set
	 */
	public static void setPannelloSpese(SottoPannelloDatiSpese pannelloSpese) {
		PannelloDati.pannelloSpese = pannelloSpese;
	}

	/**
	 * @return the pannelloEntrate
	 */
	public static SottoPannelloDatiEntrate getPannelloEntrate() {
		return pannelloEntrate;
	}

	/**
	 * @param pannelloEntrate
	 *            the pannelloEntrate to set
	 */
	public static void setPannelloEntrate(SottoPannelloDatiEntrate pannelloEntrate) {
		PannelloDati.pannelloEntrate = pannelloEntrate;
	}

	/**
	 * @return the pannelloMesi
	 */
	public static SottoPannelloMesi getPannelloMesi() {
		return pannelloMesi;
	}

	/**
	 * @param pannelloMesi
	 *            the pannelloMesi to set
	 */
	public static void setPannelloMesi(SottoPannelloMesi pannelloMesi) {
		PannelloDati.pannelloMesi = pannelloMesi;
	}

	/**
	 * @return the pannelloCategorie
	 */
	public static CostruttoreSottoPannello getPannelloCategorie() {
		return pannelloCategorie;
	}

	/**
	 * @param pannelloCategorie
	 *            the pannelloCategorie to set
	 */
	public static void setPannelloCategorie(CostruttoreSottoPannello pannelloCategorie) {
		PannelloDati.pannelloCategorie = pannelloCategorie;
	}

	/**
	 * @return the pannelloTotali
	 */
	public static SottoPannelloTotali getPannelloTotali() {
		return pannelloTotali;
	}

	/**
	 * @param pannelloTotali
	 *            the pannelloTotali to set
	 */
	public static void setPannelloTotali(SottoPannelloTotali pannelloTotali) {
		PannelloDati.pannelloTotali = pannelloTotali;
	}

}
