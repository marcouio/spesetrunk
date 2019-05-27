package com.molinari.gestionespese.domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.function.Function;
import java.util.logging.Level;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.Clausola;
import com.molinari.utility.database.ConnectionPool;
import com.molinari.utility.database.DeleteBase;
import com.molinari.utility.database.ExecutePreparedStatement;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.database.ExecuteResultSet.ElaborateResultSet;
import com.molinari.utility.database.Query;
import com.molinari.utility.database.dao.IDAO;
import com.molinari.utility.graphic.component.alert.Alert;

public class WrapSingleSpesa extends Observable implements IDAO<ISingleSpesa>, ISingleSpesa {

	private static final long serialVersionUID = 1L;
	private static final String AND = " AND ";
	private static final String DELETE_FROM = "DELETE FROM ";
	private static final String WHERE = " WHERE ";
	private static final String SELECT_FROM = "SELECT * FROM ";
	private final SingleSpesa uscita;
	private final WrapBase base = new WrapBase();

	public WrapSingleSpesa() {
		uscita = new SingleSpesa();
	}

	@Override
	public ISingleSpesa selectById(final int id) {

		final String sql = SELECT_FROM + SingleSpesa.NOME_TABELLA + WHERE + SingleSpesa.ID + " = " + id;

		try {
			return new ExecuteResultSet<ISingleSpesa>(){}.execute(rs -> {
				try {
					return fillSpesa(null, rs);
				} catch (SQLException e) {
					ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
				}
				return uscita;
			}, sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		
		return null;
	}

	public List<ISingleSpesa> selectAllForUtente() {
		
		final List<ISingleSpesa> uscite = new ArrayList<>();
		final IUtenti utente = (IUtenti) Controllore.getUtenteLogin();

		final String sql = SELECT_FROM + SingleSpesa.NOME_TABELLA + WHERE + SingleSpesa.COL_IDUTENTE + " = " + utente.getidUtente();
		
		try {
			return new ExecuteResultSet<ISingleSpesa>(){}.executeList(getSpeseFromResultSet(), sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		
		return uscite;

	}

	public static void main(final String[] args) {
		final WrapSingleSpesa wrap = new WrapSingleSpesa();
		wrap.selectAll();
	}

	@Override
	public List<ISingleSpesa> selectAll() {
		final List<ISingleSpesa> uscite = new ArrayList<>();

		final String sql = SELECT_FROM + SingleSpesa.NOME_TABELLA;
		
		try {
			return new ExecuteResultSet<ISingleSpesa>(){}.executeList(getSpeseFromResultSet(), sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		
		return uscite;

	}

	@Override
	public boolean insert(final ISingleSpesa oggettoEntita) {

		final String sql = "INSERT INTO " + SingleSpesa.NOME_TABELLA + " (" + SingleSpesa.COL_DATA + ", " + SingleSpesa.COL_INEURO + ", " + SingleSpesa.COL_DESCRIZIONE + ", " + SingleSpesa.COL_IDCATEGORIE
				+ ", " + SingleSpesa.COL_NOME + ", " + SingleSpesa.COL_IDUTENTE + ", " + SingleSpesa.COL_DATAINS + ") VALUES (?,?,?,?,?,?,?)";
		final ISingleSpesa uscitaLoc = oggettoEntita;
		final boolean inserted = new ExecutePreparedStatement<ISingleSpesa>() {

			@Override
			protected void doWithPreparedStatement(PreparedStatement ps, ISingleSpesa uscitaLoc) throws SQLException {

				final String data = uscitaLoc.getData();
				ps.setString(1, data);
				ps.setDouble(2, uscitaLoc.getinEuro());
				ps.setString(3, uscitaLoc.getdescrizione());
				if (uscitaLoc.getCatSpese() != null) {
					ps.setInt(4, uscitaLoc.getCatSpese().getidCategoria());
				}
				ps.setString(5, uscitaLoc.getNome());
				if (uscitaLoc.getUtenti() != null) {
					ps.setInt(6, uscitaLoc.getUtenti().getidUtente());
				}
				final String dataIns = uscitaLoc.getDataIns();
				ps.setString(7, dataIns);

			}
		}.executeUpdate(sql, uscitaLoc);

		if(inserted){
			CacheUscite.getSingleton().getCache().put(Integer.toString(uscitaLoc.getidSpesa()), uscitaLoc);
		}
		return inserted;
	}

	@Override
	public boolean delete(final int id) {
		String idStr = Integer.toString(id);
		boolean ok = false;


		try {
			
			DeleteBase deleteBase = new DeleteBase();
			deleteBase.setTabella(SingleSpesa.NOME_TABELLA);
			deleteBase.setClausole(Arrays.asList(new Clausola(null, SingleSpesa.ID, "=", idStr)));
			new Query().delete(deleteBase);
			
			ok = true;
			CacheUscite.getSingleton().getCache().remove(idStr);
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		return ok;
	}

	@Override
	public boolean update(final ISingleSpesa oggettoEntita) {

		final String sql = getUpdateQuery(oggettoEntita);
		
		return base.executeUpdate(sql);
	}

	private String getUpdateQuery(final ISingleSpesa oggettoEntita) {
		final ISingleSpesa uscitaLoc = oggettoEntita;
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(SingleSpesa.NOME_TABELLA);
		sb.append(" SET ");
		sb.append(SingleSpesa.COL_DATA);
		sb.append(" = '");
		sb.append(uscitaLoc.getData());
		sb.append("', ");
		sb.append(SingleSpesa.COL_INEURO);
		sb.append(" = ");
		sb.append(uscitaLoc.getinEuro());
		sb.append(", ");
		sb.append(SingleSpesa.COL_DESCRIZIONE);
		sb.append(" = '");
		sb.append(uscitaLoc.getdescrizione());
		sb.append("', ");
		sb.append(SingleSpesa.COL_IDCATEGORIE);
		sb.append(" = ");
		sb.append(uscitaLoc.getCatSpese().getidCategoria());
		sb.append(", ");
		sb.append(SingleSpesa.COL_NOME);
		sb.append(" = '");
		sb.append(uscitaLoc.getNome());
		sb.append("', ");
		sb.append(SingleSpesa.COL_IDUTENTE);
		sb.append(" = ");
		sb.append(uscitaLoc.getUtenti().getidUtente());
		sb.append(", ");
		sb.append(SingleSpesa.COL_DATAINS);
		sb.append(" = '");
		sb.append(uscitaLoc.getDataIns());
		sb.append("' WHERE ");
		sb.append(SingleSpesa.ID);
		sb.append(" = ");
		sb.append(uscitaLoc.getidSpesa());
		return sb.toString();
	}

	@Override
	public boolean deleteAll() {
		final String sql = DELETE_FROM + SingleSpesa.NOME_TABELLA;
		return base.executeUpdate(sql);
	}

	/**
	 * Permette di ottenere un vettore con un numero di entita' SingleSpesa
	 * inserito come parametro
	 *
	 * @param dieci
	 * @return List<SingleSpesa>
	 */
	public List<ISingleSpesa> movimentiUsciteFiltrate(final String dataDa, final String dataA, final String nome, final Double euro, final String catSpese) {

		final Utenti utente = (Utenti) Controllore.getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getidUtente();
		}

		final StringBuilder sql = getQueryMovUsciteFiltrate(dataDa, dataA, nome, euro, catSpese, idUtente);

		try {

			return new ExecuteResultSet<ISingleSpesa>(){}.executeList(getSpeseFromResultSet(), sql.toString());

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
			
		return new ArrayList<>();

	}

	private StringBuilder getQueryMovUsciteFiltrate(final String dataDa, final String dataA, final String nome,
			final Double euro, final String catSpese, int idUtente) {
		final StringBuilder sql = new StringBuilder();
		sql.append(SELECT_FROM + SingleSpesa.NOME_TABELLA + WHERE + SingleSpesa.COL_IDUTENTE + " = " + idUtente);
		if (AltreUtil.checkDate(dataDa) && AltreUtil.checkDate(dataA) && dataDa != null && dataA != null) {
			sql.append(AND + SingleSpesa.COL_DATA + " BETWEEN '" + dataDa + "'" + " AND '" + dataA + "'");
		} else if (AltreUtil.checkDate(dataDa) && dataDa != null) {
			sql.append(AND + SingleSpesa.COL_DATA + " = '" + dataDa + "'");
		}
		if (nome != null) {
			sql.append(AND + SingleSpesa.COL_NOME + " = '" + nome + "'");
		}
		if (euro != null) {
			sql.append(AND + SingleSpesa.COL_INEURO + " = " + euro);
		}
		if (catSpese != null && Integer.parseInt(catSpese) != 0) {
			sql.append(AND + SingleSpesa.COL_IDCATEGORIE + " = " + Integer.parseInt(catSpese));
		}
		return sql;
	}

	/**
	 * Permette di ottenere un vettore con un numero di entita' SingleSpesa
	 * inserito come parametro
	 *
	 * @param dieci
	 * @return List<SingleSpesa>
	 */
	public List<ISingleSpesa> dieciUscite(final int dieci) {

		final Utenti utente = (Utenti) Controllore.getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getidUtente();
		}

		final String sql = "select * from " + SingleSpesa.NOME_TABELLA + " where " + SingleSpesa.COL_DATA + " BETWEEN '" + Impostazioni.getAnno() + "/01/01'" + " AND '"
				+ Impostazioni.getAnno() + "/12/31'" + AND + SingleSpesa.COL_IDUTENTE + " = " + idUtente + " ORDER BY " + SingleSpesa.ID + " desc limit 0," + dieci;

		try {

			return new ExecuteResultSet<ISingleSpesa>(){}.executeList(getSpeseFromResultSet(), sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return new ArrayList<>();

	}
	
	private ElaborateResultSet<ISingleSpesa> getSpeseFromResultSet(){
		return rs -> {
			final List<ISingleSpesa> sSpesa = new ArrayList<>();
			try {
					while (rs != null && rs.next()) {
						IUtenti utenteToSet = CacheUtenti.getSingleton().getAllUtenti().get(Integer.toString(rs.getInt(8)));
						sSpesa.add(fillSpesa(utenteToSet, rs));
					}
				} catch (SQLException e) {
					ControlloreBase.getLog().log(Level.SEVERE, "Impossibile caricare il record da single_spesa: "+e.getMessage());
					ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
				}
			return sSpesa;
			
			
		};
	}

	/**
	 * Cancella l'ultima entita' "SingleSpesa" inserita
	 */
	public boolean deleteLastSpesa() {
		boolean ok = false;

		final String sql = "SELECT " + SingleSpesa.ID + " FROM " + SingleSpesa.NOME_TABELLA + WHERE + SingleSpesa.COL_IDUTENTE + " = " + ((Utenti) Controllore.getUtenteLogin()).getidUtente()
				+ " ORDER BY " + SingleSpesa.COL_DATAINS + " DESC LIMIT 1";
		
		
		
		try {
			
			Function<ResultSet, Integer> elabRS = rs -> {
				try {
					if(rs.next()) {
						return rs.getInt(1);
					}
				} catch (SQLException e) {
					ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
					ControlloreBase.getLog().severe("Operazione non eseguita: " + e.getMessage());
				}
				return -1;
			};
			
			Integer execute = new ExecuteResultSet<Integer>().execute(elabRS, sql);
			
			if(execute == -1) {
				Alert.segnalazioneErroreWarning("Non ci sono uscite per l'utente loggato");
				return false;
			}
		
			DeleteBase delBase = new DeleteBase();
			delBase.setTabella(SingleSpesa.NOME_TABELLA);
			delBase.setClausole(Arrays.asList(new Clausola(null, SingleSpesa.ID, "=", Integer.toString(execute))));
			return new Query().delete(delBase);
		
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ControlloreBase.getLog().severe("Operazione non eseguita: " + e.getMessage());
		}
		return ok;
	}

	@Override
	public String getData() {
		return uscita.getData();
	}

	@Override
	public void setData(final String data) {
		uscita.setData(data);
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
	public String getNome() {
		return uscita.getNome();
	}

	@Override
	public void setNome(final String nome) {
		uscita.setNome(nome);
	}

	@Override
	public ICatSpese getCatSpese() {
		return uscita.getCatSpese();
	}

	@Override
	public void setCatSpese(final ICatSpese catSpese) {
		uscita.setCatSpese(catSpese);
	}

	@Override
	public IUtenti getUtenti() {
		return uscita.getUtenti();
	}

	@Override
	public void setUtenti(final IUtenti utenti) {
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
	public SingleSpesa getEntitaPadre() {
		return uscita;
	}

	@Override
	public List<ISingleSpesa> selectWhere(List<Clausola> clausole,
			String appentoToQuery) {
		throw new UnsupportedOperationException();
	}

	private SingleSpesa fillSpesa(final IUtenti utente, ResultSet rs) throws SQLException {
		Map<String, ICatSpese> mappaCategorie = CacheCategorie.getSingleton().getCache();
		ICatSpese categoria = mappaCategorie.get(rs.getString(5));
		IUtenti utentiToSet = utente != null ? utente : CacheUtenti.getSingleton().getUtente(rs.getString(8));  
		final SingleSpesa ss = new SingleSpesa();
		ss.setidSpesa(rs.getInt(1));
		ss.setData(rs.getString(2));
		ss.setinEuro(rs.getDouble(3));
		ss.setdescrizione(rs.getString(4));
		ss.setNome(rs.getString(6));
		ss.setCatSpese(categoria);
		ss.setDataIns(rs.getString(7));
		ss.setUtenti(utentiToSet);
		return ss;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getidSpesa());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setidSpesa(Integer.parseInt(idEntita));
		
	}

}
