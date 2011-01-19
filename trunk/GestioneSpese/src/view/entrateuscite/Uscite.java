package view.entrateuscite;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Controllore;
import business.DBUtil;
import business.cache.CacheCategorie;
import domain.CatSpese;
import domain.SingleSpesa;
import domain.wrapper.WrapSingleSpesa;

public class Uscite extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Uscite singleton;
	private TextFieldF nome;
	private JComboBox categorie;
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
		lblNomeSpesa.setBounds(42, 71, 118, 14);
		add(lblNomeSpesa);
		
		LabelTesto lblEuro = new LabelTesto("Euro");
		lblEuro.setBounds(473, 71, 77, 14);
		add(lblEuro);
		
		LabelTesto lblCategorie = new LabelTesto("Categorie");
		lblCategorie.setBounds(178, 71, 77, 14);
		add(lblCategorie);
		
		LabelTesto lblData = new LabelTesto("Data");
		lblData.setBounds(340, 71, 77, 14);
		add(lblData);
		
		LabelTesto lblDescrizione = new LabelTesto("Descrizione Spesa");
		lblDescrizione.setBounds(43, 146, 212, 14);
		add(lblDescrizione);
		
		LabelTesto lblDescrizione_1 = new LabelTesto("Descrizione Categoria");
		lblDescrizione_1.setBounds(338, 146, 232, 14);
		add(lblDescrizione_1);
		
		final TextAreaF descrizione = new TextAreaF("Inserisci qui la descrizione della spesa");
		descrizione.setBounds(43, 167, 260, 57);
		descrizione.setLineWrap(true);
		descrizione.setWrapStyleWord(true);
		descrizione.setBackground(Color.LIGHT_GRAY);
		descrizione.setAutoscrolls(true);
		add(descrizione);
		
		final TextAreaF descCateg = new TextAreaF("Qui compare la descrizione delle categorie");
		descCateg.setBounds(338, 167, 260, 57);
		descCateg.setLineWrap(true);
		descCateg.setWrapStyleWord(true);
		descCateg.setBackground(Color.LIGHT_GRAY);
		descCateg.setAutoscrolls(true);
		add(descCateg);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(41, 278, 557, 11);
		add(separator);
		
		nome = new TextFieldF();
		nome.setBounds(41, 90, 125, 22);
		add(nome);
		nome.setColumns(10);
		
		categorie = new JComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo());
		categorie.setBounds(176, 90, 125, 22);
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
		data.setBounds(338, 90, 125, 22);
		add(data);
		
		inEuro = new TextFieldF();
		inEuro.setColumns(10);
		inEuro.setBounds(471, 90, 125, 22);
		add(inEuro);
		
		LabelTitolo lblPannelloUscite = new LabelTitolo("Pannello Uscite");
		lblPannelloUscite.setBounds(42, 24, 136, 36);
		add(lblPannelloUscite);
		
		final WrapSingleSpesa modelSingleSpesa = Controllore.getSingleton().getModel().getModelUscita();
		
		
		//Bottone Elimina
		ButtonF buttonF = new ButtonF();
		buttonF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(Controllore.getSingleton().getModel().getModelUscita().DeleteLastSpesa()){
						//TODO gestire aggiornamenti
	//					Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
						JOptionPane.showMessageDialog(null,"Ok, ultima uscita eliminata correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
					e1.printStackTrace();
				}
			}
		});
		
		
		buttonF.setText("Elimina Ultima");
		buttonF.setBounds(627, 134, 126, 36);
		add(buttonF);
		
		ButtonF bottone = new ButtonF("New button");
		bottone.setText("Inserisci");
		bottone.setBounds(627, 90, 126, 36);
		add(bottone);
		
		bottone.addActionListener(new ActionListener() {
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
							if (categorie.getSelectedItem() != null
									&& !(categorie.getSelectedItem()
											.equals(""))) {
								CatSpese cat13 = (CatSpese) categorie
										.getSelectedItem();
								ss.setCatSpese(cat13);
							}
							Date dataUtil =DBUtil.stringToDate(data.getText(), "yyyy/MM/dd");
							ss.setData(dataUtil);
							ss.setinEuro(AltreUtil.arrotondaDecimaliDouble(Double.parseDouble(inEuro.getText())));
							ss.setUtenti(Controllore.getSingleton().getUtenteLogin());
							ss.setDataIns(new Date());
							if (modelSingleSpesa.insert(ss)) {
								JOptionPane.showMessageDialog(null,"Ok, uscita inserita correttamente!!!","Perfetto!",JOptionPane.INFORMATION_MESSAGE);
								//TODO gestire log
//								log.fine("Entrata inserita, id: "	+ ss.getnome());
							}

						} else
							JOptionPane.showMessageDialog(null,"E' necessario riempire tutti i campi","Non ci siamo!",JOptionPane.ERROR_MESSAGE,new ImageIcon("imgUtil/index.jpeg"));
						try {
							//TODO gestire aggiornamenti
//							Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						DBUtil.closeConnection();
					}
				} else
					JOptionPane.showMessageDialog(null,
							"Valore in Euro inserito non correttamente","Non ci siamo!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imgUtil/index.jpeg"));
			}

		});

	}
}
