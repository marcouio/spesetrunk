package com.molinari.gestionespese.domain.wrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;

import com.molinari.gestionespese.business.PasswordHasher;
import com.molinari.gestionespese.business.cache.CacheUtenti;
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
						return utenteLoc;
					}

					return null;
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
		return selectByUserAndPass(username, password);
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
				ps.setString(2, PasswordHasher.encode(obj.getpassword()));
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

			CacheUtenti.getSingleton().getCache().remove(Integer.toString(id));
			
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}

		return ok;
	}

	@Override
	public boolean update(IUtenti oggettoEntita) {
		final String sql = "UPDATE " + Utenti.NOME_TABELLA + " SET " + Utenti.COL_USERNAME + " = ?, "
				+ Utenti.COL_PASSWOR + " = ?, " + Utenti.COL_NOME + " = ?, " + Utenti.COL_COGNOME + " = ?"
				+ WHERE + Utenti.ID + " = ?";

		return new ExecutePreparedStatement<IUtenti>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, IUtenti obj) throws SQLException {
				ps.setString(1, obj.getusername());
				ps.setString(2, PasswordHasher.encode(obj.getpassword()));
				ps.setString(3, obj.getnome());
				ps.setString(4, obj.getCognome());
				ps.setInt(5, obj.getidUtente());
			}
		}.executeUpdate(sql, oggettoEntita);
	}

	@Override
	public boolean deleteAll() {
		boolean ok = false;
		final String sql = "DELETE FROM " + Utenti.NOME_TABELLA;


		try {

			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;
			CacheUtenti.getSingleton().getCache().clear();
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}

		return ok;
	}

	public IUtenti selectByUserAndPass(String user, String pass) {
		final IUtenti utenteLoc = selectByUsername(user);
		if (utenteLoc != null && PasswordHasher.matches(pass, utenteLoc.getpassword())) {
			return utenteLoc;
		}
		return null;
	}

	private IUtenti selectByUsername(final String user) {
		final String sql = SELECT_FROM + Utenti.NOME_TABELLA + WHERE + Utenti.COL_USERNAME + " = ?";

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
						return utenteLoc;
					}

					return null;
				}

			}.executePrepared(sql, ps -> ps.setString(1, user));

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;

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
