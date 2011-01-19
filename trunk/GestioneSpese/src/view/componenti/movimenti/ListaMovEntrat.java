package view.componenti.movimenti;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TableF;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Database;
import view.OggettoVistaBase;
import view.componenti.movimenti.AscoltatoreMouseMovEntrate;
import domain.Entrate;
import domain.wrapper.Model;


public class ListaMovEntrat extends view.OggettoVistaBase {
	
	private static final long serialVersionUID = 1L;
	static int numEntry = 10;
	private JButton pulsanteNMovimenti;
	private static String[][]movimenti;
	private JButton deleteButton;
	private JButton updateButton;
	private static JTable table;
	private static JTable table1;
	private static JScrollPane scrollPane;
	private static JTextField campo;

	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		
		frame.getContentPane().add(new ListaMovEntrat());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public ListaMovEntrat() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			final DialogEntrateMov dialog = new DialogEntrateMov();
			this.setLayout(null);
			this.setSize(500, 250);
			this.setPreferredSize(new java.awt.Dimension(576, 288));
			JLabel movim = new LabelTesto("Movimenti:");
			movim.setBounds(166, 5, 89, 30);
			this.add(movim);
			campo = new TextFieldF();
			campo.setBounds(255, 11, 60, 25);
			campo.setText("10");
			numEntry = Integer.parseInt(campo.getText());
			this.add(campo);
			pulsanteNMovimenti = new ButtonF("Cambia");
			pulsanteNMovimenti.setBounds(317, 12, 90, 25);
			this.add(pulsanteNMovimenti);
			

			final String[]nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
			
			//modifica movimenti visibili
			pulsanteNMovimenti.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent e) {
					try{
						Model.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
					}catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Inserire un valore numerico: "+e1.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
			}
		});		
			movimenti = Model.getSingleton().movimentiEntrate(numEntry, Entrate.NOME_TABELLA );
			
			 	table = new TableF(movimenti, nomiColonne);
		        table.setPreferredScrollableViewportSize(new Dimension(800, 450));
		        table.getColumn("idEntrate").setPreferredWidth(70);
		        table.getColumn("euro").setPreferredWidth(90);
		        table.getColumn("nome").setPreferredWidth(120);
		        table.getColumn("data").setPreferredWidth(120);
		        table.getColumn("descrizione").setPreferredWidth(250);
		        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		        table.setRowHeight(26);
		        table.setFillsViewportHeight(true);
		        table.setBounds(30, 100, 500, 200);
		        table.addMouseListener(new AscoltatoreMouseMovEntrate(table));

		        //Create the scroll pane and add the table to it.
		        scrollPane = new JScrollPane();
		        scrollPane.setViewportView(table);

		        //Add the scroll pane to this panel.
		        this.add(scrollPane);
		        scrollPane.setBounds(21, 40, 700, 250);
			    
	        	updateButton = new ButtonF();
	        	this.add(updateButton);
	        	updateButton.setText("Aggiorna");
	        	updateButton.setBounds(193, 427, 95, 21);
	        	
	        	deleteButton = new ButtonF();
	        	this.add(deleteButton);
	        	deleteButton.setText("Cancella");
	        	deleteButton.setBounds(299, 427, 82, 21);
	        	deleteButton.addActionListener(new ActionListener() {
					
				
					public void actionPerformed(ActionEvent e) {
						try{
						dialog.setSize(400, 220);
				        dialog.setVisible(true);
				        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				        //TODO aggiornamenti(tutti)
//				        Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
						}catch (Exception e1) {
							e1.printStackTrace();
						}
//				        Database.aggiornaTabellaEntrate();
//						SottoPannelloDatiEntrate.getEnAnCorso().setText(Double.toString(Database.EAnnuale()));
//						SottoPannelloDatiEntrate.getEnMeCorso().setText(Double.toString(Database.EMensileInCorso()));
//						SottoPannelloDatiEntrate.getEntrateMesePrec().setText(Double.toString(Database.Emensile()));
					}
				});
	        	
	        	updateButton.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						dialog.setSize(400, 220);
				        dialog.setVisible(true);	
				        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				        try{
//				        	TODO aggiornamenti
//					        Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
						}catch (Exception e1) {
							e1.printStackTrace();
						}
//				        Database.aggiornaTabellaEntrate();
//				        SottoPannelloDatiEntrate.getEnAnCorso().setText(Double.toString(Database.EAnnuale()));
//				        SottoPannelloDatiEntrate.getEnMeCorso().setText(Double.toString(Database.EMensileInCorso()));
//				        SottoPannelloDatiEntrate.getEntrateMesePrec().setText(Double.toString(Database.Emensile()));
					}
				});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static JTextField getCampo() {
		return campo;
	}

	public void setCampo(JTextField campo) {
		ListaMovEntrat.campo = campo;
	}

	public static int getNumEntry() {
		return ListaMovEntrat.numEntry;
	}

	public void setNumEntry(int numEntry) {
		ListaMovEntrat.numEntry = numEntry;
	}

	public JButton getPulsante() {
		return pulsanteNMovimenti;
	}

	public void setPulsante(JButton pulsante) {
		this.pulsanteNMovimenti = pulsante;
	}

	public static JTable getTable1() {
		return table1;
	}

	public static void setTable1(JTable table1) {
		ListaMovEntrat.table1 = table1;
	}

	public static JScrollPane getScrollPane() {
		return scrollPane;
	}

	public static void setScrollPane(JScrollPane scrollPane) {
		ListaMovEntrat.scrollPane = scrollPane;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setDeleteButton(JButton deleteButton) {
		this.deleteButton = deleteButton;
	}

	public void setUpdateButton(JButton updateButton) {
		this.updateButton = updateButton;
	}

	public static String[][] getMovimenti() {
		return movimenti;
	}

	public static void setMovimenti(String[][] movimenti) {
		ListaMovEntrat.movimenti = movimenti;
	}
	public static JTable getTable() {
		return table;
	}

	public static void setTable(JTable table) {
		ListaMovEntrat.table = table;
	}
	
	public JButton getUpdateButton() {
		return updateButton;
	}
	
	public JButton getDeleteButton() {
		return deleteButton;
	}



}