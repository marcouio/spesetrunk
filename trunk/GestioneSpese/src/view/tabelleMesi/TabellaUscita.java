

package view.tabelleMesi;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import view.OggettoVistaBase;
import view.font.TableF;
import business.generatori.GeneratoreDatiTabellaUscite;

public class TabellaUscita extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;

	private static String[][] primo;
	private static JScrollPane scrollPane;



	public TabellaUscita() {
		super(new GridLayout(1,0));

		final GeneratoreDatiTabellaUscite dati = new GeneratoreDatiTabellaUscite();
		final TableF table = GeneratoreDatiTabellaUscite.createTable(dati.getMatrice(), dati.getNomiColonna());

		//Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);

		//Add the scroll pane to this panel.
		add(scrollPane);

	}

	private static void createAndShowGUI() throws Exception {
		//Create and set up the window.
		final JFrame frame = new JFrame("TabellaUscita");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		final TabellaUscita newContentPane = new TabellaUscita();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	public static String[][] getPrimo() {
		return primo;
	}

	public static void setPrimo(final String[][] primo) {
		TabellaUscita.primo = primo;
	}


	public static void main(final String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					createAndShowGUI();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public static JScrollPane getScrollPane() {
		return scrollPane;
	}


	public void setScrollPane(final JScrollPane scrollPane) {
		TabellaUscita.scrollPane = scrollPane;
	}
}