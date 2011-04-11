package business.generatori;

import business.Database;

public class GeneratoreDatiTabellaEntrate extends AbstractGeneratoreDatiTabella {

	public GeneratoreDatiTabellaEntrate() {
		super();
	}

	@Override
	public Object getOggettoMatrice(int i, int x) {
		try {
			return Double.toString(Database.getSingleton().entrateMeseTipo((i+1), getNomiColonna()[x]));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String[] getNomiColonna() {
		String[] nomiColonne = {"Fisse","Variabili"};
		return nomiColonne;
	}

}
