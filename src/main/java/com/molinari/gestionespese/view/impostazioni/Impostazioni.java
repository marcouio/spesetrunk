package com.molinari.gestionespese.view.impostazioni;

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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheLookAndFeel;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.ILookandfeel;
import com.molinari.gestionespese.domain.Lookandfeel;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.Model;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.TextFieldF;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreLanguage;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreLook;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.alert.DialogoBase;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.xml.CoreXMLManager;

public class Impostazioni extends DialogoBase {

	private static final long serialVersionUID = 1L;
	private static Impostazioni singleton;
	private static String posDatabase = "";

	private JTextField dataOdierna;
	private JTextField utente;
	private ArrayList<String> listaLook;
	private JComboBox<ILookandfeel> comboLook;
	private TextFieldF annotextField;
	private LabelListaGruppi calendario;
	private LabelListaGruppi lblImpostaAnno;
	private LabelListaGruppi lblCaricaDatabase;
	private ButtonF btnCarica;
	private ButtonF btnChange;
	private ButtonF elimina;
	private LabelListaGruppi lbltstEliminaTuttiLe;
	private LabelListaGruppi lbltstUtente;
	private JLabel lblLang;
	private static int anno = new GregorianCalendar().get(Calendar.YEAR);
	private static JTextField caricaDatabase;

	public Impostazioni(JFrame container) {
		super(container);
		initGUI();
	}

	public static final synchronized Impostazioni getSingleton() {
		if (singleton == null) {
			singleton = new Impostazioni(ControlloreBase.getApplicationframe());
		}
		return singleton;
	} // getSingleton()


