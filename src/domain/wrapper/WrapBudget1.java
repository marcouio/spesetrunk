package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import business.DBUtil;
import business.cache.CacheCategorie;
import domain.Budget;
import domain.CatSpese;

public class WrapBudget1 extends Budget implements IWrapperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM "+Budget.NOME_TABELLA+" WHERE "+Budget.ID+" = " +id;
		
		Budget budget = new Budget();
		
		try {
			
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				budget.setidBudget(rs.getInt(1));
				CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(2)));
				budget.setCatSpese(categoria);
				budget.setpercSulTot(rs.getDouble(3));
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
		return budget;

	}

	@Override
	public Iterator<Object> selectWhere(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Object> selectAll() {
		Vector<Object> budgets = new Vector<Object>();
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + Budget.NOME_TABELLA ;
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				Budget budget = new Budget();
				budget.setidBudget(rs.getInt(1));
				CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(2)));
				budget.setCatSpese(categoria);
				budget.setpercSulTot(rs.getDouble(3));
				budgets.add(budget);
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
		return budgets;
	}

	@Override
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		String sql = "";
		try {
			Budget budget = (Budget)oggettoEntita;
			
			sql="INSERT INTO " + Budget.NOME_TABELLA + " (" + Budget.IDCATEGORIE+", "+Budget.PERCSULTOT+") VALUES(?,?)";
			PreparedStatement ps = cn.prepareStatement(sql);
			if (budget.getCatSpese() != null)
				ps.setInt(1, budget.getCatSpese().getidCategoria());
			ps.setDouble(2, budget.getpercSulTot());			
			
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
		String sql = "DELETE FROM "+Budget.NOME_TABELLA+" WHERE "+Budget.ID+" = "+id;
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
		
		Budget budget = (Budget) oggettoEntita;
		String sql = "UPDATE "+Budget.NOME_TABELLA+ " SET " +Budget.IDCATEGORIE+ " = " +budget.getidCategorie()+", "+Budget.PERCSULTOT+" = "+budget.getpercSulTot()+
				" WHERE "+ Budget.ID +" = "+budget.getidBudget();
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
		String sql = "DELETE FROM "+Budget.NOME_TABELLA;
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
