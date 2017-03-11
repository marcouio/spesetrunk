package com.molinari.gestionespese.view.componenti.componentipannello;

import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.view.font.LabelTestoPiccolo;
import com.molinari.gestionespese.view.font.TextFieldF;

import controller.ControlloreBase;

public class SottoPannelloMesi {

	private static JTextField totaleMeseUscite;
	private static JComboBox<String>  comboMese;
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

			LabelTestoPiccolo jLabel9 = new LabelTestoPiccolo();
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

			LabelTestoPiccolo jLabel12 = new LabelTestoPiccolo();
			labels[1] = jLabel12;
			jLabel12.setText("Uscite per mese");
			jLabel12.setBounds(164, 66, 114, 14);

			jLabel12 = new LabelTestoPiccolo();
			labels[2] = jLabel12;
			jLabel12.setText("Entrate per mese");
			jLabel12.setBounds(317, 67, 123, 14);

			// Combo Mesi
			comboMese = new JComboBox<>();
			componenti[0] = comboMese;
			comboMese.setBounds(16, 85, 106, 27);
			comboMese.addItem("");
			for (int i = 1; i <= 12; i++) {
				comboMese.addItem(Integer.toString(i));
			}
			comboMese.setSelectedIndex(0);
			comboMese.addItemListener(e -> {
				final Object mounth = comboMese.getSelectedItem();

				if (!"".equals(mounth)) {
					final int mese = Integer.parseInt(mounth.toString());
					final double totaleMese = AltreUtil.arrotondaDecimaliDouble(Database.getSingleton().totaleUsciteMese(mese));
					final double totaleMeseE = AltreUtil.arrotondaDecimaliDouble(Database.getSingleton().totaleEntrateMese(mese));
					totaleMeseUscite.setText(Double.toString(totaleMese));
					totaleMeseEntrate.setText(Double.toString(totaleMeseE));
				}
			});

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public static void azzeraCampi() {
		comboMese.setSelectedIndex(0);
		totaleMeseUscite.setText("0.0");
		totaleMeseEntrate.setText("0.0");
	}

	public static JComboBox<String> getComboMese() {
		return comboMese;
	}

	public static void setComboMese(JComboBox<String> comboMese) {
		SottoPannelloMesi.comboMese = comboMese;
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
