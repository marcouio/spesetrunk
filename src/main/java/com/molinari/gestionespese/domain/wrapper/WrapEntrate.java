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
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.IEntrate;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;

import command.javabeancommand.AbstractOggettoEntita;
import controller.ControlloreBase;
import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;

public class WrapEntrate extends Observable implements IEntrate, IDAO {

	private final Entrate entrate;

	public WrapEntrate() {
		entrate = new Entrate();
	}

	@Override
	public Object selectById(final int id) {
		
		final String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.ID + " = " + id;

		final Entrate entrata = new Entrate();

		try {
			
			ConnectionPool.getSingleton().new ExecuteResultSet<Object>() {

				@Override
				protected Object doWithResultSet(ResultSet rs) throws SQLException {
					
					return extracted(entrata, rs);
				}

				private Object extracted(final Entrate entrata, ResultSet rs)
						throws SQLException {
					if (rs.next()) {

						if (rs.next()) {
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

					}
					return entrata;
				}
				
			}.execute(sql);


		} catch (final SQLException e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		} finally {
			
		}
		return entrata;

	}

	public List<Object> selectAllForUtente() {
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		
		final String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.IDUTENTE + " = " + utente.getidUtente();
		try {
			
			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Object>>() {

				@Override
				protected List<Object> doWithResultSet(ResultSet rs) throws SQLException {
					
					final List<Object> entrate = new ArrayList<>();
					while (rs != null && rs.next()) {
						
						final Entrate entrata = new Entrate();
						entrata.setidEntrate(rs.getInt(1));
						entrata.setdescrizione(rs.getString(2));
						entrata.setFisseoVar(rs.getString(3));
						entrata.setinEuro(rs.getDouble(4));
						entrata.setdata(rs.getString(5));
						entrata.setnome(rs.getString(6));
						entrata.setUtenti(utente);
						entrata.setDataIns(rs.getString(8));
						entrate.add(entrata);
					}
					return entrate;
				}
				
			}.execute(sql);

		} catch (final Exception e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		
		return null;
	}

	@Override
	public List<Object> selectAll() {
		

		final String sql = "SELECT * FROM " + Entrate.NOME_TABELLA;
		try {
			CacheUtenti.getSingleton().getAllUtenti();
			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Object>>() {

				@Override
				protected List<Object> doWithResultSet(ResultSet rs) throws SQLException {
					return extracted(rs);
				}

				private List<Object> extracted(ResultSet rs)
						throws SQLException {
					final List<Object> entrate = new ArrayList<>();
					
					while (rs != null && rs.next()) {
						final Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
						final Entrate entrata = new Entrate();
						entrata.setidEntrate(rs.getInt(1));
						entrata.setdescrizione(rs.getString(2));
						entrata.setFisseoVar(rs.getString(3));
						entrata.setinEuro(rs.getDouble(4));
						entrata.setdata(rs.getString(5));
						entrata.setnome(rs.getString(6));
						entrata.setUtenti(utente);
						entrata.setDataIns(rs.getString(8));
						entrate.add(entrata);
					}
					return entrate;
				}
				
			}.execute(sql);
			

		} catch (final Exception e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		} 
		
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return null;
	}

	@Override
	public boolean insert(final Object oggettoEntita) {
		boolean ok = false;
		Connection cn = ConnectionPool.getSingleton().getConnection();
		String sql = "";
		try {
			final Entrate entrata = (Entrate) oggettoEntita;

			sql = "INSERT INTO " + Entrate.NOME_TABELLA + " (" + Entrate.DESCRIZIONE + ", " + Entrate.FISSEOVAR + ", " + Entrate.INEURO + ", " + Entrate.DATA + ", " + Entrate.NOME
					+ ", " + Entrate.IDUTENTE + ", " + Entrate.DATAINS + ") VALUES (?,?,?,?,?,?,?)";
			final PreparedStatement ps = cn.prepareStatement(sql);
			// descrizione
			ps.setString(1, entrata.getdescrizione());
			// tipo
			ps.setString(2, entrata.getFisseoVar());
			// euro
			ps.setDouble(3, entrata.getinEuro());
			// data
			ps.setString(4, entrata.getdata());
			// nome
			ps.setString(5, entrata.getnome());
			// idutente
			ps.setInt(6, entrata.getUtenti().getidUtente());
			// datains
			ps.setString(7, entrata.getDataIns());

			ps.executeUpdate();
			ok = true;
		} catch (final Exception e) {
			ok = false;
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		} finally {
			try {
				cn.close();
			} catch (final SQLException e) {
				Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
			}
			DBUtil.closeConnection();
		}
		return ok;
	}

	@Override
	public boolean delete(final int id) {
		boolean ok = false;
		final String sql = "DELETE FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.ID + " = " + id;
		
		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		
		return ok;
	}

	@Override
	public boolean update(final Object oggettoEntita) {
		boolean ok = false;
		

		final Entrate entrata = (Entrate) oggettoEntita;
		final String sql = "UPDATE " + Entrate.NOME_TABELLA + " SET " + Entrate.DESCRIZIONE + " = '" + entrata.getdescrizione() + "', " + Entrate.FISSEOVAR + " = '"
				+ entrata.getFisseoVar() + "', " + Entrate.INEURO + " = " + entrata.getinEuro() + ", " + Entrate.DATA + " = '" + entrata.getdata() + "', " + Entrate.NOME + " = '"
				+ entrata.getnome() + "', " + Entrate.IDUTENTE + " = " + entrata.getUtenti().getidUtente() + ", " + Entrate.DATAINS + " = '" + entrata.getDataIns() + "' WHERE "
				+ Entrate.ID + " = " + entrata.getidEntrate();
		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		return ok;
	}

	@Override
	public boolean deleteAll() {
		boolean ok = false;
		final String sql = "DELETE FROM " + Entrate.NOME_TABELLA;
		

		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		return ok;
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

		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.IDUTENTE + " = " + idUtente);
		if (AltreUtil.checkData(dataDa) && AltreUtil.checkData(dataA)) {
			sql.append(" AND " + Entrate.DATA + " BETWEEN '" + dataDa + "'" + " AND '" + dataA + "'");
		} else if (AltreUtil.checkData(dataDa)) {
			sql.append(" AND " + Entrate.DATA + " = '" + dataDa + "'");
		}
		if (nome != null) {
			sql.append(" AND " + Entrate.NOME + " = '" + nome + "'");
		}
		if (euro != null) {
			sql.append(" AND " + Entrate.INEURO + " = " + euro);
		}
		if (categoria != null) {
			sql.append(" AND " + Entrate.FISSEOVAR + " = '" + categoria + "'");
		}
		
		try {
			
			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Entrate>>() {

				@Override
				protected List<Entrate> doWithResultSet(ResultSet rs) throws SQLException {

					List<Entrate> entrate = new ArrayList<>();
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
						entrate.add(e);
					}
					return entrate;
				}
				
			}.execute(sql.toString());
			
		} catch (final SQLException e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;

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

		final String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " where " + Entrate.DATA + " BETWEEN '" + Impostazioni.getAnno() + "/01/01" + "'" + " AND '"
				+ Impostazioni.getAnno() + "/12/31" + "'" + " AND " + Entrate.IDUTENTE + " = " + idUtente + " ORDER BY " + Entrate.ID + " desc limit 0," + numEntry;
		
		try {
			
			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Entrate>>() {

				@Override
				protected List<Entrate> doWithResultSet(ResultSet rs) throws SQLException {

					List<Entrate> entrate = new ArrayList<Entrate>();
					
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
						entrate.add(e);
					}
					
					return entrate;
				}
				
			}.execute(sql);
			
			
		} catch (final SQLException e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;

	}

	/**
	 * Cancella l'ultima entita' "Entrate" inserita
	 */
	public boolean DeleteLastEntrate() {
		boolean ok = false;
		
		final String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.IDUTENTE + " = " + ((Utenti) Controllore.getSingleton().getUtenteLogin()).getidUtente()
				+ " ORDER BY " + Entrate.DATAINS + " DESC";

		Connection cn = ConnectionPool.getSingleton().getConnection();
		
		try {
			
			ok = ConnectionPool.getSingleton().new ExecuteResultSet<Boolean>() {

				@Override
				protected Boolean doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						final String sql2 = "DELETE FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.ID + "=?";
						final PreparedStatement ps = cn.prepareStatement(sql2);
						ps.setInt(1, rs.getInt(1));
						ps.executeUpdate();
						
					}
					return true;
				}
				
			}.execute(sql);
			
		} catch (final Exception e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
			ControlloreBase.getLog().severe("Operazione non riuscita: " + e.getMessage());
		}
		return ok;
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
	public void setFisseoVar(final String FisseoVar) {
		entrate.setFisseoVar(FisseoVar);
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
	public void notifyObservers() {
		super.notifyObservers();
	}

	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return entrate;
	}

	@Override
	public Object selectWhere(List<Clausola> clausole,
			String appentoToQuery) {
		// TODO Auto-generated method stub
		return null;
	}

}
