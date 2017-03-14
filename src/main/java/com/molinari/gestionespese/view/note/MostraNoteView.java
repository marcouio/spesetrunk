package com.molinari.gestionespese.view.note;

import java.awt.Container;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.view.font.ButtonF;

import grafica.componenti.contenitori.PannelloBase;

public class MostraNoteView implements Finestra {

	private Container container;
	private JScrollPane scrollPane;
	private final JPanel pannello;

	public MostraNoteView(Container cont) {
		container = new PannelloBase(cont);
		final List<INote> note = CacheNote.getSingleton().getAllNoteForUtenteEAnno();
		PannelloBase padre = (PannelloBase) ((PannelloBase)getContainer()).getContenitorePadre();
		getContainer().setLayout(null);
		getContainer().setSize(padre.getWidth(), padre.getHeight());

		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);

		pannello = new JPanel();
		getContainer().add(scrollPane);
		scrollPane.setViewportView(pannello);
		pannello.setLayout(null);
		scrollPane.setBounds(10, 40, padre.getWidth(), padre.getHeight());

		final JButton inserisci = new ButtonF();
		inserisci.setText("+");
		getContainer().add(inserisci);

		inserisci.setBounds(82, 0, 90, 30);
		inserisci.addActionListener(new AscoltatoreApriPannelloInserisciNota(this));
		for (int i = 0; i < note.size(); i++) {
			final INote nota = note.get(i);
			final PannelloNota pNota = new PannelloNota(nota, this);
			pannello.add(pNota);
			pannello.setPreferredSize(new Dimension(220, 180 * note.size()));
			if (i == 0) {
				pNota.setBounds(0, 0, 220, 170);
			} else {
				pNota.setBounds(0, i * 170, 220, 170);
			}
		}

	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(final JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public void aggiornaVista() {
		final List<INote> note = CacheNote.getSingleton().getAllNoteForUtenteEAnno();
		pannello.removeAll();
		for (int i = 0; i < note.size(); i++) {
			final INote nota = note.get(i);
			final PannelloNota pNota = new PannelloNota(nota, this);
			pannello.add(pNota);
			pannello.setPreferredSize(new Dimension(220, 180 * note.size()));
			if (i == 0) {
				pNota.setBounds(0, 0, 220, 170);
			} else {
				pNota.setBounds(0, i * 170, 220, 170);
			}
		}
		final JScrollPane pane = scrollPane;
		pane.setViewportView(pannello);
		setScrollPane(pane);
		this.getContainer().validate();
		this.getContainer().repaint();
	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}
	
}
