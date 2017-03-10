package com.molinari.gestionespese.domain.wrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.Budget;
import com.molinari.gestionespese.domain.IBudget;
import com.molinari.gestionespese.domain.ICatSpese;

import controller.ControlloreBase;
import db.Clausola;
import db.ExecutePreparedStatement;
import db.ExecuteResultSet;
import db.dao.IDAO;

public class WrapBudget extends Observable implements IDAO<IBudget>, IBudget{

	private static final String WHERE = " WHERE ";
	private WrapBase base = new WrapBase();
	private IBudget budget;

	public WrapBudget() {
		budget = new Budget();
	}

	@Override
	public IBudget selectById(int id) {
		final String sql = getQuerySelectById(id);

		try {

			return new ExecuteResultSet<IBudget>() {

				@Override
				protected IBudget doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						final IBudget budgetLoc = new Budget();
						budgetLoc.setidBudget(rs.getInt(1));
						final ICatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(2)));
						budgetLoc.setCatSpese(categoria);
						budgetLoc.setpercSulTot(rs.getDouble(3));
					}
					return budget;
				}

			}.execute(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return budget;

	}

	private String getQuerySelectById(int id) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT * FROM ");
		stringBuilder.append(Budget.NOME_TABELLA);
		stringBuilder.append(WHERE);
		stringBuilder.append(Budget.ID);
		stringBuilder.append(" = ");
		stringBuilder.append(id);
		return stringBuilder.toString();
	}

	@Override
	public List<IBudget> selectAll() {
		final String sql = "SELECT * FROM " + Budget.NOME_TABELLA ;
		try{

			return new ExecuteResultSet<List<IBudget>>() {

				@Override
				protected List<IBudget> doWithResultSet(ResultSet rs) throws SQLException {
					final List<IBudget> budgets = new ArrayList<>();

					while(rs.next()){
						final IBudget budgetLoc = new Budget();
						budgetLoc.setidBudget(rs.getInt(1));
						final ICatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(2)));
						budgetLoc.setCatSpese(categoria);
						budgetLoc.setpercSulTot(rs.getDouble(3));
						budgets.add(budgetLoc);
					}
					return budgets;
				}

			}.execute(sql);

		}catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean insert(IBudget oggettoEntita) {

		final IBudget budgetLoc = (IBudget)oggettoEntita;

		String sql = "INSERT INTO " + Budget.NOME_TABELLA + " (" + Budget.COL_IDCATEGORIE+", "+Budget.COL_PERCSULTOT+") VALUES(?,?)";

		return new ExecutePreparedStatement<IBudget>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, IBudget obj) throws SQLException {
				if (obj.getCatSpese() != null) {
					ps.setInt(1, obj.getCatSpese().getidCategoria());
				}
				ps.setDouble(2, obj.getpercSulTot());

			}
		}.executeUpdate(sql, budgetLoc);


	}

	@Override
	public boolean delete(int id) {
		final String sql = "DELETE FROM "+Budget.NOME_TABELLA+WHERE+Budget.ID+" = "+id;

		return base.executeUpdate(sql);
	}

	@Override
	public boolean update(IBudget oggettoEntita) {

		final IBudget budgetLoc = (IBudget) oggettoEntita;
		final String sql = getQueryUpdate(budgetLoc);
		
		return base.executeUpdate(sql);
		
	}

	private String getQueryUpdate(final IBudget budgetLoc) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(Budget.NOME_TABELLA);
		sb.append(" SET ");
		sb.append(Budget.COL_IDCATEGORIE);
		sb.append(" = ");
		sb.append(budgetLoc.getidCategorie());
		sb.append(", ");
		sb.append(Budget.COL_PERCSULTOT);
		sb.append(" = ");
		sb.append(budgetLoc.getpercSulTot());
		sb.append(WHERE);
		sb.append(Budget.ID);
		sb.append(" = ");
		sb.append(budgetLoc.getidBudget());
		return sb.toString();
	}

	@Override
	public boolean deleteAll() {
		final String sql = "DELETE FROM "+Budget.NOME_TABELLA;
		return base.executeUpdate(sql);
	}

	@Override
	public IBudget getEntitaPadre() {
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
	public ICatSpese getCatSpese() {
		return budget.getCatSpese();
	}

	@Override
	public void setCatSpese(ICatSpese catSpese) {
		budget.setCatSpese(catSpese);
	}

	@Override
	public List<IBudget> selectWhere(List<Clausola> clausole,
			String appentoToQuery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getnome() {
		return null;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getidBudget());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setidBudget(Integer.parseInt(idEntita));
	}

	@Override
	public String getNome() {
		return getnome();
	}

}
