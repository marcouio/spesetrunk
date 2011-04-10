package view.impostazioni;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.GeneralFrame;
import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextFieldF;
import business.Controllore;
import business.DBUtil;
import business.Database;
import business.cache.CacheLookAndFeel;
import domain.Entrate;
import domain.Lookandfeel;
import domain.SingleSpesa;
import domain.wrapper.Model;

public class Impostazioni extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Impostazioni singleton;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Impostazioni());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static final Impostazioni getSingleton() {
        if (singleton == null) {
            synchronized (Impostazioni.class) {
                if (singleton == null) {
                    singleton = new Impostazioni();
                }
            } // if
        } // if
        return singleton;
    } // getSingleton() 


	private JTextField dataOdierna;
	private JTextField utente;
	private ArrayList<String> listaLook;
	private JComboBox comboLook;
	private TextFieldF annotextField;
	private static int anno = new GregorianCalendar().get(Calendar.YEAR);
//	private JTextField txtGen;
	private static JTextField caricaDatabase;
	public Impostazioni() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			setPreferredSize(new Dimension(626, 250));
			JLabel calendario = new LabelTesto("Data Odierna");
			calendario.setBounds(24, 111, 87, 14);
			dataOdierna = new TextFieldF();
			dataOdierna.setBounds(142, 107, 113, 27);
			dataOdierna.setEditable(false);
			
			GregorianCalendar gc = new GregorianCalendar();
			dataOdierna.setText(DBUtil.dataToString(gc.getTime(), "dd-MM-yyyy"));
			setLayout(null);
			utente = new TextFieldF();
			utente.setText(Controllore.getSingleton().getUtenteLogin().getusername());
			utente.setBounds(142, 151, 113, 27);
			this.add(calendario);
			this.add(dataOdierna);
			this.add(utente);
			
			JLabel lblImpostaAnno = new LabelTesto("Imposta anno");
			lblImpostaAnno.setBounds(280, 104, 97, 27);
			add(lblImpostaAnno);
			
			ButtonF btnChange = new ButtonF();
			btnChange.setText("Cambia");
			btnChange.setBounds(506, 103, 91, 27);
			btnChange.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						anno = Integer.parseInt(annotextField.getText());

						Database.aggiornamentoPerImpostazioni();
					}catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			add(btnChange);
			
//			JLabel lblGeneraDatabase = new LabelTesto"Genera Database");
//			lblGeneraDatabase.setBounds(24, 156, 103, 14);
//			add(lblGeneraDatabase);
			
//			txtGen = new TextFieldF();
//			txtGen.setBounds(130, 152, 114, 22);
//			add(txtGen);
			
