package com.molinari.gestionespese.view.componenti.componentipannello;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CostruttoreSottoPannello extends JPanel {

	private static final long serialVersionUID       = 1L;
	int                       distanzaDalBordoY      = 20;
	int                       distanzaDalBordoX      = 20;

	private static final int         DIST_DA_COMP_Y = 0;
	private static final int         DIST_DA_COMP_X = 20;

	private static final int         WIDTH_COMP     = 116;
	private static final int         HEIGHT_COMP       = 30;
	private int               indiceX                = distanzaDalBordoX;
	private int               indiceY                = distanzaDalBordoY;
	private JComponent[]      componenti;
	public static final int   HORIZONTAL             = 0;
	public static final int   VERTICAL               = 1;

	public CostruttoreSottoPannello(List<JComponent> componenti, List<JLabel> labels, int orientation) {
		this.setLayout(null);
		if (orientation == HORIZONTAL) {
			initLabelOrizzontale(labels);
			initComponentsOrizzontale(componenti);
			this.setSize(getMaxWidth(componenti) + distanzaDalBordoX * 2, getMaxHeight(componenti) + distanzaDalBordoY * 2);
		} else {
			//devo farlo...
		}
	}

	public CostruttoreSottoPannello() {
		//do nothing
	}

	/**
	 * Create the panel.
	 */
	public CostruttoreSottoPannello(final JComponent[] componenti, final JLabel[] labels, int orientation) {
		super();
		this.componenti = componenti;
		if (orientation == VERTICAL) {
			initGUI(componenti, labels);
			this.setPreferredSize(new Dimension(this.getMaxWidth(componenti) + this.distanzaDalBordoX * 2, (this.getMaxHeight(componenti) + this.distanzaDalBordoY * 2) * componenti.length));
		} else {
			initLabelOrizzontale(labels);
			initComponentsOrizzontale(componenti);
			this.setPreferredSize(new Dimension((this.getMaxWidth(componenti) + this.distanzaDalBordoX * 2) * componenti.length, (this.getMaxHeight(componenti) + this.distanzaDalBordoY * 2) * 2));
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension((this.getMaxWidth(componenti) + this.distanzaDalBordoX * 2) * componenti.length, (this.getMaxHeight(componenti) + this.distanzaDalBordoY * 2) * 3);
	}

	private void initGUI(final JComponent[] componenti, final JLabel[] labels) {
		this.setLayout(null);
		initComponentsVerticale(componenti, labels);
		this.setSize(getMaxWidth(componenti) + distanzaDalBordoX * 2, getMaxHeight(componenti) + distanzaDalBordoY * 2);

	}

	private void initLabelOrizzontale(final JLabel[] labels) {
		for (final JLabel label : labels) {
			label.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
			indiceX += DIST_DA_COMP_X + label.getWidth();
			this.add(label);
		}
	}

	private void initLabelOrizzontale(final List<JLabel> labels) {
		for (final JLabel label : labels) {
			label.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
			indiceX += DIST_DA_COMP_X + label.getWidth();
			this.add(label);
		}
	}

	private void initComponentsOrizzontale(JComponent[] componenti) {
		indiceX = distanzaDalBordoX;
		for (final JComponent component : componenti) {
			component.setBounds(indiceX, indiceY + getAltezzaComponent() + DIST_DA_COMP_Y, getLarghezzaComponent(), getAltezzaComponent());
			indiceX += DIST_DA_COMP_X + component.getWidth();
			this.add(component);

		}
	}

	private void initComponentsOrizzontale(List<JComponent> componenti) {
		indiceX = distanzaDalBordoX;
		for (final JComponent component : componenti) {
			component.setBounds(indiceX, indiceY + DIST_DA_COMP_Y + getAltezzaComponent(), getLarghezzaComponent(), getAltezzaComponent());
			indiceX += DIST_DA_COMP_X + component.getWidth();
			this.add(component);

		}
	}

	private void initComponentsVerticale(JComponent[] componenti, JLabel[] labels) {
		indiceX = distanzaDalBordoX;
		for (int i = 0; i < componenti.length; i++) {
			final JLabel label = labels[i];
			label.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
			indiceY += DIST_DA_COMP_Y + label.getHeight();
			this.add(label);

			final JComponent component = componenti[i];
			component.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
			indiceY += DIST_DA_COMP_Y + component.getHeight();
			this.add(component);

		}
	}

	private int getMaxWidth(final JComponent[] componenti) {
		int maxWidth = 0;
		for (final JComponent component : componenti) {
			if (component.getWidth() > maxWidth) {
				maxWidth = component.getWidth();
			}
		}
		return maxWidth;
	}

	int getMaxWidth(final List<JComponent> componenti) {
		int maxWidth = 0;
		for (final JComponent component : componenti) {
			if (component.getWidth() > maxWidth) {
				maxWidth = component.getWidth();
			}
		}
		return maxWidth;
	}

	private int getMaxHeight(final List<JComponent> componenti) {
		int maxHeight = 0;
		for (final JComponent component : componenti) {
			if (component.getHeight() > maxHeight) {
				maxHeight = component.getHeight();
			}
		}
		return maxHeight;
	}

	private int getMaxHeight(final JComponent[] componenti) {
		int maxHeight = 0;
		for (final JComponent component : componenti) {
			if (component.getHeight() > maxHeight) {
				maxHeight = component.getHeight();
			}
		}
		return maxHeight;
	}

	protected int getDistanzaDalBordoY() {
		return distanzaDalBordoY;
	}

	protected void setDistanzaDalBordoY(int distanzaDalBordoY) {
		this.distanzaDalBordoY = distanzaDalBordoY;
	}

	protected int getDistanzaDalBordoX() {
		return distanzaDalBordoX;
	}

	protected void setDistanzaDalBordoX(int distanzaDalBordoX) {
		this.distanzaDalBordoX = distanzaDalBordoX;
	}

	public int getLarghezzaComponent() {
		return WIDTH_COMP;
	}

	public int getAltezzaComponent() {
		return HEIGHT_COMP;
	}
}
