package com.molinari.gestionespese.view.componenti.componentipannello;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CostruttoreSottoPannello extends JPanel {

	private static final long serialVersionUID       = 1L;
	int                       distanzaDalBordoY      = 20;
	int                       distanzaDalBordoX      = 20;

	private final int         distanzaDaiComponentiY = 0;
	private final int         distanzaDaiComponentiX = 20;

	private final int         larghezzaComponent     = 116;
	private final int         altezzaComponent       = 30;
	private int               indiceX                = distanzaDalBordoX;
	private int               indiceY                = distanzaDalBordoY;
	private JComponent[]      componenti;
	public static final int   HORIZONTAL             = 0;
	public static final int   VERTICAL               = 1;

	public static void main(String[] args) {
		final JFrame f = new JFrame();
		final JComponent[] componenti = new JComponent[] { new JTextField("Ciao"), new JComboBox(), new JTextField("ccd") };
		final JLabel[] labels = new JLabel[] { new JLabel("ciao"), new JLabel("ciao2"), new JLabel("cdscs") };
		final CostruttoreSottoPannello pan = new CostruttoreSottoPannello(componenti, labels, 1);
		f.getContentPane().add(pan);
		f.setVisible(true);
		f.setBounds(0, 0, (pan.getMaxWidth(componenti) + pan.distanzaDalBordoX * 2) * 3, (pan.getMaxHeight(componenti) + pan.distanzaDalBordoY * 2) * 2);
		f.setSize(500, 500);
	}

	/**
	 * Create the panel.
	 */
	public CostruttoreSottoPannello(final JComponent[] componenti, final JLabel[] labels, int orientation) {
		super();
		this.componenti = componenti;
		if (orientation == VERTICAL) {
			initGUI(componenti, labels);
			// TODO dimensione pannello
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

	public CostruttoreSottoPannello(ArrayList<JComponent> componenti, ArrayList<JLabel> labels, int orientation) {
		this.setLayout(null);
		if (orientation == HORIZONTAL) {
			initLabelOrizzontale(labels);
			initComponentsOrizzontale(componenti);
			this.setSize(getMaxWidth(componenti) + distanzaDalBordoX * 2, getMaxHeight(componenti) + distanzaDalBordoY * 2);
		} else {
			// TODO
			// initGUI(componenti, labels);
			// this.setPreferredSize(new Dimension((this.getMaxWidth(componenti)
			// + this.distanzaDalBordoX * 2) * componenti.length,
			// (this.getMaxHeight(componenti) + this.distanzaDalBordoY * 2) *
			// 2));
		}
	}

	public CostruttoreSottoPannello() {
	}

	private void initGUI(final JComponent[] componenti, final JLabel[] labels) {
		this.setLayout(null);
		initComponentsVerticale(componenti, labels);
		this.setSize(getMaxWidth(componenti) + distanzaDalBordoX * 2, getMaxHeight(componenti) + distanzaDalBordoY * 2);

	}

	private void initLabelOrizzontale(final JLabel[] labels) {
		for (final JLabel label : labels) {
			label.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
			indiceX += distanzaDaiComponentiX + label.getWidth();
			this.add(label);
		}
	}

	private void initLabelOrizzontale(final ArrayList<JLabel> labels) {
		for (final JLabel label : labels) {
			label.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
			indiceX += distanzaDaiComponentiX + label.getWidth();
			this.add(label);
		}
	}

	private void initComponentsOrizzontale(JComponent[] componenti) {
		indiceX = distanzaDalBordoX;
		for (final JComponent component : componenti) {
			component.setBounds(indiceX, indiceY + getAltezzaComponent() + distanzaDaiComponentiY, getLarghezzaComponent(), getAltezzaComponent());
			indiceX += distanzaDaiComponentiX + component.getWidth();
			this.add(component);

		}
	}

	private void initComponentsOrizzontale(ArrayList<JComponent> componenti) {
		indiceX = distanzaDalBordoX;
		for (final JComponent component : componenti) {
			component.setBounds(indiceX, indiceY + distanzaDaiComponentiY + getAltezzaComponent(), getLarghezzaComponent(), getAltezzaComponent());
			indiceX += distanzaDaiComponentiX + component.getWidth();
			this.add(component);

		}
	}

	private void initComponentsVerticale(JComponent[] componenti, JLabel[] labels) {
		indiceX = distanzaDalBordoX;
		for (int i = 0; i < componenti.length; i++) {
			final JLabel label = labels[i];
			label.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
			indiceY += distanzaDaiComponentiY + label.getHeight();
			this.add(label);

			final JComponent component = componenti[i];
			component.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
			indiceY += distanzaDaiComponentiY + component.getHeight();
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

	int getMaxWidth(final ArrayList<JComponent> componenti) {
		int maxWidth = 0;
		for (final JComponent component : componenti) {
			if (component.getWidth() > maxWidth) {
				maxWidth = component.getWidth();
			}
		}
		return maxWidth;
	}

	private int getMaxHeight(final ArrayList<JComponent> componenti) {
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
		return larghezzaComponent;
	}

	public int getAltezzaComponent() {
		return altezzaComponent;
	}
}
