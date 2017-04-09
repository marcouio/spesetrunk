package com.molinari.gestionespese.business.generatori;

import java.util.Map;

import com.molinari.gestionespese.business.Database;
import com.molinari.utility.graphic.component.table.TableModel;
import com.molinari.utility.messages.I18NManager;

public class TableModelEntrate extends TableModel{
	
	private String[] listaColonne;
	private static final long serialVersionUID = 1L;

	public TableModelEntrate(Object parametro) {
		super(parametro);
	}


	@Override
	protected void preBuild(Object parametro) {
		
		final Map<Integer, String> mapMesi = TableModelUscite.getMapmesi();
		
		final String[] listaColonneloc = getListaColonneDb();
		aggiungiNomiColonne();
		for (int i = 1; i <= 12; i++) {
			final Riga riga = new Riga();
			riga.add(mapMesi.get(i));
			for (final String element : listaColonneloc) {
				final String entrataMeseTipo = Double.toString(Database.getSingleton().entrateMeseTipo(i, element));
				riga.add(entrataMeseTipo);
			}
			addRiga(riga);
		}
	}

	private void aggiungiNomiColonne(){
		final String mesi = I18NManager.getSingleton().getMessaggio("months");
		addColumn(mesi);
		for (int i = 0; i < getListaColonne().length; i++) {
			addColumn(getListaColonne()[i]);
		}
	}

	public String[] getListaColonne() {
		if(listaColonne == null){
			final String fisse = I18NManager.getSingleton().getMessaggio("fixity");
			final String variabili = I18NManager.getSingleton().getMessaggio("variables");
			listaColonne = new String[]{fisse, variabili};
		}
		return listaColonne;
	}
	public String[] getListaColonneDb() {
		if(listaColonne == null){
			final String fisse = "fisse";
			final String variabili = "variabili";
			listaColonne = new String[]{fisse, variabili};
		}
		return listaColonne;
	}

}
