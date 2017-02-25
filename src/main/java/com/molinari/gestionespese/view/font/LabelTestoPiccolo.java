package com.molinari.gestionespese.view.font;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class LabelTestoPiccolo extends JLabel {

	private static final long serialVersionUID = 1L;

	public LabelTestoPiccolo() {
		setFont(new Font("Eras Light ITC", Font.PLAIN, 10));
		setForeground(Color.darkGray);
	}

	public LabelTestoPiccolo(final String string) {
		super(string);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 10));
		setForeground(Color.darkGray);
	}

}
