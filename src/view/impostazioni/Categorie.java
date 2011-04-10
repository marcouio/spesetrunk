package view.impostazioni;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.OggettoVistaBase;
import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.Controllore;
import business.Database;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import business.comandi.CommandDeleteCategoria;
import business.comandi.CommandInserisciCategoria;
import business.comandi.CommandUpdateCategoria;
import domain.AbstractOggettoEntita;
import domain.CatSpese;
import domain.Gruppi;
import domain.wrapper.Model;

public class Categorie extends OggettoVistaBase{

	private CatSpese categoria = null;
	private JTextArea descrizione;
	private JComboBox importanza;
	private ButtonF inserisci;
	private JTextField nome;
	private JButton aggiorna;
	private static JComboBox comboCategorie;
	private ButtonF cancella;
	private static Vector<CatSpese> categorieSpesa;
	Database db = Database.getSingleton();
	private JComboBox comboGruppi;
	



	private static final long serialVersionUID = 1L;
	private static Categorie singleton = null;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Categorie());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	

	private Categorie() {
		super();
		initGUI();
	}
	
	public static final Categorie getSingleton() {
        if (singleton == null) {
            synchronized (Categorie.class) {
                if (singleton == null) {
                    singleton = new Categorie();
                }
            } // if
        } // if
        return singleton;
    } // getSingleton() 

		
	private void initGUI() {
		try {

			this.setPreferredSize(new Dimension(355, 550));
			this.setVisible(true);
			this.setLayout(null);
			
			//intestazione
			JLabel intestazione = new LabelTitolo();
			intestazione.setBounds(100, 44, 175, 20);
			intestazione.setText("Pannello Categorie");
			
			// Label nome
			JLabel labelNome = new LabelTesto();
			labelNome.setBounds(63, 75, 100, 25);
			labelNome.setText("Categoria");
			
			// Label descrizione
			JLabel labelDescrizione = new LabelTesto();
			labelDescrizione.setBounds(63, 128, 90, 25);
			labelDescrizione.setText("Descrizione");
			
			// Label Importanza
			JLabel labelCategorie = new LabelTesto();
			labelCategorie.setBounds(63, 247, 100, 25);
			labelCategorie.setText("Importanza");
			
			// Nome Spesa
			nome = new TextFieldF();
			nome.setBounds(63, 100, 206, 26);
			
			// Descrizione			
			descrizione = new TextAreaF("Inserisci la descrizione della categoria", 50, 25);
			descrizione.setLineWrap(true);
			descrizione.setWrapStyleWord(true);
			descrizione.setBounds(63, 154, 206, 88);

			//importanza Spesa
			importanza = new JComboBox();
			importanza.addItem("");
			importanza.addItem("Futili");
			importanza.addItem("Variabili");
			importanza.addItem("Fisse");
			importanza.setBounds(63, 273, 206, 25);
			
			//bottone invia
			inserisci = new ButtonF();
			inserisci.setBounds(63, 368, 206, 25);
			inserisci.setText("Inserisci Categoria");
			
			// Label Combo Categorie
			JLabel labelComboCategorie = new LabelTesto();
			labelComboCategorie.setBounds(63, 415, 100, 25);
			labelComboCategorie.setText("Lista Categorie");
			
			LabelTesto lbltstGruppo = new LabelTesto();
			lbltstGruppo.setText("Gruppo");
			lbltstGruppo.setBounds(63, 302, 100, 25);
			add(lbltstGruppo);
			
			
			Vector<Gruppi> vettoreGruppi=CacheGruppi.getSingleton().getVettoreGruppi();
			//combo gruppi
			comboGruppi = new JComboBox();			
			for(int i=0; i<vettoreGruppi.size(); i++){
				comboGruppi.addItem(vettoreGruppi.get(i));
			}
			comboGruppi.setBounds(63, 328, 206, 25);
			add(comboGruppi);
			
			categorieSpesa=CacheCategorie.getSingleton().getVettoreCategoriePerCombo(CacheCategorie.getSingleton().getAllCategorie());
			comboCategorie = new JComboBox(categorieSpesa);
			comboCategorie.setBounds(63, 443, 206, 25);
			comboCategorie.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					
					if (comboCategorie.getSelectedIndex() != 0 && comboCategorie.getSelectedItem()!=null) {
						categoria = (CatSpese) comboCategorie.getSelectedItem();
						nome.setText(categoria.getnome());
						descrizione.setText(categoria.getdescrizione());
						importanza.setSelectedItem(categoria.getimportanza());
						comboGruppi.setSelectedItem(categoria.getGruppi());
					}
				}
			});
			
			//bottone Update
			aggiorna = new ButtonF();
			aggiorna.setBounds(63, 484, 100, 25);
			aggiorna.setText("Aggiorna");
			aggiorna.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					CatSpese oldCategoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categoria.getidCategoria()));
					CatSpese newCategoria = new CatSpese();
					
					if(comboCategorie.getSelectedItem()!=null){
						if(categoria!=null)
							newCategoria.setidCategoria(categoria.getidCategoria());
						
						if(!nome.equals(""))
							newCategoria.setnome(nome.getText());
						else newCategoria.setnome(categoria.getnome());
						
						if(!descrizione.equals(""))
							newCategoria.setdescrizione(descrizione.getText());
						else newCategoria.setdescrizione(categoria.getdescrizione());
						
						if(!importanza.equals(""))
							newCategoria.setimportanza((String) importanza.getSelectedItem());	
						else newCategoria.setimportanza(categoria.getimportanza());
						
						if(comboGruppi.getSelectedItem()!=""){
							Gruppi gruppo = (Gruppi) comboGruppi.getSelectedItem();
							newCategoria.setGruppi(gruppo);
						}else{
							Gruppi gruppo = CacheGruppi.getSingleton().getGruppoPerNome("No Gruppo");
							newCategoria.setGruppi(gruppo);
						}
						try{
							if(Controllore.getSingleton().getCommandManager().invocaComando(new CommandUpdateCategoria(oldCategoria, newCategoria), "tutto")){
								JOptionPane.showMessageDialog(null,"Operazione eseguita correttamente", "Perfetto!", JOptionPane.INFORMATION_MESSAGE );
								Database.aggiornaCategorie(categoria);
								Database.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
							}
						}catch (Exception e22) {
							e22.printStackTrace();
							JOptionPane.showMessageDialog(null, "Inserisci i dati correttamente: "+e22.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
						}
					}else{
						JOptionPane.showMessageDialog(null, "Impossibile aggiornare una categoria inesistente!", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
				}
			});
			
			//bottone insert
			inserisci.addActionListener(new ActionListener() {
				
				private CatSpese categoria1;

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(!(descrizione.getText().equals("")) && !(importanza.getSelectedItem().equals("")) && !(nome.getText().equals(""))
							&& importanza.getSelectedIndex() != 0){
						
						categoria1 = new CatSpese();
						categoria1.setdescrizione(descrizione.getText());
						categoria1.setimportanza((String) importanza.getSelectedItem());
						categoria1.setnome(nome.getText());
						categoria1.setGruppi((Gruppi) comboGruppi.getSelectedItem());
						if(Controllore.getSingleton().getCommandManager().invocaComando(new CommandInserisciCategoria(categoria1), "tutto"))
							JOptionPane.showMessageDialog(null, "Categoria inserita correttamente", "Perfetto", JOptionPane.INFORMATION_MESSAGE);
						
						comboCategorie.addItem(categoria1);
						Map<String, AbstractOggettoEntita> cache = CacheCategorie.getSingleton().getCache();
						cache.put(Integer.toString(categoria1.getidCategoria()), categoria1);
						Database.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo(cache));
						
						
						
					}else
						JOptionPane.showMessageDialog(null, "E' necessario riempire tutti i campi", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				}
			});
			//bottone cancella
			cancella = new ButtonF();
			cancella.setText("Cancella");
			cancella.setBounds(169, 484, 100, 25);
			cancella.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(comboCategorie.getSelectedItem()!=null && categoria!=null){
						if(Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteCategoria(categoria),"tutto")){
							JOptionPane.showMessageDialog(null, "Categoria cancellata!", "Perfetto!", JOptionPane.INFORMATION_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, "Impossibile cancellare una categoria inesistente!", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
					log.fine("Cancellata categoria "+ categoria);
					
					comboCategorie.setModel(new DefaultComboBoxModel(CacheCategorie.getSingleton().getVettoreCategoriePerCombo(cache)));
					
					Database.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo());
				}
			});
			
			this.add(cancella);
			this.add(inserisci);
			this.add(labelDescrizione);
			this.add(labelCategorie);
			this.add(labelNome);
			this.add(descrizione);
			this.add(importanza);
			this.add(intestazione);
			this.add(nome);
			this.add(labelComboCategorie);
			this.add(comboCategorie);
			this.add(aggiorna);
			
			JSeparator separator = new JSeparator();
			separator.setOrientation(JSeparator.VERTICAL);
			separator.setBounds(319, 33, 13, 505);
			add(separator);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Vector<CatSpese> getCategorieSpesa() {
		return categorieSpesa;
	}

	public static void setCategorieSpesa(Vector<CatSpese> categorieSpesa) {
		Categorie.categorieSpesa = categorieSpesa;
	}

	/**
	 * @return the comboCategorie
	 */
	public static JComboBox getComboCategorie() {
		return comboCategorie;
	}

	/**
	 * @param comboCategorie the comboCategorie to set
	 */
	public static void setComboCategorie(JComboBox comboCategorie) {
		Categorie.comboCategorie = comboCategorie;
	}


	public JComboBox getComboGruppi() {
		return comboGruppi;
	}


	public void setComboGruppi(JComboBox comboGruppi) {
		this.comboGruppi = comboGruppi;
	}
}