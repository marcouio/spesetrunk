package com.molinari.gestionespese.view.datainsert;

import java.awt.Color;
import java.awt.Container;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.gestionespese.view.entrateuscite.AbstractUsciteView;
import com.molinari.gestionespese.view.entrateuscite.AscoltaEliminaUltimaSpesa;
import com.molinari.gestionespese.view.entrateuscite.AscoltaInserisciUscite;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelBase;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.text.TextFieldTesto;
import com.molinari.utility.messages.I18NManager;

public class PanelExpense extends AbstractUsciteView {
	
	private FieldsExpense fieldsExpense = new FieldsExpense();
	private PannelloBase pan;
	private LabelBase labName;
	private LabelBase labDesc;
	private LabelBase labCat;
	private LabelBase labEuro;
	private LabelBase labData;
	private ButtonBase inserisci;
	private ButtonBase eliminaUltima;
	
	private static final int HEIGHT_FIELD = 30;
	private static final int HEIGHT_LABEL = 15;
	
	public PanelExpense(Container container) {
		super(new WrapSingleSpesa());
		
		getModelUscita().addObserver(this);
		
		this.pan = new PannelloBase(container);
		this.pan.setBackground(Color.ORANGE);
		this.pan.setSize(container.getWidth(), container.getHeight());
		int width = pan.getWidth() - 20;
		
		labName = new LabelBase(pan);
		labName.setBounds(10, 0, width, HEIGHT_LABEL);
		
		fieldsExpense.setTfNome(new TextFieldTesto(pan));
		fieldsExpense.getTfNome().setSize(width, HEIGHT_FIELD);
		fieldsExpense.getTfNome().posizionaSottoA(labName, 0, 10);
		
		labDesc = new LabelBase(pan);
		labDesc.setSize(width, HEIGHT_LABEL);
		labDesc.posizionaSottoA(fieldsExpense.getTfNome(), 0, 15);
		
		fieldsExpense.setTaDescrizione(new TextAreaBase(pan));
		fieldsExpense.getTaDescrizione().posizionaSottoA(labDesc, 0, 10);
		fieldsExpense.getTaDescrizione().setSize(width, 100);
		
		labCat = new LabelBase(pan);
		labCat.setSize(width, HEIGHT_LABEL);
		labCat.posizionaSottoA(fieldsExpense.getTaDescrizione(), 0, 15);
		
		final List<ICatSpese> listCategoriePerCombo = CacheCategorie.getSingleton().getListCategoriePerCombo();
		fieldsExpense.setcCategorie(new ComboBoxBase<>(pan, new Vector<>(listCategoriePerCombo)));
		fieldsExpense.getcCategorie().setSize(width, HEIGHT_FIELD);
		fieldsExpense.getcCategorie().posizionaSottoA(labCat, 0, 10);

		labEuro = new LabelBase(pan);
		labEuro.setSize(width, HEIGHT_LABEL);
		labEuro.posizionaSottoA(fieldsExpense.getcCategorie(), 0, 15);
		
		fieldsExpense.setTfEuro(new TextFieldTesto(pan));
		fieldsExpense.getTfEuro().posizionaSottoA(labEuro, 0, 10);
		fieldsExpense.getTfEuro().setSize(width, HEIGHT_FIELD);
		
		labData = new LabelBase(pan);
		labData.setSize(width, HEIGHT_LABEL);
		labData.posizionaSottoA(fieldsExpense.getTfEuro(), 0, 15);
		
		fieldsExpense.setTfData(new TextFieldTesto(pan));
		fieldsExpense.getTfData().setSize(width, HEIGHT_FIELD);
		fieldsExpense.getTfData().posizionaSottoA(labData, 0, 10);
		
		inserisci = new ButtonBase(pan);
		inserisci.posizionaSottoA(fieldsExpense.getTfData(), 0, 10);
		inserisci.setSize(width / 2, 27);

		eliminaUltima = new ButtonBase(pan);
		eliminaUltima.posizionaADestraDi(inserisci, 5, 0);
		eliminaUltima.setSize(width / 2 -10, 27);
		
		eliminaUltima.addActionListener(new AscoltaEliminaUltimaSpesa(this));
		inserisci.addActionListener(new AscoltaInserisciUscite(this));
		
		initLabel();
		
	}

	public void initLabel() {
		labCat.setText(I18NManager.getSingleton().getMessaggio("categories"));
		labDesc.setText(I18NManager.getSingleton().getMessaggio("descr"));
		labEuro.setText(I18NManager.getSingleton().getMessaggio("eur"));
		labName.setText(I18NManager.getSingleton().getMessaggio("name"));
		labData.setText(I18NManager.getSingleton().getMessaggio("date"));
		inserisci.setText(I18NManager.getSingleton().getMessaggio("insert"));
		eliminaUltima.setText(I18NManager.getSingleton().getMessaggio("deletelast"));
		fieldsExpense.getTaDescrizione().setText(I18NManager.getSingleton().getMessaggio("insertheredescr"));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public PannelloBase getPan() {
		return pan;
	}

	public void setPan(PannelloBase pan) {
		this.pan = pan;
	}

	@Override
	public void aggiornaModelDaVista() {
		// TODO Auto-generated method stub
		
	}

}
