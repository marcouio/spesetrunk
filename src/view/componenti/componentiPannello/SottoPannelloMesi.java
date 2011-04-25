package view.componenti.componentiPannello;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.font.LabelTesto;
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

	ArrayList<JComponent>     componenti = new ArrayList<JComponent>();
	ArrayList<JLabel>         labels     = new ArrayList<JLabel>();
	CostruttoreSottoPannello  pannello;

	public SottoPannelloMesi() {
		super();
		initGUI();
		pannello = new CostruttoreSottoPannello(componenti, labels);
	}

	private void initGUI() {
		try {
			// jLabel8 = new LabelTitolo();
			// this.add(jLabel8);
			// jLabel8.setText("Mesi");
			// jLabel8.setBounds(177, 25, 90, 19);

			jLabel9 = new LabelTesto();
			labels.add(jLabel9);
			jLabel9.setText("Mese");
			jLabel9.setBounds(16, 67, 67, 14);

			totaleMeseUscite = new TextFieldF();
			componenti.add(totaleMeseUscite);
			totaleMeseUscite.setColumns(10);
			totaleMeseUscite.setText("0.0");
			totaleMeseUscite.setBounds(164, 84, 106, 27);

			totaleMeseEntrate = new TextFieldF();
			componenti.add(totaleMeseEntrate);
			totaleMeseEntrate.setColumns(10);
			totaleMeseEntrate.setText("0.0");
			totaleMeseEntrate.setBounds(317, 85, 106, 27);

			jLabel12 = new LabelTesto();
			labels.add(jLabel12);
			jLabel12.setText("Uscite per mese");
			jLabel12.setBounds(164, 66, 114, 14);

			jLabel12 = new LabelTesto();
			labels.add(jLabel12);
			jLabel12.setText("Entrate per mese");
			jLabel12.setBounds(317, 67, 123, 14);

			// jSeparator1 = new JSeparator();
			// this.add(jSeparator1);
			// jSeparator1.setBounds(10, 128, 420, 16);

			// Combo Mesi
			ComboMese = new JComboBox();
			componenti.add(ComboMese);
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

			// this.setPreferredSize(new Dimension(440, 150));
			// this.setLayout(null);
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
