package view.bottoni;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Main inst = null;
				try {
					inst = new Main();
					inst.setLayout(new GridLayout(1, 0));
					inst.createPannelloBottoni();
					// inst.getContentPane().add(new PannelloAScomparsa(inst));

				} catch (Throwable e) {
					e.printStackTrace();
				}
				inst.setBounds(0, 0, 1000, 700);
				// inst.setPreferredSize(new Dimension(100,70));
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});

	}

	private void createPannelloBottoni() {
		PannelloBottoni pannelloBottoni = new PannelloBottoni();
		final Container contentPane = this.getContentPane();
		ImageIcon icona = new ImageIcon("/home/kiwi/Immagini/prova.png");
		// ImageIcon icona = new
		// ImageIcon("C:\\Documents and Settings\\marco.molinari\\Documenti\\Immagini\\16 e 34\\nodo_fork_icona_roll.png");
		final ToggleBtn bottoni3 = new ToggleBtn("Movimenti", icona);
		bottoni3.settaggioBottoneStandard();
		final Bottone b3 = new Bottone(bottoni3);
		bottoni3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// !!!!ATTENZIONE!!! !!!IMPORTANTISSIMO!!!

				// contentPane.setLayout(new GridLayout(2, 1));
				// b3.setLayout(new GridLayout(1, 1));
				b3.revalidate();
				b3.repaint();
			}

		});

		PannelloBottoni pp = new PannelloBottoni();
		pp.addBottone(new Bottone(new JButton("Ciaocoac")));
		pp.setLayout(new GridLayout(1, 1));
		b3.setContenuto(pp);
		bottoni3.setPadre(b3);

		ToggleBtn bottoni2 = new ToggleBtn("Mesi", icona);
		bottoni2.settaggioBottoneStandard();
		Bottone b2 = new Bottone(bottoni2);
		bottoni2.setPadre(b2);
		ToggleBtn bottoni = new ToggleBtn("ConsolleSQL", icona);
		bottoni.settaggioBottoneStandard();
		Bottone b = new Bottone(bottoni);
		bottoni.setPadre(b);

		pannelloBottoni.addBottone(b);
		pannelloBottoni.addBottone(b2);
		pannelloBottoni.addBottone(b3);
		b3.setBorder(new EmptyBorder(2, 2, 2, 2));
		b.setBorder(new EmptyBorder(2, 2, 2, 2));
		b2.setBorder(new EmptyBorder(2, 2, 2, 2));
		contentPane.add(pannelloBottoni);
		pannelloBottoni.setBounds(0, 20, this.getWidth(), 90);
	}
}
