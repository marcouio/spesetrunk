package com.molinari.gestionespese.view.componenti.componentipannello;

import java.util.logging.Level;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.view.font.LabelTestoPiccolo;
import com.molinari.gestionespese.view.font.TextFieldF;

import controller.ControlloreBase;

public class SottoPannelloDatiEntrate {

	private JTextField entrateMesePrec;
	private JTextField enAnCorso;
	private JTextField enMeCorso;

	private JComponent[]              componenti = new JComponent[3];
	private JLabel[]                  labels     = new JLabel[3];
	private CostruttoreSottoPannello  pannello;

	public SottoPannelloDatiEntrate() {
		super();
		initGUI();
		pannello = new CostruttoreSottoPannello(componenti, labels, CostruttoreSottoPannello.VERTICAL);
	}

	private void initGUI() {
		try {
			final JLabel label1 = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("thisyear"));
			label1.setBounds(164, 66, 141, 14);
			labels[2] = label1;

			final JLabel label2 = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("lastmonth"));
			label2.setBounds(16, 67, 136, 14);
			labels[1] = label2;

			final JLabel label3 = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("thismonth"));
			label3.setBounds(317, 67, 113, 14);
			labels[0] = label3;

			entrateMesePrec = new TextFieldF();
			final double emensile = Database.eMensile();
			entrateMesePrec.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(emensile)));
			entrateMesePrec.setBounds(16, 85, 106, 27);
			componenti[1] = entrateMesePrec;
			entrateMesePrec.setColumns(10);

			enAnCorso = new TextFieldF();

			final double eAnnuale = Database.eAnnuale();
			enAnCorso.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(eAnnuale)));
			enAnCorso.setBounds(164, 84, 106, 27);
			componenti[2] = enAnCorso;
			enAnCorso.setColumns(10);

			enMeCorso = new TextFieldF();
			final double eMensile = Database.eMensileInCorso();
			enMeCorso.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(eMensile)));
			enMeCorso.setBounds(317, 85, 106, 27);
			componenti[0] = enMeCorso;
			enMeCorso.setColumns(10);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public JTextField getEntrateMesePrec() {
		return this.entrateMesePrec;

	}

	public void setEntrateMesePrec(JTextField entrateMesePrec) {
		this.entrateMesePrec = entrateMesePrec;
	}

	public JTextField getEnAnCorso() {
		return enAnCorso;
	}

	public void setEnAnCorso(JTextField enAnCorso) {
		this.enAnCorso = enAnCorso;
	}

	public JTextField getEnMeCorso() {
		return enMeCorso;
	}

	public void setEnMeCorso(JTextField enMeCorso) {
		this.enMeCorso = enMeCorso;
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
