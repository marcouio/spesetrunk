package com.molinari.gestionespese.business.generatori;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.utility.graphic.component.table.TableModel;
import com.molinari.utility.messages.I18NManager;

public class TableModelUscite extends TableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Map<Integer, String> mapMesi = new HashMap<>();

	
			private List<ICatSpese> categorie;

	public TableModelUscite(Object parametro) {
		super(parametro);
	}


	@Override
	protected void preBuild(Object parametro) {
		
		initMapMesi();
		
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


	protected static void initMapMesi() {
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

	private void aggiungiNomiColonne(){

		addColumn(I18NManager.getSingleton().getMessaggio("months"));
		for (int i = 0; i < getCategorie().size(); i++) {
			addColumn(getCategorie().get(i).getnome());
		}
	}

	public List<ICatSpese> getCategorie() {
		if(categorie == null){
			categorie = CacheCategorie.getSingleton().getVettoreCategorie();
		}
		return categorie;
	}


	public static Map<Integer, String> getMapmesi() {
		if(mapMesi.isEmpty()) {
			initMapMesi();
		}
		return mapMesi;
	}

}
