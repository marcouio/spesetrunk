package domain.wrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import business.DBUtil;
import domain.AbstractOggettoEntita;
import domain.ILookandfeel;
import domain.Lookandfeel;

public class WrapLookAndFeel extends Observable implements IWrapperEntity,ILookandfeel {

	private Lookandfeel lookandfeel;
	private static final long serialVersionUID = 1L;

	public WrapLookAndFeel(){
		lookandfeel = new Lookandfeel();
	}
	
	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM "+Lookandfeel.NOME_TABELLA+" WHERE "+Lookandfeel.ID+" = " +id;
		
		Lookandfeel look = null;
		
		try {
			
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				look = new Lookandfeel();
				look.setidLook(rs.getInt(1));
				look.setnome(rs.getString(2));
				look.setvalore(rs.getString(3));
				look.setusato(rs.getInt(4));
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
		return look;

	}

	@Override
	public Iterator<Object> selectWhere(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Object> selectAll() {
		Vector<Object> looks = new Vector<Object>();
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + Lookandfeel.NOME_TABELLA ;
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				Lookandfeel look = new Lookandfeel();
				look.setidLook(rs.getInt(1));
				look.setnome(rs.getString(2));
				look.setvalore(rs.getString(3));
				look.setusato(rs.getInt(4));
				looks.add(look);
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
		return looks;
	}

	@Override
	public boolean insert(Object oggettoEntita) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		
		Lookandfeel look = (Lookandfeel) oggettoEntita;
		String sql = "UPDATE "+Lookandfeel.NOME_TABELLA+ " SET " +
																  Lookandfeel.ID+" = "+look.getidLook()+", "+
															      Lookandfeel.NOME+" = "+look.getnome()+", "+
															  Lookandfeel.VALORE+" = "+look.getvalore()+", "+
															    Lookandfeel.USATO+" = "+look.getusato()
		+" WHERE "+ Lookandfeel.ID +" = "+look.getidLook();
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AbstractOggettoEntita getentitaPadre() {
		return lookandfeel;
	}

	@Override
	public int getidLook() {
		return lookandfeel.getidLook();
	}

	@Override
	public void setidLook(int idLook) {
		lookandfeel.setidLook(idLook);
	}

	@Override
	public String getnome() {
		return lookandfeel.getnome();
	}

	@Override
	public void setnome(String nome) {
		lookandfeel.setnome(nome);
	}

	@Override
	public int getusato() {
		return lookandfeel.getusato();
	}

	@Override
	public void setusato(int usato) {
		lookandfeel.setusato(usato);
	}

	@Override
	public String getvalore() {
		return lookandfeel.getvalore();
	}

	@Override
	public void setvalore(String valore) {
		lookandfeel.setvalore(valore);
	}
	@Override
	public void notifyObservers() {
		super.notifyObservers();
	}
	
	@Override
	protected synchronized void setChanged() {
		super.setChanged();
	}

}
