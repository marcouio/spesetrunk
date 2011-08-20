package view.font;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

public class CheckBoxF extends JCheckBox{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckBoxF() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckBoxF(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public CheckBoxF(Icon icon, boolean selected) {
		super(icon, selected);
		// TODO Auto-generated constructor stub
	}

	public CheckBoxF(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public CheckBoxF(String text, boolean selected) {
		super(text, selected);
		// TODO Auto-generated constructor stub
	}

	public CheckBoxF(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
		// TODO Auto-generated constructor stub
	}

	public CheckBoxF(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	public CheckBoxF(String text) {
		super(text);
		setForeground(Color.DARK_GRAY);
		setFont(new Font("Century Gothic", Font.PLAIN, 10));
		
	}

}
