package com.molinari.gestionespese.business;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.logging.Level;
import java.util.stream.Stream;

import org.apache.commons.math3.util.MathUtils;

import com.molinari.gestionespese.business.cache.CacheBudget;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheEntrate;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.business.cache.CacheLookAndFeel;
import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IEntrate;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.Lookandfeel;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapLookAndFeel;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.ConnectionPool;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.database.UtilDb;
import com.molinari.utility.graphic.component.alert.Alert;

public class Database {

	private static final String ID_UTENTE_INTEGER_NOT_NULL = " idUtente	INTEGER NOT NULL, ";
	private static final String NOME_TEXT_NOT_NULL = " nome	VARCHAR2 NOT NULL, ";
	private static final String FOREIGN_KEY_ID_UTENTE_REFERENCES_UTENTI_ID_UTENTE = " FOREIGN KEY(idUtente) REFERENCES utenti(idUtente) ";
	private static final String WHERE = " where ";
	private static final String ROW_S = " row/s";
	private static final String AND = " AND ";
	private static final String FROM = " FROM ";
	public static final String YYYY_MM_DD = "yyyy/MM/dd";
	private static Database singleton;
	public static final String DB_URL_WORKSPACE = "../GestioneSpese.sqlite";
	public static final String DB_URL_JAR = "./GestioneSpese.sqlite";
	private static String dburl = DB_URL_WORKSPACE;

	private Database() {

	}

