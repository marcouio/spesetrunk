package business.generatori;

import java.util.Vector;

import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;

/**
 * A differenza da quanto dice il nome, la classe da la possibilità di creare
 * anche le relative tabelle, sfruttando il metodo del padre abstract
 * 
 * @author marco.molinari
 */
public class GeneratoreDatiTabellaUscite extends AbstractGeneratoreTabella {

	Vector<CatSpese> categorie;

	public GeneratoreDatiTabellaUscite() {
		super();
	}

	@Override
	public Object getOggettoMatrice(final int i, final int x) {
		try {
			if (categorie == null) {
				categorie = CacheCategorie.getSingleton().getVettoreCategorie();
			}
			return Double.toString(Database.speseMeseCategoria(i + 1, Integer.parseInt(categorie.get(x - 1).getIdEntita())));
		} catch (final NumberFormatException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String[] getNomiColonna() {
		if (categorie == null) {
			categorie = CacheCategorie.getSingleton().getVettoreCategorie();
		}

		final int numColonne = categorie.size() + 1;
		final String[] nomiColonne = new String[numColonne];
		nomiColonne[0] = "Mesi";
		for (int i = 0; i < categorie.size(); i++) {
			nomiColonne[i + 1] = categorie.get(i).getnome();
		}
		return nomiColonne;
	}
}
