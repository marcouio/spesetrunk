package view.impostazioni;

import grafica.componenti.alert.Alert;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.font.ButtonF;
import view.font.LabelListaGruppi;
import view.font.TextFieldF;
import view.impostazioni.ascoltatori.AscoltatoreLanguage;
import view.impostazioni.ascoltatori.AscoltatoreLook;
import business.Controllore;
import business.DBUtil;
import business.aggiornatori.AggiornatoreManager;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.cache.CacheLookAndFeel;
import business.config.ConfiguratoreXml;
import domain.Entrate;
import domain.Lookandfeel;
import domain.SingleSpesa;
import domain.Utenti;
import domain.wrapper.Model;

public class Impostazioni extends JDialog {

	private static final long serialVersionUID = 1L;
	private static Impostazioni singleton;
	private static String posDatabase = "";

	private JTextField dataOdierna;
	private JTextField utente;
	private ArrayList<String> listaLook;
	private JComboBox<Lookandfeel> comboLook;
	private TextFieldF annotextField;
	private static int anno = new GregorianCalendar().get(Calendar.YEAR);
	private static JTextField caricaDatabase;

	public Impostazioni() {
		super();
		initGUI();
	}
	
	public static synchronized final Impostazioni getSingleton() {
		if (singleton == null) {
			singleton = new Impostazioni();
		}
		return singleton;
	} // getSingleton()
	

	private void initGUI() {
		try {
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			this.setTitle("Setting");
			this.setPreferredSize(new Dimension(626, 250));
			getContentPane().setLayout(null);

			final JLabel calendario = new LabelListaGruppi("Data Odierna");
			calendario.setBounds(22, 86, 87, 14);
			dataOdierna = new TextFieldF();
			dataOdierna.setBounds(140, 82, 113, 27);
			dataOdierna.setEditable(false);
			final GregorianCalendar gc = new GregorianCalendar();
			dataOdierna.setText(DBUtil.dataToString(gc.getTime(), "dd-MM-yyyy"));
			getContentPane().add(dataOdierna);
			getContentPane().add(calendario);

			utente = new TextFieldF();
			Utenti utenteLogin = (Utenti) Controllore.getSingleton().getUtenteLogin();
			utente.setText(utenteLogin != null ? utenteLogin.getusername() : null);
			utente.setBounds(140, 126, 113, 27);
			getContentPane().add(utente);

			final JLabel lblImpostaAnno = new LabelListaGruppi("Imposta anno");
			lblImpostaAnno.setBounds(278, 79, 97, 27);
			getContentPane().add(lblImpostaAnno);

			final ButtonF btnChange = new ButtonF();
			btnChange.setText("Cambia");
			btnChange.setBounds(504, 78, 91, 27);
			btnChange.addActionListener(new AscoltatoreAggiornatoreTutto() {

				@Override
				public void actionPerformedOverride(final ActionEvent e) {
					try {
						anno = Integer.parseInt(annotextField.getText());
					} catch (final Exception e1) {
						Controllore.getLog().log(Level.SEVERE, e1.getMessage(), e1);
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
			btnCarica.addActionListener(new AscoltatoreAggiornatoreTutto() {

				@Override
				public void actionPerformedOverride(final ActionEvent e) {
					AggiornatoreManager.aggiornamentoPerImpostazioni();
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
			elimina.addActionListener(new AscoltatoreAggiornatoreTutto() {
				@Override
				public void actionPerformedOverride(final ActionEvent arg0) {
					if (Model.getSingleton().getModelEntrate().deleteAll()
							&& Model.getSingleton().getModelUscita().deleteAll()) {
						// TODO creare comando per eliminare tutto
						Alert.segnalazioneInfo("Ok, tutti i dati sono stati cancellati: puoi ripartire!");
						try {
							AggiornatoreManager.aggiornamentoGenerale(Entrate.NOME_TABELLA);
							AggiornatoreManager.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
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
			final List<Lookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();

			Lookandfeel look = null;
			comboLook = new JComboBox<Lookandfeel>(new Vector<>(vettore));
			Lookandfeel system = new Lookandfeel();
			system.setnome("System");
			system.setvalore(UIManager.getSystemLookAndFeelClassName());
			system.setusato(0);
			comboLook.addItem(system);

			comboLook.setSelectedItem("System");
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

			JLabel lblLang = new JLabel("Language");
			lblLang.setBounds(278, 29, 113, 15);
			getContentPane().add(lblLang);

			Object[] languages = new Object[] { "it", "en" };
			JComboBox comboLanguage = new JComboBox(languages);

			comboLanguage.addActionListener(new AscoltatoreLanguage(comboLanguage));
			comboLanguage.setBounds(396, 24, 115, 24);

			for (int i = 0; i < languages.length; i++) {
				String lingua = ConfiguratoreXml.getSingleton().getLanguage();
				if (languages[i].equals(lingua)) {
					comboLanguage.setSelectedIndex(i);
				}
			}

			getContentPane().add(comboLanguage);

			button.addActionListener(new AscoltatoreAggiornatoreNiente() {

				@Override
				public void actionPerformedOverride(final ActionEvent arg0) {
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
