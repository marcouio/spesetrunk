package view.entrateuscite;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;


import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextAreaF;
import view.font.TextFieldF;
import javax.swing.JComboBox;

import domain.Entrate;
import domain.wrapper.WrapEntrate;

import business.AltreUtil;
import business.Controllore;
import business.DBUtil;

public class EntrateView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static EntrateView singleton;
	private TextFieldF nome;
	private JComboBox tipo;
	private TextFieldF data;
	private TextFieldF euro;
	static private ArrayList<String> lista;
	private final WrapEntrate modelEntrate = Controllore.getSingleton().getModel().getModelEntrate();

	
	public static final EntrateView getSingleton() {
		if (singleton == null) {
			synchronized (EntrateView.class) {
				if (singleton == null) {
					singleton = new EntrateView();
				}
			} // if
		} // if
		return singleton;
	} // getSingleton()
	
	/**
	 * Create the panel.
	 */
	private EntrateView() {
		setLayout(null);
		
		LabelTesto lblNomeSpesa = new LabelTesto("Nome Spesa");
		lblNomeSpesa.setText("Nome Entrata");
		lblNomeSpesa.setBounds(42, 71, 97, 14);
		add(lblNomeSpesa);
		
		LabelTesto lblEuro = new LabelTesto("Euro");
		lblEuro.setBounds(473, 71, 77, 14);
		add(lblEuro);
		
		LabelTesto lblCategorie = new LabelTesto("Categorie");
		lblCategorie.setText("Tipo");
		lblCategorie.setBounds(178, 71, 77, 14);
		add(lblCategorie);
		
		LabelTesto lblData = new LabelTesto("Data");
		lblData.setBounds(340, 71, 77, 14);
		add(lblData);
		
		LabelTesto lblDescrizione = new LabelTesto("Descrizione Spesa");
		lblDescrizione.setText("Descrizione Entrata");
		lblDescrizione.setBounds(43, 146, 123, 14);
		add(lblDescrizione);
		
		final TextAreaF descrizione = new TextAreaF();
		descrizione.setBounds(42, 167, 260, 57);
		add(descrizione);
		
		// specifica se �true� di andare a capo automaticamente a fine riga
		descrizione.setLineWrap(true);
		// va a capo con la parola se �true� o col singolo carattere se �false�
		descrizione.setWrapStyleWord(true);
		descrizione.setAutoscrolls(true);
		descrizione.setBackground(Color.LIGHT_GRAY);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(41, 278, 557, 11);
		add(separator);
		
		nome = new TextFieldF();
		nome.setBounds(41, 90, 125, 22);
		add(nome);
		nome.setColumns(10);
		
		//array per Categoria
		lista = new ArrayList<String>();
		lista.add("");
		lista.add("Variabili");
		lista.add("Fisse");
		
		tipo = new JComboBox(lista.toArray());
		tipo.setBounds(176, 90, 125, 22);
		add(tipo);
		
		GregorianCalendar gc = new GregorianCalendar();
		data = new TextFieldF(DBUtil.dataToString(gc.getTime(), "yyyy/MM/dd"));
		data.setColumns(10);
		data.setBounds(338, 90, 125, 22);
		add(data);
		
		euro = new TextFieldF();
		euro.setColumns(10);
		euro.setBounds(471, 90, 125, 22);
		add(euro);
		
		LabelTitolo lblPannelloUscite = new LabelTitolo("Pannello Uscite");
		lblPannelloUscite.setText("Pannello Entrate");
		lblPannelloUscite.setBounds(42, 24, 136, 36);
		add(lblPannelloUscite);
		
		ButtonF bottone = new ButtonF("New button");
		bottone.setText("Inserisci");
		bottone.setBounds(627, 90, 126, 36);
		add(bottone);
		
		ButtonF elimina = new ButtonF("New button");
		elimina.setText("Elimina Ultima");
		elimina.setBounds(627, 134, 126, 36);
		add(elimina);
		
		elimina.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(Controllore.getSingleton().getModel().getModelEntrate().DeleteLastEntrate()){
					//TODO gestire log e aggiornamenti generali
//					log.fine("Cancellata ultima spesa inserita");
//					Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
						JOptionPane.showMessageDialog(null,"Ok, ultima entrata eliminata correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, e2.getMessage(),"Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
//					log.severe(e2.getMessage());
					DBUtil.closeConnection();
				}
			}
		});
		
		TextAreaF textAreaF = new TextAreaF();
		textAreaF.setBounds(336, 167, 260, 57);
		add(textAreaF);
		bottone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// verifiche sulla data inserita e sul formato double euro
				if (!(nome.getText().equals("")) && !(descrizione.getText().equals("")) && !(tipo.getSelectedItem().equals("")) && tipo.getSelectedItem() != null) {
					if (AltreUtil.checkDouble(euro.getText())) {
						if (AltreUtil.checkData(data.getText())) {
						
							Entrate entr = new Entrate();
							entr.setnome(nome.getText());
							entr.setdescrizione(descrizione.getText());
							entr.setUtenti(Controllore.getSingleton().getUtenteLogin());
							entr.setDataIns(new Date());
							if (tipo.getSelectedItem() != null && !(tipo.getSelectedItem().equals("")))
								entr.setFisseoVar((String) tipo.getSelectedItem());
							
							entr.setinEuro(AltreUtil.arrotondaDecimaliDouble(Double.parseDouble(euro.getText())));
							entr.setdata(DBUtil.stringToDate(data.getText(), "yyyy/MM/dd"));

							if (modelEntrate.insert(entr)) {
								JOptionPane.showMessageDialog(null, "Ok, entrata inserita correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
								//TODO gestire log
//								log.fine("Entrata inserita, id: "
//										+ entr.getNome());
							}
						}
						try {
							//TODO gestore aggiornamenti generali
//							Database.aggiornamentoGenerale(EntrateView.NOME_TABELLA);
						} catch (Exception e1) {
							//TODO gestire log
//							log.severe("Aggiornamenti su pannelli non riusciti");
							e1.printStackTrace();
						}
					}else
						JOptionPane.showMessageDialog(null,
							"Valore in Euro inserito non correttamente",
							"Non ci siamo!", JOptionPane.ERROR_MESSAGE,
							new ImageIcon("./imgUtil/index.jpeg"));
				} else
					JOptionPane.showMessageDialog(null,
							"E' necessario riempire tutti i campi",
							"Non ci siamo!",
							JOptionPane.ERROR_MESSAGE,
							new ImageIcon("./imgUtil/index.jpeg"));
//				TODO gestire log
//					log.severe("Entrata non inserita: e' necessario riempire tutti i campi");

			}
		});

	}

	/**
	 * @return the nome
	 */
	public TextFieldF getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(TextFieldF nome) {
		this.nome = nome;
	}

	/**
	 * @return the tipo
	 */
	public JComboBox getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(JComboBox tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the data
	 */
	public TextFieldF getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(TextFieldF data) {
		this.data = data;
	}

	/**
	 * @return the euro
	 */
	public TextFieldF getEuro() {
		return euro;
	}

	/**
	 * @param euro the euro to set
	 */
	public void setEuro(TextFieldF euro) {
		this.euro = euro;
	}

	/**
	 * @return the lista
	 */
	public static ArrayList<String> getLista() {
		return lista;
	}

	/**
	 * @param lista the lista to set
	 */
	public static void setLista(ArrayList<String> lista) {
		EntrateView.lista = lista;
	}

}
