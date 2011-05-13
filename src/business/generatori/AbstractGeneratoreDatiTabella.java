package business.generatori;

import java.awt.Dimension;

import javax.swing.table.DefaultTableModel;

import view.font.TableF;

public abstract class AbstractGeneratoreDatiTabella {

	// private TableF table;
	private Object[][] matrice;

	public AbstractGeneratoreDatiTabella() {
		init();
	}

	private void init() {
		String[] nomiColonne = getNomiColonna();

		matrice = new String[12][nomiColonne.length];
		matrice[0][0] = "Gennaio";
		matrice[1][0] = "Febbraio";
		matrice[2][0] = "Marzo";
		matrice[3][0] = "Aprile";
		matrice[4][0] = "Maggio";
		matrice[5][0] = "Giugno";
		matrice[6][0] = "Luglio";
		matrice[7][0] = "Agosto";
		matrice[8][0] = "Settembre";
		matrice[9][0] = "Ottobre";
		matrice[10][0] = "Novembre";
		matrice[11][0] = "Dicembre";
		for (int i = 0; i < 12; i++) {
			for (int x = 1; x < nomiColonne.length; x++) {
				try {

					matrice[i][x] = getOggettoMatrice(i, x - 1);
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
