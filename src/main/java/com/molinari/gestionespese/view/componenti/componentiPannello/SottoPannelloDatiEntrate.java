package com.molinari.gestionespese.view.componenti.componentiPannello;

import java.util.logging.Level;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.view.font.LabelTestoPiccolo;
import com.molinari.gestionespese.view.font.TextFieldF;

public class SottoPannelloDatiEntrate {

	private static final long serialVersionUID = 1L;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new SottoPannelloDatiEntrate().getPannello());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private static JTextField EntrateMesePrec;
	private static JTextField enAnCorso;
	private static JTextField enMeCorso;

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
			JLabel label_1 = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("thisyear"));
			label_1.setBounds(164, 66, 141, 14);
			labels[2] = label_1;

			JLabel label_2 = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("lastmonth"));
			label_2.setBounds(16, 67, 136, 14);
			labels[1] = label_2;

			JLabel label_3 = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("thismonth"));
			label_3.setBounds(317, 67, 113, 14);
			labels[0] = label_3;

			EntrateMesePrec = new TextFieldF();
			double Emensile = Database.eMensile();
			EntrateMesePrec.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(Emensile)));
			EntrateMesePrec.setBounds(16, 85, 106, 27);
			componenti[1] = EntrateMesePrec;
			EntrateMesePrec.setColumns(10);

			enAnCorso = new TextFieldF();

			double eAnnuale = Database.EAnnuale();
			enAnCorso.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(eAnnuale)));
			enAnCorso.setBounds(164, 84, 106, 27);
			componenti[2] = enAnCorso;
			enAnCorso.setColumns(10);

			enMeCorso = new TextFieldF();
			double eMensile = Database.eMensileInCorso();
			enMeCorso.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(eMensile)));
			enMeCorso.setBounds(317, 85, 106, 27);
			componenti[0] = enMeCorso;
			enMeCorso.setColumns(10);

		} catch (Exception e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public static JTextField getEntrateMesePrec() {
		return SottoPannelloDatiEntrate.EntrateMesePrec;

	}

	public static void setEntrateMesePrec(JTextField entrateMesePrec) {
		EntrateMesePrec = entrateMesePrec;
	}

	public static JTextField getEnAnCorso() {
		return enAnCorso;
	}

	public static void setEnAnCorso(JTextField enAnCorso) {
		SottoPannelloDatiEntrate.enAnCorso = enAnCorso;
	}

	public static JTextField getEnMeCorso() {
		return enMeCorso;
	}

	public static void setEnMeCorso(JTextField enMeCorso) {
		enMeCorso = enMeCorso;
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
