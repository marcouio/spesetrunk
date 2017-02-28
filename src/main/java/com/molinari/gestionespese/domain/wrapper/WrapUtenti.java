package com.molinari.gestionespese.domain.wrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;

import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.Utenti;

import command.javabeancommand.AbstractOggettoEntita;
import controller.ControlloreBase;
import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;

public class WrapUtenti extends Observable implements IDAO, IUtenti {

	private static final String WHERE = " WHERE ";
	private static final String SELECT_FROM = "SELECT * FROM ";
	private Utenti utente;

	public WrapUtenti() {
		utente = new Utenti();
	}
	
	@Override
	public Object selectById(int id) {
		
		String sql = SELECT_FROM + Utenti.NOME_TABELLA + WHERE + Utenti.ID + "=" + id;
		try {
			
			return ConnectionPool.getSingleton().new ExecuteResultSet<Object>() {

				@Override
				protected Object doWithResultSet(ResultSet rs) throws SQLException {
					
					if (rs.next()) {
						Utenti utenteLoc = new Utenti();
						utenteLoc.setidUtente(rs.getInt(1));
						utenteLoc.setNome(rs.getString(2));
						utenteLoc.setCognome(rs.getString(3));
						utenteLoc.setusername(rs.getString(4));
						utenteLoc.setpassword(rs.getString(5));
					}
					
					return utente;
				}
				
			}.execute(sql);
			
		} catch (Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		} 
		return null;
	}

	@Override
	public List<Object> selectAll() {
		
		String sql = SELECT_FROM + Utenti.NOME_TABELLA;

		try {

			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Object>>() {

				@Override
				protected List<Object> doWithResultSet(ResultSet rs) throws SQLException {
					final List<Object> utenti = new ArrayList<>();
					
					while(rs != null && rs.next()) {
						Utenti utenteLoc = new Utenti();
						utenteLoc.setidUtente(rs.getInt(1));
						utenteLoc.setNome(rs.getString(2));
						utenteLoc.setCognome(rs.getString(3));
						utenteLoc.setusername(rs.getString(4));
						utenteLoc.setpassword(rs.getString(5));
						utenti.add(utenteLoc);
					}
										
					return utenti;
				}
				
			}.execute(sql);
			
		} catch (Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		} 
		return new ArrayList<>();

	}

	public Utenti utenteLogin(String username, String password) {
		String sql = SELECT_FROM + Utenti.NOME_TABELLA + WHERE + Utenti.USERNAME + " = '" + username + "' AND " + Utenti.PASSWORD
				+ "='" + password + "'";
		
		Utenti utenteLoc = new Utenti();

		try {
			
			return ConnectionPool.getSingleton().new ExecuteResultSet<Utenti>() {

				@Override
				protected Utenti doWithResultSet(ResultSet rs) throws SQLException {
					
					if (rs.next()) {
						utenteLoc.setidUtente(rs.getInt(1));
						utenteLoc.setusername(rs.getString(2));
						utenteLoc.setpassword(rs.getString(3));
					}
					
					return utenteLoc;
				}
				
			}.execute(sql);
			
		} catch (SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		} 
		return utenteLoc;

	}

	@Override
	public boolean insert(Object oggettoEntita) {
		
		Utenti utenteLoc = (Utenti) oggettoEntita;
		
		String sql = "INSERT INTO " + Utenti.NOME_TABELLA + " (" + Utenti.USERNAME + ", " + Utenti.PASSWORD + ", " +  Utenti.NOME
				+ ", " + Utenti.COGNOME + ") VALUES (?,?,?,?)";
		
		return ConnectionPool.getSingleton().new ExecutePreparedStatement<Utenti>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, Utenti obj) throws SQLException {
				ps.setString(1, obj.getusername());
				ps.setString(2, obj.getpassword());
				ps.setString(3, obj.getnome());
				ps.setString(4, obj.getCognome());
			}
		}.executeUpdate(sql, utenteLoc);
		
	}

	@Override
	public boolean delete(int id) {
		boolean ok = false;
		String sql = "DELETE FROM " + Utenti.NOME_TABELLA + WHERE + Utenti.ID + " = " + id;

		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		
		return ok;
	}

	@Override
	public boolean update(Object oggettoEntita) {
		boolean ok = false;

		Utenti utenteLoc = (Utenti) oggettoEntita;
		String sql = "UPDATE " + Utenti.NOME_TABELLA + " SET " + Utenti.USERNAME + " = " + utenteLoc.getusername() + ", "
				+ Utenti.PASSWORD + " = " + utenteLoc.getpassword() + ", " + Utenti.NOME + " = " + utenteLoc.getnome() + ", "
				+ Utenti.COGNOME + " = " + utenteLoc.getCognome() + WHERE + Utenti.ID + " = " + utenteLoc.getidUtente();
		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		
		return ok;
	}

	@Override
	public boolean deleteAll() {
		boolean ok = false;
		String sql = "DELETE FROM " + Utenti.NOME_TABELLA;
		

		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		
		return ok;
	}

	public Utenti selectByUserAndPass(String user, String pass) {
		String sql = SELECT_FROM + Utenti.NOME_TABELLA + WHERE + Utenti.USERNAME + " = '" + user + "' AND " + Utenti.PASSWORD
				+ "='" + pass + "'";
		
		

		try {
			
			return ConnectionPool.getSingleton().new ExecuteResultSet<Utenti>() {

				@Override
				protected Utenti doWithResultSet(ResultSet rs) throws SQLException {
					Utenti utenteLoc = null;
					if (rs.next()) {
						utenteLoc = new Utenti();
						utenteLoc.setidUtente(rs.getInt(1));
						utenteLoc.setNome(rs.getString(2));
						utenteLoc.setCognome(rs.getString(3));
						utenteLoc.setusername(rs.getString(4));
						utenteLoc.setpassword(rs.getString(5));
					}
					
					return utenteLoc;
				}
				
			}.execute(sql);
			
		} catch (SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		} 
		return utente;

	}

	@Override
	public int getidUtente() {
		return utente.getidUtente();
	}

	@Override
	public void setidUtente(int idUtente) {
		utente.setidUtente(idUtente);
	}

	@Override
	public String getpassword() {
		return utente.getpassword();
	}

	@Override
	public void setpassword(String password) {
		utente.setpassword(password);
	}

	@Override
	public String getusername() {
		return utente.getusername();
	}

	@Override
	public void setusername(String username) {
		utente.setusername(username);
	}

	@Override
	public Set<Entrate> getEntrates() {
		return utente.getEntrates();
	}

	@Override
	public void setEntrates(Set<Entrate> entrates) {
		utente.setEntrates(entrates);
	}

	@Override
	public Set<SingleSpesa> getSingleSpesas() {
		return utente.getSingleSpesas();
	}

	@Override
	public void setSingleSpesas(Set<SingleSpesa> singleSpesas) {
		utente.setSingleSpesas(singleSpesas);
	}

	@Override
	public void setNome(String nome) {
		utente.setNome(nome);
	}

	@Override
	public String getnome() {
		return utente.getnome();
	}

	@Override
	public void setCognome(String cognome) {
		utente.setCognome(cognome);
	}

	@Override
	public String getCognome() {
		return utente.getCognome();
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return utente;
	}
	

	@Override
	public Object selectWhere(List<Clausola> clausole,
			String appentoToQuery){
		throw new UnsupportedOperationException();
	}

}
