package business.generatori;

import java.util.HashMap;
import java.util.Vector;

import domain.CatSpese;
import domain.Gruppi;
import business.Database;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import business.internazionalizzazione.I18NManager;
import grafica.componenti.table.TableModel;
import grafica.componenti.table.TableModel.Riga;

public class TableModelUsciteGruppi extends TableModel{

	private static final long serialVersionUID = 1L;
	Vector<Gruppi> gruppi = null;
	Vector<CatSpese> catSpese = null;

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

public Vector<Gruppi> getGruppi() {
	if(gruppi == null){
		gruppi = CacheGruppi.getSingleton().getVettoreGruppiSenzaZero();
	}
	return gruppi;
}

public Vector<CatSpese> getCategorie() {
	if(catSpese == null){
		catSpese = CacheCategorie.getSingleton().getCategorieSenzaGruppo();
	}
	return catSpese;
}
	
}
