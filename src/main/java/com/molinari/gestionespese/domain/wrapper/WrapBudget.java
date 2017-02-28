package com.molinari.gestionespese.domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.Budget;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.IBudget;

import command.javabeancommand.AbstractOggettoEntita;
import controller.ControlloreBase;
import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;

public class WrapBudget extends Observable implements IDAO, IBudget{


	Budget budget;

	public WrapBudget() {
		budget = new Budget();
	}

	@Override
	public Object selectById(int id) {
		final String sql = "SELECT * FROM "+Budget.NOME_TABELLA+" WHERE "+Budget.ID+" = " +id;

		final Budget budget = new Budget();

		try {

			return ConnectionPool.getSingleton().new ExecuteResultSet<Object>() {

				@Override
				protected Object doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						budget.setidBudget(rs.getInt(1));
						final CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(2)));
						budget.setCatSpese(categoria);
						budget.setpercSulTot(rs.getDouble(3));
					}
					return budget;
				}

			}.execute(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return budget;

	}

	@Override
	public List<Object> selectAll() {
		final String sql = "SELECT * FROM " + Budget.NOME_TABELLA ;
		try{

			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Object>>() {

				@Override
				protected List<Object> doWithResultSet(ResultSet rs) throws SQLException {
					final List<Object> budgets = new ArrayList<>();

					while(rs.next()){
						final Budget budget = new Budget();
						budget.setidBudget(rs.getInt(1));
						final CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(2)));
						budget.setCatSpese(categoria);
						budget.setpercSulTot(rs.getDouble(3));
						budgets.add(budget);
					}
					return budgets;
				}

			}.execute(sql);

		}catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	@Override
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		final Connection cn = ConnectionPool.getSingleton().getConnection();
		String sql = "";
		try {
			final Budget budget = (Budget)oggettoEntita;

			sql="INSERT INTO " + Budget.NOME_TABELLA + " (" + Budget.IDCATEGORIE+", "+Budget.PERCSULTOT+") VALUES(?,?)";
			final PreparedStatement ps = cn.prepareStatement(sql);
			if (budget.getCatSpese() != null) {
				ps.setInt(1, budget.getCatSpese().getidCategoria());
			}
			ps.setDouble(2, budget.getpercSulTot());

			ps.executeUpdate();
			ok = true;
		} catch (final Exception e) {
			ok = false;
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		} finally {
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		}
		return ok;
	}

	@Override
	public boolean delete(int id) {
		boolean ok = false;
		final String sql = "DELETE FROM "+Budget.NOME_TABELLA+" WHERE "+Budget.ID+" = "+id;

		try {
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok=true;

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok=false;
		}

		ConnectionPool.getSingleton().chiudiOggettiDb(null);

		return ok;
	}

	@Override
	public boolean update(Object oggettoEntita) {
		boolean ok = false;


		final Budget budget = (Budget) oggettoEntita;
		final String sql = "UPDATE "+Budget.NOME_TABELLA+ " SET " +Budget.IDCATEGORIE+ " = " +budget.getidCategorie()+", "+Budget.PERCSULTOT+" = "+budget.getpercSulTot()+
				" WHERE "+ Budget.ID +" = "+budget.getidBudget();
		try {
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok=true;

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok=false;
		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return ok;
	}

	@Override
	public boolean deleteAll() {
		boolean ok = false;
		final String sql = "DELETE FROM "+Budget.NOME_TABELLA;


		try {
			ConnectionPool.getSingleton().executeUpdate(sql);
			ok=true;

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
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
	public Object selectWhere(List<Clausola> clausole,
			String appentoToQuery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getnome() {
		return null;
	}

}
