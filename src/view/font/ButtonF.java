package view.font;

import java.awt.Font;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.Color;

public class ButtonF extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ButtonF() {
		setBackground(Color.GRAY);
		setForeground(Color.WHITE);
		setFont(new Font("DejaVu Sans", Font.PLAIN, 11));
	}

	public ButtonF(Icon arg0) {
		super(arg0);
	}

	public ButtonF(String arg0) {
		super(arg0);
		setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 10));
	}

	public ButtonF(Action arg0) {
		super(arg0);
	}

	public ButtonF(String arg0, Icon arg1) {
		super(arg0, arg1);
	}

}
