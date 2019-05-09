package com.molinari.gestionespese.view.entrateuscite;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreEntrate;
import com.molinari.gestionespese.business.cache.CacheEntrate;
import com.molinari.gestionespese.business.comandi.entrate.CommandDeleteEntrata;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.text.CorreggiTesto;

public class EntrateView extends AbstractEntrateView {

	private final TextFieldBase         tfNome;
	private final TextAreaBase          taDescrizione;
	private final JComboBox<INCOMETYPE>          cbTipo;
	private final TextFieldBase         tfData;
	private final TextFieldBase         tfEuro;

	/**
	 * Create the panel.
	 */
	public EntrateView(final WrapEntrate entrate) {
		super(entrate);
		getDialog().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
		getDialog().setTitle(I18NManager.getSingleton().getMessaggio("insertentry"));
		getModelEntrate().addObserver(this);
		getDialog().getContentPane().setLayout(null);

		initLabel();

		taDescrizione = new TextAreaBase(I18NManager.getSingleton().getMessaggio("insertheredescrentry"), getDialog().getContentPane());
		taDescrizione.setBounds(13, 89, 318, 75);
		getDialog().getContentPane().add(taDescrizione);

		// specifica se �true� di andare a capo automaticamente a fine riga
		taDescrizione.setLineWrap(true);
		// va a capo con la parola se �true� o col singolo carattere se �false�
		taDescrizione.setWrapStyleWord(true);
		taDescrizione.setAutoscrolls(true);

		tfNome = new TextFieldBase(getDialog().getContentPane());
		tfNome.setBounds(12, 38, 150, 27);
		getDialog().getContentPane().add(tfNome);
		tfNome.setColumns(10);

		// array per Categoria
		final ArrayList<INCOMETYPE> listaCombo = new ArrayList<>();
		listaCombo.add(null);
		INCOMETYPE[] values = INCOMETYPE.values();
		for(int i = 0; i<values.length; i++){
			listaCombo.add(values[i]);
		}

		cbTipo = new JComboBox<>(INCOMETYPE.values());
		cbTipo.setBounds(181, 38, 150, 27);
		getDialog().getContentPane().add(cbTipo);

		final GregorianCalendar gc = new GregorianCalendar();
		tfData = new TextFieldBase(DBUtil.dataToString(gc.getTime(), "yyyy/MM/dd"), getDialog().getContentPane());
		tfData.setColumns(10);
		tfData.setBounds(13, 191, 150, 27);
		getDialog().getContentPane().add(tfData);

		tfEuro = new TextFieldBase("0.0", getDialog().getContentPane());
		tfEuro.setColumns(10);
		tfEuro.setBounds(182, 191, 150, 27);
		getDialog().getContentPane().add(tfEuro);

		final ButtonBase inserisci = new ButtonBase(getDialog().getContentPane());
		inserisci.setText(I18NManager.getSingleton().getMessaggio("insert"));
		inserisci.setBounds(13, 238, 149, 27);
		getDialog().getContentPane().add(inserisci);

		final ButtonBase eliminaUltima = new ButtonBase(getDialog().getContentPane());
		eliminaUltima.setText(I18NManager.getSingleton().getMessaggio("deletelast"));
		eliminaUltima.setBounds(184, 238, 144, 27);
		getDialog().getContentPane().add(eliminaUltima);

		eliminaUltima.addActionListener(new AscoltatoreAggiornatoreEntrate() {

			@Override
			protected void actionPerformedOverride(ActionEvent e) {
				super.actionPerformedOverride(e);

				try {
					aggiornaModelDaVista();
					if (Controllore.invocaComando(new CommandDeleteEntrata(getModelEntrate()))) {
						final String msg = I18NManager.getSingleton().getMessaggio("okentrata")+" " + getModelEntrate().getnome() + " "+ I18NManager.getSingleton().getMessaggio("correctlydeleted");
						Alert.segnalazioneInfo(msg);
					}
				} catch (final Exception e2) {
					ControlloreBase.getLog().log(Level.SEVERE, e2.getMessage(), e2);
					Alert.segnalazioneErroreGrave(e2.getMessage());
				}
			}

		});

		inserisci.addActionListener(new AscoltaInserisciEntrate(this));

	}

	private void initLabel() {
		final LabelTestoPiccolo lblNomeEntrata = new LabelTestoPiccolo("Nome Entrata", getDialog().getContentPane());
		lblNomeEntrata.setText(I18NManager.getSingleton().getMessaggio("name"));
		lblNomeEntrata.setBounds(13, 12, 97, 27);
		getDialog().getContentPane().add(lblNomeEntrata);

		final LabelTestoPiccolo lblEuro = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("eur"), getDialog().getContentPane());
		lblEuro.setBounds(184, 165, 77, 27);
		getDialog().getContentPane().add(lblEuro);

		final LabelTestoPiccolo lblCategorie = new LabelTestoPiccolo("Categorie", getDialog().getContentPane());
		lblCategorie.setText(I18NManager.getSingleton().getMessaggio("type"));
		lblCategorie.setBounds(181, 12, 77, 27);
		getDialog().getContentPane().add(lblCategorie);

		final LabelTestoPiccolo lblData = new LabelTestoPiccolo("Data", getDialog().getContentPane());
		lblData.setBounds(13, 165, 77, 27);
		getDialog().getContentPane().add(lblData);

		final LabelTestoPiccolo lblDescrizione = new LabelTestoPiccolo("Descrizione Spesa", getDialog().getContentPane());
		lblDescrizione.setText(I18NManager.getSingleton().getMessaggio("descr"));
		lblDescrizione.setBounds(14, 64, 123, 25);
		getDialog().getContentPane().add(lblDescrizione);
	}

	/**
	 * @return the lista
	 */
	public static List<INCOMETYPE> getLista() {
		return new ArrayList<>(Arrays.asList(INCOMETYPE.values()));
	}

	@Override
	public void aggiornaModelDaVista() {
		final int idEntrate = CacheEntrate.getSingleton().getMaxId() + 1;
		getModelEntrate().setidEntrate(idEntrate);

		final CorreggiTesto checkTesto = new CorreggiTesto(tfNome.getText());

		final String nome = checkTesto.getTesto();
		setcNome(nome);

		checkTesto.setTesto(taDescrizione.getText());
		final String descri = checkTesto.getTesto();
		setcDescrizione(descri);

		int ordinal = ((INCOMETYPE) cbTipo.getSelectedItem()).ordinal();
		setFisseOVar(Integer.toString(ordinal));
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

	@Override
	public void update(final Observable o, final Object arg) {
		tfNome.setText(getcNome());
		taDescrizione.setText(getcDescrizione());
		cbTipo.setSelectedItem(getFisseOVar());
		tfData.setText(getcData());
		tfEuro.setText(getdEuro().toString());

	}

}