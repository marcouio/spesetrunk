package view.impostazioni;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import view.Alert;
import view.font.ButtonF;
import view.font.LabelListaGruppi;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.Controllore;
import business.Database;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import business.comandi.gruppi.CommandInserisciGruppo;
import business.comandi.gruppi.CommandUpdateGruppo;
import domain.Gruppi;
import domain.IGruppi;
import domain.wrapper.Model;
import domain.wrapper.WrapGruppi;

public class GruppiView extends AbstractGruppiView {

	private Gruppi     gruppi = null;
	private JComboBox  comboGruppi;
	private TextFieldF nome;
	private TextAreaF  descrizione;

	public GruppiView(final WrapGruppi gruppo) {
		super(gruppo);
		modelGruppi.addObserver(this);
		initGUI();
	}

	private void initGUI() {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
		inserisci.setText("Inserisci Gruppo");
		inserisci.setBounds(26, 214, 206, 25);
		getContentPane().add(inserisci);

		inserisci.addActionListener(new ActionListener() {

			private Gruppi gruppo1;

			@Override
			public void actionPerformed(final ActionEvent e) {

				setGruppo();
				if (nonEsistonoCampiNonValorizzati()) {

					if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandInserisciGruppo(modelGruppi), "tutto")) {
						gruppo1 = CacheGruppi.getSingleton().getGruppo(Integer.toString(modelGruppi.getidGruppo()));
						if (gruppo1 != null) {
							comboGruppi.addItem(gruppo1);
						}
						final String messaggio = "Gruppo inserito correttamente";
						Alert.info(messaggio, Alert.TITLE_OK);
						Controllore.getLog().info(messaggio);
						modelGruppi.setChanged();
						modelGruppi.notifyObservers();
					}
				} else {
					final String messaggio = "E' necessario riempire tutti i campi";
					Alert.errore(messaggio, Alert.TITLE_ERROR);
					Controllore.getLog().severe(messaggio);
				}
			}

		});

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

		aggiorna.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				final Gruppi oldGruppo = CacheGruppi.getSingleton().getGruppo(Integer.toString(gruppi.getidGruppo()));

				if (comboGruppi.getSelectedItem() != null) {
					setGruppo();
					if (gruppi != null) {
						modelGruppi.setidGruppo(gruppi.getidGruppo());
					}
					try {
						if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandUpdateGruppo(oldGruppo, (IGruppi) modelGruppi.getentitaPadre()), "tutto")) {
							Model.getSingleton().getGruppiCombo(true);
							Database.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
							Alert.operazioniSegnalazioneInfo("Aggiornata correttamente categoria " + modelGruppi.getnome());
							modelGruppi.setChanged();
							modelGruppi.notifyObservers();
						}
					} catch (final Exception e22) {
						e22.printStackTrace();
						Alert.operazioniSegnalazioneErrore("Inserisci i dati correttamente: " + e22.getMessage());
					}
				} else {
					Alert.operazioniSegnalazioneErrore("Impossibile aggiornare un gruppo inesistente!");
				}
			}
		});

		cancella.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (comboGruppi.getSelectedIndex() != 0 && comboGruppi.getSelectedItem() != null && gruppi != null) {
					Model.getSingleton().getModelUscita().delete(gruppi.getidGruppo());
					Alert.operazioniSegnalazioneInfo("Cancellato correttamente gruppo: " + gruppi);
				} else {
					Alert.operazioniSegnalazioneErrore("Impossibile cancellare un gruppo inesistente!");
				}
				comboGruppi.removeItem(gruppi);
				Database.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
			}
		});

	}

	private boolean nonEsistonoCampiNonValorizzati() {
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

	private void setGruppo() {

		final int idGruppo = (CacheGruppi.getSingleton().getMaxId()) + 1;
		getModelGruppi().setidGruppo(idGruppo);
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
