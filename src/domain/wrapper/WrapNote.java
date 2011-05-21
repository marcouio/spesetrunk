package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import business.Controllore;
import business.DBUtil;
import business.cache.CacheUtenti;
import domain.AbstractOggettoEntita;
import domain.Entrate;
import domain.INote;
import domain.Note;
import domain.Utenti;

public class WrapNote extends Observable implements IWrapperEntity, INote {

	private final Note note;

	public WrapNote(Note nota) {
		this.note = nota;
	}

	public WrapNote() {
		note = new Note();
	}

	@Override
	public String getData() {
		return note.getData();
	}

	@Override
	public void setData(String _data_) {
		note.setData(_data_);
	}

	@Override
	public String getDataIns() {
		return note.getDataIns();
	}

	@Override
	public void setDataIns(String _dataIns_) {
		note.setDataIns(_dataIns_);
	}

	@Override
	public String getDescrizione() {
		return note.getDescrizione();
	}

	@Override
	public void setDescrizione(String _descrizione_) {
		note.setDescrizione(_descrizione_);
	}

	@Override
	public int getIdNote() {
		return note.getIdNote();
	}

	@Override
	public void setIdNote(int _idNote_) {
		note.setIdNote(_idNote_);
	}

	@Override
	public int getIdUtente() {
		return note.getIdUtente();
	}

	@Override
	public void setIdUtente(int _idUtente_) {
		note.setIdUtente(_idUtente_);
	}

	@Override
	public String getnome() {
		return note.getnome();
	}

	@Override
	public void setNome(String _nome_) {
		note.setNome(_nome_);
	}

	@Override
	public void setUtenti(Utenti utenti) {
		note.setUtenti(utenti);
	}

	@Override
	public Utenti getUtenti() {
		return note.getUtenti();
	}

	@Override
	public AbstractOggettoEntita getentitaPadre() {
		return note;
	}

	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + Note.NOME_TABELLA + " WHERE " + Note.ID + " = " + id;

		Note note = null;

		try {

			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				note = new Note();
				note.setIdNote(rs.getInt(1));
				note.setNome(rs.getString(2));
				note.setDescrizione(rs.getString(3));
				note.setUtenti(Controllore.getSingleton().getUtenteLogin());
				note.setData(rs.getString(5));
				note.setDataIns(rs.getString(6));

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
		return note;
	}

	@Override
	public Iterator<Object> selectWhere(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Object> selectAll() {
		Vector<Object> entrate = new Vector<Object>();
		Connection cn = DBUtil.getConnection();

		String sql = "SELECT * FROM " + Note.NOME_TABELLA;
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(4)));
				Note nota = new Note();
				nota.setIdNote(rs.getInt(1));
				nota.setDescrizione(rs.getString(3));
				nota.setData(rs.getString(6));
				nota.setNome(rs.getString(2));
				nota.setUtenti(utente);
				nota.setDataIns(rs.getString(5));
				entrate.add(nota);
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
			Note nota = (Note) oggettoEntita;

			sql = "INSERT INTO " + Note.NOME_TABELLA + " (" + Note.DESCRIZIONE + ", " + Entrate.DATA
			                + ", " + Entrate.NOME + ", " + Entrate.IDUTENTE + ", " + Entrate.DATAINS
			                + ") VALUES (?,?,?,?,?)";
			PreparedStatement ps = cn.prepareStatement(sql);
			// descrizione
			ps.setString(1, nota.getDescrizione());
			// data
			ps.setString(2, nota.getData());
			// nome
			ps.setString(3, nota.getnome());
			// idutente
			ps.setInt(4, nota.getUtenti().getidUtente());
			// datains
			ps.setString(5, nota.getDataIns());

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
		String sql = "DELETE FROM " + Note.NOME_TABELLA + " WHERE " + Note.ID + " = " + id;
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

		Note nota = (Note) oggettoEntita;
		String sql = "UPDATE " + Note.NOME_TABELLA + " SET " + Note.DESCRIZIONE + " = '" + nota.getDescrizione() + "', "
		                + Entrate.DATA + " = '" + nota.getData() + "', "
		                + Entrate.NOME + " = '" + nota.getnome() + "', " + Entrate.IDUTENTE + " = " +
		                nota.getUtenti().getidUtente() + ", " + Entrate.DATAINS + " = '" +
		                nota.getDataIns() + "' WHERE " + Note.ID + " = " + nota.getIdNote();
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
		String sql = "DELETE FROM " + Note.NOME_TABELLA;
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

	/**
	 * Cancella l'ultima entita' "Note" inserita
	 */
	public boolean DeleteLastNote() {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + Note.NOME_TABELLA + " WHERE " + Note.IDUTENTE + " = " + Controllore.getSingleton().getUtenteLogin().getidUtente()
		                + " ORDER BY " + Note.DATAINS + " DESC";

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				String sql2 = "DELETE FROM " + Note.NOME_TABELLA + " WHERE " + Note.ID + "=?";
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
	public void notifyObservers() {
		super.notifyObservers();
	}

	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}
}
