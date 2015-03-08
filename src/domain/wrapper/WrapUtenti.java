package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;
import java.util.Vector;

import business.DBUtil;
import command.javabeancommand.AbstractOggettoEntita;
import db.Clausola;
import db.dao.IDAO;
import domain.Entrate;
import domain.IUtenti;
import domain.SingleSpesa;
import domain.Utenti;

public class WrapUtenti extends Observable implements IDAO, IUtenti {

	private Utenti utente;

	public WrapUtenti() {
		utente = new Utenti();
	}
	
	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + Utenti.NOME_TABELLA + " WHERE " + Utenti.ID + "=" + id;
		Utenti utente = null;
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				utente = new Utenti();
				utente.setidUtente(rs.getInt(1));
				utente.setNome(rs.getString(2));
				utente.setCognome(rs.getString(3));
				utente.setusername(rs.getString(4));
				utente.setpassword(rs.getString(5));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return utente;
	}

	@Override
	public Vector<Object> selectAll() {
		Vector<Object> utenti = new Vector<Object>();
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + Utenti.NOME_TABELLA;

		try {

			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Utenti utente = new Utenti();
				utente.setidUtente(rs.getInt(1));
				utente.setNome(rs.getString(2));
				utente.setCognome(rs.getString(3));
				utente.setusername(rs.getString(4));
				utente.setpassword(rs.getString(5));
				utenti.add(utente);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return utenti;

	}

	public Utenti utenteLogin(String username, String password) {
		String sql = "SELECT * FROM " + Utenti.NOME_TABELLA + " WHERE " + Utenti.USERNAME + " = '" + username + "' AND " + Utenti.PASSWORD
				+ "='" + password + "'";
		Connection cn = DBUtil.getConnection();
		Utenti utente = new Utenti();

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				utente.setidUtente(rs.getInt(1));
				utente.setusername(rs.getString(2));
				utente.setpassword(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return utente;

	}

	@Override
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		String sql = "";
		try {
			Utenti utente = (Utenti) oggettoEntita;
			sql = "INSERT INTO " + Utenti.NOME_TABELLA + " (" + Utenti.USERNAME + ", " + Utenti.PASSWORD + ", " +  Utenti.NOME
					+ ", " + Utenti.COGNOME + ") VALUES (?,?,?,?)";
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, utente.getusername());
			ps.setString(2, utente.getpassword());
			ps.setString(3, utente.getnome());
			ps.setString(4, utente.getCognome());

			ps.executeUpdate();
			ok = true;
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeConnection();
		}
		return ok;

	}

	@Override
	public boolean delete(int id) {
		boolean ok = false;
		String sql = "DELETE FROM " + Utenti.NOME_TABELLA + " WHERE " + Utenti.ID + " = " + id;
		Connection cn = DBUtil.getConnection();

		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok = true;

		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public boolean update(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();

		Utenti utente = (Utenti) oggettoEntita;
		String sql = "UPDATE " + Utenti.NOME_TABELLA + " SET " + Utenti.USERNAME + " = " + utente.getusername() + ", "
				+ Utenti.PASSWORD + " = " + utente.getpassword() + ", " + Utenti.NOME + " = " + utente.getnome() + ", "
				+ Utenti.COGNOME + " = " + utente.getCognome() + " WHERE " + Utenti.ID + " = " + utente.getidUtente();
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok = true;

		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public boolean deleteAll() {
		boolean ok = false;
		String sql = "DELETE FROM " + Utenti.NOME_TABELLA;
		Connection cn = DBUtil.getConnection();

		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok = true;

		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	public Utenti selectByUserAndPass(String user, String pass) {
		String sql = "SELECT * FROM " + Utenti.NOME_TABELLA + " WHERE " + Utenti.USERNAME + " = '" + user + "' AND " + Utenti.PASSWORD
				+ "='" + pass + "'";
		Connection cn = DBUtil.getConnection();
		Utenti utente =null;

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				utente = new Utenti();
				utente.setidUtente(rs.getInt(1));
				utente.setNome(rs.getString(2));
				utente.setCognome(rs.getString(3));
				utente.setusername(rs.getString(4));
				utente.setpassword(rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
	public void notifyObservers() {
		super.notifyObservers();
	}
	
	@Override
	protected synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public Object selectWhere(ArrayList<Clausola> clausole,
			String appentoToQuery) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
