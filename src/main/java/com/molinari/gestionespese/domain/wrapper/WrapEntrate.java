package com.molinari.gestionespese.domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.cache.CacheEntrate;
import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.IEntrate;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.Clausola;
import com.molinari.utility.database.ConnectionPool;
import com.molinari.utility.database.DeleteBase;
import com.molinari.utility.database.ExecutePreparedStatement;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.database.OggettoSQL;
import com.molinari.utility.database.Query;
import com.molinari.utility.database.dao.IDAO;

public class WrapEntrate extends Observable implements IEntrate, IDAO<IEntrate> {

	private static final long serialVersionUID = 1L;
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
	public IEntrate selectById(final int id) {

		final String sql = SELECT_FROM + Entrate.NOME_TABELLA + WHERE + Entrate.ID + " = " + id;

		try {

			return getEntrata(sql);
			
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		} finally {
		}
		return null;

	}

	private IEntrate getEntrata(final String sql) throws SQLException {
		return new ExecuteResultSet<IEntrate>() {

			@Override
			protected IEntrate doWithResultSet(ResultSet rs) throws SQLException {
				final IEntrate entrata = new Entrate();
				if (rs.next()) {
					riempiEntrataFromResultSet(entrata, rs);
				}
				return entrata;
			}

			

		}.execute(sql);
	}

