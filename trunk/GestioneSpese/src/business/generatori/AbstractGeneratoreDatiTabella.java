package business.generatori;

import java.awt.Dimension;

import javax.swing.table.DefaultTableModel;

import view.font.TableF;

public abstract class AbstractGeneratoreDatiTabella {

//	private TableF table;
	private Object[][] matrice;
	
	public AbstractGeneratoreDatiTabella() {
		init();
	}

	private void init() {
	
        matrice = new String[12][getNomiColonna().length];

        for(int i=0; i<12; i++){
        	for(int x=0; x<getNomiColonna().length; x++){
        		try {
					matrice[i][x] = getOggettoMatrice(i,x);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}        	
        }
	}
	
	public static TableF createTable(Object[][] primo, String[] nomiColonne) {
		TableF table = new TableF(primo, nomiColonne);
		table.setModel(new DefaultTableModel(primo, nomiColonne));
        table = new TableF(primo, nomiColonne);
        
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setRowHeight(27);
        return table;
	}
	
	public abstract String[] getNomiColonna();
	
	public abstract Object getOggettoMatrice(int i, int x);

	public Object[][] getMatrice() {
		return matrice;
	}

	
}
