package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextAreaF;

import business.DBUtil;
import business.Database;
import view.OggettoVistaBase;



public class NewSql extends OggettoVistaBase {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JTextArea areaSql;
	private JTextArea result;
	private JLabel labelResult;
	private JButton bottone;
	private JButton bottoneSvuota;
	private JScrollPane jsp = null;
	private String totale = "";

	private String riga;

	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new NewSql());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public NewSql() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(950, 750));
			this.setLayout(null);
			
				
				label = new LabelTesto();
				this.add(label);
				label.setText("Sql string: ");
				label.setBounds(34, 19, 70, 21);
				areaSql = new TextAreaF();
				this.add(areaSql);
				areaSql.setBounds(127, 30, 436, 74);
				areaSql.setText("Inserisci qui il tuo codice sql");
				areaSql.setWrapStyleWord(true);
				jsp= new JScrollPane(areaSql);
				jsp.setBounds(areaSql.getX(), areaSql.getY(),areaSql.getWidth(), areaSql.getHeight());
				this.add(jsp);
				
				bottoneSvuota = new ButtonF();
				this.add(bottoneSvuota);
				bottoneSvuota.setText("Svuota");
				bottoneSvuota.setBounds(20, 75, 75, 21);
				
				
				bottone = new ButtonF();
				this.add(bottone);
				bottone.setText("Esegui");
				bottone.setBounds(20, 43, 75, 21);
				
			
				result = new TextAreaF();
				this.add(result);
				result.setBounds(52, 161, 850, 350);
				JScrollPane scroll = new JScrollPane(result);
				scroll.setBounds(result.getX(), result.getY(),result.getWidth(), result.getHeight());
				this.add(scroll);
				labelResult = new LabelTitolo();
				labelResult.setFont(new Font("Eras Light ITC", Font.PLAIN, 14));
				this.add(labelResult);
				labelResult.setText("Result");
				labelResult.setBounds(420, 135, 70, 21);
				bottoneSvuota.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						riga="";
						totale="";
						result.setText("");
						
					}
				});
				
				bottone.addActionListener(new ActionListener() {
	
					@SuppressWarnings("rawtypes")
					@Override
					public void actionPerformed(ActionEvent e) {
						String sql = areaSql.getText();
						try{
							HashMap<String, ArrayList> nomi = Database.getSingleton().terminaleSql(sql);
							
							Iterator<String> chiavi = nomi.keySet().iterator();
							
								while(chiavi.hasNext()){
									String chiave = chiavi.next();
									ArrayList lista = nomi.get(chiave);
									riga = ""; 
									String finale ="";
									int i=0;
									for(i=0; i<lista.size(); i++){
										String porzione = DBUtil.creaStringStessaDimensione((String) lista.get(i), 14);
										riga = riga + porzione;
									}
									String trattini="";
									
									for(int x =0; x<(i*15); x++){
										trattini = trattini+"-";
										finale = finale+"*";
									}
									riga = riga+"\n"+trattini+"\n";
									totale = totale+riga;
								}
							result.setFont(new Font("Courier", Font.ITALIC, 10));
							totale = totale +"\n"+"\n";
							result.setText(totale+"\n");
							
						}catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
						}
					}
				});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}