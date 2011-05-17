package view.note;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import view.font.ButtonF;
import business.cache.CacheNote;
import domain.Note;
import domain.wrapper.WrapNote;

public class MostraNoteWiew extends JFrame {

	private static final long serialVersionUID = 1L;
	private JScrollPane       scrollPane;
	private final JPanel      pannello;

	public MostraNoteWiew() {
		ArrayList<Note> note = CacheNote.getSingleton().getAllNoteForUtenteEAnno();

		setResizable(true);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();

		pannello = new JPanel();
		// pannello.setLayout(new GridLayout(note.size(), 1, 0, 0));
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(pannello);
		pannello.setLayout(null);
		scrollPane.setBounds(10, 40, 258, 384);

		this.setTitle("Note");

		final NoteView dialog = new NoteView(new WrapNote(), this);

		JButton inserisci = new ButtonF();
		inserisci.setText("+");
		getContentPane().add(inserisci);

		inserisci.setBounds(95, 0, 90, 30);
		inserisci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(0, 0, 347, 318);
				dialog.setVisible(true);

			}
		});
		for (int i = 0; i < note.size(); i++) {
			Note note2 = note.get(i);
			PannelloNota pNota = new PannelloNota(note2);
			pannello.add(pNota);
			pannello.setPreferredSize(new Dimension(300, 170 * note.size()));
			if (i == 0) {
				pNota.setBounds(0, 0, 260, 170);
			} else {
				pNota.setBounds(0, i * 170, 260, 200);
			}
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MostraNoteWiew fe = new MostraNoteWiew();
				fe.setVisible(true);
				fe.setSize(280, 500);
			}
		});
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public void aggiornaVista() {
		this.invalidate();
		this.repaint();
		ArrayList<Note> note = CacheNote.getSingleton().getAllNoteForUtenteEAnno();
		for (int i = 0; i < note.size(); i++) {
			Note note2 = note.get(i);
			PannelloNota pNota = new PannelloNota(note2);
			pannello.add(pNota);
			pannello.setPreferredSize(new Dimension(300, 170 * note.size()));
			if (i == 0) {
				pNota.setBounds(0, 0, 260, 170);
			} else {
				pNota.setBounds(0, i * 170, 260, 200);
			}
		}
		this.invalidate();
		this.repaint();
	}
}
