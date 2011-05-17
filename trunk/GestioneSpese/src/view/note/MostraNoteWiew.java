package view.note;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import business.cache.CacheNote;
import domain.Note;

public class MostraNoteWiew extends JFrame {

	private static final long serialVersionUID = 1L;
	private JScrollPane       scrollPane;

	public MostraNoteWiew() {
		setResizable(false);
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		this.scrollPane = new JScrollPane();
		scrollPane.setLayout(new GridLayout(0, 1));
		getContentPane().add(scrollPane);
		this.setTitle("Note");
		ArrayList<Note> note = CacheNote.getSingleton().getAllNoteForUtenteEAnno();
		for (Note note2 : note) {
			scrollPane.add(new PannelloNota(note2));
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame f = new JFrame();
				MostraNoteWiew fe = new MostraNoteWiew();
				f.getContentPane().add(fe);
				f.setVisible(true);
				f.setSize(280, 500);
			}
		});
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
}
