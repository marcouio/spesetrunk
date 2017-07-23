package com.molinari.gestionespese.view.datainsert;

import com.molinari.gestionespese.view.entrateuscite.EntrateView.INCOMETYPE;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.text.TextFieldTesto;

public class FieldsIncome {

	private TextFieldTesto tfNome;
	private TextAreaBase taDescrizione;
	private ComboBoxBase<INCOMETYPE> cbTipo;
	private TextFieldTesto tfData;
	private TextFieldTesto tfEuro;
	
	public TextFieldTesto getTfNome() {
		return tfNome;
	}
	public TextAreaBase getTaDescrizione() {
		return taDescrizione;
	}
	public ComboBoxBase<INCOMETYPE> getCbTipo() {
		return cbTipo;
	}
	public TextFieldTesto getTfData() {
		return tfData;
	}
	public TextFieldTesto getTfEuro() {
		return tfEuro;
	}
	public void setTfNome(TextFieldTesto tfNome) {
		this.tfNome = tfNome;
	}
	public void setTaDescrizione(TextAreaBase taDescrizione) {
		this.taDescrizione = taDescrizione;
	}
	public void setCbTipo(ComboBoxBase<INCOMETYPE> cbTipo) {
		this.cbTipo = cbTipo;
	}
	public void setTfData(TextFieldTesto tfData) {
		this.tfData = tfData;
	}
	public void setTfEuro(TextFieldTesto tfEuro) {
		this.tfEuro = tfEuro;
	}
}
