package view.bottoni;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class PannelloBottoni extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static final int MODE_PIENO = 0;

	private static final int ALTEZZA_BOTTONE = 50;

	protected final ArrayList<Bottone> listaBottoni = new ArrayList<Bottone>();
	protected final ButtonGroup gruppoBottoni = new ButtonGroup();

	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame inst = new JFrame();
				inst.setBounds(0, 0, 1000, 750);
				inst.add(new PannelloBottoni(MODE_PIENO));
				inst.setTitle("PannelloBottoni");
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			}
		});
	}

	/**
	 * Create the panel.
	 */
	public PannelloBottoni() {
		init();
	}

	public PannelloBottoni(final ArrayList<Bottone> bottoni) {

		init();

		for (final Bottone toggleBtn : bottoni) {
			listaBottoni.add(toggleBtn);
			gruppoBottoni.add(toggleBtn.getBottone());
			this.addBottone(toggleBtn);
		}

	}

	public PannelloBottoni(final int mode) {
		init();

		final ToggleBtn bottoni1 = new ToggleBtn("Primo", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		final ToggleBtn bottoni2 = new ToggleBtn("Secondo", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		final ToggleBtn bottoni3 = new ToggleBtn("Terzo", new ImageIcon("/home/kiwi/Immagini/prova.png"));

		bottoni1.settaggioBottoneStandard();
		bottoni2.settaggioBottoneStandard();
		bottoni3.settaggioBottoneStandard();

		final Bottone b1 = new Bottone(bottoni1);
		final Bottone b2 = new Bottone(bottoni2);
		final Bottone b3 = new Bottone(bottoni3);

		this.addBottone(b3);
		this.addBottone(b2);
		this.addBottone(b1);

		final PannelloBottoni pp = new PannelloBottoni();
		pp.add(new JButton("ciaociao"));
		b3.setContenuto(pp);

	}

	protected void init() {
		this.setLayout(new GridLayout(1, 4));
	}

	public void addBottone(final Bottone bottone) {
		this.add(bottone);
		this.gruppoBottoni.add(bottone.getBottone());
		this.listaBottoni.add(bottone);
		if (bottone.getBottone() != null) {
			bottone.getBottone().setPreferredSize(new Dimension(getWidth(), ALTEZZA_BOTTONE));
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
		final Bottone b = ((Bottone) ((ToggleBtn) e.getSource()).getPadre());
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
