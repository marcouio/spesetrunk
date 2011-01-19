package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import business.DBUtil;
import domain.Risparmio;

public class WrapRisparmio extends Risparmio implements IWrapperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + NOME_TABELLA + " WHERE " + ID + "=" +id;
		Risparmio risparmio = new Risparmio();
		try{
			
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				risparmio.setidRisparmio(rs.getInt(1));
				risparmio.setPerSulTotale(rs.getDouble(2));
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
		return risparmio;
	}

	@Override
	public Iterator<Object> selectWhere(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Object> selectAll() {
		Vector<Object> risparmi = new Vector<Object>();
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + Risparmio.NOME_TABELLA ;
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				Risparmio risparmio = new Risparmio();
				risparmio.setidRisparmio(rs.getInt(1));
				risparmio.setPerSulTotale(rs.getDouble(2));
				risparmi.add(risparmio);
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
		return risparmi;
	
	}

	@Override
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		String sql = "";
		try {
			Risparmio risparmio = (Risparmio)oggettoEntita;
			
			sql="INSERT INTO " + Risparmio.NOME_TABELLA + " (" +Risparmio.PERCSULTOT+") VALUES(?)";
			PreparedStatement ps = cn.prepareStatement(sql);
			
			ps.setDouble(1, risparmio.getPerSulTotale());
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
		String sql = "DELETE FROM "+Risparmio.NOME_TABELLA+" WHERE "+Risparmio.ID+" = "+id;
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
		
		Risparmio risparmio = (Risparmio) oggettoEntita;
		String sql = "UPDATE "+Risparmio.NOME_TABELLA+ " SET " +Risparmio.PERCSULTOT+ " = " +risparmio.getPerSulTotale()
		+" WHERE "+ Risparmio.ID +" = "+risparmio.getidRisparmio();
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
		String sql = "DELETE FROM "+Risparmio.NOME_TABELLA;
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
