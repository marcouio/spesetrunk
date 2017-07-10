package com.molinari.gestionespese.business.generatori;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.utility.graphic.component.table.TableModel;
import com.molinari.utility.messages.I18NManager;

public class TableModelUsciteGruppi extends TableModel{

	private static final long serialVersionUID = 1L;
	private transient Map<Integer, List<IGruppi>> mapGprs;

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
				List<IGruppi> listGrps = getMapGprs().get(((IUtenti)Controllore.getUtenteLogin()).getidUtente());
				final int idGruppo = Integer.parseInt(listGrps.get(x).getIdEntita());
				final String spesaMeseGruppo = Double.toString(Database.speseMeseGruppo(i, idGruppo));
				riga.add(spesaMeseGruppo);
			}

			addRiga(riga);
		}

	}

	private void aggiungiNomiColonne(){

		addColumn(I18NManager.getSingleton().getMessaggio("months"));
		for (int i = 0; i < getGruppi().size(); i++) {
			addColumn(getGruppi().get(i).getnome());
		}
	}

	public List<IGruppi> getGruppi() {
		Integer idUte = ((IUtenti)Controllore.getUtenteLogin()).getidUtente();
		if(!getMapGprs().containsKey(idUte)){
			List<IGruppi> vettoreGruppiSenzaZero = CacheGruppi.getSingleton().getVettoreGruppiSenzaZero();
			getMapGprs().put(idUte, vettoreGruppiSenzaZero);
			
			return vettoreGruppiSenzaZero;
		}
		return getMapGprs().get(idUte);
	}

	public Map<Integer, List<IGruppi>> getMapGprs() {
		if (mapGprs == null) {
			mapGprs = new HashMap<>();
		}
		return mapGprs;
	}

	public void setMapGprs(Map<Integer, List<IGruppi>> mapGprs) {
		this.mapGprs = mapGprs;
	}
}
