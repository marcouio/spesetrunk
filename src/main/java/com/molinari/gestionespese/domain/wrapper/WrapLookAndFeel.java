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
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.Clausola;
import com.molinari.utility.database.ExecutePreparedStatement;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.database.dao.IDAO;

public class WrapLookAndFeel extends Observable implements IDAO<ILookandfeel>, ILookandfeel {

	private static final long serialVersionUID = 1L;
	private final ILookandfeel lookandfeel;
	private WrapBase<Lookandfeel> base;

	public WrapLookAndFeel() {
		lookandfeel = new Lookandfeel();
		base = new WrapBase<Lookandfeel>((Lookandfeel) lookandfeel);
	}

	@Override
	public ILookandfeel selectById(int id) {

		final String sql = "SELECT * FROM " + Lookandfeel.NOME_TABELLA + " WHERE " + Lookandfeel.ID + " = " + id;

		final ILookandfeel look = new Lookandfeel();

		try {

			new ExecuteResultSet<ILookandfeel>() {

				@Override
				protected ILookandfeel doWithResultSet(ResultSet rs) throws SQLException {

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
	public List<ILookandfeel> selectAll() {
		final List<ILookandfeel> looks = new ArrayList<>();

		final String sql = "SELECT * FROM " + Lookandfeel.NOME_TABELLA;
		try {

			new ExecuteResultSet<List<ILookandfeel>>() {

				@Override
				protected List<ILookandfeel> doWithResultSet(ResultSet rs) throws SQLException {
					while (rs != null && rs.next()) {
						final ILookandfeel look = new Lookandfeel();
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
	public boolean insert(ILookandfeel oggettoEntita) {
		String sql = getQueryInsert();

		return new ExecutePreparedStatement<ILookandfeel>(){

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, ILookandfeel obj) throws SQLException {
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
	public boolean update(ILookandfeel oggettoEntita) {
		final ILookandfeel look = oggettoEntita;
		final String sql = getQueryUpdate(look);
		return base .executeUpdate(sql);
	}

	private String getQueryUpdate(final ILookandfeel look) {
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
	public ILookandfeel getEntitaPadre()  {
		return lookandfeel;
	}

	@Override
	public List<ILookandfeel> selectWhere(List<Clausola> clausole, String appentoToQuery)  {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getidLook());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setidLook(Integer.parseInt(idEntita));
	}

	@Override
	public String getNome() {
		return getnome();
	}

}
