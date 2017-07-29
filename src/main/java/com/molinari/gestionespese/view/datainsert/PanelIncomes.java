package com.molinari.gestionespese.view.datainsert;

import java.awt.Container;
import java.util.Date;
import java.util.Observable;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.cache.CacheEntrate;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.gestionespese.view.entrateuscite.AbstractEntrateView;
import com.molinari.gestionespese.view.entrateuscite.AscoltaInserisciEntrate;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelBase;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.text.TextFieldTesto;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.text.CorreggiTesto;

public class PanelIncomes extends AbstractEntrateView implements DataPanelView{

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
		this.pan.setSize(container.getWidth(), container.getHeight());
		
		labName = new LabelBase(pan);
		int width = pan.getWidth() - 20;
		labName.setBounds(10, 0, width, HEIGHT_LABEL);
	
		getFieldsIncome().setTfNome(new TextFieldTesto(pan));
		getFieldsIncome().getTfNome().setSize(width, HEIGHT_FIELD);
		getFieldsIncome().getTfNome().posizionaSottoA(labName, 0, 15);
		
		labDesc = new LabelBase(pan);
		labDesc.setSize(width, HEIGHT_LABEL);
		labDesc.posizionaSottoA(getFieldsIncome().getTfNome(), 0, 15);
		
		getFieldsIncome().setTaDescrizione(new TextAreaBase(pan));
		getFieldsIncome().getTaDescrizione().posizionaSottoA(labDesc, 0, 15);
		getFieldsIncome().getTaDescrizione().setSize(width, 100);
		
		labTipo = new LabelBase(pan);
		labTipo.posizionaSottoA(getFieldsIncome().getTaDescrizione(), 0, 15);
		labTipo.setSize(width, HEIGHT_LABEL);
		
		getFieldsIncome().setCbTipo(new ComboBoxBase<>(pan, INCOMETYPE.values()));
		getFieldsIncome().getCbTipo().posizionaSottoA(labTipo, 0, 10);
		getFieldsIncome().getCbTipo().setSize(width, HEIGHT_FIELD);
		
		labData = new LabelBase(pan);
		labData.posizionaSottoA(getFieldsIncome().getCbTipo(), 0, 15);
		labData.setSize(width, HEIGHT_LABEL);
		
		getFieldsIncome().setTfData(new TextFieldTesto(pan));
		getFieldsIncome().getTfData().posizionaSottoA(labData, 0, 10);
		getFieldsIncome().getTfData().setSize(width, HEIGHT_FIELD);
		
		labEuro = new LabelBase(pan);
		labEuro.posizionaSottoA(getFieldsIncome().getTfData(), 0, 15);
		labEuro.setSize(width, HEIGHT_LABEL);
		
		getFieldsIncome().setTfEuro(new TextFieldTesto(pan));
		getFieldsIncome().getTfEuro().posizionaSottoA(labEuro, 0, 10);
		getFieldsIncome().getTfEuro().setSize(width, HEIGHT_FIELD);
		
		inserisci = new ButtonBase(pan);
		inserisci.posizionaSottoA(getFieldsIncome().getTfEuro(), 0, 10);
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
		getFieldsIncome().getTaDescrizione().setText(I18NManager.getSingleton().getMessaggio("insertheredescrentry"));
	}

	@Override
	public PannelloBase getPan() {
		return pan;
	}

	public void setPan(PannelloBase pan) {
		this.pan = pan;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		getFieldsIncome().getTfNome().setText(getcNome());
		getFieldsIncome().getTaDescrizione().setText(getcDescrizione());
		getFieldsIncome().getCbTipo().setSelectedItem(getFisseOVar());
		getFieldsIncome().getTfData().setText(getcData());
		getFieldsIncome().getTfEuro().setText(getdEuro().toString());

	}

	@Override
	public void aggiornaModelDaVista() {
		final int idEntrate = CacheEntrate.getSingleton().getMaxId() + 1;
		getModelEntrate().setidEntrate(idEntrate);

		final CorreggiTesto checkTesto = new CorreggiTesto(getFieldsIncome().getTfNome().getText());

		final String nome = checkTesto.getTesto();
		setcNome(nome);

		checkTesto.setTesto(getFieldsIncome().getTaDescrizione().getText());
		final String descri = checkTesto.getTesto();
		setcDescrizione(descri);

		int ordinal = ((INCOMETYPE) getFieldsIncome().getCbTipo().getSelectedItem()).ordinal();
		setFisseOVar(Integer.toString(ordinal));
		if (AltreUtil.checkData(getFieldsIncome().getTfData().getText())) {
			setcData(getFieldsIncome().getTfData().getText());
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("datainformat");
			Alert.segnalazioneErroreGrave(messaggio);
		}
		if (AltreUtil.checkDouble(getFieldsIncome().getTfEuro().getText())) {
			final Double euro = Double.parseDouble(getFieldsIncome().getTfEuro().getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("valorenotcorrect");
			Alert.segnalazioneErroreGrave(messaggio);
		}
		setUtenti((Utenti) Controllore.getUtenteLogin());
		setDataIns(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
	}

	public FieldsIncome getFieldsIncome() {
		return fieldsIncome;
	}

	public void setFieldsIncome(FieldsIncome fieldsIncome) {
		this.fieldsIncome = fieldsIncome;
	}

	@Override
	public boolean aggiorna() {
		initLabes();
		return true;
	}

}
