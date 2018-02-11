package com.molinari.gestionespese.view.datainsert;

import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.molinari.gestionespese.business.Finestra;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.container.PannelloBase;

public class DataInsertView implements Finestra, ItemListener {

	private Container container;
	private PannelloBase pans;
	
	
	public enum TOOL_PANEL{
		LOGIN, REGISTER, INCOMES, CHARGES, CATEGORIES, GROUPS 
	}
	
	private List<DataPanelView> panelsList = new ArrayList<>();
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
	
	public void update(){
		for (DataPanelView pannelloBase : panelsList) {
			pannelloBase.aggiorna();
		}
	}
	
	private void createContent() {

		PanelLogin panelLogin = new PanelLogin(pans);
		panelLogin.getPan().setVisible(true);
		getPanelsList().add(panelLogin);
		
		PanelRegister panelRegister = new PanelRegister(pans);
		panelRegister.getPan().setVisible(false);
		getPanelsList().add(panelRegister);
		
		PanelIncomes panelIncomes = new PanelIncomes(this.pans);
		panelIncomes.getPan().setVisible(false);
		getPanelsList().add(panelIncomes);
		
		PanelExpense panelExpense = new PanelExpense(pans);
		panelExpense.getPan().setVisible(false);
		getPanelsList().add(panelExpense);
		
		PanelCategories panelCategories = new PanelCategories(pans);
		panelCategories.getPan().setVisible(false);
		getPanelsList().add(panelCategories);
		
		PanelGroups panelGroups = new PanelGroups(pans);
		panelGroups.getPan().setVisible(false);
		getPanelsList().add(panelGroups);

	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}

	public List<DataPanelView> getPanelsList() {
		return panelsList;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		for (final DataPanelView pannello : getPanelsList()) {
			pannello.getPan().setVisible(false);
		}
		if(e.getStateChange() == ItemEvent.SELECTED){
			int selectedIndex = combo.getSelectedIndex();
			DataPanelView dataPanelView = panelsList.get(selectedIndex);
			PannelloBase pannelloBase = dataPanelView.getPan();
			dataPanelView.aggiorna();
			pannelloBase.setVisible(true);
		}
	}

}