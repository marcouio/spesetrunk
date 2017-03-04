package com.molinari.gestionespese.domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import com.molinari.gestionespese.domain.ILookandfeel;
import com.molinari.gestionespese.domain.Lookandfeel;

import command.javabeancommand.AbstractOggettoEntita;
import controller.ControlloreBase;
import db.Clausola;
import db.ConnectionPool;
import db.ExecuteResultSet;
import db.dao.IDAO;

public class WrapLookAndFeel extends Observable implements IDAO<Lookandfeel>, ILookandfeel {

	private final Lookandfeel lookandfeel;
	private WrapBase base = new WrapBase();;

	public WrapLookAndFeel() {
		lookandfeel = new Lookandfeel();
	}

	@Override
	public Lookandfeel selectById(int id) {

		final String sql = "SELECT * FROM " + Lookandfeel.NOME_TABELLA + " WHERE " + Lookandfeel.ID + " = " + id;

		final Lookandfeel look = new Lookandfeel();;

		try {

			new ExecuteResultSet<Lookandfeel>() {

				@Override
				protected Lookandfeel doWithResultSet(ResultSet rs) throws SQLException {

					if (rs.next()) {
						look.setidLook(rs.getInt(1));
						look.setnome(rs.getString(2));
						look.setvalore(rs.getString(3));
						look.setusato(rs.getInt(4));
					}
					return look;
				}

			}.execute(sql);



		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return look;

	}

	@Override
	public List<Lookandfeel> selectAll() {
		final List<Lookandfeel> looks = new ArrayList<>();

		final String sql = "SELECT * FROM " + Lookandfeel.NOME_TABELLA;
		try {

			new ExecuteResultSet<List<Lookandfeel>>() {

				@Override
				protected List<Lookandfeel> doWithResultSet(ResultSet rs) throws SQLException {
					while (rs != null && rs.next()) {
						final Lookandfeel look = new Lookandfeel();
						look.setidLook(rs.getInt(1));
						look.setnome(rs.getString(2));
						look.setvalore(rs.getString(3));
						look.setusato(rs.getInt(4));
						looks.add(look);
					}
					return looks;
				}

			}.execute(sql);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return looks;
	}

	@Override
	public boolean insert(Lookandfeel oggettoEntita) {
		boolean ok = false;
		final Connection cn = ConnectionPool.getSingleton().getConnection();
		String sql = "";
		try {
			final Lookandfeel look = (Lookandfeel) oggettoEntita;

			sql = "INSERT INTO " + Lookandfeel.NOME_TABELLA + " (" + Lookandfeel.COL_NOME + ", " + Lookandfeel.COL_VALORE
					+ ", " + Lookandfeel.COL_USATO + ") VALUES (?,?,?)";
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
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		}
		return ok;
	}

	@Override
	public boolean delete(int id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update(Lookandfeel oggettoEntita) {
		boolean ok = false;


		final Lookandfeel look = (Lookandfeel) oggettoEntita;
		final String sql = "UPDATE " + Lookandfeel.NOME_TABELLA + " SET " + Lookandfeel.ID + " = " + look.getidLook() + ", "
				+ Lookandfeel.COL_NOME + " = " + look.getnome() + ", " + Lookandfeel.COL_VALORE + " = " + look.getvalore()
				+ ", " + Lookandfeel.COL_USATO + " = " + look.getusato() + " WHERE " + Lookandfeel.ID + " = "
				+ look.getidLook();
		return base .executeUpdate(sql);


	}

	@Override
	public boolean deleteAll() {
		throw new UnsupportedOperationException();
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
	public Lookandfeel getEntitaPadre()  {
		return lookandfeel;
	}

	@Override
	public List<Lookandfeel> selectWhere(List<Clausola> clausole, String appentoToQuery)  {
		throw new UnsupportedOperationException();
	}

}
