package view.entrateuscite;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import view.Alert;
import view.font.ButtonF;
import view.font.LabelListaGruppi;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Controllore;
import business.CorreggiTesto;
import business.DBUtil;
import business.ascoltatori.AscoltatoreAggiornatoreUscite;
import business.cache.CacheCategorie;
import business.cache.CacheUscite;
import business.comandi.singlespese.CommandDeleteSpesa;
import business.internazionalizzazione.I18NManager;
import domain.CatSpese;
import domain.Utenti;
import domain.wrapper.WrapSingleSpesa;

public class UsciteView extends AbstractUsciteView {

	private static final long serialVersionUID = 1L;
	private final TextFieldF  tfNome;
	private final TextFieldF  tfData;
	private final TextFieldF  tfEuro;
	private final TextAreaF   taDescrizione;
	private static JComboBox  cCategorie;

	public static void main(final String[] args) {
		try {
			final UsciteView dialog = new UsciteView(new WrapSingleSpesa());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setBounds(0, 0, 347, 407);
			dialog.setVisible(true);
		} catch (final Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Create the panel.
	 */
	public UsciteView(final WrapSingleSpesa spesa) {
		super(spesa);
		setTitle(I18NManager.getSingleton().getMessaggio("insertcharge"));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		modelUscita.addObserver(this);
		getContentPane().setLayout(null);

		initLabel();

		taDescrizione = new TextAreaF();
		taDescrizione.setText(I18NManager.getSingleton().getMessaggio("insertheredescr"));
		taDescrizione.setBounds(13, 87, 318, 75);
		taDescrizione.setLineWrap(true);
		taDescrizione.setWrapStyleWord(true);
		taDescrizione.setAutoscrolls(true);
		getContentPane().add(taDescrizione);

		final TextAreaF descCateg = new TextAreaF();
		descCateg.setText(I18NManager.getSingleton().getMessaggio("heredesc"));
		descCateg.setBounds(13, 242, 318, 75);
		descCateg.setLineWrap(true);
		descCateg.setWrapStyleWord(true);
		descCateg.setAutoscrolls(true);
		getContentPane().add(descCateg);

		tfNome = new TextFieldF();
		tfNome.setBounds(12, 38, 150, 27);
		getContentPane().add(tfNome);
		tfNome.setColumns(10);

		cCategorie = new JComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo());
		cCategorie.setBounds(181, 38, 150, 27);
		getContentPane().add(cCategorie);

		cCategorie.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent e) {
				CatSpese spese = null;
				if (cCategorie.getSelectedIndex() != 0) {
					spese = (CatSpese) cCategorie.getSelectedItem();
					// int indice = categorie.getSelectedIndex();
					// il campo sotto serve per inserire la descrizione nel
					// caso
					// si selezioni
					// una categoria e si vogliono maggiori info

					descCateg.setText(spese != null ? spese.getdescrizione() : "");
				}

			}
		});

		tfData = new TextFieldF("0.0");
		tfData.setColumns(10);
		final GregorianCalendar gc = new GregorianCalendar();
		tfData.setText(DBUtil.dataToString(gc.getTime(), "yyyy/MM/dd"));
		tfData.setBounds(13, 189, 150, 27);
		getContentPane().add(tfData);

		tfEuro = new TextFieldF();
		tfEuro.setColumns(10);
		tfEuro.setBounds(184, 189, 150, 27);
		getContentPane().add(tfEuro);

		// Bottone Elimina
		final ButtonF eliminaUltima = new ButtonF();
		eliminaUltima.addActionListener(new AscoltatoreAggiornatoreUscite() {

			@Override
			protected void actionPerformedOverride(final ActionEvent e) throws Exception {
				super.actionPerformedOverride(e);
				try {
					Controllore.invocaComando(new CommandDeleteSpesa(modelUscita));
					// TODO verificare se necessario ripristinare l'update
					// update(modelUscita, null);
				} catch (final Exception e1) {
					Alert.operazioniSegnalazioneErroreGrave("Cancellazione della spesa " + modelUscita.getnome() + " non riuscita: " + e1.getMessage());
					e1.printStackTrace();
				}
			}
		});

		eliminaUltima.setText("Elimina Ultima");
		eliminaUltima.setBounds(184, 325, 147, 27);
		getContentPane().add(eliminaUltima);

		final ButtonF inserisci = new ButtonF();
		inserisci.setText("Inserisci");
		inserisci.setBounds(13, 325, 150, 27);
		getContentPane().add(inserisci);

		inserisci.addActionListener(new AscoltaInserisciUscite(this));

	}

	public boolean nonEsistonoCampiNonValorizzati() {
		return getcNome() != null && getcDescrizione() != null && getcData() != null && getDataIns() != null && getCategoria() != null && getdEuro() != 0.0 && getUtenti() != null;
	}

	private void initLabel() {
		final LabelListaGruppi lblNomeSpesa = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("name"));
		lblNomeSpesa.setBounds(13, 12, 118, 27);
		getContentPane().add(lblNomeSpesa);

		final LabelListaGruppi lblEuro = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("eur"));
		lblEuro.setBounds(184, 163, 77, 27);
		getContentPane().add(lblEuro);

		final LabelListaGruppi lblCategorie = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("categories"));
		lblCategorie.setBounds(181, 12, 125, 27);
		getContentPane().add(lblCategorie);

		final LabelListaGruppi lblData = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("date"));
		lblData.setBounds(13, 163, 77, 27);
		getContentPane().add(lblData);

		final LabelListaGruppi lblDescrizione = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("descr"));
		lblDescrizione.setBounds(14, 62, 212, 25);
		getContentPane().add(lblDescrizione);

		final LabelListaGruppi lblDescrizione_1 = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("descr")+" "+I18NManager.getSingleton().getMessaggio("category"));
		lblDescrizione_1.setBounds(13, 216, 232, 27);
		getContentPane().add(lblDescrizione_1);
	}

	public void aggiornaModelDaVista() {
		final int idSpesa = (CacheUscite.getSingleton().getMaxId()) + 1;
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
			Alert.operazioniSegnalazioneErroreGrave(messaggio);
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			final Double euro = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("valorenotcorrect");
			Alert.operazioniSegnalazioneErroreGrave(messaggio);
		}
		setUtenti((Utenti) Controllore.getSingleton().getUtenteLogin());
		setDataIns(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
	}

	/**
	 * @return the categorie
	 */
	public JComboBox getComboCategorie() {
		return cCategorie;
	}

	/**
	 * @param categorie
	 *            the categorie to set
	 */
	public void setComboCategorie(final JComboBox categorie) {
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
