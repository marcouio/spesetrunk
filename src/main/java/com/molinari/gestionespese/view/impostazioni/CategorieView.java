package com.molinari.gestionespese.view.impostazioni;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Observable;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreAggiornaCategoria;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreCancellaCategoria;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreInserisciCategoria;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.text.CorreggiTesto;

public class CategorieView extends AbstractCategorieView {

	private ICatSpese categoria = null;
	private JTextArea taDescrizione;
	private JComboBox<String> cbImportanza;
	private JTextField tfNome;
	private JComboBox<ICatSpese> cbCategorie;
	private List<ICatSpese> categorieSpesa;
	private JComboBox<IGruppi> cbGruppi;

	public CategorieView(final WrapCatSpese cat) {
		super(cat);

		getModelCatSpese().addObserver(this);
		initGUI();
	}

	private void initGUI() {
		try {

			getDialog().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
			getDialog().setTitle("Categorie");
			this.getDialog().setPreferredSize(new Dimension(260, 556));
			getDialog().setLayout(null);

			initLabel();

			// Nome Spesa
			tfNome = new TextFieldBase(getDialog().getContentPane());
			tfNome.setBounds(26, 37, 206, 26);

			// Descrizione
			taDescrizione = new TextAreaBase(I18NManager.getSingleton().getMessaggio("insertherecatdescr"), 50, 25, getDialog().getContentPane());
			taDescrizione.setLineWrap(true);
			taDescrizione.setWrapStyleWord(true);
			taDescrizione.setBounds(26, 91, 206, 88);

			// importanza Spesa
			cbImportanza = new JComboBox<>();
			cbImportanza.addItem("");
			cbImportanza.addItem("Futili");
			cbImportanza.addItem("Variabili");
			cbImportanza.addItem("Fisse");
			cbImportanza.setBounds(26, 210, 206, 25);

			// bottone invia
			final ButtonBase inserisci = new ButtonBase(getDialog().getContentPane());
			inserisci.setBounds(26, 305, 206, 25);
			inserisci.setText(I18NManager.getSingleton().getMessaggio("inscat"));
			inserisci.setActionCommand("Inserisci Categoria");

			final List<IGruppi> vettoreGruppi = CacheGruppi.getSingleton().getListCategoriePerCombo(CacheGruppi.getSingleton().getAllGruppi());
			// combo gruppi
			cbGruppi = new JComboBox<>();
			for (int i = 0; i < vettoreGruppi.size(); i++) {
				cbGruppi.addItem(vettoreGruppi.get(i));
			}
			cbGruppi.setBounds(26, 265, 206, 25);
			getDialog().getContentPane().add(cbGruppi);

			categorieSpesa = CacheCategorie.getSingleton().getListCategoriePerCombo();
			cbCategorie = new JComboBox<>(new Vector<>(categorieSpesa));
			cbCategorie.setBounds(26, 380, 206, 25);
			cbCategorie.addItemListener(getListener());

			// bottone Update
			final ButtonBase aggiorna = new ButtonBase(getDialog().getContentPane());
			aggiorna.setBounds(26, 421, 100, 25);
			aggiorna.setText(I18NManager.getSingleton().getMessaggio("update"));
			aggiorna.setActionCommand("Aggiorna");
			aggiorna.addActionListener(new AscoltatoreAggiornaCategoria(this));

			// bottone insert
			inserisci.addActionListener(new AscoltatoreInserisciCategoria(this));

			// bottone cancella
			final ButtonBase cancella = new ButtonBase(getDialog().getContentPane());
			cancella.setActionCommand("Cancella");
			cancella.setText(I18NManager.getSingleton().getMessaggio("delete"));
			cancella.setBounds(132, 421, 100, 25);
			cancella.addActionListener(new AscoltatoreCancellaCategoria(this));

			getDialog().getContentPane().add(cancella);
			getDialog().getContentPane().add(inserisci);
			getDialog().getContentPane().add(taDescrizione);
			getDialog().getContentPane().add(cbImportanza);
			getDialog().getContentPane().add(tfNome);
			getDialog().getContentPane().add(cbCategorie);
			getDialog().getContentPane().add(aggiorna);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private ItemListener getListener() {
		return (ItemEvent e) -> {

			if (cbCategorie.getSelectedIndex() != 0 && cbCategorie.getSelectedItem() != null) {
				setFields();

			}
		};
	}

	private void setFields() {
		categoria = (ICatSpese) cbCategorie.getSelectedItem();
		tfNome.setText(categoria.getnome());
		taDescrizione.setText(categoria.getdescrizione());
		CATEGORYTYPE[] values = CATEGORYTYPE.values();
		cbImportanza.setSelectedItem(values[Integer.parseInt(categoria.getimportanza())]);
		final int numeroGruppi = cbGruppi.getModel().getSize();
		boolean trovato = false;
		
		for (int i = 0; i < numeroGruppi; i++) {
			final IGruppi gruppo = cbGruppi.getModel().getElementAt(i);
			boolean groupIsNotNull = gruppo != null && gruppo.getnome()!=null &&  categoria.getGruppi()!=null;
			if (groupIsNotNull && gruppo.getnome().equals(categoria.getGruppi().getnome())) {
				cbGruppi.setSelectedIndex(i);
				trovato = true;
			}
		}
		if(!trovato){
			cbGruppi.setSelectedIndex(0);
		}
	}

	@Override
	public void aggiornaModelDaVista(final String actionCommand) {

		if ("Inserisci".equals(actionCommand)) {
			final int idCategoria = CacheCategorie.getSingleton().getMaxId() + 1;
			getModelCatSpese().setidCategoria(idCategoria);
		} else {
			final int idCategoriaDaCombo = getCategoria().getidCategoria();
			if (idCategoriaDaCombo == 0) {
				final int idCategorie = CacheCategorie.getSingleton().getMaxId() + 1;
				getModelCatSpese().setidCategoria(idCategorie);
			} else {
				getModelCatSpese().setidCategoria(idCategoriaDaCombo);
			}
		}
		final CorreggiTesto checkTestoNome = new CorreggiTesto(tfNome.getText());
		setcNome(checkTestoNome.getTesto());

		final CorreggiTesto checkTestoDescrizione = new CorreggiTesto(taDescrizione.getText());
		setcDescrizione(checkTestoDescrizione.getTesto());
		setcImportanza((String) cbImportanza.getSelectedItem());
		setGruppo((IGruppi) cbGruppi.getSelectedItem());

	}

	public List<ICatSpese> getCategorieSpesa() {
		return categorieSpesa;
	}

	public void setCategorieSpesa(final List<ICatSpese> categorieSpesa) {
		this.categorieSpesa = categorieSpesa;
	}

	/**
	 * @return the comboCategorie
	 */
	@Override
	public JComboBox<ICatSpese> getComboCategorie() {
		return cbCategorie;
	}

	/**
	 * @param comboCategorie
	 *            the comboCategorie to set
	 */
	public void setComboCategorie(final JComboBox<ICatSpese> comboCategorie) {
		this.cbCategorie = comboCategorie;
	}

	public JComboBox<IGruppi> getComboGruppi() {
		return cbGruppi;
	}

	public void setComboGruppi(final JComboBox<IGruppi> comboGruppi) {
		this.cbGruppi = comboGruppi;
	}

	@Override
	public ICatSpese getCategoria() {
		return categoria;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		tfNome.setText(getcNome());
		taDescrizione.setText(getcDescrizione());
		cbImportanza.setSelectedItem(getcImportanza());
		cbGruppi.setSelectedItem(getGruppo());

	}

	private void initLabel() {

		// Label nome
		final JLabel labelNome = new LabelTestoPiccolo(getDialog().getContentPane());
		labelNome.setBounds(26, 12, 100, 25);
		labelNome.setText(I18NManager.getSingleton().getMessaggio("category"));

		// Label descrizione
		final JLabel labelDescrizione = new LabelTestoPiccolo(getDialog().getContentPane());
		labelDescrizione.setBounds(26, 65, 90, 25);
		labelDescrizione.setText(I18NManager.getSingleton().getMessaggio("descr"));

		// Label Importanza
		final JLabel labelCategorie = new LabelTestoPiccolo(getDialog().getContentPane());
		labelCategorie.setBounds(26, 184, 100, 25);
		labelCategorie.setText(I18NManager.getSingleton().getMessaggio("tipology"));

		// Label Combo Categorie
		final JLabel labelComboCategorie = new LabelTestoPiccolo(getDialog().getContentPane());
		labelComboCategorie.setBounds(26, 352, 100, 25);
		labelComboCategorie.setText(I18NManager.getSingleton().getMessaggio("catlist"));

		final LabelTestoPiccolo lbltstGruppo = new LabelTestoPiccolo(getDialog().getContentPane());
		lbltstGruppo.setText(I18NManager.getSingleton().getMessaggio("group"));
		lbltstGruppo.setBounds(26, 239, 100, 25);
		getDialog().getContentPane().add(lbltstGruppo);
		getDialog().getContentPane().add(labelDescrizione);
		getDialog().getContentPane().add(labelCategorie);
		getDialog().getContentPane().add(labelNome);
		getDialog().getContentPane().add(labelComboCategorie);

	}

	@Override
	public boolean updateGui() {
		getDialog().dispose();
		return true;
	}

}