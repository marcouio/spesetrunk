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

import controller.ControlloreBase;
import db.Clausola;
import db.ConnectionPool;
import db.ExecutePreparedStatement;
import db.ExecuteResultSet;
import db.dao.IDAO;

public class WrapRisparmio extends Observable implements IDAO<Risparmio>,IRisparmio{

	private static final String WHERE = " WHERE ";
	private final Risparmio risparmio;
	private WrapBase base = new WrapBase();

	public WrapRisparmio() {
		risparmio = new Risparmio();
	}

	@Override
	public Risparmio selectById(int id) {
		final Risparmio risparmioLoc = new Risparmio();

		final String sql = "SELECT * FROM " + Risparmio.NOME_TABELLA + WHERE + Risparmio.ID + "=" +id;
		try{

			new ExecuteResultSet<Risparmio>() {

				@Override
				protected Risparmio doWithResultSet(ResultSet rs) throws SQLException {

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

		ConnectionPool.getSingleton().chiudiOggettiDb(null);

		return risparmioLoc;
	}

	@Override
	public List<Risparmio> selectAll() {
		final List<Risparmio> risparmi = new ArrayList<>();

		final String sql = "SELECT * FROM " + Risparmio.NOME_TABELLA ;
		try{

			return new ExecuteResultSet<List<Risparmio>>() {

				@Override
				protected List<Risparmio> doWithResultSet(ResultSet rs) throws SQLException {
					final List<Risparmio> risparmi = new ArrayList<>();

					while(rs.next()){
						final Risparmio risparmioLoc = new Risparmio();
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
	public boolean insert(Risparmio oggettoEntita) {
		String sql = "INSERT INTO " + Risparmio.NOME_TABELLA + " (" +Risparmio.COL_PERCSULTOT+") VALUES(?)";

		return new ExecutePreparedStatement<Risparmio>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, Risparmio obj) throws SQLException {
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
	public boolean update(Risparmio oggettoEntita) {

		final Risparmio risparmioLoc = (Risparmio) oggettoEntita;
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
	public Risparmio getEntitaPadre() {
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
	public List<Risparmio> selectWhere(List<Clausola> clausole,
			String appentoToQuery) {
		throw new UnsupportedOperationException();
	}

}
