package com.molinari.gestionespese.view.componenti.componentipannello;

import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;

import grafica.componenti.contenitori.PannelloBase;

public class PannelloAScomparsa implements ItemListener, Finestra {

	private Container container;
	private final ArrayList<JPanel>  pannelli = new ArrayList<>();
	private JComboBox<String>                combo;
	private transient SottoPannelloDatiSpese   pannelloSpese;
	private transient SottoPannelloDatiEntrate pannelloEntrate;
	private transient SottoPannelloMesi        pannelloMesi;
	private transient SottoPannelloCategorie   pannelloCategorie;
	private transient SottoPannelloTotali      pannelloTotali;
	CostruttoreSottoPannello[] arrayPannelli;

	public PannelloAScomparsa(Container cont) {
		container = new PannelloBase(cont);
		initGui();
	}

	private void initGui() {
		PannelloBase padre = (PannelloBase) ((PannelloBase)getContainer()).getContenitorePadre();
		getContainer().setLayout(null);
		getContainer().setSize(padre.getWidth(), padre.getHeight());
		pannelloSpese = new SottoPannelloDatiSpese();
		pannelloEntrate = new SottoPannelloDatiEntrate();
		pannelloMesi = new SottoPannelloMesi();
		pannelloCategorie = new SottoPannelloCategorie();
		pannelloTotali = new SottoPannelloTotali();

		initArrayPannello();

		combo = new JComboBox<>();
		getContainer().add(combo);
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

		final JPanel p = new JPanel();

		for (final JPanel pannello : pannelli) {
			pannello.setVisible(false);
			getContainer().remove(pannello);
		}
		pannelli.clear();
		CostruttoreSottoPannello sottoPannello;
		if(combo.getSelectedIndex() != 0 && e.getStateChange() == ItemEvent.SELECTED){
			sottoPannello = arrayPannelli[combo.getSelectedIndex()];
			mostra(p, sottoPannello);
		}
		getContainer().validate();
		getContainer().repaint();

	}

	private void mostra(JPanel p, CostruttoreSottoPannello sottoPannello) {
		getContainer().add(p);
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

	public SottoPannelloDatiSpese getPannelloSpese() {
		return pannelloSpese;
	}

	public void setPannelloSpese(SottoPannelloDatiSpese pannelloSpese) {
		this.pannelloSpese = pannelloSpese;
	}

	public SottoPannelloDatiEntrate getPannelloEntrate() {
		return pannelloEntrate;
	}

	public void setPannelloEntrate(SottoPannelloDatiEntrate pannelloEntrate) {
		this.pannelloEntrate = pannelloEntrate;
	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}
}