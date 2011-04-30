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

public class SottoPannelloDatiEntrate {

	private static final long serialVersionUID = 1L;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		// frame.getContentPane().add(new SottoPannelloDatiEntrate());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private static JTextField EntrateMesePrec;
	private static JTextField EnAnCorso;
	private static JTextField EnMeCorso;

	JComponent[]              componenti = new JComponent[3];
	JLabel[]                  labels     = new JLabel[3];
	CostruttoreSottoPannello  pannello;

	public SottoPannelloDatiEntrate() {
		super();
		initGUI();
		pannello = new CostruttoreSottoPannello(componenti, labels, CostruttoreSottoPannello.VERTICAL);
	}

	private void initGUI() {
		try {
			JLabel label_1 = new LabelTestoPiccolo("Anno in corso");
			label_1.setBounds(164, 66, 141, 14);
			labels[2] = label_1;

			JLabel label_2 = new LabelTestoPiccolo("Mese prec.");
			label_2.setBounds(16, 67, 136, 14);
			labels[1] = label_2;

			JLabel label_3 = new LabelTestoPiccolo("Mese in corso");
			label_3.setBounds(317, 67, 113, 14);
			labels[0] = label_3;

			EntrateMesePrec = new TextFieldF();
			double Emensile = Database.Emensile();
			EntrateMesePrec.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(Emensile)));
			EntrateMesePrec.setBounds(16, 85, 106, 27);
			componenti[1] = EntrateMesePrec;
			EntrateMesePrec.setColumns(10);

			EnAnCorso = new TextFieldF();

			double EAnnuale = Database.EAnnuale();
			EnAnCorso.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(EAnnuale)));
			EnAnCorso.setBounds(164, 84, 106, 27);
			componenti[2] = EnAnCorso;
			EnAnCorso.setColumns(10);

			EnMeCorso = new TextFieldF();
			double EMensile = Database.EMensileInCorso();
			EnMeCorso.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(EMensile)));
			EnMeCorso.setBounds(317, 85, 106, 27);
			componenti[0] = EnMeCorso;
			EnMeCorso.setColumns(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JTextField getEntrateMesePrec() {
		return SottoPannelloDatiEntrate.EntrateMesePrec;

	}

	public static void setEntrateMesePrec(JTextField entrateMesePrec) {
		EntrateMesePrec = entrateMesePrec;
	}

	public static JTextField getEnAnCorso() {
		return EnAnCorso;
	}

	public static void setEnAnCorso(JTextField enAnCorso) {
		EnAnCorso = enAnCorso;
	}

	public static JTextField getEnMeCorso() {
		return EnMeCorso;
	}

	public static void setEnMeCorso(JTextField enMeCorso) {
		EnMeCorso = enMeCorso;
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
