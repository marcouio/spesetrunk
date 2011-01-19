package view.componenti.movimenti;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TextFieldF;

import business.AltreUtil;
import business.Database;
import domain.Entrate;
import domain.wrapper.Model;

public class DialogEntrateMov extends javax.swing.JDialog {

	private static JLabel labelEuro = new LabelTesto("Euro");
	private static JLabel labelData =new LabelTesto("Data");
	private static JLabel labelTipoEntrate = new LabelTesto("Tipo Entrata");
	private static JLabel labelDescrizione = new LabelTesto("Descrizione");
	private static JLabel labelNome = new LabelTesto("Nome");
	private static JLabel labelIdEntrate = new LabelTesto("Chiave Entrata");
	private static JTextField euro = new TextFieldF();
	private static JTextField data = new TextFieldF();
	//TODO verificare se è necessario sostituire campo tipoEntrata
	private static JComboBox tipoEntrata = new JComboBox(Model.getNomiColonneEntrate());
//	private static JTextField tipoEntrata = new TextFieldF();
	private static JTextField descrizione = new TextFieldF();
	private static JTextField nome = new TextFieldF();
	private static JTextField idEntrate = new TextFieldF();
	private JButton update = new ButtonF("Aggiorna");
	private JButton delete = new ButtonF("Cancella");

	private static final long serialVersionUID = 1L;
	/**
	* Auto-generated main method to display this JDialog
	*/
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				DialogEntrateMov inst = new DialogEntrateMov(frame);
				inst.setVisible(true);
			}
		});
	}
	
	public DialogEntrateMov(JFrame frame) {
		super(frame);
		initGUI();
	}
	public DialogEntrateMov() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			//questo permette di mantenere il focus sulla dialog
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			idEntrate.setEditable(false);
			this.setLayout(new GridLayout(7,2));
			update.setSize(60, 40);
			delete.setSize(60, 40);
			labelData.setSize(100, 40);
			labelDescrizione.setSize(100, 40);
			labelEuro.setSize(100, 40);
			labelIdEntrate.setSize(100, 40);
			labelNome.setSize(100, 40);
			labelTipoEntrate.setSize(100, 40);
			
		
			update.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String[]nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
					JTextField campo = ListaMovEntrat.getCampo();
					HashMap<String, String> campi = new HashMap<String, String>();
					HashMap<String, String> clausole = new HashMap<String, String>();
					if(AltreUtil.checkData(data.getText())){
						if(AltreUtil.checkDouble(euro.getText())){
							if(!(idEntrate.getText().equals("")) && !(nome.getText().equals("")) && !(descrizione.getText().equals(""))
									&& tipoEntrata.getSelectedIndex()!=0 && tipoEntrata.getSelectedItem()!=null &&
									!(data.getText().equals("")) && !(euro.getText().equals(""))){
								clausole.put(Entrate.ID, idEntrate.getText());
								campi.put(Entrate.NOME, nome.getText());
								campi.put(Entrate.DESCRIZIONE, descrizione.getText());
								campi.put(Entrate.FISSEOVAR, tipoEntrata.getSelectedItem().toString());
								campi.put(Entrate.DATA, data.getText());
								campi.put(Entrate.INEURO, euro.getText());
								try{
									if(Database.getSingleton().eseguiIstruzioneSql("UPDATE", Entrate.NOME_TABELLA, campi, clausole))
										JOptionPane.showMessageDialog(null,"Operazione eseguita correttamente", "Perfetto!", JOptionPane.INFORMATION_MESSAGE );
									Model.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
								}catch (Exception e22) {
									JOptionPane.showMessageDialog(null, "Inserisci i dati correttamente", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
								}
								//chiude la dialog e rilascia le risorse
								dispose();
							}else
								JOptionPane.showMessageDialog(null, "Tutti i campi devono essere valorizzati! ", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
						}else 
							JOptionPane.showMessageDialog(null, "Valore in Euro inserito non correttamente", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
				}
				
			});
			delete.addActionListener(new ActionListener() {
				String[]nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
				JTextField campo = ListaMovEntrat.getCampo();
				@Override
				public void actionPerformed(ActionEvent e) {
					HashMap<String, String> clausole = new HashMap<String, String>();
					if(Entrate.ID!=null)
						clausole.put(Entrate.ID, idEntrate.getText());
						if(Database.getSingleton().eseguiIstruzioneSql("DELETE", Entrate.NOME_TABELLA, null, clausole))
							JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente", "Perfetto!", JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "Inserisci i dati correttamente", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));

					if(campo!=null)
						Model.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
					else{
						Model.aggiornaMovimentiEntrateDaEsterno(nomiColonne, 10);
					}
					//chiude la dialog e rilascia le risorse
					dispose();
				}
			});
			
			this.add(labelIdEntrate);
			this.add(idEntrate);
			this.add(labelNome);
			this.add(nome);
			this.add(labelDescrizione);
			this.add(descrizione);
			this.add(labelData);
			this.add(data);
			this.add(labelTipoEntrate);
			this.add(tipoEntrata);
			this.add(labelEuro);
			this.add(euro);
			this.add(update);
			this.add(delete);
			setSize(300, 500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setEuro(JTextField euro) {
		DialogEntrateMov.euro = euro;
	}

	public static JTextField getEuro() {
		return euro;
	}

	public static void setData(JTextField data) {
		DialogEntrateMov.data = data;
	}

	public static JTextField getData() {
		return data;
	}

	public static void setDescrizione(JTextField descrizione) {
		DialogEntrateMov.descrizione = descrizione;
	}

	public static JTextField getDescrizione() {
		return descrizione;
	}

	public static void setTipoEntrata(JComboBox tipoEntrata) {
		DialogEntrateMov.tipoEntrata = tipoEntrata;
	}

	public static JComboBox getTipoEntrata() {
		return tipoEntrata;
	}

	public static void setNome(JTextField nome) {
		DialogEntrateMov.nome = nome;
	}

	public static JTextField getNome() {
		return nome;
	}

	public static void setIdEntrate(JTextField idEntrate) {
		DialogEntrateMov.idEntrate = idEntrate;
	}

	public static JTextField getIdEntrate() {
		return idEntrate;
	}

}
