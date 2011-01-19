package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import business.DBUtil;
import business.cache.CacheGruppi;
import domain.CatSpese;
import domain.Gruppi;

public class WrapCatSpese extends CatSpese implements IWrapperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM "+CatSpese.NOME_TABELLA+" WHERE "+CatSpese.ID+" = " +id;
		
		CatSpese categorie = new CatSpese();
		
		try {
			
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
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
		String sql = "SELECT * FROM " + NOME_TABELLA ;
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				
//				Gruppi gruppo =  Controllore.getSingleton().getCacheGruppi().getGruppo(Integer.toString(rs.getInt(5)));
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
	
	
}
