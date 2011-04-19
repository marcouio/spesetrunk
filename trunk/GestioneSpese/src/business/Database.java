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

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import view.componenti.componentiPannello.SottoPannelloCategorie2;
import view.componenti.componentiPannello.SottoPannelloDatiEntrate;
import view.componenti.componentiPannello.SottoPannelloDatiSpese;
import view.componenti.componentiPannello.SottoPannelloMesi;
import view.componenti.componentiPannello.SottoPannelloTotali;
import view.entrateuscite.Uscite;
import view.font.TableF;
import view.impostazioni.Categorie;
import view.impostazioni.Impostazioni;
import view.impostazioni.SettingGruppi;
import view.tabelle.TabellaEntrata;
import view.tabelle.TabellaUscita;
import business.cache.CacheCategorie;
import business.cache.CacheEntrate;
import business.cache.CacheUscite;
import business.generatori.GeneratoreDatiTabellaUscite;
import business.generatori.GeneratoreDatiTabellaEntrate;
import domain.CatSpese;
import domain.Entrate;
import domain.Gruppi;
import domain.SingleSpesa;
import domain.Utenti;
import domain.wrapper.Model;

public class Database {

	private static Database singleton;

	static Logger log = AltreUtil.getLog();
	
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
					sql.append(") ").append(" VALUES (");
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
		ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();

		for(int i = 0;i<listaUscite.size(); i++){
			SingleSpesa uscita = listaUscite.get(i);
			CatSpese cat = uscita.getCatSpese();
			if(cat!=null){
				Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
				int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
				if(mesee==mese && cat.getidCategoria()==categoria)
					spesaTotMeseCat +=uscita.getinEuro();
			}
		}	
		return AltreUtil.arrotondaDecimaliDouble(spesaTotMeseCat);
	}
	
	public static double speseMeseGruppo(int mese, int gruppo){
		double spesaTotMeseGruppo=0.0;
		ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();
		for(int i = 0;i<listaUscite.size(); i++){
			SingleSpesa uscita = listaUscite.get(i);
			CatSpese cat = uscita.getCatSpese();
			if(cat!=null){
				Gruppi group = cat.getGruppi();
				Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
				int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
				if(group!=null && group.getidGruppo()!=0){
					if(mesee==mese && group.getidGruppo()==gruppo){
						spesaTotMeseGruppo +=uscita.getinEuro();
					}
				}
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(spesaTotMeseGruppo);
	}
	
	public static double speseMeseSenzaGruppo(int mese, int categoria){
		double spesaTotMeseCat=0.0;
		ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();
		for(int i = 0;i<listaUscite.size(); i++){
			SingleSpesa uscita = listaUscite.get(i);
			CatSpese cat = uscita.getCatSpese();
			if(cat!=null){
				Gruppi group = cat.getGruppi();
				Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
				int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
				if(group==null || group.getidGruppo()==0){
					if(mesee==mese && cat.getidCategoria()==categoria)
						spesaTotMeseCat +=uscita.getinEuro();
				}
			}
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
		ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtenteEAnno();
		for(int i = 0;i<listaEntrate.size(); i++){
			Entrate entrata = listaEntrate.get(i);
			String cat = entrata.getFisseoVar();
			Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
			int mesee = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));

			if(mesee==mese && cat.equals(tipoEntrata))
				entrateMeseTipo += entrata.getinEuro();
		}	
		
		return AltreUtil.arrotondaDecimaliDouble(entrateMeseTipo);
	}

	/**
	 * Calcola interrogando il database il totale delle spese nel mese passato come parametro
	 * @param mese
	 * @return totale mensile delle spese(double)
	 */
	public double totaleUsciteMese(int mese){	
		double totaleMese = 0;
		ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for(int i = 0;i<listaUscite.size(); i++){
			SingleSpesa uscita = listaUscite.get(i);
			Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
			int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
			if(mesee==mese)
				totaleMese += uscita.getinEuro();
		}	
		return AltreUtil.arrotondaDecimaliDouble(totaleMese);
	}
	
	/**
	 * Calcola interrogando il database il totale delle entrate nel mese passato come parametro
	 * @param mese
	 * @return totale mensile delle spese(double)
	 */
	public double totaleEntrateMese(int mese){
		double totaleMese = 0;
		ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtenteEAnno();
		for(int i = 0;i<listaEntrate.size(); i++){
			Entrate entrata = listaEntrate.get(i);
			Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
			int mesee = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));
			//TODO serve l'anno??
			int annoo = Integer.parseInt(DBUtil.dataToString(dataEntrata, "yyyy"));
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
			Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
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
			Date dataEntrate = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
			int annoo = Integer.parseInt(DBUtil.dataToString(dataEntrate, "yyyy"));
			if(annoo==anno && FxOVar.equals(FissoOVar))
				totale += entrata.getinEuro();
		}
		return AltreUtil.arrotondaDecimaliDouble(totale);
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
			 +Entrate.ID+", "+Entrate.NOME_TABELLA+"."+Entrate.DATAINS+" as inserimento"+" FROM "+tabella+" order by "+Entrate.ID+" desc";
		else if(tabella.equals(SingleSpesa.NOME_TABELLA))
			 sql = "SELECT "+SingleSpesa.NOME_TABELLA+"."+SingleSpesa.DATA+" as data, "+SingleSpesa.NOME_TABELLA+"."+SingleSpesa.NOME+", "
			 +SingleSpesa.NOME_TABELLA+"."+SingleSpesa.DESCRIZIONE+", "+SingleSpesa.NOME_TABELLA+"."+SingleSpesa.INEURO
			 +" as euro, "+CatSpese.NOME_TABELLA+"."+CatSpese.NOME+" as categoria, "+SingleSpesa.NOME_TABELLA+"."+SingleSpesa.ID+", "
			 +SingleSpesa.NOME_TABELLA+"."+SingleSpesa.DATAINS+" as inserimento"+" FROM "+tabella+", "+CatSpese.NOME_TABELLA +", "+Utenti.NOME_TABELLA + " where "+SingleSpesa.NOME_TABELLA+"."+SingleSpesa.IDCATEGORIE+" = "
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
			Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
			String annoDaData = DBUtil.dataToString(dataEntrata, "yyyy");
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
			Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
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
			Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
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
			Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
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
			Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
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
			Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
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

		
		ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();
		for(int i = 0;i<listaUscite.size(); i++){
			SingleSpesa uscita = listaUscite.get(i);
			if(uscita!=null){
				if(uscita.getCatSpese()!=null){
					String importanzaSpesa = uscita.getCatSpese().getimportanza();
					if(importanzaSpesa.equals(importanza)){
						speseTipo += uscita.getinEuro();
					}
				}
			}
		}	

		if(speseTipo!=0)
			if(totaleAnnuo!=0.0){
			percentualeTipo = speseTipo/totaleAnnuo * 100;
			}
		else
			percentualeTipo = 0;

		
		return AltreUtil.arrotondaDecimaliDouble(percentualeTipo);
		
	}
	
