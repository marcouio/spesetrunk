package com.molinari.gestionespese.domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.Database;
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
import com.molinari.utility.database.Query;
import com.molinari.utility.database.UtilDb;
import com.molinari.utility.database.dao.IDAO;

public class WrapEntrate extends Observable implements IEntrate, IDAO<IEntrate> {

	private static final long serialVersionUID = 1L;
	private static final String AND = " AND ";
	private static final String DELETE_FROM = "DELETE FROM ";
	private static final String WHERE = " WHERE ";
	private static final String SELECT_FROM = "SELECT * FROM ";
	private final Entrate entrate;
	private final WrapBase<Entrate> base; 
	public WrapEntrate() {
		entrate = new Entrate();
		base = new WrapBase<Entrate>(entrate);
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
					if (obj.getUtenti() != null) {
						ps.setInt(7, obj.getUtenti().getidUtente());
					} else {
						ps.setNull(7, java.sql.Types.INTEGER);
					}
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
		final String sql = "UPDATE " + Entrate.NOME_TABELLA + " SET " + Entrate.COL_DESCRIZIONE + " = ?, "
				+ Entrate.COL_FISSEOVAR + " = ?, " + Entrate.COL_INEURO + " = ?, " + Entrate.COL_DATA + " = ?, "
				+ Entrate.COL_NOME + " = ?, " + Entrate.COL_IDUTENTE + " = ?, " + Entrate.COL_DATAINS + " = ?"
				+ WHERE + Entrate.ID + " = ?";

		return new ExecutePreparedStatement<IEntrate>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, IEntrate obj) throws SQLException {
				ps.setString(1, obj.getdescrizione());
				ps.setString(2, obj.getFisseoVar());
				ps.setDouble(3, obj.getinEuro());
				ps.setString(4, obj.getdata());
				ps.setString(5, obj.getnome());
				if (obj.getUtenti() != null) {
					ps.setInt(6, obj.getUtenti().getidUtente());
				} else {
					ps.setNull(6, java.sql.Types.INTEGER);
				}
				ps.setString(7, obj.getDataIns());
				ps.setInt(8, obj.getidEntrate());
			}
		}.executeUpdate(sql, oggettoEntita);
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
		final List<Object> parametri = new ArrayList<>();
		sql.append(SELECT_FROM + Entrate.NOME_TABELLA + WHERE + Entrate.COL_IDUTENTE + " = ?");
		parametri.add(idUtente);
		if (AltreUtil.checkDate(dataDa) && AltreUtil.checkDate(dataA)) {
			sql.append(AND + Entrate.COL_DATA + " BETWEEN ? AND ?");
			parametri.add(dataDa);
			parametri.add(dataA);
		} else if (AltreUtil.checkDate(dataDa)) {
			sql.append(AND + Entrate.COL_DATA + " = ?");
			parametri.add(dataDa);
		}
		if (nome != null) {
			sql.append(AND + Entrate.COL_NOME + " = ?");
			parametri.add(nome);
		}
		if (euro != null) {
			sql.append(AND + Entrate.COL_INEURO + " = ?");
			parametri.add(euro);
		}
		if (categoria != null) {
			sql.append(AND + Entrate.COL_FISSEOVAR + " = ?");
			parametri.add(categoria);
		}

		ExecuteResultSet<List<Entrate>> execRsDieciEntrate = getExecRsEntrate(utente);
		return execRsDieciEntrate != null ? execRsDieciEntrate.executePrepared(sql.toString(), ps -> {
			for (int i = 0; i < parametri.size(); i++) {
				ps.setObject(i + 1, parametri.get(i));
			}
		}) : new ArrayList<>();

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

		List<IEntrate> yearIncomes = CacheEntrate.getSingleton().getAllEntrateForUtenteEAnno();
		yearIncomes.sort((e1, e2) -> sortEntrata(e1, e2));
		List collect = yearIncomes.stream().limit(10).collect(Collectors.toList());
		return collect;

	}

	protected int sortEntrata(IEntrate e1, IEntrate e2) {
		int sortStringData = UtilDb.sortStringData(Database.YYYY_MM_DD, e1.getdata(), e2.getdata());
		if(sortStringData != 0) {
			return sortStringData;
		}
		if(e1.getidEntrate() > e2.getidEntrate()) {
			return -1;
		}
		return 1;
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
	public List<IEntrate> selectWhere(List<Clausola> clausole, String appentoToQuery) {
		List selectWhere = base.selectWhere(clausole, appentoToQuery);
		return selectWhere;
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
