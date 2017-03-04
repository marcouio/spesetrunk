package com.molinari.gestionespese.business.generatori;

import java.util.HashMap;
import java.util.List;

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.domain.CatSpese;

import grafica.componenti.table.TableModel;

public class TableModelUscite extends TableModel{
	public static HashMap<Integer, String> mapMesi = new HashMap<>();

	static{
		mapMesi.put(1, I18NManager.getSingleton().getMessaggio("january"));
		mapMesi.put(2, I18NManager.getSingleton().getMessaggio("february"));
		mapMesi.put(3, I18NManager.getSingleton().getMessaggio("march"));
		mapMesi.put(4, I18NManager.getSingleton().getMessaggio("april"));
		mapMesi.put(5, I18NManager.getSingleton().getMessaggio("may"));
		mapMesi.put(6, I18NManager.getSingleton().getMessaggio("june"));
		mapMesi.put(7, I18NManager.getSingleton().getMessaggio("july"));
		mapMesi.put(8, I18NManager.getSingleton().getMessaggio("august"));
		mapMesi.put(9, I18NManager.getSingleton().getMessaggio("september"));
		mapMesi.put(10, I18NManager.getSingleton().getMessaggio("october"));
		mapMesi.put(11, I18NManager.getSingleton().getMessaggio("november"));
		mapMesi.put(12, I18NManager.getSingleton().getMessaggio("december"));
	}

	List<CatSpese> categorie;

	public TableModelUscite(Object parametro) throws Exception {
		super(parametro);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void preBuild(Object parametro) {
		aggiungiNomiColonne();
		for (int i = 1; i <= 12; i++) {
			final Riga riga = new Riga();
			riga.add(mapMesi.get(i));
			for (int x = 0; x < getCategorie().size(); x++) {
				final int idCategoria = Integer.parseInt(categorie.get(x).getIdEntita());
				final String spesaMeseCat = Double.toString(Database.speseMeseCategoria(i, idCategoria));
				riga.add(spesaMeseCat);
			}
			addRiga(riga);
		}
	}

	private void aggiungiNomiColonne(){

		addColumn(I18NManager.getSingleton().getMessaggio("months"));
		for (int i = 0; i < getCategorie().size(); i++) {
			addColumn(getCategorie().get(i).getnome());
		}
	}

	public List<CatSpese> getCategorie() {
		if(categorie == null){
			categorie = CacheCategorie.getSingleton().getVettoreCategorie();
		}
		return categorie;
	}

}
