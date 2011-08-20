package view.note;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import view.font.ButtonF;
import business.cache.CacheNote;
import domain.Note;

public class MostraNoteView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private final JPanel pannello;

	public MostraNoteView() {
		final ArrayList<Note> note = CacheNote.getSingleton().getAllNoteForUtenteEAnno();
		this.setSize(250, 425);
		setResizable(true);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);

		pannello = new JPanel();
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(pannello);
		pannello.setLayout(null);
		scrollPane.setBounds(10, 40, 230, 350);

		this.setTitle("Note");

		final JButton inserisci = new ButtonF();
		inserisci.setText("+");
		getContentPane().add(inserisci);

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

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				final MostraNoteView fe = new MostraNoteView();
				fe.setVisible(true);
				fe.setSize(280, 500);
			}
		});
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
			System.out.println(nota.getnome());
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
		this.validate();
		this.repaint();
	}
}
