package com.molinari.gestionespese.view.datainsert;

import java.awt.Container;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.gestionespese.view.entrateuscite.AbstractUsciteView;
import com.molinari.gestionespese.view.entrateuscite.AscoltaEliminaUltimaSpesa;
import com.molinari.gestionespese.view.entrateuscite.AscoltaInserisciUscite;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelBase;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.text.TextFieldTesto;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.text.CorreggiTesto;

public class PanelExpense extends AbstractUsciteView implements DataPanelView  {
	
	private FieldsExpense fieldsExpense = new FieldsExpense();
	private PannelloBase pan;
	private LabelBase labName;
	private LabelBase labDesc;
	private LabelBase labCat;
	private LabelBase labEuro;
	private LabelBase labData;
	private ButtonBase inserisci;
	private ButtonBase eliminaUltima;
	private ComboBoxBase<ICatSpese>  cCategorie;
	private TextFieldTesto tfData;
	
	private static final int HEIGHT_FIELD = 30;
	private static final int HEIGHT_LABEL = 15;
	
	public PanelExpense(Container container) {
		super(new WrapSingleSpesa());
		
		getModelUscita().addObserver(this);
		
		this.pan = new PannelloBase(container);
		this.pan.setSize(container.getWidth(), container.getHeight());
		int width = pan.getWidth() - 20;
		
		labName = new LabelBase(pan);
		labName.setBounds(10, 0, width, HEIGHT_LABEL);
		
		getFieldsExpense().setTfNome(new TextFieldTesto(pan));
		getFieldsExpense().getTfNome().setSize(width, HEIGHT_FIELD);
		getFieldsExpense().getTfNome().posizionaSottoA(labName, 0, 10);
		
		labDesc = new LabelBase(pan);
		labDesc.setSize(width, HEIGHT_LABEL);
		labDesc.posizionaSottoA(getFieldsExpense().getTfNome(), 0, 15);
		
		getFieldsExpense().setTaDescrizione(new TextAreaBase(pan));
		getFieldsExpense().getTaDescrizione().posizionaSottoA(labDesc, 0, 10);
		getFieldsExpense().getTaDescrizione().setSize(width, 100);
		
		labCat = new LabelBase(pan);
		labCat.setSize(width, HEIGHT_LABEL);
		labCat.posizionaSottoA(getFieldsExpense().getTaDescrizione(), 0, 15);
		
		cCategorie = new ComboBoxBase<>(pan);
		initCategories();
		getFieldsExpense().setcCategorie(cCategorie);
		getFieldsExpense().getcCategorie().setSize(width, HEIGHT_FIELD);
		getFieldsExpense().getcCategorie().posizionaSottoA(labCat, 0, 10);

		labEuro = new LabelBase(pan);
		labEuro.setSize(width, HEIGHT_LABEL);
		labEuro.posizionaSottoA(getFieldsExpense().getcCategorie(), 0, 15);
		
		getFieldsExpense().setTfEuro(new TextFieldTesto(pan));
		getFieldsExpense().getTfEuro().posizionaSottoA(labEuro, 0, 10);
		getFieldsExpense().getTfEuro().setSize(width, HEIGHT_FIELD);
		
		labData = new LabelBase(pan);
		labData.setSize(width, HEIGHT_LABEL);
		labData.posizionaSottoA(getFieldsExpense().getTfEuro(), 0, 15);
		
		tfData = new TextFieldTesto(pan);
		
		getFieldsExpense().setTfData(tfData);
		getFieldsExpense().getTfData().setSize(width, HEIGHT_FIELD);
		getFieldsExpense().getTfData().posizionaSottoA(labData, 0, 10);
		
		inserisci = new ButtonBase(pan);
		inserisci.posizionaSottoA(getFieldsExpense().getTfData(), 0, 10);
		inserisci.setSize(width / 2, 27);

		eliminaUltima = new ButtonBase(pan);
		eliminaUltima.posizionaADestraDi(inserisci, 5, 0);
		eliminaUltima.setSize(width / 2 -10, 27);
		
		eliminaUltima.addActionListener(new AscoltaEliminaUltimaSpesa(this));
		inserisci.addActionListener(new AscoltaInserisciUscite(this));
		
		initLabel();
		
	}

	public void initCategories() {
		final List<ICatSpese> listCategoriePerCombo = CacheCategorie.getSingleton().getListCategoriePerCombo();
		DefaultComboBoxModel<ICatSpese> model = new DefaultComboBoxModel<>(new Vector<>(listCategoriePerCombo));
		cCategorie.setModel(model);
	}

	public void initLabel() {
		labCat.setText(I18NManager.getSingleton().getMessaggio("categories"));
		labDesc.setText(I18NManager.getSingleton().getMessaggio("descr"));
		labEuro.setText(I18NManager.getSingleton().getMessaggio("eur"));
		labName.setText(I18NManager.getSingleton().getMessaggio("name"));
		labData.setText(I18NManager.getSingleton().getMessaggio("date"));
		inserisci.setText(I18NManager.getSingleton().getMessaggio("insert"));
		eliminaUltima.setText(I18NManager.getSingleton().getMessaggio("deletelast"));
		getFieldsExpense().getTaDescrizione().setText(I18NManager.getSingleton().getMessaggio("insertheredescr"));
		final GregorianCalendar gc = new GregorianCalendar();
		tfData.setText(DBUtil.dataToString(gc.getTime(), "yyyy/MM/dd"));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		getFieldsExpense().getTfNome().setText(getcNome());
		getFieldsExpense().getTaDescrizione().setText(getcDescrizione());
		getFieldsExpense().getcCategorie().setSelectedItem(getCategoria());
		getFieldsExpense().getTfData().setText(getcData());
		getFieldsExpense().getTfEuro().setText(getdEuro().toString());

	}

	@Override
	public PannelloBase getPan() {
		return pan;
	}

	public void setPan(PannelloBase pan) {
		this.pan = pan;
	}

	@Override
	public void aggiornaModelDaVista() {
		final int idSpesa = CacheUscite.getSingleton().getMaxId() + 1;
		getModelUscita().setidSpesa(idSpesa);

		final CorreggiTesto checkTesto = new CorreggiTesto(getFieldsExpense().getTfNome().getText());
		final String nomeCheckato = checkTesto.getTesto();
		setcNome(nomeCheckato);

		checkTesto.setTesto(getFieldsExpense().getTaDescrizione().getText());
		final String descrizioneCheckato = checkTesto.getTesto();
		setcDescrizione(descrizioneCheckato);

		setCategoria((ICatSpese) getFieldsExpense().getcCategorie().getSelectedItem());
		if (AltreUtil.checkDate(getFieldsExpense().getTfData().getText())) {
			setcData(getFieldsExpense().getTfData().getText());
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("datainformat");
			Alert.segnalazioneErroreGrave(messaggio);
		}
		if (AltreUtil.checkDouble(getFieldsExpense().getTfEuro().getText())) {
			final Double euro = Double.parseDouble(getFieldsExpense().getTfEuro().getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("valorenotcorrect");
			Alert.segnalazioneErroreGrave(messaggio);
		}
		setUtenti((Utenti) Controllore.getUtenteLogin());
		setDataIns(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
	}

	public FieldsExpense getFieldsExpense() {
		return fieldsExpense;
	}

	public void setFieldsExpense(FieldsExpense fieldsExpense) {
		this.fieldsExpense = fieldsExpense;
	}

	@Override
	public boolean aggiorna() {
		initLabel();
		initCategories();
		return true;
	}

}
