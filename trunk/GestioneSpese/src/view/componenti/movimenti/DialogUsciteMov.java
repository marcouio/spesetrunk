package view.componenti.movimenti;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import business.Controllore;
import business.cache.CacheUscite;
import business.comandi.CommandDeleteSpesa;
import business.comandi.CommandUpdateSpesa;
import domain.CatSpese;
import domain.SingleSpesa;
import domain.wrapper.Model;

public class DialogUsciteMov extends javax.swing.JDialog {

	private static JLabel labelEuro = new LabelTesto("Euro");
	private static JLabel labelData =new LabelTesto("Data");
	private static JLabel labelCategoria = new LabelTesto("Categoria");
	private static JLabel labelDescrizione = new LabelTesto("Descrizione");
	private static JLabel labelNome = new LabelTesto("Nome");
	private static JLabel labelIdSpesa = new LabelTesto("Chiave Uscita");
	private static JTextField euro = new TextFieldF();
	private static JTextField data = new TextFieldF();
	private static JComboBox categoria;
//	private static JTextField categoria = new TextFieldF();
	private static JTextField descrizione = new TextFieldF();
	private static JTextField nome = new TextFieldF();
	private static JTextField idSpesa = new TextFieldF();
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
				DialogUsciteMov inst = new DialogUsciteMov(frame);
				inst.setVisible(true);
			}
		});
	}
	
	public DialogUsciteMov(JFrame frame) {
		super(frame);
		initGUI();
	}
	public DialogUsciteMov() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
//			questo permette di mantenere il focus sulla dialog
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			categoria = new JComboBox(Model.getSingleton().getCategorieCombo(false));
			idSpesa.setEditable(false);
			this.setLayout(new GridLayout(7,2));
			update.setSize(60, 40);
			delete.setSize(60, 40);
			labelData.setSize(100, 40);
			labelDescrizione.setSize(100, 40);
			labelEuro.setSize(100, 40);
			labelIdSpesa.setSize(100, 40);
			labelNome.setSize(100, 40);
			labelCategoria.setSize(100, 40);
			
			
			update.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					String[]nomiColonne = (String[]) AltreUtil.generaNomiColonne(SingleSpesa.NOME_TABELLA);
					JTextField campo = ListaMovUscite.getCampo();
					if(AltreUtil.checkData(data.getText())){
						if(AltreUtil.checkDouble(euro.getText())){
							if(!(idSpesa.getText().equals("")) && !(nome.getText().equals("")) && !(descrizione.getText().equals(""))
									&& categoria.getSelectedItem()!=null && !(data.getText().equals("")) && !(euro.getText().equals(""))){
								
									SingleSpesa oldSpesa = CacheUscite.getSingleton().getSingleSpesa(idSpesa.getText());
									SingleSpesa newSpesa = new SingleSpesa();
									newSpesa.setidSpesa(Integer.parseInt(idSpesa.getText()));
									newSpesa.setData(data.getText());
									newSpesa.setDataIns(oldSpesa.getDataIns());
									newSpesa.setdescrizione(descrizione.getText());
									newSpesa.setinEuro(Double.parseDouble(euro.getText()));
									newSpesa.setnome(nome.getText());
									newSpesa.setCatSpese((CatSpese) categoria.getSelectedItem());
									newSpesa.setUtenti(Controllore.getSingleton().getUtenteLogin());
								try{
									if(Controllore.getSingleton().getCommandManager().invocaComando(new CommandUpdateSpesa(oldSpesa, newSpesa), SingleSpesa.NOME_TABELLA)){
										JOptionPane.showMessageDialog(null,"Operazione eseguita correttamente", "Perfetto!", JOptionPane.INFORMATION_MESSAGE );
									}
								}catch (Exception e22) {
									JOptionPane.showMessageDialog(null, "Inserisci i dati correttamente", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
								}
								Model.aggiornaMovimentiUsciteDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
								//chiude la dialog e rilascia le risorse
								dispose();
							}else
								JOptionPane.showMessageDialog(null, "Tutti i dati devono essere valorizzati", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
						}else 
							JOptionPane.showMessageDialog(null, "Valore in Euro inserito non correttamente", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
				}
			});

			delete.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					if(idSpesa.getText()!=null){
						SingleSpesa ss = CacheUscite.getSingleton().getSingleSpesa(idSpesa.getText());
						if(ss!=null){
							if(Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteSpesa(ss),SingleSpesa.NOME_TABELLA)){
								JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente", "Perfetto!", JOptionPane.INFORMATION_MESSAGE);
							}else
								JOptionPane.showMessageDialog(null, "Inserisci i dati correttamente", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
						}
					}
					//chiude la dialog e rilascia le risorse
					dispose();
				}
			});
			
			this.add(labelIdSpesa);
			this.add(idSpesa);
			this.add(labelNome);
			this.add(nome);
			this.add(labelDescrizione);
			this.add(descrizione);
			this.add(labelData);
			this.add(data);
			this.add(labelCategoria);
			this.add(categoria);
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
		DialogUsciteMov.euro = euro;
	}

	public static JTextField getEuro() {
		return euro;
	}

	public static void setData(JTextField data) {
		DialogUsciteMov.data = data;
	}

	public static JTextField getData() {
		return data;
	}

	public static void setDescrizione(JTextField descrizione) {
		DialogUsciteMov.descrizione = descrizione;
	}

	public static JTextField getDescrizione() {
		return descrizione;
	}

	public static void setCategoria(JComboBox categoria) {
		DialogUsciteMov.categoria = categoria;
	}

	public static JComboBox getCategoria() {
		return categoria;
	}

	public static void setNome(JTextField nome) {
		DialogUsciteMov.nome = nome;
	}

	public static JTextField getNome() {
		return nome;
	}

	public static void setIdSpesa(JTextField idSpesa) {
		DialogUsciteMov.idSpesa = idSpesa;
	}

	public static JTextField getIdSpesa() {
		return idSpesa;
	}

}
