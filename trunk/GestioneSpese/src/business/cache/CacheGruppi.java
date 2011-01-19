package business.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import domain.CatSpese;
import domain.Gruppi;
import domain.wrapper.WrapGruppi;

public class CacheGruppi extends AbstractCacheBase{

	private boolean caricata=false;
	private static CacheGruppi singleton;
	private static Map<String, Gruppi> cache;
	
	private CacheGruppi(){
		cache = new HashMap<String, Gruppi>();
	}
	
	public static CacheGruppi getSingleton(){
		if (singleton == null) {
			synchronized (CacheGruppi.class) {
				if (singleton == null) {
					singleton = new CacheGruppi();
				}
			} // if
		} // if
		return singleton;
	}

	WrapGruppi gruppiDAO = new WrapGruppi();
	
	
	public Gruppi getGruppo(String id){
		Gruppi gruppo = (Gruppi) cache.get(id); 
		if(gruppo == null){
			gruppo = caricaGruppo(id);
			if(gruppo!=null){
				cache.put(id, gruppo);
			}
		}
	return cache.get(id);
	}

	private Gruppi caricaGruppo(String id){
		return (Gruppi) new WrapGruppi().selectById(Integer.parseInt(id));
	}
	
	public Map<String, Gruppi> chargeAllGruppi(){
		Vector<Object>gruppi = gruppiDAO.selectAll();
		if(gruppi!=null && gruppi.size()>0){
			for(int i=0; i<gruppi.size();i++){
				Gruppi gruppo = (Gruppi) gruppi.get(i);
				int id = gruppo.getidGruppo();
				if(cache.get(id) == null){
					cache.put(Integer.toString(id), gruppo);
				}
			}
		}
		return cache;
	}
	
	public Map<String, Gruppi> getAllGruppi(){
		if(caricata)
			return cache;
		else
			return chargeAllGruppi();
	}
	
	public Vector<Gruppi>getVettoreGruppi(){
		Vector<Gruppi> gruppi = new Vector<Gruppi>();
		Map<String, Gruppi> mappa = this.getAllGruppi();
		Object[] lista = mappa.values().toArray();
		for(int i=0;i<lista.length;i++){
			gruppi.add((Gruppi) lista[i]);
		}
		return gruppi;
	}

}
