package com.molinari.gestionespese.view.datainsert;

import java.util.List;

import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.view.impostazioni.AbstractCategorieView.CATEGORYTYPE;
import com.molinari.utility.graphic.component.combo.ComboBoxBase;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;

public class FieldsCategories {

	private ICatSpese categoria = null;
	private TextAreaBase taDescrizione;
	private ComboBoxBase<CATEGORYTYPE> cbImportanza;
	private TextFieldBase tfNome;
	private ComboBoxBase<ICatSpese> cbCategorie;
	private List<ICatSpese> categorieSpesa;
	private ComboBoxBase<IGruppi> cbGruppi;
	
	public ICatSpese getCategoria() {
		return categoria;
	}
	public void setCategoria(ICatSpese categoria) {
		this.categoria = categoria;
	}
	public TextAreaBase getTaDescrizione() {
		return taDescrizione;
	}
	public void setTaDescrizione(TextAreaBase taDescrizione) {
		this.taDescrizione = taDescrizione;
	}
	public ComboBoxBase<CATEGORYTYPE> getCbImportanza() {
		return cbImportanza;
	}
	public void setCbImportanza(ComboBoxBase<CATEGORYTYPE> cbImportanza) {
		this.cbImportanza = cbImportanza;
	}
	public TextFieldBase getTfNome() {
		return tfNome;
	}
	public void setTfNome(TextFieldBase tfNome) {
		this.tfNome = tfNome;
	}
	public ComboBoxBase<ICatSpese> getCbCategorie() {
		return cbCategorie;
	}
	public void setCbCategorie(ComboBoxBase<ICatSpese> cbCategorie) {
		this.cbCategorie = cbCategorie;
	}
	public List<ICatSpese> getCategorieSpesa() {
		return categorieSpesa;
	}
	public void setCategorieSpesa(List<ICatSpese> categorieSpesa) {
		this.categorieSpesa = categorieSpesa;
	}
	public ComboBoxBase<IGruppi> getCbGruppi() {
		return cbGruppi;
	}
	public void setCbGruppi(ComboBoxBase<IGruppi> cbGruppi) {
		this.cbGruppi = cbGruppi;
	}
}
