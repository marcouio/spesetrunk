package prove;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import business.Controllore;
import business.cache.CacheBudget;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import domain.Budget;
import domain.CatSpese;
import domain.Gruppi;
import domain.SingleSpesa;
import domain.Utenti;
import domain.wrapper.WrapCatSpese;
import domain.wrapper.WrapGruppi;
import domain.wrapper.WrapSingleSpesa;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Controllore controllore = Controllore.getSingleton();
//		CacheCategorie cat = controllore.getCacheCategorie();
		CacheCategorie.getSingleton().getCatSpese("1");
		CatSpese categoria = CacheCategorie.getSingleton().getCatSpese("1");
		Gruppi gruppo = CacheGruppi.getSingleton().getGruppo("1");
		
		Utenti utente = new Utenti();
		utente.setCognome("cognome");
		utente.setNome("nome");
		utente.setpassword("pass");
		utente.setusername("user");
		
		SingleSpesa uscita = new SingleSpesa();
		uscita.setCatSpese(categoria);
		uscita.setData(new Date(12,12,2010));
		uscita.setDataIns(new Date(12,12,2010));
		uscita.setdescrizione("ciaociaco");
		uscita.setinEuro(20.23);
		uscita.setnome("ciaowww");	
		uscita.setUtenti(utente);
		
		new WrapSingleSpesa().insert(uscita);
	    Budget budget = CacheBudget.getSingleton().getBudget("1");
		System.out.println(gruppo.getdescrizione());
		System.out.println(categoria.getdescrizione());
		System.out.println(budget.getpercSulTot());
		Map<String, Budget> mappa = CacheBudget.getSingleton().chargeAllBudget();
		System.out.println(mappa);
	}

}
