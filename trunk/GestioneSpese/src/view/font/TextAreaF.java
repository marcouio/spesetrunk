package view.font;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.text.Document;

public class TextAreaF extends JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextAreaF() {
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
		setBackground(Color.LIGHT_GRAY);
	}

	public TextAreaF(String arg0) {
		super(arg0);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
		setBackground(Color.LIGHT_GRAY);
	}

	public TextAreaF(Document arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public TextAreaF(int arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public TextAreaF(String arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
		setBackground(Color.LIGHT_GRAY);
	}

	public TextAreaF(Document arg0, String arg1, int arg2, int arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
