package com.molinari.gestionespese.view.componenti.componentipannello;

import java.awt.Container;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.molinari.utility.graphic.component.container.PannelloBase;

public class CostruttoreSottoPannello {

	int                       distanzaDalBordoY      = 20;
	int                       distanzaDalBordoX      = 20;

	private static final int         DIST_DA_COMP_Y = 0;

	private static final int         WIDTH_COMP     = 116;
	private static final int         HEIGHT_COMP       = 30;
	private int               indiceX                = distanzaDalBordoX;
	private int               indiceY                = distanzaDalBordoY;
	private PannelloBase pannello;
	private JComponent[] compontents;
	private JLabel[] labels;
	public static final int   HORIZONTAL             = 0;
	public static final int   VERTICAL               = 1;

	/**
	 * Create the panel.
	 */
	public CostruttoreSottoPannello(PannelloBase pannello, final JComponent[] componenti, final JLabel[] labels) {
		super();
		this.compontents = componenti;
		this.labels = labels;
				
		this.pannello = pannello;
		this.pannello.setBounds(40, 140, 200, 200);
	}

	

	public void initGUI(final JComponent[] componenti, final JLabel[] labels) {
		pannello.setLayout(null);
		initComponentsVerticale(componenti, labels);

	}


	private void initComponentsVerticale(JComponent[] componenti, JLabel[] labels) {
		indiceX = distanzaDalBordoX;
		for (int i = 0; i < componenti.length; i++) {
			final JLabel label = labels[i];
			label.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
			indiceY += DIST_DA_COMP_Y + label.getHeight();
			pannello.add(label);

			final JComponent component = componenti[i];
			component.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
			indiceY += DIST_DA_COMP_Y + component.getHeight();
			pannello.add(component);

		}
	}

	public int getMaxWidth(final JComponent[] componenti) {
		int maxWidth = 0;
		for (final JComponent component : componenti) {
			if (component.getWidth() > maxWidth) {
				maxWidth = component.getWidth();
			}
		}
		return maxWidth;
	}

	public int getMaxWidth(final List<JComponent> componenti) {
		int maxWidth = 0;
		for (final JComponent component : componenti) {
			if (component.getWidth() > maxWidth) {
				maxWidth = component.getWidth();
			}
		}
		return maxWidth;
	}

	public int getMaxHeight(final List<JComponent> componenti) {
		int maxHeight = 0;
		for (final JComponent component : componenti) {
			if (component.getHeight() > maxHeight) {
				maxHeight = component.getHeight();
			}
		}
		return maxHeight;
	}

	public int getMaxHeight(final JComponent[] componenti) {
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

	public PannelloBase getPannello() {
		return pannello;
	}

	public void setPannello(PannelloBase pannello) {
		this.pannello = pannello;
	}



	public JComponent[] getCompontents() {
		return compontents;
	}



	public void setCompontents(JComponent[] compontents) {
		this.compontents = compontents;
	}



	public JLabel[] getLabels() {
		return labels;
	}



	public void setLabels(JLabel[] labels) {
		this.labels = labels;
	}
}
