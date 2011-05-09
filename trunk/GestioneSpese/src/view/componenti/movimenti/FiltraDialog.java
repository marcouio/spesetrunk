package view.componenti.movimenti;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TextFieldF;

public class FiltraDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private TextFieldF        textField;
	private TextFieldF        textField_1;
	private TextFieldF        textField_2;
	private TextFieldF        textField_3;
	private TextFieldF        textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FiltraDialog dialog = new FiltraDialog();
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
						        // TODO Auto-generated method stub

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
			textField = new TextFieldF();
			textField.setColumns(10);
			textField.setBounds(62, 26, 89, 25);
			getContentPane().add(textField);
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
			textField_1 = new TextFieldF();
			textField_1.setColumns(10);
			textField_1.setBounds(62, 56, 89, 25);
			getContentPane().add(textField_1);
		}
		{
			textField_2 = new TextFieldF();
			textField_2.setColumns(10);
			textField_2.setBounds(209, 26, 89, 25);
			getContentPane().add(textField_2);
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
			textField_3 = new TextFieldF();
			textField_3.setColumns(10);
			textField_3.setBounds(341, 26, 89, 25);
			getContentPane().add(textField_3);
		}
		{
			LabelTesto label = new LabelTesto("Categoria:");
			label.setBounds(436, 26, 82, 15);
			getContentPane().add(label);
		}
		{
			textField_4 = new TextFieldF();
			textField_4.setColumns(10);
			textField_4.setBounds(512, 26, 89, 25);
			getContentPane().add(textField_4);
		}
	}

}
