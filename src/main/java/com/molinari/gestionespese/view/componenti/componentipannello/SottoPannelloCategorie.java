package com.molinari.gestionespese.view.componenti.componentipannello;

import java.awt.Container;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;
import com.molinari.utility.messages.I18NManager;

public class SottoPannelloCategorie {

	private JComboBox<ICatSpese> categorieCombo;
	private JTextField totaleMeseCategoria;
	private JTextField totaleAnnualeCateg;

	JComponent[] componenti = new JComponent[3];
	JLabel[] labels = new JLabel[3];
	CostruttoreSottoPannello pannello;

	public SottoPannelloCategorie(Container container) {
		super();
		pannello = new CostruttoreSottoPannello(new PannelloBase(container), componenti, labels);
		initGUI();
		pannello.initGUI(componenti, labels);
	}

	private void initGUI() {
		try {

			LabelTestoPiccolo jLabel5 = new LabelTestoPiccolo(pannello.getPannello());
			jLabel5.setText(I18NManager.getSingleton().getMessaggio("categories"));
			jLabel5.setBounds(177, 25, 90, 19);
			labels[0] = jLabel5;

			LabelTestoPiccolo jLabel11 = new LabelTestoPiccolo(pannello.getPannello());
			jLabel11.setText(I18NManager.getSingleton().getMessaggio("annualtotal"));
			jLabel11.setBounds(135, 67, 78, 14);
			labels[1] = jLabel11;

			LabelTestoPiccolo jLabel6 = new LabelTestoPiccolo(pannello.getPannello());
			jLabel6.setText(I18NManager.getSingleton().getMessaggio("monthlytotal"));
			jLabel6.setBounds(253, 67, 106, 14);
			labels[2] = jLabel6;

			totaleAnnualeCateg = new TextFieldBase(pannello.getPannello());
			totaleAnnualeCateg.setColumns(10);
			totaleAnnualeCateg.setText("0.0");
			totaleAnnualeCateg.setBounds(135, 83, 106, 27);
			componenti[1] = totaleAnnualeCateg;

			totaleMeseCategoria = new TextFieldBase(pannello.getPannello());
			totaleMeseCategoria.setColumns(10);
			totaleMeseCategoria.setText("0.0");
			totaleMeseCategoria.setBounds(253, 83, 106, 27);
			componenti[2] = totaleMeseCategoria;

			// CategoriaSpese
			final List<ICatSpese> listCategoriePerCombo = CacheCategorie.getSingleton().getListCategoriePerCombo();
			categorieCombo = new JComboBox<>(new Vector<>(listCategoriePerCombo));

			categorieCombo.setBounds(16, 85, 106, 27);

			categorieCombo.setSelectedIndex(0);
			categorieCombo.addItemListener(e -> {
				CatSpese spese = null;
				if (categorieCombo.getSelectedIndex() != 0) {
					spese = (CatSpese) categorieCombo.getSelectedItem();
					final int mese = new GregorianCalendar().get(Calendar.MONTH) + 1;
					double spesa = getSpeseMeseCategorie(spese, mese);

					totaleAnnualeCateg.setText(Double.toString(Database.totaleUscitaAnnoCategoria(spese.getidCategoria())));
					totaleMeseCategoria.setText(Double.toString(spesa));
				}
			});
			componenti[0] = categorieCombo;

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private double getSpeseMeseCategorie(CatSpese spese, final int mese) {
		try {
			return Database.speseMeseCategoria(mese, spese.getidCategoria());
		} catch (final Exception e1) {
			ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
		}
		return 0;
	}

	public JComboBox<ICatSpese> getCategorieCombo() {
		return categorieCombo;
	}

	public void setCategorieCombo(final JComboBox<ICatSpese> categorieCombo) {
		this.categorieCombo = categorieCombo;
	}

	public JTextField getTotaleMeseCategoria() {
		return totaleMeseCategoria;
	}

	public void setTotaleMeseCategoria(final JTextField totaleMeseCategoria) {
		this.totaleMeseCategoria = totaleMeseCategoria;
	}

	public JTextField getTotaleAnnualeCateg() {
		return totaleAnnualeCateg;
	}

	public void setTotaleAnnualeCateg(final JTextField totaleAnnualeCateg) {
		this.totaleAnnualeCateg = totaleAnnualeCateg;
	}

	public void azzeraCampi() {
		if (categorieCombo != null && totaleAnnualeCateg != null && totaleMeseCategoria != null) {
			categorieCombo.setSelectedIndex(0);
			totaleAnnualeCateg.setText("0.0");
			totaleMeseCategoria.setText("0.0");
		}
	}

	protected CostruttoreSottoPannello getPannello() {
		return pannello;
	}

	protected void setPannello(final CostruttoreSottoPannello pannello) {
		this.pannello = pannello;
	}

	protected JComponent[] getComponenti() {
		return componenti;
	}

	protected void setComponenti(final JComponent[] componenti) {
		this.componenti = componenti;
	}

	protected JLabel[] getLabels() {
		return labels;
	}

	protected void setLabels(final JLabel[] labels) {
		this.labels = labels;
	}

}
