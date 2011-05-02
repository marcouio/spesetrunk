package view.componenti.componentiPannello;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PannelloAScomparsa2 extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame inst = null;
				try {
					inst = new JFrame();
					inst.getContentPane().add(new PannelloAScomparsa2(inst));

				} catch (Throwable e) {
					e.printStackTrace();
				}
				inst.setBounds(0, 0, 100, 70);
				inst.setPreferredSize(new Dimension(100, 70));
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}

	private final ArrayList<JPanel>  pannelli = new ArrayList<JPanel>();
	private JComboBox                combo;
	private final JFrame             padre;
	private SottoPannelloDatiSpese   pannelloSpese;
	private SottoPannelloDatiEntrate pannelloEntrate;
	private SottoPannelloMesi        pannelloMesi;
	private SottoPannelloCategorie   pannelloCategorie;
	private SottoPannelloTotali      pannelloTotali;

	public PannelloAScomparsa2(JFrame contenitore) {
		padre = contenitore;
		initGui(padre);
	}

	private void initGui(JFrame contenitore) {

		this.setLayout(null);

		pannelloSpese = new SottoPannelloDatiSpese();
		pannelloEntrate = new SottoPannelloDatiEntrate();
		pannelloMesi = new SottoPannelloMesi();
		pannelloCategorie = new SottoPannelloCategorie();
		pannelloTotali = new SottoPannelloTotali();

		combo = new JComboBox();
		this.add(combo);
		combo.setBounds(20, 20, 160, 40);
		combo.addItem("");
		combo.addItem("COMBO 1");
		combo.addItem("COMBO 2");
		combo.addItem("COMBO 3");
		combo.addItem("COMBO 4");
		combo.addItem("COMBO 5");
		combo.setSelectedIndex(0);
		combo.addItemListener(this);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Point location = combo.getLocation();

		JPanel p = new JPanel();

		// p.setLocation(((int) location.getX()), ((int) location.getY() +
		// combo.getHeight()));
		for (JPanel pannello : pannelli) {
			pannello.setVisible(false);
			this.remove(pannello);
		}
		pannelli.clear();
		if (combo.getSelectedItem().equals("COMBO 1") && e.getStateChange() == ItemEvent.SELECTED) {
			JPanel sottoPannello = pannelloSpese.getPannello();
			mostra(p, sottoPannello);
			padre.setSize(getWidth(), padre.getPreferredSize().height + sottoPannello.getPreferredSize().height);

		} else if (combo.getSelectedItem().equals("COMBO 2") && e.getStateChange() == ItemEvent.SELECTED) {
			JPanel sottoPannello = pannelloCategorie.getPannello();
			mostra(p, sottoPannello);
			padre.setSize(getWidth(), padre.getPreferredSize().height + sottoPannello.getPreferredSize().height);

		} else if (combo.getSelectedItem().equals("COMBO 3") && e.getStateChange() == ItemEvent.SELECTED) {
			JPanel sottoPannello = pannelloEntrate.getPannello();
			mostra(p, sottoPannello);
			padre.setSize(getWidth(), padre.getPreferredSize().height + sottoPannello.getPreferredSize().height);
		} else if (combo.getSelectedItem().equals("COMBO 4") && e.getStateChange() == ItemEvent.SELECTED) {
			JPanel sottoPannello = pannelloMesi.getPannello();
			mostra(p, sottoPannello);
			padre.setSize(getWidth(), padre.getPreferredSize().height + sottoPannello.getPreferredSize().height);
		} else if (combo.getSelectedItem().equals("COMBO 5") && e.getStateChange() == ItemEvent.SELECTED) {
			JPanel sottoPannello = pannelloTotali.getPannello();
			mostra(p, sottoPannello);
			padre.setSize(getWidth(), padre.getPreferredSize().height + sottoPannello.getPreferredSize().height);
		} else if (combo.getSelectedItem().equals("") && e.getStateChange() == ItemEvent.SELECTED) {
			padre.setSize(getWidth(), 70);
		}
		p.setBounds(60, ((int) location.getY()) + combo.getHeight(), 200, 200);
		revalidate();
		repaint();

	}

	private void mostra(JPanel p, JPanel sottoPannello) {
		this.add(p);
		pannelli.add(p);
		p.add(sottoPannello);
		p.setVisible(true);
	}

}