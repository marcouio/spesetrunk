package view.componenti.componentiPannello;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.font.LabelTesto;
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

	ArrayList<JComponent>     componenti = new ArrayList<JComponent>();
	ArrayList<JLabel>         labels     = new ArrayList<JLabel>();
	CostruttoreSottoPannello  pannello;

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
		pannello = new CostruttoreSottoPannello(componenti, labels);
	}

	private void initGUI() {
		try {

			// JLabel Intestazione = new LabelTitolo("Spese Dati");
			// Intestazione.setBounds(177, 25, 93, 19);
			// this.add(Intestazione);

			JLabel meseincorso = new LabelTesto("Anno in corso");
			labels.add(meseincorso);
			meseincorso.setBounds(164, 66, 141, 14);

			speseAnnuali = new TextFieldF();
			componenti.add(speseAnnuali);
			speseAnnuali.setBounds(164, 84, 106, 27);
			speseAnnuali.setColumns(8);

			JLabel label = new LabelTesto("Mese prec.");
			label.setBounds(317, 67, 123, 14);
			labels.add(label);

			mensile = Database.Mensile();
			mesePrecUsc = new TextFieldF();
			mesePrecUsc.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(mensile)));
			mesePrecUsc.setBounds(317, 85, 106, 27);
			componenti.add(mesePrecUsc);
			mesePrecUsc.setColumns(9);

			JLabel label2 = new LabelTesto("Mese in corso");
			label2.setBounds(16, 67, 136, 13);
			labels.add(label2);

			annuale = Database.Annuale();
			speseAnnuali.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(annuale)));

			// JSeparator separator = new JSeparator();
			// separator.setBounds(10, 128, 420, 16);
			// this.add(separator);
			meseInCors = new TextFieldF();
			mensile2 = Database.MensileInCorso();
			meseInCors.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(mensile2)));
			componenti.add(meseInCors);
			meseInCors.setColumns(8);
			meseInCors.setBounds(16, 85, 106, 27);

			// this.setPreferredSize(new Dimension(440, 150));
			// this.setLayout(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected ArrayList<JComponent> getComponenti() {
		return componenti;
	}

	protected void setComponenti(ArrayList<JComponent> componenti) {
		this.componenti = componenti;
	}

	protected ArrayList<JLabel> getLabels() {
		return labels;
	}

	protected void setLabels(ArrayList<JLabel> labels) {
		this.labels = labels;
	}

	protected CostruttoreSottoPannello getPannello() {
		return pannello;
	}

	protected void setPannello(CostruttoreSottoPannello pannello) {
		this.pannello = pannello;
	}

}
