package view.entrateuscite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.AltreUtil;
import business.CheckTesto;
import business.Controllore;
import business.DBUtil;
import business.Database;
import business.cache.CacheCategorie;
import business.cache.CacheUscite;
import business.comandi.CommandDeleteSpesa;
import business.comandi.CommandInserisciSpesa;
import domain.CatSpese;
import domain.SingleSpesa;
import domain.wrapper.WrapSingleSpesa;

public class UsciteView extends AbstractUsciteView {

	private static final long serialVersionUID = 1L;
	private final TextFieldF  tfNome;
	private final TextFieldF  tfData;
	private final TextFieldF  tfEuro;
	private final TextAreaF   taDescrizione;
	private static JComboBox  cCategorie;

	public static void main(String[] args) {
		try {
			UsciteView dialog = new UsciteView(new WrapSingleSpesa());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setBounds(0, 0, 347, 407);
			dialog.setVisible(true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Create the panel.
	 */
	public UsciteView(WrapSingleSpesa spesa) {
		super(spesa);
		setTitle("Inserimento Spese");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		modelUscita.addObserver(this);
		getContentPane().setLayout(null);

		initLabel();

		taDescrizione = new TextAreaF();
		taDescrizione.setText("Inserisci qui la descrizione della spesa");
		taDescrizione.setBounds(13, 87, 318, 75);
		taDescrizione.setLineWrap(true);
		taDescrizione.setWrapStyleWord(true);
		taDescrizione.setAutoscrolls(true);
		getContentPane().add(taDescrizione);

		final TextAreaF descCateg = new TextAreaF();
		descCateg.setText("Qui compare la descrizione delle categorie");
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
			public void itemStateChanged(ItemEvent e) {
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
		GregorianCalendar gc = new GregorianCalendar();
		tfData.setText(DBUtil.dataToString(gc.getTime(), "yyyy/MM/dd"));
		tfData.setBounds(13, 189, 150, 27);
		getContentPane().add(tfData);

		tfEuro = new TextFieldF();
		tfEuro.setColumns(10);
		tfEuro.setBounds(184, 189, 150, 27);
		getContentPane().add(tfEuro);

		// Bottone Elimina
		ButtonF eliminaUltima = new ButtonF();
		eliminaUltima.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					// TODO metodo che restituisce ultima spesa oppure usare
					// getLast() del CommandManager
					// if(modelUscita.deleteLastSpesa()){
					if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteSpesa(modelUscita), SingleSpesa.NOME_TABELLA)) {
						update(modelUscita, null);
						Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
						JOptionPane.showMessageDialog(null, "Ok, ultima uscita eliminata correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
					e1.printStackTrace();
				}
			}
		});

		eliminaUltima.setText("Elimina Ultima");
		eliminaUltima.setBounds(184, 325, 147, 27);
		getContentPane().add(eliminaUltima);

		ButtonF inserisci = new ButtonF();
		inserisci.setText("Inserisci");
		inserisci.setBounds(13, 325, 150, 27);
		getContentPane().add(inserisci);

		inserisci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setUscite();
				if (nonEsistonoCampiNonValorizzati()) {
					if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandInserisciSpesa(modelUscita), SingleSpesa.NOME_TABELLA)) {
						JOptionPane.showMessageDialog(null, "Ok, uscita inserita correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
						// TODO log.fine("Uscita inserita, nome: "
						// + modelUscita.getnome() + ", id: " +
						// modelUscita.getidSpesa());
					}
				} else {
					JOptionPane.showMessageDialog(null, "E' necessario riempire tutti i campi", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
					// TODO
					// log.severe("Uscita non inserita: e' necessario riempire tutti i campi");
				}

			}

		});
	}

	private boolean nonEsistonoCampiNonValorizzati() {
		return getcNome() != null && getcDescrizione() != null && getcData() != null && getDataIns() != null && getCategoria() != null && getdEuro() != 0.0 && getUtenti() != null;
	}

	private void initLabel() {
		LabelTesto lblNomeSpesa = new LabelTesto("Nome Spesa");
		lblNomeSpesa.setBounds(13, 12, 118, 27);
		getContentPane().add(lblNomeSpesa);

		LabelTesto lblEuro = new LabelTesto("Euro");
		lblEuro.setBounds(184, 163, 77, 27);
		getContentPane().add(lblEuro);

		LabelTesto lblCategorie = new LabelTesto("Categorie");
		lblCategorie.setBounds(181, 12, 125, 27);
		getContentPane().add(lblCategorie);

		LabelTesto lblData = new LabelTesto("Data");
		lblData.setBounds(13, 163, 77, 27);
		getContentPane().add(lblData);

		LabelTesto lblDescrizione = new LabelTesto("Descrizione Spesa");
		lblDescrizione.setBounds(14, 62, 212, 25);
		getContentPane().add(lblDescrizione);

		LabelTesto lblDescrizione_1 = new LabelTesto("Descrizione Categoria");
		lblDescrizione_1.setBounds(13, 216, 232, 27);
		getContentPane().add(lblDescrizione_1);
	}

	private void setUscite() {
		int idSpesa = (CacheUscite.getSingleton().getMaxId()) + 1;
		getModelUscita().setidSpesa(idSpesa);

		CheckTesto checkTesto = new CheckTesto(tfNome.getText());
		String nomeCheckato = checkTesto.getTesto();
		setcNome(nomeCheckato);

		checkTesto.setTesto(taDescrizione.getText());
		String descrizioneCheckato = checkTesto.getTesto();
		setcDescrizione(descrizioneCheckato);

		setCategoria((CatSpese) cCategorie.getSelectedItem());
		if (AltreUtil.checkData(tfData.getText())) {
			setcData(tfData.getText());
		} else {
			String messaggio = "La data va inserita con il seguente formato: aaaa/mm/gg";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			Double euro = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		} else {
			String messaggio = "Valore in Euro inserito non correttamente";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		setUtenti(Controllore.getSingleton().getUtenteLogin());
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
	public void setComboCategorie(JComboBox categorie) {
		UsciteView.cCategorie = categorie;
	}

	@Override
	public void update(Observable o, Object arg) {
		tfNome.setText(getcNome());
		taDescrizione.setText(getcDescrizione());
		cCategorie.setSelectedItem(getCategoria());
		tfData.setText(getcData());
		tfEuro.setText(getdEuro().toString());
	}
}
