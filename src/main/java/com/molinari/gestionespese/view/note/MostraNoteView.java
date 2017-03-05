package com.molinari.gestionespese.view.note;

import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.view.font.ButtonF;

import grafica.componenti.contenitori.FrameBase;
import grafica.componenti.contenitori.PannelloBase;

public class MostraNoteView implements Finestra {

	private Container container;
	private JScrollPane scrollPane;
	private final JPanel pannello;

	public MostraNoteView(Container cont) {
		container = new PannelloBase(cont);
		final ArrayList<Note> note = CacheNote.getSingleton().getAllNoteForUtenteEAnno();
		getContainer().setSize(250, 425);
		getContainer().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);

		pannello = new JPanel();
		getContainer().add(scrollPane);
		scrollPane.setViewportView(pannello);
		pannello.setLayout(null);
		scrollPane.setBounds(10, 40, 230, 350);

		final JButton inserisci = new ButtonF();
		inserisci.setText("+");
		getContainer().add(inserisci);

		inserisci.setBounds(82, 0, 90, 30);
		inserisci.addActionListener(new AscoltatoreApriPannelloInserisciNota(this));
		for (int i = 0; i < note.size(); i++) {
			final Note nota = note.get(i);
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
		final ArrayList<Note> note = CacheNote.getSingleton().getAllNoteForUtenteEAnno();
		pannello.removeAll();
		for (int i = 0; i < note.size(); i++) {
			final Note nota = note.get(i);
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

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}
}
