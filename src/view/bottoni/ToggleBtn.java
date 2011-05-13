package view.bottoni;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

/**
 * La classe estende JToggleButton per modificarne il funzionamento di default
 */
public class ToggleBtn extends JToggleButton {
	private static final long serialVersionUID           = 1L;
	String                    s;
	ImageIcon                 i;

	MyIcon                    icona;
	private JPanel            padre;
	private final int         distanzaBordoImageX        = 10;
	private final int         distanzaBordoXTestoDaIcona = 5;

	public MyIcon getMyIcon() {
		return icona != null ? icona : new MyIcon();
	}

	public ToggleBtn(final String text) {
		super(text);
		s = text;
	}

	public ToggleBtn(final String text, final ImageIcon icon, JPanel padre) {
		this(text, icon);
		this.setPadre(padre);
		s = text;
		i = icon;

	}

	public ToggleBtn(final String text, final ImageIcon icon) {
		super(text, icon);
		s = text;
		i = icon;
	}

	public ToggleBtn(final ImageIcon icon) {
		super(icon);
		i = icon;
	}

	@Override
	public void paintComponent(final Graphics g) {
		this.setIcon(new ImageIcon());
		this.setText(s);
		this.setIconTextGap(42);
		super.paintComponent(g);
		this.setIcon(i);
		getIcon().paintIcon(this, g, distanzaBordoImageX, getHeight() / 2 - i.getIconHeight() / 2);
		if (this.isSelected()) {
			disegnaBottone(g, Color.GRAY, new Color(167, 243, 239));
		}
	}// end paint

	public void disegnaBottone(Graphics g, final Color foreground,
	                final Color selected) {
		final int w = getWidth();
		final int h = getHeight();
		g.setColor(selected); // background color
		g.fillRoundRect(1, 1, w - 1, h - 1, 7, 7);
		g.setColor(Color.WHITE);
		g.drawRoundRect(1, 1, w - 2, h - 2, 7, 7);
		g.setColor(foreground); // foreground color
		if (i != null) {
			g.drawImage(i.getImage(), distanzaBordoImageX, getHeight() / 2 - i.getIconHeight() / 2, null);
		}
		if (s != null && i != null) {
			g.drawString(s, i.getIconWidth() + distanzaBordoImageX + distanzaBordoXTestoDaIcona, (h + g.getFontMetrics().getAscent()) / 2 - 1);
		} else {
			g.drawString(s, 25 + distanzaBordoImageX + distanzaBordoXTestoDaIcona, (h + g.getFontMetrics().getAscent()) / 2 - 1);
		}
	}

	public void settaggioBottoneStandard() {
		BorderFactory.createEtchedBorder();
		this.setRolloverEnabled(true);
		this.setBackground(Color.WHITE);
		this.setHorizontalAlignment(SwingConstants.LEFT); // allinea il
		if (this instanceof ToggleBtn) {
			final Icon icona1 = (this).getMyIcon();
			this.setRolloverIcon(icona1);
		}
		this.setRolloverEnabled(true);
	}

	public void setPadre(JPanel padre) {
		this.padre = padre;
	}

	public JPanel getPadre() {
		return padre;
	}

	/**
	 * ﻿ * Estende la classe Icon per settarla come Icon di default al rollover
	 * di ﻿ * ToggleBtn ﻿
	 */
	private class MyIcon implements Icon {
		public MyIcon() {
			super();
		}

		@Override
		public int getIconHeight() {
			return getHeight();
		}

		@Override
		public int getIconWidth() {
			return getWidth();
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int x,
		                final int y) {
			if (g != null) {
				disegnaBottone(g, Color.BLACK, new Color(252, 228, 179));
			}
		}
	}

}