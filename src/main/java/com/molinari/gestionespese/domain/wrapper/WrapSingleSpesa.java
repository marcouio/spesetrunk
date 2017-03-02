package com.molinari.gestionespese.domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;

import command.javabeancommand.AbstractOggettoEntita;
import controller.ControlloreBase;
import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;
import grafica.componenti.alert.Alert;

public class WrapSingleSpesa extends Observable implements IDAO, ISingleSpesa {

	private static final String AND = " AND ";
	private static final String DELETE_FROM = "DELETE FROM ";
	private static final String WHERE = " WHERE ";
	private static final String SELECT_FROM = "SELECT * FROM ";
	private final SingleSpesa uscita;

	public WrapSingleSpesa() {
		uscita = new SingleSpesa();
	}

	@Override
	public Object selectById(final int id) {

		final String sql = SELECT_FROM + SingleSpesa.NOME_TABELLA + WHERE + SingleSpesa.ID + " = " + id;


		try {

			return ConnectionPool.getSingleton().new ExecuteResultSet<Object>() {

				@Override
				protected Object doWithResultSet(ResultSet rs) throws SQLException {
					final SingleSpesa uscitaLoc = new SingleSpesa();

					if (rs.next()) {
						final CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(5)));
						final Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
						uscitaLoc.setidSpesa(rs.getInt(1));
						uscitaLoc.setData(rs.getString(2));
						uscitaLoc.setinEuro(rs.getDouble(3));
						uscitaLoc.setdescrizione(rs.getString(4));
						uscitaLoc.setCatSpese(categoria);
						uscitaLoc.setnome(rs.getString(6));
						uscitaLoc.setUtenti(utente);
						uscitaLoc.setDataIns(rs.getString(8));
					}

					return uscitaLoc;
				}

			}.execute(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	public List<Object> selectAllForUtente() {
		final List<Object> uscite = new ArrayList<>();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		final Map<String, CatSpese> mappaCategorie = CacheCategorie.getSingleton().getAllCategorie();

		final String sql = SELECT_FROM + SingleSpesa.NOME_TABELLA + WHERE + SingleSpesa.IDUTENTE + " = " + utente.getidUtente();
		try {

			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Object>>() {

				@Override
				protected List<Object> doWithResultSet(ResultSet rs) throws SQLException {
					final List<Object> uscite = new ArrayList<>();

					while (rs != null && rs.next()) {
						final CatSpese categoria = mappaCategorie.get(Integer.toString(rs.getInt(5)));

						final SingleSpesa uscitaLoc = new SingleSpesa();
						uscitaLoc.setidSpesa(rs.getInt(1));
						uscitaLoc.setData(rs.getString(2));
						uscitaLoc.setinEuro(rs.getDouble(3));
						uscitaLoc.setdescrizione(rs.getString(4));
						uscitaLoc.setCatSpese(categoria);
						uscitaLoc.setnome(rs.getString(6));
						uscitaLoc.setUtenti(utente);
						uscitaLoc.setDataIns(rs.getString(8));
						uscite.add(uscitaLoc);
					}

					return uscite;
				}

			}.execute(sql);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return uscite;

	}

	public static void main(final String[] args) {
		final WrapSingleSpesa wrap = new WrapSingleSpesa();
		wrap.selectAll();
	}

	@Override
	public List<Object> selectAll() {
		final List<Object> uscite = new ArrayList<>();
		final Map<String, Utenti> mappaUtenti = CacheUtenti.getSingleton().getAllUtenti();
		final Map<String, CatSpese> mappaCategorie = CacheCategorie.getSingleton().getAllCategorie();

		final String sql = SELECT_FROM + SingleSpesa.NOME_TABELLA;
		try {

			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Object>>() {

				@Override
				protected List<Object> doWithResultSet(ResultSet rs) throws SQLException {
					final List<Object> uscite = new ArrayList<>();

					while (rs != null && rs.next()) {
						final Utenti utente = mappaUtenti.get(Integer.toString(rs.getInt(7)));
						final CatSpese categoria = mappaCategorie.get(Integer.toString(rs.getInt(5)));

						final SingleSpesa uscitaLoc = new SingleSpesa();
						uscitaLoc.setidSpesa(rs.getInt(1));
						uscitaLoc.setData(rs.getString(2));
						uscitaLoc.setinEuro(rs.getDouble(3));
						uscitaLoc.setdescrizione(rs.getString(4));
						uscitaLoc.setCatSpese(categoria);
						uscitaLoc.setnome(rs.getString(6));
						uscitaLoc.setUtenti(utente);
						uscitaLoc.setDataIns(rs.getString(8));
						uscite.add(uscitaLoc);
					}
					return uscite;
				}

			}.execute(sql);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return uscite;

	}

	@Override
	public boolean insert(final Object oggettoEntita) {

		final String sql = "INSERT INTO " + SingleSpesa.NOME_TABELLA + " (" + SingleSpesa.DATA + ", " + SingleSpesa.INEURO + ", " + SingleSpesa.DESCRIZIONE + ", " + SingleSpesa.IDCATEGORIE
				+ ", " + SingleSpesa.NOME + ", " + SingleSpesa.IDUTENTE + ", " + SingleSpesa.DATAINS + ") VALUES (?,?,?,?,?,?,?)";
		final SingleSpesa uscitaLoc = (SingleSpesa) oggettoEntita;
		final boolean inserted = ConnectionPool.getSingleton().new ExecutePreparedStatement<SingleSpesa>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, SingleSpesa uscitaLoc) throws SQLException {

				final String data = uscitaLoc.getData();
				ps.setString(1, data);
				ps.setDouble(2, uscitaLoc.getinEuro());
				ps.setString(3, uscitaLoc.getdescrizione());
				if (uscitaLoc.getCatSpese() != null) {
					ps.setInt(4, uscitaLoc.getCatSpese().getidCategoria());
				}
				ps.setString(5, uscitaLoc.getnome());
				if (uscitaLoc.getUtenti() != null) {
					ps.setInt(6, uscitaLoc.getUtenti().getidUtente());
				}
				final String dataIns = uscitaLoc.getDataIns();
				ps.setString(7, dataIns);

			}
		}.executeUpdate(sql, uscitaLoc);

		if(inserted){
			CacheUscite.getSingleton().getCache().put(Integer.toString(uscitaLoc.getidSpesa()), uscitaLoc);
		}
		return inserted;
	}

	@Override
	public boolean delete(final int id) {
		boolean ok = false;
		final String sql = DELETE_FROM + SingleSpesa.NOME_TABELLA + WHERE + SingleSpesa.ID + " = " + id;


		try {
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;
			CacheUscite.getSingleton().getCache().remove(id);
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		return ok;
	}

	@Override
	public boolean update(final Object oggettoEntita) {
		boolean ok = false;


		final SingleSpesa uscitaLoc = (SingleSpesa) oggettoEntita;
		final String sql = "UPDATE " + SingleSpesa.NOME_TABELLA + " SET " + SingleSpesa.DATA + " = '" + uscitaLoc.getData() + "', " + SingleSpesa.INEURO + " = " + uscitaLoc.getinEuro()
		+ ", " + SingleSpesa.DESCRIZIONE + " = '" + uscitaLoc.getdescrizione() + "', " + SingleSpesa.IDCATEGORIE + " = " + uscitaLoc.getCatSpese().getidCategoria() + ", "
		+ SingleSpesa.NOME + " = '" + uscitaLoc.getnome() + "', " + SingleSpesa.IDUTENTE + " = " + uscitaLoc.getUtenti().getidUtente() + ", " + SingleSpesa.DATAINS + " = '"
		+ uscitaLoc.getDataIns() + "' WHERE " + SingleSpesa.ID + " = " + uscitaLoc.getidSpesa();
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
	public boolean deleteAll() {
		boolean ok = false;
		final String sql = DELETE_FROM + SingleSpesa.NOME_TABELLA;


		try {

			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		return ok;
	}

	/**
	 * Permette di ottenere un vettore con un numero di entita' SingleSpesa
	 * inserito come parametro
	 *
	 * @param dieci
	 * @return List<SingleSpesa>
	 */
	public List<SingleSpesa> movimentiUsciteFiltrate(final String dataDa, final String dataA, final String nome, final Double euro, final String catSpese) {

		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getidUtente();
		}

		final StringBuilder sql = new StringBuilder();
		sql.append(SELECT_FROM + SingleSpesa.NOME_TABELLA + WHERE + SingleSpesa.IDUTENTE + " = " + idUtente);
		if (AltreUtil.checkData(dataDa) && AltreUtil.checkData(dataA) && dataDa != null && dataA != null) {
			sql.append(AND + SingleSpesa.DATA + " BETWEEN '" + dataDa + "'" + " AND '" + dataA + "'");
		} else if (AltreUtil.checkData(dataDa) && dataDa != null) {
			sql.append(AND + SingleSpesa.DATA + " = '" + dataDa + "'");
		}
		if (nome != null) {
			sql.append(AND + SingleSpesa.NOME + " = '" + nome + "'");
		}
		if (euro != null) {
			sql.append(AND + SingleSpesa.INEURO + " = " + euro);
		}
		if (catSpese != null && Integer.parseInt(catSpese) != 0) {
			sql.append(AND + SingleSpesa.IDCATEGORIE + " = " + Integer.parseInt(catSpese));
		}


		try {

			return ConnectionPool.getSingleton().new ExecuteResultSet<List<SingleSpesa>>() {

				@Override
				protected List<SingleSpesa> doWithResultSet(ResultSet rs) throws SQLException {
					final List<SingleSpesa> uscite = new ArrayList<>();

					final List<SingleSpesa> sSpesa = new ArrayList<>();
					while (rs != null && rs.next()) {
						final CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(rs.getString(5));
						final SingleSpesa ss = new SingleSpesa();
						ss.setidSpesa(rs.getInt(1));
						ss.setData(rs.getString(2));
						ss.setinEuro(rs.getDouble(3));
						ss.setdescrizione(rs.getString(4));
						ss.setnome(rs.getString(6));
						ss.setCatSpese(categoria);
						ss.setDataIns(rs.getString(8));
						ss.setUtenti(utente);
						sSpesa.add(ss);
					}

					return uscite;
				}

			}.execute(sql.toString());

		} catch (final SQLException e) {
			ControlloreBase.getLog().severe("Operazione non eseguita: " + e.getMessage());
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		return new ArrayList<>();

	}

	/**
	 * Permette di ottenere un vettore con un numero di entita' SingleSpesa
	 * inserito come parametro
	 *
	 * @param dieci
	 * @return List<SingleSpesa>
	 */
	public List<SingleSpesa> dieciUscite(final int dieci) {

		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getidUtente();
		}

		final String sql = "select * from " + SingleSpesa.NOME_TABELLA + " where " + SingleSpesa.DATA + " BETWEEN '" + Impostazioni.getAnno() + "/01/01'" + " AND '"
				+ Impostazioni.getAnno() + "/12/31'" + AND + SingleSpesa.IDUTENTE + " = " + idUtente + " ORDER BY " + SingleSpesa.ID + " desc limit 0," + dieci;

		CacheCategorie.getSingleton().getAllCategorie();

		try {

			return ConnectionPool.getSingleton().new ExecuteResultSet<List<SingleSpesa>>() {

				@Override
				protected List<SingleSpesa> doWithResultSet(ResultSet rs) throws SQLException {

					final List<SingleSpesa> sSpesa = new ArrayList<>();
					while (rs != null && rs.next()) {
						final SingleSpesa ss = new SingleSpesa();
						final CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(rs.getString(5));
						ss.setidSpesa(rs.getInt(1));
						ss.setData(rs.getString(2));
						ss.setinEuro(rs.getDouble(3));
						ss.setdescrizione(rs.getString(4));
						ss.setnome(rs.getString(6));
						ss.setCatSpese(categoria);
						ss.setDataIns(rs.getString(8));
						ss.setUtenti(utente);
						sSpesa.add(ss);
					}
					return sSpesa;
				}

			}.execute(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, "Impossibile caricare il record da single_spesa: "+e.getMessage());
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return new ArrayList<>();

	}

	/**
	 * Cancella l'ultima entita' "SingleSpesa" inserita
	 */
	public boolean deleteLastSpesa() {
		boolean ok = false;

		final String sql = SELECT_FROM + SingleSpesa.NOME_TABELLA + WHERE + Entrate.COL_IDUTENTE + " = " + ((Utenti) Controllore.getSingleton().getUtenteLogin()).getidUtente()
				+ " ORDER BY " + SingleSpesa.DATAINS + " DESC";

		try {

			ok = ConnectionPool.getSingleton().new ExecuteResultSet<Boolean>() {

				@Override
				protected Boolean doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {

						final String sql2 = DELETE_FROM + SingleSpesa.NOME_TABELLA + WHERE + SingleSpesa.ID + "=?";

						final Connection cn = ConnectionPool.getSingleton().getConnection();
						final PreparedStatement ps = cn.prepareStatement(sql2);
						ps.setInt(1, rs.getInt(1));
						ps.executeUpdate();

						ConnectionPool.getSingleton().chiudiOggettiDb(cn);
						return true;
					} else {
						Alert.segnalazioneErroreWarning("Non ci sono uscite per l'utente loggato");
						return false;
					}
				}

			}.execute(sql);


		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ControlloreBase.getLog().severe("Operazione non eseguita: " + e.getMessage());
		}
		return ok;
	}

	@Override
	public String getData() {
		return uscita.getData();
	}

	@Override
	public void setData(final String data) {
		uscita.setData(data);
	}

	@Override
	public String getdescrizione() {
		return uscita.getdescrizione();
	}

	@Override
	public void setdescrizione(final String descrizione) {
		uscita.setdescrizione(descrizione);
	}

	@Override
	public int getidSpesa() {
		return uscita.getidSpesa();
	}

	@Override
	public void setidSpesa(final int idSpesa) {
		uscita.setidSpesa(idSpesa);
		uscita.setIdEntita(Integer.toString(idSpesa));
	}

	@Override
	public double getinEuro() {
		return uscita.getinEuro();
	}

	@Override
	public void setinEuro(final double d) {
		uscita.setinEuro(d);
	}

	@Override
	public String getnome() {
		return uscita.getnome();
	}

	@Override
	public void setnome(final String nome) {
		uscita.setnome(nome);
	}

	@Override
	public CatSpese getCatSpese() {
		return uscita.getCatSpese();
	}

	@Override
	public void setCatSpese(final CatSpese catSpese) {
		uscita.setCatSpese(catSpese);
	}

	@Override
	public Utenti getUtenti() {
		return uscita.getUtenti();
	}

	@Override
	public void setUtenti(final Utenti utenti) {
		uscita.setUtenti(utenti);
	}

	@Override
	public void setDataIns(final String dataIns) {
		uscita.setDataIns(dataIns);
	}

	@Override
	public String getDataIns() {
		return uscita.getDataIns();
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return uscita;
	}

	@Override
	public Object selectWhere(List<Clausola> clausole,
			String appentoToQuery) {
		throw new UnsupportedOperationException();
	}

}
