package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Set;
import java.util.Vector;

import business.ConnectionPoolGGS;
import business.DBUtil;
import business.cache.CacheCategorie;
import business.cache.CacheUtenti;
import command.javabeancommand.AbstractOggettoEntita;
import db.Clausola;
import db.ConnectionPool;
import db.ConnectionPool.ExecuteResultSet;
import db.dao.IDAO;
import domain.CatSpese;
import domain.Entrate;
import domain.Gruppi;
import domain.IGruppi;
import domain.Utenti;

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

			return new ConnectionPoolGGS().new ExecuteResultSet<Object>() {

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
			e.printStackTrace();
		} 
		return gruppo;

	}

	@Override
	public Vector<Object> selectAll() {
		
		final String sql = "SELECT * FROM " + Gruppi.NOME_TABELLA + " ORDER BY " + Gruppi.ID + " asc";
		try {
			
			return new ConnectionPoolGGS().new ExecuteResultSet<Vector<Object>>() {

				@Override
				protected Vector<Object> doWithResultSet(ResultSet rs) throws SQLException {
					final Vector<Object> gruppi = new Vector<Object>();
					
					while (rs.next()) {
						
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
			ok = false;
		}
		
		return ok;
	}

	public Gruppi selectByNome(final String nome) {

		
		final String sql = "SELECT * FROM " + Gruppi.NOME_TABELLA + " WHERE " + Gruppi.NOME + " = \"" + nome + "\"";

		Gruppi gruppo = new Gruppi();

		try {

			return new ConnectionPoolGGS().new ExecuteResultSet<Gruppi>() {

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
			e.printStackTrace();
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
	public AbstractOggettoEntita getEntitaPadre() throws Exception {
		return gruppo;
	}

	@Override
	public Object selectWhere(ArrayList<Clausola> clausole,
			String appentoToQuery) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}