	private void initGUI() {
		try {
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			this.setTitle("Setting");
			this.setPreferredSize(new Dimension(626, 280));
			getContentPane().setLayout(null);

			calendario = new LabelListaGruppi();
			calendario.setBounds(22, 86, 87, 14);
			dataOdierna = new TextFieldF();
			dataOdierna.setBounds(140, 82, 113, 27);
			dataOdierna.setEditable(false);
			final GregorianCalendar gc = new GregorianCalendar();
			dataOdierna.setText(DBUtil.dataToString(gc.getTime(), "dd-MM-yyyy"));
			getContentPane().add(dataOdierna);
			getContentPane().add(calendario);

			utente = new TextFieldF();
			utente.setBounds(140, 126, 113, 27);
			getContentPane().add(utente);

			lblImpostaAnno = new LabelListaGruppi();
			lblImpostaAnno.setBounds(278, 79, 97, 27);
			getContentPane().add(lblImpostaAnno);

			btnChange = new ButtonF();
			btnChange.setBounds(504, 78, 91, 27);
			btnChange.addActionListener(getListenerChange());
			getContentPane().add(btnChange);

			lblCaricaDatabase = new LabelListaGruppi();
			lblCaricaDatabase.setBounds(22, 183, 113, 14);
			getContentPane().add(lblCaricaDatabase);

			btnCarica = new ButtonF();
			btnCarica.setBounds(333, 179, 91, 27);
			getContentPane().add(btnCarica);
			btnCarica.addActionListener(getListenerCharge());

			caricaDatabase = new TextFieldF();
			caricaDatabase.setBounds(140, 179, 149, 27);
			getContentPane().add(caricaDatabase);

			final ButtonF button = new ButtonF();
			button.setText("...");
			button.setBounds(287, 179, 29, 27);
			getContentPane().add(button);

			elimina = new ButtonF();
			elimina.addActionListener(getListenerDelete());

			elimina.setBounds(504, 125, 91, 27);
			getContentPane().add(elimina);

			final JDesktopPane desktopPane = new JDesktopPane();
			desktopPane.setBounds(94, 138, 1, 1);
			getContentPane().add(desktopPane);

			lbltstEliminaTuttiLe = new LabelListaGruppi();
			lbltstEliminaTuttiLe.setBounds(278, 126, 232, 27);
			getContentPane().add(lbltstEliminaTuttiLe);

			lbltstUtente = new LabelListaGruppi();
			lbltstUtente.setBounds(22, 130, 87, 14);
			getContentPane().add(lbltstUtente);

			annotextField = new TextFieldF();
			annotextField.setText(Integer.toString(gc.get(Calendar.YEAR)));
			annotextField.setBounds(375, 78, 113, 27);
			getContentPane().add(annotextField);

			final CacheLookAndFeel cacheLook = CacheLookAndFeel.getSingleton();
			final List<ILookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();

			ILookandfeel look;
			comboLook = new JComboBox<>(new Vector<>(vettore));
			final Lookandfeel system = new Lookandfeel();
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

			lblLang = new JLabel();
			lblLang.setBounds(278, 29, 113, 15);
			getContentPane().add(lblLang);

			final String[] languages = new String[] { "it", "en" };
			final JComboBox<String> comboLanguage = new JComboBox<>(languages);

			comboLanguage.addActionListener(new AscoltatoreLanguage(comboLanguage));
			comboLanguage.setBounds(396, 24, 115, 24);

			for (int i = 0; i < languages.length; i++) {
				final String lingua = CoreXMLManager.getSingleton().getLanguage();
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

			updateText();
			
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void updateText() {
		final Utenti utenteLogin = (Utenti) Controllore.getUtenteLogin();
		lblLang.setText(I18NManager.getSingleton().getMessaggio("lang"));
		calendario.setText(I18NManager.getSingleton().getMessaggio("todaydate"));
		utente.setText(utenteLogin != null ? utenteLogin.getusername() : null);
		lblImpostaAnno.setText(I18NManager.getSingleton().getMessaggio("setyear"));
		btnChange.setText(I18NManager.getSingleton().getMessaggio("change"));
		lblCaricaDatabase.setText(I18NManager.getSingleton().getMessaggio("loaddb"));
		btnCarica.setText(I18NManager.getSingleton().getMessaggio("load"));
		elimina.setText(I18NManager.getSingleton().getMessaggio("delete"));
		lbltstEliminaTuttiLe.setText(I18NManager.getSingleton().getMessaggio("deleteAll"));
		lbltstUtente.setText(I18NManager.getSingleton().getMessaggio("user"));
		update(getGraphics());
	}

	private AscoltatoreAggiornatoreTutto getListenerDelete() {
		return new AscoltatoreAggiornatoreTutto() {
			@Override
			public void actionPerformedOverride(final ActionEvent arg0) {
				if (Model.getSingleton().getModelEntrate().deleteAll()
						&& Model.getSingleton().getModelUscita().deleteAll()) {
					// creare comando per eliminare tutto
					Alert.segnalazioneInfo(I18NManager.getSingleton().getMessaggio("okdeleteall"));
					try {
						AggiornatoreManager.aggiornamentoGenerale(Entrate.NOME_TABELLA);
						AggiornatoreManager.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
					} catch (final Exception e) {
						ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
					}
				}
			}
		};
	}

	private AscoltatoreAggiornatoreTutto getListenerCharge() {
		return new AscoltatoreAggiornatoreTutto() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				AggiornatoreManager.aggiornamentoPerImpostazioni();
			}
		};
	}

	private AscoltatoreAggiornatoreTutto getListenerChange() {
		return new AscoltatoreAggiornatoreTutto() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					anno = Integer.parseInt(annotextField.getText());
				} catch (final Exception e1) {
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
				}
			}
		};
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
	public List<String> getListaLook() {
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
	public JComboBox<ILookandfeel> getComboLook() {
		return comboLook;
	}

	/**
	 * @param comboLook
	 *            the comboLook to set
	 */
	public void setComboLook(final JComboBox<ILookandfeel> comboLook) {
		this.comboLook = comboLook;
	}

	public static String getPosDatabase() {
		return posDatabase;
	}

	public static void setPosDatabase(final String posDatabase) {
		Impostazioni.posDatabase = posDatabase;
	}
}
