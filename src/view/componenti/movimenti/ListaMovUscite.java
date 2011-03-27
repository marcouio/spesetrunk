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

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TableF;
import view.font.TextFieldF;
import business.AltreUtil;
import domain.SingleSpesa;
import domain.wrapper.Model;


public class ListaMovUscite extends view.OggettoVistaBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	int numUscite = 10;
	private ButtonF pulsanteNMovimenti;
	private static JTextField campo;
	private ButtonF updateButton;

	private ButtonF deleteButton;
	private static String[][]movimenti;
	private static JTable table;
	private static JTable table1;
	


	private static JScrollPane scrollPane;
	
	
	public ListaMovUscite() {
		final DialogUsciteMov dialog = new DialogUsciteMov();
		this.setSize(500, 250);
		this.setPreferredSize(new Dimension(800, 502));
		this.setLayout(null);
		
		JLabel movim = new LabelTesto("Movimenti:");
		movim.setBounds(166, 17, 89, 30);
		this.add(movim);
		campo = new TextFieldF();
		campo.setBounds(255, 23, 60, 25);
		campo.setText("10");
		
		numUscite = Integer.parseInt(campo.getText());
		this.add(campo);
		pulsanteNMovimenti = new ButtonF();
		pulsanteNMovimenti.setText("Cambia");
		pulsanteNMovimenti.setBounds(317, 24, 90, 25);
		this.add(pulsanteNMovimenti);
		
		final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(SingleSpesa.NOME_TABELLA);
		
		//n movimenti visibili
		pulsanteNMovimenti.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try{
					Model.aggiornaMovimentiUsciteDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Inserire un valore numerico: "+e1.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				}
						
		}
	});		

		movimenti = Model.getSingleton().movimentiUscite(numUscite, SingleSpesa.NOME_TABELLA );
		
	
		table = new TableF(movimenti, nomiColonne);
		table.setPreferredScrollableViewportSize(new Dimension(800, 450));
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumn("idSpesa").setPreferredWidth(70);
        table.getColumn("euro").setPreferredWidth(90);
        table.getColumn("nome").setPreferredWidth(120);
        table.getColumn("data").setPreferredWidth(120);
        table.getColumn("descrizione").setPreferredWidth(250);
        table.setRowHeight(26);
        table.setBounds(30, 100, 500, 200);
        table.addMouseListener(new AscoltatoreMouseMovUscite(table));
        		        
        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);

        //Add the scroll pane to this panel.
        this.add(scrollPane);
        scrollPane.setBounds(21, 70, 664, 337);

	        updateButton = new ButtonF();
        	this.add(updateButton);
        	updateButton.setText("Aggiorna");
        	updateButton.setBounds(193, 427, 95, 21);
        	
        	deleteButton = new ButtonF();
        	this.add(deleteButton);
        	deleteButton.setText("Cancella");
        	deleteButton.setBounds(299,427, 82, 21);
        	deleteButton.addActionListener(new ActionListener() {
				
			
				public void actionPerformed(ActionEvent e) {
					
					dialog.setSize(400, 220);
			        dialog.setVisible(true);
			        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					try {
//						TODO aggiornamenti
//						Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
        	
        	updateButton.addActionListener(new ActionListener() {
				

				public void actionPerformed(ActionEvent e) {
					
					dialog.setSize(400, 220);
			        dialog.setVisible(true);	
			        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					try {
						//TODO aggiornamenti
//						Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});

		
	}
	 private static void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame frame = new JFrame("Lista Movimenti Uscite");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //Create and set up the content pane.
	        ListaMovUscite newContentPane = new ListaMovUscite();
	        newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }
	    public static String[][] getMovimenti() {
			return movimenti;
		}

		public static void setMovimenti(String[][] movimenti) {
			ListaMovUscite.movimenti = movimenti;
		}
		public static JTable getTable() {
			return table;
		}

		public static void setTable(JTable table) {
			ListaMovUscite.table = table;
		}
		/**
		 * @return the numUscite
		 */
		public int getNumUscite() {
			return numUscite;
		}
		/**
		 * @param numUscite the numUscite to set
		 */
		public void setNumUscite(int numUscite) {
			this.numUscite = numUscite;
		}
		/**
		 * @return the pulsante
		 */
		public JButton getPulsante() {
			return pulsanteNMovimenti;
		}
		/**
		 * @param pulsante the pulsante to set
		 */
		public void setPulsante(ButtonF pulsante) {
			this.pulsanteNMovimenti = pulsante;
		}
		/**
		 * @return the campo
		 */
		public static JTextField getCampo() {
			return campo;
		}
		/**
		 * @param campo the campo to set
		 */
		public static void setCampo(JTextField campo) {
			ListaMovUscite.campo = campo;
		}
		/**
		 * @return the updateButton
		 */
		public JButton getUpdateButton() {
			return updateButton;
		}
		/**
		 * @param updateButton the updateButton to set
		 */
		public void setUpdateButton(ButtonF updateButton) {
			this.updateButton = updateButton;
		}

		/**
		 * @return the deleteButton
		 */
		public JButton getDeleteButton() {
			return deleteButton;
		}
		/**
		 * @param deleteButton the deleteButton to set
		 */
		public void setDeleteButton(ButtonF deleteButton) {
			this.deleteButton = deleteButton;
		}
		/**
		 * @return the table1
		 */
		public static JTable getTable1() {
			return table1;
		}
		/**
		 * @param table1 the table1 to set
		 */
		public static void setTable1(JTable table1) {
			ListaMovUscite.table1 = table1;
		}
		/**
		 * @return the scrollPane
		 */
		public static JScrollPane getScrollPane() {
			return scrollPane;
		}
		/**
		 * @param scrollPane the scrollPane to set
		 */
		public static void setScrollPane(JScrollPane scrollPane) {
			ListaMovUscite.scrollPane = scrollPane;
		}
		/**
		 * @return the serialversionuid
		 */
		public static long getSerialversionuid() {
			return serialVersionUID;
		}



	    public static void main(String[] args) {

	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }

}