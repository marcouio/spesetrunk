package business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import view.impostazioni.Impostazioni;


public class DBUtil {
	public static final String URL="jdbc:sqlite:../GestioneSpese.sqlite"; 	
//	public static final String URL="jdbc:sqlite:/home/marcouio/workspace/GestioneSpese.sqlite";
//	public static final String URL="jdbc:sqlite:/home/marcouio/workspace/GestioneSpese.sqlite";
//	public static final String URL="jdbc:sqlite:../"+(new Impostazioni().getCaricaDatabase()) != null?Impostazioni.getCaricaDatabase().getText():"entrate_uscite.db";
	public static final String USR="root";
	public static final String PWD="marco";
	public static final String DRIVERCLASSNAME="org.sqlite.JDBC";
	private static Connection connection=null;
	private static String mese;
	private static String mesi;

	static{
		try {
			Class.forName(DBUtil.DRIVERCLASSNAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Dato un campo, ne valuta la lunghezza. Se e' piu' corto della dimensione 
	 * inserita come parametro aggiunge campi vuoti, altrimenti tronca aggiungendo
	 * uno spazio finale.
	 * 
	 * @param campo
	 * @param dimensione
	 * @return String
	 */
	public static String creaStringStessaDimensione(String campo, int dimensione){
		if(campo.length()<dimensione){
			for(int i=campo.length(); i<dimensione+1; i++){
				campo = campo+ " ";
			}
		}
		else{
			campo = campo.substring(0, dimensione);
			campo = campo+" ";
		}
		return campo;
	}
	
	/**
	 * Metodo utile quando puo' capitare di ottenere in input un integer come mese con
	 * una sola cifra. In tal caso lo trasforma in stringa aggiungendogli uno zero 
	 * davanti. In questa maniera e' possibile utilizzarlo per comporre una data
	 * 
	 * @param corrente
	 * @return String
	 * @throws Exception 
	 */
	public static String convertiMese2(int corrente) throws Exception{
		if(corrente<10)
			mesi = "0"+ corrente;
		else if(corrente>12){
				throw new Exception("Mese non esistente");
		
		}else 
			mesi = Integer.toString(corrente);
		return mesi;
	}
	
	public static String convertiGiorno(int corrente){
		if(corrente<10)
			mesi = "0"+ corrente;
		else
			mesi = Integer.toString(corrente);
		return mesi;
	}
	
//	questo metodo serve per aggiungere uno zero a Calendar.MONTH se necessario
	/**
	 * Aggiunge uno zero davanti a Calendar.MONTH se necessario.
	 * Se il parametro � 1 restituisce il mese corrente, se � 0
	 * restituisce il mese precedente
	 * 
	 * @param corrente
	 * @return String
	 */
	public static String convertiMese(int corrente){
		
		if(corrente == 1){
			int MESE = new GregorianCalendar().get(Calendar.MONTH)+1;
			if(new GregorianCalendar().get(Calendar.MONTH)+1 < 10){
				mese = "0"+ MESE;
			}
			else{
				mese = Integer.toString(MESE);
			}
		}
		else if(corrente == 0){
			int MESE = new GregorianCalendar().get(Calendar.MONTH);
			if(new GregorianCalendar().get(Calendar.MONTH) < 10){
				mese = "0"+ MESE;
			}
			else{
				mese = Integer.toString(MESE);
			}
		}
		return mese;
		
	}
	
/**
 * Data uno stringa contenente una formato Date restituisce 
 * ancora un Date ma nel formato: dd-MMM-YYYY 
 * 
 * @param data
 * @return Date
 * @throws ParseException
 */
public static Date formatDate2(String data) throws ParseException{
		
		DateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
		
		Date dataNuova=format.parse(data);
		
		return dataNuova;
	}

public static Date formatDate3(String data) throws ParseException{
	
	DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
	
	Date dataNuova=format.parse(data);
	
	return dataNuova;
}

//CONVERSIONE FORMATO STRINGA --> DATA

/**
 * Il metodo da la possibilit� di convertire una stringa in una data, passandogli il formato
 * in cui verr� � presentata nella stringa
 * 
 * @param date
 * @param format
 * @return Date
 */
public static Date stringToDate(String date, String format) {
	SimpleDateFormat formatter = new SimpleDateFormat(format);
	Date dataConvertita = null;
	try {
		dataConvertita = formatter.parse(date);
	}
	catch (ParseException e) {
		System.out.println("ERRORE stringToDate");
	}
	return dataConvertita;
}
//CONVERSIONE FORMATO DATA --> STRINGA
	
/**
 * Trasforma una data in una stringa seguendo il formato specificato
 * 
 * @param date
 * @param format
 * @return String
 */
public static String dataToString(Date date, String format) {
	SimpleDateFormat formatter = new SimpleDateFormat(format);
return formatter.format(date);
}



	/**
	 * Data uno stringa contenente una formato Date restituisce 
	 * ancora un Date ma nel formato: dd-MM-YYYY
	 *  
	 * @param data
	 * @return Date
	 * @throws ParseException
	 */
	public static Date formatDate(String data) throws ParseException{
		
		DateFormat format=new SimpleDateFormat("dd-MM-yyyy");
		
		Date dataNuova=format.parse(data);
		
		return dataNuova;
	}
	
	/**
	 * Data uno stringa contenente una formato Date restituisce 
	 * ancora un Date ma nel formato: dd-MM-YYYY
	 * 
	 * @param data
	 * @return Date
	 * @throws ParseException
	 */
	public static Date formatDateTime(String data) throws ParseException{
		
		DateFormat format=new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		Date dataNuova=format.parse(data);
		
		return dataNuova;
	}
	
	
	public static Connection getConnection2(){
		Connection connection2 = null;
		try {
//			if(Impostazioni.getCaricaDatabase()==null){
				connection2 = DriverManager.getConnection(DBUtil.URL);
//			}else{
//				connection = DriverManager.getConnection("jdbc:sqlite:"+Impostazioni.getCaricaDatabase().getText());
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return connection2;
	}
	
	
	/**
	 * Metodo per ottenere una connessione al database
	 * 
	 * @return Connection
	 */
	public static Connection getConnection(){
		try {
//			if(Impostazioni.getCaricaDatabase()==null){
				connection = DriverManager.getConnection(DBUtil.URL);
//			}else{
//				connection = DriverManager.getConnection("jdbc:sqlite:"+Impostazioni.getCaricaDatabase().getText());
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return connection;
	}
	
	public static Connection getConnection(String fileDatabase){
		try {
			if(Impostazioni.getCaricaDatabase()==null){
				connection = DriverManager.getConnection(DBUtil.URL);
			}else{
				connection = DriverManager.getConnection("jdbc:sqlite:"+fileDatabase);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return connection;
	}

	
	/**
	 * Metodo per chiudere una connessione al database
	 */
	public static void closeConnection(){
		
		if(connection!=null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	}
	
	/**
     * Il metodo fornisce l'ultimo giorno del mese.
     */
    public static final int getLastDayMonth(final int mese, final int anno) {
        switch (mese) {
            case (1):
                return 31;
            case (2):
                return new GregorianCalendar().isLeapYear(anno) ? 29 : 28;
            case (3):
                return 31;
            case (4):
                return 30;
            case (5):
                return 31;
            case (6):
                return 30;
            case (7):
                return 31;
            case (8):
                return 31;
            case (9):
                return 30;
            case (10):
                return 31;
            case (11):
                return 30;
            case (12):
                return 31;
            default:
                throw new ArrayIndexOutOfBoundsException(mese);
        }
    } 

	
}