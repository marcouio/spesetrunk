package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;

import business.ConnectionPoolGGS;
import business.DBUtil;
import command.javabeancommand.AbstractOggettoEntita;
import db.Clausola;
import db.ConnectionPool;
import db.ConnectionPool.ExecuteResultSet;
import db.dao.IDAO;
import domain.ILookandfeel;
import domain.Lookandfeel;

public class WrapLookAndFeel extends Observable implements IDAO, ILookandfeel {

	private final Lookandfeel lookandfeel;

	public WrapLookAndFeel() {
		lookandfeel = new Lookandfeel();
	}

	@Override
	public Object selectById(int id) {
		
		String sql = "SELECT * FROM " + Lookandfeel.NOME_TABELLA + " WHERE " + Lookandfeel.ID + " = " + id;

		final Lookandfeel look = new Lookandfeel();;

		try {

			new ConnectionPoolGGS().new ExecuteResultSet<Object>() {

				@Override
				protected Object doWithResultSet(ResultSet rs) throws SQLException {
					
					if (rs.next()) {
						look.setidLook(rs.getInt(1));
						look.setnome(rs.getString(2));
						look.setvalore(rs.getString(3));
						look.setusato(rs.getInt(4));
					}
					return look;
				}
				
			}.execute(sql);
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return look;

	}

	@Override
	public Vector<Object> selectAll() {
		final Vector<Object> looks = new Vector<Object>();
		
		String sql = "SELECT * FROM " + Lookandfeel.NOME_TABELLA;
		try {
			
			new ConnectionPoolGGS().new ExecuteResultSet<Vector<Object>>() {

				@Override
				protected Vector<Object> doWithResultSet(ResultSet rs) throws SQLException {
					while (rs.next()) {
						Lookandfeel look = new Lookandfeel();
						look.setidLook(rs.getInt(1));
						look.setnome(rs.getString(2));
						look.setvalore(rs.getString(3));
						look.setusato(rs.getInt(4));
						looks.add(look);
					}
					return looks;
				}
				
			}.execute(sql);

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return looks;
	}

	@Override
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = ConnectionPool.getSingleton().getConnection();
		String sql = "";
		try {
			final Lookandfeel look = (Lookandfeel) oggettoEntita;

			sql = "INSERT INTO " + Lookandfeel.NOME_TABELLA + " (" + Lookandfeel.NOME + ", " + Lookandfeel.VALORE
					+ ", " + Lookandfeel.USATO + ") VALUES (?,?,?)";
			final PreparedStatement ps = cn.prepareStatement(sql);
			// nome
			ps.setString(1, look.getnome());
			// valore
			ps.setString(2, look.getvalore());
			// usato
			ps.setDouble(3, look.getusato());

			ps.executeUpdate();
			ok = true;
		} catch (final Exception e) {
			ok = false;
			e.printStackTrace();
		} 
		return ok;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Object oggettoEntita) {
		boolean ok = false;
		

		Lookandfeel look = (Lookandfeel) oggettoEntita;
		String sql = "UPDATE " + Lookandfeel.NOME_TABELLA + " SET " + Lookandfeel.ID + " = " + look.getidLook() + ", "
				+ Lookandfeel.NOME + " = " + look.getnome() + ", " + Lookandfeel.VALORE + " = " + look.getvalore()
				+ ", " + Lookandfeel.USATO + " = " + look.getusato() + " WHERE " + Lookandfeel.ID + " = "
				+ look.getidLook();
		try {
			
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok = true;

		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		
		return ok;

	}

	@Override
	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return false;
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

	@Override
	public AbstractOggettoEntita getEntitaPadre() throws Exception {
		return lookandfeel;
	}

	@Override
	public Object selectWhere(ArrayList<Clausola> clausole,
			String appentoToQuery) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
