package business.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import command.javabeancommand.AbstractOggettoEntita;

import domain.Lookandfeel;
import domain.wrapper.WrapLookAndFeel;

public class CacheLookAndFeel extends AbstractCacheBase{

	private static CacheLookAndFeel singleton;
	private WrapLookAndFeel lookDAO = new WrapLookAndFeel();
	
	private CacheLookAndFeel(){
		cache = new HashMap<String, AbstractOggettoEntita>();
	}
	public static CacheLookAndFeel getSingleton(){
		if (singleton == null) {
			synchronized (CacheLookAndFeel.class) {
				if (singleton == null) {
					singleton = new CacheLookAndFeel();
				}	
					
			} // if
		} // if
		return singleton;
	}
	
	public Map<String, AbstractOggettoEntita> chargeAllLook(){
		Vector<Object>looks = lookDAO.selectAll();
		if(looks!=null && looks.size()>0){
			for(int i=0; i<looks.size();i++){
				Lookandfeel lookFeel = (Lookandfeel) looks.get(i);
				int id = lookFeel.getidLook();
				if(cache.get(id) == null){
					cache.put(Integer.toString(id), lookFeel);
				}
			}
		}
		caricata = true;
		return cache;
	}
	
	public Map<String, AbstractOggettoEntita> getAllLooks(){
		if(caricata)
			return cache;
		else
			return chargeAllLook();
	}
	
	public Vector<Lookandfeel>getVettoreLooksPerCombo(){
		Vector<Lookandfeel> looks = new Vector<Lookandfeel>();
		Map<String, AbstractOggettoEntita> mappa = this.getAllLooks();
		Object[] lista = mappa.values().toArray();
		Lookandfeel look = new Lookandfeel();
		look.setnome("");
		looks.add(look);
		for(int i=0;i<lista.length;i++){
			looks.add((Lookandfeel) lista[i]);
		}
		return looks;
	}
}
