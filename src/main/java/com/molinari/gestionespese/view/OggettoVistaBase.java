package com.molinari.gestionespese.view;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class OggettoVistaBase extends JPanel {
	protected Font titolo;

	public OggettoVistaBase(final GridLayout gridLayout) {
		super(gridLayout);
	}

	public OggettoVistaBase() {
		super();
		titolo = new Font("Tahoma", Font.BOLD | Font.ITALIC, 14);

	}
	protected static final long serialVersionUID = 1L;

	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame inst = new JFrame();
				inst.setSize(950, 700);
				inst.getContentPane().add(new OggettoVistaBase());
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);

			}
		});
	}

}
