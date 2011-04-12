package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import business.DBUtil;
import business.cache.CacheGruppi;
import domain.AbstractOggettoEntita;
import domain.Budget;
import domain.CatSpese;
import domain.Gruppi;
import domain.ICatSpese;
import domain.SingleSpesa;

public class WrapCatSpese implements IWrapperEntity,ICatSpese{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CatSpese categoria;
	
	public WrapCatSpese() {
		categoria = new CatSpese();
	}

	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM "+CatSpese.NOME_TABELLA+" WHERE "+CatSpese.ID+" = " +id;
		
		CatSpese categorie = null;
		
		try {
			
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				categorie = new CatSpese();
				Gruppi gruppo =  CacheGruppi.getSingleton().getGruppo(Integer.toString(rs.getInt(5)));
//				Gruppi gruppo =  Controllore.getSingleton().getCacheGruppi().getGruppo(Integer.toString(rs.getInt(5)));
				categorie.setidCategoria(rs.getInt(1));
				categorie.setdescrizione(rs.getString(2));
				categorie.setimportanza(rs.getString(3));
				categorie.setnome(rs.getString(4));
				categorie.setGruppi(gruppo);
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
		return categorie;
	}

	@Override
	public Iterator<Object> selectWhere(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Object> selectAll() {
		Vector<Object> categorie = new Vector<Object>();
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + CatSpese.NOME_TABELLA ;
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				
				Gruppi gruppo =  CacheGruppi.getSingleton().getGruppo(Integer.toString(rs.getInt(5)));
				CatSpese categoria = new CatSpese();
				categoria.setidCategoria(rs.getInt(1));
				categoria.setdescrizione(rs.getString(2));
				categoria.setimportanza(rs.getString(3));
				categoria.setnome(rs.getString(4));
				categoria.setGruppi(gruppo);
				categorie.add(categoria);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return categorie;
		
	}

	@Override
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		String sql = "";
		try {
			CatSpese categoria = (CatSpese)oggettoEntita;
			
			sql="INSERT INTO " + CatSpese.NOME_TABELLA + " (" + CatSpese.DESCRIZIONE+", "+CatSpese.IMPORTANZA+", "+CatSpese.NOME+", "+CatSpese.IDGRUPPO+") VALUES(?,?,?,?)";
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
		String sql = "DELETE FROM "+CatSpese.NOME_TABELLA+" WHERE "+CatSpese.ID+" = "+id;
		Connection cn = DBUtil.getConnection();
		
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
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
		
		CatSpese categoria = (CatSpese) oggettoEntita;
		String sql = "UPDATE "+CatSpese.NOME_TABELLA+ " SET " +CatSpese.DESCRIZIONE+ " = " +categoria.getdescrizione()+", "+CatSpese.IMPORTANZA+" = "
		+categoria.getimportanza()+", "+CatSpese.NOME+ " = " +categoria.getnome()+", "+CatSpese.IDGRUPPO+ " = " +categoria.getidGruppo()
		+" WHERE "+ CatSpese.ID +" = "+categoria.getidCategoria();
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
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
		String sql = "DELETE FROM "+CatSpese.NOME_TABELLA;
		Connection cn = DBUtil.getConnection();
		
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
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
	public AbstractOggettoEntita getentitaPadre() {
		return categoria;
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
	public int getidGruppo() {
		return categoria.getidGruppo();
	}

	@Override
	public void setidGruppo(int idGruppo) {
		categoria.setidGruppo(idGruppo);
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
	
	
}
