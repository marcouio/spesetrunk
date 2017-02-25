package com.molinari.gestionespese.view.bottoni;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

public class PannelloBottoniInterno extends PannelloBottoni {

	public PannelloBottoniInterno(Container contenitore) {
		super(contenitore);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void init() {
		BorderLayout lay = new BorderLayout(0, 0);
		this.setLayout(lay);
	}

	@Override
	public void addBottone(Bottone bottone) {
		if (listaBottoni.size() == 0) {
			this.add(bottone, BorderLayout.NORTH);
		} else {
			this.add(bottone, BorderLayout.CENTER);
		}
		this.gruppoBottoni.add(bottone.getBottone());
		this.listaBottoni.add(bottone);
		if (bottone.getBottone() != null) {
			bottone.getBottone().addActionListener(this);
		}
	}

	public void addDueBottoni(ArrayList<Bottone> dueBottoni) {
		Bottone bottone = dueBottoni.get(0);
		this.add(bottone, BorderLayout.NORTH);
		this.gruppoBottoni.add(bottone.getBottone());
		this.listaBottoni.add(bottone);
		if (bottone.getBottone() != null) {
			bottone.getBottone().addActionListener(this);
		}
		bottone.getBottone().setPreferredSize(new Dimension(getWidth(), 22));

		Bottone bottone2 = dueBottoni.get(1);
		this.add(bottone2, BorderLayout.CENTER);
		this.gruppoBottoni.add(bottone2.getBottone());
		this.listaBottoni.add(bottone2);
		if (bottone2.getBottone() != null) {
			bottone2.getBottone().addActionListener(this);
		}
		bottone2.getBottone().setPreferredSize(new Dimension(getWidth(), 22));
	}
}
