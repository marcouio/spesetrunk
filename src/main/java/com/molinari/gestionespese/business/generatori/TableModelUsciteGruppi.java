package com.molinari.gestionespese.business.generatori;

import java.util.HashMap;
import java.util.List;

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Gruppi;

import grafica.componenti.table.TableModel;

public class TableModelUsciteGruppi extends TableModel{

	private static final long serialVersionUID = 1L;
	List<Gruppi> gruppi = null;
	List<CatSpese> catSpese = null;

	public TableModelUsciteGruppi(Object parametro) throws Exception {
		super(parametro);
	}

	@Override
	protected void preBuild(Object parametro) throws Exception {
		aggiungiNomiColonne();
		HashMap<Integer, String> mapMesi = TableModelUscite.mapMesi;
		for (int i = 1; i <= 12; i++) {
			Riga riga = new Riga();
			riga.add(mapMesi.get(i));
			for (int x = 0; x < getGruppi().size(); x++) {
				int idGruppo = Integer.parseInt(gruppi.get(x).getIdEntita());
				String spesaMeseGruppo = Double.toString(Database.speseMeseGruppo(i, idGruppo));
				riga.add(spesaMeseGruppo);
			}
			for (int x = 0; x < getCategorie().size(); x++) {
				int idCategoria = Integer.parseInt(catSpese.get(x).getIdEntita());
				String spesaMeseCat = Double.toString(Database.speseMeseCategoria(i, idCategoria));
				riga.add(spesaMeseCat);
			}
	
			addRiga(riga);
		}
		
	}
	
	private void aggiungiNomiColonne(){
		
		addColumn(I18NManager.getSingleton().getMessaggio("months"));
		for (int i = 0; i < getGruppi().size(); i++) {
			addColumn(getGruppi().get(i).getnome());
		}
		for (int i = 0; i < getCategorie().size(); i++) {
			addColumn(getCategorie().get(i).getnome());
		}
	}

public List<Gruppi> getGruppi() {
	if(gruppi == null){
		gruppi = CacheGruppi.getSingleton().getVettoreGruppiSenzaZero();
	}
	return gruppi;
}

public List<CatSpese> getCategorie() {
	if(catSpese == null){
		catSpese = CacheCategorie.getSingleton().getCategorieSenzaGruppo();
	}
	return catSpese;
}
	
}