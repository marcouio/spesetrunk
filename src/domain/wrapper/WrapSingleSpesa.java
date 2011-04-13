package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import view.impostazioni.Impostazioni;
import business.Controllore;
import business.DBUtil;
import business.cache.CacheCategorie;
import business.cache.CacheUscite;
import business.cache.CacheUtenti;
import domain.AbstractOggettoEntita;
import domain.CatSpese;
import domain.Entrate;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.Utenti;

public class WrapSingleSpesa extends Observable implements IWrapperEntity, ISingleSpesa{

	private SingleSpesa uscita;
	private static final long serialVersionUID = 1L;

	public WrapSingleSpesa() {
		uscita = new SingleSpesa();
	}
	
	@Override
	public Object selectById(int id) {
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM "+SingleSpesa.NOME_TABELLA+" WHERE "+SingleSpesa.ID+" = " +id;
		
		SingleSpesa uscita = null;
		
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				uscita = new SingleSpesa();
				CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(5)));
				Utenti utente = CacheUtenti.getSingleton().getUtente(Integer.toString(rs.getInt(7)));
				uscita.setidSpesa(rs.getInt(1));
				uscita.setData(rs.getString(2));
				uscita.setinEuro(rs.getDouble(3));
				uscita.setdescrizione(rs.getString(4));
				uscita.setCatSpese(categoria);
				uscita.setnome(rs.getString(6));
				uscita.setUtenti(utente);
				uscita.setDataIns(rs.getString(8));
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
		return uscita;
	}

	@Override
	public Iterator<Object> selectWhere(String where) {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector<Object> selectAllForUtente() {
		Vector<Object> uscite = new Vector<Object>();
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		Map<String, AbstractOggettoEntita> mappaCategorie = CacheCategorie.getSingleton().getAllCategorie();
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + SingleSpesa.NOME_TABELLA + " WHERE " +SingleSpesa.IDUTENTE+ " = " + utente.getidUtente();
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				CatSpese categoria = (CatSpese)mappaCategorie.get(Integer.toString(rs.getInt(5)));
				
				SingleSpesa uscita = new SingleSpesa();
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
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return uscite;

	}

	@Override
	public Vector<Object> selectAll() {
		Vector<Object> uscite = new Vector<Object>();
		Map<String, AbstractOggettoEntita> mappaUtenti =  CacheUtenti.getSingleton().getAllUtenti();
		Map<String, AbstractOggettoEntita> mappaCategorie = CacheCategorie.getSingleton().getAllCategorie();
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + SingleSpesa.NOME_TABELLA ;
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				Utenti utente = (Utenti)mappaUtenti.get(Integer.toString(rs.getInt(7)));
				CatSpese categoria = (CatSpese)mappaCategorie.get(Integer.toString(rs.getInt(5)));
				
				SingleSpesa uscita = new SingleSpesa();
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
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return uscite;

	}

	@Override
	public boolean insert(Object oggettoEntita) {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		String sql = "";
		try {
			SingleSpesa uscita = (SingleSpesa)oggettoEntita;
			
			sql="INSERT INTO " + SingleSpesa.NOME_TABELLA + " (" + SingleSpesa.DATA+", "+SingleSpesa.INEURO+", "
			+SingleSpesa.DESCRIZIONE+", "+SingleSpesa.IDCATEGORIE+", "+SingleSpesa.NOME+", "+SingleSpesa.IDUTENTE+", "+SingleSpesa.DATAINS+") VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps = cn.prepareStatement(sql);
			String data = uscita.getData();
			ps.setString(1, data);
			ps.setDouble(2, uscita.getinEuro());
			ps.setString(3, uscita.getdescrizione());
			if(uscita.getCatSpese()!=null)
				ps.setInt(4, uscita.getCatSpese().getidCategoria());
			ps.setString(5, uscita.getnome());
			if(uscita.getUtenti()!=null)
				ps.setInt(6, uscita.getUtenti().getidUtente());
			String dataIns = uscita.getDataIns();
			ps.setString(7, dataIns);
			ps.executeUpdate();
			ok = true;
			CacheUscite.getSingleton().getCache().put(Integer.toString(uscita.getidSpesa()), uscita);
			System.out.println("Stampata uscita: "+uscita);
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
		String sql = "DELETE FROM "+SingleSpesa.NOME_TABELLA+" WHERE "+SingleSpesa.ID+" = "+id;
		Connection cn = DBUtil.getConnection();
		
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok=true;
			CacheUscite.getSingleton().getCache().remove(id);
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
		
		SingleSpesa uscita = (SingleSpesa) oggettoEntita;
		String sql = "UPDATE "+SingleSpesa.NOME_TABELLA+ " SET " +SingleSpesa.DATA+ " = " +uscita.getData()+", "+SingleSpesa.INEURO+" = "
		+uscita.getinEuro()+", "+SingleSpesa.DESCRIZIONE+ " = " +uscita.getdescrizione()+", "+SingleSpesa.IDCATEGORIE+ " = " 
		+uscita.getCatSpese().getidCategoria()+SingleSpesa.NOME+ " = " + uscita.getnome()+SingleSpesa.IDUTENTE +" = "
		+uscita.getUtenti().getidUtente()+SingleSpesa.DATAINS+ " = " + uscita.getDataIns()+" WHERE "+ SingleSpesa.ID +" = "+uscita.getidSpesa();
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
		String sql = "DELETE FROM "+SingleSpesa.NOME_TABELLA;
		Connection cn = DBUtil.getConnection();
		
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok=true;
//			CacheUscite.getSingleton().getCache().reset();
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
	
	/**
	 * Permette di ottenere un vettore con un numero di entita' SingleSpesa 
	 * inserito come parametro
	 * 
	 * @param dieci
	 * @return Vector<SingleSpesa>
	 */
	public Vector<SingleSpesa> dieciUscite(int dieci) {
		Vector<SingleSpesa> sSpesa = null;
		
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if(utente!=null)
			idUtente=utente.getidUtente();
		
		String sql = "select * from "+SingleSpesa.NOME_TABELLA+" where "+SingleSpesa.DATA
				+" BETWEEN '"+Impostazioni.getAnno()+"/01/01'"+" AND '"+Impostazioni.getAnno()+"/12/31'"
				+" AND "+SingleSpesa.IDUTENTE+ " = " + idUtente+" ORDER BY "+SingleSpesa.ID+" desc limit 0,"+dieci;
		Connection cn = DBUtil.getConnection();

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			sSpesa = new Vector<SingleSpesa>();
			while (rs.next()) {
				CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(rs.getString(5));
				SingleSpesa ss = new SingleSpesa();
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
		} catch (SQLException e) {
//			TODO implementare log
//			log.severe("Impossibile caricare il record da single_spesa: "+e.getMessage());
			e.printStackTrace();
		}
		try {
			cn.close();
		} catch (SQLException e) {
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
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM "+ SingleSpesa.NOME_TABELLA +" WHERE "+Entrate.IDUTENTE+" = "+Controllore.getSingleton().getUtenteLogin().getidUtente()+" ORDER BY "+SingleSpesa.DATAINS +" DESC";

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				SingleSpesa ss = new SingleSpesa();
				ss = new SingleSpesa();
				ss.setidSpesa(rs.getInt(1));
				
				String sql2 = "DELETE FROM "+SingleSpesa.NOME_TABELLA+" WHERE "+SingleSpesa.ID+"=?";
				PreparedStatement ps = cn.prepareStatement(sql2);
				ps.setInt(1, ss.getidSpesa());
				ps.executeUpdate();
				ok=true;
			}else{
				JOptionPane.showMessageDialog(null,"Non ci sono uscite per l'utente loggato","Non ci siamo!",JOptionPane.ERROR_MESSAGE,new ImageIcon("imgUtil/index.jpeg"));
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

	@Override
	public String getData() {
		return uscita.getData();
	}

	@Override
	public void setData(String Data) {
		uscita.setData(Data);
	}

	@Override
	public String getdescrizione() {
		return uscita.getdescrizione();
	}

	@Override
	public void setdescrizione(String descrizione) {
		uscita.setdescrizione(descrizione);
	}

	@Override
	public int getidSpesa() {
		return uscita.getidSpesa();
	}

	@Override
	public void setidSpesa(int idSpesa) {
		uscita.setidSpesa(idSpesa);
	}
	
	@Override
	public double getinEuro() {
		return uscita.getinEuro();
	}

	@Override
	public void setinEuro(double d) {
		uscita.setinEuro(d);
	}

	@Override
	public String getnome() {
		return uscita.getnome();
	}

	@Override
	public void setnome(String nome) {
		uscita.setnome(nome);
	}

	@Override
	public CatSpese getCatSpese() {
		return uscita.getCatSpese();
	}

	@Override
	public void setCatSpese(CatSpese catSpese) {
		uscita.setCatSpese(catSpese);
	}

	@Override
	public Utenti getUtenti() {
		return uscita.getUtenti();
	}

	@Override
	public void setUtenti(Utenti utenti) {
		uscita.setUtenti(utenti);
	}

	@Override
	public void setDataIns(String dataIns) {
		uscita.setDataIns(dataIns);
	}

	@Override
	public String getDataIns() {
		return uscita.getDataIns();
	}

	@Override
	public AbstractOggettoEntita getentitaPadre() {
		return uscita;
	}


}
