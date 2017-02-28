package com.molinari.gestionespese.view.impostazioni;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.CorreggiTesto;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.TextAreaF;
import com.molinari.gestionespese.view.font.TextFieldF;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreAggiornaCategoria;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreCancellaCategoria;
import com.molinari.gestionespese.view.impostazioni.ascoltatori.AscoltatoreInserisciCategoria;

public class CategorieView extends AbstractCategorieView {

	private CatSpese categoria = null;
	private JTextArea taDescrizione;
	private JComboBox<String> cbImportanza;
	private JTextField tfNome;
	private JComboBox<CatSpese> cbCategorie;
	private List<CatSpese> categorieSpesa;
	private JComboBox<Gruppi> cbGruppi;

	private static final long serialVersionUID = 1L;

	public CategorieView(final WrapCatSpese cat) {
		super(cat);

		modelCatSpese.addObserver(this);
		initGUI();
	}
	
	private void initGUI() {
		try {

			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setModalityType(ModalityType.APPLICATION_MODAL);
			setTitle("Categorie");
			this.setPreferredSize(new Dimension(260, 556));
			setLayout(null);

			initLabel();

			// Nome Spesa
			tfNome = new TextFieldF();
			tfNome.setBounds(26, 37, 206, 26);

			// Descrizione
			taDescrizione = new TextAreaF("Inserisci la descrizione della categoria", 50, 25);
			taDescrizione.setLineWrap(true);
			taDescrizione.setWrapStyleWord(true);
			taDescrizione.setBounds(26, 91, 206, 88);

			// importanza Spesa
			cbImportanza = new JComboBox();
			cbImportanza.addItem("");
			cbImportanza.addItem("Futili");
			cbImportanza.addItem("Variabili");
			cbImportanza.addItem("Fisse");
			cbImportanza.setBounds(26, 210, 206, 25);

			// bottone invia
			final ButtonF inserisci = new ButtonF();
			inserisci.setBounds(26, 305, 206, 25);
			inserisci.setText("Inserisci Categoria");

			final List<Gruppi> vettoreGruppi = CacheGruppi.getSingleton().getListCategoriePerCombo(CacheGruppi.getSingleton().getAllGruppi());
			// combo gruppi
			cbGruppi = new JComboBox<Gruppi>();
			for (int i = 0; i < vettoreGruppi.size(); i++) {
				cbGruppi.addItem(vettoreGruppi.get(i));
			}
			cbGruppi.setBounds(26, 265, 206, 25);
			getContentPane().add(cbGruppi);

			categorieSpesa = CacheCategorie.getSingleton().getListCategoriePerCombo(CacheCategorie.getSingleton().getAllCategorie());
			cbCategorie = new JComboBox<>(new Vector<>(categorieSpesa));
			cbCategorie.setBounds(26, 380, 206, 25);
			cbCategorie.addItemListener(e -> {

				if (cbCategorie.getSelectedIndex() != 0 && cbCategorie.getSelectedItem() != null) {
					categoria = (CatSpese) cbCategorie.getSelectedItem();
					tfNome.setText(categoria.getnome());
					taDescrizione.setText(categoria.getdescrizione());
					cbImportanza.setSelectedItem(categoria.getimportanza());
					final int numeroGruppi = cbGruppi.getModel().getSize();
					boolean trovato = false;
					for (int i = 0; i < numeroGruppi; i++) {
						final Gruppi gruppo = (Gruppi) cbGruppi.getModel().getElementAt(i);
						if (gruppo != null && gruppo.getnome()!=null &&  categoria.getGruppi()!=null) {
							if(gruppo.getnome().equals(categoria.getGruppi().getnome())){
								cbGruppi.setSelectedIndex(i);
								trovato = true;
							}
						}
					}
					if(!trovato){
						cbGruppi.setSelectedIndex(0);
					}

				}
			});

			// bottone Update
			final ButtonF aggiorna = new ButtonF();
			aggiorna.setBounds(26, 421, 100, 25);
			aggiorna.setText("Aggiorna");
			aggiorna.addActionListener(new AscoltatoreAggiornaCategoria(this));

			// bottone insert
			inserisci.addActionListener(new AscoltatoreInserisciCategoria(this));

			// bottone cancella
			final ButtonF cancella = new ButtonF();
			cancella.setText("Cancella");
			cancella.setBounds(132, 421, 100, 25);
			cancella.addActionListener(new AscoltatoreCancellaCategoria(this));

			getContentPane().add(cancella);
			getContentPane().add(inserisci);
			getContentPane().add(taDescrizione);
			getContentPane().add(cbImportanza);
			getContentPane().add(tfNome);
			getContentPane().add(cbCategorie);
			getContentPane().add(aggiorna);

		} catch (final Exception e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public boolean nonEsistonoCampiNonValorizzati() {
		return getcDescrizione() != null && getcImportanza() != null && getcNome() != null && getcImportanza() != null;
	}

	public void aggiornaModelDaVista(final String actionCommand) {

		if (actionCommand.equals("Inserisci")) {
			final int idCategoria = (CacheCategorie.getSingleton().getMaxId()) + 1;
			getModelCatSpese().setidCategoria(idCategoria);
		} else {
			final int idCategoriaDaCombo = getCategoria().getidCategoria();
			if (idCategoriaDaCombo == 0) {
				final int idCategorie = (CacheCategorie.getSingleton().getMaxId()) + 1;
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
		setGruppo((Gruppi) cbGruppi.getSelectedItem());

	}

	public List<CatSpese> getCategorieSpesa() {
		return categorieSpesa;
	}

	public void setCategorieSpesa(final List<CatSpese> categorieSpesa) {
		this.categorieSpesa = categorieSpesa;
	}

	/**
	 * @return the comboCategorie
	 */
	public JComboBox getComboCategorie() {
		return cbCategorie;
	}

	/**
	 * @param comboCategorie
	 *            the comboCategorie to set
	 */
	public void setComboCategorie(final JComboBox comboCategorie) {
		this.cbCategorie = comboCategorie;
	}

	public JComboBox getComboGruppi() {
		return cbGruppi;
	}

	public void setComboGruppi(final JComboBox comboGruppi) {
		this.cbGruppi = comboGruppi;
	}

	public CatSpese getCategoria() {
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
		final JLabel labelNome = new LabelListaGruppi();
		labelNome.setBounds(26, 12, 100, 25);
		labelNome.setText("Categoria");

		// Label descrizione
		final JLabel labelDescrizione = new LabelListaGruppi();
		labelDescrizione.setBounds(26, 65, 90, 25);
		labelDescrizione.setText("Descrizione");

		// Label Importanza
		final JLabel labelCategorie = new LabelListaGruppi();
		labelCategorie.setBounds(26, 184, 100, 25);
		labelCategorie.setText("Importanza");

		// Label Combo Categorie
		final JLabel labelComboCategorie = new LabelListaGruppi();
		labelComboCategorie.setBounds(26, 352, 100, 25);
		labelComboCategorie.setText("Lista Categorie");

		final LabelListaGruppi lbltstGruppo = new LabelListaGruppi();
		lbltstGruppo.setText("Gruppo");
		lbltstGruppo.setBounds(26, 239, 100, 25);
		getContentPane().add(lbltstGruppo);
		getContentPane().add(labelDescrizione);
		getContentPane().add(labelCategorie);
		getContentPane().add(labelNome);
		getContentPane().add(labelComboCategorie);

	}

}