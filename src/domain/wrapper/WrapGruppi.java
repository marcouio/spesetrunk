package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import business.DBUtil;
import domain.Gruppi;

public class WrapGruppi extends Gruppi implements IWrapperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection2();
		String sql = "SELECT * FROM "+Gruppi.NOME_TABELLA+" WHERE "+Gruppi.ID+" = " +id;
		
		Gruppi gruppo = new Gruppi();
		
		try {
			
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				gruppo.setidGruppo(rs.getInt(1));
				gruppo.setnome(rs.getString(2));
				gruppo.setdescrizione(rs.getString(3));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return gruppo;

	}

	@Override
	public Iterator<Object> selectWhere(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Object> selectAll() {
		Vector<Object> gruppi = new Vector<Object>();
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + Gruppi.NOME_TABELLA ;
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
			
				Gruppi gruppo = new Gruppi();
				gruppo.setidGruppo(rs.getInt(1));
				gruppo.setnome(rs.getString(2));
				gruppo.setdescrizione(rs.getString(3));
				gruppi.add(gruppo);
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
		return gruppi;
		
	}

	@Override
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		String sql = "";
		try {
			Gruppi gruppo = (Gruppi)oggettoEntita;
			
			sql="INSERT INTO " + Gruppi.NOME_TABELLA + " (" + Gruppi.NOME+", "+Gruppi.DESCRIZIONE+") VALUES(?,?)";
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, gruppo.getnome());
			ps.setString(2, gruppo.getdescrizione());
			
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
		String sql = "DELETE FROM "+Gruppi.NOME_TABELLA+" WHERE "+Gruppi.ID+" = "+id;
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
		
		Gruppi gruppo = (Gruppi) oggettoEntita;
		String sql = "UPDATE "+Gruppi.NOME_TABELLA+ " SET " +Gruppi.NOME+ " = " +gruppo.getnome()+", "+Gruppi.DESCRIZIONE+" = "
		+gruppo.getdescrizione()+" WHERE "+ Gruppi.ID +" = "+gruppo.getidGruppo();
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
		String sql = "DELETE FROM "+Gruppi.NOME_TABELLA;
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
