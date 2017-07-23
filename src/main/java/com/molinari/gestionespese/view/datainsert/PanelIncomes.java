package com.molinari.gestionespese.view.datainsert;

import java.awt.Color;
import java.awt.Container;
import java.util.Observable;

import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.gestionespese.view.entrateuscite.AbstractEntrateView;
import com.molinari.gestionespese.view.entrateuscite.AscoltaInserisciEntrate;
import com.molinari.gestionespese.view.entrateuscite.EntrateView.INCOMETYPE;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelBase;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.text.TextFieldTesto;
import com.molinari.utility.messages.I18NManager;

public class PanelIncomes extends AbstractEntrateView {

	private static final int HEIGHT_FIELD = 30;
	private static final int HEIGHT_LABEL = 15;

	private FieldsIncome fieldsIncome = new FieldsIncome();
	
	private PannelloBase pan;
	private LabelBase labName;
	private LabelBase labDesc;
	private LabelBase labTipo;
	private LabelBase labData;
	private LabelBase labEuro;
	private ButtonBase inserisci;
	private ButtonBase eliminaUltima;

	public PanelIncomes(Container container) {
		super(new WrapEntrate());
		
		getModelEntrate().addObserver(this);
		
		this.pan = new PannelloBase(container);
		this.pan.setBackground(Color.ORANGE);
		this.pan.setSize(container.getWidth(), container.getHeight());
		
		labName = new LabelBase(pan);
		int width = pan.getWidth() - 20;
		labName.setBounds(10, 0, width, HEIGHT_LABEL);
	
		fieldsIncome.setTfNome(new TextFieldTesto(pan));
		fieldsIncome.getTfNome().setSize(width, HEIGHT_FIELD);
		fieldsIncome.getTfNome().posizionaSottoA(labName, 0, 15);
		
		labDesc = new LabelBase(pan);
		labDesc.setSize(width, HEIGHT_LABEL);
		labDesc.posizionaSottoA(fieldsIncome.getTfNome(), 0, 15);
		
		fieldsIncome.setTaDescrizione(new TextAreaBase(pan));
		fieldsIncome.getTaDescrizione().posizionaSottoA(labDesc, 0, 15);
		fieldsIncome.getTaDescrizione().setSize(width, 100);
		
		labTipo = new LabelBase(pan);
		labTipo.posizionaSottoA(fieldsIncome.getTaDescrizione(), 0, 15);
		labTipo.setSize(width, HEIGHT_LABEL);
		
		fieldsIncome.setCbTipo(new ComboBoxBase<>(pan, INCOMETYPE.values()));
		fieldsIncome.getCbTipo().posizionaSottoA(labTipo, 0, 10);
		fieldsIncome.getCbTipo().setSize(width, HEIGHT_FIELD);
		
		labData = new LabelBase(pan);
		labData.posizionaSottoA(fieldsIncome.getCbTipo(), 0, 15);
		labData.setSize(width, HEIGHT_LABEL);
		
		fieldsIncome.setTfData(new TextFieldTesto(pan));
		fieldsIncome.getTfData().posizionaSottoA(labData, 0, 10);
		fieldsIncome.getTfData().setSize(width, HEIGHT_FIELD);
		
		labEuro = new LabelBase(pan);
		labEuro.posizionaSottoA(fieldsIncome.getTfData(), 0, 15);
		labEuro.setSize(width, HEIGHT_LABEL);
		
		fieldsIncome.setTfEuro(new TextFieldTesto(pan));
		fieldsIncome.getTfEuro().posizionaSottoA(labEuro, 0, 10);
		fieldsIncome.getTfEuro().setSize(width, HEIGHT_FIELD);
		
		inserisci = new ButtonBase(pan);
		inserisci.posizionaSottoA(fieldsIncome.getTfEuro(), 0, 10);
		inserisci.setSize(width / 2, 27);

		eliminaUltima = new ButtonBase(pan);
		eliminaUltima.posizionaADestraDi(inserisci, 5, 0);
		eliminaUltima.setSize(width / 2 -10, 27);

		eliminaUltima.addActionListener(new AscoltaEliminaUltimaEntrata(this));

		inserisci.addActionListener(new AscoltaInserisciEntrate(this));

		initLabes();
	}

	private void initLabes() {
		labTipo.setText(I18NManager.getSingleton().getMessaggio("type"));
		labName.setText(I18NManager.getSingleton().getMessaggio("name"));
		labDesc.setText(I18NManager.getSingleton().getMessaggio("descr"));
		labData.setText(I18NManager.getSingleton().getMessaggio("date"));
		labEuro.setText(I18NManager.getSingleton().getMessaggio("eur"));
		inserisci.setText(I18NManager.getSingleton().getMessaggio("insert"));
		eliminaUltima.setText(I18NManager.getSingleton().getMessaggio("deletelast"));
		fieldsIncome.getTaDescrizione().setText(I18NManager.getSingleton().getMessaggio("insertheredescrentry"));
	}

	public PannelloBase getPan() {
		return pan;
	}

	public void setPan(PannelloBase pan) {
		this.pan = pan;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		fieldsIncome.getTfNome().setText(getcNome());
		fieldsIncome.getTaDescrizione().setText(getcDescrizione());
		fieldsIncome.getCbTipo().setSelectedItem(getFisseOVar());
		fieldsIncome.getTfData().setText(getcData());
		fieldsIncome.getTfEuro().setText(getdEuro().toString());

	}

	@Override
	public void aggiornaModelDaVista() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean nonEsistonoCampiNonValorizzati() {
		// TODO Auto-generated method stub
		return false;
	}

}
