package com.molinari.gestionespese.view.datainsert;

import java.awt.Container;
import java.util.List;
import java.util.Observable;

import javax.swing.JComboBox;

import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;
import com.molinari.gestionespese.view.impostazioni.AbstractGruppiView;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreAggiornaGruppo;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreEliminaGruppo;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreInserisciGruppo;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelBase;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.text.TextFieldTesto;
import com.molinari.utility.messages.I18NManager;

public class PanelGroups extends AbstractGruppiView implements DataPanelView{

	private static final int HEIGHT_FIELD = 30;
	private static final int HEIGHT_LABEL = 15;
	
	private PannelloBase pan;
	private TextFieldTesto tfNome;
	private LabelBase labName;
	private LabelBase labDesc;
	private TextAreaBase tfDescr;
	private ComboBoxBase<IGruppi> cbGruppi;
	private ButtonBase inserisci;
	private LabelBase lbltstListaGruppi;
	
	private Gruppi gruppi = null;
	private ButtonBase aggiorna;
	private ButtonBase cancella;
	
	public PanelGroups(Container padre) {
		super(new WrapGruppi());
		this.setPan(new PannelloBase(padre));
		this.getPan().setSize(padre.getWidth(), padre.getHeight());
		
		int width = getPan().getWidth() - 20;
		
		labName = new LabelBase(pan);
		labName.setBounds(10, 0, width, HEIGHT_LABEL);
		
		tfNome = new TextFieldTesto(pan);
		tfNome.setSize(width, HEIGHT_FIELD);
		tfNome.posizionaSottoA(labName, 0, 10);
		
		labDesc = new LabelBase(pan);
		labDesc.setSize(width, HEIGHT_LABEL);
		labDesc.posizionaSottoA(tfNome, 0, 15);
		
		tfDescr = new TextAreaBase(pan);
		tfDescr.posizionaSottoA(labDesc, 0, 10);
		tfDescr.setSize(width, 100);

		inserisci = new ButtonBase(pan);
		inserisci.setActionCommand("Inserisci");
		inserisci.posizionaSottoA(tfDescr, 0, 15);
		inserisci.setSize(width, HEIGHT_FIELD);
		
		inserisci.addActionListener(new AscoltatoreInserisciGruppo(this));
		
		lbltstListaGruppi = new LabelBase(pan);
		lbltstListaGruppi.setSize(width, HEIGHT_LABEL);
		lbltstListaGruppi.posizionaSottoA(inserisci, 0, 15);
		
		final List<IGruppi> vettoreGruppi = CacheGruppi.getSingleton().getVettoreGruppi();
		vettoreGruppi.add(0, new Gruppi());
		cbGruppi = new ComboBoxBase<>(pan, vettoreGruppi);
		cbGruppi.setSize(width, HEIGHT_FIELD);
		cbGruppi.posizionaSottoA(lbltstListaGruppi, 0, 10);
		
		cbGruppi.addItemListener(e -> {
			if (cbGruppi.getSelectedIndex() != 0 && cbGruppi.getSelectedItem() != null) {
				gruppi = (Gruppi) cbGruppi.getSelectedItem();
				tfNome.setText(gruppi.getnome());
				tfDescr.setText(gruppi.getdescrizione());
			}
		});
		
		aggiorna = new ButtonBase(pan);
		aggiorna.setActionCommand("Aggiorna");
		aggiorna.setSize(width/2, HEIGHT_FIELD);
		aggiorna.posizionaSottoA(cbGruppi, 0, 15);

		cancella = new ButtonBase(pan);
		cancella.setActionCommand("Cancella");
		cancella.setSize(width/2, HEIGHT_FIELD);
		cancella.posizionaADestraDi(aggiorna, 0, 0);

		aggiorna.addActionListener(new AscoltatoreAggiornaGruppo(this));

		cancella.addActionListener(new AscoltatoreEliminaGruppo(this));
		
		initLabel();
		
	}

	public void initLabel() {
		labName.setText(I18NManager.getSingleton().getMessaggio("groups"));
		labDesc.setText(I18NManager.getSingleton().getMessaggio("descr"));
		lbltstListaGruppi.setText(I18NManager.getSingleton().getMessaggio("grpslist"));
		inserisci.setText(I18NManager.getSingleton().getMessaggio("insert"));
		aggiorna.setText(I18NManager.getSingleton().getMessaggio("update"));
		cancella.setText(I18NManager.getSingleton().getMessaggio("delete"));
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
		tfNome.setText(getNome());
		tfDescr.setText(getDescrizione());
		
	}

	@Override
	public JComboBox<IGruppi> getComboGruppi() {
		return cbGruppi;
	}

	@Override
	public void setGruppo(String actionCommand) {
		if ("Inserisci".equals(actionCommand)) {
			final int idGruppo = CacheGruppi.getSingleton().getMaxId() + 1;
			getModelGruppi().setidGruppo(idGruppo);
		} else {
			int idGruppoDaCombo = 0;
			if (gruppi != null) {
				// prendo l'id del gruppo selezionato dalla combo
				idGruppoDaCombo = gruppi.getidGruppo();
			}
			// se non ha un id gli assegno prendendo il massimo degli id
			// presenti
			if (idGruppoDaCombo == 0) {
				final int idGruppo = CacheGruppi.getSingleton().getMaxId() + 1;
				getModelGruppi().setidGruppo(idGruppo);

				// altrimenti gli setto il suo
			} else {
				getModelGruppi().setidGruppo(idGruppoDaCombo);
			}
		}
	}

	@Override
	public boolean aggiorna() {
		initLabel();
		return true;
	}
}
