package view.bottoni;

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

public class PannelloBottoni extends JPanel implements ActionListener {

	private static final long        serialVersionUID = 1L;
	public static final int          MODE_PIENO       = 0;

	private final ArrayList<Bottone> listaBottoni     = new ArrayList<Bottone>();
	private final ButtonGroup        gruppoBottoni    = new ButtonGroup();

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame inst = new JFrame();
				inst.setBounds(0, 0, 1000, 750);
				inst.add(new PannelloBottoni(MODE_PIENO));
				inst.setTitle("PannelloBottoni");
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
	}

	/**
	 * Create the panel.
	 */
	public PannelloBottoni() {
		init();
	}

	public PannelloBottoni(ArrayList<Bottone> bottoni) {

		init();

		for (Bottone toggleBtn : bottoni) {
			listaBottoni.add(toggleBtn);
			gruppoBottoni.add(toggleBtn.getBottone());
			this.addBottone(toggleBtn);
		}

	}

	public PannelloBottoni(int mode) {
		init();

		ToggleBtn bottoni1 = new ToggleBtn("Primo", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		ToggleBtn bottoni2 = new ToggleBtn("Secondo", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		ToggleBtn bottoni3 = new ToggleBtn("Terzo", new ImageIcon("/home/kiwi/Immagini/prova.png"));

		bottoni1.settaggioBottoneStandard();
		bottoni2.settaggioBottoneStandard();
		bottoni3.settaggioBottoneStandard();

		Bottone b1 = new Bottone(bottoni1);
		Bottone b2 = new Bottone(bottoni2);
		Bottone b3 = new Bottone(bottoni3);

		this.addBottone(b3);
		this.addBottone(b2);
		this.addBottone(b1);

		PannelloBottoni pp = new PannelloBottoni();
		pp.add(new JButton("ciaociao"));
		b3.setContenuto(pp);

	}

	private void init() {
		this.setLayout(new GridLayout(1, 0));
	}

	public void addBottone(Bottone bottone) {
		this.add(bottone);
		this.gruppoBottoni.add(bottone.getBottone());
		this.listaBottoni.add(bottone);
		if (bottone.getBottone() != null) {
			bottone.getBottone().addActionListener(this);
		}
	}

	public void deselezionaBottoni() {
		this.gruppoBottoni.clearSelection();
		for (Bottone button : listaBottoni) {
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
	public void actionPerformed(ActionEvent e) {
		Bottone b = ((Bottone) ((ToggleBtn) e.getSource()).getPadre());
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
