package business.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import domain.AbstractOggettoEntita;
import domain.Gruppi;
import domain.wrapper.WrapGruppi;

public class CacheGruppi extends AbstractCacheBase{

	private static CacheGruppi singleton;
	
	private CacheGruppi(){
		cache = new HashMap<String, AbstractOggettoEntita>();
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
	return (Gruppi)cache.get(id);
	}
	
	public Gruppi getGruppoPerNome(String nome){
		Gruppi gruppo = (Gruppi) cache.get(nome); 
		if(gruppo == null){
			gruppo = caricaGruppoPerNome(nome);
			if(gruppo!=null){
				cache.put(Integer.toString(gruppo.getidGruppo()), gruppo);
			}
		}
	return (Gruppi)cache.get(Integer.toString(gruppo.getidGruppo()));
	}

	private Gruppi caricaGruppoPerNome(String nome) {
		return (Gruppi) new WrapGruppi().selectByNome(nome);
	}

	private Gruppi caricaGruppo(String id){
		return (Gruppi) new WrapGruppi().selectById(Integer.parseInt(id));
	}
	
	public Map<String, AbstractOggettoEntita> chargeAllGruppi(){
		Vector<Object>gruppi = gruppiDAO.selectAll();
		if(gruppi!=null && gruppi.size()>0){
			for(int i=0; i<gruppi.size();i++){
				Gruppi gruppo = (Gruppi) gruppi.get(i);
				int id = gruppo.getidGruppo();
				if(cache.get(id) == null){
					cache.put(Integer.toString(id), gruppo);
				}
			}
			caricata = true;
		}
		return cache;
	}
	
	public Map<String, AbstractOggettoEntita> getAllGruppi(){
		if(caricata)
			return cache;
		else
			return chargeAllGruppi();
	}
	
	public Vector<Gruppi>getVettoreGruppiSenzaZero(){
		Vector<Gruppi> gruppi = new Vector<Gruppi>();
		Map<String, AbstractOggettoEntita> mappa = this.getAllGruppi();
		Object[] lista = mappa.values().toArray();
		for(int i=0;i<lista.length;i++){
			Gruppi gruppo = (Gruppi) lista[i];
			if(gruppo != null || gruppo.getnome()!=null)
				if(!gruppo.getnome().equals("No Gruppo"))
					gruppi.add((Gruppi) lista[i]);
		}
		return gruppi;
	}
	
	public Vector<Gruppi>getVettoreGruppi(){
		Vector<Gruppi> gruppi = new Vector<Gruppi>();
		Map<String, AbstractOggettoEntita> mappa = this.getAllGruppi();
		Object[] lista = mappa.values().toArray();
		for(int i=lista.length-1;i>=0;i--){
			gruppi.add((Gruppi) lista[i]);
		}
		return gruppi;
	}



}
