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
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * La classe estende JToggleButton per modificarne il funzionamento di default
 */
public class ToggleBtn extends JToggleButton {
	private static final long serialVersionUID    = 1L;
	String                    s;
	ImageIcon                 i;

	MyIcon                    icona;
	private JPanel            padre;
	private int               distanzaBordoImageX = 10;
	private int               xPartenzaTesto      = 38;

	Color                     colorForeground;
	Color                     colorBackground;

	public MyIcon getMyIcon() {
		return icona != null ? icona : new MyIcon();
	}

	public ToggleBtn(final String text, final ImageIcon icon, final int xDistanzaBordoImmagine, int xPartenzaTesto) {
		this(text, icon);
		this.distanzaBordoImageX = xDistanzaBordoImmagine != -1 ? xDistanzaBordoImmagine : distanzaBordoImageX;
		this.xPartenzaTesto = xPartenzaTesto != -1 ? xPartenzaTesto : this.xPartenzaTesto;
	}

	public ToggleBtn(final String text) {
		super("");
		s = text;
	}

	public ToggleBtn(final String text, final ImageIcon icon, JPanel padre) {
		this(text, icon);
		this.setPadre(padre);
	}

	public ToggleBtn(final String text, final ImageIcon icon) {
		super("", icon);
		s = text;
		i = icon;
	}

	public ToggleBtn(final ImageIcon icon) {
		super(icon);
		i = icon;
	}

	@Override
	public void paintComponent(final Graphics g) {
		// setto l'icona a null per non farla disegnare dal super.paintcomponent
		this.setIcon(new ImageIcon());

		// sono costretto a fare il super del paintComponent per via del
		// rollover che altrimenti dovrei gestire io
		super.paintComponent(g);

		// invece di far disegnare il testo al paintcomponent ho settato il text
		// a "" e il testo passato nel costruttore lo disegno qui così ho un
		// maggiore controllo
		g.drawString(s != null ? s : "", getLarghezzaImmagine(i) + distanzaBordoImageX + calcolaTextGap(i), (getHeight() + g.getFontMetrics().getAscent()) / 2 - 1);

		// reimposto l'icona a quella passata
		this.setIcon(i);
		getIcon().paintIcon(this, g, distanzaBordoImageX, getHeight() / 2 - i.getIconHeight() / 2);
		if (this.isSelected()) {
			// effetto pulsante premuto ridefinito
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
		g.drawString(s != null ? s : "", getLarghezzaImmagine(i) + distanzaBordoImageX + calcolaTextGap(i), (h + g.getFontMetrics().getAscent()) / 2 - 1);
	}

	private int getLarghezzaImmagine(Icon i) {
		if (i != null) {
			return i.getIconWidth();
		} else {
			return 0;
		}

	}

	private int calcolaTextGap(Icon i) {
		if (i != null) {
			if (xPartenzaTesto > (i.getIconWidth() - distanzaBordoImageX)) {
				return xPartenzaTesto - (i.getIconWidth() - distanzaBordoImageX + 7);
			} else {
				return 0;
			}
		} else {
			return xPartenzaTesto;
		}
	}

	public void settaggioBottoneStandard() {

		Border bordo = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.LIGHT_GRAY);
		// Border bordo = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
		// Color.RED, Color.GREEN);
		this.setBorder(bordo);
		this.setRolloverEnabled(true);
		this.setBackground(Color.WHITE);
		this.setHorizontalAlignment(SwingConstants.LEFT); // allinea il
		                                                  // contenuto a sinitra
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