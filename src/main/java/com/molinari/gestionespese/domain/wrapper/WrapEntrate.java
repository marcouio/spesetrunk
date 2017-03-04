package com.molinari.gestionespese.domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.IEntrate;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;

import controller.ControlloreBase;
import db.Clausola;
import db.ConnectionPool;
import db.ExecutePreparedStatement;
import db.ExecuteResultSet;
import db.dao.IDAO;

public class WrapEntrate extends Observable implements IEntrate, IDAO<Entrate> {

	private static final String AND = " AND ";
	private static final String DELETE_FROM = "DELETE FROM ";
	private static final String WHERE = " WHERE ";
	private static final String SELECT_FROM = "SELECT * FROM ";
	private final Entrate entrate;
	private final WrapBase base = new WrapBase(); 
	public WrapEntrate() {
		entrate = new Entrate();
	}

	@Override
	public Entrate selectById(final int id) {

		final String sql = SELECT_FROM + Entrate.NOME_TABELLA + WHERE + Entrate.ID + " = " + id;

		try {

			return getEntrata(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		} finally {
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return null;

	}

	private Entrate getEntrata(final String sql) throws SQLException {
		final Entrate entrata = new Entrate();
		return new ExecuteResultSet<Entrate>() {

			@Override
			protected Entrate doWithResultSet(ResultSet rs) throws SQLException {

				if (rs.next()) {
					riempiEntrataFromResultSet(entrata, rs);
				}
				return entrata;
			}

			

		}.execute(sql);
	}

	public List<Entrate> selectAllForUtente() {
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();

		final String sql = SELECT_FROM + Entrate.NOME_TABELLA + WHERE + Entrate.COL_IDUTENTE + " = " + utente.getidUtente();
		try {

			return new ExecuteResultSet<List<Entrate>>() {

				@Override
				protected List<Entrate> doWithResultSet(ResultSet rs) throws SQLException {

					final List<Entrate> entrateLoc = new ArrayList<>();
					while (rs != null && rs.next()) {

						final Entrate entrata = riempiEntrataWithResultSet(utente, rs);
						entrateLoc.add(entrata);
					}
					return entrateLoc;
				}

			}.execute(sql);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		return new ArrayList<>();
	}

	@Override
	public List<Entrate> selectAll() {


		final String sql = SELECT_FROM + Entrate.NOME_TABELLA;
		try {
			CacheUtenti.getSingleton().getAllUtenti();
			return new ExecuteResultSet<List<Entrate>>() {

				@Override
				protected List<Entrate> doWithResultSet(ResultSet rs) throws SQLException {
					return extracted(rs);
				}

				private List<Entrate> extracted(ResultSet rs)
						throws SQLException {
					final List<Entrate> entrateLoc = new ArrayList<>();

					while (rs != null && rs.next()) {
						final Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
						final Entrate entrata = riempiEntrataWithResultSet(utente, rs);
						entrateLoc.add(entrata);
					}
					return entrateLoc;
				}

			}.execute(sql);


		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return new ArrayList<>();
	}

	@Override
	public boolean insert(final Entrate oggettoEntita) {
		boolean ok = false;
		final Connection cn = ConnectionPool.getSingleton().getConnection();
		String sql = "";
		try {

			sql = "INSERT INTO " + Entrate.NOME_TABELLA + " (" + Entrate.COL_DESCRIZIONE + ", " + Entrate.COL_FISSEOVAR + ", " + Entrate.COL_INEURO + ", " + Entrate.COL_DATA + ", " + Entrate.COL_NOME
					+ ", " + Entrate.COL_IDUTENTE + ", " + Entrate.COL_DATAINS + ") VALUES (?,?,?,?,?,?,?)";
			
			new ExecutePreparedStatement<Entrate>() {

				@Override
				protected void doWithPreparedStatement(PreparedStatement ps, Entrate obj) throws SQLException {
					ps.setString(1, obj.getdescrizione());
					ps.setString(2, obj.getFisseoVar());
					ps.setDouble(3, obj.getinEuro());
					ps.setString(4, obj.getdata());
					ps.setString(5, obj.getnome());
					ps.setInt(6, obj.getUtenti().getidUtente());
					ps.setString(7, obj.getDataIns());
					
				}
			}.executeUpdate(sql, (Entrate) oggettoEntita);
			
			
		} catch (final Exception e) {
			ok = false;
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		} finally {
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		}
		return ok;
	}

	private PreparedStatement createPreparedStatement(final Connection cn, String sql) throws SQLException {
		return cn.prepareStatement(sql);
	}

	@Override
	public boolean delete(final int id) {
		boolean ok = false;
		final String sql = DELETE_FROM + Entrate.NOME_TABELLA + WHERE + Entrate.ID + " = " + id;

		try {

			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}

		return ok;
	}

	@Override
	public boolean update(final Entrate oggettoEntita) {

		final Entrate entrata = (Entrate) oggettoEntita;
		final String sql = getQueryUpdate(entrata);
		
		boolean ok = base.executeUpdate(sql);
		
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return ok;
	}

	private String getQueryUpdate(final Entrate entrata) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(Entrate.NOME_TABELLA);
		sb.append(" SET ");
		sb.append(Entrate.COL_DESCRIZIONE);
		sb.append(" = '");
		sb.append(entrata.getdescrizione());
		sb.append("', ");
		sb.append(Entrate.COL_FISSEOVAR);
		sb.append(" = '");
		sb.append(entrata.getFisseoVar());
		sb.append("', ");
		sb.append(Entrate.COL_INEURO);
		sb.append(" = ");
		sb.append(entrata.getinEuro());
		sb.append(", ");
		sb.append(Entrate.COL_DATA);
		sb.append(" = '");
		sb.append(entrata.getdata());
		sb.append("', ");
		sb.append(Entrate.COL_NOME);
		sb.append(" = '");
		sb.append(entrata.getnome());
		sb.append("', ");
		sb.append(Entrate.COL_IDUTENTE);
		sb.append(" = ");
		sb.append(entrata.getUtenti().getidUtente());
		sb.append(", ");
		sb.append(Entrate.COL_DATAINS);
		sb.append(" = '");
		sb.append(entrata.getDataIns());
		sb.append("' WHERE ");
		sb.append(Entrate.ID);
		sb.append(" = ");
		sb.append(entrata.getidEntrate());
		return sb.toString();
	}

	@Override
	public boolean deleteAll() {
		final String sql = DELETE_FROM + Entrate.NOME_TABELLA;
		return base.executeUpdate(sql);
	}

	// metodo che restituisce le ultime dieci entrate
	/**
	 * Permette di ottenere un vettore con un numero di entita' entrate inserito
	 * come parametro
	 *
	 * @param numEntry
	 * @return List<Entrate>
	 */
	public List<Entrate> movimentiEntrateFiltrati(final String dataDa, final String dataA, final String nome, final Double euro, final String categoria) {
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getidUtente();
		}

		final StringBuilder sql = new StringBuilder();
		sql.append(SELECT_FROM + Entrate.NOME_TABELLA + WHERE + Entrate.COL_IDUTENTE + " = " + idUtente);
		if (AltreUtil.checkData(dataDa) && AltreUtil.checkData(dataA)) {
			sql.append(AND + Entrate.COL_DATA + " BETWEEN '" + dataDa + "'" + " AND '" + dataA + "'");
		} else if (AltreUtil.checkData(dataDa)) {
			sql.append(AND + Entrate.COL_DATA + " = '" + dataDa + "'");
		}
		if (nome != null) {
			sql.append(AND + Entrate.COL_NOME + " = '" + nome + "'");
		}
		if (euro != null) {
			sql.append(AND + Entrate.COL_INEURO + " = " + euro);
		}
		if (categoria != null) {
			sql.append(AND + Entrate.COL_FISSEOVAR + " = '" + categoria + "'");
		}

		try {

			return new ExecuteResultSet<List<Entrate>>() {

				@Override
				protected List<Entrate> doWithResultSet(ResultSet rs) throws SQLException {

					final List<Entrate> entrateLoc = new ArrayList<>();
					while (rs != null && rs.next()) {
						final Entrate e = new Entrate();
						e.setdata(rs.getString(5));
						e.setdescrizione(rs.getString(2));
						e.setFisseoVar(rs.getString(3));
						e.setidEntrate(rs.getInt(1));
						e.setinEuro(rs.getDouble(4));
						e.setnome(rs.getString(6));
						e.setUtenti(utente);
						e.setDataIns(rs.getString(8));
						entrateLoc.add(e);
					}
					return entrateLoc;
				}

			}.execute(sql.toString());

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return new ArrayList<>();

	}

	// metodo che restituisce le ultime dieci entrate
	/**
	 * Permette di ottenere un vettore con un numero di entita' entrate inserito
	 * come parametro
	 *
	 * @param numEntry
	 * @return List<Entrate>
	 */
	public List<Entrate> dieciEntrate(final int numEntry) {
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getidUtente();
		}

		final String sql = getQueryDieciEntrate(numEntry, idUtente);

		try {
			return getExecRsDieciEntrate(utente).execute(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return new ArrayList<>();

	}

	private String getQueryDieciEntrate(final int numEntry, int idUtente) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(SELECT_FROM);
		stringBuilder.append(Entrate.NOME_TABELLA);
		stringBuilder.append(" WHERE ");
		stringBuilder.append(Entrate.COL_DATA);
		stringBuilder.append(" BETWEEN '");
		stringBuilder.append(Impostazioni.getAnno());
		stringBuilder.append("/01/01");
		stringBuilder.append("'");
		stringBuilder.append(" AND '");
		stringBuilder.append(Impostazioni.getAnno());
		stringBuilder.append("/12/31");
		stringBuilder.append("'");
		stringBuilder.append(AND);
		stringBuilder.append(Entrate.COL_IDUTENTE);
		stringBuilder.append(" = ");
		stringBuilder.append(idUtente);
		stringBuilder.append(" ORDER BY ");
		stringBuilder.append(Entrate.ID);
		stringBuilder.append(" desc limit 0,");
		stringBuilder.append(numEntry);
		return stringBuilder.toString();
	}

	private ExecuteResultSet<List<Entrate>> getExecRsDieciEntrate(final Utenti utente) {
		return new ExecuteResultSet<List<Entrate>>() {

			@Override
			protected List<Entrate> doWithResultSet(ResultSet rs) throws SQLException {

				final List<Entrate> entrateLoc = new ArrayList<>();

				while (rs != null && rs.next()) {
					final Entrate e = new Entrate();
					e.setdata(rs.getString(5));
					e.setdescrizione(rs.getString(2));
					e.setFisseoVar(rs.getString(3));
					e.setidEntrate(rs.getInt(1));
					e.setinEuro(rs.getDouble(4));
					e.setnome(rs.getString(6));
					e.setUtenti(utente);
					e.setDataIns(rs.getString(8));
					entrateLoc.add(e);
				}

				return entrateLoc;
			}

		};
	}

	/**
	 * Cancella l'ultima entita' "Entrate" inserita
	 */
	public boolean DeleteLastEntrate() {
		boolean ok = false;

		final String sql = getQueryDeleteLastEntrate();

		final Connection cn = ConnectionPool.getSingleton().getConnection();

		try {

			ok = new ExecuteResultSet<Boolean>() {

				@Override
				protected Boolean doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						final String sql2 = DELETE_FROM + Entrate.NOME_TABELLA + WHERE + Entrate.ID + "=?";
						
						final PreparedStatement ps = createPreparedStatement(cn, sql2);
						ps.setInt(1, rs.getInt(1));
						ps.executeUpdate();
					}
					return true;
				}

			}.execute(sql);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ControlloreBase.getLog().severe("Operazione non riuscita: " + e.getMessage());
		}
		return ok;
	}

	private String getQueryDeleteLastEntrate() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(SELECT_FROM);
		stringBuilder.append(Entrate.NOME_TABELLA);
		stringBuilder.append(WHERE);
		stringBuilder.append(Entrate.COL_IDUTENTE);
		stringBuilder.append(" = ");
		stringBuilder.append(((Utenti) Controllore.getSingleton().getUtenteLogin()).getidUtente());
		stringBuilder.append(" ORDER BY ");
		stringBuilder.append(Entrate.COL_DATAINS);
		stringBuilder.append(" DESC");
		return stringBuilder.toString();
	}

