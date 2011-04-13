package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import view.impostazioni.Impostazioni;

import business.Controllore;
import domain.AbstractOggettoEntita;
import domain.Entrate;
import domain.Utenti;
import domain.wrapper.WrapEntrate;

public class CacheEntrate extends AbstractCacheBase{

	private static CacheEntrate singleton;
	
	private CacheEntrate(){
		cache = new HashMap<String, AbstractOggettoEntita>();
		caricata=false;
	}
	public static CacheEntrate getSingleton(){
		if (singleton == null) {
			synchronized (CacheEntrate.class) {
				if (singleton == null) {
					singleton = new CacheEntrate();
				}
			} // if
		} // if
		return singleton;
	}

	WrapEntrate entrateDAO = new WrapEntrate();
	
	public Entrate getEntrate(String id){
		Entrate entrate = (Entrate) cache.get(id); 
		if(entrate == null){
			entrate = caricaEntrate(id);
			if(entrate!=null){
				cache.put(id, entrate);
			}
		}
	return (Entrate)cache.get(id);
	}
	
	private Entrate caricaEntrate(String id){
		return (Entrate) new WrapEntrate().selectById(Integer.parseInt(id));
	}
	
	public Map<String, AbstractOggettoEntita> chargeAllEntrate(){
		Vector<Object> entrate = entrateDAO.selectAll();
		if(entrate!=null && entrate.size()>0){
			for(int i=0; i<entrate.size();i++){
				Entrate entrata = (Entrate) entrate.get(i);
				int id = entrata.getidEntrate();
				if(cache.get(id) == null){
					cache.put(Integer.toString(id), entrata);
				}
			}
		}
		caricata=true;
		return cache;
	}
	
	public Map<String, AbstractOggettoEntita> getAllEntrate(){
		if(caricata)
			return cache;
		else
			return chargeAllEntrate();
	}
	
	public ArrayList<Entrate> getAllEntrateForUtente(){
		ArrayList<Entrate> listaEntrate = new ArrayList<Entrate>(); 
		Map<String, AbstractOggettoEntita> mappa = getAllEntrate();
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		if(mappa!=null && utente!=null){
			Iterator<String> chiavi = mappa.keySet().iterator();
			
			while(chiavi.hasNext()){
				Entrate entrata =  (Entrate)mappa.get(chiavi.next());
				if(entrata!=null && (entrata.getUtenti()!=null || entrata.getUtenti().getidUtente()!=0)){
					if(entrata.getUtenti().getidUtente()==utente.getidUtente()){
						listaEntrate.add(entrata);
					}
				}
			}
		}
		return listaEntrate;
	}
	
	public ArrayList<Entrate> getAllEntrateForUtenteEAnno(){
		ArrayList<Entrate> listaEntrate = new ArrayList<Entrate>(); 
		Map<String, AbstractOggettoEntita> mappa = getAllEntrate();
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		String annoDaText = Impostazioni.getSingleton().getAnnotextField().getText();
		
		if(mappa!=null && utente!=null){
			Iterator<String> chiavi = mappa.keySet().iterator();
			
			while(chiavi.hasNext()){
				Entrate entrata =  (Entrate)mappa.get(chiavi.next());
				if(entrata!=null && (entrata.getUtenti()!=null || entrata.getUtenti().getidUtente()!=0)){
					String annoEntrata = entrata.getdata().substring(0, 4);
					if(entrata.getUtenti().getidUtente()==utente.getidUtente() && annoEntrata.equals(annoDaText)){
						listaEntrate.add(entrata);
					}
				}
			}
		}
		return listaEntrate;
	}

}
