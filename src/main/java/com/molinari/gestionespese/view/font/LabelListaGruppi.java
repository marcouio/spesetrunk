package com.molinari.gestionespese.view.font;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class LabelListaGruppi extends JLabel{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public LabelListaGruppi() {
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
		setForeground(Color.darkGray);
	}

	public LabelListaGruppi(String string) {
		super(string);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
		setForeground(Color.darkGray);
	}

}
