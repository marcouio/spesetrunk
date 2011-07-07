package view.impostazioni;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.Alert;
import view.font.ButtonF;
import view.font.LabelListaGruppi;
import view.font.TextFieldF;
import business.Controllore;
import business.DBUtil;
import business.Database;
import business.cache.CacheLookAndFeel;
import domain.Entrate;
import domain.Lookandfeel;
import domain.SingleSpesa;
import domain.wrapper.Model;

public class Impostazioni extends JDialog {

	private static final long serialVersionUID = 1L;
	private static Impostazioni singleton;
	private static String posDatabase = "";

	public static void main(final String[] args) {
		final Impostazioni dialog = new Impostazioni();
		dialog.pack();
		dialog.setVisible(true);
	}

	public static final Impostazioni getSingleton() {
		if (singleton == null) {
			synchronized (Impostazioni.class) {
				if (singleton == null) {
					singleton = new Impostazioni();
				}
			} // if
		} // if
		return singleton;
	} // getSingleton()

	private JTextField dataOdierna;
	private JTextField utente;
	private ArrayList<String> listaLook;
	private JComboBox comboLook;
	private TextFieldF annotextField;
	private static int anno = new GregorianCalendar().get(Calendar.YEAR);
	private static JTextField caricaDatabase;

	public Impostazioni() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			this.setTitle("Setting");
			this.setPreferredSize(new Dimension(626, 250));
			final JLabel calendario = new LabelListaGruppi("Data Odierna");
			calendario.setBounds(22, 86, 87, 14);
			dataOdierna = new TextFieldF();
			dataOdierna.setBounds(140, 82, 113, 27);
			dataOdierna.setEditable(false);

			final GregorianCalendar gc = new GregorianCalendar();
			dataOdierna.setText(DBUtil.dataToString(gc.getTime(), "dd-MM-yyyy"));
			getContentPane().setLayout(null);
			utente = new TextFieldF();
			utente.setText(Controllore.getSingleton().getUtenteLogin().getusername());
			utente.setBounds(140, 126, 113, 27);
			getContentPane().add(calendario);
			getContentPane().add(dataOdierna);
			getContentPane().add(utente);

			final JLabel lblImpostaAnno = new LabelListaGruppi("Imposta anno");
			lblImpostaAnno.setBounds(278, 79, 97, 27);
			getContentPane().add(lblImpostaAnno);

			final ButtonF btnChange = new ButtonF();
			btnChange.setText("Cambia");
			btnChange.setBounds(504, 78, 91, 27);
			btnChange.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						anno = Integer.parseInt(annotextField.getText());
						Database.aggiornamentoPerImpostazioni();
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			getContentPane().add(btnChange);

			final JLabel lblCaricaDatabase = new LabelListaGruppi("Carica Database");
			lblCaricaDatabase.setBounds(22, 183, 113, 14);
			getContentPane().add(lblCaricaDatabase);

