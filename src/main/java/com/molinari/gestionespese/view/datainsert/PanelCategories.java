package com.molinari.gestionespese.view.datainsert;

import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;
import com.molinari.gestionespese.view.impostazioni.AbstractCategorieView;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreAggiornaCategoria;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreCancellaCategoria;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreInserisciCategoria;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelBase;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.text.CorreggiTesto;

public class PanelCategories extends AbstractCategorieView {
	
	private static final int HEIGHT_FIELD = 30;
	private static final int HEIGHT_LABEL = 15;

	FieldsCategories fields = new FieldsCategories();
	
	private PannelloBase pan;
	private LabelBase labName;
	private LabelBase labDescr;
	private LabelBase labType;
	private LabelBase labGroup;
	private LabelBase labCategorie;
	private ButtonBase inserisci;
	private ButtonBase aggiorna;
	private ButtonBase cancella;
	
	public PanelCategories(Container padre) {
		super(new WrapCatSpese());
		getModelCatSpese().addObserver(this);
		
		this.pan = new PannelloBase(padre);
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
		
		labGroup = new LabelBase(pan);
		labGroup.setSize(width, HEIGHT_LABEL);
		labGroup.posizionaSottoA(getFields().getCbImportanza(), 0, 15);
		
		final List<IGruppi> vettoreGruppi = CacheGruppi.getSingleton().getListCategoriePerCombo(CacheGruppi.getSingleton().getAllGruppi());
		ComboBoxModel<IGruppi> modelGroup = new DefaultComboBoxModel<>(new Vector<>(vettoreGruppi));
		ComboBoxBase<IGruppi> comboGroup = new ComboBoxBase<>(pan);
		comboGroup.setModel(modelGroup);
		getFields().setCbGruppi(comboGroup);
		comboGroup.setSize(width, HEIGHT_FIELD);
		comboGroup.posizionaSottoA(labGroup, 0, 10);
		
		labCategorie = new LabelBase(pan);
		labCategorie.setSize(width, HEIGHT_LABEL);
		labCategorie.posizionaSottoA(getFields().getCbGruppi(), 0, 15);
		
		getFields().setCategorieSpesa(CacheCategorie.getSingleton().getListCategoriePerCombo());
		getFields().setCbCategorie(new ComboBoxBase<>(pan, new Vector<>(getFields().getCategorieSpesa())));
		getFields().getCbCategorie().posizionaSottoA(labCategorie, 0, 10);
		getFields().getCbCategorie().setSize(width, HEIGHT_FIELD);
		getFields().getCbCategorie().addItemListener(getListener());
		
		inserisci = new ButtonBase(pan);
		inserisci.setActionCommand("Inserisci Categoria");
		inserisci.setSize(width, HEIGHT_FIELD);
		inserisci.posizionaSottoA(getFields().getCbCategorie(), 0, 15);
		inserisci.addActionListener(new AscoltatoreInserisciCategoria(this));
		
		// bottone Update
		aggiorna = new ButtonBase(pan);
		aggiorna.setSize(width / 2, HEIGHT_FIELD);
		aggiorna.setActionCommand("Aggiorna");
		aggiorna.posizionaSottoA(inserisci, 0, 15);
		aggiorna.addActionListener(new AscoltatoreAggiornaCategoria(this));


		// bottone cancella
		cancella = new ButtonBase(pan);
		cancella.setActionCommand("Cancella");
		cancella.setSize(width/2, HEIGHT_FIELD);
		cancella.posizionaADestraDi(aggiorna, 0, 0);
		cancella.addActionListener(new AscoltatoreCancellaCategoria(this));
		
		initLabel();
	}
	
	private ItemListener getListener() {
		return (ItemEvent e) -> {

			if (getFields().getCbCategorie().getSelectedIndex() != 0 && getFields().getCbCategorie().getSelectedItem() != null) {
				setFields();

			}
		};
	}

