package business.generatori;

import grafica.componenti.table.TableModel;

import java.util.HashMap;
import business.Database;
import business.internazionalizzazione.I18NManager;

public class TableModelEntrate extends TableModel{
	public static HashMap<Integer, String> mapMesi = new HashMap<Integer, String>();
	 
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

	String[] listaColonne;
	
	public TableModelEntrate(Object parametro) throws Exception {
		super(parametro);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void preBuild(Object parametro) throws Exception {
		aggiungiNomiColonne();
		for (int i = 1; i <= 12; i++) {
			Riga riga = new Riga();
			riga.add(mapMesi.get(i));
			for (int x = 0; x < getListaColonne().length; x++) {
				String entrataMeseTipo = Double.toString(Database.getSingleton().entrateMeseTipo((i), listaColonne[x]));
				riga.add(entrataMeseTipo);
			}
			addRiga(riga);
		}
	}
	
	private void aggiungiNomiColonne(){
		String mesi = I18NManager.getSingleton().getMessaggio("months");
		addColumn(mesi);
		for (int i = 0; i < getListaColonne().length; i++) {
			addColumn(getListaColonne()[i]);
		}
	}
	
	public String[] getListaColonne() {
		if(listaColonne == null){
			String fisse = I18NManager.getSingleton().getMessaggio("fixity");
			String variabili = I18NManager.getSingleton().getMessaggio("variables");
			listaColonne = new String[]{fisse, variabili};
		}
		return listaColonne;
	}

}
