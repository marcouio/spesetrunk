package com.molinari.gestionespese.domain.wrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;

import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.IGruppi;

import controller.ControlloreBase;
import db.Clausola;
import db.ExecutePreparedStatement;
import db.ExecuteResultSet;
import db.dao.IDAO;

public class WrapGruppi extends Observable implements IDAO<Gruppi>, IGruppi {

	private static final String WHERE = " WHERE ";
	private static final String SELECT_FROM = "SELECT * FROM ";
	private final Gruppi gruppo;
	private WrapBase base = new WrapBase();

	public WrapGruppi() {
		gruppo = new Gruppi();
	}

	@Override
	public Gruppi selectById(final int id) {

		final String sql = getQuerySelectById(id);

		final Gruppi gruppoLoc = new Gruppi();

		try {

			return new ExecuteResultSet<Gruppi>() {

				@Override
				protected Gruppi doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						gruppoLoc.setidGruppo(rs.getInt(1));
						gruppoLoc.setnome(rs.getString(2));
						gruppoLoc.setdescrizione(rs.getString(3));
					}

					return gruppoLoc;
				}

			}.execute(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return gruppoLoc;

	}

	private String getQuerySelectById(final int id) {
		StringBuilder sb = new StringBuilder();
		sb.append(SELECT_FROM);
		sb.append(Gruppi.NOME_TABELLA);
		sb.append(WHERE);
		sb.append(Gruppi.ID);
		sb.append(" = ");
		sb.append(id);
		return sb.toString();
	}

	@Override
	public List<Gruppi> selectAll() {

		final String sql = SELECT_FROM + Gruppi.NOME_TABELLA + " ORDER BY " + Gruppi.ID + " asc";
		try {

			return new ExecuteResultSet<List<Gruppi>>() {

				@Override
				protected List<Gruppi> doWithResultSet(ResultSet rs) throws SQLException {
					final List<Gruppi> gruppi = new ArrayList<>();

					while (rs != null && rs.next()) {

						final Gruppi gruppoLoc = new Gruppi();
						gruppoLoc.setidGruppo(rs.getInt(1));
						gruppoLoc.setnome(rs.getString(2));
						gruppoLoc.setdescrizione(rs.getString(3));
						gruppi.add(gruppoLoc);
					}
					return gruppi;
				}

			}.execute(sql);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return new ArrayList<>();

	}

	@Override
	public boolean insert(final Gruppi oggettoEntita) {
		String sql = getQueryInsert();

		ExecutePreparedStatement<Gruppi> eps = new ExecutePreparedStatement<Gruppi>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, Gruppi obj) throws SQLException {
				ps.setString(1, gruppo.getnome());
				ps.setString(2, gruppo.getdescrizione());

			}
		};
		return eps.executeUpdate(sql, oggettoEntita);
	}

	private String getQueryInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(Gruppi.NOME_TABELLA);
		sb.append(" (");
		sb.append(Gruppi.COL_NOME);
		sb.append(", ");
		sb.append(Gruppi.COL_DESCRIZIONE);
		sb.append(") VALUES(?,?)");
		return sb.toString();
	}

	@Override
	public boolean delete(final int id) {
		final String sql = getQueryDelete(id);
		return base.executeUpdate(sql);
	}

	private String getQueryDelete(final int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(Gruppi.NOME_TABELLA);
		sb.append(WHERE);
		sb.append(Gruppi.ID);
		sb.append(" = ");
		sb.append(id);
		return sb.toString();
	}

	@Override
	public boolean update(final Gruppi oggettoEntita) {

		final Gruppi gruppoLoc = (Gruppi) oggettoEntita;
		final String sql = getQueryUpdate(gruppoLoc);
		
		return base.executeUpdate(sql);
	}

	private String getQueryUpdate(final Gruppi gruppoLoc) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(Gruppi.NOME_TABELLA);
		sb.append(" SET ");
		sb.append(Gruppi.COL_NOME);
		sb.append(" = '");
		sb.append(gruppoLoc.getnome());
		sb.append("', ");
		sb.append(Gruppi.COL_DESCRIZIONE);
		sb.append(" = '");
		sb.append(gruppoLoc.getdescrizione());
		sb.append("' WHERE ");
		sb.append(Gruppi.ID);
		sb.append(" = ");
		sb.append(gruppoLoc.getidGruppo());
		return sb.toString();
	}

	@Override
	public boolean deleteAll() {
		final String sql = "DELETE FROM " + Gruppi.NOME_TABELLA;
		return base.executeUpdate(sql);
	}

	public Gruppi selectByNome(final String nome) {


		final String sql = getQuerySelectByName(nome);

		final Gruppi gruppoLoc = new Gruppi();

		try {

			return new ExecuteResultSet<Gruppi>() {

				@Override
				protected Gruppi doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						gruppoLoc.setidGruppo(rs.getInt(1));
						gruppoLoc.setnome(rs.getString(2));
						gruppoLoc.setdescrizione(rs.getString(3));
					}

					return gruppoLoc;
				}

			}.execute(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return gruppoLoc;
	}

	private String getQuerySelectByName(final String nome) {
		StringBuilder sb = new StringBuilder();
		sb.append(SELECT_FROM);
		sb.append(Gruppi.NOME_TABELLA);
		sb.append(WHERE);
		sb.append(Gruppi.COL_NOME);
		sb.append(" = \"");
		sb.append(nome);
		sb.append("\"");
		return sb.toString();
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
	public Gruppi getEntitaPadre()  {
		return gruppo;
	}
	
	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public List<Gruppi> selectWhere(List<Clausola> clausole,
			String appentoToQuery)  {
		throw new UnsupportedOperationException();
	}

}