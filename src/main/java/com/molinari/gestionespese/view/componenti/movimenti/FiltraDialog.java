package com.molinari.gestionespese.view.componenti.movimenti;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;
import com.molinari.utility.messages.I18NManager;

public abstract class FiltraDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final TextFieldBase tfDa;
	private final TextFieldBase tfA;
	private final TextFieldBase tfNome;
	private final TextFieldBase tfEuro;
	protected JComboBox<?> comboBoxCat;

	private String dataDa;
	private String dataA;
	private String nome;
	private Double euro;
	protected String categoria;

	/**
	 * Create the dialog.
	 */
	public FiltraDialog() {
		setTitle(I18NManager.getSingleton().getMessaggio("filtertrans"));
		setBounds(100, 100, 663, 168);
		getContentPane().setLayout(null);

		final JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 83, 631, 35);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);

		final ButtonBase okButton = new ButtonBase("OK", getContentPane());
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		okButton.addActionListener(e -> {
			getMovimenti();
			dispose();
		});

		getRootPane().setDefaultButton(okButton);

		final ButtonBase cancelButton = new ButtonBase(I18NManager.getSingleton().getMessaggio("cancel"), getContentPane());
		cancelButton.setActionCommand(I18NManager.getSingleton().getMessaggio("cancel"));
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(e -> dispose());

		tfDa = new TextFieldBase(getContentPane());
		tfDa.setColumns(10);
		tfDa.setBounds(62, 26, 89, 25);
		getContentPane().add(tfDa);

		LabelTestoPiccolo label = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("from") + ":", getContentPane());
		label.setBounds(17, 28, 43, 15);
		getContentPane().add(label);

		label = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("to")+ ":", getContentPane());
		label.setBounds(18, 59, 43, 15);
		getContentPane().add(label);

		tfA = new TextFieldBase(getContentPane());
		tfA.setColumns(10);
		tfA.setBounds(62, 56, 89, 25);
		getContentPane().add(tfA);

		tfNome = new TextFieldBase(getContentPane());
		tfNome.setColumns(10);
		tfNome.setBounds(209, 26, 89, 25);
		getContentPane().add(tfNome);

		label = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("name")+ ":", getContentPane());
		label.setBounds(163, 26, 55, 15);
		getContentPane().add(label);

		label = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("eur")+ ":", getContentPane());
		label.setBounds(307, 27, 55, 15);
		getContentPane().add(label);

		tfEuro = new TextFieldBase(getContentPane());
		tfEuro.setColumns(10);
		tfEuro.setBounds(341, 26, 89, 25);
		getContentPane().add(tfEuro);
		label = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("category")+ ":", getContentPane());
		label.setBounds(443, 27, 82, 15);
		getContentPane().add(label);

	}

	public abstract String[][] getMovimenti();

	protected String getDataDa() {
		if (!"".equals(tfDa.getText())) {
			dataDa = tfDa.getText();
		}
		return dataDa;
	}

	protected void setDataDa(String dataDa) {
		this.dataDa = dataDa;
	}

	protected String getDataA() {
		if (!"".equals(tfA.getText())) {
			dataA = tfA.getText();
		}
		return dataA;
	}

	protected void setDataA(String dataA) {
		this.dataA = dataA;
	}

	protected String getNome() {
		if (!"".equals(tfNome.getText())) {
			nome = tfNome.getText();
		}
		return nome;
	}

	protected void setNome(String nome) {
		this.nome = nome;
	}

	protected Double getEuro() {
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			euro = Double.parseDouble(tfEuro.getText());
		}
		return euro;
	}

	protected void setEuro(double euro) {
		this.euro = euro;
	}

	protected String getCategoria() {
		return categoria;
	}

	protected void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
