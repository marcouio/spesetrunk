package com.molinari.gestionespese.view.datainsert;

import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.text.TextFieldTesto;

public class FieldsExpense {

	private TextFieldTesto  tfNome;
	private TextFieldTesto  tfData;
	private TextFieldTesto  tfEuro;
	private TextAreaBase   taDescrizione;
	private ComboBoxBase<ICatSpese>  cCategorie;
	public TextFieldTesto getTfNome() {
		return tfNome;
	}
	public void setTfNome(TextFieldTesto tfNome) {
		this.tfNome = tfNome;
	}
	public TextFieldTesto getTfData() {
		return tfData;
	}
	public void setTfData(TextFieldTesto tfData) {
		this.tfData = tfData;
	}
	public TextFieldTesto getTfEuro() {
		return tfEuro;
	}
	public void setTfEuro(TextFieldTesto tfEuro) {
		this.tfEuro = tfEuro;
	}
	public TextAreaBase getTaDescrizione() {
		return taDescrizione;
	}
	public void setTaDescrizione(TextAreaBase taDescrizione) {
		this.taDescrizione = taDescrizione;
	}
	public ComboBoxBase<ICatSpese> getcCategorie() {
		return cCategorie;
	}
	public void setcCategorie(ComboBoxBase<ICatSpese> cCategorie) {
		this.cCategorie = cCategorie;
	}
	}
