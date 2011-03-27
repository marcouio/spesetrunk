

package view.tabelle;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import view.OggettoVistaBase;
import view.font.TableF;
import business.Database;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import domain.CatSpese;
import domain.Gruppi;

public class TabellaUscitaGruppi extends OggettoVistaBase {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTable table;
	
	private static String[][] primo;
		

    
	public TabellaUscitaGruppi() {
        super(new GridLayout(1,0));
        
        
        getDatiPerTabella();

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        
        //Add the scroll pane to this panel.
        add(scrollPane);
        
    }


	private void getDatiPerTabella() {
		Vector<Gruppi> gruppi = null;
		CacheGruppi cacheGruppi = CacheGruppi.getSingleton();
		if(cacheGruppi!=null)
			gruppi = CacheGruppi.getSingleton().getVettoreGruppiSenzaZero();
//		Database.speseMeseGruppo(mese, gruppo)
		Vector<CatSpese> catSpese = CacheCategorie.getSingleton().getCategorieSenzaGruppo();
		
        int numColonne = catSpese.size()+gruppi.size();
        ArrayList<String> nomiColonne = new ArrayList<String>();
        
        for(int i=0; i<catSpese.size(); i++){
        	CatSpese categoria = catSpese.get(i);
        	nomiColonne.add(categoria.getnome());        	
        }
        
        for(int i=0; i<gruppi.size(); i++){
        	Gruppi gruppo = gruppi.get(i);
        	if(gruppo.getidGruppo()!=0){
        		nomiColonne.add(gruppo.getnome());
        	}
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
        for(int i=0; i<12; i++){
        	int y=0;
        	for(int x=catSpese.size(); x<numColonne; x++){
        		primo[i][x]= Double.toString(Database.speseMeseGruppo(i+1, gruppi.get(y).getidGruppo()));
        		y++;
        	}
        }
        table = new TableF(primo, nomiColonne.toArray());
        table.setRowHeight(27);
        table.setPreferredScrollableViewportSize(new Dimension(700, 300));
        table.setFillsViewportHeight(true);
	}


    private static void createAndShowGUI() throws Exception {
        //Create and set up the window.
        JFrame frame = new JFrame("TabellaUscita");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TabellaUscitaGruppi newContentPane = new TabellaUscitaGruppi();
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
		TabellaUscitaGruppi.primo = primo;
	}
	public static JTable getTable() {
		return table;
	}

	public static void setTable(JTable table) {
		TabellaUscitaGruppi.table = table;
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