	@Override
	public String getdata() {
		return entrate.getdata();
	}

	@Override
	public void setdata(final String data) {
		entrate.setdata(data);
	}

	@Override
	public String getdescrizione() {
		return entrate.getdescrizione();
	}

	@Override
	public void setdescrizione(final String descrizione) {
		entrate.setdescrizione(descrizione);
	}

	@Override
	public String getFisseoVar() {
		return entrate.getFisseoVar();
	}

	@Override
	public void setFisseoVar(final String fisseoVar) {
		entrate.setFisseoVar(fisseoVar);
	}

	@Override
	public int getidEntrate() {
		return entrate.getidEntrate();
	}

	@Override
	public void setidEntrate(final int idEntrate) {
		entrate.setidEntrate(idEntrate);
	}

	@Override
	public double getinEuro() {
		return entrate.getinEuro();
	}

	@Override
	public void setinEuro(final double inEuro) {
		entrate.setinEuro(inEuro);
	}

	@Override
	public String getnome() {
		return entrate.getnome();
	}

	@Override
	public void setnome(final String nome) {
		entrate.setnome(nome);
	}

	@Override
	public Utenti getUtenti() {
		return entrate.getUtenti();
	}

	@Override
	public void setUtenti(final Utenti utenti) {
		entrate.setUtenti(utenti);
	}

