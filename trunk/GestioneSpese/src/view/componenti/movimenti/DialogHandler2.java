package view.componenti.movimenti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogHandler2 extends WindowAdapter implements ActionListener {
	private JButton closeButton = new JButton("chiudi");
	private JDialog dialog;
	
	public DialogHandler2(JFrame owner) {
		closeButton.addActionListener(this);
		JPanel content = new JPanel(new BorderLayout());
		content.setPreferredSize(new Dimension(300, 200));
		JPanel center = new JPanel();
		content.add(center, BorderLayout.CENTER);
		JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonContainer.add(closeButton);
		content.add(buttonContainer, BorderLayout.SOUTH);
		dialog = new JDialog(owner);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setTitle(owner.getTitle() + " - Dialog");
		dialog.setContentPane(content);
	}
	
	public void showDialog() {
		if(dialog.isDisplayable() == false) {
			dialog.pack();
		}
		dialog.setVisible(true);
	}
	
	public void disposeDialog() {
		dialog.dispose();
	}
	
	public void windowClosing(WindowEvent e) {
		disposeDialog();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeButton) {
			disposeDialog();
		}
	}
}