package com.molinari.gestionespese.domain.wrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import com.molinari.gestionespese.domain.ILookandfeel;
import com.molinari.gestionespese.domain.Lookandfeel;

import controller.ControlloreBase;
import db.Clausola;
import db.ExecutePreparedStatement;
import db.ExecuteResultSet;
import db.dao.IDAO;

public class WrapLookAndFeel extends Observable implements IDAO<Lookandfeel>, ILookandfeel {

	private final Lookandfeel lookandfeel;
	private WrapBase base = new WrapBase();

	public WrapLookAndFeel() {
		lookandfeel = new Lookandfeel();
	}

	@Override
	public Lookandfeel selectById(int id) {

		final String sql = "SELECT * FROM " + Lookandfeel.NOME_TABELLA + " WHERE " + Lookandfeel.ID + " = " + id;

		final Lookandfeel look = new Lookandfeel();

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
		String sql = getQueryInsert();

		return new ExecutePreparedStatement<Lookandfeel>(){

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, Lookandfeel obj) throws SQLException {
				// nome
				ps.setString(1, oggettoEntita.getnome());
				// valore
				ps.setString(2, oggettoEntita.getvalore());
				// usato
				ps.setDouble(3, oggettoEntita.getusato());

			}

		}.executeUpdate(sql, oggettoEntita);

	}

	private String getQueryInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(Lookandfeel.NOME_TABELLA);
		sb.append(" (");
		sb.append(Lookandfeel.COL_NOME);
		sb.append(", ");
		sb.append(Lookandfeel.COL_VALORE);
		sb.append(", ");
		sb.append(Lookandfeel.COL_USATO);
		sb.append(") VALUES (?,?,?)");
		return sb.toString();
	}

	@Override
	public boolean delete(int id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update(Lookandfeel oggettoEntita) {

		final Lookandfeel look = (Lookandfeel) oggettoEntita;
		final String sql = getQueryUpdate(look);
		return base .executeUpdate(sql);


	}

	private String getQueryUpdate(final Lookandfeel look) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(Lookandfeel.NOME_TABELLA);
		sb.append(" SET ");
		sb.append(Lookandfeel.ID);
		sb.append(" = ");
		sb.append(look.getidLook());
		sb.append(", ");
		sb.append(Lookandfeel.COL_NOME);
		sb.append(" = ");
		sb.append(look.getnome());
		sb.append(", ");
		sb.append(Lookandfeel.COL_VALORE);
		sb.append(" = ");
		sb.append(look.getvalore());
		sb.append(", ");
		sb.append(Lookandfeel.COL_USATO);
		sb.append(" = ");
		sb.append(look.getusato());
		sb.append(" WHERE ");
		sb.append(Lookandfeel.ID);
		sb.append(" = ");
		sb.append(look.getidLook());
		return sb.toString();
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
