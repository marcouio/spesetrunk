

package view.tabelle;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import view.OggettoVistaBase;
import view.font.TableF;
import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;

public class TabellaUscita extends OggettoVistaBase {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTable table;
	
	private static String[][] primo;
	private static JScrollPane scrollPane;
	

    
	public TabellaUscita() {
        super(new GridLayout(1,0));
        
        
        getDatiPerTabella();

        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane(table);
        
        //Add the scroll pane to this panel.
        add(scrollPane);
        
    }


	private void getDatiPerTabella() {
		Vector<CatSpese> catSpese = CacheCategorie.getSingleton().getVettoreCategorie();
        int numColonne = catSpese.size();
        String[] nomiColonne = new String[numColonne];
        
        for(int i=0; i<catSpese.size(); i++){
        	nomiColonne[i] = catSpese.get(i).getnome(); 
        }
        
        primo = new String[12][numColonne];

        for(int i=0; i<12; i++){
        	for(int x=0; x<catSpese.size(); x++){
        		try {
					
					primo[i][x]= Double.toString(Database.speseMeseCategoria(i+1, catSpese.get(x).getidCategoria()));
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}        	
        }
        table = new TableF(primo, nomiColonne);
        table.setRowHeight(27);
        table.setPreferredScrollableViewportSize(new Dimension(700, 300));
        table.setFillsViewportHeight(true);
	}


    private static void createAndShowGUI() throws Exception {
        //Create and set up the window.
        JFrame frame = new JFrame("TabellaUscita");
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
		TabellaUscita.primo = primo;
	}
	public static JTable getTable() {
		return table;
	}

	public static void setTable(JTable table) {
		TabellaUscita.table = table;
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


	public static JScrollPane getScrollPane() {
		return scrollPane;
	}


	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
}