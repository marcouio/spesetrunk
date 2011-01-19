package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import business.Controllore;
import domain.Entrate;
import domain.Utenti;
import domain.wrapper.WrapEntrate;

public class CacheEntrate extends AbstractCacheBase{

	private boolean caricata;
	private static Map<String, Entrate> cache;
	private static CacheEntrate singleton;
	
	private CacheEntrate(){
		cache = new HashMap<String, Entrate>();
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
	return cache.get(id);
	}
	
	private Entrate caricaEntrate(String id){
		return (Entrate) new WrapEntrate().selectById(Integer.parseInt(id));
	}
	
	public Map<String, Entrate> chargeAllEntrate(){
		Vector<Object>entrate = entrateDAO.selectAll();
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
	
	public Map<String, Entrate> getAllEntrate(){
		if(caricata)
			return cache;
		else
			return chargeAllEntrate();
	}
	
	public ArrayList<Entrate> getAllEntrateForUtente(){
		ArrayList<Entrate> listaEntrate = new ArrayList<Entrate>(); 
		Map<String, Entrate> mappa = getAllEntrate();
		Iterator<String> chiavi = mappa.keySet().iterator();
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		if(mappa!=null && utente!=null){
			while(chiavi.hasNext()){
				Entrate entrata =  mappa.get(chiavi.next());
				if(entrata.getidUtente()==utente.getidUtente()){
					listaEntrate.add(entrata);
				}
			}
		}
		return listaEntrate;
	}
	
	public Map<String, Entrate> getCache() {
		return cache;
	}
	public void setCache(Map<String, Entrate> cache) {
		CacheEntrate.cache = cache;
	}

}
