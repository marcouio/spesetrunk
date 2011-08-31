package view.report;

import java.util.HashMap;

public class Mesi {

	public static final String GENNAIO = "1";
	public static final String FEBBRAIO = "2";
	public static final String MARZO = "3";
	public static final String APRILE = "4";
	public static final String MAGGIO = "5";
	public static final String GIUGNO = "6";
	public static final String LUGLIO = "7";
	public static final String AGOSTO = "8";
	public static final String SETTEMBRE = "9";
	public static final String OTTOBRE = "10";
	public static final String NOVEMBRE = "11";
	public static final String DICEMBRE = "12";
	
	private static HashMap<String, String> mesiStringa = new HashMap<String, String>();
	
	static{
		mesiStringa.put(GENNAIO, "Gennaio");
		mesiStringa.put(FEBBRAIO, "Febbraio");
		mesiStringa.put(MARZO, "Marzo");
		mesiStringa.put(APRILE, "Aprile");
		mesiStringa.put(MAGGIO, "Maggio");
		mesiStringa.put(GIUGNO, "Giugno");
		mesiStringa.put(LUGLIO, "Luglio");
		mesiStringa.put(AGOSTO, "Agosto");
		mesiStringa.put(SETTEMBRE, "Settembre");
		mesiStringa.put(OTTOBRE, "Ottobre");
		mesiStringa.put(NOVEMBRE, "Novembre");
		mesiStringa.put(DICEMBRE, "Dicembre");
	}
	
	public static String getMeseStringa(String numMese){
		return mesiStringa.get(numMese);
	}
	
	public static String getMeseStringa(int numMese){
		String mese = Integer.toString(numMese);
		return mesiStringa.get(mese);
	}
	
}
