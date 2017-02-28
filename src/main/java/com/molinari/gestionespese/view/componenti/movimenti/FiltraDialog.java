package com.molinari.gestionespese.view.componenti.movimenti;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.TextFieldF;

public abstract class FiltraDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final TextFieldF tfDa;
	private final TextFieldF tfA;
	private final TextFieldF tfNome;
	private final TextFieldF tfEuro;
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
		setBounds(100, 100, 663, 148);
		getContentPane().setLayout(null);

		final JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 83, 661, 35);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);

		final ButtonF okButton = new ButtonF("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		okButton.addActionListener(e -> {
			getMovimenti();
			dispose();
		});

		getRootPane().setDefaultButton(okButton);

		final ButtonF cancelButton = new ButtonF(I18NManager.getSingleton().getMessaggio("cancel"));
		cancelButton.setActionCommand(I18NManager.getSingleton().getMessaggio("cancel"));
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(e -> dispose());

		tfDa = new TextFieldF();
		tfDa.setColumns(10);
		tfDa.setBounds(62, 26, 89, 25);
		getContentPane().add(tfDa);

		LabelListaGruppi label = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("from") + ":");
		label.setBounds(17, 28, 43, 15);
		getContentPane().add(label);

		label = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio(
				"to")
				+ ":");
		label.setBounds(18, 59, 43, 15);
		getContentPane().add(label);

		tfA = new TextFieldF();
		tfA.setColumns(10);
		tfA.setBounds(62, 56, 89, 25);
		getContentPane().add(tfA);

		tfNome = new TextFieldF();
		tfNome.setColumns(10);
		tfNome.setBounds(209, 26, 89, 25);
		getContentPane().add(tfNome);

		label = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio(
				"name")
				+ ":");
		label.setBounds(163, 26, 55, 15);
		getContentPane().add(label);

		label = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio(
				"eur")
				+ ":");
		label.setBounds(307, 27, 55, 15);
		getContentPane().add(label);

		tfEuro = new TextFieldF();
		tfEuro.setColumns(10);
		tfEuro.setBounds(341, 26, 89, 25);
		getContentPane().add(tfEuro);
		label = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio(
				"category")
				+ ":");
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
