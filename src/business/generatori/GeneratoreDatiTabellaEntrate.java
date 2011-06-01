package business.generatori;

import business.Database;

/**
 * A differenza da quanto dice il nome, la classe da la possibilità di creare
 * anche le relative tabelle, sfruttando il metodo del padre abstract
 * 
 * @author marco.molinari
 */
public class GeneratoreDatiTabellaEntrate extends AbstractGeneratoreTabella {

	public GeneratoreDatiTabellaEntrate() {
		super();
	}

	@Override
	public Object getOggettoMatrice(final int i, final int x) {
		try {
			return Double.toString(Database.getSingleton().entrateMeseTipo((i + 1), getNomiColonna()[x]));
		} catch (final NumberFormatException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String[] getNomiColonna() {
		final String[] nomiColonne = { "Mesi", "Fisse", "Variabili" };
		return nomiColonne;
	}

}
