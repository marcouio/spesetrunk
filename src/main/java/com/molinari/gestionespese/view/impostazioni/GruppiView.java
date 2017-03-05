package com.molinari.gestionespese.view.impostazioni;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.util.List;
import java.util.Observable;

import javax.swing.JComboBox;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.TextAreaF;
import com.molinari.gestionespese.view.font.TextFieldF;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreAggiornaGruppo;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreEliminaGruppo;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreInserisciGruppo;

public class GruppiView extends AbstractGruppiView {

	/**
	 * 
	 */
	private Gruppi gruppi = null;
	private JComboBox<Gruppi> comboGruppi;
	private TextFieldF nome;
	private TextAreaF descrizione;

	public GruppiView(final WrapGruppi gruppo) {
		super(gruppo);
		modelGruppi.addObserver(this);
		initGUI();
	}

	private void initGUI() {

		getDialog().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getDialog().setTitle("Gruppi");
		getDialog().getContentPane().setLayout(null);

		initLabel();
		getDialog().setPreferredSize(new Dimension(260, 405));
		getDialog().setModalityType(ModalityType.APPLICATION_MODAL);

		nome = new TextFieldF();
		nome.setBounds(25, 49, 206, 26);
		getDialog().getContentPane().add(nome);

		descrizione = new TextAreaF("Inserisci la descrizione della spesa", 50, 25);
		descrizione.setWrapStyleWord(true);
		descrizione.setLineWrap(true);
		descrizione.setAutoscrolls(true);
		descrizione.setBounds(25, 103, 206, 88);
		getDialog().getContentPane().add(descrizione);

		final ButtonF inserisci = new ButtonF();
		inserisci.setText("Inserisci");
		inserisci.setBounds(26, 214, 206, 25);
		getDialog().getContentPane().add(inserisci);

		inserisci.addActionListener(new AscoltatoreInserisciGruppo(this));

		final List<Gruppi> vettoreGruppi = CacheGruppi.getSingleton().getVettoreGruppi();
		comboGruppi = new JComboBox<>();
		comboGruppi.addItem(new Gruppi());

		for (int i = 0; i < vettoreGruppi.size(); i++) {
			comboGruppi.addItem(vettoreGruppi.get(i));
		}

		comboGruppi.setBounds(25, 279, 206, 25);
		getDialog().getContentPane().add(comboGruppi);
		comboGruppi.addItemListener(e -> {
			if (comboGruppi.getSelectedIndex() != 0 && comboGruppi.getSelectedItem() != null) {
				gruppi = (Gruppi) comboGruppi.getSelectedItem();
				nome.setText(gruppi.getnome());
				descrizione.setText(gruppi.getdescrizione());
			}
		});

		final ButtonF aggiorna = new ButtonF();
		aggiorna.setText("Aggiorna");
		aggiorna.setBounds(25, 320, 100, 25);
		getDialog().getContentPane().add(aggiorna);

		final ButtonF cancella = new ButtonF();
		cancella.setText("Cancella");
		cancella.setBounds(131, 320, 100, 25);
		getDialog().getContentPane().add(cancella);

		aggiorna.addActionListener(new AscoltatoreAggiornaGruppo(this));

		cancella.addActionListener(new AscoltatoreEliminaGruppo(this));

	}

	@Override
	public void update(final Observable o, final Object arg) {
		nome.setText(getNome());
		descrizione.setText(getDescrizione());

	}

	public boolean nonEsistonoCampiNonValorizzati() {
		return getDescrizione() != null && getNome() != null;
	}

	private void initLabel() {
		final LabelListaGruppi lbltstGruppo = new LabelListaGruppi();
		lbltstGruppo.setText("Gruppo");
		lbltstGruppo.setBounds(25, 24, 100, 25);
		getDialog().getContentPane().add(lbltstGruppo);

		final LabelListaGruppi lbltstListaGruppi = new LabelListaGruppi();
		lbltstListaGruppi.setText("Lista Gruppi");
		lbltstListaGruppi.setBounds(25, 251, 100, 25);
		getDialog().getContentPane().add(lbltstListaGruppi);

		final LabelListaGruppi labelDescrizione = new LabelListaGruppi();
		labelDescrizione.setText("Descrizione");
		labelDescrizione.setBounds(25, 77, 90, 25);
		getDialog().getContentPane().add(labelDescrizione);

	}

	public void setGruppo(final String actionCommand) {
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

		setNome(nome.getText());
		setDescrizione(descrizione.getText());

	}

	public JComboBox<Gruppi> getComboGruppi() {
		return comboGruppi;
	}

	public void setComboGruppi(final JComboBox<Gruppi> comboGruppi) {
		this.comboGruppi = comboGruppi;
	}
}
