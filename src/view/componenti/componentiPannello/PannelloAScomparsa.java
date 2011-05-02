package view.componenti.componentiPannello;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class PannelloAScomparsa extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame inst = null;
				try {
					inst = new JFrame();
					inst.getContentPane().add(new PannelloAScomparsa(inst));

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

	private final ArrayList<JPanel> pannelli = new ArrayList<JPanel>();
	private JComboBox               combo;
	private final JFrame            padre;

	public PannelloAScomparsa(JFrame contenitore) {
		padre = contenitore;
		initGui(padre);
	}

	private void initGui(JFrame contenitore) {

		this.setLayout(new FlowLayout());

		combo = new JComboBox();
		this.add(combo);
		combo.setBounds(800 / 2 - 160 / 2, 30, 160, 40);
		combo.addItem("");
		combo.addItem("COMBO 1");
		combo.addItem("COMBO 2");
		combo.addItem("COMBO 3");
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
		if (combo.getSelectedItem().equals("COMBO 1") && e.getStateChange() == ItemEvent.SELECTED) {

			JTextField f = new JTextField("Ciao 1");
			mostra(p, f);
			f.setPreferredSize(new Dimension(100, 50));
			padre.setSize(getWidth(), padre.getPreferredSize().height + f.getPreferredSize().height);

		} else if (combo.getSelectedItem().equals("COMBO 2") && e.getStateChange() == ItemEvent.SELECTED) {

			JTextField f = new JTextField("Ciao 2");
			mostra(p, f);
			f.setPreferredSize(new Dimension(100, 50));
			padre.setSize(getWidth(), padre.getPreferredSize().height + f.getPreferredSize().height);

		} else if (combo.getSelectedItem().equals("COMBO 3") && e.getStateChange() == ItemEvent.SELECTED) {

			JTextField f = new JTextField("Ciao 3");
			f.setPreferredSize(new Dimension(100, 110));
			mostra(p, f);
			padre.setSize(getWidth(), padre.getPreferredSize().height + f.getPreferredSize().height);

		} else if (combo.getSelectedItem().equals("") && e.getStateChange() == ItemEvent.SELECTED) {
			padre.setSize(getWidth(), 70);
		}
		revalidate();
		repaint();

	}

	private void mostra(JPanel p, JTextField f) {
		this.add(p);
		pannelli.add(p);
		p.setLayout(new FlowLayout());
		p.add(f);
		p.setVisible(true);
	}

}