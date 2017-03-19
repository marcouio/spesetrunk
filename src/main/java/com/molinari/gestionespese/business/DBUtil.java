package com.molinari.gestionespese.business;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.sqlite.JDBC;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.UtilDb;

public class DBUtil {

	private static String url = "jdbc:sqlite:" + Database.getDburl();
	public static final String USR = "root";
	public static final String DRIVERCLASSNAME = JDBC.class.getName();

	private DBUtil(){

	}

	/**
	 * Dato un campo, ne valuta la lunghezza. Se e' piu' corto della dimensione
	 * inserita come parametro aggiunge campi vuoti, altrimenti tronca
	 * aggiungendo uno spazio finale.
	 *
	 * @param campo
	 * @param dimensione
	 * @return String
	 */
	public static String creaStringStessaDimensione(String campo, final int dimensione) {
		String field = campo;
		if (field.length() < dimensione) {
			for (int i = campo.length(); i < dimensione + 1; i++) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(field);
				stringBuilder.append(" ");
				field = stringBuilder.toString();
			}
		} else {
			field = field.substring(0, dimensione);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(field);
			stringBuilder.append(" ");
			field = stringBuilder.toString();
		}
		return field;
	}

	/**
	 * Metodo utile quando puo' capitare di ottenere in input un integer come
	 * mese con una sola cifra. In tal caso lo trasforma in stringa
	 * aggiungendogli uno zero davanti. In questa maniera e' possibile
	 * utilizzarlo per comporre una data
	 *
	 * @param corrente
	 * @return String
	 * @throws Exception
	 */
	public static String convertiMese2(final int corrente) {
		return UtilDb.convertiMeseWithTwoDigit(corrente);
	}

	public static String convertiGiorno(final int corrente) {
		return UtilDb.convertiGiorno(corrente);
	}

	// questo metodo serve per aggiungere uno zero a Calendar.MONTH se
	// necessario
	/**
	 * Aggiunge uno zero davanti a Calendar.MONTH se necessario. Se il parametro
	 * vale 1 restituisce il mese corrente, se vale 0 restituisce il mese precedente
	 *
	 * @param corrente
	 * @return String
	 */
	public static String convertiMese(final int corrente) {
		return UtilDb.convertiMese(corrente);
	}

	/**
	 * Data uno stringa contenente una formato Date restituisce ancora un Date
	 * ma nel formato: dd-MMM-YYYY
	 *
	 * @param data
	 * @return Date
	 * @throws ParseException
	 */
	public static Date formatDate2(final String data) throws ParseException {
		final DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		return format.parse(data);
	}

	public static Date formatDate3(final String data) throws ParseException {
		final DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.parse(data);
	}

	// CONVERSIONE FORMATO STRINGA --> DATA

	/**
	 * Il metodo da la possibilit� di convertire una stringa in una data,
	 * passandogli il formato in cui verr� � presentata nella stringa
	 *
	 * @param date
	 * @param format
	 * @return Date
	 */
	public static Date stringToDate(final String date, final String format) {
		final SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date dataConvertita = null;
		try {
			dataConvertita = formatter.parse(date);
		} catch (final ParseException e) {
			ControlloreBase.getLog().log(Level.SEVERE, "ERRORE del metodo stringToDate su DBUtil", e);
		}
		return dataConvertita;
	}

	// CONVERSIONE FORMATO DATA --> STRINGA

	/**
	 * Trasforma una data in una stringa seguendo il formato specificato
	 *
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String dataToString(final Date date, final String format) {
		return UtilDb.dataToString(date, format);
	}

	/**
	 * Data uno stringa contenente una formato Date restituisce ancora un Date
	 * ma nel formato: dd-MM-YYYY
	 *
	 * @param data
	 * @return Date
	 * @throws ParseException
	 */
	public static Date formatDate(final String date) throws ParseException {
		return UtilDb.stringToDate(date, "dd-MM-yyyy");
	}

	/**
	 * Data uno stringa contenente una formato Date restituisce ancora un Date
	 * ma nel formato: dd-MM-YYYY
	 *
	 * @param data
	 * @return Date
	 * @throws ParseException
	 */
	public static Date formatDateTime(final String date) throws ParseException {
		return UtilDb.stringToDate(date, "dd-MM-yyyy HH:mm");
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		DBUtil.url = url;
	}

}