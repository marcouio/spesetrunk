package view.impostazioni;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.WindowConstants;

import view.font.ButtonF;
import view.font.LabelListaGruppi;
import view.font.TextAreaF;
import view.font.TextFieldF;
import view.impostazioni.ascoltatori.AscoltatoreAggiornaGruppo;
import view.impostazioni.ascoltatori.AscoltatoreEliminaGruppo;
import view.impostazioni.ascoltatori.AscoltatoreInserisciGruppo;
import business.cache.CacheGruppi;
import domain.Gruppi;
import domain.wrapper.WrapGruppi;

public class GruppiView extends AbstractGruppiView {

	private Gruppi gruppi = null;
	private JComboBox comboGruppi;
	private TextFieldF nome;
	private TextAreaF descrizione;

	public GruppiView(final WrapGruppi gruppo) {
		super(gruppo);
		modelGruppi.addObserver(this);
		initGUI();
	}

	private void initGUI() {

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Gruppi");
		getContentPane().setLayout(null);

		initLabel();
		this.setPreferredSize(new Dimension(260, 405));
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		nome = new TextFieldF();
		nome.setBounds(25, 49, 206, 26);
		getContentPane().add(nome);

		descrizione = new TextAreaF("Inserisci la descrizione della spesa", 50, 25);
		descrizione.setWrapStyleWord(true);
		descrizione.setLineWrap(true);
		descrizione.setAutoscrolls(true);
		descrizione.setBounds(25, 103, 206, 88);
		getContentPane().add(descrizione);

		final ButtonF inserisci = new ButtonF();
		inserisci.setText("Inserisci");
		inserisci.setBounds(26, 214, 206, 25);
		getContentPane().add(inserisci);

		inserisci.addActionListener(new AscoltatoreInserisciGruppo(this));

		final Vector<Gruppi> vettoreGruppi = CacheGruppi.getSingleton().getVettoreGruppi();
		comboGruppi = new JComboBox();
		comboGruppi.addItem("");

		for (int i = 0; i < vettoreGruppi.size(); i++) {
			comboGruppi.addItem(vettoreGruppi.get(i));
		}

		comboGruppi.setBounds(25, 279, 206, 25);
		getContentPane().add(comboGruppi);
		comboGruppi.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (comboGruppi.getSelectedIndex() != 0 && comboGruppi.getSelectedItem() != null) {
					gruppi = (Gruppi) comboGruppi.getSelectedItem();
					nome.setText(gruppi.getnome());
					descrizione.setText(gruppi.getdescrizione());
				}
			}
		});

		final ButtonF aggiorna = new ButtonF();
		aggiorna.setText("Aggiorna");
		aggiorna.setBounds(25, 320, 100, 25);
		getContentPane().add(aggiorna);

		final ButtonF cancella = new ButtonF();
		cancella.setText("Cancella");
		cancella.setBounds(131, 320, 100, 25);
		getContentPane().add(cancella);

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
		getContentPane().add(lbltstGruppo);

		final LabelListaGruppi lbltstListaGruppi = new LabelListaGruppi();
		lbltstListaGruppi.setText("Lista Gruppi");
		lbltstListaGruppi.setBounds(25, 251, 100, 25);
		getContentPane().add(lbltstListaGruppi);

		final LabelListaGruppi labelDescrizione = new LabelListaGruppi();
		labelDescrizione.setText("Descrizione");
		labelDescrizione.setBounds(25, 77, 90, 25);
		getContentPane().add(labelDescrizione);

	}

	public void setGruppo(final String actionCommand) {
		if (actionCommand.equals("Inserisci")) {
			final int idGruppo = (CacheGruppi.getSingleton().getMaxId()) + 1;
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
				final int idGruppo = (CacheGruppi.getSingleton().getMaxId()) + 1;
				getModelGruppi().setidGruppo(idGruppo);

				// altrimenti gli setto il suo
			} else {
				getModelGruppi().setidGruppo(idGruppoDaCombo);
			}
		}

		setNome(nome.getText());
		setDescrizione(descrizione.getText());

	}

	private static final long serialVersionUID = 1L;

	public static void main(final String[] args) {
		final GruppiView dialog = new GruppiView(new WrapGruppi());
		dialog.pack();
		dialog.setBounds(10, 10, 500, 500);
		dialog.setVisible(true);
	}

	public JComboBox getComboGruppi() {
		return comboGruppi;
	}

	public void setComboGruppi(final JComboBox comboGruppi) {
		this.comboGruppi = comboGruppi;
	}
}
