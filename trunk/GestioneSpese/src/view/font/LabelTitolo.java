package view.font;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class LabelTitolo extends JLabel{

	private static final long serialVersionUID = 1L;

	public LabelTitolo() {
		super();
		setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		setForeground(Color.darkGray);
	}

	public LabelTitolo(final String string) {
		super(string);
		setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		setForeground(Color.darkGray);
	}
}
