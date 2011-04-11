

package view.tabelle;


import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import view.OggettoVistaBase;
import view.font.TableF;
import business.generatori.GeneratoreDatiTabellaEntrate;

public class TabellaEntrata extends OggettoVistaBase {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String[][] primo;
	private static String[] nomiColonne = {"Fisse","Variabili"};

    
	public static String[] getNomiColonne() {
		return nomiColonne;
	}

	public static void setNomiColonne(String[] nomiColonne) {
		TabellaEntrata.nomiColonne = nomiColonne;
	}

	private static JScrollPane scrollPane;
	
	public TabellaEntrata() {
        super(new GridLayout(1,0));
        TableF table = null;
        try{
        	GeneratoreDatiTabellaEntrate dati = new GeneratoreDatiTabellaEntrate();
        	table = GeneratoreDatiTabellaEntrate.createTable(dati.getMatrice(), dati.getNomiColonna());
        }catch (Exception e) {
			e.printStackTrace();
		}

        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane(table);

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

	protected void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
}