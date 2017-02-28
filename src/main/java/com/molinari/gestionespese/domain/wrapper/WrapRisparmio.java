package com.molinari.gestionespese.domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import com.molinari.gestionespese.domain.IRisparmio;
import com.molinari.gestionespese.domain.Risparmio;

import command.javabeancommand.AbstractOggettoEntita;
import controller.ControlloreBase;
import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;

public class WrapRisparmio extends Observable implements IDAO,IRisparmio{

	private final Risparmio risparmio;

	public WrapRisparmio() {
		risparmio = new Risparmio();
	}

	@Override
	public Object selectById(int id) {

		final String sql = "SELECT * FROM " + Risparmio.NOME_TABELLA + " WHERE " + Risparmio.ID + "=" +id;
		final Risparmio risparmio = new Risparmio();
		try{

			ConnectionPool.getSingleton().new ExecuteResultSet<Object>() {

				@Override
				protected Object doWithResultSet(ResultSet rs) throws SQLException {

					if(rs != null && rs.next()){
						risparmio.setidRisparmio(rs.getInt(1));
						risparmio.setPerSulTotale(rs.getDouble(2));
					}

					return risparmio;
				}

			}.execute(sql);


		}catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		ConnectionPool.getSingleton().chiudiOggettiDb(null);

		return risparmio;
	}

	@Override
	public List<Object> selectAll() {
		final List<Object> risparmi = new ArrayList<>();

		final String sql = "SELECT * FROM " + Risparmio.NOME_TABELLA ;
		try{

			return ConnectionPool.getSingleton().new ExecuteResultSet<List<Object>>() {

				@Override
				protected List<Object> doWithResultSet(ResultSet rs) throws SQLException {
					final List<Object> risparmi = new ArrayList<>();

					while(rs.next()){
						final Risparmio risparmio = new Risparmio();
						risparmio.setidRisparmio(rs.getInt(1));
						risparmio.setPerSulTotale(rs.getDouble(2));
						risparmi.add(risparmio);
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
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		final Connection cn = ConnectionPool.getSingleton().getConnection();
		String sql = "";
		try {
			final Risparmio risparmio = (Risparmio)oggettoEntita;

			sql="INSERT INTO " + Risparmio.NOME_TABELLA + " (" +Risparmio.PERCSULTOT+") VALUES(?)";
			final PreparedStatement ps = cn.prepareStatement(sql);

			ps.setDouble(1, risparmio.getPerSulTotale());
			ps.executeUpdate();
			ok = true;
		} catch (final Exception e) {
			ok = false;
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		ConnectionPool.getSingleton().chiudiOggettiDb(cn);

		return ok;
	}

	@Override
	public boolean delete(int id) {
		boolean ok = false;
		final String sql = "DELETE FROM "+Risparmio.NOME_TABELLA+" WHERE "+Risparmio.ID+" = "+id;


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


		final Risparmio risparmio = (Risparmio) oggettoEntita;
		final String sql = "UPDATE "+Risparmio.NOME_TABELLA+ " SET " +Risparmio.PERCSULTOT+ " = " +risparmio.getPerSulTotale()
		+" WHERE "+ Risparmio.ID +" = "+risparmio.getidRisparmio();
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
		final String sql = "DELETE FROM "+Risparmio.NOME_TABELLA;


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
	public void setPerSulTotale(double PerSulTotale) {
		risparmio.setPerSulTotale(PerSulTotale);
	}

	@Override
	public String getnome() {
		return risparmio.getnome();
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

}
