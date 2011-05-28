package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import view.impostazioni.Impostazioni;

import business.Controllore;
import domain.AbstractOggettoEntita;
import domain.SingleSpesa;
import domain.Utenti;
import domain.wrapper.WrapSingleSpesa;

public class CacheUscite extends AbstractCacheBase{

	private static CacheUscite singleton;
	
	private CacheUscite(){
		cache = new HashMap<String, AbstractOggettoEntita>();
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
	return (SingleSpesa)cache.get(id);
	}
	
	private SingleSpesa caricaSingleSpesa(String id){
		return (SingleSpesa) new WrapSingleSpesa().selectById(Integer.parseInt(id));
	}
	
	private Map<String, AbstractOggettoEntita> chargeAllUscite(){
		Vector<Object>uscite =usciteDAO.selectAll();
		if(uscite!=null && uscite.size()>0){
			for(int i=0; i<uscite.size();i++){
				SingleSpesa uscita = (SingleSpesa) uscite.get(i);
				int id = uscita.getidSpesa();
				if(cache.get(id) == null){
					cache.put(Integer.toString(id), uscita);
				}
			}
			caricata = true;
		}else{
			cache=null;
		}
		return cache;
	}
	
	public Map<String, AbstractOggettoEntita> getAllUscite(){
		if(caricata)
			return cache;
		else
			return chargeAllUscite();
	}
	
	public ArrayList<SingleSpesa> getAllUsciteForUtente(){
		ArrayList<SingleSpesa> listaUscite = new ArrayList<SingleSpesa>(); 
		Map<String, AbstractOggettoEntita> mappa = getAllUscite();
		Iterator<String> chiavi = mappa.keySet().iterator();
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		if(mappa!=null && utente!=null){
			while(chiavi.hasNext()){
				SingleSpesa uscita = (SingleSpesa) mappa.get(chiavi.next());
				if(uscita!=null && uscita.getUtenti()!=null){
					if(uscita.getUtenti().getidUtente()==utente.getidUtente()){
						listaUscite.add(uscita);
					}
				}
			}
		}
		return listaUscite;
	}
	public ArrayList<SingleSpesa> getAllUsciteForUtenteEAnno(){
		ArrayList<SingleSpesa> listaUscite = new ArrayList<SingleSpesa>(); 
		Map<String, AbstractOggettoEntita> mappa = getAllUscite();
		Iterator<String> chiavi = mappa.keySet().iterator();
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		String annoDaText = Impostazioni.getSingleton().getAnnotextField().getText();
		
		if(mappa!=null && utente!=null){
			while(chiavi.hasNext()){
				SingleSpesa uscita = (SingleSpesa) mappa.get(chiavi.next());
				if(uscita!=null && uscita.getUtenti()!=null){
					String annoUscita = uscita.getData().substring(0, 4);
					if(uscita.getUtenti().getidUtente()==utente.getidUtente() && annoUscita.equals(annoDaText) ){
						listaUscite.add(uscita);
					}
				}
			}
		}
		return listaUscite;
	}
	public int getMaxId() {
		int maxId = 0;
		Map<String, AbstractOggettoEntita> mappa = getAllUscite();
		Iterator<String> chiavi = mappa.keySet().iterator();
		if(mappa!=null){
			while(chiavi.hasNext()){
				SingleSpesa uscita = (SingleSpesa) mappa.get(chiavi.next());
				if(uscita!=null){
					int idSpesa = uscita.getidSpesa();
					if(idSpesa>maxId){
						maxId = idSpesa;
					}
				}
			}
		}
		return maxId;
	}
	
}
