package view.entrateuscite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import view.OggettoVistaBase;
import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Controllore;
import business.DBUtil;
import business.Database;
import business.cache.CacheCategorie;
import business.comandi.CommandInserisciSpesa;
import domain.CatSpese;
import domain.SingleSpesa;
import domain.wrapper.WrapSingleSpesa;

public class Uscite extends OggettoVistaBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Uscite singleton;
	private TextFieldF nome;
	private static JComboBox categorie;
	/**
	 * @return the categorie
	 */
	public JComboBox getCategorie() {
			return categorie;
	}

	/**
	 * @param categorie the categorie to set
	 */
	public void setCategorie(JComboBox categorie) {
		Uscite.categorie = categorie;
	}


	private TextFieldF data;
	private TextFieldF inEuro;
	
	public static final Uscite getSingleton() {
		if (singleton == null) {
			synchronized (Uscite.class) {
				if (singleton == null) {
					singleton = new Uscite();
				}
			} // if
		} // if
		return singleton;
	} // getSingleton()
	
	
	/**
	 * Create the panel.
	 */
	public Uscite() {
		this.setLayout(null);
		
		LabelTesto lblNomeSpesa = new LabelTesto("Nome Spesa");
		lblNomeSpesa.setBounds(42, 64, 118, 27);
		add(lblNomeSpesa);
		
		LabelTesto lblEuro = new LabelTesto("Euro");
		lblEuro.setBounds(564, 64, 77, 27);
		add(lblEuro);
		
		LabelTesto lblCategorie = new LabelTesto("Categorie");
		lblCategorie.setBounds(210, 64, 125, 27);
		add(lblCategorie);
		
		LabelTesto lblData = new LabelTesto("Data");
		lblData.setBounds(393, 64, 77, 27);
		add(lblData);
		
		LabelTesto lblDescrizione = new LabelTesto("Descrizione Spesa");
		lblDescrizione.setBounds(43, 142, 212, 25);
		add(lblDescrizione);
		
		LabelTesto lblDescrizione_1 = new LabelTesto("Descrizione Categoria");
		lblDescrizione_1.setBounds(393, 141, 232, 27);
		add(lblDescrizione_1);
		
		final TextAreaF descrizione = new TextAreaF();
		descrizione.setText("Inserisci qui la descrizione della spesa");
		descrizione.setBounds(42, 167, 318, 75);
		descrizione.setLineWrap(true);
		descrizione.setWrapStyleWord(true);
		descrizione.setAutoscrolls(true);
		add(descrizione);
		
		final TextAreaF descCateg = new TextAreaF();
		descCateg.setText("Qui compare la descrizione delle categorie");
		descCateg.setBounds(393, 167, 318, 75);
		descCateg.setLineWrap(true);
		descCateg.setWrapStyleWord(true);
		descCateg.setAutoscrolls(true);
		add(descCateg);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(41, 278, 820, 10);
		add(separator);
		
		nome = new TextFieldF();
		nome.setBounds(41, 90, 150, 27);
		add(nome);
		nome.setColumns(10);
		
		categorie = new JComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo());
		categorie.setBounds(210, 90, 150, 27);
		add(categorie);
		
		categorie.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				CatSpese spese = null;
				if (categorie.getSelectedIndex() != 0) {
					spese = (CatSpese) categorie.getSelectedItem();
					// int indice = categorie.getSelectedIndex();
					// il campo sotto serve per inserire la descrizione nel
					// caso
					// si selezioni
					// una categoria e si vogliono maggiori info

					descCateg.setText(spese.getdescrizione());
				}

				
			}
		});
		
		data = new TextFieldF();
		data.setColumns(10);
		GregorianCalendar gc = new GregorianCalendar();
		data.setText(DBUtil.dataToString(gc.getTime(), "yyyy/MM/dd"));
		data.setBounds(393, 90, 150, 27);
		add(data);
		
		inEuro = new TextFieldF();
		inEuro.setColumns(10);
		inEuro.setBounds(564, 90, 150, 27);
		add(inEuro);
		
		LabelTitolo lblPannelloUscite = new LabelTitolo("Pannello Uscite");
		lblPannelloUscite.setBounds(42, 24, 136, 36);
		add(lblPannelloUscite);
		
		final WrapSingleSpesa modelSingleSpesa = Controllore.getSingleton().getModel().getModelUscita();
		
		
		//Bottone Elimina
		ButtonF eliminaUltima = new ButtonF();
		eliminaUltima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//TODO metodo che restituisce ultima spesa oppure usare getLast() del CommandManager
//					if(Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteSpesa())){}
					if(Controllore.getSingleton().getModel().getModelUscita().deleteLastSpesa()){
						Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
						JOptionPane.showMessageDialog(null,"Ok, ultima uscita eliminata correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
					e1.printStackTrace();
				}
			}
		});
		
		
		eliminaUltima.setText("Elimina Ultima");
		eliminaUltima.setBounds(735, 134, 126, 36);
		add(eliminaUltima);
		
		ButtonF inserisci = new ButtonF();
		inserisci.setText("Inserisci");
		inserisci.setBounds(735, 90, 126, 36);
		add(inserisci);
		
		inserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (AltreUtil.checkDouble(inEuro.getText())) {
					if (AltreUtil.checkData(data.getText())) {
						if (!(nome.getText().equals(""))
								&& !(descrizione.getText().equals(""))
								&& !(categorie.getSelectedItem().equals("") && !(categorie
										.getSelectedIndex() != 0))) {
							SingleSpesa ss = new SingleSpesa();
							ss.setnome(nome.getText());
							ss.setdescrizione(descrizione.getText());
							if (categorie.getSelectedItem() != null && !(categorie.getSelectedItem().equals(""))) {
								CatSpese cat13 = (CatSpese) categorie.getSelectedItem();
								ss.setCatSpese(cat13);
							}
							String dataUtil =data.getText();
							ss.setData(dataUtil);
							ss.setinEuro(AltreUtil.arrotondaDecimaliDouble(Double.parseDouble(inEuro.getText())));
							ss.setUtenti(Controllore.getSingleton().getUtenteLogin());
							ss.setDataIns(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
							if(Controllore.getSingleton().getCommandManager().invocaComando(new CommandInserisciSpesa(ss),SingleSpesa.NOME_TABELLA)){
								JOptionPane.showMessageDialog(null,"Ok, uscita inserita correttamente!!!","Perfetto!",JOptionPane.INFORMATION_MESSAGE);
								log.fine("Uscita inserita, id: "	+ ss.getnome());
							}

						} else
							JOptionPane.showMessageDialog(null,"E' necessario riempire tutti i campi","Non ci siamo!",JOptionPane.ERROR_MESSAGE,new ImageIcon("imgUtil/index.jpeg"));
						DBUtil.closeConnection();
					}
				} else
					JOptionPane.showMessageDialog(null,
							"Valore in Euro inserito non correttamente","Non ci siamo!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imgUtil/index.jpeg"));
			}

		});

	}
}
