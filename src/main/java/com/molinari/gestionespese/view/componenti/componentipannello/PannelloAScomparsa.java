package com.molinari.gestionespese.view.componenti.componentipannello;

import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.molinari.gestionespese.business.Finestra;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.messages.I18NManager;

public class PannelloAScomparsa implements ItemListener, Finestra {

	private Container container;
	private JComboBox<String>                combo;
	private SottoPannelloDatiSpese   pannelloSpese;
	private SottoPannelloDatiEntrate pannelloEntrate;
	private SottoPannelloMesi        pannelloMesi;
	private SottoPannelloCategorie   pannelloCategorie;
	private SottoPannelloTotali      pannelloTotali;
	CostruttoreSottoPannello[] arrayPannelli;

	public PannelloAScomparsa(Container cont) {
		container = new PannelloBase(cont);
		initGui();
	}

	private void initGui() {
		PannelloBase padre = (PannelloBase) ((PannelloBase)getContainer()).getContenitorePadre();
		getContainer().setLayout(null);
		getContainer().setSize(padre.getWidth(), padre.getHeight());
		pannelloSpese = new SottoPannelloDatiSpese(getContainer());
		setSubPanelDimension(pannelloSpese.getPannello());
		pannelloEntrate = new SottoPannelloDatiEntrate(getContainer());
		setSubPanelDimension(pannelloEntrate.getPannello());
		pannelloMesi = new SottoPannelloMesi(getContainer());
		setSubPanelDimension(pannelloMesi.getPannello());
		pannelloCategorie = new SottoPannelloCategorie(getContainer());
		setSubPanelDimension(pannelloCategorie.getPannello());
		pannelloTotali = new SottoPannelloTotali(getContainer());
		setSubPanelDimension(pannelloTotali.getPannello());

		hideAllPanel();
		
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

		hideAllPanel();
		
		CostruttoreSottoPannello sottoPannello;
		if(combo.getSelectedIndex() != 0 && e.getStateChange() == ItemEvent.SELECTED){
			sottoPannello = arrayPannelli[combo.getSelectedIndex()];
			mostra(sottoPannello);
		}
		getContainer().validate();
		getContainer().repaint();

	}

	private void hideAllPanel() {
		pannelloSpese.getPannello().getPannello().setVisible(false);
		pannelloCategorie.getPannello().getPannello().setVisible(false);
		pannelloEntrate.getPannello().getPannello().setVisible(false);
		pannelloMesi.getPannello().getPannello().setVisible(false);
		pannelloTotali.getPannello().getPannello().setVisible(false);
	}

	private void mostra(CostruttoreSottoPannello sottoPannello) {
		
		sottoPannello.getPannello().setVisible(true);
	}

	private void setSubPanelDimension(CostruttoreSottoPannello sottoPannello) {
		int width = sottoPannello.getMaxWidth(sottoPannello.getCompontents()) + sottoPannello.distanzaDalBordoX * 2;
		int height = sottoPannello.getMaxHeight(sottoPannello.getCompontents()) + sottoPannello.distanzaDalBordoY * 2 * sottoPannello.getCompontents().length;
		sottoPannello.getPannello().setBounds(50, 100,width, height * 2);
	}

	private void initArrayPannello() {
		CostruttoreSottoPannello costruttoreSottoPannello = new CostruttoreSottoPannello(new PannelloBase(getContainer()), null, null);
		arrayPannelli = new CostruttoreSottoPannello[]{
				costruttoreSottoPannello,
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

	public SottoPannelloCategorie getPannelloCategorie() {
		return pannelloCategorie;
	}

	public void setPannelloCategorie(SottoPannelloCategorie pannelloCategorie) {
		this.pannelloCategorie = pannelloCategorie;
	}
}