	public List<IEntrate> selectAllForUtente() {
		final Utenti utente = (Utenti) Controllore.getUtenteLogin();

		final String sql = SELECT_FROM + Entrate.NOME_TABELLA + WHERE + Entrate.COL_IDUTENTE + " = " + utente.getidUtente();
		try {

			return new ExecuteResultSet<List<IEntrate>>() {

				@Override
				protected List<IEntrate> doWithResultSet(ResultSet rs) throws SQLException {

					final List<IEntrate> entrateLoc = new ArrayList<>();
					while (rs != null && rs.next()) {

						final IEntrate entrata = riempiEntrataWithResultSet(utente, rs);
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
	public List<IEntrate> selectAll() {


		final String sql = SELECT_FROM + Entrate.NOME_TABELLA;
		try {
			CacheUtenti.getSingleton().getAllUtenti();
			return new ExecuteResultSet<List<IEntrate>>() {

				@Override
				protected List<IEntrate> doWithResultSet(ResultSet rs) throws SQLException {
					return extracted(rs);
				}

				private List<IEntrate> extracted(ResultSet rs)
						throws SQLException {
					final List<IEntrate> entrateLoc = new ArrayList<>();

					while (rs != null && rs.next()) {
						final IUtenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
						final IEntrate entrata = riempiEntrataWithResultSet(utente, rs);
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
	public boolean insert(final IEntrate oggettoEntita) {
		boolean ok = false;
		String sql = "";
		try {

			sql = "INSERT INTO " + Entrate.NOME_TABELLA + " (" + Entrate.COL_IDENTRATE + ", "+ Entrate.COL_DESCRIZIONE + ", " + Entrate.COL_FISSEOVAR + ", " + Entrate.COL_INEURO + ", " + Entrate.COL_DATA + ", " + Entrate.COL_NOME
					+ ", " + Entrate.COL_IDUTENTE + ", " + Entrate.COL_DATAINS + ") VALUES (?,?,?,?,?,?,?,?)";
			
			return new ExecutePreparedStatement<IEntrate>() {

				@Override
				protected void doWithPreparedStatement(PreparedStatement ps, IEntrate obj) throws SQLException {
					ps.setInt(1, obj.getidEntrate());
					ps.setString(2, obj.getdescrizione());
					ps.setString(3, obj.getFisseoVar());
					ps.setDouble(4, obj.getinEuro());
					ps.setString(5, obj.getdata());
					ps.setString(6, obj.getnome());
					ps.setInt(7, obj.getUtenti().getidUtente());
					ps.setString(8, obj.getDataIns());
					
				}
			}.executeUpdate(sql, oggettoEntita);
			
			
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return ok;
	}

	private PreparedStatement createPreparedStatement(final Connection cn, String sql) throws SQLException {
		return cn.prepareStatement(sql);
	}

	@Override
	public boolean delete(final int id) {
		boolean ok = false;

		try {
			DeleteBase deleteBase = new DeleteBase();
			deleteBase.setTabella(Entrate.NOME_TABELLA);
			deleteBase.setClausole(Arrays.asList(new Clausola(null, Entrate.ID, "=", Integer.toString(id))));
			new Query().delete(deleteBase);
		
			ok = true;
			CacheEntrate.getSingleton().getCache().remove(Integer.toString(id));
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		return ok;
	}

	@Override
	public boolean update(final IEntrate oggettoEntita) {
		final String sql = getQueryUpdate(oggettoEntita);
		return base.executeUpdate(sql);
	}

	private String getQueryUpdate(final IEntrate entrata) {
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
	 * @throws SQLException 
	 */
	public List<Entrate> movimentiEntrateFiltrati(final String dataDa, final String dataA, final String nome, final Double euro, final String categoria) throws SQLException {
		final Utenti utente = (Utenti) Controllore.getUtenteLogin();
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

		ExecuteResultSet<List<Entrate>> execRsDieciEntrate = getExecRsEntrate(utente);
		return execRsDieciEntrate != null ? execRsDieciEntrate.execute(sql.toString()) :  new ArrayList<>();

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
		final Utenti utente = (Utenti) Controllore.getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getidUtente();
		}

		final String sql = getQueryDieciEntrate(numEntry, idUtente);

		try {
			ExecuteResultSet<List<Entrate>> execRsEntrate = getExecRsEntrate(utente);
			return execRsEntrate != null ? execRsEntrate.execute(sql) : new ArrayList<>();

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

	private ExecuteResultSet<List<Entrate>> getExecRsEntrate(final Utenti utente) {
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

			};
		} catch (Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Cancella l'ultima entita' "Entrate" inserita
	 */
	public boolean deleteLastEntrate() {
		boolean ok = false;


		try {
			final String sql = DELETE_FROM + Entrate.NOME_TABELLA + WHERE + Entrate.ID + "=(" + getQueryDeleteLastEntrate() + ")";
			ConnectionPool.getSingleton().executeUpdate(sql);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ControlloreBase.getLog().severe("Operazione non riuscita: " + e.getMessage());
		}
		return ok;
	}

	private String getQueryDeleteLastEntrate() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT " + Entrate.ID + " FROM ");
		stringBuilder.append(Entrate.NOME_TABELLA);
		stringBuilder.append(WHERE);
		stringBuilder.append(Entrate.COL_IDUTENTE);
		stringBuilder.append(" = ");
		stringBuilder.append(((Utenti) Controllore.getUtenteLogin()).getidUtente());
		stringBuilder.append(" ORDER BY ");
		stringBuilder.append(Entrate.COL_DATAINS);
		stringBuilder.append(" DESC LIMIT 1");
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
	public IUtenti getUtenti() {
		return entrate.getUtenti();
	}

	@Override
	public void setUtenti(final IUtenti utenti) {
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
	public List<IEntrate> selectWhere(List<Clausola> clausole,
			String appentoToQuery) {
		throw new UnsupportedOperationException();
	}

	private void riempiEntrataFromResultSet(final IEntrate entrata, ResultSet rs) throws SQLException {
		final IUtenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
		entrata.setidEntrate(rs.getInt(1));
		entrata.setdescrizione(rs.getString(2));
		entrata.setFisseoVar(rs.getString(3));
		entrata.setinEuro(rs.getDouble(4));
		entrata.setdata(rs.getString(5));
		entrata.setnome(rs.getString(6));
		entrata.setUtenti(utente);
		entrata.setDataIns(rs.getString(8));
	}

	private IEntrate riempiEntrataWithResultSet(final IUtenti utente, ResultSet rs) throws SQLException {
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

	@Override
	public String getIdEntita() {
		return Integer.toString(getidEntrate());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setidEntrate(Integer.parseInt(idEntita));
	}

	@Override
	public String getNome() {
		return getnome();
	}

}
