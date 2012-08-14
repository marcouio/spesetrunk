package business;

import java.io.File;
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

import view.Alert;
import view.impostazioni.Impostazioni;
import business.cache.CacheEntrate;
import business.cache.CacheUscite;
import domain.CatSpese;
import domain.Entrate;
import domain.Gruppi;
import domain.Lookandfeel;
import domain.SingleSpesa;
import domain.Utenti;
import domain.wrapper.WrapLookAndFeel;

public class Database {

	private static Database singleton;
	public static final String DB_URL_WORKSPACE = "../GestioneSpese.sqlite";
	public static final String DB_URL_JAR = "./GestioneSpese.sqlite";
	public static String DB_URL = DB_URL_WORKSPACE;

	private Database() {

	}

	public static void main(final String[] args) {
		try {
			Database.getSingleton().generaDB();
			System.out.println("Db generato");
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final Database getSingleton() {
		if (singleton == null) {
			synchronized (Database.class) {
				if (singleton == null) {
					singleton = new Database();
				}
			} // if
		} // if
		return singleton;
	}

	public void generaDatiTabellaLook() {
		final WrapLookAndFeel wrap = new WrapLookAndFeel();

		final Lookandfeel plastic3d = new Lookandfeel();
		plastic3d.setnome("Plastic3D");
		plastic3d.setvalore("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
		plastic3d.setusato(0);
		wrap.insert(plastic3d);

		final Lookandfeel nimbus = new Lookandfeel();
		nimbus.setnome("Nimbus");
		nimbus.setvalore("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		nimbus.setusato(1);
		wrap.insert(nimbus);

		final Lookandfeel tiny = new Lookandfeel();
		tiny.setnome("Tiny");
		tiny.setvalore("de.muntjak.tinylookandfeel.TinyLookAndFeel");
		tiny.setusato(0);
		wrap.insert(tiny);

		final Lookandfeel motif = new Lookandfeel();
		motif.setnome("Motif");
		motif.setvalore("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		motif.setusato(0);
		wrap.insert(motif);

		final Lookandfeel metal = new Lookandfeel();
		metal.setnome("Metal");
		metal.setvalore("javax.swing.plaf.metal.MetalLookAndFeel");
		metal.setusato(0);
		wrap.insert(metal);

		final Lookandfeel pago = new Lookandfeel();
		pago.setnome("Pago");
		pago.setvalore("com.pagosoft.plaf.PgsLookAndFeel");
		pago.setusato(0);
		wrap.insert(pago);

	}

	public void generaDB() throws SQLException {
		@SuppressWarnings("unused")
		final
		File db = new File(Database.DB_URL);
		String sql = new String();
		final Connection cn = DBUtil.getConnection();
		sql = "CREATE TABLE \"Utenti\" (\"idUtente\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , \"nome\" TEXT NOT NULL , \"cognome\" TEXT NOT NULL , \"username\" TEXT NOT NULL  UNIQUE , \"password\" TEXT NOT NULL );";
		final Statement st = cn.createStatement();
		st.execute(sql.toString());
		sql = "CREATE TABLE \"gruppi\" (\"idGruppo\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"nome\" TEXT NOT NULL , \"descrizione\" TEXT);";
		st.execute(sql.toString());
		sql = "CREATE TABLE \"lookAndFeel\" (\"idLook\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , \"nome\" TEXT NOT NULL , \"valore\" TEXT NOT NULL , \"usato\" INTEGER NOT NULL );";
		st.execute(sql.toString());
		sql = "CREATE TABLE \"risparmio\" (\"idRisparmio\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"PerSulTotale\" DOUBLE NOT NULL , \"nomeOggetto\" TEXT, \"costoOggetto\" DOUBLE);";
		st.execute(sql.toString());
		sql = "CREATE TABLE \"cat_spese\" (\"idCategoria\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"descrizione\"  TEXT NOT NULL,\"importanza\"  TEXT NOT NULL,\"nome\"  TEXT NOT NULL,\"idGruppo\" INTEGER NOT NULL,CONSTRAINT \"keygruppo\" FOREIGN KEY (\"idGruppo\") REFERENCES \"gruppi\" (\"idGruppo\"));";
		st.execute(sql.toString());
		sql = "CREATE TABLE \"budget\" (\"idBudget\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"idCategorie\"  INTEGER NOT NULL UNIQUE,\"percSulTot\"  DOUBLE NOT NULL,CONSTRAINT \"keyspesa\" FOREIGN KEY (\"idCategorie\") REFERENCES \"cat_spese\" (\"idCategoria\"));";
		st.execute(sql.toString());
		sql = "CREATE TABLE \"entrate\" (\"idEntrate\" INTEGER PRIMARY KEY  NOT NULL ,\"descrizione\" TEXT NOT NULL ,\"Fisse_o_Var\" TEXT NOT NULL ,\"inEuro\" INTEGER NOT NULL ,\"data\" TEXT NOT NULL ,\"nome\" TEXT NOT NULL ,\"idUtente\" INTEGER NOT NULL ,\"dataIns\" TEXT);";
		st.execute(sql.toString());
		sql = "CREATE TABLE \"single_spesa\" (\"idSpesa\" INTEGER PRIMARY KEY  NOT NULL ,\"Data\" TEXT NOT NULL ,\"inEuro\" INTEGER NOT NULL ,\"descrizione\" TEXT NOT NULL ,\"idCategorie\" INTEGER NOT NULL ,\"nome\" TEXT NOT NULL ,\"idUtente\" INTEGER NOT NULL ,\"dataIns\" TEXT);";
		st.execute(sql.toString());
		sql = "CREATE TABLE \"note\" (\"idNote\" INTEGER PRIMARY KEY  NOT NULL ,\"nome\" TEXT NOT NULL ,\"descrizione\" TEXT NOT NULL ,\"idUtente\" INTEGER NOT NULL ,\"data\" TEXT NOT NULL ,\"dataIns\" TEXT NOT NULL );";
		st.execute(sql.toString());
		cn.close();

		generaDatiTabellaLook();
	}

	/**
	 * 
	 * Esegue un'istruzione SQL specificando come parametri il comando, la
	 * tabella, i campi di riferimento e clausole where. Non permette funzioni
	 * complesse.
	 * 
	 * @param comando
	 * @param tabella
	 * @param campi
	 * @param clausole
	 * @return boolean
	 */
	public boolean eseguiIstruzioneSql(final String comando, final String tabella, final HashMap<String, String> campi,
			final HashMap<String, String> clausole) {
		boolean ok = false;
		try {
			ok = false;
			final StringBuffer sql = new StringBuffer();
			final String command = comando.toUpperCase();

			if (tabella != null && comando != null) {
				// comando
				if (command.equals("INSERT")) {
					ok = gestioneIstruzioneInsert(tabella, campi, ok, sql, command);
				} else if (command.equals("UPDATE")) {
					ok = gestioneIstruzioneUpdate(tabella, campi, clausole, ok, sql, command);
				} else if (command.equals("DELETE")) {
					ok = gestioneIstruzioneDelete(tabella, clausole, ok, sql, command);
				} else if (command.equals("SELECT")) {
					gestioneIstruzioneSelect(campi, clausole, sql, command);
				}
			}

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
		return ok;
	}

	private void gestioneIstruzioneSelect(final HashMap<String, String> campi, final HashMap<String, String> clausole,
			final StringBuffer sql, final String command) {
		sql.append(command);
		if (campi == null) {
			sql.append(" * ");
		} else {
			final Iterator<String> iterSelect = clausole.keySet().iterator();
			while (iterSelect.hasNext()) {
				final String prossimo = iterSelect.next();
				sql.append(" " + prossimo);
			}
			if (iterSelect.hasNext()) {
				sql.append(", ");
			}

		}
		if (!clausole.isEmpty()) {
			sql.append("WHERE 1=1");
			final Iterator<String> where = clausole.keySet().iterator();
			while (where.hasNext()) {
				sql.append(" AND ");
				final String prossimo = where.next();
				sql.append(prossimo).append(" = ");
				try {
					sql.append(Integer.parseInt(clausole.get(prossimo)));
				} catch (final NumberFormatException e) {
					sql.append("'" + clausole.get(prossimo) + "'");
				}
			}
		}
	}

	private boolean gestioneIstruzioneDelete(final String tabella, final HashMap<String, String> clausole, boolean ok,
			final StringBuffer sql, final String command) throws SQLException {
		sql.append(command).append(" FROM ").append(tabella);
		if (!clausole.isEmpty()) {
			sql.append(" WHERE 1=1");
			final Iterator<String> where = clausole.keySet().iterator();
			while (where.hasNext()) {
				sql.append(" AND ");

				final String prossimo = where.next();
				sql.append(prossimo).append(" = ");

				try {
					sql.append(Integer.parseInt(clausole.get(prossimo)));
				} catch (final NumberFormatException e) {
					sql.append("'" + clausole.get(prossimo) + "'");
				}
			}
			if (where.hasNext()) {
				sql.append(", ");
			}
			final Connection cn = DBUtil.getConnection();
			final Statement st = cn.createStatement();
			if (st.executeUpdate(sql.toString()) != 0) {
				ok = true;
			}
			cn.close();
		}
		return ok;
	}

	private boolean gestioneIstruzioneUpdate(final String tabella, final HashMap<String, String> campi,
			final HashMap<String, String> clausole, boolean ok, final StringBuffer sql, final String command)
	throws SQLException {
		final Iterator<String> iterUpdate = campi.keySet().iterator();
		sql.append(command).append(" " + tabella).append(" SET ");
		while (iterUpdate.hasNext()) {
			final String prossimo = iterUpdate.next();
			sql.append(prossimo).append(" = ");
			try {
				if (campi.get(prossimo).contains(".")) {
					sql.append(Double.parseDouble(campi.get(prossimo)));
				} else {
					sql.append(Integer.parseInt(campi.get(prossimo)));
				}
			} catch (final NumberFormatException e) {
				sql.append("'" + campi.get(prossimo) + "'");
			}
			if (iterUpdate.hasNext()) {
				sql.append(", ");
			}
		}
		if (!clausole.isEmpty()) {
			sql.append(" WHERE 1=1");
			final Iterator<String> where = clausole.keySet().iterator();

			while (where.hasNext()) {
				sql.append(" AND ");
				final String prossimo = where.next();
				sql.append(prossimo).append(" = ");
				try {
					sql.append(Integer.parseInt(clausole.get(prossimo)));
				} catch (final NumberFormatException e) {
					sql.append("'" + clausole.get(prossimo) + "'");
				}
				if (where.hasNext()) {
					sql.append(", ");
				}
			}
		}
		DBUtil.closeConnection();
		final Connection cn = DBUtil.getConnection();
		final Statement st = cn.createStatement();
		if (st.executeUpdate(sql.toString()) != 0) {
			ok = true;
		}
		cn.close();
		return ok;
	}

	private boolean gestioneIstruzioneInsert(final String tabella, final HashMap<String, String> campi, boolean ok,
			final StringBuffer sql, final String command) throws SQLException {
		sql.append(command).append(" INTO ").append(tabella);
		sql.append("(");
		final Iterator<String> iterInsert = campi.keySet().iterator();

		while (iterInsert.hasNext()) {
			final String prossimo = iterInsert.next();
			// aggiunge nome colonna
			sql.append(prossimo);
			if (iterInsert.hasNext()) {
				sql.append(", ");
			}
		}
		sql.append(") ").append(" VALUES (");
		final Iterator<String> iterInsert2 = campi.keySet().iterator();
		while (iterInsert2.hasNext()) {
			final String prossimo = iterInsert2.next();
			try {
				sql.append(Integer.parseInt(campi.get(prossimo)));
			} catch (final NumberFormatException e) {
				sql.append("'" + campi.get(prossimo) + "'");
			}
			if (iterInsert2.hasNext()) {
				sql.append(", ");
			}
		}

		sql.append(")");
		final Connection cn = DBUtil.getConnection();
		final Statement st = cn.createStatement();
		if (st.executeUpdate(sql.toString()) != 0) {
			ok = true;
		}
		cn.close();
		System.out.println("Record inserito correttamente");
		return ok;
	}

	/**
	 * Il metodo esegue le stringhe di codice Sql inserite nel campo.
	 * 
	 * @param sql
	 * @return HashMap<String, ArrayList>
	 */
	@SuppressWarnings("rawtypes")
	public HashMap<String, ArrayList> terminaleSql(final String sql) {
		final HashMap<String, ArrayList> nomi = new HashMap<String, ArrayList>();
		final Connection cn = DBUtil.getConnection();
		if (sql.substring(0, 1).equalsIgnoreCase("S")) {
			try {
				final Statement st = cn.createStatement();
				final ResultSet rs = st.executeQuery(sql);
				final ResultSetMetaData rsmd = rs.getMetaData();
				final ArrayList<String> lista = new ArrayList<String>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					lista.add(rsmd.getColumnLabel(i + 1));
				}
				nomi.put("nomiColonne", lista);
				int z = 0;
				while (rs.next()) {
					final ArrayList<String> lista2 = new ArrayList<String>();
					z++;
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
							lista2.add(Integer.toString(rs.getInt(i)));
						} else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
							lista2.add(Double.toString(rs.getInt(i)));
						} else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
							lista2.add(rs.getDate(i).toString());
						} else {
							lista2.add(rs.getString(i));
						}
					}
					nomi.put("row" + z, lista2);
				}

			} catch (final SQLException e) {
				Alert.operazioniSegnalazioneErroreGrave("Operazione SQL non eseguita:" + e.getMessage());
			}

		} else {
			Statement st;
			try {
				st = cn.createStatement();
				st.executeUpdate(sql);
			} catch (final SQLException e) {
				Alert.operazioniSegnalazioneErroreGrave("Operazione SQL non eseguita:" + e.getMessage());
			}
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return nomi;
	}

	// questo metodo riempie tabella uscite
	/**
	 * Restuisce un double che rappresenta la somma delle uscite per tipologia
	 * e mese di appartenenza
	 * 
	 * @param mese
	 * @param categoria
	 * @return double
	 * @throws Exception
	 */
	public static double speseMeseCategoria(final int mese, final int categoria) throws Exception {

		double spesaTotMeseCat = 0.0;
		final ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();

		for (int i = 0; i < listaUscite.size(); i++) {
			final SingleSpesa uscita = listaUscite.get(i);
			final CatSpese cat = uscita.getCatSpese();
			if (cat != null) {
				final Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
				final int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
				if (mesee == mese && cat.getidCategoria() == categoria) {
					spesaTotMeseCat += uscita.getinEuro();
				}
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(spesaTotMeseCat);
	}

	public static double speseMeseGruppo(final int mese, final int gruppo) {
		double spesaTotMeseGruppo = 0.0;
		final ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();
		for (int i = 0; i < listaUscite.size(); i++) {
			final SingleSpesa uscita = listaUscite.get(i);
			final CatSpese cat = uscita.getCatSpese();
			if (cat != null) {
				final Gruppi group = cat.getGruppi();
				final Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
				final int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
				if (group != null && group.getidGruppo() != 0) {
					if (mesee == mese && group.getidGruppo() == gruppo) {
						spesaTotMeseGruppo += uscita.getinEuro();
					}
				}
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(spesaTotMeseGruppo);
	}

	public static double speseMeseSenzaGruppo(final int mese, final int categoria) {
		double spesaTotMeseCat = 0.0;
		final ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();
		for (int i = 0; i < listaUscite.size(); i++) {
			final SingleSpesa uscita = listaUscite.get(i);
			final CatSpese cat = uscita.getCatSpese();
			if (cat != null) {
				final Gruppi group = cat.getGruppi();
				final Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
				final int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
				if (group == null || group.getidGruppo() == 0) {
					if (mesee == mese && cat.getidCategoria() == categoria) {
						spesaTotMeseCat += uscita.getinEuro();
					}
				}
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(spesaTotMeseCat);
	}

	// questo metodo riempie la tabella delle entrate
	/**
	 * Restuisce un double che rappresenta la somma delle entrate per tipologia
	 * e mese di appartenenza
	 * 
	 * @param mese
	 * @param tipoEntrata
	 * @return double
	 * @throws Exception
	 */
	public double entrateMeseTipo(final int mese, final String tipoEntrata) throws Exception {
		double entrateMeseTipo = 0;
		final ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtenteEAnno();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final Entrate entrata = listaEntrate.get(i);
			final String cat = entrata.getFisseoVar();
			final Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
			final int mesee = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));

			if (mesee == mese && cat.equals(tipoEntrata)) {
				entrateMeseTipo += entrata.getinEuro();
			}
		}

		return AltreUtil.arrotondaDecimaliDouble(entrateMeseTipo);
	}

	/**
	 * Calcola interrogando la cache il totale delle spese nel mese passato come
	 * parametro
	 * 
	 * @param mese
	 * @return totale mensile delle spese(double)
	 */
	public double totaleUsciteMese(final int mese) {
		double totaleMese = 0;
		final ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for (int i = 0; i < listaUscite.size(); i++) {
			final SingleSpesa uscita = listaUscite.get(i);
			final Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
			final int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
			if (mesee == mese) {
				totaleMese += uscita.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(totaleMese);
	}

	/**
	 * Calcola interrogando la cache il totale delle entrate nel mese passato
	 * come parametro
	 * 
	 * @param mese
	 * @return totale mensile delle spese(double)
	 */
	public double totaleEntrateMese(final int mese) {
		double totaleMese = 0;
		final ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtenteEAnno();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final Entrate entrata = listaEntrate.get(i);
			final Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
			final int mesee = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));
			if (mesee == mese) {
				totaleMese += entrata.getinEuro();
			}
		}

		return AltreUtil.arrotondaDecimaliDouble(totaleMese);
	}

	public static double totaleUscitaAnnoCategoria(final int categoria) {
		double totale = 0;
		final int anno = Impostazioni.getAnno();
		final ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for (int i = 0; i < listaUscite.size(); i++) {
			final SingleSpesa uscita = listaUscite.get(i);
			final int cat = uscita.getCatSpese().getidCategoria();
			final Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
			final int annoo = Integer.parseInt(DBUtil.dataToString(dataUscita, "yyyy"));
			if (annoo == anno && cat == categoria) {
				totale += uscita.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(totale);
	}

	public static double totaleEntrateAnnoCategoria(final String FissoOVar) {
		double totale = 0;
		final int anno = Impostazioni.getAnno();
		final ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final Entrate entrata = listaEntrate.get(i);
			final String FxOVar = entrata.getFisseoVar();
			final Date dataEntrate = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
			final int annoo = Integer.parseInt(DBUtil.dataToString(dataEntrate, "yyyy"));
			if (annoo == anno && FxOVar.equals(FissoOVar)) {
				totale += entrata.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(totale);
	}

	/**
	 * Restituisce in un vettore di stringhe i nomi delle colonne della tabella
	 * specificata nel parametro
	 * 
	 * @param tabella
	 * @return Vector<String>
	 */
	public Vector<String> nomiColonne(final String tabella) {
		Vector<String> colonne = null;
		String sql = "";
		if (tabella.equals(Entrate.NOME_TABELLA)) {
			sql = "SELECT " + Entrate.NOME_TABELLA + "." + Entrate.DATA + ", " + Entrate.NOME_TABELLA + "."
			+ Entrate.NOME + ", " + Entrate.NOME_TABELLA + "." + Entrate.DESCRIZIONE + ", "
			+ Entrate.NOME_TABELLA + "." + Entrate.INEURO + " as euro, " + Entrate.NOME_TABELLA + "."
			+ Entrate.FISSEOVAR + " as categoria, " + Entrate.NOME_TABELLA + "." + Entrate.ID + ", "
			+ Entrate.NOME_TABELLA + "." + Entrate.DATAINS + " as inserimento" + " FROM " + tabella
			+ " order by " + Entrate.ID + " desc";
		} else if (tabella.equals(SingleSpesa.NOME_TABELLA)) {
			sql = "SELECT " + SingleSpesa.NOME_TABELLA + "." + SingleSpesa.DATA + " as data, "
			+ SingleSpesa.NOME_TABELLA + "." + SingleSpesa.NOME + ", " + SingleSpesa.NOME_TABELLA + "."
			+ SingleSpesa.DESCRIZIONE + ", " + SingleSpesa.NOME_TABELLA + "." + SingleSpesa.INEURO
			+ " as euro, " + CatSpese.NOME_TABELLA + "." + CatSpese.NOME + " as categoria, "
			+ SingleSpesa.NOME_TABELLA + "." + SingleSpesa.ID + ", " + SingleSpesa.NOME_TABELLA + "."
			+ SingleSpesa.DATAINS + " as inserimento" + " FROM " + tabella + ", " + CatSpese.NOME_TABELLA
			+ ", " + Utenti.NOME_TABELLA + " where " + SingleSpesa.NOME_TABELLA + "." + SingleSpesa.IDCATEGORIE
			+ " = " + CatSpese.NOME_TABELLA + "." + CatSpese.ID + " and " + SingleSpesa.NOME_TABELLA + "."
			+ SingleSpesa.IDUTENTE + " = " + Utenti.NOME_TABELLA + "." + Utenti.ID + " order by "
			+ SingleSpesa.ID + " desc";
		}

		final Connection cn = DBUtil.getConnection();
		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			final ResultSetMetaData rsm = rs.getMetaData();
			colonne = new Vector<String>();
			for (int i = 1; i <= rsm.getColumnCount(); i++) {
				colonne.add(rsm.getColumnName(i));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			Controllore.getLog().severe(
					"Errore nel caricamento dal database dei nomi delle colonne di " + tabella + ": " + e.getMessage());
		}
		DBUtil.closeConnection();
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return colonne;
	}

	/**
	 * @return Metodo per calcolare il totale delle entrate annuali
	 */
	public static double EAnnuale() {
		double Eannuale = 0;

		final int anno = Impostazioni.getAnno();
		final ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final Entrate entrata = listaEntrate.get(i);
			final Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
			final String annoDaData = DBUtil.dataToString(dataEntrata, "yyyy");
			if (Integer.parseInt(annoDaData) == anno) {
				Eannuale += entrata.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(Eannuale);
	}

	/**
	 * @return Metodo per calcolare il totale delle spese annuali
	 */
	public static double Annuale() {
		final int anno = Impostazioni.getAnno();
		double annuale = 0;
		final ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for (int i = 0; i < listaUscite.size(); i++) {
			final SingleSpesa uscita = listaUscite.get(i);
			final Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
			final String annoDaData = DBUtil.dataToString(dataUscita, "yyyy");
			if (Integer.parseInt(annoDaData) == anno) {
				annuale += uscita.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(annuale);

	}

	/**
	 * 
	 * 
	 * @return Metodo per calcolare il totale delle entrate per il mese
	 *         precedente
	 */
	public static double Emensile() {
		double Emensile10 = 0;
		final GregorianCalendar data = new GregorianCalendar();
		final ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final Entrate entrata = listaEntrate.get(i);
			final Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
			final int mese = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));
			final int anno = Integer.parseInt(DBUtil.dataToString(dataEntrata, "yyyy"));
			if (mese == data.get(Calendar.MONTH) && anno == data.get(Calendar.YEAR)) {
				Emensile10 += entrata.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(Emensile10);

	}

	/**
	 * 
	 * 
	 * @return Metodo per calcolare il totale delle uscite per il mese
	 *         precedente
	 */
	public static double Mensile() {
		double mensile1 = 0;
		final GregorianCalendar data = new GregorianCalendar();
		final ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for (int i = 0; i < listaUscite.size(); i++) {
			final SingleSpesa uscita = listaUscite.get(i);
			final Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
			final int mese = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
			final int anno = Integer.parseInt(DBUtil.dataToString(dataUscita, "yyyy"));

			if (mese == data.get(Calendar.MONTH) && anno == data.get(Calendar.YEAR)) {
				mensile1 += uscita.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(mensile1);

	}

	/**
	 * 
	 * 
	 * @return metodo per calcolare il totale delle entrate per il mese
	 *         precedente
	 */
	public static double EMensileInCorso() {
		final double Emensile = 0;
		double Emensile10 = 0;
		final GregorianCalendar data = new GregorianCalendar();
		final ArrayList<Entrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final Entrate entrata = listaEntrate.get(i);
			final Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), "yyyy/MM/dd");
			final int mese = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));
			final int anno = Integer.parseInt(DBUtil.dataToString(dataEntrata, "yyyy"));
			if (mese == (data.get(Calendar.MONTH) + 1) && anno == data.get(Calendar.YEAR)) {
				Emensile10 += entrata.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(Emensile);

	}

	/**
	 * Metodo che calcola il totale delle uscite per il mese in corso
	 * 
	 * @return double
	 * @throws SQLException
	 */
	public static double MensileInCorso() {
		double mensile = 0;
		final GregorianCalendar data = new GregorianCalendar();
		final ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for (int i = 0; i < listaUscite.size(); i++) {
			final SingleSpesa uscita = listaUscite.get(i);
			final Date dataUscita = DBUtil.stringToDate(uscita.getData(), "yyyy/MM/dd");
			final int mese = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
			final int anno = Integer.parseInt(DBUtil.dataToString(dataUscita, "yyyy"));

			if (mese == (data.get(Calendar.MONTH) + 1) && anno == data.get(Calendar.YEAR)) {
				mensile += uscita.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(mensile);
	}

	public static double percentoUscite(final String importanza) {
		double percentualeTipo = 0;
		final double totaleAnnuo = Annuale();
		double speseTipo = 0;

		final ArrayList<SingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();
		for (int i = 0; i < listaUscite.size(); i++) {
			final SingleSpesa uscita = listaUscite.get(i);
			if (uscita != null) {
				if (uscita.getCatSpese() != null) {
					final String importanzaSpesa = uscita.getCatSpese().getimportanza();
					if (importanzaSpesa.equals(importanza)) {
						speseTipo += uscita.getinEuro();
					}
				}
			}
		}

		if (speseTipo != 0) {
			if (totaleAnnuo != 0.0) {
				percentualeTipo = speseTipo / totaleAnnuo * 100;
			} else {
				percentualeTipo = 0;
			}
		}

		return AltreUtil.arrotondaDecimaliDouble(percentualeTipo);

	}
}