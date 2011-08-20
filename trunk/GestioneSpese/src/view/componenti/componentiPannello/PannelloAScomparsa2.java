package view.componenti.componentiPannello;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PannelloAScomparsa2 extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame inst = null;
				try {
					inst = new PannelloAScomparsa2();

				} catch (Throwable e) {
					e.printStackTrace();
				}
				inst.setBounds(0, 0, 250, 425);
				inst.setPreferredSize(new Dimension(250, 425));
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}

	private final ArrayList<JPanel>  pannelli = new ArrayList<JPanel>();
	private JComboBox                combo;
	private SottoPannelloDatiSpese   pannelloSpese;
	private SottoPannelloDatiEntrate pannelloEntrate;
	private SottoPannelloMesi        pannelloMesi;
	private SottoPannelloCategorie   pannelloCategorie;
	private SottoPannelloTotali      pannelloTotali;

	public PannelloAScomparsa2() {
		initGui();
	}

	private void initGui() {

		this.setLayout(null);
		this.setTitle("Pannello Dati");
		pannelloSpese = new SottoPannelloDatiSpese();
		pannelloEntrate = new SottoPannelloDatiEntrate();
		pannelloMesi = new SottoPannelloMesi();
		pannelloCategorie = new SottoPannelloCategorie();
		pannelloTotali = new SottoPannelloTotali();

		combo = new JComboBox();
		this.add(combo);
		combo.setBounds(65, 50, 120, 40);
		combo.addItem("");
		combo.addItem("1 - Spese");
		combo.addItem("2 - Categorie");
		combo.addItem("3 - Entrate");
		combo.addItem("4 - Mesi");
		combo.addItem("5 - Totali");
		combo.setSelectedIndex(0);
		combo.addItemListener(this);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		JPanel p = new JPanel();

		for (JPanel pannello : pannelli) {
			pannello.setVisible(false);
			this.remove(pannello);
		}
		pannelli.clear();
		if (combo.getSelectedItem().equals("1 - Spese") && e.getStateChange() == ItemEvent.SELECTED) {
			JPanel sottoPannello = pannelloSpese.getPannello();
			mostra(p, sottoPannello);
			p.setBounds(50, 90, sottoPannello.getPreferredSize().width, sottoPannello.getPreferredSize().height);

		} else if (combo.getSelectedItem().equals("2 - Categorie") && e.getStateChange() == ItemEvent.SELECTED) {
			JPanel sottoPannello = pannelloCategorie.getPannello();
			mostra(p, sottoPannello);
			p.setBounds(50, 90, sottoPannello.getPreferredSize().width, sottoPannello.getPreferredSize().height);

		} else if (combo.getSelectedItem().equals("3 - Entrate") && e.getStateChange() == ItemEvent.SELECTED) {
			JPanel sottoPannello = pannelloEntrate.getPannello();
			mostra(p, sottoPannello);
			p.setBounds(50, 90, sottoPannello.getPreferredSize().width, sottoPannello.getPreferredSize().height);
		} else if (combo.getSelectedItem().equals("4 - Mesi") && e.getStateChange() == ItemEvent.SELECTED) {
			JPanel sottoPannello = pannelloMesi.getPannello();
			mostra(p, sottoPannello);
			p.setBounds(50, 90, sottoPannello.getPreferredSize().width, sottoPannello.getPreferredSize().height);
		} else if (combo.getSelectedItem().equals("5 - Totali") && e.getStateChange() == ItemEvent.SELECTED) {
			JPanel sottoPannello = pannelloTotali.getPannello();
			mostra(p, sottoPannello);
			p.setBounds(50, 90, sottoPannello.getPreferredSize().width, sottoPannello.getPreferredSize().height);
		}
		this.validate();
		repaint();

	}

	private void mostra(JPanel p, JPanel sottoPannello) {
		this.add(p);
		pannelli.add(p);
		p.add(sottoPannello);
		p.setVisible(true);
	}

}