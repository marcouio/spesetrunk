package view.impostazioni;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;

import view.OggettoVistaBase;
import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.Database;
import business.cache.CacheGruppi;
import domain.Gruppi;
import domain.wrapper.Model;

public class SettingGruppi extends OggettoVistaBase {
	
	private static SettingGruppi singleton;
	private Gruppi gruppi = null;
	private Database db = Database.getSingleton();
	private JComboBox comboGruppi;
	
	public static final SettingGruppi getSingleton() {
        if (singleton == null) {
            synchronized (Categorie.class) {
                if (singleton == null) {
                    singleton = new SettingGruppi();
                }
            } // if
        } // if
        return singleton;
    } // getSingleton() 
	
	public SettingGruppi() {
		setLayout(null);
		
		LabelTesto lbltstGruppo = new LabelTesto();
		lbltstGruppo.setText("Gruppo");
		lbltstGruppo.setBounds(89, 61, 100, 25);
		add(lbltstGruppo);
		
		final TextFieldF nome = new TextFieldF();
		nome.setBounds(89, 86, 206, 26);
		add(nome);
		
		LabelTesto labelTesto_1 = new LabelTesto();
		labelTesto_1.setText("Descrizione");
		labelTesto_1.setBounds(89, 114, 90, 25);
		add(labelTesto_1);
		
		final TextAreaF descrizione = new TextAreaF("Inserisci la descrizione della spesa", 50, 25);
		descrizione.setWrapStyleWord(true);
		descrizione.setLineWrap(true);
		descrizione.setAutoscrolls(true);
		descrizione.setBounds(89, 140, 206, 88);
		add(descrizione);
		
		ButtonF btnfInserisciGruppo = new ButtonF();
		btnfInserisciGruppo.setText("Inserisci Gruppo");
		btnfInserisciGruppo.setBounds(90, 251, 206, 25);
		add(btnfInserisciGruppo);
		
		LabelTitolo label = new LabelTitolo("GRUPPI");
		label.setText("Gruppi");
		label.setBounds(274, 34, 90, 27);
		add(label);
		
		Vector<Gruppi> vettoreGruppi = CacheGruppi.getSingleton().getVettoreGruppi();
		comboGruppi = new JComboBox();
		comboGruppi.addItem("");
		
		for(int i=0; i<vettoreGruppi.size(); i++){
			comboGruppi.addItem(vettoreGruppi.get(i));
		}
		
		comboGruppi.setBounds(351, 85, 206, 25);
		add(comboGruppi);
		comboGruppi.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (comboGruppi.getSelectedIndex() != 0 && comboGruppi.getSelectedItem()!=null) {
					gruppi = (Gruppi) comboGruppi.getSelectedItem();
					nome.setText(gruppi.getnome());
					descrizione.setText(gruppi.getdescrizione());
				}
			}
		});
		
		LabelTesto lbltstListaGruppi = new LabelTesto();
		lbltstListaGruppi.setText("Lista Gruppi");
		lbltstListaGruppi.setBounds(351, 57, 100, 25);
		add(lbltstListaGruppi);
		
		ButtonF aggiorna = new ButtonF();
		aggiorna.setText("Aggiorna");
		aggiorna.setBounds(351, 126, 100, 25);
		add(aggiorna);
		
		ButtonF cancella = new ButtonF();
		cancella.setText("Cancella");
		cancella.setBounds(457, 126, 100, 25);
		add(cancella);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 12, 575, 25);
		add(separator);
		
		aggiorna.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<String, String> campi = new HashMap<String, String>();
				HashMap<String, String> clausole = new HashMap<String, String>();
				if(comboGruppi.getSelectedIndex()!=0 && comboGruppi.getSelectedItem()!=null){
					if(gruppi!=null)
						clausole.put(Gruppi.ID, Integer.toString(gruppi.getidGruppo()));
						campi.put(Gruppi.NOME, nome.getText());
					if(descrizione!=null)
						campi.put(Gruppi.DESCRIZIONE, descrizione.getText());
			
					try{
						if(db.eseguiIstruzioneSql("UPDATE", Gruppi.NOME_TABELLA, campi, clausole))
							JOptionPane.showMessageDialog(null,"Operazione eseguita correttamente", "Perfetto!", JOptionPane.INFORMATION_MESSAGE );
//						TODO verificare se ricarica deve essere true
							Model.getSingleton().getGruppiCombo(true);
//							Database.aggiornaCategorie(spese);
//							Database.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
					}catch (Exception e22) {
						JOptionPane.showMessageDialog(null, "Inserisci i dati correttamente: "+e22.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
				}else{
					JOptionPane.showMessageDialog(null, "Impossibile aggiornare una categoria inesistente!", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				}
				
			}
		});
		
		cancella.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboGruppi.getSelectedIndex()!=0 && comboGruppi.getSelectedItem()!=null && gruppi!=null){
					Model.getSingleton().getModelUscita().delete(gruppi.getidGruppo());
				}else{
					JOptionPane.showMessageDialog(null, "Impossibile cancellare una categoria inesistente!", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				}
				log.fine("Cancellata categoria inserita spesa"+ gruppi);
				comboGruppi.removeItem(gruppi);
//				Database.aggiornamentoComboBox(new Database().Spese());
			}
		});
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new SettingGruppi());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setBounds(10, 10, 500,500);
		frame.setVisible(true);
	}

	public JComboBox getComboGruppi() {
		return comboGruppi;
	}

	public void setComboGruppi(JComboBox comboGruppi) {
		this.comboGruppi = comboGruppi;
	}
}
