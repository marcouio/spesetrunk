package view.componenti.componentiPannello;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.font.LabelTestoPiccolo;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Database;

public class SottoPannelloMesi {

	private static final long serialVersionUID = 1L;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		// frame.getContentPane().add(new SottoPannelloMesi());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private JLabel            jLabel9;
	private static JTextField totaleMeseUscite;
	private JLabel            jLabel12;
	private static JComboBox  ComboMese;
	private static JTextField totaleMeseEntrate;

	JComponent[]              componenti = new JComponent[3];
	JLabel[]                  labels     = new JLabel[3];
	CostruttoreSottoPannello  pannello;

	public SottoPannelloMesi() {
		super();
		initGUI();
		pannello = new CostruttoreSottoPannello(componenti, labels, CostruttoreSottoPannello.VERTICAL);
	}

	private void initGUI() {
		try {

			jLabel9 = new LabelTestoPiccolo();
			labels[0] = jLabel9;
			jLabel9.setText("Mese");
			jLabel9.setBounds(16, 67, 67, 14);

			totaleMeseUscite = new TextFieldF();
			componenti[1] = totaleMeseUscite;
			totaleMeseUscite.setColumns(10);
			totaleMeseUscite.setText("0.0");
			totaleMeseUscite.setBounds(164, 84, 106, 27);

			totaleMeseEntrate = new TextFieldF();
			componenti[2] = totaleMeseEntrate;
			totaleMeseEntrate.setColumns(10);
			totaleMeseEntrate.setText("0.0");
			totaleMeseEntrate.setBounds(317, 85, 106, 27);

			jLabel12 = new LabelTestoPiccolo();
			labels[1] = jLabel12;
			jLabel12.setText("Uscite per mese");
			jLabel12.setBounds(164, 66, 114, 14);

			jLabel12 = new LabelTestoPiccolo();
			labels[2] = jLabel12;
			jLabel12.setText("Entrate per mese");
			jLabel12.setBounds(317, 67, 123, 14);

			// Combo Mesi
			ComboMese = new JComboBox();
			componenti[0] = ComboMese;
			ComboMese.setBounds(16, 85, 106, 27);
			ComboMese.addItem("");
			for (int i = 0; i < 12; i++)
				ComboMese.addItem(i + 1);
			ComboMese.setSelectedIndex(0);
			ComboMese.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					Object mounth = ComboMese.getSelectedItem();

					if (!mounth.equals("")) {
						int mese = Integer.parseInt(mounth.toString());
						double totaleMese = AltreUtil.arrotondaDecimaliDouble(Database.getSingleton().totaleUsciteMese(mese));
						double totaleMeseE = AltreUtil.arrotondaDecimaliDouble(Database.getSingleton().totaleEntrateMese(mese));
						totaleMeseUscite.setText(Double.toString(totaleMese));
						totaleMeseEntrate.setText(Double.toString(totaleMeseE));
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void azzeraCampi() {
		ComboMese.setSelectedIndex(0);
		totaleMeseUscite.setText("0.0");
		totaleMeseEntrate.setText("0.0");
	}

	public static JComboBox getComboMese() {
		return ComboMese;
	}

	public static void setComboMese(JComboBox comboMese) {
		ComboMese = comboMese;
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
