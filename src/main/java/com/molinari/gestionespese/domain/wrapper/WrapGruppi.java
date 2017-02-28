package com.molinari.gestionespese.domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;

import com.molinari.gestionespese.business.ConnectionPoolGGS;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.Utenti;

import java.util.List;

import command.javabeancommand.AbstractOggettoEntita;
import db.Clausola;
import db.ConnectionPool;
import db.ConnectionPool.ExecuteResultSet;
import db.dao.IDAO;

public class WrapGruppi extends Observable implements IDAO, IGruppi {

	private final Gruppi gruppo;

	public WrapGruppi() {
		gruppo = new Gruppi();
	}

	@Override
	public Object selectById(final int id) {
		
		final String sql = "SELECT * FROM " + Gruppi.NOME_TABELLA + " WHERE " + Gruppi.ID + " = " + id;

		final Gruppi gruppo = new Gruppi();

		try {

			return ConnectionPool.getSingleton().new ExecuteResultSet<Object>() {

				@Override
				protected Object doWithResultSet(ResultSet rs) throws SQLException {
					
					if (rs.next()) {
						gruppo.setidGruppo(rs.getInt(1));
						gruppo.setnome(rs.getString(2));
						gruppo.setdescrizione(rs.getString(3));
					}
					
					return gruppo;
				}
				
			}.execute(sql);

		} catch (final SQLException e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		} 
		return gruppo;

	}

	@Override
	public List<Object> selectAll() {
		
		final String sql = "SELECT * FROM " + Gruppi.NOME_TABELLA + " ORDER BY " + Gruppi.ID + " asc";
		try {
			
			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Object>>() {

				@Override
				protected List<Object> doWithResultSet(ResultSet rs) throws SQLException {
					final List<Object> gruppi = new ArrayList<>();
					
					while (rs != null && rs.next()) {
						
						final Gruppi gruppo = new Gruppi();
						gruppo.setidGruppo(rs.getInt(1));
						gruppo.setnome(rs.getString(2));
						gruppo.setdescrizione(rs.getString(3));
						gruppi.add(gruppo);
					}
					return gruppi;
				}
				
			}.execute(sql);
			
		} catch (final Exception e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		} 
		return null;

	}

	@Override
	public boolean insert(final Object oggettoEntita) {
		boolean ok = false;
		Connection cn = ConnectionPool.getSingleton().getConnection();
		String sql = "";
		try {
			final Gruppi gruppo = (Gruppi) oggettoEntita;

			sql = "INSERT INTO " + Gruppi.NOME_TABELLA + " (" + Gruppi.NOME + ", " + Gruppi.DESCRIZIONE
					+ ") VALUES(?,?)";
			final PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, gruppo.getnome());
			ps.setString(2, gruppo.getdescrizione());

			ps.executeUpdate();
			ok = true;
		} catch (final Exception e) {
			ok = false;
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		} 
		
		ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		return ok;
	}

	@Override
	public boolean delete(final int id) {
		boolean ok = false;
		final String sql = "DELETE FROM " + Gruppi.NOME_TABELLA + " WHERE " + Gruppi.ID + " = " + id;
		

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
		

		final Gruppi gruppo = (Gruppi) oggettoEntita;
		final String sql = "UPDATE " + Gruppi.NOME_TABELLA + " SET " + Gruppi.NOME + " = '" + gruppo.getnome() + "', "
				+ Gruppi.DESCRIZIONE + " = '" + gruppo.getdescrizione() + "' WHERE " + Gruppi.ID + " = "
				+ gruppo.getidGruppo();
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
		final String sql = "DELETE FROM " + Gruppi.NOME_TABELLA;
		

		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		
		return ok;
	}

	public Gruppi selectByNome(final String nome) {

		
		final String sql = "SELECT * FROM " + Gruppi.NOME_TABELLA + " WHERE " + Gruppi.NOME + " = \"" + nome + "\"";

		Gruppi gruppo = new Gruppi();

		try {

			return ConnectionPool.getSingleton().new ExecuteResultSet<Gruppi>() {

				@Override
				protected Gruppi doWithResultSet(ResultSet rs) throws SQLException {
					
					if (rs.next()) {
						gruppo.setidGruppo(rs.getInt(1));
						gruppo.setnome(rs.getString(2));
						gruppo.setdescrizione(rs.getString(3));
					}
					
					return gruppo;
				}
				
			}.execute(sql);
			
		} catch (final SQLException e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		} 
		return gruppo;
	}

	@Override
	public String getdescrizione() {
		return gruppo.getdescrizione();
	}

	@Override
	public void setdescrizione(final String descrizione) {
		gruppo.setdescrizione(descrizione);
	}

	@Override
	public int getidGruppo() {
		return gruppo.getidGruppo();
	}

	@Override
	public void setidGruppo(final int idGruppo) {
		gruppo.setidGruppo(idGruppo);
	}

	@Override
	public String getnome() {
		return gruppo.getnome();
	}

	@Override
	public void setnome(final String nome) {
		gruppo.setnome(nome);
	}

	@Override
	public Set<CatSpese> getCatSpeses() {
		return gruppo.getCatSpeses();
	}

	@Override
	public void setCatSpeses(final Set<CatSpese> catSpeses) {
		gruppo.setCatSpeses(catSpeses);
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
	public AbstractOggettoEntita getEntitaPadre()  {
		return gruppo;
	}

	@Override
	public Object selectWhere(List<Clausola> clausole,
			String appentoToQuery)  {
		// TODO Auto-generated method stub
		return null;
	}

}