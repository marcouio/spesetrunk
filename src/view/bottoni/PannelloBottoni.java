package view.bottoni;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import view.sidebar.ToggleBtn;
import business.DBUtil;

public class PannelloBottoni extends JPanel implements ActionListener {

	private static final long        serialVersionUID = 1L;
	private final ArrayList<Bottone> listaBottoni     = new ArrayList<Bottone>();

	public static final int          MODE_PIENO       = 0;

	// public static final int MODE_VUOTO = 1;

	public PannelloBottoni() {
		init();
	}

	public PannelloBottoni(ArrayList<Bottone> bottoni) {
		init();

		for (Bottone toggleBtn : bottoni) {
			listaBottoni.add(toggleBtn);
			this.addBottone(toggleBtn);
		}

	}

	public PannelloBottoni(int mode) {
		init();

		ToggleBtn bottoni3 = new ToggleBtn("Primo", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		bottoni3.settaggioBottoneStandard();
		Bottone b3 = new Bottone(bottoni3);
		ToggleBtn bottoni2 = new ToggleBtn("Secondo", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		bottoni2.settaggioBottoneStandard();
		Bottone b2 = new Bottone(bottoni3);
		ToggleBtn bottoni = new ToggleBtn("Terzo", new ImageIcon("/home/kiwi/Immagini/prova.png"));
		bottoni.settaggioBottoneStandard();
		Bottone b = new Bottone(bottoni3);

		this.addBottone(b3);
		this.addBottone(b2);
		this.addBottone(b);
	}

	private void init() {
		this.setLayout(new GridLayout(1, 0));
	}

	public void addBottone(Bottone bottone) {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		p.add(bottone);
		this.add(p);

		listaBottoni.add(bottone);

		bottone.getBottone().addActionListener(this);

	}

	public void deselezionaBottoni() {
		for (Bottone bottone : listaBottoni) {
			bottone.getBottone().setSelected(false);
			bottone.getContent().setVisible(false);
		}
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DBUtil.closeConnection();
				JFrame inst = new JFrame();
				inst.setBounds(0, 0, 1000, 650);
				inst.add(new PannelloBottoni(MODE_PIENO));
				inst.setTitle("PannelloBottoni");
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		deselezionaBottoni();
		Bottone b = ((Bottone) ((ToggleBtn) e.getSource()).getPadre());
		((ToggleBtn) e.getSource()).setSelected(true);
		b.getContent().setVisible(true);
	}
}
