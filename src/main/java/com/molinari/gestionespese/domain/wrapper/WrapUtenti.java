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
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.Clausola;
import com.molinari.utility.database.ConnectionPool;
import com.molinari.utility.database.ExecutePreparedStatement;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.database.dao.IDAO;

public class WrapUtenti extends Observable implements IDAO<IUtenti>, IUtenti {

	private static final long serialVersionUID = 1L;
	private static final String WHERE = " WHERE ";
	private static final String SELECT_FROM = "SELECT * FROM ";
	private final IUtenti utente;

	public WrapUtenti() {
		utente = new Utenti();
	}

	@Override
	public IUtenti selectById(int id) {

		final String sql = SELECT_FROM + Utenti.NOME_TABELLA + WHERE + Utenti.ID + "=" + id;
		try {

			return new ExecuteResultSet<IUtenti>() {

				@Override
				protected IUtenti doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						final IUtenti utenteLoc = new Utenti();
						utenteLoc.setidUtente(rs.getInt(1));
						utenteLoc.setNome(rs.getString(2));
						utenteLoc.setCognome(rs.getString(3));
						utenteLoc.setusername(rs.getString(4));
						utenteLoc.setpassword(rs.getString(5));
					}

					return utente;
				}

			}.execute(sql);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<IUtenti> selectAll() {

		final String sql = SELECT_FROM + Utenti.NOME_TABELLA;

		try {

			return new ExecuteResultSet<List<IUtenti>>() {

				@Override
				protected List<IUtenti> doWithResultSet(ResultSet rs) throws SQLException {
					final List<IUtenti> utenti = new ArrayList<>();

					while(rs != null && rs.next()) {
						final IUtenti utenteLoc = new Utenti();
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

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return new ArrayList<>();

	}

	public IUtenti utenteLogin(String username, String password) {
		final String sql = SELECT_FROM + Utenti.NOME_TABELLA + WHERE + Utenti.COL_USERNAME + " = '" + username + "' AND " + Utenti.COL_PASSWOR
				+ "='" + password + "'";

		final IUtenti utenteLoc = new Utenti();

		try {

			return new ExecuteResultSet<IUtenti>() {

				@Override
				protected IUtenti doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						utenteLoc.setidUtente(rs.getInt(1));
						utenteLoc.setusername(rs.getString(2));
						utenteLoc.setpassword(rs.getString(3));
					}

					return utenteLoc;
				}

			}.execute(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return utenteLoc;

	}

	@Override
	public boolean insert(IUtenti oggettoEntita) {

		final IUtenti utenteLoc = oggettoEntita;

		final String sql = "INSERT INTO " + Utenti.NOME_TABELLA + " (" + Utenti.COL_USERNAME + ", " + Utenti.COL_PASSWOR + ", " +  Utenti.COL_NOME
				+ ", " + Utenti.COL_COGNOME + ") VALUES (?,?,?,?)";

		return new ExecutePreparedStatement<IUtenti>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, IUtenti obj) throws SQLException {
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
		final String sql = "DELETE FROM " + Utenti.NOME_TABELLA + WHERE + Utenti.ID + " = " + id;

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
	public boolean update(IUtenti oggettoEntita) {
		boolean ok = false;

		final IUtenti utenteLoc = oggettoEntita;
		final String sql = "UPDATE " + Utenti.NOME_TABELLA + " SET " + Utenti.COL_USERNAME + " = " + utenteLoc.getusername() + ", "
				+ Utenti.COL_PASSWOR + " = " + utenteLoc.getpassword() + ", " + Utenti.COL_NOME + " = " + utenteLoc.getnome() + ", "
				+ Utenti.COL_COGNOME + " = " + utenteLoc.getCognome() + WHERE + Utenti.ID + " = " + utenteLoc.getidUtente();
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
		final String sql = "DELETE FROM " + Utenti.NOME_TABELLA;


		try {

			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}

		return ok;
	}

	public IUtenti selectByUserAndPass(String user, String pass) {
		final String sql = SELECT_FROM + Utenti.NOME_TABELLA + WHERE + Utenti.COL_USERNAME + " = '" + user + "' AND " + Utenti.COL_PASSWOR
				+ "='" + pass + "'";



		try {

			return new ExecuteResultSet<IUtenti>() {

				@Override
				protected IUtenti doWithResultSet(ResultSet rs) throws SQLException {
					IUtenti utenteLoc = null;
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

		} catch (final SQLException e) {
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
	public IUtenti getEntitaPadre() {
		return utente;
	}


	@Override
	public List<IUtenti> selectWhere(List<Clausola> clausole,
			String appentoToQuery){
		throw new UnsupportedOperationException();
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getidUtente());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setidUtente(Integer.parseInt(idEntita));
	}

	@Override
	public String getNome() {
		return getnome();
	}

}
