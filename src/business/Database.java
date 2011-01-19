package business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import view.impostazioni.Impostazioni;
import business.cache.CacheEntrate;
import business.cache.CacheUscite;
import domain.CatSpese;
import domain.Entrate;
import domain.SingleSpesa;
import domain.Utenti;

public class Database {

	private static Database singleton;

	Logger log = AltreUtil.getLog();
	
	private Database(){
		
	};
	
	public static final Database getSingleton(){
		if (singleton == null) {
			synchronized (Database.class) {
				if (singleton == null) {
					singleton = new Database();
				}
			} // if
		} // if
		return singleton;
	}
	
		
	/**
	 * 
	 * Esegue un'istruzione SQL specificando come parametri il comando, la tabella, i
	 * campi di riferimento e clausole where. Non permette funzioni complesse.
	 * 
	 * @param comando
	 * @param tabella
	 * @param campi
	 * @param clausole
	 * @return boolean
	 */
	public boolean eseguiIstruzioneSql(String comando, String tabella,
			HashMap<String, String> campi, HashMap<String, String> clausole) {
		boolean ok = false;
		try {
			ok = false;
			// connection = DBUtil.getConnection();
			StringBuffer sql = new StringBuffer();
			String command =comando.toUpperCase(); 
			
			if (tabella != null && comando != null ) {
				// comando
				if (command.equals("INSERT")) {
					sql.append(command).append(" INTO ").append(tabella);
					sql.append("(");
					Iterator<String> iterInsert = campi.keySet().iterator();

					while (iterInsert.hasNext()) {
						String prossimo = iterInsert.next();
						// aggiunge nome colonna
						sql.append(prossimo);
						if (iterInsert.hasNext())
							sql.append(", ");
					}
					sql.append(") ").append(" VALUE (");
					Iterator<String> iterInsert2 = campi.keySet().iterator();
					while (iterInsert2.hasNext()) {
						String prossimo = iterInsert2.next();
						try {
							sql.append(Integer.parseInt(campi.get(prossimo)));
						} catch (NumberFormatException e) {
							sql.append("'" + campi.get(prossimo) + "'");
						}
						if (iterInsert2.hasNext())
							sql.append(", ");
					}
			
					sql.append(")");
					 Connection cn = DBUtil.getConnection();
					 Statement st = cn.createStatement();
					 if(st.executeUpdate(sql.toString())!=0)
							ok=true;
					 cn.close();
					 System.out.println("Record inserito correttamente");
				} else if (command.equals("UPDATE")) {
					Iterator<String> iterUpdate = campi.keySet().iterator();
					sql.append(command).append(" " + tabella).append(" SET ");
					while (iterUpdate.hasNext()) {
						String prossimo = iterUpdate.next();
						sql.append(prossimo).append(" = ");
						try {
							if(campi.get(prossimo).contains(".")){
								sql.append(Double.parseDouble(campi.get(prossimo)));
							}else
								sql.append(Integer.parseInt(campi.get(prossimo)));
						} catch (NumberFormatException e) {
							sql.append("'" + campi.get(prossimo) + "'");
						}
						if (iterUpdate.hasNext()) {
							sql.append(", ");
						}
					}
					if (!clausole.isEmpty()) {
						sql.append(" WHERE 1=1");
						Iterator<String> where = clausole.keySet().iterator();
						
						while (where.hasNext()) {
							sql.append(" AND ");
							String prossimo = where.next();
							sql.append(prossimo).append(" = ");
							try{
								sql.append(Integer.parseInt(clausole.get(prossimo)));
							}catch (NumberFormatException e) {
								sql.append("'"+clausole.get(prossimo)+"'");
							}
							if(where.hasNext())
								sql.append(", ");
						}
					}
					Connection cn = DBUtil.getConnection();
					Statement st = cn.createStatement();
					if(st.executeUpdate(sql.toString())!=0)
						ok=true;
					cn.close();	
				} else if (command.equals("DELETE")) {
					sql.append(command).append(" FROM ").append(tabella);
					if(!clausole.isEmpty()){
						sql.append(" WHERE 1=1");
						Iterator<String> where = clausole.keySet().iterator();
						while (where.hasNext()) {
							sql.append(" AND ");
						
							String prossimo = where.next();
							sql.append(prossimo).append(" = ");
							
							try{
								sql.append(Integer.parseInt(clausole.get(prossimo)));
							}catch (NumberFormatException e) {
								sql.append("'"+clausole.get(prossimo)+"'");
							}
						}
						if(where.hasNext())
							sql.append(", ");
						Connection cn = DBUtil.getConnection();
						Statement st = cn.createStatement();
						if(st.executeUpdate(sql.toString())!=0)
							ok=true;
						cn.close();
					}
					
				} else if (command.equals("SELECT")) {
					sql.append(command);
					if(campi==null)
						sql.append(" * ");
					else{
						Iterator<String> iterSelect = clausole.keySet().iterator();
						while(iterSelect.hasNext()){
							String prossimo = iterSelect.next();
							sql.append(" "+prossimo);
						}
						if(iterSelect.hasNext())
							sql.append(", ");
						
					}
					if(!clausole.isEmpty()){
						sql.append("WHERE 1=1");
						Iterator<String> where = clausole.keySet().iterator();
						while(where.hasNext()){
							sql.append(" AND ");
							String prossimo = where.next();
							sql.append(prossimo).append(" = ");
							try{
								sql.append(Integer.parseInt(clausole.get(prossimo)));
							}catch (NumberFormatException e) {
								sql.append("'"+clausole.get(prossimo)+"'");
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
		return ok;
	}

	/**
	 * Il metodo esegue le stringhe di codice Sql inserite nel campo.
	 * Non esegue ancora operazione e formule complesse: da implementare.
	 * 
	 * @param sql
	 * @return HashMap<String, ArrayList>
	 */
	@SuppressWarnings("rawtypes")
	public HashMap<String, ArrayList> terminaleSql(String sql) {
		HashMap<String, ArrayList> nomi = new HashMap<String, ArrayList>();
		Connection cn = DBUtil.getConnection();
		if (sql.substring(0, 1).equals("s") || sql.substring(0, 1).equals("S")) {
			try {
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				ArrayList<String> lista = new ArrayList<String>();
				for(int i = 0; i < rsmd.getColumnCount(); i++) {
					lista.add(rsmd.getColumnLabel(i+1));
				}
				nomi.put("nomiColonne",lista);
				int z=0;
				while(rs.next()){
					ArrayList<String> lista2 = new ArrayList<String>();
					z++;
					for(int i = 1; i<=rsmd.getColumnCount(); i++){
						if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
							lista2.add(Integer.toString(rs.getInt(i)));
						}else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
							lista2.add(Double.toString(rs.getInt(i)));
						}else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
							lista2.add(rs.getDate(i).toString());
						}else{
							lista2.add(rs.getString(i));
						}
					}
					nomi.put("row"+z, lista2);
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Operazione non eseguita: "+e.getMessage(), "Non va!", JOptionPane.ERROR_MESSAGE, new ImageIcon(
				"immgUtil/index.jpeg"));
				log.severe("Operazione SQL non eseguita:"+e.getMessage());
				e.printStackTrace();
			}

		} else {
			Statement st;
			try {
				st = cn.createStatement();
				st.executeUpdate(sql);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Operazione non eseguita: "+e.getMessage(), "Non va!", JOptionPane.ERROR_MESSAGE, new ImageIcon(
				"immgUtil/index.jpeg"));
				log.severe("Operazione SQL non eseguita:"+e.getMessage());
				e.printStackTrace();
			}
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return nomi;
	}
	
	// questo metodo riempie tabella uscite
	/**
	 * Restuisce un double che rappresenta la somma delle entrate per tipologia e mese di
	 * appartenenza
	 * 
	 * @param mese
	 * @param categoria
	 * @return double
	 * @throws Exception 
	 */
	public static double speseMeseCategoria(int mese, int categoria) throws Exception {
	
			double spesaTotMeseCat=0.0;
			ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();

			for(int i = 0;i<listaUscite.size(); i++){
				SingleSpesa uscita = listaUscite.get(i);
				CatSpese cat = uscita.getCatSpese();
				Date dataUscita = uscita.getData();
				int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
				if(mesee==mese && cat.getidCategoria()==categoria)
					spesaTotMeseCat +=uscita.getinEuro();
			}	
			return AltreUtil.arrotondaDecimaliDouble(spesaTotMeseCat);
	}
	
	
	// questo metodo riempie la tabella delle entrate
	/**
	 * Restuisce un double che rappresenta la somma delle entrate per tipologia e mese di
	 * appartenenza
	 * 
	 * @param mese
	 * @param tipoEntrata
	 * @return double
	 * @throws Exception 
	 */
	public double entrateMeseTipo(int mese, String tipoEntrata) throws Exception {
		double entrateMeseTipo = 0;
		ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for(int i = 0;i<listaEntrate.size(); i++){
			Entrate entrata = listaEntrate.get(i);
			String cat = entrata.getFisseoVar();
			Date dataEntrata = entrata.getdata();
			int mesee = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));

			if(mesee==mese && cat.equals(tipoEntrata))
				entrateMeseTipo += entrata.getinEuro();
		}	
		
