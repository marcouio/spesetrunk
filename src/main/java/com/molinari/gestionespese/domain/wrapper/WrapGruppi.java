package com.molinari.gestionespese.domain.wrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;

import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;

import controller.ControlloreBase;
import db.Clausola;
import db.ExecutePreparedStatement;
import db.ExecuteResultSet;
import db.dao.IDAO;

public class WrapGruppi extends Observable implements IDAO<IGruppi>, IGruppi {

	private static final long serialVersionUID = 1L;
	private static final String WHERE = " WHERE ";
	private static final String SELECT_FROM = "SELECT * FROM ";
	private final Gruppi gruppo;
	private WrapBase base = new WrapBase();

	public WrapGruppi() {
		gruppo = new Gruppi();
	}

	@Override
	public IGruppi selectById(final int id) {

		final String sql = getQuerySelectById(id);

		final IGruppi gruppoLoc = new Gruppi();

		try {

			return new ExecuteResultSet<IGruppi>() {

				@Override
				protected IGruppi doWithResultSet(ResultSet rs) throws SQLException {

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
	public List<IGruppi> selectAll() {

		final String sql = SELECT_FROM + Gruppi.NOME_TABELLA + " ORDER BY " + Gruppi.ID + " asc";
		try {

			return new ExecuteResultSet<List<IGruppi>>() {

				@Override
				protected List<IGruppi> doWithResultSet(ResultSet rs) throws SQLException {
					final List<IGruppi> gruppi = new ArrayList<>();

					while (rs != null && rs.next()) {

						final IGruppi gruppoLoc = new Gruppi();
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
	public boolean insert(final IGruppi oggettoEntita) {
		String sql = getQueryInsert();

		ExecutePreparedStatement<IGruppi> eps = new ExecutePreparedStatement<IGruppi>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, IGruppi obj) throws SQLException {
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
	public boolean update(final IGruppi oggettoEntita) {

		final IGruppi gruppoLoc = oggettoEntita;
		final String sql = getQueryUpdate(gruppoLoc);
		
		return base.executeUpdate(sql);
	}

	private String getQueryUpdate(final IGruppi gruppoLoc) {
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

	public IGruppi selectByNome(final String nome) {


		final String sql = getQuerySelectByName(nome);

		final IGruppi gruppoLoc = new Gruppi();

		try {

			return new ExecuteResultSet<IGruppi>() {

				@Override
				protected IGruppi doWithResultSet(ResultSet rs) throws SQLException {

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
	public Set<ICatSpese> getCatSpeses() {
		return gruppo.getCatSpeses();
	}

	@Override
	public void setCatSpeses(final Set<ICatSpese> catSpeses) {
		gruppo.setCatSpeses(catSpeses);
	}

	@Override
	public IGruppi getEntitaPadre()  {
		return gruppo;
	}
	
	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public List<IGruppi> selectWhere(List<Clausola> clausole,
			String appentoToQuery)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getidGruppo());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setidGruppo(Integer.parseInt(idEntita));
	}

	@Override
	public String getNome() {
		return getnome();
	}

}