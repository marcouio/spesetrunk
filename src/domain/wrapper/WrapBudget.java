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
import business.cache.CacheCategorie;
import command.javabeancommand.AbstractOggettoEntita;
import db.Clausola;
import db.ConnectionPool;
import db.ConnectionPool.ExecuteResultSet;
import db.dao.IDAO;
import domain.Budget;
import domain.CatSpese;
import domain.IBudget;
import domain.Lookandfeel;

public class WrapBudget extends Observable implements IDAO, IBudget{


	Budget budget;
	
	public WrapBudget() {
		budget = new Budget();
	}

	@Override
	public Object selectById(int id) {
		String sql = "SELECT * FROM "+Budget.NOME_TABELLA+" WHERE "+Budget.ID+" = " +id;
		
		final Budget budget = new Budget();
		
		try {
			
			return new ConnectionPoolGGS().new ExecuteResultSet<Object>() {

				@Override
				protected Object doWithResultSet(ResultSet rs) throws SQLException {
					
					if (rs.next()) {
						budget.setidBudget(rs.getInt(1));
						CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(2)));
						budget.setCatSpese(categoria);
						budget.setpercSulTot(rs.getDouble(3));
					}
					return budget;
				}
				
			}.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return budget;

	}

	@Override
	public Vector<Object> selectAll() {
		String sql = "SELECT * FROM " + Budget.NOME_TABELLA ;
		try{
			
			return new ConnectionPoolGGS().new ExecuteResultSet<Vector<Object>>() {

				@Override
				protected Vector<Object> doWithResultSet(ResultSet rs) throws SQLException {
					Vector<Object> budgets = new Vector<Object>();
					
					while(rs.next()){
						Budget budget = new Budget();
						budget.setidBudget(rs.getInt(1));
						CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(2)));
						budget.setCatSpese(categoria);
						budget.setpercSulTot(rs.getDouble(3));
						budgets.add(budget);
					}
					return budgets;
				}
				
			}.execute(sql);
			
		}catch (Exception e) {
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
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		}
		return ok;
	}

	@Override
	public boolean delete(int id) {
		boolean ok = false;
		String sql = "DELETE FROM "+Budget.NOME_TABELLA+" WHERE "+Budget.ID+" = "+id;
		
		try {
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
		}
		
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		
		return ok;
	}

	@Override
	public boolean update(Object oggettoEntita) {
		boolean ok = false;
		
		
		Budget budget = (Budget) oggettoEntita;
		String sql = "UPDATE "+Budget.NOME_TABELLA+ " SET " +Budget.IDCATEGORIE+ " = " +budget.getidCategorie()+", "+Budget.PERCSULTOT+" = "+budget.getpercSulTot()+
				" WHERE "+ Budget.ID +" = "+budget.getidBudget();
		try {
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return ok;
	}

	@Override
	public boolean deleteAll() {
		boolean ok = false;
		String sql = "DELETE FROM "+Budget.NOME_TABELLA;
		
		
		try {
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return ok;
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return budget;
	}

	@Override
	public int getidBudget() {
		return budget.getidBudget();
	}

	@Override
	public void setidBudget(int idBudget) {
		budget.setidBudget(idBudget);
		
	}

	@Override
	public int getidCategorie() {
		return budget.getidCategorie();
	}

	@Override
	public void setidCategorie(int idCategorie) {
		budget.setidCategorie(idCategorie);		
	}

	@Override
	public double getpercSulTot() {
		return budget.getpercSulTot();
	}

	@Override
	public void setpercSulTot(double percSulTot) {
		budget.setpercSulTot(percSulTot);		
	}

	@Override
	public CatSpese getCatSpese() {
		return budget.getCatSpese();
	}

	@Override
	public void setCatSpese(CatSpese catSpese) {
		budget.setCatSpese(catSpese);		
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
	public Object selectWhere(ArrayList<Clausola> clausole,
			String appentoToQuery) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getnome() {
		return null;
	}

}
