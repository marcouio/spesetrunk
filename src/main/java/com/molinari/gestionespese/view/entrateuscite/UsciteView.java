package com.molinari.gestionespese.view.entrateuscite;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.CorreggiTesto;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreUscite;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.business.comandi.singlespese.CommandDeleteSpesa;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.TextAreaF;
import com.molinari.gestionespese.view.font.TextFieldF;

import grafica.componenti.alert.Alert;

public class UsciteView extends AbstractUsciteView {

	private final TextFieldF  tfNome;
	private final TextFieldF  tfData;
	private final TextFieldF  tfEuro;
	private final TextAreaF   taDescrizione;
	private static JComboBox<ICatSpese>  cCategorie;

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

		taDescrizione = new TextAreaF();
		taDescrizione.setText(I18NManager.getSingleton().getMessaggio("insertheredescr"));
		taDescrizione.setBounds(13, 87, 318, 75);
		taDescrizione.setLineWrap(true);
		taDescrizione.setWrapStyleWord(true);
		taDescrizione.setAutoscrolls(true);
		getDialog().getContentPane().add(taDescrizione);

		final TextAreaF descCateg = new TextAreaF();
		descCateg.setText(I18NManager.getSingleton().getMessaggio("heredesc"));
		descCateg.setBounds(13, 242, 318, 75);
		descCateg.setLineWrap(true);
		descCateg.setWrapStyleWord(true);
		descCateg.setAutoscrolls(true);
		getDialog().getContentPane().add(descCateg);

		tfNome = new TextFieldF();
		tfNome.setBounds(12, 38, 150, 27);
		getDialog().getContentPane().add(tfNome);
		tfNome.setColumns(10);

		final List<ICatSpese> listCategoriePerCombo = CacheCategorie.getSingleton().getListCategoriePerCombo();
		cCategorie = new JComboBox<>(new Vector<>(listCategoriePerCombo));
		cCategorie.setBounds(181, 38, 150, 27);
		getDialog().getContentPane().add(cCategorie);

		cCategorie.addItemListener(e -> {
			CatSpese spese = null;
			if (cCategorie.getSelectedIndex() != 0) {
				spese = (CatSpese) cCategorie.getSelectedItem();
				// il campo sotto serve per inserire la descrizione nel
				// caso
				// si selezioni
				// una categoria e si vogliono maggiori info

				descCateg.setText(spese != null ? spese.getdescrizione() : "");
			}

		});

		tfData = new TextFieldF("0.0");
		tfData.setColumns(10);
		final GregorianCalendar gc = new GregorianCalendar();
		tfData.setText(DBUtil.dataToString(gc.getTime(), "yyyy/MM/dd"));
		tfData.setBounds(13, 189, 150, 27);
		getDialog().getContentPane().add(tfData);

		tfEuro = new TextFieldF();
		tfEuro.setColumns(10);
		tfEuro.setBounds(184, 189, 150, 27);
		getDialog().getContentPane().add(tfEuro);

		// Bottone Elimina
		final ButtonF eliminaUltima = new ButtonF();
		eliminaUltima.addActionListener(new AscoltatoreAggiornatoreUscite() {

			@Override
			protected void actionPerformedOverride(final ActionEvent e) {
				super.actionPerformedOverride(e);
				try {
					Controllore.invocaComando(new CommandDeleteSpesa(getModelUscita()));
				} catch (final Exception e1) {
					Alert.segnalazioneEccezione(e1,"Cancellazione della spesa " + getModelUscita().getNome() + " non riuscita: " + e1.getMessage());
				}
			}
		});

		eliminaUltima.setText("Elimina Ultima");
		eliminaUltima.setBounds(184, 325, 147, 27);
		getDialog().getContentPane().add(eliminaUltima);

		final ButtonF inserisci = new ButtonF();
		inserisci.setText("Inserisci");
		inserisci.setBounds(13, 325, 150, 27);
		getDialog().getContentPane().add(inserisci);

		inserisci.addActionListener(new AscoltaInserisciUscite(this));

	}

	private void initLabel() {
		final LabelListaGruppi lblNomeSpesa = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("name"));
		lblNomeSpesa.setBounds(13, 12, 118, 27);
		getDialog().getContentPane().add(lblNomeSpesa);

		final LabelListaGruppi lblEuro = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("eur"));
		lblEuro.setBounds(184, 163, 77, 27);
		getDialog().getContentPane().add(lblEuro);

		final LabelListaGruppi lblCategorie = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("categories"));
		lblCategorie.setBounds(181, 12, 125, 27);
		getDialog().getContentPane().add(lblCategorie);

		final LabelListaGruppi lblData = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("date"));
		lblData.setBounds(13, 163, 77, 27);
		getDialog().getContentPane().add(lblData);

		final LabelListaGruppi lblDescrizione = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("descr"));
		lblDescrizione.setBounds(14, 62, 212, 25);
		getDialog().getContentPane().add(lblDescrizione);

		final LabelListaGruppi lblDescrizione1 = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("descr")+" "+I18NManager.getSingleton().getMessaggio("category"));
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

		setCategoria((CatSpese) cCategorie.getSelectedItem());
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
		setUtenti((Utenti) Controllore.getSingleton().getUtenteLogin());
		setDataIns(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
	}

	/**
	 * @return the categorie
	 */
	public static JComboBox<ICatSpese> getComboCategorie() {
		return cCategorie;
	}

	/**
	 * @param categorie
	 *            the categorie to set
	 */
	public static void setComboCategorie(final JComboBox<ICatSpese> categorie) {
		UsciteView.cCategorie = categorie;
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
