package view.sidebar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

/**
 * La classe estende JToggleButton per modificarne il funzionamento di default
 */
public class ToggleBtn extends JToggleButton {
	private static final long serialVersionUID = 1L;
	String                    s;
	ImageIcon                 i;

	MyIcon                    icona;
	private JPanel            padre;

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
		ImageIcon nuova = i;
		ImageIcon sostituta = new ImageIcon();
		this.setIcon(sostituta);
		super.paintComponent(g);
		this.setIcon(nuova);
		i.paintIcon(this, g, 5, getHeight() / 2 - i.getIconHeight()
		                / 2);
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
		if (s != null && i != null) {
			g.drawString(s, (i.getIconWidth()) + 4, (h + g.getFontMetrics()
			                .getAscent()) / 2 - 1);
		}
	}

	public void settaggioBottoneStandard() {
		this.setRolloverEnabled(true);
		this.setBorder(null);
		this.setBackground(Color.WHITE);
		this.setHorizontalAlignment(SwingConstants.LEFT); // allinea il
		                                                  // contenuto del
		                                                  // bottone a sinistra
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
	 * Estende la classe Icon per settarla come Icon di default al rollover di
	 * ToggleBtn
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