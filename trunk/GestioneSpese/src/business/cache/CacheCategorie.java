package business.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import domain.CatSpese;
import domain.wrapper.WrapCatSpese;

public class CacheCategorie extends AbstractCacheBase{

	private boolean caricata=false;
	private static CacheCategorie singleton;
	private static Map<String, CatSpese> cache;
	
	private CacheCategorie(){
		cache = new HashMap<String, CatSpese>();
	}
	public static CacheCategorie getSingleton(){
		if (singleton == null) {
			synchronized (CacheCategorie.class) {
				if (singleton == null) {
					singleton = new CacheCategorie();
				}
			} // if
		} // if
		return singleton;
	}

	WrapCatSpese catSpeseDAO = new WrapCatSpese();
	
	
	
	public CatSpese getCatSpese(String id){
		CatSpese categoria = (CatSpese) cache.get(id); 
		if(categoria == null){
			categoria = caricaCategoria(id);
			if(categoria!=null){
				cache.put(id, categoria);
			}
		}
		return cache.get(id);
//	return categoria;
	}

	private CatSpese caricaCategoria(String id){
		CatSpese categorie = (CatSpese) new WrapCatSpese().selectById(Integer.parseInt(id));
		return categorie;
	}
	
	public Map<String, CatSpese> chargeAllCategorie(){
		Vector<Object>categorie = catSpeseDAO.selectAll();
		if(categorie!=null && categorie.size()>0){
			for(int i=0; i<categorie.size();i++){
				CatSpese categoria = (CatSpese) categorie.get(i);
				int id = categoria.getidCategoria();
				if(cache.get(id) == null){
					cache.put(Integer.toString(id), categoria);
				}
			}
		}
		caricata = true;
		return cache;
	}
	
	public Map<String, CatSpese> getAllCategorie(){
		if(caricata)
			return cache;
		else
			return chargeAllCategorie();
	}
	
	public Vector<CatSpese>getVettoreCategoriePerCombo(){
		Vector<CatSpese> categorie = new Vector<CatSpese>();
		Map<String, CatSpese> mappa = this.getAllCategorie();
		Object[] lista = mappa.values().toArray();
		categorie.add(null);
		for(int i=0;i<lista.length;i++){
			categorie.add((CatSpese) lista[i]);
		}
		return categorie;
	}
	
	public Vector<CatSpese>getVettoreCategorie(){
		Vector<CatSpese> categorie = new Vector<CatSpese>();
		Map<String, CatSpese> mappa = this.getAllCategorie();
		Object[] lista = mappa.values().toArray();
		for(int i=0;i<lista.length;i++){
			categorie.add((CatSpese) lista[i]);
		}
		return categorie;
	}
	
	public Object[] arrayCategorie(){
		Map<String, CatSpese> mappa = this.getAllCategorie();
		return(Object[]) mappa.values().toArray(); 
	}
	public boolean isCaricata() {
		return caricata;
	}
	public void setCaricata(boolean caricata) {
		this.caricata = caricata;
	}
}
