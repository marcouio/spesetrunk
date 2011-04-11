package business.generatori;

import java.util.Vector;

import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;

public class GeneratoreDatiTabellaUscite extends AbstractGeneratoreDatiTabella {

	Vector<CatSpese> categorie;
	
	public GeneratoreDatiTabellaUscite() {
		super();
	}

	@Override
	public Object getOggettoMatrice(int i, int x) {
		try {
			if(categorie == null)
				categorie = CacheCategorie.getSingleton().getVettoreCategorie();
			return Double.toString(Database.speseMeseCategoria(i+1, Integer.parseInt(categorie.get(x).getIdEntita())));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String[] getNomiColonna() {
		if(categorie == null)
			categorie = CacheCategorie.getSingleton().getVettoreCategorie();

		int numColonne = categorie.size();
        String[] nomiColonne = new String[numColonne];
        
        for(int i=0; i<categorie.size(); i++){
        	nomiColonne[i] = categorie.get(i).getnome(); 
        }
        return nomiColonne;
	}

}
