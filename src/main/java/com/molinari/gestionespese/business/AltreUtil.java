package com.molinari.gestionespese.business;

import java.time.format.DateTimeParseException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.io.UtilIo;
import com.molinari.utility.math.UtilMath;
import com.molinari.utility.text.UtilText;
import com.molinari.utility.xml.CoreXMLManager;

public class AltreUtil {

	public static final int GENNAIO = 1;
	public static final int FEBBRAIO = 2;
	public static final int MARZO = 3;
	public static final int APRILE = 4;
	public static final int MAGGIO = 5;
	public static final int GIUGNO = 6;
	public static final int LUGLIO = 7;
	public static final int AGOSTO = 8;
	public static final int SETTEMBRE = 9;
	public static final int OTTOBRE = 10;
	public static final int NOVEMBRE = 11;
	public static final int DICEMBRE = 12;

	public static final String IMGUTILPATH = "imgUtil/";

	private AltreUtil() {
		//do nothing
	}

	public static boolean checkInteger(final String integer) {
		return UtilMath.isInteger(integer);
	}

	public static boolean checkDouble(final String number) {
		return UtilMath.isDouble(number);
	}

	public static boolean checkDate(final String data) {
		try {
			return data != null && UtilText.checkDate(data, CoreXMLManager.getSingleton().getDateFormat());
		} catch (final DateTimeParseException e2) {
			return false;
		}
		
	}

	/**
	 * Prende l'intero del double e lo sottrae al double stesso per ottenere i
	 * decimali. Quindi trasformo i decimali in long moltiplicando per
	 * 100(verificando che la moltiplicazione non dia 0). In questo modo posso
	 * utilizzare il metodo Math.round che permette l'arrotondamento. Divido
	 * nuovamente per 100 e riottengo i decimali arrotondati a due cifre. A
	 * questo punto posso aggiungerli nuovamente agli interi
	 *
	 * @param d
	 * @return un double arrotondato a due cifre
	 */
	public static double arrotondaDecimaliDouble(final double d) {
		return UtilMath.arrotondaDecimaliDouble(d);
	}

	/**
	 * Cancella tutti i file all'interno della directory passato come parametro
	 *
	 * @param directory
	 * @return
	 */
	public static boolean deleteFileDaDirectory2(final String directory) {
		UtilIo.deleteFileDaDirectory(directory);
		return true;
	}

	public static boolean deleteFileDaDirectory(final String directory, final String treCharIniziali) {
		UtilIo.deleteFileDaDirectory(directory, treCharIniziali);
		return true;
	}

	/**
	 * Genera per una tabella specifica un array con tutti i nomi delle colonne.
	 * Sfrutta il metodo nomiColonne(tabella) che crea un vettore con i nomi
	 * delle colonne
	 *
	 * @param tabella
	 * @return Object[]
	 */
	public static Object[] generaNomiColonne(final String tabella) {
		// nomi delle colonne
		final List<String> nomi = Database.getSingleton().nomiColonne(tabella);
		final String[] nomiColonne = new String[nomi.size()];
		for (int i = 0; i < nomi.size(); i++) {
			nomiColonne[i] = nomi.get(i);
		}
		return nomiColonne;
	}
}