			final ButtonF btnCarica = new ButtonF();
			btnCarica.setText("Carica");
			btnCarica.setBounds(333, 179, 91, 27);
			getContentPane().add(btnCarica);
			btnCarica.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					Database.aggiornamentoPerImpostazioni();
				}
			});

			caricaDatabase = new TextFieldF();
			caricaDatabase.setBounds(140, 179, 149, 27);
			caricaDatabase.setText(posDatabase);
			getContentPane().add(caricaDatabase);

			final ButtonF button = new ButtonF();
			button.setText("...");
			button.setBounds(287, 179, 29, 27);
			getContentPane().add(button);

			final ButtonF elimina = new ButtonF();
			elimina.setText("Elimina");
			elimina.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent arg0) {
					if (Model.getSingleton().getModelEntrate().deleteAll() && Model.getSingleton().getModelUscita().deleteAll()) {
						Alert.operazioniSegnalazioneInfo("Ok, tutti i dati sono stati cancellati: puoi ripartire!");
						try {
							Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
							Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
						} catch (final Exception e) {
							e.getMessage();
						}
					}
				}
			});

			elimina.setBounds(504, 125, 91, 27);
			getContentPane().add(elimina);

			final JDesktopPane desktopPane = new JDesktopPane();
			desktopPane.setBounds(94, 138, 1, 1);
			getContentPane().add(desktopPane);

			final LabelListaGruppi lbltstEliminaTuttiLe = new LabelListaGruppi("Carica Database");
			lbltstEliminaTuttiLe.setText("Elimina dati per entrate e uscite");
			lbltstEliminaTuttiLe.setBounds(278, 126, 232, 27);
			getContentPane().add(lbltstEliminaTuttiLe);

			final LabelListaGruppi lbltstUtente = new LabelListaGruppi("Data Odierna");
			lbltstUtente.setText("Utente");
			lbltstUtente.setBounds(22, 130, 87, 14);
			getContentPane().add(lbltstUtente);

			annotextField = new TextFieldF();
			annotextField.setText(Integer.toString(gc.get(Calendar.YEAR)));
			annotextField.setBounds(375, 78, 113, 27);
			getContentPane().add(annotextField);

			final CacheLookAndFeel cacheLook = CacheLookAndFeel.getSingleton();
			final Vector<Lookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();

			Lookandfeel look = null;
			comboLook = new JComboBox(vettore);
			for (int i = 0; i < vettore.size(); i++) {
				look = vettore.get(i);
				if (look.getusato() == 1) {
					comboLook.setSelectedIndex(i);
				}
			}

			comboLook.setBounds(140, 24, 115, 24);
			getContentPane().add(comboLook);
			comboLook.addActionListener(new AscoltatoreLook(comboLook, vettore));

			final JLabel labelLook = new JLabel("Look");
			labelLook.setBounds(22, 29, 70, 15);
			getContentPane().add(labelLook);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent arg0) {
					final JFileChooser fileopen = new JFileChooser();
					final FileFilter filter = new FileNameExtensionFilter("db files", "db", "sqlite");
					fileopen.addChoosableFileFilter(filter);

					final int ret = fileopen.showDialog(null, "Open file");

					if (ret == JFileChooser.APPROVE_OPTION) {
						final File file = fileopen.getSelectedFile();
						posDatabase = file.getAbsolutePath();
						caricaDatabase.setText(posDatabase);
					}

				}
			});

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public TextFieldF getAnnotextField() {
		return annotextField;
	}

	public void setAnnotextField(final TextFieldF annotextField) {
		this.annotextField = annotextField;
	}

	/**
	 * @return the dataOdierna
	 */
	public JTextField getDataOdierna() {
		return dataOdierna;
	}

	/**
	 * @param dataOdierna
	 *            the dataOdierna to set
	 */
	public void setDataOdierna(final JTextField dataOdierna) {
		this.dataOdierna = dataOdierna;
	}

	/**
	 * @return the listaLook
	 */
	public ArrayList<String> getListaLook() {
		return listaLook;
	}

	/**
	 * @param listaLook
	 *            the listaLook to set
	 */
	public void setListaLook(final ArrayList<String> listaLook) {
		this.listaLook = listaLook;
	}

	public static int getAnno() {
		return anno;
	}

	public static void setAnno(final int anno) {
		Impostazioni.anno = anno;
	}

	public static JTextField getCaricaDatabase() {
		return caricaDatabase;
	}

	public static void setCaricaDatabase(final JTextField caricaDatabase) {
		Impostazioni.caricaDatabase = caricaDatabase;
	}

	/**
	 * @return the utente
	 */
	public JTextField getUtente() {
		return utente;
	}

	/**
	 * @param utente
	 *            the utente to set
	 */
	public void setUtente(final JTextField utente) {
		this.utente = utente;
	}

	/**
	 * @return the comboLook
	 */
	public JComboBox getComboLook() {
		return comboLook;
	}

	/**
	 * @param comboLook
	 *            the comboLook to set
	 */
	public void setComboLook(final JComboBox comboLook) {
		this.comboLook = comboLook;
	}

	public static String getPosDatabase() {
		return posDatabase;
	}

	public static void setPosDatabase(final String posDatabase) {
		Impostazioni.posDatabase = posDatabase;
	}
}
