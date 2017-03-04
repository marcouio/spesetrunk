
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

public class SottoPannelloDatiSpese {


	private static JTextField meseInCors;
	private static JTextField speseAnnuali;
	private double     annuale;
	private static JTextField mesePrecUsc;
	private double     mensile;

	private JComponent[]              componenti = new JComponent[3];
	private JLabel[]                  labels     = new JLabel[3];
	private CostruttoreSottoPannello  pannello;
	
	public SottoPannelloDatiSpese() {
		super();
		initGUI();
		pannello = new CostruttoreSottoPannello(componenti, labels, CostruttoreSottoPannello.VERTICAL);
	}
	
	private void initGUI() {
		try {

			final JLabel meseincorso = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("thisyear"));
			labels[2] = meseincorso;
			meseincorso.setBounds(164, 66, 141, 14);

			speseAnnuali = new TextFieldF();
			componenti[2] = speseAnnuali;
			speseAnnuali.setBounds(164, 84, 106, 27);
			speseAnnuali.setColumns(8);

			final JLabel label = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("lastmonth"));
			label.setBounds(317, 67, 123, 14);
			labels[1] = label;

			mensile = Database.uMensile();
			mesePrecUsc = new TextFieldF();
			mesePrecUsc.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(mensile)));
			mesePrecUsc.setBounds(317, 85, 106, 27);
			componenti[1] = mesePrecUsc;

			final JLabel label2 = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("thismonth"));
			label2.setBounds(16, 67, 136, 13);
			labels[0] = label2;

			annuale = Database.uAnnuale();
			speseAnnuali.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(annuale)));

			meseInCors = new TextFieldF();
			double mensile2 = Database.uMensileInCorso();
			meseInCors.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(mensile2)));
			componenti[0] = meseInCors;
			meseInCors.setBounds(16, 85, 106, 27);
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
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

	public double getAnnuale() {
		return annuale;
	}

	public void setAnnuale(double annuale) {
		this.annuale = annuale;
	}

	public static JTextField getMesePrecUsc() {
		return mesePrecUsc;
	}

	public static void setMesePrecUsc(JTextField mesePrecUsc) {
		SottoPannelloDatiSpese.mesePrecUsc = mesePrecUsc;
	}

	public double getMensile() {
		return mensile;
	}

	public void setMensile(double mensile) {
		this.mensile = mensile;
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