	@Override
	public void setDataIns(final String date) {
		entrate.setDataIns(date);
	}

	@Override
	public String getDataIns() {
		return entrate.getDataIns();
	}

	@Override
	public Entrate getEntitaPadre() {
		return entrate;
	}

	@Override
	public List<Entrate> selectWhere(List<Clausola> clausole,
			String appentoToQuery) {
		throw new UnsupportedOperationException();
	}

	private void riempiEntrataFromResultSet(final Entrate entrata, ResultSet rs) throws SQLException {
		final Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
		entrata.setidEntrate(rs.getInt(1));
		entrata.setdescrizione(rs.getString(2));
		entrata.setFisseoVar(rs.getString(3));
		entrata.setinEuro(rs.getDouble(4));
		entrata.setdata(rs.getString(5));
		entrata.setnome(rs.getString(6));
		entrata.setUtenti(utente);
		entrata.setDataIns(rs.getString(8));
	}

	private Entrate riempiEntrataWithResultSet(final Utenti utente, ResultSet rs) throws SQLException {
		final Entrate entrata = new Entrate();
		entrata.setidEntrate(rs.getInt(1));
		entrata.setdescrizione(rs.getString(2));
		entrata.setFisseoVar(rs.getString(3));
		entrata.setinEuro(rs.getDouble(4));
		entrata.setdata(rs.getString(5));
		entrata.setnome(rs.getString(6));
		entrata.setUtenti(utente);
		entrata.setDataIns(rs.getString(8));
		return entrata;
	}

}
