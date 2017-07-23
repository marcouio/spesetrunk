package com.molinari.gestionespese.view.datainsert;

import java.awt.Color;
import java.awt.Container;
import java.util.Observable;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;
import com.molinari.gestionespese.view.impostazioni.AbstractCategorieView;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelBase;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;

public class PanelCategories extends AbstractCategorieView {
	
	private static final int HEIGHT_FIELD = 30;
	private static final int HEIGHT_LABEL = 15;

	FieldsCategories fields = new FieldsCategories();
	
	public FieldsCategories getFields() {
		return fields;
	}

	public void setFields(FieldsCategories fields) {
		this.fields = fields;
	}

	private PannelloBase pan;
	private LabelBase labName;
	private LabelBase labDescr;
	private LabelBase labType;
	
	public PanelCategories(Container padre) {
		super(new WrapCatSpese());
		getModelCatSpese().addObserver(this);
		
		this.pan = new PannelloBase(padre);
		this.pan.setBackground(Color.ORANGE);
		this.pan.setSize(padre.getWidth(), padre.getHeight());
		
		labName = new LabelBase(pan);
		int width = pan.getWidth() - 20;
		labName.setBounds(10, 0, width, HEIGHT_LABEL);
		
		getFields().setTfNome(new TextFieldBase(pan));
		getFields().getTfNome().setSize(width, HEIGHT_FIELD);
		getFields().getTfNome().posizionaSottoA(labName, 0, 10);
		
		labDescr = new LabelBase(pan);
		labDescr.setSize(width, HEIGHT_LABEL);
		labDescr.posizionaSottoA(getFields().getTfNome(), 0, 15);
		
		getFields().setTaDescrizione(new TextAreaBase(pan));
		getFields().getTaDescrizione().setSize(width, 100);
		getFields().getTaDescrizione().posizionaSottoA(labDescr, 0, 10);
				
		labType = new LabelBase(pan);
		labType.posizionaSottoA(getFields().getTaDescrizione(), 0, 15);
		labType.setSize(width, HEIGHT_LABEL);
		
		ComboBoxModel<CATEGORYTYPE> model = new DefaultComboBoxModel<>(CATEGORYTYPE.values());
		ComboBoxBase<CATEGORYTYPE> comboType = new ComboBoxBase<>(pan);
		comboType.setModel(model);
		
		getFields().setCbImportanza(comboType);
		getFields().getCbImportanza().setSize(width, HEIGHT_FIELD);
		getFields().getCbImportanza().posizionaSottoA(labType, 0, 10);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}
