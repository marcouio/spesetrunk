package com.molinari.gestionespese.view.componenti.componentipannello;

import java.awt.Container;
import java.util.logging.Level;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;
import com.molinari.utility.messages.I18NManager;

public class SottoPannelloTotali {

	private static JTextField percentoVariabili;
	private static JTextField percentoFutili;
	private static JTextField avanzo;

	JComponent[]              componenti = new JComponent[3];
	JLabel[]                  labels     = new JLabel[3];
	CostruttoreSottoPannello  pannello;

	public SottoPannelloTotali(Container container) {
		super();
		pannello = new CostruttoreSottoPannello(new PannelloBase(container),componenti, labels);
		initGUI();
		pannello.initGUI(componenti, labels);
	}

	private void initGUI() {
		try {

			LabelTestoPiccolo jLabel2 = new LabelTestoPiccolo(pannello.getPannello());
			labels[0] = jLabel2;
			jLabel2.setText("% " + I18NManager.getSingleton().getMessaggio("spesefut"));
			jLabel2.setBounds(317, 67, 106, 14);
			jLabel2.setOpaque(true);

			LabelTestoPiccolo jLabel3 = new LabelTestoPiccolo(pannello.getPannello());
			labels[1] = jLabel3;
			jLabel3.setText("% " + I18NManager.getSingleton().getMessaggio("spesevar"));
			jLabel3.setBounds(164, 66, 141, 15);

			LabelTestoPiccolo jLabel4 = new LabelTestoPiccolo(pannello.getPannello());
			labels[2] = jLabel4;
			jLabel4.setText(I18NManager.getSingleton().getMessaggio("avanzo")+"/"+I18NManager.getSingleton().getMessaggio("disavanzo"));
			jLabel4.setBounds(16, 67, 128, 14);

			avanzo = new TextFieldBase(pannello.getPannello());
			componenti[2] = avanzo;
			avanzo.setColumns(10);

			final double differenza = AltreUtil.arrotondaDecimaliDouble(Database.eAnnuale() - Database.uAnnuale());
			avanzo.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(differenza)));
			avanzo.setBounds(16, 85, 106, 27);
			avanzo.setSize(92, 27);

			final double percVariabili = Database.percentoUscite(CatSpese.IMPORTANZA_VARIABILE);

			final double percFutili = Database.percentoUscite(CatSpese.IMPORTANZA_FUTILE);

			percentoVariabili = new TextFieldBase(pannello.getPannello());
			componenti[1] = percentoVariabili;
			percentoVariabili.setColumns(10);
			percentoVariabili.setText(Double.toString(percVariabili));
			percentoVariabili.setBounds(164, 84, 106, 27);
			percentoVariabili.setSize(92, 27);

			percentoFutili = new TextFieldBase(pannello.getPannello());
			componenti[0] = percentoFutili;
			percentoFutili.setColumns(10);
			percentoFutili.setText(Double.toString(percFutili));
			percentoFutili.setBounds(317, 85, 106, 27);
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
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
