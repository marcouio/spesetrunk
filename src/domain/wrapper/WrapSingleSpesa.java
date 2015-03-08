package domain.wrapper;

import grafica.componenti.alert.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Vector;

import view.impostazioni.Impostazioni;
import business.AltreUtil;
import business.Controllore;
import business.DBUtil;
import business.cache.CacheCategorie;
import business.cache.CacheUscite;
import business.cache.CacheUtenti;
import command.javabeancommand.AbstractOggettoEntita;
import db.Clausola;
import db.dao.IDAO;
import domain.CatSpese;
import domain.Entrate;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.Utenti;

public class WrapSingleSpesa extends Observable implements IDAO, ISingleSpesa {

	private final SingleSpesa uscita;

	public WrapSingleSpesa() {
		uscita = new SingleSpesa();
	}

	@Override
	public Object selectById(final int id) {
		final Connection cn = DBUtil.getConnection();
		final String sql = "SELECT * FROM " + SingleSpesa.NOME_TABELLA + " WHERE " + SingleSpesa.ID + " = " + id;

		SingleSpesa uscita = null;

		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				uscita = new SingleSpesa();
				final CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(5)));
				final Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
				uscita.setidSpesa(rs.getInt(1));
				uscita.setData(rs.getString(2));
				uscita.setinEuro(rs.getDouble(3));
				uscita.setdescrizione(rs.getString(4));
				uscita.setCatSpese(categoria);
				uscita.setnome(rs.getString(6));
				uscita.setUtenti(utente);
				uscita.setDataIns(rs.getString(8));
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeConnection();
		}
		return uscita;
	}

	public Vector<Object> selectAllForUtente() {
		final Vector<Object> uscite = new Vector<Object>();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		final Map<String, AbstractOggettoEntita> mappaCategorie = CacheCategorie.getSingleton().getAllCategorie();
		final Connection cn = DBUtil.getConnection();
		final String sql = "SELECT * FROM " + SingleSpesa.NOME_TABELLA + " WHERE " + SingleSpesa.IDUTENTE + " = " + utente.getidUtente();
		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				final CatSpese categoria = (CatSpese) mappaCategorie.get(Integer.toString(rs.getInt(5)));

				final SingleSpesa uscita = new SingleSpesa();
				uscita.setidSpesa(rs.getInt(1));
				uscita.setData(rs.getString(2));
				uscita.setinEuro(rs.getDouble(3));
				uscita.setdescrizione(rs.getString(4));
				uscita.setCatSpese(categoria);
				uscita.setnome(rs.getString(6));
				uscita.setUtenti(utente);
				uscita.setDataIns(rs.getString(8));
				uscite.add(uscita);
			}

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}
		return uscite;

	}

	public static void main(final String[] args) {
		final WrapSingleSpesa wrap = new WrapSingleSpesa();
		wrap.selectAll();
	}

	@Override
	public Vector<Object> selectAll() {
		final Vector<Object> uscite = new Vector<Object>();
		final Map<String, AbstractOggettoEntita> mappaUtenti = CacheUtenti.getSingleton().getAllUtenti();
		final Map<String, AbstractOggettoEntita> mappaCategorie = CacheCategorie.getSingleton().getAllCategorie();
		final Connection cn = DBUtil.getConnection();
		final String sql = "SELECT * FROM " + SingleSpesa.NOME_TABELLA;
		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				final Utenti utente = (Utenti) mappaUtenti.get(Integer.toString(rs.getInt(7)));
				final CatSpese categoria = (CatSpese) mappaCategorie.get(Integer.toString(rs.getInt(5)));

				final SingleSpesa uscita = new SingleSpesa();
				uscita.setidSpesa(rs.getInt(1));
				uscita.setData(rs.getString(2));
				uscita.setinEuro(rs.getDouble(3));
				uscita.setdescrizione(rs.getString(4));
				uscita.setCatSpese(categoria);
				uscita.setnome(rs.getString(6));
				uscita.setUtenti(utente);
				uscita.setDataIns(rs.getString(8));
				uscite.add(uscita);
			}

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}
		return uscite;

	}

	@Override
	public boolean insert(final Object oggettoEntita) {
		boolean ok = false;
		final Connection cn = DBUtil.getConnection();
		String sql = "";
		try {
			final SingleSpesa uscita = (SingleSpesa) oggettoEntita;

			sql = "INSERT INTO " + SingleSpesa.NOME_TABELLA + " (" + SingleSpesa.DATA + ", " + SingleSpesa.INEURO + ", " + SingleSpesa.DESCRIZIONE + ", " + SingleSpesa.IDCATEGORIE
			+ ", " + SingleSpesa.NOME + ", " + SingleSpesa.IDUTENTE + ", " + SingleSpesa.DATAINS + ") VALUES (?,?,?,?,?,?,?)";
			final PreparedStatement ps = cn.prepareStatement(sql);
			final String data = uscita.getData();
			ps.setString(1, data);
			ps.setDouble(2, uscita.getinEuro());
			ps.setString(3, uscita.getdescrizione());
			if (uscita.getCatSpese() != null) {
				ps.setInt(4, uscita.getCatSpese().getidCategoria());
			}
			ps.setString(5, uscita.getnome());
			if (uscita.getUtenti() != null) {
				ps.setInt(6, uscita.getUtenti().getidUtente());
			}
			final String dataIns = uscita.getDataIns();
			ps.setString(7, dataIns);
			ps.executeUpdate();
			ok = true;
			CacheUscite.getSingleton().getCache().put(Integer.toString(uscita.getidSpesa()), uscita);
			System.out.println("Stampata uscita: " + uscita);
		} catch (final Exception e) {
			ok = false;
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeConnection();
		}
		return ok;
	}

	@Override
	public boolean delete(final int id) {
		boolean ok = false;
		final String sql = "DELETE FROM " + SingleSpesa.NOME_TABELLA + " WHERE " + SingleSpesa.ID + " = " + id;
		final Connection cn = DBUtil.getConnection();

		try {
			final Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok = true;
			CacheUscite.getSingleton().getCache().remove(id);
		} catch (final SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public boolean update(final Object oggettoEntita) {
		boolean ok = false;
		final Connection cn = DBUtil.getConnection();

		final SingleSpesa uscita = (SingleSpesa) oggettoEntita;
		final String sql = "UPDATE " + SingleSpesa.NOME_TABELLA + " SET " + SingleSpesa.DATA + " = '" + uscita.getData() + "', " + SingleSpesa.INEURO + " = " + uscita.getinEuro()
		+ ", " + SingleSpesa.DESCRIZIONE + " = '" + uscita.getdescrizione() + "', " + SingleSpesa.IDCATEGORIE + " = " + uscita.getCatSpese().getidCategoria() + ", "
		+ SingleSpesa.NOME + " = '" + uscita.getnome() + "', " + SingleSpesa.IDUTENTE + " = " + uscita.getUtenti().getidUtente() + ", " + SingleSpesa.DATAINS + " = '"
		+ uscita.getDataIns() + "' WHERE " + SingleSpesa.ID + " = " + uscita.getidSpesa();
		try {
			final Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok = true;

		} catch (final SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public boolean deleteAll() {
		boolean ok = false;
		final String sql = "DELETE FROM " + SingleSpesa.NOME_TABELLA;
		final Connection cn = DBUtil.getConnection();

		try {
			final Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok = true;
			// CacheUscite.getSingleton().getCache().reset();
		} catch (final SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	/**
	 * Permette di ottenere un vettore con un numero di entita' SingleSpesa
	 * inserito come parametro
	 * 
	 * @param dieci
	 * @return Vector<SingleSpesa>
	 */
	public Vector<SingleSpesa> movimentiUsciteFiltrate(final String dataDa, final String dataA, final String nome, final Double euro, final String catSpese) {
		Vector<SingleSpesa> sSpesa = null;

		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getidUtente();
		}

		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM " + SingleSpesa.NOME_TABELLA + " WHERE " + SingleSpesa.IDUTENTE + " = " + idUtente);
		if (AltreUtil.checkData(dataDa) && AltreUtil.checkData(dataA) && dataDa != null && dataA != null) {
			sql.append(" AND " + SingleSpesa.DATA + " BETWEEN '" + dataDa + "'" + " AND '" + dataA + "'");
		} else if (AltreUtil.checkData(dataDa) && dataDa != null) {
			sql.append(" AND " + SingleSpesa.DATA + " = '" + dataDa + "'");
		}
		if (nome != null) {
			sql.append(" AND " + SingleSpesa.NOME + " = '" + nome + "'");
		}
		if (euro != null) {
			sql.append(" AND " + SingleSpesa.INEURO + " = " + euro);
		}
		if (catSpese != null && Integer.parseInt(catSpese) != 0) {
			sql.append(" AND " + SingleSpesa.IDCATEGORIE + " = " + Integer.parseInt(catSpese));
		}
		final Connection cn = DBUtil.getConnection();

		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql.toString());
			sSpesa = new Vector<SingleSpesa>();
			while (rs.next()) {
				final CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(rs.getString(5));
				final SingleSpesa ss = new SingleSpesa();
				ss.setidSpesa(rs.getInt(1));
				ss.setData(rs.getString(2));
				ss.setinEuro(rs.getDouble(3));
				ss.setdescrizione(rs.getString(4));
				ss.setnome(rs.getString(6));
				ss.setCatSpese(categoria);
				ss.setDataIns(rs.getString(8));
				ss.setUtenti(utente);
				sSpesa.add(ss);
			}
		} catch (final SQLException e) {
			Controllore.getLog().severe("Operazione non eseguita: " + e.getMessage());
			e.printStackTrace();
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return sSpesa;

	}

	/**
	 * Permette di ottenere un vettore con un numero di entita' SingleSpesa
	 * inserito come parametro
	 * 
	 * @param dieci
	 * @return Vector<SingleSpesa>
	 */
	public Vector<SingleSpesa> dieciUscite(final int dieci) {
		Vector<SingleSpesa> sSpesa = null;

		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getidUtente();
		}

		final String sql = "select * from " + SingleSpesa.NOME_TABELLA + " where " + SingleSpesa.DATA + " BETWEEN '" + Impostazioni.getAnno() + "/01/01'" + " AND '"
		+ Impostazioni.getAnno() + "/12/31'" + " AND " + SingleSpesa.IDUTENTE + " = " + idUtente + " ORDER BY " + SingleSpesa.ID + " desc limit 0," + dieci;
		final Connection cn = DBUtil.getConnection();

		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			sSpesa = new Vector<SingleSpesa>();
			while (rs.next()) {
				final CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(rs.getString(5));
				final SingleSpesa ss = new SingleSpesa();
				ss.setidSpesa(rs.getInt(1));
				ss.setData(rs.getString(2));
				ss.setinEuro(rs.getDouble(3));
				ss.setdescrizione(rs.getString(4));
				ss.setnome(rs.getString(6));
				ss.setCatSpese(categoria);
				ss.setDataIns(rs.getString(8));
				ss.setUtenti(utente);
				sSpesa.add(ss);
			}
		} catch (final SQLException e) {
			// TODO implementare log
			// log.severe("Impossibile caricare il record da single_spesa: "+e.getMessage());
			e.printStackTrace();
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return sSpesa;

	}

	/**
	 * Cancella l'ultima entita' "SingleSpesa" inserita
	 */
	public boolean deleteLastSpesa() {
		boolean ok = false;
		final Connection cn = DBUtil.getConnection();
		final String sql = "SELECT * FROM " + SingleSpesa.NOME_TABELLA + " WHERE " + Entrate.IDUTENTE + " = " + ((Utenti) Controllore.getSingleton().getUtenteLogin()).getidUtente()
		+ " ORDER BY " + SingleSpesa.DATAINS + " DESC";

		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				SingleSpesa ss = new SingleSpesa();
				ss = new SingleSpesa();
				ss.setidSpesa(rs.getInt(1));

				final String sql2 = "DELETE FROM " + SingleSpesa.NOME_TABELLA + " WHERE " + SingleSpesa.ID + "=?";
				final PreparedStatement ps = cn.prepareStatement(sql2);
				ps.setInt(1, ss.getidSpesa());
				ps.executeUpdate();
				ok = true;
			} else {
				Alert.segnalazioneErroreWarning("Non ci sono uscite per l'utente loggato");
			}

		} catch (final Exception e) {
			e.printStackTrace();
			Controllore.getLog().severe("Operazione non eseguita: " + e.getMessage());
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public String getData() {
		return uscita.getData();
	}

	@Override
	public void setData(final String Data) {
		uscita.setData(Data);
	}

	@Override
	public String getdescrizione() {
		return uscita.getdescrizione();
	}

	@Override
	public void setdescrizione(final String descrizione) {
		uscita.setdescrizione(descrizione);
	}

	@Override
	public int getidSpesa() {
		return uscita.getidSpesa();
	}

	@Override
	public void setidSpesa(final int idSpesa) {
		uscita.setidSpesa(idSpesa);
		uscita.setIdEntita(Integer.toString(idSpesa));
	}

	@Override
	public double getinEuro() {
		return uscita.getinEuro();
	}

	@Override
	public void setinEuro(final double d) {
		uscita.setinEuro(d);
	}

	@Override
	public String getnome() {
		return uscita.getnome();
	}

	@Override
	public void setnome(final String nome) {
		uscita.setnome(nome);
	}

	@Override
	public CatSpese getCatSpese() {
		return uscita.getCatSpese();
	}

	@Override
	public void setCatSpese(final CatSpese catSpese) {
		uscita.setCatSpese(catSpese);
	}

	@Override
	public Utenti getUtenti() {
		return uscita.getUtenti();
	}

	@Override
	public void setUtenti(final Utenti utenti) {
		uscita.setUtenti(utenti);
	}

	@Override
	public void setDataIns(final String dataIns) {
		uscita.setDataIns(dataIns);
	}

	@Override
	public String getDataIns() {
		return uscita.getDataIns();
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return uscita;
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

}
