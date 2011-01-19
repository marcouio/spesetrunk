package view.font;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class TextFieldF extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextFieldF() {
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TextFieldF(String text) {
		super(text);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TextFieldF(int columns) {
		super(columns);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TextFieldF(String text, int columns) {
		super(text, columns);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TextFieldF(Document doc, String text, int columns) {
		super(doc, text, columns);
		// TODO Auto-generated constructor stub
	}

}
