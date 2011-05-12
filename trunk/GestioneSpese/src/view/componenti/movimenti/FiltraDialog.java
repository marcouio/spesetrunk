package view.componenti.movimenti;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TextFieldF;
import business.AltreUtil;

public abstract class FiltraDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private TextFieldF        tfDa;
	private TextFieldF        tfA;
	private TextFieldF        tfNome;
	private TextFieldF        tfEuro;
	protected JComboBox       comboBoxCat;

	private String            dataDa;
	private String            dataA;
	private String            nome;
	private Double            euro;
	protected String          categoria;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FiltraDialog dialog = new FiltraDialog() {

				private static final long serialVersionUID = 1L;

				@Override
				public String[][] getMovimenti() {
					return null;
				}
			};
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FiltraDialog() {
		setTitle("Filtra Movimenti");
		setBounds(100, 100, 663, 148);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 83, 661, 35);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				ButtonF okButton = new ButtonF("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						getMovimenti();
						dispose();
					}
				});

				getRootPane().setDefaultButton(okButton);
			}
			{
				ButtonF cancelButton = new ButtonF("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}
		{
			tfDa = new TextFieldF();
			tfDa.setColumns(10);
			tfDa.setBounds(62, 26, 89, 25);
			getContentPane().add(tfDa);
		}
		{
			LabelTesto label = new LabelTesto("Da:");
			label.setBounds(36, 37, 43, 15);
			getContentPane().add(label);
		}
		{
			LabelTesto label = new LabelTesto("A:");
			label.setBounds(46, 65, 43, 15);
			getContentPane().add(label);
		}
		{
			tfA = new TextFieldF();
			tfA.setColumns(10);
			tfA.setBounds(62, 56, 89, 25);
			getContentPane().add(tfA);
		}
		{
			tfNome = new TextFieldF();
			tfNome.setColumns(10);
			tfNome.setBounds(209, 26, 89, 25);
			getContentPane().add(tfNome);
		}
		{
			LabelTesto label = new LabelTesto("Nome:");
			label.setBounds(163, 26, 55, 15);
			getContentPane().add(label);
		}
		{
			LabelTesto label = new LabelTesto("Euro:");
			label.setBounds(303, 26, 55, 15);
			getContentPane().add(label);
		}
		{
			tfEuro = new TextFieldF();
			tfEuro.setColumns(10);
			tfEuro.setBounds(341, 26, 89, 25);
			getContentPane().add(tfEuro);
		}
		{
			LabelTesto label = new LabelTesto("Categoria:");
			label.setBounds(436, 26, 82, 15);
			getContentPane().add(label);
		}
	}

	public abstract String[][] getMovimenti();

	protected String getDataDa() {
		if (!tfDa.getText().equals("")) {
			dataDa = tfDa.getText();
		}
		return dataDa;
	}

	protected void setDataDa(String dataDa) {
		this.dataDa = dataDa;
	}

	protected String getDataA() {
		if (!tfA.getText().equals("")) {
			dataA = tfA.getText();
		}
		return dataA;
	}

	protected void setDataA(String dataA) {
		this.dataA = dataA;
	}

	protected String getNome() {
		if (!tfNome.getText().equals("")) {
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
