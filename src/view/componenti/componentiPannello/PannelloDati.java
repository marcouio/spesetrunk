package view.componenti.componentiPannello;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import view.OggettoVistaBase;
import view.layout.DirectionalFlowLayout;

public class PannelloDati extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		PannelloDati dati2 = new PannelloDati();
		frame.getContentPane().add(dati2);
	}

	private static SottoPannelloDatiSpese   pannelloSpese;
	private static SottoPannelloDatiEntrate pannelloEntrate;
	private static SottoPannelloMesi        pannelloMesi;
	private static SottoPannelloCategorie   pannelloCategorie;
	private static SottoPannelloTotali      pannelloTotali;

	public PannelloDati() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			setLayout(new DirectionalFlowLayout(FlowLayout.LEFT, DirectionalFlowLayout.HORIZONTAL_DIRECTION));
			pannelloSpese = new SottoPannelloDatiSpese();
			pannelloEntrate = new SottoPannelloDatiEntrate();
			pannelloMesi = new SottoPannelloMesi();
			pannelloCategorie = new SottoPannelloCategorie();
			pannelloTotali = new SottoPannelloTotali();

			this.add(pannelloSpese.getPannello());
			this.add(pannelloTotali.getPannello());
			this.add(pannelloCategorie.getPannello());
			this.add(pannelloMesi.getPannello());
			this.add(pannelloEntrate.getPannello());

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
	public static SottoPannelloCategorie getPannelloCategorie() {
		return pannelloCategorie;
	}

	/**
	 * @param pannelloCategorie
	 *            the pannelloCategorie to set
	 */
	public static void setPannelloCategorie(SottoPannelloCategorie pannelloCategorie) {
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
