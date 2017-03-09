package com.molinari.gestionespese.domain.wrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Level;

import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.domain.Budget;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.SingleSpesa;

import controller.ControlloreBase;
import db.Clausola;
import db.ConnectionPool;
import db.ExecutePreparedStatement;
import db.ExecuteResultSet;
import db.dao.IDAO;

public class WrapCatSpese extends Observable implements ICatSpese, IDAO<CatSpese> {

	private static final String WHERE = " WHERE ";
	private final CatSpese categoria;
	private WrapBase base = new WrapBase();

	public WrapCatSpese() {
		categoria = new CatSpese();
	}

	@Override
	public CatSpese selectById(int id) {

		final String sql = "SELECT * FROM " + CatSpese.NOME_TABELLA + WHERE + CatSpese.ID + " = " + id;

		final CatSpese categorie = new CatSpese();

		try {

			new ExecuteResultSet<CatSpese>() {

				@Override
				protected CatSpese doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						final String idGruppo = Integer.toString(rs.getInt(5));

						categorie.setidCategoria(rs.getInt(1));
						categorie.setdescrizione(rs.getString(2));
						categorie.setimportanza(rs.getString(3));
						categorie.setnome(rs.getString(4));
						ConnectionPool.getSingleton().chiudiOggettiDb(null);
						final IGruppi gruppo = CacheGruppi.getSingleton().getGruppo(idGruppo);
						categorie.setGruppi(gruppo);
					}
					return categorie;
				}

			}.execute(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return categorie;
	}

	@Override
	public List<CatSpese> selectAll() {

		final String sql = "SELECT * FROM " + CatSpese.NOME_TABELLA;
		try {
			CacheGruppi.getSingleton().getAllGruppi();

			return new ExecuteResultSet<List<CatSpese>>() {


				@Override
				protected List<CatSpese> doWithResultSet(ResultSet rs) throws SQLException {

					final List<CatSpese> categorie = new ArrayList<>();


					while (rs != null && rs.next()) {

						final String idGruppo = Integer.toString(rs.getInt(5));
						final CatSpese categoriaLoc = new CatSpese();
						categoriaLoc.setidCategoria(rs.getInt(1));
						categoriaLoc.setdescrizione(rs.getString(2));
						categoriaLoc.setimportanza(rs.getString(3));
						categoriaLoc.setnome(rs.getString(4));
						categorie.add(categoriaLoc);

						final IGruppi gruppo = CacheGruppi.getSingleton().getGruppo(idGruppo);
						categoriaLoc.setGruppi(gruppo);
					}
					return categorie;
				}

			}.execute(sql);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return new ArrayList<>();

	}

	@Override
	public boolean insert(CatSpese oggettoEntita) {
		String sql = "INSERT INTO " + CatSpese.NOME_TABELLA + " (" + CatSpese.COL_DESCRIZIONE + ", " + CatSpese.COL_IMPORTANZA + ", " + CatSpese.COL_NOME + ", " + CatSpese.IDGRUPPO + ") VALUES(?,?,?,?)";

		return new ExecutePreparedStatement<CatSpese>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, CatSpese obj) throws SQLException {
				ps.setString(1, obj.getdescrizione());
				ps.setString(2, obj.getimportanza());
				ps.setString(3, obj.getnome());
				if (obj.getGruppi() != null) {
					ps.setInt(4, obj.getGruppi().getidGruppo());
				}

			}
		}.executeUpdate(sql, oggettoEntita);

	}

	@Override
	public boolean delete(int id) {
		final String sql = "DELETE FROM " + CatSpese.NOME_TABELLA + WHERE + CatSpese.ID + " = " + id;
		return base.executeUpdate(sql);
	}

	@Override
	public boolean update(CatSpese oggettoEntita) {


		final CatSpese categoriaLoc = (CatSpese) oggettoEntita;
		final String sql = "UPDATE " + CatSpese.NOME_TABELLA + " SET " + CatSpese.COL_DESCRIZIONE + " = '" + categoriaLoc.getdescrizione() + "', " + CatSpese.COL_IMPORTANZA + " = '"
				+ categoriaLoc.getimportanza() + "', " + CatSpese.COL_NOME + " = '" + categoriaLoc.getnome() + "', " + CatSpese.IDGRUPPO + " = " + categoriaLoc.getGruppi().getidGruppo()
				+ WHERE + CatSpese.ID + " = " + categoriaLoc.getidCategoria();
		return base.executeUpdate(sql);
	}

	@Override
	public boolean deleteAll() {
		boolean ok = false;
		final String sql = "DELETE FROM " + CatSpese.NOME_TABELLA;


		try {

			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return ok;
	}

	@Override
	public String getdescrizione() {
		return categoria.getdescrizione();
	}

	@Override
	public void setdescrizione(String descrizione) {
		categoria.setdescrizione(descrizione);
	}

	@Override
	public int getidCategoria() {
		return categoria.getidCategoria();
	}

	@Override
	public void setidCategoria(int idCategoria) {
		categoria.setidCategoria(idCategoria);
	}

	@Override
	public String getimportanza() {
		return categoria.getimportanza();
	}

	@Override
	public void setimportanza(String importanza) {
		categoria.setimportanza(importanza);
	}

	@Override
	public String getnome() {
		return categoria.getnome();
	}

	@Override
	public void setnome(String nome) {
		categoria.setnome(nome);
	}

	@Override
	public Budget getBudget() {
		return categoria.getBudget();
	}

	@Override
	public void setBudget(Budget budget) {
		categoria.setBudget(budget);
	}

	@Override
	public IGruppi getGruppi() {
		return categoria.getGruppi();
	}

	@Override
	public void setGruppi(IGruppi gruppi) {
		categoria.setGruppi(gruppi);
	}

	@Override
	public Set<SingleSpesa> getSingleSpesas() {
		return categoria.getSingleSpesas();
	}

	@Override
	public void setSingleSpesas(Set<SingleSpesa> singleSpesas) {
		categoria.setSingleSpesas(singleSpesas);
	}

	@Override
	public CatSpese getEntitaPadre()  {
		return categoria;
	}
	
	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public List<CatSpese> selectWhere(List<Clausola> clausole, String appentoToQuery)  {
		throw new UnsupportedOperationException();
	}

}
