package view.impostazioni;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.Controllore;
import business.Database;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import business.comandi.CommandDeleteCategoria;
import business.comandi.CommandInserisciCategoria;
import business.comandi.CommandUpdateCategoria;
import domain.CatSpese;
import domain.Gruppi;
import domain.ICatSpese;
import domain.wrapper.WrapCatSpese;

public class CategorieView extends AbstractCategorieView {

	private CatSpese                categoria        = null;
	private JTextArea               taDescrizione;
	private JComboBox               cbImportanza;
	private JTextField              tfNome;
	private static JComboBox        cbCategorie;
	private static Vector<CatSpese> categorieSpesa;
	final Database                  db               = Database.getSingleton();
	private JComboBox               cbGruppi;

	private static final long       serialVersionUID = 1L;

	public static void main(String[] args) {
		CategorieView dialog = new CategorieView(new WrapCatSpese());
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// dialog.setBounds(0, 0, 260, 556);
		dialog.setVisible(true);
	}

	public CategorieView(WrapCatSpese cat) {
		super(cat);

		modelCatSpese.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		try {

			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setModalityType(ModalityType.APPLICATION_MODAL);
			setTitle("Categorie");
			this.setPreferredSize(new Dimension(260, 556));
			setLayout(null);

			initLabel();

			// Nome Spesa
			tfNome = new TextFieldF();
			tfNome.setBounds(26, 37, 206, 26);

			// Descrizione
			taDescrizione = new TextAreaF(
			                "Inserisci la descrizione della categoria", 50, 25);
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
			ButtonF inserisci = new ButtonF();
			inserisci.setBounds(26, 305, 206, 25);
			inserisci.setText("Inserisci Categoria");

			Vector<Gruppi> vettoreGruppi = CacheGruppi.getSingleton()
			                .getVettoreGruppi();
			// combo gruppi
			cbGruppi = new JComboBox();
			for (int i = 0; i < vettoreGruppi.size(); i++) {
				cbGruppi.addItem(vettoreGruppi.get(i));
			}
			cbGruppi.setBounds(26, 265, 206, 25);
			getContentPane().add(cbGruppi);

			categorieSpesa = CacheCategorie.getSingleton()
			                .getVettoreCategoriePerCombo(CacheCategorie.getSingleton().getAllCategorie());
			cbCategorie = new JComboBox(categorieSpesa);
			cbCategorie.setBounds(26, 380, 206, 25);
			cbCategorie.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {

					if (cbCategorie.getSelectedIndex() != 0
					                && cbCategorie.getSelectedItem() != null) {
						categoria = (CatSpese) cbCategorie.getSelectedItem();
						tfNome.setText(categoria.getnome());
						taDescrizione.setText(categoria.getdescrizione());
						cbImportanza.setSelectedItem(categoria.getimportanza());
						cbGruppi.setSelectedItem(categoria.getGruppi());
					}
				}
			});