// ************************************** METODI DI AGGIORNAMENTO ***************************************
	
	
	public static void aggiornamentoPerImpostazioni(){
		try{
			aggiornamentoGenerale(Entrate.NOME_TABELLA);
			aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
			SottoPannelloMesi.azzeraCampi();
			SottoPannelloCategorie2.azzeraCampi();
			SottoPannelloTotali.getPercentoFutili().setText(Double.toString(percentoUscite(CatSpese.IMPORTANZA_FUTILE)));
			SottoPannelloTotali.getPercentoVariabili().setText(Double.toString(percentoUscite(CatSpese.IMPORTANZA_VARIABILE)));
			SottoPannelloTotali.getAvanzo().setText(Double.toString(AltreUtil.arrotondaDecimaliDouble((EAnnuale())-(Annuale()))));
			DBUtil.closeConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Dopo una variazione o inserimento di un movimento permette l'aggiornamento 
	 * di tutti i pannelli rispetto al tipo (Uscita, 'SingleSpesa.NOME_TABELLA', e Entrata,
	 * 'Entrate.NOME_TABELLA')
	 * @param tipo
	 * @throws Exception 
	 */
	public static void aggiornamentoGenerale(String tipo) throws Exception{
		//TODO Implementare un codice che gestisce tutti gli aggiornamenti dell'applicazione
		
		if(tipo.equals(SingleSpesa.NOME_TABELLA)){
			final String[]nomiColonne = (String[]) AltreUtil.generaNomiColonne(SingleSpesa.NOME_TABELLA);
			aggiornaTabellaUscite();
			SottoPannelloDatiSpese.getMeseInCors().setText(Double.toString(MensileInCorso()));
			SottoPannelloDatiSpese.getMesePrecUsc().setText(Double.toString(Mensile()));
			SottoPannelloDatiSpese.getSpeseAnnuali().setText(Double.toString(Annuale()));
			Model.aggiornaMovimentiUsciteDaEsterno(nomiColonne, 10);
			DBUtil.closeConnection();
		}else if(tipo.equals(Entrate.NOME_TABELLA)){
			final String[]nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
			aggiornaTabellaEntrate();
			SottoPannelloDatiEntrate.getEnAnCorso().setText(Double.toString(EAnnuale()));
			SottoPannelloDatiEntrate.getEnMeCorso().setText(Double.toString(EMensileInCorso()));
			SottoPannelloDatiEntrate.getEntrateMesePrec().setText(Double.toString(Emensile()));
			Model.aggiornaMovimentiEntrateDaEsterno(nomiColonne, 10);
			DBUtil.closeConnection();
		}else{
			throw new Exception("Aggiornamento non gestito: "+tipo);
		}
	}
	
	/**
	 * Il metodo aggiorna la combobox dei gruppi nel pannello Categorie passandogli
	 * come parametro una entita 'Gruppi'. L'aggiornamento avviene scorrendo gli elementi
	 * della combo: quando id del gruppo passato come parametro e' lo stesso di quello
	 * nella combo, quest'ultimo viene eliminato. Quindi aggiunge il nuovo gruppo nella
	 * stessa posizione di quello eliminato
	 * 
	 * @param gruppo
	 */
	public static void aggiornaGruppi(Gruppi gruppo){
		int max=0;
		String sql = "SELECT MAX("+Gruppi.ID+") FROM "+Gruppi.NOME_TABELLA;
		Connection cn = DBUtil.getConnection();
		
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			max = rs.getInt(1);
			DBUtil.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JComboBox gruppi = Categorie.getSingleton().getComboGruppi();
		JComboBox gruppi1 = SettingGruppi.getSingleton().getComboGruppi();
		
		gruppi.setSelectedIndex(0);		
		int i=1;
		for(i=1; i<=max; i++){
			
			Gruppi gruppo1 = (Gruppi) gruppi1.getItemAt(i);
			if(gruppo1==null){
				gruppo1 = new Gruppi();
				gruppo1.setidGruppo(-1);
			}
			if(gruppo.getidGruppo()==gruppo1.getidGruppo()){
				gruppi1.removeItemAt(i);
				CatSpese categoriaPresa = CacheCategorie.getSingleton().getCatSpese(Integer.toString(gruppo.getidGruppo()));
				//non è possibile sostituirlo la categoria presa dal database con quella passata nel parametro
				//perché il parametro mantiene i vecchi settaggi e non si aggiorna
				gruppi1.insertItemAt(categoriaPresa, i);		
				DBUtil.closeConnection();
			}
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		
	}
	
	//aggiorna le categorie nel pannello di uscite
	/**
	 * Il metodo aggiorna la combobox delle categorie nel pannello uscite passandogli
	 * come parametro una entita CatSpese. L'aggiornamento avviene scorrendo gli elementi
	 * della combo: quando id della categoria passata come parametro e' lo stesso di quello
	 * nella combo, quest'ultima viene eliminata. Quindi aggiunge la nuova categoria nella
	 * stessa posizione di quella eliminata
	 * 
	 * @param CatSpese
	 */
	public static void aggiornaCategorie(CatSpese categoria){
		int max=0;
		String sql = "SELECT MAX("+CatSpese.ID+") FROM "+CatSpese.NOME_TABELLA;
		Connection cn = DBUtil.getConnection();
		
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			max = rs.getInt(1);
			DBUtil.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JComboBox categorie1 = Categorie.getComboCategorie();
		
		int i=1;
		for(i=1; i<=max; i++){
			
			CatSpese catspese1 = (CatSpese) categorie1.getItemAt(i);
			if(catspese1==null){
				catspese1 = new CatSpese();
				catspese1.setidCategoria(-1);
			}
			if(categoria.getidCategoria()==catspese1.getidCategoria()){
				categorie1.removeItemAt(i);
				CatSpese categoriaPresa = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categoria.getidCategoria()));
				//non è possibile sostituirlo la categoria presa dal database con quella passata nel parametro
				//perché il parametro mantiene i vecchi settaggi e non si aggiorna
				categorie1.insertItemAt(categoriaPresa, i);		
				DBUtil.closeConnection();
			}
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
	}

	// aggiorno tabella uscite/mese in seguito a variazioni di altre tabelle
	/**
	 * il metodo aggiorna la matrice primo[][] che rappresenta i dati della tabella uscite.
	 * Utile nel caso in cui vengano aggiornte altre tabelle e si vogliano aggiornare anche
	 * questi dati. 
	 * @throws Exception 
	 */
	public static void aggiornaTabellaUscite(){

		GeneratoreDatiTabellaUscite dati = new GeneratoreDatiTabellaUscite();
        TableF table = GeneratoreDatiTabellaUscite.createTable(dati.getMatrice(), dati.getNomiColonna());
        JScrollPane pane = TabellaUscita.getScrollPane();
        pane.setViewportView(table);
	
	}

	// aggiorno tabella entrate/mese in seguito a variazioni di altre tabelle
	/**
	 * Metodo che serve per aggiornare la matrice entrate/mese dopo variazioni avvenute
	 * in altri pannelli
	 * @throws Exception 
	 */
	public static void aggiornaTabellaEntrate() {
		
		GeneratoreDatiTabellaEntrate dati = new GeneratoreDatiTabellaEntrate();
        TableF table = GeneratoreDatiTabellaEntrate.createTable(dati.getMatrice(), dati.getNomiColonna());
        JScrollPane pane = TabellaEntrata.getScrollPane();
        pane.setViewportView(table);
	}
	
	public static void aggiornamentoComboBox(Vector<CatSpese>categorie){
		DefaultComboBoxModel model = new DefaultComboBoxModel(categorie);

		SottoPannelloCategorie2.getCategorieCombo().setModel(model);
		SottoPannelloCategorie2.getCategorieCombo().validate();
		SottoPannelloCategorie2.getCategorieCombo().repaint();
		
		Uscite.getSingleton().getComboCategorie().setModel(model);
		Uscite.getSingleton().getComboCategorie().validate();
		Uscite.getSingleton().getComboCategorie().repaint();
	}

	
}