	public static synchronized Database getSingleton() {

		if (singleton == null) {
			singleton = new Database();
		}
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
		nimbus.setvalore("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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
		new File(Database.dburl);

		String sql = "CREATE TABLE utenti (idUtente INTEGER PRIMARY KEY  auto_increment  NOT NULL , nome VARCHAR2 NOT NULL , cognome VARCHAR2 NOT NULL , username VARCHAR2 NOT NULL  UNIQUE , password VARCHAR2 NOT NULL );";
		final ConnectionPool cp = ConnectionPool.getSingleton();
		executeUpdate(sql, cp);
		sql = queryCreateGruppo();
		executeUpdate(sql, cp);
		sql = "CREATE TABLE lookAndFeel (idLook INTEGER PRIMARY KEY  auto_increment  NOT NULL , nome VARCHAR2 NOT NULL , valore VARCHAR2 NOT NULL , usato INTEGER NOT NULL );";
		executeUpdate(sql, cp);
		sql = "CREATE TABLE risparmio (idRisparmio INTEGER PRIMARY KEY  auto_increment  NOT NULL , PerSulTotale DOUBLE NOT NULL , nomeOggetto VARCHAR2, costoOggetto DOUBLE);";
		executeUpdate(sql, cp);
		sql = "CREATE TABLE cat_spese (idCategoria  INTEGER PRIMARY KEY auto_increment NOT NULL,descrizione  VARCHAR2 NOT NULL,importanza  VARCHAR2 NOT NULL,nome  VARCHAR2 NOT NULL,idGruppo INTEGER NOT NULL, FOREIGN KEY (idGruppo) REFERENCES gruppi (idGruppo));";
		executeUpdate(sql, cp);
		sql = "CREATE TABLE budget (idBudget  INTEGER PRIMARY KEY auto_increment NOT NULL,idCategorie  INTEGER NOT NULL UNIQUE,percSulTot  DOUBLE NOT NULL,FOREIGN KEY (idCategorie) REFERENCES cat_spese (idCategoria));";
		executeUpdate(sql, cp);
		sql = queryCreateEntrate();
		executeUpdate(sql, cp);
		sql = queryCreateUscite();
		executeUpdate(sql, cp);
		sql = queryCreateNote();
		executeUpdate(sql, cp);

		generaDatiTabellaLook();
	}

	private String queryCreateEntrate() {
		StringBuilder sb = new StringBuilder();
		sb.append(" CREATE TABLE entrate ( ");
		sb.append(" idEntrate	INTEGER NOT NULL, ");
		sb.append(" descrizione	VARCHAR2 NOT NULL, ");
		sb.append(" Fisse_o_Var	VARCHAR2 NOT NULL, ");
		sb.append(" inEuro	INTEGER NOT NULL, ");
		sb.append(" data	VARCHAR2 NOT NULL, ");
		sb.append(NOME_TEXT_NOT_NULL);
		sb.append(ID_UTENTE_INTEGER_NOT_NULL);
		sb.append(" dataIns	VARCHAR2, ");
		sb.append(" PRIMARY KEY(idEntrate), ");
		sb.append(FOREIGN_KEY_ID_UTENTE_REFERENCES_UTENTI_ID_UTENTE);
		sb.append(" ); ");
		return sb.toString();
	}

	private String queryCreateUscite() {
		StringBuilder sb = new StringBuilder();
		sb.append(" CREATE TABLE single_spesa ( ");
		sb.append(" idSpesa	INTEGER NOT NULL PRIMARY KEY auto_increment,  ");
		sb.append(" Data	VARCHAR2 NOT NULL, ");
		sb.append(" inEuro	INTEGER NOT NULL, ");
		sb.append(" descrizione	VARCHAR2 NOT NULL, ");
		sb.append(" idCategorie	INTEGER NOT NULL, ");
		sb.append(NOME_TEXT_NOT_NULL);
		sb.append(" dataIns	TEXT, ");
		sb.append(ID_UTENTE_INTEGER_NOT_NULL);
		sb.append(FOREIGN_KEY_ID_UTENTE_REFERENCES_UTENTI_ID_UTENTE);
		sb.append(", FOREIGN KEY(idCategorie) REFERENCES cat_spese(idCategoria)");
		sb.append(" ); ");
		return sb.toString();
	}

	private String queryCreateGruppo() {
		StringBuilder sb = new StringBuilder();
		sb.append(" CREATE TABLE gruppi ( ");
		sb.append(" idGruppo	INTEGER NOT NULL PRIMARY KEY auto_increment,  ");
		sb.append(NOME_TEXT_NOT_NULL);
		sb.append(" descrizione	TEXT, ");
		sb.append(ID_UTENTE_INTEGER_NOT_NULL);
		sb.append(FOREIGN_KEY_ID_UTENTE_REFERENCES_UTENTI_ID_UTENTE);
		sb.append(" ); ");
		return sb.toString();
	}
	private String queryCreateNote() {
		StringBuilder sb = new StringBuilder();
		sb.append(" CREATE TABLE note ( ");
		sb.append(" idNote	INTEGER NOT NULL, ");
		sb.append(" nome	VARCHAR2 NOT NULL, ");
		sb.append(" descrizione	TEXT NOT NULL, ");
		sb.append(ID_UTENTE_INTEGER_NOT_NULL);
		sb.append(" data	VARCHAR2 NOT NULL, ");
		sb.append(" dataIns	VARCHAR2 NOT NULL, ");
		sb.append(" PRIMARY KEY(idNote), ");
		sb.append(FOREIGN_KEY_ID_UTENTE_REFERENCES_UTENTI_ID_UTENTE);
		sb.append(" ); ");
		return sb.toString();
	}

	private void executeUpdate(String sql, final ConnectionPool cp) throws SQLException {
		cp.executeUpdate(sql);
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
	public boolean eseguiIstruzioneSql(final String comando, final String tabella, final Map<String, String> campi,
			final Map<String, String> clausole) {
		return UtilDb.eseguiIstruzioneSql(comando, tabella, campi, clausole);
	}

	/**
	 * Il metodo esegue le stringhe di codice Sql inserite nel campo.
	 *
	 * @param sql
	 * @return HashMap<String, ArrayList>
	 */
	public Map<String, ArrayList<String>> terminaleSql(final String sql) {
		final HashMap<String, ArrayList<String>> nomi = new HashMap<>();
		if (startWith(sql, "S")) {
			try {

				return execute(sql, nomi);

			} catch (final SQLException e) {
				ControlloreBase.getLog().log(Level.SEVERE, "Operazione SQL non eseguita: " + sql, e);
				Alert.segnalazioneErroreGrave("Operazione SQL non eseguita:" + e.getMessage());
			}

		} else {

			try {
				int executeUpdate = ConnectionPool.getSingleton().executeUpdate(sql);
				sendAlert(sql, executeUpdate);
				invalidateCache();
			} catch (final SQLException e) {
				ControlloreBase.getLog().log(Level.SEVERE, "Operazione SQL non eseguita: " + sql, e);
				Alert.segnalazioneErroreGrave("Operazione SQL non eseguita:" + e.getMessage());
			}
		}
		return nomi;
	}

	private void invalidateCache() {
		CacheEntrate.getSingleton().setCaricata(false);
		CacheUscite.getSingleton().setCaricata(false);
		CacheCategorie.getSingleton().setCaricata(false);
		CacheGruppi.getSingleton().setCaricata(false);
		CacheBudget.getSingleton().setCaricata(false);
		CacheUtenti.getSingleton().setCaricata(false);
		CacheLookAndFeel.getSingleton().setCaricata(false);
		CacheNote.getSingleton().setCaricata(false);
		
	}

	private boolean startWith(final String sql, String starting) {
		return starting.equalsIgnoreCase(sql.substring(0, 1));
	}

	private void sendAlert(String sql, long executeUpdate) {
		if (startWith(sql, "I")) {
			Alert.segnalazioneInfo("Insert " + executeUpdate + ROW_S);
		} else if (startWith(sql, "U")) {
			Alert.segnalazioneInfo("Update " + executeUpdate + ROW_S);
		} else if (startWith(sql, "D")) {
			Alert.segnalazioneInfo("Delete " + executeUpdate + ROW_S);
		}

	}

	private Map<String, ArrayList<String>> execute(final String sql, final HashMap<String, ArrayList<String>> nomi)
			throws SQLException {
		return new ExecuteResultSet<HashMap<String, ArrayList<String>>>() {

			@Override
			protected HashMap<String, ArrayList<String>> doWithResultSet(ResultSet rs) throws SQLException {
				final ResultSetMetaData rsmd = rs.getMetaData();
				final ArrayList<String> lista = new ArrayList<>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					lista.add(rsmd.getColumnLabel(i + 1));
				}
				nomi.put("nomiColonne", lista);
				int z = 0;
				while (rs.next()) {
					final ArrayList<String> lista2 = new ArrayList<>();
					z++;
					riempiLista(rs, rsmd, lista2);
					nomi.put("row" + z, lista2);
				}
				return nomi;
			}

		}.execute(sql);
	}

	private void riempiLista(ResultSet rs, final ResultSetMetaData rsmd, final ArrayList<String> lista2)
			throws SQLException {
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
	}

	// questo metodo riempie tabella uscite
	/**
	 * Restuisce un double che rappresenta la somma delle uscite per tipologia e
	 * mese di appartenenza
	 *
	 * @param mese
	 * @param categoria
	 * @return double
	 * @throws Exception
	 */
	public static double speseMeseCategoria(final int mese, final int categoria) {

		double spesaTotMeseCat = 0.0;
		final List<ISingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();

		for (int i = 0; i < listaUscite.size(); i++) {
			final ISingleSpesa uscita = listaUscite.get(i);
			final ICatSpese cat = uscita.getCatSpese();
			if (cat != null) {
				final Date dataUscita = DBUtil.stringToDate(uscita.getData(), YYYY_MM_DD);
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
		final List<ISingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();

		for (int i = 0; i < listaUscite.size(); i++) {
			final ISingleSpesa uscita = listaUscite.get(i);
			final ICatSpese cat = uscita.getCatSpese();
			if (cat != null) {
				final IGruppi group = cat.getGruppi();
				final Date dataUscita = DBUtil.stringToDate(uscita.getData(), YYYY_MM_DD);
				final int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
				if (group != null && group.getidGruppo() != 0 && mesee == mese && group.getidGruppo() == gruppo) {
					spesaTotMeseGruppo += uscita.getinEuro();
				}
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(spesaTotMeseGruppo);
	}

	public static double speseMeseSenzaGruppo(final int mese, final int categoria) {
		double spesaTotMeseCat = 0.0;
		final List<ISingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();
		for (int i = 0; i < listaUscite.size(); i++) {
			final ISingleSpesa uscita = listaUscite.get(i);
			final ICatSpese cat = uscita.getCatSpese();
			if (cat != null) {
				final IGruppi group = cat.getGruppi();
				final Date dataUscita = DBUtil.stringToDate(uscita.getData(), YYYY_MM_DD);
				final int mesee = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
				if ((group == null || group.getidGruppo() == 0) && mesee == mese && cat.getidCategoria() == categoria) {
					spesaTotMeseCat += uscita.getinEuro();
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
	public double entrateMeseTipo(final int mese, final String tipoEntrata) {
		double entrateMeseTipo = 0;
		final List<IEntrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtenteEAnno();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final IEntrate entrata = listaEntrate.get(i);
			final String cat = entrata.getFisseoVar();
			final Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), YYYY_MM_DD);
			final int mesee = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));

			if (mesee == mese && cat.equalsIgnoreCase(tipoEntrata)) {
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
		final List<ISingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for (int i = 0; i < listaUscite.size(); i++) {
			final ISingleSpesa uscita = listaUscite.get(i);
			final Date dataUscita = DBUtil.stringToDate(uscita.getData(), YYYY_MM_DD);
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
		final List<IEntrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtenteEAnno();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final IEntrate entrata = listaEntrate.get(i);
			final Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), YYYY_MM_DD);
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
		final List<ISingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for (int i = 0; i < listaUscite.size(); i++) {
			final ISingleSpesa uscita = listaUscite.get(i);
			final int cat = uscita.getCatSpese().getidCategoria();
			final Date dataUscita = DBUtil.stringToDate(uscita.getData(), YYYY_MM_DD);
			final int annoo = Integer.parseInt(DBUtil.dataToString(dataUscita, "yyyy"));
			if (annoo == anno && cat == categoria) {
				totale += uscita.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(totale);
	}

	public static double totaleEntrateAnnoCategoria(final String fissoOVar) {
		double totale = 0;
		final int anno = Impostazioni.getAnno();
		final List<IEntrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final IEntrate entrata = listaEntrate.get(i);
			final String fxOVar = entrata.getFisseoVar();
			final Date dataEntrate = DBUtil.stringToDate(entrata.getdata(), YYYY_MM_DD);
			final int annoo = Integer.parseInt(DBUtil.dataToString(dataEntrate, "yyyy"));
			if (annoo == anno && fxOVar.equals(fissoOVar)) {
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
	 * @return List<String>
	 */
	public List<String> nomiColonne(final String tabella) {
		String sql = "";
		if (tabella.equals(Entrate.NOME_TABELLA)) {
			sql = "SELECT " + Entrate.NOME_TABELLA + "." + Entrate.COL_DATA + ", " + Entrate.NOME_TABELLA + "."
					+ Entrate.COL_NOME + ", " + Entrate.NOME_TABELLA + "." + Entrate.COL_DESCRIZIONE + ", "
					+ Entrate.NOME_TABELLA + "." + Entrate.COL_INEURO + " as euro, " + Entrate.NOME_TABELLA + "."
					+ Entrate.COL_FISSEOVAR + " as categoria, " + Entrate.NOME_TABELLA + "." + Entrate.ID + ", "
					+ Entrate.NOME_TABELLA + "." + Entrate.COL_DATAINS + " as inserimento" + FROM + tabella
					+ " order by " + Entrate.ID + " desc";
		} else if (tabella.equals(SingleSpesa.NOME_TABELLA)) {
			sql = "SELECT " + SingleSpesa.NOME_TABELLA + "." + SingleSpesa.COL_DATA + " as data, "
					+ SingleSpesa.NOME_TABELLA + "." + SingleSpesa.COL_NOME + ", " + SingleSpesa.NOME_TABELLA + "."
					+ SingleSpesa.COL_DESCRIZIONE + ", " + SingleSpesa.NOME_TABELLA + "." + SingleSpesa.COL_INEURO
					+ " as euro, " + CatSpese.NOME_TABELLA + "." + CatSpese.COL_NOME + " as categoria, "
					+ SingleSpesa.NOME_TABELLA + "." + SingleSpesa.ID + ", " + SingleSpesa.NOME_TABELLA + "."
					+ SingleSpesa.COL_DATAINS + " as inserimento" + FROM + tabella + ", " + CatSpese.NOME_TABELLA + ", "
					+ Utenti.NOME_TABELLA + WHERE + SingleSpesa.NOME_TABELLA + "." + SingleSpesa.COL_IDCATEGORIE + " = "
					+ CatSpese.NOME_TABELLA + "." + CatSpese.ID + AND + SingleSpesa.NOME_TABELLA + "."
					+ SingleSpesa.COL_IDUTENTE + " = " + Utenti.NOME_TABELLA + "." + Utenti.ID + " order by "
					+ SingleSpesa.ID + " desc";
		}

		try {
			return new ExecuteResultSet<List<String>>() {

				@Override
				protected List<String> doWithResultSet(ResultSet rs) throws SQLException {
					final List<String> colonne = new ArrayList<>();

					final ResultSetMetaData rsm = rs.getMetaData();
					final int columnCount = rsm.getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						colonne.add(rsm.getColumnName(i));
					}
					
					return colonne;
				}
			}.execute(sql);
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		return new ArrayList<>();
	}

	/**
	 * @return Metodo per calcolare il totale delle entrate annuali
	 */
	public static double eAnnuale() {
		double eannuale = 0;

		final int anno = Impostazioni.getAnno();
		final List<IEntrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final IEntrate entrata = listaEntrate.get(i);
			final Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), YYYY_MM_DD);
			final String annoDaData = DBUtil.dataToString(dataEntrata, "yyyy");
			if (Integer.parseInt(annoDaData) == anno) {
				eannuale += entrata.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(eannuale);
	}

	/**
	 * @return Metodo per calcolare il totale delle spese annuali
	 */
	public static double uAnnuale() {
		final int anno = Impostazioni.getAnno();
		double annuale = 0;
		final List<ISingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for (int i = 0; i < listaUscite.size(); i++) {
			final ISingleSpesa uscita = listaUscite.get(i);
			final Date dataUscita = DBUtil.stringToDate(uscita.getData(), YYYY_MM_DD);
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
	public static double eMensile() {
		double emensile10 = 0;
		final GregorianCalendar data = new GregorianCalendar();
		final List<IEntrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final IEntrate entrata = listaEntrate.get(i);
			final Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), YYYY_MM_DD);
			final int mese = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));
			final int anno = Integer.parseInt(DBUtil.dataToString(dataEntrata, "yyyy"));
			if (mese == data.get(Calendar.MONTH) && anno == data.get(Calendar.YEAR)) {
				emensile10 += entrata.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(emensile10);

	}

	/**
	 *
	 *
	 * @return Metodo per calcolare il totale delle uscite per il mese
	 *         precedente
	 */
	public static double uMensile() {
		double mensile1 = 0;
		final GregorianCalendar data = new GregorianCalendar();
		final List<ISingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for (int i = 0; i < listaUscite.size(); i++) {
			final ISingleSpesa uscita = listaUscite.get(i);
			final Date dataUscita = DBUtil.stringToDate(uscita.getData(), YYYY_MM_DD);
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
	public static double eMensileInCorso() {
		double emensile10 = 0;
		final GregorianCalendar data = new GregorianCalendar();
		final List<IEntrate> listaEntrate = CacheEntrate.getSingleton().getAllEntrateForUtente();
		for (int i = 0; i < listaEntrate.size(); i++) {
			final IEntrate entrata = listaEntrate.get(i);
			final Date dataEntrata = DBUtil.stringToDate(entrata.getdata(), YYYY_MM_DD);
			final int mese = Integer.parseInt(DBUtil.dataToString(dataEntrata, "MM"));
			final int anno = Integer.parseInt(DBUtil.dataToString(dataEntrata, "yyyy"));
			if (mese == data.get(Calendar.MONTH) + 1 && anno == data.get(Calendar.YEAR)) {
				emensile10 += entrata.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(emensile10);

	}

	/**
	 * Metodo che calcola il totale delle uscite per il mese in corso
	 *
	 * @return double
	 * @throws SQLException
	 */
	public static double uMensileInCorso() {
		double mensile = 0;
		final GregorianCalendar data = new GregorianCalendar();
		final List<ISingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtente();
		for (int i = 0; i < listaUscite.size(); i++) {
			final ISingleSpesa uscita = listaUscite.get(i);
			final Date dataUscita = DBUtil.stringToDate(uscita.getData(), YYYY_MM_DD);
			final int mese = Integer.parseInt(DBUtil.dataToString(dataUscita, "MM"));
			final int anno = Integer.parseInt(DBUtil.dataToString(dataUscita, "yyyy"));

			if (mese == data.get(Calendar.MONTH) + 1 && anno == data.get(Calendar.YEAR)) {
				mensile += uscita.getinEuro();
			}
		}
		return AltreUtil.arrotondaDecimaliDouble(mensile);
	}

	public static double percentoUscite(final String importanza) {
		double percentualeTipo = 0;
		final double totaleAnnuo = uAnnuale();

		final List<ISingleSpesa> listaUscite = CacheUscite.getSingleton().getAllUsciteForUtenteEAnno();
		final Stream<ISingleSpesa> stream = listaUscite.stream();
		final Predicate<? super ISingleSpesa> predicate = getPredicatePercentoUscite(importanza);
		final Stream<ISingleSpesa> filter = stream.filter(predicate);
		final double speseTipo = filter.mapToDouble(getFilter()).sum();
		boolean spesezero = MathUtils.equals(speseTipo, 0);
		boolean totannozero = MathUtils.equals(totaleAnnuo, 0);
		if (!spesezero && !totannozero) {
			percentualeTipo = speseTipo / totaleAnnuo * 100;
		}

		return AltreUtil.arrotondaDecimaliDouble(percentualeTipo);

	}

	private static Predicate<? super ISingleSpesa> getPredicatePercentoUscite(final String importanza) {
		return ss -> importanza != null && ss.getCatSpese() != null
				&& importanza.equals(ss.getCatSpese().getimportanza());
	}

	private static ToDoubleFunction<? super ISingleSpesa> getFilter() {
		return ISingleSpesa::getinEuro;
	}

	public static String getDburl() {
		return dburl;
	}

	public static void setDburl(String dburl) {
		Database.dburl = dburl;
	}

}