			// bottone Update
			ButtonF aggiorna = new ButtonF();
			aggiorna.setBounds(26, 421, 100, 25);
			aggiorna.setText("Aggiorna");
			aggiorna.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					CatSpese oldCategoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categoria.getidCategoria()));

					if (cbCategorie.getSelectedItem() != null) {
						setCategoria();
						if (categoria != null)
							modelCatSpese.setidCategoria(categoria.getidCategoria());

						if (getGruppo() == null) {
							Gruppi gruppo = CacheGruppi.getSingleton().getGruppoPerNome("No Gruppo");
							modelCatSpese.setGruppi(gruppo);
						}
						try {
							if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandUpdateCategoria(oldCategoria,
							                                                (ICatSpese) modelCatSpese
							                                                                .getentitaPadre()),
							                                "tutto")) {
								Database.aggiornaCategorie((CatSpese) modelCatSpese
								                .getentitaPadre());
								JOptionPane.showMessageDialog(null,
								                "Operazione eseguita correttamente",
								                "Perfetto!",
								                JOptionPane.INFORMATION_MESSAGE);
								modelCatSpese.setChanged();
								modelCatSpese.notifyObservers();
							}
						} catch (Exception e22) {
							e22.printStackTrace();
							JOptionPane.showMessageDialog(
							                null,
							                "Inserisci i dati correttamente: "
							                                + e22.getMessage(),
							                "Non ci siamo!", JOptionPane.ERROR_MESSAGE,
							                new ImageIcon("imgUtil/index.jpeg"));
						}
					} else {
						JOptionPane
						                .showMessageDialog(
						                                null,
						                                "Impossibile aggiornare una categoria inesistente!",
						                                "Non ci siamo!",
						                                JOptionPane.ERROR_MESSAGE,
						                                new ImageIcon("imgUtil/index.jpeg"));
					}
				}
			});

			// bottone insert
			inserisci.addActionListener(new ActionListener() {

				private CatSpese categoria1;

				@Override
				public void actionPerformed(ActionEvent e) {
					setCategoria();
					if (getcDescrizione() != null && getcImportanza() != null
					                && getcNome() != null && getcImportanza() != null) {

						if (Controllore
						                .getSingleton()
						                .getCommandManager()
						                .invocaComando(
						                                new CommandInserisciCategoria(
						                                                modelCatSpese), "tutto")) {
							cbCategorie.addItem(categoria1);
							JOptionPane
							                .showMessageDialog(null,
							                                "Categoria inserita correttamente",
							                                "Perfetto",
							                                JOptionPane.INFORMATION_MESSAGE);
							modelCatSpese.setChanged();
							modelCatSpese.notifyObservers();
						}
					} else
						JOptionPane.showMessageDialog(null,
						                "E' necessario riempire tutti i campi",
						                "Non ci siamo!", JOptionPane.ERROR_MESSAGE,
						                new ImageIcon("imgUtil/index.jpeg"));
				}
			});
			// bottone cancella
			ButtonF cancella = new ButtonF();
			cancella.setText("Cancella");
			cancella.setBounds(132, 421, 100, 25);
			cancella.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setCategoria();
					if (cbCategorie.getSelectedItem() != null
					                && categoria != null) {
						if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteCategoria(modelCatSpese), "tutto")) {
							cbCategorie.removeItem(categoria);
							JOptionPane.showMessageDialog(null,
							                "Categoria cancellata!", "Perfetto!",
							                JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Impossibile cancellare una categoria inesistente!", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
					// log.fine("Cancellata categoria " + categoria);
				}
			});

			getContentPane().add(cancella);
			getContentPane().add(inserisci);
			getContentPane().add(taDescrizione);
			getContentPane().add(cbImportanza);
			getContentPane().add(tfNome);
			getContentPane().add(cbCategorie);
			getContentPane().add(aggiorna);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCategoria() {
		setcNome(tfNome.getText());
		setcDescrizione(taDescrizione.getText());
		setcImportanza((String) cbImportanza.getSelectedItem());
		setGruppo((Gruppi) cbGruppi.getSelectedItem());

	}

	public static Vector<CatSpese> getCategorieSpesa() {
		return categorieSpesa;
	}

	public static void setCategorieSpesa(Vector<CatSpese> categorieSpesa) {
		CategorieView.categorieSpesa = categorieSpesa;
	}

	/**
	 * @return the comboCategorie
	 */
	public static JComboBox getComboCategorie() {
		return cbCategorie;
	}

	/**
	 * @param comboCategorie
	 *            the comboCategorie to set
	 */
	public static void setComboCategorie(JComboBox comboCategorie) {
		CategorieView.cbCategorie = comboCategorie;
	}

	public JComboBox getComboGruppi() {
		return cbGruppi;
	}

	public void setComboGruppi(JComboBox comboGruppi) {
		this.cbGruppi = comboGruppi;
	}

	@Override
	public void update(Observable o, Object arg) {
		tfNome.setText(getcNome());
		taDescrizione.setText(getcDescrizione());
		cbImportanza.setSelectedItem(getcImportanza());
		cbGruppi.setSelectedItem(getGruppo());

	}

	private void initLabel() {

		// Label nome
		JLabel labelNome = new LabelTesto();
		labelNome.setBounds(26, 12, 100, 25);
		labelNome.setText("Categoria");

		// Label descrizione
		JLabel labelDescrizione = new LabelTesto();
		labelDescrizione.setBounds(26, 65, 90, 25);
		labelDescrizione.setText("Descrizione");

		// Label Importanza
		JLabel labelCategorie = new LabelTesto();
		labelCategorie.setBounds(26, 184, 100, 25);
		labelCategorie.setText("Importanza");

		// Label Combo Categorie
		JLabel labelComboCategorie = new LabelTesto();
		labelComboCategorie.setBounds(26, 352, 100, 25);
		labelComboCategorie.setText("Lista Categorie");

		LabelTesto lbltstGruppo = new LabelTesto();
		lbltstGruppo.setText("Gruppo");
		lbltstGruppo.setBounds(26, 239, 100, 25);
		getContentPane().add(lbltstGruppo);
		getContentPane().add(labelDescrizione);
		getContentPane().add(labelCategorie);
		getContentPane().add(labelNome);
		getContentPane().add(labelComboCategorie);

	}
}