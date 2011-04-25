package view.componenti.componentiPannello;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.font.LabelTesto;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Database;
import domain.CatSpese;

public class SottoPannelloTotali {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		// frame.getContentPane().add(new SottoPannelloTotali());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private JLabel            jLabel2;
	private JLabel            jLabel3;
	private JLabel            jLabel4;
	private static JTextField percentoVariabili;
	private static JTextField percentoFutili;
	private static JTextField avanzo;
	private static JLabel     jLabel1;
	private JSeparator        jSeparator1;

	ArrayList<JComponent>     componenti = new ArrayList<JComponent>();
	ArrayList<JLabel>         labels     = new ArrayList<JLabel>();
	CostruttoreSottoPannello  pannello;

	public SottoPannelloTotali() {
		super();
		initGUI();
		pannello = new CostruttoreSottoPannello(componenti, labels);
	}

	private void initGUI() {
		try {
			// jLabel1 = new LabelTitolo();
			// this.add(jLabel1);
			// jLabel1.setText("Totali");
			// jLabel1.setBounds(177, 23, 90, 19);

			// jSeparator1 = new JSeparator();
			// this.add(jSeparator1);
			// jSeparator1.setBounds(10, 128, 420, 16);

			jLabel2 = new LabelTesto();
			labels.add(jLabel2);
			jLabel2.setText("% Spese Futili");
			jLabel2.setBounds(317, 67, 106, 14);
			jLabel2.setOpaque(true);

			jLabel3 = new LabelTesto();
			labels.add(jLabel3);
			jLabel3.setText("% Spese Variabili");
			jLabel3.setBounds(164, 66, 141, 15);

			jLabel4 = new LabelTesto();
			labels.add(jLabel4);
			jLabel4.setText("Avanzo/disavanzo");
			jLabel4.setBounds(16, 67, 128, 14);

			avanzo = new TextFieldF();
			componenti.add(avanzo);
			avanzo.setColumns(10);

			// JTextField perEntrate = SottoPannelloDatiEntrate.getEnAnCorso()
			// != null?SottoPannelloDatiEntrate.getEnAnCorso():new
			// TextFieldF("0");
			// JTextField perUscite =
			// SottoPannelloDatiSpese.getSpeseAnnuali()!=null?SottoPannelloDatiSpese.getSpeseAnnuali():new
			// TextFieldF("0");
			double differenza = AltreUtil.arrotondaDecimaliDouble((Database.EAnnuale()) - (Database.Annuale()));
			avanzo.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(differenza)));
			avanzo.setBounds(16, 85, 106, 27);
			avanzo.setSize(92, 27);

			double percVariabili = Database.percentoUscite(CatSpese.IMPORTANZA_VARIABILE);

			double percFutili = Database.percentoUscite(CatSpese.IMPORTANZA_FUTILE);

			percentoVariabili = new TextFieldF();
			componenti.add(percentoVariabili);
			percentoVariabili.setColumns(10);
			percentoVariabili.setText(Double.toString(percVariabili));
			percentoVariabili.setBounds(164, 84, 106, 27);
			percentoVariabili.setSize(92, 27);

			percentoFutili = new TextFieldF();
			componenti.add(percentoFutili);
			percentoFutili.setColumns(10);
			percentoFutili.setText(Double.toString(percFutili));
			percentoFutili.setBounds(317, 85, 106, 27);
			// this.setPreferredSize(new Dimension(440, 150));
			// this.setLayout(null);
			// this.setSize(400, 100);
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

	public static JTextField getPercentoVariabili() {
		return percentoVariabili;
	}

	public static void setPercentoVariabili(JTextField percentoVariabili) {
		SottoPannelloTotali.percentoVariabili = percentoVariabili;
	}

	public static JTextField getPercentoFutili() {
		return percentoFutili;
	}

	public static void setPercentoFutili(JTextField percentoFutili) {
		SottoPannelloTotali.percentoFutili = percentoFutili;
	}

	public static JTextField getAvanzo() {
		return avanzo;
	}

	public static void setAvanzo(JTextField avanzo) {
		SottoPannelloTotali.avanzo = avanzo;
	}

}