		return AltreUtil.arrotondaDecimaliDouble(entrateMeseTipo);
	}
	
	
	/**
	 * Calcola interrogando il database il totale delle entrate nel mese passato come parametro
	 * @param mese
	 * @return totale mensile delle spese(double)
	 */
	public static double totaleEntrateMese(int mese){
		double totaleMese = 0;
		ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		
		
		for(int i = 0;i<listaEntrate.size(); i++){
			Entrate entrata = listaEntrate.get(i);
			Date dataEntrata = entrata.getdata();
			int mesee = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));
			if(mesee==mese)
				totaleMese += entrata.getinEuro();
		}	
		
		return AltreUtil.arrotondaDecimaliDouble(totaleMese);
	}
	
	
	public static double totaleUscitaAnnoCategoria(int categoria){
		double totale=0;
		int anno = Impostazioni.getAnno();
		ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for(int i = 0;i<listaUscite.size(); i++){
			SingleSpesa uscita = listaUscite.get(i);
			int cat = uscita.getCatSpese().getidCategoria();
			Date dataUscita= uscita.getData();
			int annoo = Integer.parseInt(DBUtil.dataToString(dataUscita, "yyyy"));
			if(annoo==anno && cat==categoria)
				totale += uscita.getinEuro();
		}
		return AltreUtil.arrotondaDecimaliDouble(totale);
	}

	public static double totaleEntrateAnnoCategoria(String FissoOVar){
		double totale=0;
		int anno = Impostazioni.getAnno();
		ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for(int i = 0;i<listaEntrate.size(); i++){
			Entrate entrata = listaEntrate.get(i);
			String FxOVar = entrata.getFisseoVar(); 
			Date dataUscita= entrata.getdata();
//			int mese = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
			int annoo = Integer.parseInt(DBUtil.dataToString(dataUscita, "yyyy"));
			if(annoo==anno && FxOVar.equals(FissoOVar))
				totale += entrata.getinEuro();
		}
		return AltreUtil.arrotondaDecimaliDouble(totale);
	}
	
	
	/**
	 * Calcola interrogando il database il totale delle spese nel mese passato come parametro
	 * @param mese
	 * @return totale mensile delle spese(double)
	 */
	public static double totaleUsciteMese(int mese){	
		double totaleMese = 0;
		ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for(int i = 0;i<listaUscite.size(); i++){
			SingleSpesa uscita = listaUscite.get(i);
			Date dataUscita = uscita.getData();
			int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
			if(mesee==mese)
				totaleMese += uscita.getinEuro();
		}	
		return AltreUtil.arrotondaDecimaliDouble(totaleMese);
	}
	
	/**
	 * Restituisce in un vettore di stringhe i nomi delle colonne della tabella specificata
	 * nel parametro
	 *  
	 * @param tabella
	 * @return Vector<String>
	 */
	public Vector<String> nomiColonne(String tabella) {
		Vector<String> colonne = null;
		String sql="";
		if(tabella.equals(Entrate.NOME_TABELLA))
			 sql = "SELECT "+Entrate.NOME_TABELLA+"."+Entrate.DATA+", "+Entrate.NOME_TABELLA+"."+Entrate.NOME+", "+Entrate.NOME_TABELLA+"."+Entrate.DESCRIZIONE+", "
			 +Entrate.NOME_TABELLA+"."+Entrate.INEURO+" as euro, "+Entrate.NOME_TABELLA+"."+Entrate.FISSEOVAR+" as categoria, "+Entrate.NOME_TABELLA+"."
			 +Entrate.ID+" FROM "+tabella+" order by "+Entrate.ID+" desc";
		else if(tabella.equals(SingleSpesa.NOME_TABELLA))
			 sql = "SELECT "+SingleSpesa.NOME_TABELLA+"."+SingleSpesa.DATA+" as data, "+SingleSpesa.NOME_TABELLA+"."+SingleSpesa.NOME+", "
			 +SingleSpesa.NOME_TABELLA+"."+SingleSpesa.DESCRIZIONE+", "+SingleSpesa.NOME_TABELLA+"."+SingleSpesa.INEURO
			 +" as euro, "+CatSpese.NOME_TABELLA+"."+CatSpese.NOME+" as categoria, "+SingleSpesa.NOME_TABELLA+"."+SingleSpesa.ID
			 +" FROM "+tabella+", "+CatSpese.NOME_TABELLA +", "+Utenti.NOME_TABELLA + " where "+SingleSpesa.NOME_TABELLA+"."+SingleSpesa.IDCATEGORIE+" = "
			 +CatSpese.NOME_TABELLA+"."+CatSpese.ID+" and "+SingleSpesa.NOME_TABELLA+"."+SingleSpesa.IDUTENTE+" = "+ Utenti.NOME_TABELLA+"."+Utenti.ID 
			 +" order by "+SingleSpesa.ID+" desc";
		
		Connection cn = DBUtil.getConnection();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			ResultSetMetaData rsm = rs.getMetaData();
			colonne = new Vector<String>();
			for (int i = 1; i <= rsm.getColumnCount(); i++) {
				colonne.add(rsm.getColumnName(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.severe("Errore nel caricamento dal database dei nomi delle colonne di "+ tabella+". "+e.getMessage());
		}
		DBUtil.closeConnection();
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colonne;
	}
	
	
	/**
	 *
	 * 
	 * @return Metodo per calcolare il totale delle entrate annuali
	 */
	public static double EAnnuale() {
		double Eannuale = 0;

		int anno = Impostazioni.getAnno();
		ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for(int i = 0;i<listaEntrate.size(); i++){
			Entrate entrata = listaEntrate.get(i);
			Date dataUscita = entrata.getdata();
			String annoDaData = DBUtil.dataToString(dataUscita, "yyyy");
			if(Integer.parseInt(annoDaData)==anno)
				Eannuale += entrata.getinEuro();
		}	
		return AltreUtil.arrotondaDecimaliDouble(Eannuale);
	}

	/**
	 * 
	 * 
	 * @return Metodo per calcolare il totale delle spese annuali
	 */	
	public static double Annuale() {
		int anno = Impostazioni.getAnno();
		double annuale = 0;
		ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for(int i = 0;i<listaUscite.size(); i++){
			SingleSpesa uscita = listaUscite.get(i);
			Date dataUscita = uscita.getData();
			String annoDaData = DBUtil.dataToString(dataUscita, "yyyy");
			if(Integer.parseInt(annoDaData)==anno)
				annuale += uscita.getinEuro();
		}	
			return AltreUtil.arrotondaDecimaliDouble(annuale);
		
	}

	/**
	 *
	 * 
	 * @return  Metodo per calcolare il totale delle entrate per il mese precedente
	 */
	public static double Emensile() {
		double Emensile10 = 0;
		GregorianCalendar data = new GregorianCalendar();
		ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for(int i = 0;i<listaEntrate.size(); i++){
			Entrate entrata = listaEntrate.get(i);
			Date dataEntrata = entrata.getdata();
			int mese = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));
			int anno = Integer.parseInt(DBUtil.dataToString(dataEntrata, "yyyy"));
			if(mese==data.get(Calendar.MONTH) && anno==data.get(Calendar.YEAR))
				Emensile10 += entrata.getinEuro();
		}	
		return AltreUtil.arrotondaDecimaliDouble(Emensile10);

	}

	/**
	 * 
	 * 
	 * @return Metodo per calcolare il totale delle uscite per il mese precedente
	 */
	public static double Mensile() {
		double mensile1 = 0;
		GregorianCalendar data = new GregorianCalendar();
		ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for(int i = 0;i<listaUscite.size(); i++){
			SingleSpesa uscita = listaUscite.get(i);
			Date dataUscita = uscita.getData();
			int mese = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
			int anno = Integer.parseInt(DBUtil.dataToString(dataUscita, "yyyy"));
			
			if(mese==data.get(Calendar.MONTH)&& anno==data.get(Calendar.YEAR))
				mensile1 += uscita.getinEuro();
		}	
		return AltreUtil.arrotondaDecimaliDouble(mensile1);

	}

	/**
	 * 
	 * 
	 * @return metodo per calcolare il totale delle entrate per il mese precedente
	 */
	public static double EMensileInCorso() {
		double Emensile = 0;
		double Emensile10 = 0;
		GregorianCalendar data = new GregorianCalendar();
		ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for(int i = 0;i<listaEntrate.size(); i++){
			Entrate entrata = listaEntrate.get(i);
			Date dataEntrata = entrata.getdata();
			int mese = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));
			int anno = Integer.parseInt(DBUtil.dataToString(dataEntrata, "yyyy"));
			if(mese==(data.get(Calendar.MONTH)+1) && anno==data.get(Calendar.YEAR))
				Emensile10 += entrata.getinEuro();
		}	
		return AltreUtil.arrotondaDecimaliDouble(Emensile);


	}

	/**
	 * Metodo che calcola il totale delle uscite per il mese in corso
	 * 
	 * @return double
	 * @throws SQLException
	 */
	public static double MensileInCorso(){
		double mensile = 0;
		GregorianCalendar data = new GregorianCalendar();
		ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for(int i = 0;i<listaUscite.size(); i++){
			SingleSpesa uscita = listaUscite.get(i);
			Date dataUscita = uscita.getData();
			int mese = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
			int anno = Integer.parseInt(DBUtil.dataToString(dataUscita, "yyyy"));
			
			if(mese==(data.get(Calendar.MONTH)+1)&& anno==data.get(Calendar.YEAR))
				mensile += uscita.getinEuro();
		}	
		return AltreUtil.arrotondaDecimaliDouble(mensile);
	}
	
	public static double percentoUscite(String importanza){
		double percentualeTipo = 0;
		double totaleAnnuo = Annuale();
		double speseTipo=0;

		
		ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for(int i = 0;i<listaUscite.size(); i++){
			SingleSpesa uscita = listaUscite.get(i);
			String importanzaSpesa = uscita.getCatSpese().getimportanza();
			if(importanzaSpesa.equals(importanza))
				speseTipo += uscita.getinEuro();
		}	

		if(speseTipo!=0)
			percentualeTipo = speseTipo/totaleAnnuo * 100;
		else
			percentualeTipo = 0;

		return AltreUtil.arrotondaDecimaliDouble(percentualeTipo);
		
	}
	
	
	
	
}
