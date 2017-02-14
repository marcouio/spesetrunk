package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import business.cache.CacheGruppi;
import command.javabeancommand.AbstractOggettoEntita;
import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;
import domain.Budget;
import domain.CatSpese;
import domain.Gruppi;
import domain.ICatSpese;
import domain.SingleSpesa;

public class WrapCatSpese extends Observable implements ICatSpese, IDAO {

	private final CatSpese categoria;

	public WrapCatSpese() {
		categoria = new CatSpese();
	}

	@Override
	public Object selectById(int id) {
		
		String sql = "SELECT * FROM " + CatSpese.NOME_TABELLA + " WHERE " + CatSpese.ID + " = " + id;

		final CatSpese categorie = new CatSpese();

		try {
			
			ConnectionPool.getSingleton().new ExecuteResultSet<Object>() {

				@Override
				protected Object doWithResultSet(ResultSet rs) throws SQLException {
					
					if (rs.next()) {
						String idGruppo = Integer.toString(rs.getInt(5));
						
						categorie.setidCategoria(rs.getInt(1));
						categorie.setdescrizione(rs.getString(2));
						categorie.setimportanza(rs.getString(3));
						categorie.setnome(rs.getString(4));
						ConnectionPool.getSingleton().chiudiOggettiDb(null);
						Gruppi gruppo = CacheGruppi.getSingleton().getGruppo(idGruppo);
						categorie.setGruppi(gruppo);
					}
					return categorie;
				}
				
			}.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return categorie;
	}

	@Override
	public List<Object> selectAll() {
		
		String sql = "SELECT * FROM " + CatSpese.NOME_TABELLA;
		try {
			CacheGruppi.getSingleton().getAllGruppi();
			
			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Object>>() {

				
				@Override
				protected List<Object> doWithResultSet(ResultSet rs) throws SQLException {

					List<Object> categorie = new ArrayList<>();
					
					
					while (rs != null && rs.next()) {
						
						String idGruppo = Integer.toString(rs.getInt(5));
						CatSpese categoria = new CatSpese();
						categoria.setidCategoria(rs.getInt(1));
						categoria.setdescrizione(rs.getString(2));
						categoria.setimportanza(rs.getString(3));
						categoria.setnome(rs.getString(4));
						categorie.add(categoria);
						
						Gruppi gruppo = CacheGruppi.getSingleton().getGruppo(idGruppo);
						categoria.setGruppi(gruppo);
					}
					return categorie;
				}
				
			}.execute(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;

	}

	@Override
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = ConnectionPool.getSingleton().getConnection();
		String sql = "";
		try {
			CatSpese categoria = (CatSpese) oggettoEntita;

			sql = "INSERT INTO " + CatSpese.NOME_TABELLA + " (" + CatSpese.DESCRIZIONE + ", " + CatSpese.IMPORTANZA + ", " + CatSpese.NOME + ", " + CatSpese.IDGRUPPO + ") VALUES(?,?,?,?)";
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, categoria.getdescrizione());
			ps.setString(2, categoria.getimportanza());
			ps.setString(3, categoria.getnome());
			if (categoria.getGruppi() != null)
				ps.setInt(4, categoria.getGruppi().getidGruppo());

			ps.executeUpdate();
			ok = true;
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		} finally {
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		}
		return ok;
	}

	@Override
	public boolean delete(int id) {
		boolean ok = false;
		String sql = "DELETE FROM " + CatSpese.NOME_TABELLA + " WHERE " + CatSpese.ID + " = " + id;
		

		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return ok;
	}

	@Override
	public boolean update(Object oggettoEntita) {
		boolean ok = false;
		

		CatSpese categoria = (CatSpese) oggettoEntita;
		String sql = "UPDATE " + CatSpese.NOME_TABELLA + " SET " + CatSpese.DESCRIZIONE + " = '" + categoria.getdescrizione() + "', " + CatSpese.IMPORTANZA + " = '"
		                + categoria.getimportanza() + "', " + CatSpese.NOME + " = '" + categoria.getnome() + "', " + CatSpese.IDGRUPPO + " = " + categoria.getGruppi().getidGruppo()
		                + " WHERE " + CatSpese.ID + " = " + categoria.getidCategoria();
		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return ok;

	}

	@Override
	public boolean deleteAll() {
		boolean ok = false;
		String sql = "DELETE FROM " + CatSpese.NOME_TABELLA;
		

		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (SQLException e) {
			e.printStackTrace();
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
	public Gruppi getGruppi() {
		return categoria.getGruppi();
	}

	@Override
	public void setGruppi(Gruppi gruppi) {
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
	public void notifyObservers() {
		super.notifyObservers();
	}

	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() throws Exception {
		return categoria;
	}

	@Override
	public Object selectWhere(ArrayList<Clausola> clausole,
			String appentoToQuery) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
