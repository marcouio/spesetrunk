package com.molinari.gestionespese.domain.wrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;

import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.Clausola;
import com.molinari.utility.database.ExecutePreparedStatement;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.database.dao.IDAO;

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

		return selectGruppiSingleReturn(sql);

	}

	private IGruppi selectGruppiSingleReturn(final String sql) {

		final Map<String, IUtenti> mappaUtenti = CacheUtenti.getSingleton().getAllUtenti();
		
		try {

			return new ExecuteResultSet<IGruppi>() {

				@Override
				protected IGruppi doWithResultSet(ResultSet rs) throws SQLException {
					IGruppi gruppoLoc = new Gruppi();

					if (rs.next()) {
						gruppoLoc = fillSingleGroup(mappaUtenti, rs);
					}

					return gruppoLoc;
				}

			}.execute(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return new Gruppi();
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

		final Map<String, IUtenti> mappaUtenti = CacheUtenti.getSingleton().getAllUtenti();
		
		final String sql = SELECT_FROM + Gruppi.NOME_TABELLA + " ORDER BY " + Gruppi.ID + " asc";
		try {

			return new ExecuteResultSet<List<IGruppi>>() {

				@Override
				protected List<IGruppi> doWithResultSet(ResultSet rs) throws SQLException {
					final List<IGruppi> gruppi = new ArrayList<>();

					while (rs != null && rs.next()) {

						final IGruppi gruppoLoc = fillSingleGroup(mappaUtenti, rs);
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
	
	private IGruppi fillSingleGroup(final Map<String, IUtenti> mappaUtenti, ResultSet rs)
			throws SQLException {
		final IGruppi gruppoLoc = new Gruppi();
		gruppoLoc.setidGruppo(rs.getInt(1));
		gruppoLoc.setnome(rs.getString(2));
		gruppoLoc.setdescrizione(rs.getString(3));
		IUtenti utenti = mappaUtenti.get(Integer.toString(rs.getInt(4)));
		gruppoLoc.setUtenti(utenti);
		return gruppoLoc;
	}

	@Override
	public boolean insert(final IGruppi oggettoEntita) {
		String sql = getQueryInsert();

		ExecutePreparedStatement<IGruppi> eps = new ExecutePreparedStatement<IGruppi>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, IGruppi obj) throws SQLException {
				ps.setString(1, obj.getnome());
				ps.setString(2, obj.getdescrizione());
				
				if (obj.getUtenti() != null) {
					ps.setInt(3, obj.getUtenti().getidUtente());
				}

			}
		};
		return eps.executeUpdate(sql, oggettoEntita);
	}
	
	@Override
	public String toString() {
		return getnome();
	}

	private String getQueryInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(Gruppi.NOME_TABELLA);
		sb.append(" (");
		sb.append(Gruppi.COL_NOME);
		sb.append(", ");
		sb.append(Gruppi.COL_DESCRIZIONE);
		sb.append(", ");
		sb.append(Gruppi.COL_IDUTENTE);
		sb.append(") VALUES(?,?,?)");
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
		IUtenti utenti = gruppoLoc.getUtenti();
		if(utenti != null){
			int idUtente = utenti.getidUtente();
			sb.append("', ");
			sb.append(Gruppi.COL_IDUTENTE);
			sb.append(" = '");
			sb.append(idUtente);
		}
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

		return selectGruppiSingleReturn(sql);
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

	@Override
	public IUtenti getUtenti() {
		return gruppo.getUtenti();
	}

	@Override
	public void setUtenti(IUtenti utenti) {
		gruppo.setUtenti(utenti);
	}

}