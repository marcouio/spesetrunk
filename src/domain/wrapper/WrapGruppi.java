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
import domain.CatSpese;
import domain.Gruppi;
import domain.IGruppi;

public class WrapGruppi extends Observable implements IDAO, IGruppi {

	private final Gruppi gruppo;

	public WrapGruppi() {
		gruppo = new Gruppi();
	}

	@Override
	public Object selectById(final int id) {
		final Connection cn = DBUtil.getConnection();
		final String sql = "SELECT * FROM " + Gruppi.NOME_TABELLA + " WHERE " + Gruppi.ID + " = " + id;

		Gruppi gruppo = null;

		try {

			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				gruppo = new Gruppi();
				gruppo.setidGruppo(rs.getInt(1));
				gruppo.setnome(rs.getString(2));
				gruppo.setdescrizione(rs.getString(3));
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}
		return gruppo;

	}

	@Override
	public Vector<Object> selectAll() {
		final Vector<Object> gruppi = new Vector<Object>();
		final Connection cn = DBUtil.getConnection();
		final String sql = "SELECT * FROM " + Gruppi.NOME_TABELLA + " ORDER BY " + Gruppi.ID + " asc";
		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {

				final Gruppi gruppo = new Gruppi();
				gruppo.setidGruppo(rs.getInt(1));
				gruppo.setnome(rs.getString(2));
				gruppo.setdescrizione(rs.getString(3));
				gruppi.add(gruppo);
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
		return gruppi;

	}

	@Override
	public boolean insert(final Object oggettoEntita) {
		boolean ok = false;
		final Connection cn = DBUtil.getConnection();
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
		final String sql = "DELETE FROM " + Gruppi.NOME_TABELLA + " WHERE " + Gruppi.ID + " = " + id;
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

		final Gruppi gruppo = (Gruppi) oggettoEntita;
		final String sql = "UPDATE " + Gruppi.NOME_TABELLA + " SET " + Gruppi.NOME + " = '" + gruppo.getnome() + "', "
				+ Gruppi.DESCRIZIONE + " = '" + gruppo.getdescrizione() + "' WHERE " + Gruppi.ID + " = "
				+ gruppo.getidGruppo();
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
		final String sql = "DELETE FROM " + Gruppi.NOME_TABELLA;
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

	public Gruppi selectByNome(final String nome) {

		final Connection cn = DBUtil.getConnection();
		final String sql = "SELECT * FROM " + Gruppi.NOME_TABELLA + " WHERE " + Gruppi.NOME + " = \"" + nome + "\"";

		Gruppi gruppo = null;

		try {

			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				gruppo = new Gruppi();
				gruppo.setidGruppo(rs.getInt(1));
				gruppo.setnome(rs.getString(2));
				gruppo.setdescrizione(rs.getString(3));
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
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