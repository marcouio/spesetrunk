package com.molinari.gestionespese.business.generatori;

import java.util.List;
import java.util.Map;

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.utility.messages.I18NManager;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;

import com.molinari.utility.graphic.component.table.TableModel;

public class TableModelUsciteGruppi extends TableModel{

	private static final long serialVersionUID = 1L;
	List<IGruppi> gruppi = null;
	List<ICatSpese> catSpese = null;

	public TableModelUsciteGruppi(Object parametro) {
		super(parametro);
	}

	@Override
	protected void preBuild(Object parametro) {
		aggiungiNomiColonne();
		final Map<Integer, String> mapMesi = TableModelUscite.getMapmesi();
		for (int i = 1; i <= 12; i++) {
			final Riga riga = new Riga();
			riga.add(mapMesi.get(i));
			for (int x = 0; x < getGruppi().size(); x++) {
				final int idGruppo = Integer.parseInt(gruppi.get(x).getIdEntita());
				final String spesaMeseGruppo = Double.toString(Database.speseMeseGruppo(i, idGruppo));
				riga.add(spesaMeseGruppo);
			}
			for (int x = 0; x < getCategorie().size(); x++) {
				final int idCategoria = Integer.parseInt(catSpese.get(x).getIdEntita());
				final String spesaMeseCat = Double.toString(Database.speseMeseCategoria(i, idCategoria));
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

	public List<IGruppi> getGruppi() {
		if(gruppi == null){
			gruppi = CacheGruppi.getSingleton().getVettoreGruppiSenzaZero();
		}
		return gruppi;
	}

	public List<ICatSpese> getCategorie() {
		if(catSpese == null){
			catSpese = CacheCategorie.getSingleton().getCategorieSenzaGruppo();
		}
		return catSpese;
	}

}