//			JButton btnGenera = new ButtonF("Genera");
//			btnGenera.setBounds(297, 152, 91, 23);
//			add(btnGenera);
//			btnGenera.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent arg0) {
//					new GeneraDB(txtGen.getText());
//				}
//			});
			
			JLabel lblCaricaDatabase = new LabelTesto("Carica Database");
			lblCaricaDatabase.setBounds(24, 208, 113, 14);
			add(lblCaricaDatabase);
			
			final ButtonF btnCarica = new ButtonF();
			btnCarica.setText("Carica");
			btnCarica.setBounds(335, 204, 91, 27);
			add(btnCarica);
			btnCarica.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Database.aggiornamentoPerImpostazioni();
				}
			});
			
			
			caricaDatabase = new TextFieldF();
			caricaDatabase.setBounds(142, 204, 149, 27);
			caricaDatabase.setText("GestioneSpese.sqlite");
			add(caricaDatabase);
			
			ButtonF button = new ButtonF();
			button.setText("...");
			button.setBounds(289, 204, 29, 27);
			add(button);
			
			JLabel label = new LabelTitolo("Impostazioni");
			label.setBounds(476, 44, 120, 34);
			add(label);
			
			
			ButtonF elimina = new ButtonF();
			elimina.setText("Elimina");
			elimina.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(Model.getSingleton().getModelEntrate().deleteAll() && Model.getSingleton().getModelUscita().deleteAll()){
						JOptionPane.showMessageDialog(null,"Ok, tutti i dati sono stati cancellati: puoi ripartire!","Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
						try{
						Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
						Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
						}catch (Exception e) {
							e.getMessage();
						}
					}
				}
			});
			
			
			elimina.setBounds(506, 150, 91, 27);
			add(elimina);
			
			JDesktopPane desktopPane = new JDesktopPane();
			desktopPane.setBounds(96, 163, 1, 1);
			add(desktopPane);
			
			LabelTesto lbltstEliminaTuttiLe = new LabelTesto("Carica Database");
			lbltstEliminaTuttiLe.setText("Elimina dati per entrate e uscite");
			lbltstEliminaTuttiLe.setBounds(280, 151, 232, 27);
			add(lbltstEliminaTuttiLe);
			
			LabelTesto lbltstUtente = new LabelTesto("Data Odierna");
			lbltstUtente.setText("Utente");
			lbltstUtente.setBounds(24, 155, 87, 14);
			add(lbltstUtente);
			
			annotextField = new TextFieldF();
			annotextField.setText(Integer.toString(gc.get(Calendar.YEAR)));
			annotextField.setBounds(377, 103, 113, 27);
			add(annotextField);
			
			CacheLookAndFeel cacheLook = CacheLookAndFeel.getSingleton();
			final Vector<Lookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();
			
			Lookandfeel look=null;
			comboLook = new JComboBox(vettore);
			for(int i=0; i<vettore.size();i++){
				look = vettore.get(i);
				if(look.getusato()==1){
					comboLook.setSelectedIndex(i);
				}
			}
			
			comboLook.setBounds(142, 49, 115, 24);
			add(comboLook);
			comboLook.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String look = "";
					Lookandfeel valoreLook = (Lookandfeel) comboLook.getSelectedItem();
					if(valoreLook!=null){
						look = (String) valoreLook.getvalore();
						
						for(int i=0; i<vettore.size();i++){
							Lookandfeel lookAnd = vettore.get(i);
							lookAnd.setusato(0);
							HashMap<String, String> campi = new HashMap<String, String>();
							HashMap<String, String> clausole = new HashMap<String, String>();
							campi.put(Lookandfeel.USATO, "0");
							clausole.put(Lookandfeel.ID, Integer.toString(lookAnd.getidLook()));
							Database.getSingleton().eseguiIstruzioneSql("update", Lookandfeel.NOME_TABELLA, campi, clausole);
//							new WrapLookAndFeel().update(lookAnd);
						}
						
						valoreLook.setusato(1);
						HashMap<String, String> campi = new HashMap<String, String>();
						HashMap<String, String> clausole = new HashMap<String, String>();
						campi.put(Lookandfeel.USATO, "1");
						clausole.put(Lookandfeel.ID, Integer.toString(valoreLook.getidLook()));
						Database.getSingleton().eseguiIstruzioneSql("update", Lookandfeel.NOME_TABELLA, campi, clausole);
						
//						new WrapLookAndFeel().update(valoreLook);
					}
					else
						look = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
					try {
						UIManager.setLookAndFeel(look);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 catch (ClassCastException e1) {
							comboLook.setSelectedIndex(0);
							e1.printStackTrace();
						}
					
					GeneralFrame frame = GeneralFrame.getSingleton();
					SwingUtilities.updateComponentTreeUI(frame);
					frame.setBounds(0, 0, 1000, 650);

				}
			});
			
			JLabel labelLook = new JLabel("Look");
			labelLook.setBounds(24, 54, 70, 15);
			add(labelLook);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser fileopen = new JFileChooser();
				    FileFilter filter = new FileNameExtensionFilter("db files", "db");
				    fileopen.addChoosableFileFilter(filter);

				    int ret = fileopen.showDialog(null, "Open file");

				    if (ret == JFileChooser.APPROVE_OPTION) {
				      File file = fileopen.getSelectedFile();
				      caricaDatabase.setText(file.getName());
				    }
					
				}
			});
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public TextFieldF getAnnotextField() {
		return annotextField;
	}

	public void setAnnotextField(TextFieldF annotextField) {
		this.annotextField = annotextField;
	}

	/**
	 * @return the dataOdierna
	 */
	public JTextField getDataOdierna() {
		return dataOdierna;
	}

	/**
	 * @param dataOdierna the dataOdierna to set
	 */
	public void setDataOdierna(JTextField dataOdierna) {
		this.dataOdierna = dataOdierna;
	}

	/**
	 * @return the listaLook
	 */
	public ArrayList<String> getListaLook() {
		return listaLook;
	}

	/**
	 * @param listaLook the listaLook to set
	 */
	public void setListaLook(ArrayList<String> listaLook) {
		this.listaLook = listaLook;
	}

	public static int getAnno() {
		return anno;
	}

	public static void setAnno(int anno) {
		Impostazioni.anno = anno;
	}
	public static JTextField getCaricaDatabase() {
		return caricaDatabase;
	}

	public static void setCaricaDatabase(JTextField caricaDatabase) {
		Impostazioni.caricaDatabase = caricaDatabase;
	}

	/**
	 * @return the utente
	 */
	public JTextField getUtente() {
		return utente;
	}

	/**
	 * @param utente the utente to set
	 */
	public void setUtente(JTextField utente) {
		this.utente = utente;
	}

	/**
	 * @return the comboLook
	 */
	public JComboBox getComboLook() {
		return comboLook;
	}

	/**
	 * @param comboLook the comboLook to set
	 */
	public void setComboLook(JComboBox comboLook) {
		this.comboLook = comboLook;
	}
}
