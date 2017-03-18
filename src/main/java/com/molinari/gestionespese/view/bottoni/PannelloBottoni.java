package com.molinari.gestionespese.view.bottoni;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;

import com.molinari.utility.graphic.component.button.ToggleBtn;
import com.molinari.utility.graphic.component.container.PannelloBase;

public class PannelloBottoni extends PannelloBase implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static final int MODE_PIENO = 0;

	private static final int ALTEZZA_BOTTONE = 50;

	protected final ArrayList<Bottone> listaBottoni = new ArrayList<>();
	protected final ButtonGroup gruppoBottoni = new ButtonGroup();


	/**
	 * Create the panel.
	 */
	public PannelloBottoni(Container contenitore) {
		super(contenitore);
		init();
	}

	public PannelloBottoni(Container contenitore, final List<Bottone> bottoni) {
		super(contenitore);
		init();

		for (final Bottone toggleBtn : bottoni) {
			listaBottoni.add(toggleBtn);
			gruppoBottoni.add(toggleBtn.getBottone());
			this.addBottone(toggleBtn);
		}

	}

	protected void init() {
		this.setLayout(new GridLayout(1, 4));
	}

	public void addBottone(final Bottone bottone) {
		this.add(bottone);
		this.gruppoBottoni.add(bottone.getBottone());
		this.listaBottoni.add(bottone);
		if (bottone.getBottone() != null) {
			bottone.getBottone().setPreferredSize(new Dimension(getContenitorePadre().getWidth(), ALTEZZA_BOTTONE));
			bottone.getBottone().addActionListener(this);
		}
	}

	public void deselezionaBottoni() {
		this.gruppoBottoni.clearSelection();
		for (final Bottone button : listaBottoni) {
			button.contrai();
		}
	}

	protected ArrayList<Bottone> getListaBottoni() {
		return listaBottoni;
	}

	public ButtonGroup getGruppoBottoni() {
		return gruppoBottoni;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Bottone b = (Bottone) ((ToggleBtn) e.getSource()).getPadre();
		if (b != null) {
			if (b.isEspanso()) {
				deselezionaBottoni();
				b.contrai();
				((ToggleBtn) e.getSource()).setSelected(false);
			} else {
				deselezionaBottoni();
				((ToggleBtn) e.getSource()).setSelected(true);
				b.espandi();
			}
		}
	}

}
