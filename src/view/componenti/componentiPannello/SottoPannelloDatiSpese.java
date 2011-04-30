package view.componenti.componentiPannello;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.font.LabelTestoPiccolo;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Database;

public class SottoPannelloDatiSpese {

	private static final long serialVersionUID = 1L;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new SottoPannelloDatiSpese().getPannello());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private static JTextField meseInCors;
	private static JTextField speseAnnuali;
	private static double     annuale;
	private static JTextField mesePrecUsc;
	private static double     mensile;
	private static double     mensile2;

	JComponent[]              componenti = new JComponent[3];
	JLabel[]                  labels     = new JLabel[3];
	CostruttoreSottoPannello  pannello;

	private void initGUI() {
		try {

			JLabel meseincorso = new LabelTestoPiccolo("Anno in corso");
			labels[2] = meseincorso;
			meseincorso.setBounds(164, 66, 141, 14);

			speseAnnuali = new TextFieldF();
			componenti[2] = speseAnnuali;
			speseAnnuali.setBounds(164, 84, 106, 27);
			speseAnnuali.setColumns(8);

			JLabel label = new LabelTestoPiccolo("Mese prec.");
			label.setBounds(317, 67, 123, 14);
			labels[1] = label;

			mensile = Database.Mensile();
			mesePrecUsc = new TextFieldF();
			mesePrecUsc.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(mensile)));
			mesePrecUsc.setBounds(317, 85, 106, 27);
			componenti[1] = mesePrecUsc;

			JLabel label2 = new LabelTestoPiccolo("Mese in corso");
			label2.setBounds(16, 67, 136, 13);
			labels[0] = label2;

			annuale = Database.Annuale();
			speseAnnuali.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(annuale)));

			meseInCors = new TextFieldF();
			mensile2 = Database.MensileInCorso();
			meseInCors.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(mensile2)));
			componenti[0] = meseInCors;
			meseInCors.setBounds(16, 85, 106, 27);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JTextField getMeseInCors() {
		return meseInCors;
	}

	public static void setMeseInCors(JTextField meseInCors) {
		SottoPannelloDatiSpese.meseInCors = meseInCors;
	}

	public static JTextField getSpeseAnnuali() {
		return speseAnnuali;
	}

	public static void setSpeseAnnuali(JTextField speseAnnuali) {
		SottoPannelloDatiSpese.speseAnnuali = speseAnnuali;
	}

	public static double getAnnuale() {
		return annuale;
	}

	public static void setAnnuale(double annuale) {
		SottoPannelloDatiSpese.annuale = annuale;
	}

	public static JTextField getMesePrecUsc() {
		return mesePrecUsc;
	}

	public static void setMesePrecUsc(JTextField mesePrecUsc) {
		SottoPannelloDatiSpese.mesePrecUsc = mesePrecUsc;
	}

	public static double getMensile() {
		return mensile;
	}

	public static void setMensile(double mensile) {
		SottoPannelloDatiSpese.mensile = mensile;
	}

	public SottoPannelloDatiSpese() {
		super();
		initGUI();
		pannello = new CostruttoreSottoPannello(componenti, labels, CostruttoreSottoPannello.VERTICAL);
	}

	protected JComponent[] getComponenti() {
		return componenti;
	}

	protected void setComponenti(JComponent[] componenti) {
		this.componenti = componenti;
	}

	protected JLabel[] getLabels() {
		return labels;
	}

	protected void setLabels(JLabel[] labels) {
		this.labels = labels;
	}

	protected CostruttoreSottoPannello getPannello() {
		return pannello;
	}

	protected void setPannello(CostruttoreSottoPannello pannello) {
		this.pannello = pannello;
	}

}
