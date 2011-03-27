package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import view.impostazioni.Impostazioni;
import business.Controllore;
import business.DBUtil;
import business.cache.CacheUtenti;
import domain.Entrate;
import domain.Utenti;

public class WrapEntrate extends Entrate implements IWrapperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM "+Entrate.NOME_TABELLA+" WHERE "+Entrate.ID+" = " +id;
		
		Entrate entrata = null;
		
		try {
			
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				entrata = new Entrate();
				Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
				entrata.setidEntrate(rs.getInt(1));
				entrata.setdescrizione(rs.getString(2));
				entrata.setFisseoVar(rs.getString(3));
				entrata.setinEuro(rs.getDouble(4));
				entrata.setdata(rs.getString(5));
				entrata.setnome(rs.getString(6));
				entrata.setUtenti(utente);
				entrata.setDataIns(rs.getString(8));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeConnection();
		}
		return entrata;

	}

	@Override
	public Iterator<Object> selectWhere(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector<Object> selectAllForUtente() {
		Vector<Object> entrate = new Vector<Object>();
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + NOME_TABELLA +" WHERE " + Entrate.IDUTENTE + " = " +utente.getidUtente();
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				
				Entrate entrata = new Entrate();
				entrata.setidEntrate(rs.getInt(1));
				entrata.setdescrizione(rs.getString(2));
				entrata.setFisseoVar(rs.getString(3));
				entrata.setinEuro(rs.getDouble(4));
				entrata.setdata(rs.getString(5));
				entrata.setnome(rs.getString(6));
				entrata.setUtenti(utente);
				entrata.setDataIns(rs.getString(8));
				entrate.add(entrata);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return entrate;
	}
	
	@Override
	public Vector<Object> selectAll() {
		Vector<Object> entrate = new Vector<Object>();
		Connection cn = DBUtil.getConnection();
		
		String sql = "SELECT * FROM " + NOME_TABELLA ;
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
				Entrate entrata = new Entrate();
				entrata.setidEntrate(rs.getInt(1));
				entrata.setdescrizione(rs.getString(2));
				entrata.setFisseoVar(rs.getString(3));
				entrata.setinEuro(rs.getDouble(4));
				entrata.setdata(rs.getString(5));
				entrata.setnome(rs.getString(6));
				entrata.setUtenti(utente);
				entrata.setDataIns(rs.getString(8));
				entrate.add(entrata);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return entrate;
	}

	@Override
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		String sql = "";
		try {
			Entrate entrata = (Entrate)oggettoEntita;
			
			sql="INSERT INTO " + Entrate.NOME_TABELLA + " (" + Entrate.DESCRIZIONE+", "+Entrate.FISSEOVAR+", "
			+Entrate.INEURO+", "+Entrate.DATA+", "+Entrate.NOME+", "+Entrate.IDUTENTE+", "+Entrate.DATAINS
			+") VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps = cn.prepareStatement(sql);
			//descrizione
			ps.setString(1, entrata.getdescrizione());
			//tipo
			ps.setString(2, entrata.getFisseoVar());
			//euro
			ps.setDouble(3, entrata.getinEuro());
			//data
			ps.setString(4, entrata.getdata());
			//nome
			ps.setString(5, entrata.getnome());
			//idutente
			ps.setInt(6, entrata.getUtenti().getidUtente());
			//datains
			ps.setString(7, entrata.getDataIns());
			
			ps.executeUpdate();
			ok = true;
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtil.closeConnection();
		}
		return ok;
	}

	@Override
	public boolean delete(int id) {
		boolean ok = false;
		String sql = "DELETE FROM "+Entrate.NOME_TABELLA+" WHERE "+Entrate.ID+" = "+id;
		Connection cn = DBUtil.getConnection();
		
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();		
		return ok;
	}

	@Override
	public boolean update(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		
		Entrate entrata = (Entrate) oggettoEntita;
		String sql = "UPDATE "+Entrate.NOME_TABELLA+ " SET " +Entrate.DESCRIZIONE+ " = " +entrata.getdescrizione()+", "+Entrate.FISSEOVAR+" = "
		+entrata.getFisseoVar()+", "+Entrate.INEURO+ " = " +entrata.getinEuro()+", "+Entrate.DATA+ " = " +entrata.getdata()
		+Entrate.NOME+ " = " + entrata.getnome()+Entrate.IDUTENTE +" = "+entrata.getUtenti().getidUtente()
		+Entrate.DATAINS+ " = " + entrata.getDataIns()+" WHERE "+ Entrate.ID +" = "+entrata.getidEntrate();
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public boolean deleteAll() {
		boolean ok = false;
		String sql = "DELETE FROM "+Entrate.NOME_TABELLA;
		Connection cn = DBUtil.getConnection();
		
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();		
		return ok;
	}
	
	// metodo che restituisce le ultime dieci entrate
	/**
	 * Permette di ottenere un vettore con un numero di entita' entrate 
	 * inserito come parametro
	 * 
	 * @param numEntry
	 * @return Vector<Entrate>
	 */
	public Vector<Entrate> dieciEntrate(int numEntry) {
		Vector<Entrate>entrate=null;
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if(utente!=null)
			idUtente=utente.getidUtente();
		
		//TODO verificare che impostazioni venga pescato correttamente (non passare dal controller?)
		String sql = "SELECT * FROM "+Entrate.NOME_TABELLA+" where "+Entrate.DATA
		+" BETWEEN '"+Impostazioni.getAnno()+"/01/01"+"'"+" AND '"+Impostazioni.getAnno()+"/12/31"+"'"
		+" AND "+Entrate.IDUTENTE+ " = " + idUtente +" ORDER BY "+Entrate.ID+" desc limit 0,"+numEntry;
		Connection cn = DBUtil.getConnection();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			entrate = new Vector<Entrate>();
				while (rs.next()) {
					Entrate e = new Entrate();
					e.setdata(rs.getString(5));
					e.setdescrizione(rs.getString(2));
					e.setFisseoVar(rs.getString(3));
					e.setidEntrate(rs.getInt(1));
					e.setinEuro(rs.getDouble(4));
					e.setnome(rs.getString(6));
					e.setUtenti(utente);
					e.setDataIns(rs.getString(8));
					entrate.add(e);
				}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return entrate;

	}
	
	/**
	 * Cancella l'ultima entita' "Entrate" inserita
	 */
	public boolean DeleteLastEntrate() {
		boolean ok= false;
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM "+ Entrate.NOME_TABELLA + " WHERE "+ Entrate.IDUTENTE + " = "+ Controllore.getSingleton().getUtenteLogin().getidUtente() 
		+" ORDER BY "+ Entrate.DATAINS +" DESC";

		try {
			//TODO inserire controllo: si puo' cancellare l'ultima spesa solo se l'ha inserita lo stesso utente che la cancella
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				String sql2 = "DELETE FROM "+Entrate.NOME_TABELLA+" WHERE "+Entrate.ID+"=?";
				PreparedStatement ps = cn.prepareStatement(sql2);
				ps.setInt(1, rs.getInt(1));
				ps.executeUpdate();
				ok = true;

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//TODO gestire log
//			log.severe("Operazione SQL di delete 'SingleSpesa' non eseguita:"+e.getMessage());
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

}
