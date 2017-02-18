package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import business.Controllore;
import business.DBUtil;
import business.cache.CacheUtenti;
import command.javabeancommand.AbstractOggettoEntita;
import controller.ControlloreBase;
import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;
import domain.Entrate;
import domain.INote;
import domain.Note;
import domain.Utenti;

public class WrapNote extends Observable implements IDAO, INote {

	private final Note note;

	public WrapNote(final Note nota) {
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
	public void setData(final String _data_) {
		note.setData(_data_);
	}

	@Override
	public String getDataIns() {
		return note.getDataIns();
	}

	@Override
	public void setDataIns(final String _dataIns_) {
		note.setDataIns(_dataIns_);
	}

	@Override
	public String getDescrizione() {
		return note.getDescrizione();
	}

	@Override
	public void setDescrizione(final String _descrizione_) {
		note.setDescrizione(_descrizione_);
	}

	@Override
	public int getIdNote() {
		return note.getIdNote();
	}

	@Override
	public void setIdNote(final int _idNote_) {
		note.setIdNote(_idNote_);
	}

	@Override
	public int getIdUtente() {
		return note.getIdUtente();
	}

	@Override
	public void setIdUtente(final int _idUtente_) {
		note.setIdUtente(_idUtente_);
	}

	@Override
	public String getnome() {
		return note.getnome();
	}

	@Override
	public void setNome(final String _nome_) {
		note.setNome(_nome_);
	}

	@Override
	public void setUtenti(final Utenti utenti) {
		note.setUtenti(utenti);
	}

	@Override
	public Utenti getUtenti() {
		return note.getUtenti();
	}

	@Override
	public Object selectById(final int id) {
		
		final String sql = "SELECT * FROM " + Note.NOME_TABELLA + " WHERE " + Note.ID + " = " + id;

		final Note note = new Note();

		try {

			ConnectionPool.getSingleton().new ExecuteResultSet<Object>() {

				@Override
				protected Object doWithResultSet(ResultSet rs) throws SQLException {
					
					if (rs.next()) {
						note.setIdNote(rs.getInt(1));
						note.setNome(rs.getString(2));
						note.setDescrizione(rs.getString(3));
						note.setUtenti((Utenti) Controllore.getSingleton().getUtenteLogin());
						note.setData(rs.getString(5));
						note.setDataIns(rs.getString(6));
						
					}
					
					return note;
				}
				
			}.execute(sql);

		} catch (final SQLException e) {
			e.printStackTrace();
		} 
		return note;
	}

	@Override
	public List<Object> selectAll() {
		final List<Object> note = new ArrayList<>();
		

		final String sql = "SELECT * FROM " + Note.NOME_TABELLA;
		try {
			
			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Object>>() {

				@Override
				protected List<Object> doWithResultSet(ResultSet rs) throws SQLException {
					final List<Object> note = new ArrayList<>();
					
					while (rs != null && rs.next()) {
						final Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(4)));
						final Note nota = new Note();
						nota.setIdNote(rs.getInt(1));
						nota.setDescrizione(rs.getString(3));
						nota.setData(rs.getString(6));
						nota.setNome(rs.getString(2));
						nota.setUtenti(utente);
						nota.setDataIns(rs.getString(5));
						note.add(nota);
					}
					
					return note;
				}
				
			}.execute(sql);
			
		} catch (final Exception e) {
			e.printStackTrace();
		} 
			
		
		return note;
	}

	@Override
	public boolean insert(final Object oggettoEntita) {
		boolean ok = false;
		Connection cn = ConnectionPool.getSingleton().getConnection();
		String sql = "";
		try {
			final Note nota = (Note) oggettoEntita;

			sql = "INSERT INTO " + Note.NOME_TABELLA + " (" + Note.DESCRIZIONE + ", " + Entrate.DATA + ", " + Entrate.NOME + ", " + Entrate.IDUTENTE + ", " + Entrate.DATAINS
					+ ") VALUES (?,?,?,?,?)";
			final PreparedStatement ps = cn.prepareStatement(sql);
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
		final String sql = "DELETE FROM " + Note.NOME_TABELLA + " WHERE " + Note.ID + " = " + id;
		

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
		

		final Note nota = (Note) oggettoEntita;
		final String sql = "UPDATE " + Note.NOME_TABELLA + " SET " + Note.DESCRIZIONE + " = '" + nota.getDescrizione() + "', " + Entrate.DATA + " = '" + nota.getData() + "', "
				+ Entrate.NOME + " = '" + nota.getnome() + "', " + Entrate.IDUTENTE + " = " + nota.getUtenti().getidUtente() + ", " + Entrate.DATAINS + " = '" + nota.getDataIns()
				+ "' WHERE " + Note.ID + " = " + nota.getIdNote();
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
		final String sql = "DELETE FROM " + Note.NOME_TABELLA;
		

		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		
		return ok;
	}

	/**
	 * Cancella l'ultima entita' "Note" inserita
	 */
	public boolean DeleteLastNote() {
		boolean ok = false;
		
		final String sql = "SELECT * FROM " + Note.NOME_TABELLA + " WHERE " + Note.IDUTENTE + " = " + ((Utenti) Controllore.getSingleton().getUtenteLogin()).getidUtente() + " ORDER BY "
				+ Note.DATAINS + " DESC";
		Connection cn = ConnectionPool.getSingleton().getConnection();

		try {
			
			ok = ConnectionPool.getSingleton().new ExecuteResultSet<Boolean>() {

				@Override
				protected Boolean doWithResultSet(ResultSet rs) throws SQLException {
					
					if (rs.next()) {
						final String sql2 = "DELETE FROM " + Note.NOME_TABELLA + " WHERE " + Note.ID + "=?";
						final PreparedStatement ps = cn.prepareStatement(sql2);
						ps.setInt(1, rs.getInt(1));
						ps.executeUpdate();
						return true;

					}
					return false;
				}
				
			}.execute(sql);
			
		} catch (final Exception e) {
			e.printStackTrace();
			ControlloreBase.getLog().severe("Operazione non eseguita: " + e.getMessage());
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
	public void notifyObservers() {
		super.notifyObservers();
	}

	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return note;
	}

	@Override
	public Object selectWhere(List<Clausola> clausole,
			String appentoToQuery) {
		// TODO Auto-generated method stub
		return null;
	}
}
