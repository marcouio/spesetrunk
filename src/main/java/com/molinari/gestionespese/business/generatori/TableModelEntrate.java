package com.molinari.gestionespese.business.generatori;

import java.util.HashMap;
import java.util.Map;

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;

import com.molinari.utility.graphic.component.table.TableModel;

public class TableModelEntrate extends TableModel{
	protected static final Map<Integer, String> mapMesi = new HashMap<>();

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

	private String[] listaColonne;
	private static final long serialVersionUID = 1L;

	public TableModelEntrate(Object parametro) {
		super(parametro);
	}


	@Override
	protected void preBuild(Object parametro) {
		final String[] listaColonneloc = getListaColonne();
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

}
