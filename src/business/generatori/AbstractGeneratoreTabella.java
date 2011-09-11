package business.generatori;

import java.awt.Dimension;

import javax.swing.table.DefaultTableModel;

import view.font.TableF;
import business.internazionalizzazione.I18NManager;

/**
 * Fornisce il codice comune per le tabelle e stabilisce i i metodi da
 * implementare per le classi figlie
 * 
 * @author marco.molinari
 * 
 */
public abstract class AbstractGeneratoreTabella {

	private Object[][] matrice;

	public AbstractGeneratoreTabella() {
		init();
	}

	private void init() {
		final String[] nomiColonne = getNomiColonna();

		matrice = new String[12][nomiColonne.length];

		colonnaMesi();

		for (int i = 0; i < 12; i++) {
			for (int x = 1; x < nomiColonne.length; x++) {
				try {

					matrice[i][x] = getOggettoMatrice(i, x);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Aggiunge alla matrice della tabella, alla prima colonna, i mesi
	 */
	private void colonnaMesi() {
		matrice[0][0] = I18NManager.getSingleton().getMessaggio("january");
		matrice[1][0] = I18NManager.getSingleton().getMessaggio("february");
		matrice[2][0] = I18NManager.getSingleton().getMessaggio("march");
		matrice[3][0] = I18NManager.getSingleton().getMessaggio("april");
		matrice[4][0] = I18NManager.getSingleton().getMessaggio("may");
		matrice[5][0] = I18NManager.getSingleton().getMessaggio("june");
		matrice[6][0] = I18NManager.getSingleton().getMessaggio("july");
		matrice[7][0] = I18NManager.getSingleton().getMessaggio("august");
		matrice[8][0] = I18NManager.getSingleton().getMessaggio("september");
		matrice[9][0] = I18NManager.getSingleton().getMessaggio("october");
		matrice[10][0] = I18NManager.getSingleton().getMessaggio("november");
		matrice[11][0] = I18NManager.getSingleton().getMessaggio("december");
	}

	/**
	 * 
	 * Permette di generare una tabella
	 * 
	 * @param primo
	 * @param nomiColonne
	 * @return TableF
	 */
	public static TableF createTable(final Object[][] primo, final String[] nomiColonne) {
		TableF table = new TableF(primo, nomiColonne);
		table.setModel(new DefaultTableModel(primo, nomiColonne));
		table = new TableF(primo, nomiColonne);

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setRowHeight(27);
		return table;
	}

	/**
	 * Da implementare in ogni sottoclasse, deve contenere un array di string
	 * con i nomi delle colonne
	 * 
	 * @return String[]
	 */
	public abstract String[] getNomiColonna();

	/**
	 * 
	 * @param i
	 * @param x
	 * @return
	 */
	public abstract Object getOggettoMatrice(int i, int x);

	public Object[][] getMatrice() {
		return matrice;
	}

}
