package view.bottoni;

import java.awt.BorderLayout;

import javax.swing.AbstractButton;
import javax.swing.JPanel;

public class Bottone extends JPanel {

	private static final long serialVersionUID = 1L;
	private AbstractButton    bottone;
	private PannelloBottoni   contenuto;
	private boolean           isEspanso;

	public static final int   RIEMPITO         = 0;

	public Bottone() {
		init();
	}

	public Bottone(AbstractButton bottone) {
		init();
		this.bottone = bottone;
		this.add(bottone, BorderLayout.NORTH);
	}

	public Bottone(PannelloBottoni contenuto) {
		init();
		this.contenuto = contenuto;
		this.add(this.contenuto, BorderLayout.SOUTH);
	}

	public Bottone(PannelloBottoni contenuto, AbstractButton bottone) {
		init();
		this.contenuto = contenuto;
		this.bottone = bottone;
		this.add(this.bottone, BorderLayout.NORTH);
		this.add(this.contenuto, BorderLayout.SOUTH);

	}

	private void init() {
		this.setLayout(new BorderLayout());
	}

	public void espandi() {
		setEspanso(true);
		if (contenuto != null) {
			contenuto.setVisible(true);
		}
		revalidate();
		repaint();
	}

	public void contrai() {
		setEspanso(false);
		if (contenuto != null) {
			contenuto.setVisible(false);
		}
	}

	public AbstractButton getBottone() {
		return bottone;
	}

	protected void setBottone(AbstractButton bottone) {
		this.bottone = bottone;
	}

	public PannelloBottoni getContenuto() {
		return contenuto;
	}

	public void setContenuto(PannelloBottoni contenuto) {
		this.contenuto = contenuto;
		add(this.contenuto);
		this.contenuto.setVisible(false);
	}

	public void setEspanso(boolean isEspanso) {
		this.isEspanso = isEspanso;
	}

	public boolean isEspanso() {
		return isEspanso;
	}

}
