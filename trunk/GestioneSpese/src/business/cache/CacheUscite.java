package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import business.Controllore;
import domain.SingleSpesa;
import domain.Utenti;
import domain.wrapper.WrapSingleSpesa;

public class CacheUscite extends AbstractCacheBase{

	private boolean caricata=false;
	private static CacheUscite singleton;
	private Map<String, SingleSpesa> cache;
	
	private CacheUscite(){
		cache = new HashMap<String, SingleSpesa>();
	}
	public static CacheUscite getSingleton(){
		if (singleton == null) {
			synchronized (CacheUscite.class) {
				if (singleton == null) {
					singleton = new CacheUscite();
				}
			} // if
		} // if
		return singleton;
	}

	WrapSingleSpesa usciteDAO = new WrapSingleSpesa();
	
	
	public SingleSpesa getSingleSpesa(String id){
		SingleSpesa uscita = (SingleSpesa) cache.get(id); 
		if(uscita == null){
			uscita = caricaSingleSpesa(id);
			if(uscita!=null){
				cache.put(id, uscita);
			}
		}
	return cache.get(id);
	}
	
	private SingleSpesa caricaSingleSpesa(String id){
		return (SingleSpesa) new WrapSingleSpesa().selectById(Integer.parseInt(id));
	}
	
	private Map<String, SingleSpesa> chargeAllUscite(){
		Vector<Object>uscite =usciteDAO.selectAll();
		if(uscite!=null && uscite.size()>0){
			for(int i=0; i<uscite.size();i++){
				SingleSpesa uscita = (SingleSpesa) uscite.get(i);
				int id = uscita.getidSpesa();
				if(cache.get(id) == null){
					cache.put(Integer.toString(id), uscita);
				}
			}
		}
		return cache;
	}
	
	public Map<String, SingleSpesa> getAllUscite(){
		if(caricata)
			return cache;
		else
			return chargeAllUscite();
	}
	
	public ArrayList<SingleSpesa> getAllUsciteForUtente(){
		ArrayList<SingleSpesa> listaUscite = new ArrayList<SingleSpesa>(); 
		Map<String, SingleSpesa> mappa = getAllUscite();
		Iterator<String> chiavi = mappa.keySet().iterator();
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		if(mappa!=null && utente!=null){
			while(chiavi.hasNext()){
				SingleSpesa uscita =  mappa.get(chiavi.next());
				if(uscita.getidUtente()==utente.getidUtente()){
					listaUscite.add(uscita);
				}
			}
		}
		return listaUscite;
	}
	
	public Map<String, SingleSpesa> getCache() {
		return cache;
	}
	public void setCache(Map<String, SingleSpesa> cache) {
		this.cache = cache;
	}
}
