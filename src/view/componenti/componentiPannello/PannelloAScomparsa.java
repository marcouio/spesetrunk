package view.componenti.componentiPannello;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import business.internazionalizzazione.I18NManager;

public class PannelloAScomparsa extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;

	private final ArrayList<JPanel>  pannelli = new ArrayList<JPanel>();
	private JComboBox                combo;
	private SottoPannelloDatiSpese   pannelloSpese;
	private SottoPannelloDatiEntrate pannelloEntrate;
	private SottoPannelloMesi        pannelloMesi;
	private SottoPannelloCategorie   pannelloCategorie;
	private SottoPannelloTotali      pannelloTotali;
	CostruttoreSottoPannello[] arrayPannelli;

	public PannelloAScomparsa() {
		initGui();
	}

	private void initGui() {

		this.setLayout(null);
		this.setTitle(I18NManager.getSingleton().getMessaggio("datapanel"));
		this.setSize(250, 425);
		pannelloSpese = new SottoPannelloDatiSpese();
		pannelloEntrate = new SottoPannelloDatiEntrate();
		pannelloMesi = new SottoPannelloMesi();
		pannelloCategorie = new SottoPannelloCategorie();
		pannelloTotali = new SottoPannelloTotali();

		initArrayPannello();
		
		combo = new JComboBox();
		this.add(combo);
		combo.setBounds(65, 50, 120, 40);
		combo.addItem("");
		combo.addItem("1 - " + I18NManager.getSingleton().getMessaggio("withdrawal"));
		combo.addItem("2 - " + I18NManager.getSingleton().getMessaggio("categories"));
		combo.addItem("3 - " + I18NManager.getSingleton().getMessaggio("entries"));
		combo.addItem("4 - " + I18NManager.getSingleton().getMessaggio("months"));
		combo.addItem("5 - " + I18NManager.getSingleton().getMessaggio("totals"));
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
		CostruttoreSottoPannello sottoPannello = null;
		if(combo.getSelectedIndex() != 0 && e.getStateChange() == ItemEvent.SELECTED){
			sottoPannello = arrayPannelli[combo.getSelectedIndex()];
			mostra(p, sottoPannello);
		}
		this.validate();
		repaint();

	}

	private void mostra(JPanel p, CostruttoreSottoPannello sottoPannello) {
		this.add(p);
		pannelli.add(p);
		p.add(sottoPannello);
		p.setVisible(true);
		p.setBounds(50, 90, sottoPannello.getPreferredSize().width, sottoPannello.getPreferredSize().height);
	}
	
	private void initArrayPannello() {
		arrayPannelli = new CostruttoreSottoPannello[]{
				new CostruttoreSottoPannello(), 
				pannelloSpese.getPannello(),
				pannelloCategorie.getPannello(),
				pannelloEntrate.getPannello(),
				pannelloMesi.getPannello(),
				pannelloTotali.getPannello()
		};
	}


}