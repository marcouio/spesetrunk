package com.molinari.gestionespese.domain.wrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import com.molinari.gestionespese.domain.IRisparmio;
import com.molinari.gestionespese.domain.Risparmio;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.Clausola;
import com.molinari.utility.database.ExecutePreparedStatement;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.database.dao.IDAO;

public class WrapRisparmio extends Observable implements IDAO<IRisparmio>,IRisparmio{

	private static final long serialVersionUID = 1L;
	private static final String WHERE = " WHERE ";
	private final IRisparmio risparmio;
	private WrapBase<Risparmio> base;

	public WrapRisparmio() {
		risparmio = new Risparmio();
		base = new WrapBase<Risparmio>((Risparmio) risparmio);
	}

	@Override
	public IRisparmio selectById(int id) {
		final IRisparmio risparmioLoc = new Risparmio();

		final String sql = "SELECT * FROM " + Risparmio.NOME_TABELLA + WHERE + Risparmio.ID + "=" +id;
		try{

			new ExecuteResultSet<IRisparmio>() {

				@Override
				protected IRisparmio doWithResultSet(ResultSet rs) throws SQLException {

					if(rs != null && rs.next()){
						risparmioLoc.setidRisparmio(rs.getInt(1));
						risparmioLoc.setPerSulTotale(rs.getDouble(2));
					}

					return risparmioLoc;
				}

			}.execute(sql);


		}catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		return risparmioLoc;
	}

	@Override
	public List<IRisparmio> selectAll() {
		final List<IRisparmio> risparmi = new ArrayList<>();

		final String sql = "SELECT * FROM " + Risparmio.NOME_TABELLA ;
		try{

			return new ExecuteResultSet<List<IRisparmio>>() {

				@Override
				protected List<IRisparmio> doWithResultSet(ResultSet rs) throws SQLException {
					final List<IRisparmio> risparmi = new ArrayList<>();

					while(rs.next()){
						final IRisparmio risparmioLoc = new Risparmio();
						risparmioLoc.setidRisparmio(rs.getInt(1));
						risparmioLoc.setPerSulTotale(rs.getDouble(2));
						risparmi.add(risparmioLoc);
					}

					return risparmi;
				}

			}.execute(sql);

		}catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return risparmi;

	}

	@Override
	public boolean insert(IRisparmio oggettoEntita) {
		String sql = "INSERT INTO " + Risparmio.NOME_TABELLA + " (" +Risparmio.COL_PERCSULTOT+") VALUES(?)";

		return new ExecutePreparedStatement<IRisparmio>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, IRisparmio obj) throws SQLException {
				ps.setDouble(1, risparmio.getPerSulTotale());
			}
		}.executeUpdate(sql, oggettoEntita);
	}

	@Override
	public boolean delete(int id) {
		final String sql = "DELETE FROM "+Risparmio.NOME_TABELLA+WHERE+Risparmio.ID+" = "+id;
		return base.executeUpdate(sql);
	}

	@Override
	public boolean update(IRisparmio oggettoEntita) {

		final IRisparmio risparmioLoc = oggettoEntita;
		final String sql = "UPDATE "+Risparmio.NOME_TABELLA+ " SET " +Risparmio.COL_PERCSULTOT+ " = " +risparmioLoc.getPerSulTotale()
		+WHERE+ Risparmio.ID +" = "+risparmioLoc.getidRisparmio();
		
		return base.executeUpdate(sql);
	}

	@Override
	public boolean deleteAll() {
		final String sql = "DELETE FROM "+Risparmio.NOME_TABELLA;

		return base.executeUpdate(sql);
	}

	@Override
	public IRisparmio getEntitaPadre() {
		return risparmio;
	}

	@Override
	public int getidRisparmio() {
		return risparmio.getidRisparmio();
	}

	@Override
	public void setidRisparmio(int idRisparmio) {
		risparmio.setidRisparmio(idRisparmio);
	}

	@Override
	public double getPerSulTotale() {
		return risparmio.getPerSulTotale();
	}

	@Override
	public void setPerSulTotale(double perSulTotale) {
		risparmio.setPerSulTotale(perSulTotale);
	}

	@Override
	public String getnome() {
		return risparmio.getnome();
	}

	@Override
	public List<IRisparmio> selectWhere(List<Clausola> clausole,
			String appentoToQuery) {
		throw new UnsupportedOperationException();
	}

}
