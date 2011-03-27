package view.font;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class LabelTesto extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LabelTesto() {
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
		setForeground(Color.darkGray);
	}
	
	public LabelTesto(String string) {
		super(string);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
		setForeground(Color.darkGray);
	}

}
