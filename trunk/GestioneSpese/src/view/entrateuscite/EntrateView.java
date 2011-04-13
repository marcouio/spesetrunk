package view.entrateuscite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

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
import business.comandi.CommandInserisciEntrata;
import domain.Entrate;
import domain.wrapper.WrapEntrate;

public class EntrateView extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;
	private static EntrateView singleton;
	
	static private ArrayList<String> lista;
	
	private final WrapEntrate modelEntrate = Controllore.getSingleton().getModel().getModelEntrate();
	private TextFieldF tfNome;
	private TextAreaF taDescrizione;
	private JComboBox cbTipo;
	private TextFieldF tfData;
	private TextFieldF tfEuro;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame inst = new JFrame();	
				inst.setSize(950, 700);
				inst.getContentPane().add(new EntrateView());
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				
			}
		});
	}
	
	
	
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
		lblNomeSpesa.setBounds(42, 64, 97, 27);
		add(lblNomeSpesa);
		
		LabelTesto lblEuro = new LabelTesto("Euro");
		lblEuro.setBounds(564, 64, 77, 27);
		add(lblEuro);
		
		LabelTesto lblCategorie = new LabelTesto("Categorie");
		lblCategorie.setText("Tipo");
		lblCategorie.setBounds(210, 64, 77, 27);
		add(lblCategorie);
		
		LabelTesto lblData = new LabelTesto("Data");
		lblData.setBounds(393, 64, 77, 27);
		add(lblData);
		
		LabelTesto lblDescrizione = new LabelTesto("Descrizione Spesa");
		lblDescrizione.setText("Descrizione Entrata");
		lblDescrizione.setBounds(43, 142, 123, 25);
		add(lblDescrizione);
		
		taDescrizione = new TextAreaF("Inserisci qui la descrizione dell'entrata");
		taDescrizione.setBounds(42, 167, 318, 75);
		add(taDescrizione);
		
		// specifica se �true� di andare a capo automaticamente a fine riga
		taDescrizione.setLineWrap(true);
		// va a capo con la parola se �true� o col singolo carattere se �false�
		taDescrizione.setWrapStyleWord(true);
		taDescrizione.setAutoscrolls(true);
		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(41, 278, 557, 11);
		add(separator);
		
		tfNome = new TextFieldF();
		tfNome.setBounds(41, 90, 150, 27);
		add(tfNome);
		tfNome.setColumns(10);
		
		//array per Categoria
		lista = new ArrayList<String>();
		lista.add("");
		lista.add("Variabili");
		lista.add("Fisse");
		
		cbTipo = new JComboBox(lista.toArray());
		cbTipo.setBounds(210, 90, 150, 27);
		add(cbTipo);
		
		GregorianCalendar gc = new GregorianCalendar();
		tfData = new TextFieldF(DBUtil.dataToString(gc.getTime(), "yyyy/MM/dd"));
		tfData.setColumns(10);
		tfData.setBounds(393, 90, 150, 27);
		add(tfData);
		
		tfEuro = new TextFieldF();
		tfEuro.setColumns(10);
		tfEuro.setBounds(562, 90, 150, 27);
		add(tfEuro);
		
		LabelTitolo lblPannelloUscite = new LabelTitolo("Pannello Uscite");
		lblPannelloUscite.setText("Pannello Entrate");
		lblPannelloUscite.setBounds(42, 24, 136, 36);
		add(lblPannelloUscite);
		
		ButtonF inserisci = new ButtonF();
		inserisci.setText("Inserisci");
		inserisci.setBounds(735, 90, 126, 36);
		add(inserisci);
		
		ButtonF elimina = new ButtonF();
		elimina.setText("Elimina Ultima");
		elimina.setBounds(735, 134, 126, 36);
		add(elimina);
		
		elimina.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(Controllore.getSingleton().getModel().getModelEntrate().DeleteLastEntrate()){
					log.fine("Cancellata ultima spesa inserita");
					Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
						JOptionPane.showMessageDialog(null,"Ok, ultima entrata eliminata correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, e2.getMessage(),"Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
					log.severe(e2.getMessage());
					DBUtil.closeConnection();
				}
			}
		});
		
		TextAreaF vuota = new TextAreaF("");
		vuota.setBounds(393, 167, 317, 75);
		add(vuota);
		inserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!(getcNome().equals("")) && !(getcDescrizione().equals("")) && !(cbTipo.getSelectedItem().equals("")) && cbTipo.getSelectedItem() != null) {
					if (getdEuro()!=null) {
						if (getcData()!=null) {
							
							Entrate entr = new Entrate();
							entr.setnome(getcNome());
							entr.setdescrizione(getcDescrizione());
							entr.setUtenti(Controllore.getSingleton().getUtenteLogin());
							entr.setDataIns(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
							if (cbTipo.getSelectedItem() != null && !(cbTipo.getSelectedItem().equals("")))
								entr.setFisseoVar((String) cbTipo.getSelectedItem());
							
							entr.setinEuro(AltreUtil.arrotondaDecimaliDouble(getdEuro()));
							entr.setdata(getcData());

							if(Controllore.getSingleton().getCommandManager().invocaComando(new CommandInserisciEntrata(entr), Entrate.NOME_TABELLA)){
								JOptionPane.showMessageDialog(null, "Ok, entrata inserita correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
								log.fine("Entrata inserita, id: "
										+ entr.getnome());
							}
						}
						try {
							Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
						} catch (Exception e1) {
							log.severe("Aggiornamenti su pannelli non riusciti");
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
					log.severe("Entrata non inserita: e' necessario riempire tutti i campi");

			}
		});

	}

	protected String getcNome() {
		return tfNome.getText();
	}

	protected void setcNome(String cNome) {
		this.tfNome.setText(cNome);
	}

	protected String getcData() {
		if(AltreUtil.checkData(tfData.getText()))
			return tfData.getText();
		else
			return null;
	}

	protected void setcData(String cData) {
		this.tfData.setText(cData);
	}

	protected Double getdEuro() {
		if(AltreUtil.checkDouble(tfEuro.getText()))
			return Double.parseDouble(tfEuro.getText());
		else{
			return null;
		}
	}

	protected void setdEuro(Double dEuro) {
		this.tfEuro.setText(Double.toString(dEuro));
	}

	/**
	 * @return the lista
	 */
	public static ArrayList<String> getLista() {
		return EntrateView.lista;
	}

	/**
	 * @param lista the lista to set
	 */
	public static void setLista(ArrayList<String> lista) {
		EntrateView.lista = lista;
	}

	public String getcDescrizione() {
		return taDescrizione.getText();
	}

	public void setcDescrizione(String cDescrizione) {
		this.taDescrizione.setText(cDescrizione);
	}

}
