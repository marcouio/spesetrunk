package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Observable;
import java.util.Set;
import java.util.Vector;

import business.DBUtil;
import domain.AbstractOggettoEntita;
import domain.CatSpese;
import domain.Gruppi;
import domain.IGruppi;

public class WrapGruppi extends Observable implements IWrapperEntity, IGruppi{

	private static final long serialVersionUID = 1L;
	private Gruppi gruppo;
	
	public WrapGruppi() {
		gruppo = new Gruppi();
	}

	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection2();
		String sql = "SELECT * FROM "+Gruppi.NOME_TABELLA+" WHERE "+Gruppi.ID+" = " +id;
		
		Gruppi gruppo = null;
		
		try {
			
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				gruppo = new Gruppi();
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
		String sql = "SELECT * FROM " + Gruppi.NOME_TABELLA +" ORDER BY "+Gruppi.ID+" asc";
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()){
			
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

	public Gruppi selectByNome(String nome) {
		
			Connection cn = DBUtil.getConnection2();
			String sql = "SELECT * FROM "+Gruppi.NOME_TABELLA+" WHERE "+Gruppi.NOME+" = \"" +nome+"\"";
			
			Gruppi gruppo = null;
			
			try {
				
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				if(rs.next()){
					gruppo = new Gruppi();
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
	public AbstractOggettoEntita getentitaPadre() {
		return gruppo;
	}

	@Override
	public String getdescrizione() {
		return gruppo.getdescrizione();
	}

	@Override
	public void setdescrizione(String descrizione) {
		gruppo.setdescrizione(descrizione);
	}

	@Override
	public int getidGruppo() {
		return gruppo.getidGruppo();
	}

	@Override
	public void setidGruppo(int idGruppo) {
		gruppo.setidGruppo(idGruppo);
	}

	@Override
	public String getnome() {
		return gruppo.getnome();
	}

	@Override
	public void setnome(String nome) {
		gruppo.setnome(nome);
	}

	@Override
	public Set<CatSpese> getCatSpeses() {
		return gruppo.getCatSpeses();
	}

	@Override
	public void setCatSpeses(Set<CatSpese> catSpeses) {
		gruppo.setCatSpeses(catSpeses);
	}

	
}
