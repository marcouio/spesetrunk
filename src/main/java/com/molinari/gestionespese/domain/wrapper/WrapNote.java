package com.molinari.gestionespese.domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.domain.Utenti;

import controller.ControlloreBase;
import db.Clausola;
import db.ConnectionPool;
import db.ExecutePreparedStatement;
import db.ExecuteResultSet;
import db.dao.IDAO;

public class WrapNote extends Observable implements IDAO<Note>, INote {

	private static final String DELETE_FROM = "DELETE FROM ";
	private static final String WHERE = " WHERE ";
	private static final String SELECT_FROM = "SELECT * FROM ";
	private final Note note;
	private WrapBase base = new WrapBase();

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
	public void setData(final String data) {
		note.setData(data);
	}

	@Override
	public String getDataIns() {
		return note.getDataIns();
	}

	@Override
	public void setDataIns(final String dataIns) {
		note.setDataIns(dataIns);
	}

	@Override
	public String getDescrizione() {
		return note.getDescrizione();
	}

	@Override
	public void setDescrizione(final String descrizione) {
		note.setDescrizione(descrizione);
	}

	@Override
	public int getIdNote() {
		return note.getIdNote();
	}

	@Override
	public void setIdNote(final int idNote) {
		note.setIdNote(idNote);
	}

	@Override
	public int getIdUtente() {
		return note.getIdUtente();
	}

	@Override
	public void setIdUtente(final int idUtente) {
		note.setIdUtente(idUtente);
	}

	@Override
	public String getnome() {
		return note.getnome();
	}

	@Override
	public void setNome(final String nome) {
		note.setNome(nome);
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
	public Note selectById(final int id) {

		final String sql = SELECT_FROM + Note.NOME_TABELLA + WHERE + Note.ID + " = " + id;

		try {

			new ExecuteResultSet<Note>() {

				@Override
				protected Note doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						final Note noteLoc = new Note();
						noteLoc.setIdNote(rs.getInt(1));
						noteLoc.setNome(rs.getString(2));
						noteLoc.setDescrizione(rs.getString(3));
						noteLoc.setUtenti((Utenti) Controllore.getSingleton().getUtenteLogin());
						noteLoc.setData(rs.getString(5));
						noteLoc.setDataIns(rs.getString(6));

					}

					return note;
				}

			}.execute(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return note;
	}

	@Override
	public List<Note> selectAll() {


		final String sql = SELECT_FROM + Note.NOME_TABELLA;
		try {

			return new ExecuteResultSet<List<Note>>() {

				@Override
				protected List<Note> doWithResultSet(ResultSet rs) throws SQLException {
					final List<Note> noteList = new ArrayList<>();

					while (rs != null && rs.next()) {
						final Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(4)));
						final Note nota = new Note();
						nota.setIdNote(rs.getInt(1));
						nota.setDescrizione(rs.getString(3));
						nota.setData(rs.getString(6));
						nota.setNome(rs.getString(2));
						nota.setUtenti(utente);
						nota.setDataIns(rs.getString(5));
						noteList.add(nota);
					}

					return noteList;
				}

			}.execute(sql);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}


		return new ArrayList<>();
	}

	@Override
	public boolean insert(final Note oggettoEntita) {
		String sql = "INSERT INTO " + Note.NOME_TABELLA + " (" + Note.COL_DESCRIZIONE + ", " + Entrate.COL_DATA + ", " + Entrate.COL_NOME + ", " + Entrate.COL_IDUTENTE + ", " + Entrate.COL_DATAINS
				+ ") VALUES (?,?,?,?,?)";

		return new ExecutePreparedStatement<Note>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, Note obj) throws SQLException {
				// descrizione
				ps.setString(1, obj.getDescrizione());
				// data
				ps.setString(2, obj.getData());
				// nome
				ps.setString(3, obj.getnome());
				// idutente
				ps.setInt(4, obj.getUtenti().getidUtente());
				// datains
				ps.setString(5, obj.getDataIns());

			}
		}.executeUpdate(sql, oggettoEntita);
	}

	@Override
	public boolean delete(final int id) {
		final String sql = DELETE_FROM + Note.NOME_TABELLA + WHERE + Note.ID + " = " + id;

		return base.executeUpdate(sql);
	}

	@Override
	public boolean update(final Note oggettoEntita) {
		boolean ok = false;


		final Note nota = (Note) oggettoEntita;
		final String sql = "UPDATE " + Note.NOME_TABELLA + " SET " + Note.COL_DESCRIZIONE + " = '" + nota.getDescrizione() + "', " + Entrate.COL_DATA + " = '" + nota.getData() + "', "
				+ Entrate.COL_NOME + " = '" + nota.getnome() + "', " + Entrate.COL_IDUTENTE + " = " + nota.getUtenti().getidUtente() + ", " + Entrate.COL_DATAINS + " = '" + nota.getDataIns()
				+ "' WHERE " + Note.ID + " = " + nota.getIdNote();
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
		final String sql = DELETE_FROM + Note.NOME_TABELLA;


		try {

			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}

		return ok;
	}

	/**
	 * Cancella l'ultima entita' "Note" inserita
	 */
	public boolean DeleteLastNote() {
		boolean ok = false;

		final String sql = SELECT_FROM + Note.NOME_TABELLA + WHERE + Note.COL_IDUTENTE + " = " + ((Utenti) Controllore.getSingleton().getUtenteLogin()).getidUtente() + " ORDER BY "
				+ Note.COL_DATAINS + " DESC";
		final Connection cn = ConnectionPool.getSingleton().getConnection();

		try {

			ok = new ExecuteResultSet<Boolean>() {

				@Override
				protected Boolean doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						final String sql2 = DELETE_FROM + Note.NOME_TABELLA + WHERE + Note.ID + "=?";
						final PreparedStatement ps = cn.prepareStatement(sql2);
						ps.setInt(1, rs.getInt(1));
						ps.executeUpdate();
						ps.close();
						return true;

					}
					return false;
				}

			}.execute(sql);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ControlloreBase.getLog().severe("Operazione non eseguita: " + e.getMessage());
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public Note getEntitaPadre() {
		return note;
	}

	@Override
	public List<Note> selectWhere(List<Clausola> clausole,
			String appentoToQuery) {
		throw new UnsupportedOperationException();
	}
}
