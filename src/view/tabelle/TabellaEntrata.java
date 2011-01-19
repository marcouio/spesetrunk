

package view.tabelle;


import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import view.font.TableF;

import business.Database;
import view.OggettoVistaBase;

public class TabellaEntrata extends OggettoVistaBase {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTable table;
	
	private static String[][] primo;
	private static String[] nomiColonne = null;

    
	public static String[] getNomiColonne() {
		return nomiColonne;
	}

	public static void setNomiColonne(String[] nomiColonne) {
		TabellaEntrata.nomiColonne = nomiColonne;
	}

	public TabellaEntrata() throws Exception {
		 super(new GridLayout(1,0));
        
//        Vector<Entrate> Entrate = new Database().entrate();
        nomiColonne = new String[2];
        
        
        	nomiColonne[0] = "Fisse";
        	nomiColonne[1] = "Variabili";
        
        
        primo = new String[12][2];
        
        for(int i=0; i<12; i++){
        	for(int x=0; x<2; x++){
        		primo[i][x]= Double.toString(Database.getSingleton().entrateMeseTipo((i+1), nomiColonne[x]));
        	}        	
        }

        table = new TableF(primo, nomiColonne);
        table.setRowHeight(27);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    private static void createAndShowGUI() throws Exception {
        //Create and set up the window.
        JFrame frame = new JFrame("Tabella Entrata");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TabellaUscita newContentPane = new TabellaUscita();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    public static String[][] getPrimo() {
		return primo;
	}

	public static void setPrimo(String[][] primo) {
		TabellaEntrata.primo = primo;
	}
	public static JTable getTable() {
		return table;
	}

	public static void setTable(JTable table) {
		TabellaEntrata.table = table;
	}



    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
    }
}