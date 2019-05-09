package com.molinari.gestionespese.view.entrateuscite;

import java.awt.Dialog.ModalityType;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.text.CorreggiTesto;

public class UsciteView extends AbstractUsciteView {

	private TextFieldBase  tfNome;
	private TextFieldBase  tfData;
	private TextFieldBase  tfEuro;
	private TextAreaBase   taDescrizione;
	private JComboBox<ICatSpese>  cCategorie;

	public UsciteView(){
		super(new WrapSingleSpesa());
	}
	
	/**
	 * Create the panel.
	 */
	public UsciteView(final WrapSingleSpesa spesa) {
		super(spesa);
		getDialog().setTitle(I18NManager.getSingleton().getMessaggio("insertcharge"));
		getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
		getDialog().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getModelUscita().addObserver(this);
		getDialog().getContentPane().setLayout(null);

		initLabel();

		taDescrizione = new TextAreaBase(getDialog().getContentPane());
		taDescrizione.setText(I18NManager.getSingleton().getMessaggio("insertheredescr"));
		taDescrizione.setBounds(13, 87, 318, 75);
		taDescrizione.setLineWrap(true);
		taDescrizione.setWrapStyleWord(true);
		taDescrizione.setAutoscrolls(true);
		getDialog().getContentPane().add(taDescrizione);

		final TextAreaBase descCateg = new TextAreaBase(getDialog().getContentPane());
		descCateg.setText(I18NManager.getSingleton().getMessaggio("heredesc"));
		descCateg.setBounds(13, 242, 318, 75);
		descCateg.setLineWrap(true);
		descCateg.setWrapStyleWord(true);
		descCateg.setAutoscrolls(true);
		getDialog().getContentPane().add(descCateg);

		tfNome = new TextFieldBase(getDialog().getContentPane());
		tfNome.setBounds(12, 38, 150, 27);
		getDialog().getContentPane().add(tfNome);
		tfNome.setColumns(10);

		final List<ICatSpese> listCategoriePerCombo = CacheCategorie.getSingleton().getListCategoriePerCombo();
		cCategorie = new JComboBox<>(new Vector<>(listCategoriePerCombo));
		cCategorie.setBounds(181, 38, 150, 27);
		getDialog().getContentPane().add(cCategorie);

		cCategorie.addItemListener(e -> {
			ICatSpese spese = null;
			if (cCategorie.getSelectedIndex() != 0) {
				spese = (ICatSpese) cCategorie.getSelectedItem();
				// il campo sotto serve per inserire la descrizione nel
				// caso
				// si selezioni
				// una categoria e si vogliono maggiori info

				descCateg.setText(spese != null ? spese.getdescrizione() : "");
			}

		});

		tfData = new TextFieldBase("0.0", getDialog().getContentPane());
		tfData.setColumns(10);
		final GregorianCalendar gc = new GregorianCalendar();
		tfData.setText(DBUtil.dataToString(gc.getTime(), "yyyy/MM/dd"));
		tfData.setBounds(13, 189, 150, 27);
		getDialog().getContentPane().add(tfData);

		tfEuro = new TextFieldBase(getDialog().getContentPane());
		tfEuro.setColumns(10);
		tfEuro.setBounds(184, 189, 150, 27);
		getDialog().getContentPane().add(tfEuro);

		// Bottone Elimina
		final ButtonBase eliminaUltima = new ButtonBase(getDialog().getContentPane());
		eliminaUltima.addActionListener(new AscoltaEliminaUltimaSpesa(this));

		eliminaUltima.setText("Elimina Ultima");
		eliminaUltima.setBounds(184, 325, 147, 27);
		getDialog().getContentPane().add(eliminaUltima);

		final ButtonBase inserisci = new ButtonBase(getDialog().getContentPane());
		inserisci.setText("Inserisci");
		inserisci.setBounds(13, 325, 150, 27);
		getDialog().getContentPane().add(inserisci);

		inserisci.addActionListener(new AscoltaInserisciUscite(this));

	}

	private void initLabel() {
		final LabelTestoPiccolo lblNomeSpesa = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("name"), getDialog().getContentPane());
		lblNomeSpesa.setBounds(13, 12, 118, 27);
		getDialog().getContentPane().add(lblNomeSpesa);

		final LabelTestoPiccolo lblEuro = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("eur"), getDialog().getContentPane());
		lblEuro.setBounds(184, 163, 77, 27);
		getDialog().getContentPane().add(lblEuro);

		final LabelTestoPiccolo lblCategorie = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("categories"), getDialog().getContentPane());
		lblCategorie.setBounds(181, 12, 125, 27);
		getDialog().getContentPane().add(lblCategorie);

		final LabelTestoPiccolo lblData = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("date"), getDialog().getContentPane());
		lblData.setBounds(13, 163, 77, 27);
		getDialog().getContentPane().add(lblData);

		final LabelTestoPiccolo lblDescrizione = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("descr"), getDialog().getContentPane());
		lblDescrizione.setBounds(14, 62, 212, 25);
		getDialog().getContentPane().add(lblDescrizione);

		final LabelTestoPiccolo lblDescrizione1 = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("descr")+" "+I18NManager.getSingleton().getMessaggio("category"), getDialog().getContentPane());
		lblDescrizione1.setBounds(13, 216, 232, 27);
		getDialog().getContentPane().add(lblDescrizione1);
	}

	public void aggiornaModelDaVista() {
		final int idSpesa = CacheUscite.getSingleton().getMaxId() + 1;
		getModelUscita().setidSpesa(idSpesa);

		final CorreggiTesto checkTesto = new CorreggiTesto(tfNome.getText());
		final String nomeCheckato = checkTesto.getTesto();
		setcNome(nomeCheckato);

		checkTesto.setTesto(taDescrizione.getText());
		final String descrizioneCheckato = checkTesto.getTesto();
		setcDescrizione(descrizioneCheckato);

		setCategoria((ICatSpese) cCategorie.getSelectedItem());
		if (AltreUtil.checkData(tfData.getText())) {
			setcData(tfData.getText());
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("datainformat");
			Alert.segnalazioneErroreGrave(messaggio);
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			final Double euro = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("valorenotcorrect");
			Alert.segnalazioneErroreGrave(messaggio);
		}
		setUtenti((Utenti) Controllore.getUtenteLogin());
		setDataIns(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
	}

	/**
	 * @return the categorie
	 */
	public JComboBox<ICatSpese> getComboCategorie() {
		return cCategorie;
	}

	/**
	 * @param categorie
	 *            the categorie to set
	 */
	public void setComboCategorie(final JComboBox<ICatSpese> categorie) {
		cCategorie = categorie;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		tfNome.setText(getcNome());
		taDescrizione.setText(getcDescrizione());
		cCategorie.setSelectedItem(getCategoria());
		tfData.setText(getcData());
		tfEuro.setText(getdEuro().toString());
	}
}
