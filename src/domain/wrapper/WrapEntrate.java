package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import view.impostazioni.Impostazioni;
import business.Controllore;
import business.DBUtil;
import business.cache.CacheUtenti;
import domain.AbstractOggettoEntita;
import domain.Entrate;
import domain.IEntrate;
import domain.Utenti;

public class WrapEntrate extends Observable implements IWrapperEntity, IEntrate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Entrate     entrate;

	public WrapEntrate() {
		entrate = new Entrate();
	}

	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.ID + " = " + id;

		Entrate entrata = null;

		try {

			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				entrata = new Entrate();
				Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
				entrata.setidEntrate(rs.getInt(1));
				entrata.setdescrizione(rs.getString(2));
				entrata.setFisseoVar(rs.getString(3));
				entrata.setinEuro(rs.getDouble(4));
				entrata.setdata(rs.getString(5));
				entrata.setnome(rs.getString(6));
				entrata.setUtenti(utente);
				entrata.setDataIns(rs.getString(8));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeConnection();
		}
		return entrata;

	}

	@Override
	public Iterator<Object> selectWhere(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector<Object> selectAllForUtente() {
		Vector<Object> entrate = new Vector<Object>();
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.IDUTENTE + " = " + utente.getidUtente();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				Entrate entrata = new Entrate();
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

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return entrate;
	}

	@Override
	public Vector<Object> selectAll() {
		Vector<Object> entrate = new Vector<Object>();
		Connection cn = DBUtil.getConnection();

		String sql = "SELECT * FROM " + Entrate.NOME_TABELLA;
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
				Entrate entrata = new Entrate();
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

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return entrate;
	}

	@Override
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		String sql = "";
		try {
			Entrate entrata = (Entrate) oggettoEntita;

			sql = "INSERT INTO " + Entrate.NOME_TABELLA + " (" + Entrate.DESCRIZIONE + ", " + Entrate.FISSEOVAR + ", "
			                + Entrate.INEURO + ", " + Entrate.DATA + ", " + Entrate.NOME + ", " + Entrate.IDUTENTE + ", " + Entrate.DATAINS
			                + ") VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps = cn.prepareStatement(sql);
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
		String sql = "DELETE FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.ID + " = " + id;
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

		Entrate entrata = (Entrate) oggettoEntita;
		String sql = "UPDATE " + Entrate.NOME_TABELLA + " SET " + Entrate.DESCRIZIONE + " = '" + entrata.getdescrizione() + "', " + Entrate.FISSEOVAR + " = '"
		                + entrata.getFisseoVar() + "', " + Entrate.INEURO + " = " + entrata.getinEuro() + ", " + Entrate.DATA + " = '" + entrata.getdata() + "', "
		                + Entrate.NOME + " = '" + entrata.getnome() + "', " + Entrate.IDUTENTE + " = " + entrata.getUtenti().getidUtente() + ", "
		                + Entrate.DATAINS + " = '" + entrata.getDataIns() + "' WHERE " + Entrate.ID + " = " + entrata.getidEntrate();
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
		String sql = "DELETE FROM " + Entrate.NOME_TABELLA;
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

	// metodo che restituisce le ultime dieci entrate
	/**
	 * Permette di ottenere un vettore con un numero di entita' entrate inserito
	 * come parametro
	 * 
	 * @param numEntry
	 * @return Vector<Entrate>
	 */
	public Vector<Entrate> dieciEntrate(int numEntry) {
		Vector<Entrate> entrate = null;
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if (utente != null)
			idUtente = utente.getidUtente();

		// TODO verificare che impostazioni venga pescato correttamente (non
		// passare dal controller?)
		String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " where " + Entrate.DATA
		                + " BETWEEN '" + Impostazioni.getAnno() + "/01/01" + "'" + " AND '" + Impostazioni.getAnno() + "/12/31" + "'"
		                + " AND " + Entrate.IDUTENTE + " = " + idUtente + " ORDER BY " + Entrate.ID + " desc limit 0," + numEntry;
		Connection cn = DBUtil.getConnection();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			entrate = new Vector<Entrate>();
			while (rs.next()) {
				Entrate e = new Entrate();
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return entrate;

	}

	/**
	 * Cancella l'ultima entita' "Entrate" inserita
	 */
	public boolean DeleteLastEntrate() {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.IDUTENTE + " = " + Controllore.getSingleton().getUtenteLogin().getidUtente()
		                + " ORDER BY " + Entrate.DATAINS + " DESC";

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				String sql2 = "DELETE FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.ID + "=?";
				PreparedStatement ps = cn.prepareStatement(sql2);
				ps.setInt(1, rs.getInt(1));
				ps.executeUpdate();
				ok = true;

			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO gestire log
			// log.severe("Operazione SQL di delete 'SingleSpesa' non eseguita:"+e.getMessage());
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
	public AbstractOggettoEntita getentitaPadre() {
		return entrate;
	}

	@Override
	public String getdata() {
		return entrate.getdata();
	}

	@Override
	public void setdata(String data) {
		entrate.setdata(data);
	}

	@Override
	public String getdescrizione() {
		return entrate.getdescrizione();
	}

	@Override
	public void setdescrizione(String descrizione) {
		entrate.setdescrizione(descrizione);
	}

	@Override
	public String getFisseoVar() {
		return entrate.getFisseoVar();
	}

	@Override
	public void setFisseoVar(String FisseoVar) {
		entrate.setFisseoVar(FisseoVar);
	}

	@Override
	public int getidEntrate() {
		return entrate.getidEntrate();
	}

	@Override
	public void setidEntrate(int idEntrate) {
		entrate.setidEntrate(idEntrate);
	}

	@Override
	public double getinEuro() {
		return entrate.getinEuro();
	}

	@Override
	public void setinEuro(double inEuro) {
		entrate.setinEuro(inEuro);
	}

	@Override
	public String getnome() {
		return entrate.getnome();
	}

	@Override
	public void setnome(String nome) {
		entrate.setnome(nome);
	}

	@Override
	public Utenti getUtenti() {
		return entrate.getUtenti();
	}

	@Override
	public void setUtenti(Utenti utenti) {
		entrate.setUtenti(utenti);
	}

	@Override
	public void setDataIns(String date) {
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

}
