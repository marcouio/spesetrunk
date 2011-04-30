package view.sidebar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/**
 * La classe estende JToggleButton per modificarne il funzionamento di default
 */
public class ToggleBtn extends JToggleButton {
	private static final long serialVersionUID = 1L;
	String s;
	ImageIcon i;

	MyIcon icona;

	public MyIcon getMyIcon() {
		return icona != null ? icona : new MyIcon();
	}

	public ToggleBtn(final String text) {
		super(text);
		s = text;
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

	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (this.isSelected()) {
			disegnaBottone(g, Color.GRAY, new Color(167, 243, 239));
		}
	}// end paint

	public void disegnaBottone(Graphics g, final Color foreground,
			final Color selected) {
		final int w = getWidth();
		final int h = getHeight();
		g.setColor(selected); // background color
		g.fillRect(0, 0, w, h);
		g.setColor(foreground); // foreground color
		if (i != null) {
			g.drawImage(i.getImage(), 0, getHeight() / 2 - i.getIconHeight()
					/ 2, null);
		}
		if (s != null) {
			g.drawString(s, (i.getIconWidth()) + 4, (h + g.getFontMetrics()
					.getAscent()) / 2 - 1);
		}
	}

	/**
	 * Estende la classe Icon per settarla come Icon di default al rollover di
	 * ToggleBtn
	 */
	private class MyIcon implements Icon {
		public MyIcon() {
			super();
		}

		public int getIconHeight() {
			return getHeight();
		}

		public int getIconWidth() {
			return getWidth();
		}

		public void paintIcon(final Component c, final Graphics g, final int x,
				final int y) {
			if (g != null) {
				disegnaBottone(g, Color.BLACK,new Color(252, 228, 179));
			}
		}
	}

}