	private void setFields() {
		getFields().setCategoria((ICatSpese) getFields().getCbCategorie().getSelectedItem());
		ICatSpese categoria = getFields().getCategoria();
		getFields().getTfNome().setText(categoria.getnome());
		getFields().getTaDescrizione().setText(categoria.getdescrizione());
		String importanza = categoria.getimportanza();
		CATEGORYTYPE[] values = CATEGORYTYPE.values();
		getFields().getCbImportanza().setSelectedItem(values[Integer.parseInt(importanza)]);
		ComboBoxBase<IGruppi> cbGruppi = getFields().getCbGruppi();
		final int numeroGruppi = cbGruppi.getModel().getSize();
		boolean trovato = false;
		
		for (int i = 0; i < numeroGruppi; i++) {
			final IGruppi gruppo = cbGruppi.getModel().getElementAt(i);
			boolean groupIsNotNull = gruppo != null && gruppo.getnome()!=null &&  categoria.getGruppi()!=null;
			if (groupIsNotNull && gruppo.getnome().equals(categoria.getGruppi().getnome())) {
				cbGruppi.setSelectedIndex(i);
				trovato = true;
			}
		}
		if(!trovato){
			cbGruppi.setSelectedIndex(0);
		}
	}

	public void initLabel() {
		labName.setText(I18NManager.getSingleton().getMessaggio("category"));
		labDescr.setText(I18NManager.getSingleton().getMessaggio("descr"));
		labType.setText(I18NManager.getSingleton().getMessaggio("tipology"));
		labCategorie.setText(I18NManager.getSingleton().getMessaggio("catlist"));
		labGroup.setText(I18NManager.getSingleton().getMessaggio("group"));
		
		aggiorna.setText(I18NManager.getSingleton().getMessaggio("update"));
		inserisci.setText(I18NManager.getSingleton().getMessaggio("inscat"));
		cancella.setText(I18NManager.getSingleton().getMessaggio("delete"));
		
		getFields().getTaDescrizione().setText(I18NManager.getSingleton().getMessaggio("insertherecatdescr"));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		getFields().getTfNome().setText(getcNome());
		getFields().getTaDescrizione().setText(getcDescrizione());
		getFields().getCbImportanza().setSelectedItem(getcImportanza());
		getFields().getCbGruppi().setSelectedItem(getGruppo());
	}

	@Override
	public ComboBoxBase<ICatSpese> getComboCategorie() {
		return getFields().getCbCategorie();
	}

	@Override
	public void aggiornaModelDaVista(String actionCommand) {
		if ("Inserisci".equals(actionCommand)) {
			final int idCategoria = CacheCategorie.getSingleton().getMaxId() + 1;
			getModelCatSpese().setidCategoria(idCategoria);
		} else {
			final int idCategoriaDaCombo = getCategoria().getidCategoria();
			if (idCategoriaDaCombo == 0) {
				final int idCategorie = CacheCategorie.getSingleton().getMaxId() + 1;
				getModelCatSpese().setidCategoria(idCategorie);
			} else {
				getModelCatSpese().setidCategoria(idCategoriaDaCombo);
			}
		}
		final CorreggiTesto checkTestoNome = new CorreggiTesto(getFields().getTfNome().getText());
		setcNome(checkTestoNome.getTesto());

		final CorreggiTesto checkTestoDescrizione = new CorreggiTesto(getFields().getTaDescrizione().getText());
		setcDescrizione(checkTestoDescrizione.getTesto());
		CATEGORYTYPE selectedItem = (CATEGORYTYPE) getFields().getCbImportanza().getSelectedItem();
		setcImportanza(Integer.toString(selectedItem.ordinal()));
		setGruppo((IGruppi) getFields().getCbGruppi().getSelectedItem());
		
	}

	@Override
	public ICatSpese getCategoria() {
		return getFields().getCategoria();
	}
	
	public FieldsCategories getFields() {
		return fields;
	}

	public void setFields(FieldsCategories fields) {
		this.fields = fields;
	}

	public PannelloBase getPan() {
		return pan;
	}

}
