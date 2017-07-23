package com.molinari.gestionespese.view.datainsert;

import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

import com.molinari.gestionespese.business.Finestra;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.container.PannelloBase;

public class DataInsertView implements Finestra, ItemListener {

	private Container container;
	private PannelloBase pans;
	
	
	public enum TOOL_PANEL{
		LOGIN, REGISTER, INCOMES, CHARGES, CATEGORIES, GROUPS, CONFIGS 
	}
	
	private List<PannelloBase> panelsList = new ArrayList<>();
	private ComboBoxBase<TOOL_PANEL> combo;
	
	public DataInsertView(Container padre) {
		super();
		
		this.container = new PannelloBase(padre);
		container.setSize(padre.getWidth(), padre.getHeight());
		
		combo = new ComboBoxBase<>(getContainer());
		final DefaultComboBoxModel<TOOL_PANEL> model = new DefaultComboBoxModel<>(TOOL_PANEL.values());
		combo.setModel(model);
		combo.setSize(padre.getWidth(), 40);
		
		combo.addItemListener(this);
		
		this.pans = new PannelloBase(container);
		this.pans.posizionaSottoA(combo, 0, 50);
		this.pans.setSize(container.getWidth(), container.getHeight() - 50);
		createContent();
	}

	private void createContent() {

		PanelLogin panelLogin = new PanelLogin(pans);
		getPanelsList().add(panelLogin.getPan());
		
		PanelIncomes panelIncomes = new PanelIncomes(this.pans);
		panelIncomes.getPan().setVisible(false);
		getPanelsList().add(panelIncomes.getPan());
		
		PanelExpense panelExpense = new PanelExpense(pans);
		panelExpense.getPan().setVisible(false);
		getPanelsList().add(panelExpense.getPan());
		
		PanelCategories panelCategories = new PanelCategories(pans);
		panelCategories.getPan().setVisible(false);
		getPanelsList().add(panelCategories.getPan());
		
		
	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}

	public List<PannelloBase> getPanelsList() {
		return panelsList;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		for (final JPanel pannello : getPanelsList()) {
			pannello.setVisible(false);
		}
		
		int selectedIndex = combo.getSelectedIndex();
		if(e.getStateChange() == ItemEvent.SELECTED){
			PannelloBase pannelloBase = panelsList.get(selectedIndex);
			pannelloBase.setVisible(true);
		}
	}

}