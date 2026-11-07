package com.molinari.gestionespese.business;

import java.util.Date;

import org.h2.Driver;

import com.molinari.utility.database.UtilDb;

public class DBUtil {

	private static final String H2_URL_PREFIX = "jdbc:h2:";
	private static final String H2_URL_SUFFIX = ";MODE=ORACLE";
	private static String url;
	public static final String USR = "sa";
	public static final String DRIVERCLASSNAME = Driver.class.getName();

	private DBUtil(){

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
		return UtilDb.stringToDate(date, format);
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

	public static String getUrl() {
		if (url != null) {
			return url;
		}
		return H2_URL_PREFIX + Database.getDburl() + H2_URL_SUFFIX;
	}

	public static void setUrl(String url) {
		DBUtil.url = url;
	}

}