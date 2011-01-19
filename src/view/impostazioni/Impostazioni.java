package view.impostazioni;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.OggettoVistaBase;
import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.Controllore;
import business.DBUtil;
import domain.wrapper.Model;

public class Impostazioni extends OggettoVistaBase {

	
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
	private static int anno = new GregorianCalendar().get(Calendar.YEAR);
//	private JTextField txtGen;
	private static JTextField caricaDatabase;
	public Impostazioni() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			setPreferredSize(new Dimension(626, 550));
			JLabel calendario = new LabelTesto("Data Odierna");
			calendario.setBounds(24, 111, 87, 14);
			dataOdierna = new TextFieldF();
			dataOdierna.setBounds(142, 107, 78, 23);
			dataOdierna.setEditable(false);
			
			GregorianCalendar gc = new GregorianCalendar();
			dataOdierna.setText(DBUtil.dataToString(gc.getTime(), "dd-MM-yyyy"));
			setLayout(null);
			utente = new TextFieldF();
			utente.setText(Controllore.getSingleton().getUtenteLogin().getusername());
			utente.setBounds(142, 151, 113, 22);
			this.add(calendario);
			this.add(dataOdierna);
			this.add(utente);
			
			JLabel lblImpostaAnno = new LabelTesto("Imposta anno");
			lblImpostaAnno.setBounds(292, 109, 97, 14);
			add(lblImpostaAnno);
			
			ButtonF btnChange = new ButtonF("Cambia");
			btnChange.setBounds(506, 103, 91, 23);
			btnChange.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						anno = Integer.parseInt(utente.getText());
						//TODO aggiornamenti
//						Database.aggiornamentoPerImpostazioni();
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
			
			final ButtonF btnCarica = new ButtonF("Carica");
			btnCarica.setBounds(297, 202, 91, 23);
			add(btnCarica);
			btnCarica.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO aggiornamenti
//					Database.aggiornamentoPerImpostazioni();
				}
			});
			
			
			caricaDatabase = new TextFieldF();
			caricaDatabase.setBounds(142, 204, 114, 22);
			caricaDatabase.setText("GestioneSpese.sqlite");
			add(caricaDatabase);
			
			ButtonF button = new ButtonF("...");
			button.setBounds(251, 204, 29, 22);
			add(button);
			
			JLabel label = new LabelTitolo("Impostazioni");
			label.setBounds(268, 44, 120, 34);
			add(label);
			
			JTextArea textArea = new TextAreaF();
			StringBuffer sb = new StringBuffer();
			sb.append("SUGGERIMENTI\n\n- Per l'utilizzo della consolle sql, le tabelle sono:");
			sb.append("entrate, single_spesa, cat_spese");
			sb.append("\n\n");
			sb.append("- Per eliminare o aggiornare una spesa o una entrata vai sul pannello \"movimenti\"");
			sb.append("\n\n");
			sb.append("- Per maggiori informazioni vai sul pannello di \"help\"");
			textArea.setText(sb.toString());
			textArea.setEditable(false);
			// specifica se �true� di andare a capo automaticamente a fine riga
			textArea.setLineWrap(true);
			// va a capo con la parola se �true� o col singolo carattere se �false�
			textArea.setWrapStyleWord(true);
			textArea.setAutoscrolls(true);
			textArea.setBounds(24, 249, 573, 265);
			add(textArea);
			
			ButtonF button_1 = new ButtonF("Elimina");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(Model.getSingleton().getModelEntrate().deleteAll() && Model.getSingleton().getModelUscita().deleteAll()){
						JOptionPane.showMessageDialog(null,"Ok, tutti i dati sono stati cancellati: puoi ripartire!","Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
						try{
//							TODO aggiornamenti
//						Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
//						Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
						}catch (Exception e) {
							e.getMessage();
						}
					}
				}
			});
			
			
			button_1.setBounds(506, 150, 91, 23);
			add(button_1);
			
			JDesktopPane desktopPane = new JDesktopPane();
			desktopPane.setBounds(96, 163, 1, 1);
			add(desktopPane);
			
			LabelTesto lbltstEliminaTuttiLe = new LabelTesto("Carica Database");
			lbltstEliminaTuttiLe.setText("Elimina tutte le entrate e tutte le uscite");
			lbltstEliminaTuttiLe.setBounds(292, 157, 211, 14);
			add(lbltstEliminaTuttiLe);
			
			LabelTesto lbltstUtente = new LabelTesto("Data Odierna");
			lbltstUtente.setText("Utente");
			lbltstUtente.setBounds(24, 155, 87, 14);
			add(lbltstUtente);
			
			TextFieldF anno = new TextFieldF();
			anno.setText(Integer.toString(gc.get(Calendar.YEAR)));
			anno.setBounds(377, 103, 113, 22);
			add(anno);
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
}
