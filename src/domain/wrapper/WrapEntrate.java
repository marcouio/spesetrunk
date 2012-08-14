package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import command.javabeancommand.AbstractOggettoEntita;

import view.impostazioni.Impostazioni;
import business.AltreUtil;
import business.Controllore;
import business.DBUtil;
import business.cache.CacheUtenti;
import db.dao.IDAO;
import domain.Entrate;
import domain.IEntrate;
import domain.Utenti;

public class WrapEntrate extends Observable implements IEntrate, IDAO {

	private final Entrate entrate;

	public WrapEntrate() {
		entrate = new Entrate();
	}

	@Override
	public Object selectById(final int id) {
		final Connection cn = DBUtil.getConnection();
		final String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.ID + " = " + id;

		Entrate entrata = null;

		try {

			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				entrata = new Entrate();
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

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeConnection();
		}
		return entrata;

	}

	public Vector<Object> selectAllForUtente() {
		final Vector<Object> entrate = new Vector<Object>();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		final Connection cn = DBUtil.getConnection();
		final String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.IDUTENTE + " = " + utente.getidUtente();
		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

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

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}
		return entrate;
	}

	@Override
	public Vector<Object> selectAll() {
		final Vector<Object> entrate = new Vector<Object>();
		final Connection cn = DBUtil.getConnection();

		final String sql = "SELECT * FROM " + Entrate.NOME_TABELLA;
		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
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

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}
		return entrate;
	}

	@Override
	public boolean insert(final Object oggettoEntita) {
		boolean ok = false;
		final Connection cn = DBUtil.getConnection();
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
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeConnection();
		}
		return ok;
	}

	@Override
	public boolean delete(final int id) {
		boolean ok = false;
		final String sql = "DELETE FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.ID + " = " + id;
		final Connection cn = DBUtil.getConnection();

		try {
			final Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public boolean update(final Object oggettoEntita) {
		boolean ok = false;
		final Connection cn = DBUtil.getConnection();

		final Entrate entrata = (Entrate) oggettoEntita;
		final String sql = "UPDATE " + Entrate.NOME_TABELLA + " SET " + Entrate.DESCRIZIONE + " = '" + entrata.getdescrizione() + "', " + Entrate.FISSEOVAR + " = '"
				+ entrata.getFisseoVar() + "', " + Entrate.INEURO + " = " + entrata.getinEuro() + ", " + Entrate.DATA + " = '" + entrata.getdata() + "', " + Entrate.NOME + " = '"
				+ entrata.getnome() + "', " + Entrate.IDUTENTE + " = " + entrata.getUtenti().getidUtente() + ", " + Entrate.DATAINS + " = '" + entrata.getDataIns() + "' WHERE "
				+ Entrate.ID + " = " + entrata.getidEntrate();
		try {
			final Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public boolean deleteAll() {
		boolean ok = false;
		final String sql = "DELETE FROM " + Entrate.NOME_TABELLA;
		final Connection cn = DBUtil.getConnection();

		try {
			final Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		try {
			cn.close();
		} catch (final SQLException e) {
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
	public Vector<Entrate> movimentiEntrateFiltrati(final String dataDa, final String dataA, final String nome, final Double euro, final String categoria) {
		Vector<Entrate> entrate = null;
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
		final Connection cn = DBUtil.getConnection();
		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql.toString());
			entrate = new Vector<Entrate>();
			while (rs.next()) {
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
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return entrate;

	}

	// metodo che restituisce le ultime dieci entrate
	/**
	 * Permette di ottenere un vettore con un numero di entita' entrate inserito
	 * come parametro
	 * 
	 * @param numEntry
	 * @return Vector<Entrate>
	 */
	public Vector<Entrate> dieciEntrate(final int numEntry) {
		Vector<Entrate> entrate = null;
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getidUtente();
		}

		final String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " where " + Entrate.DATA + " BETWEEN '" + Impostazioni.getAnno() + "/01/01" + "'" + " AND '"
				+ Impostazioni.getAnno() + "/12/31" + "'" + " AND " + Entrate.IDUTENTE + " = " + idUtente + " ORDER BY " + Entrate.ID + " desc limit 0," + numEntry;
		final Connection cn = DBUtil.getConnection();
		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			entrate = new Vector<Entrate>();
			while (rs.next()) {
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
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		try {
			cn.close();
		} catch (final SQLException e) {
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
		final Connection cn = DBUtil.getConnection();
		final String sql = "SELECT * FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.IDUTENTE + " = " + ((Utenti) Controllore.getSingleton().getUtenteLogin()).getidUtente()
				+ " ORDER BY " + Entrate.DATAINS + " DESC";

		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				final String sql2 = "DELETE FROM " + Entrate.NOME_TABELLA + " WHERE " + Entrate.ID + "=?";
				final PreparedStatement ps = cn.prepareStatement(sql2);
				ps.setInt(1, rs.getInt(1));
				ps.executeUpdate();
				ok = true;

			}

		} catch (final Exception e) {
			e.printStackTrace();
			Controllore.getLog().severe("Operazione non riuscita: " + e.getMessage());
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
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
	public AbstractOggettoEntita getEntitaPadre() throws Exception {
		return entrate;
	}

	@Override
	public Object selectWhere(HashMap<String, String> clausole)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
