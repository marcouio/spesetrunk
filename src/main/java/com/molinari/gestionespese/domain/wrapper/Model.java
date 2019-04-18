package com.molinari.gestionespese.domain.wrapper;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.view.entrateuscite.AbstractEntrateView.INCOMETYPE;
import com.molinari.utility.controller.ControlloreBase;

public class Model {

	private WrapCatSpese modelCategorie;
	private WrapGruppi modelGruppi;
	private WrapBudget modelBudget;
	private WrapEntrate modelEntrate;
	private WrapRisparmio modelRisparmio;
	private WrapSingleSpesa modelUscita;
	private WrapUtenti modelUtenti;
	private WrapLookAndFeel modelLookAndFeel;
	private static Model singleton;
	private static String[][] primoUscite;
	private static String[] nomiColonneUscite;
	private static String[][] movimentiEntrate;
	private static String[][] movimentiUscite;

	private static List<ICatSpese> catSpese = CacheCategorie.getSingleton().getVettoreCategorie();

	private Model() {
		modelCategorie = new WrapCatSpese();
		modelGruppi = new WrapGruppi();
		modelBudget = new WrapBudget();
		modelEntrate = new WrapEntrate();
		modelRisparmio = new WrapRisparmio();
		modelUscita = new WrapSingleSpesa();
		modelUtenti = new WrapUtenti();
	}

	public static synchronized Model getSingleton() {
		if (singleton == null) {
			singleton = new Model();
		} // if
		return singleton;
	}

	public WrapCatSpese getModelCategorie() {
		return modelCategorie;
	}

	public void setModelCategorie(final WrapCatSpese modelCategorie) {
		this.modelCategorie = modelCategorie;
	}

	public WrapGruppi getModelGruppi() {
		return modelGruppi;
	}

	public void setModelGruppi(final WrapGruppi modelGruppi) {
		this.modelGruppi = modelGruppi;
	}

	public WrapBudget getModelBudget() {
		return modelBudget;
	}

	public void setModelBudget(final WrapBudget modelBudget) {
		this.modelBudget = modelBudget;
	}

	public WrapEntrate getModelEntrate() {
		return modelEntrate;
	}

	public void setModelEntrate(final WrapEntrate modelEntrate) {
		this.modelEntrate = modelEntrate;
	}

	public WrapRisparmio getModelRisparmio() {
		return modelRisparmio;
	}

	public void setModelRisparmio(final WrapRisparmio modelRisparmio) {
		this.modelRisparmio = modelRisparmio;
	}

	public WrapSingleSpesa getModelUscita() {
		return modelUscita;
	}

	public void setModelUscita(final WrapSingleSpesa modelUscita) {
		this.modelUscita = modelUscita;
	}

	public WrapUtenti getModelUtenti() {
		return modelUtenti;
	}

	public void setModelUtenti(final WrapUtenti modelUtenti) {
		this.modelUtenti = modelUtenti;
	}

	// *************************************TABELLA-USCITE******************************************

	private static String[][] setTabellaUscitaPrimo() {

		final int numColonne = catSpese.size();
		final String[] nomiColonne = new String[numColonne];

		for (int i = 0; i < catSpese.size(); i++) {
			nomiColonne[i] = catSpese.get(i).getnome();
		}

		primoUscite = new String[12][numColonne];

		for (int i = 0; i < 12; i++) {
			for (int x = 0; x < catSpese.size(); x++) {
				try {
					primoUscite[i][x] = Double
							.toString(Database.speseMeseCategoria(i + 1, catSpese.get(x).getidCategoria()));
				} catch (final Exception e) {
					ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
		return primoUscite;
	}

	private static String[] nomiColonneUscite() {

		final int numColonne = catSpese.size();
		nomiColonneUscite = new String[numColonne];

		for (int i = 0; i < catSpese.size(); i++) {
			nomiColonneUscite[i] = catSpese.get(i).getnome();
		}
		return nomiColonneUscite;
	}

	public static String[][] getPrimoUscite() {
		return setTabellaUscitaPrimo();
	}

	public static void setPrimoUscite(final String[][] primo) {
		Model.primoUscite = primo;
	}

	public static void setNomiColonneUscite(final String[] nomiColonneUscite) {
		Model.nomiColonneUscite = nomiColonneUscite;
	}

	public static String[] getNomiColonneUscite() {
		return nomiColonneUscite();
	}

	// *************************************TABELLA-ENTRATE******************************************

	private static INCOMETYPE[] nomiColonneEntrate() {
		return INCOMETYPE.values();
	}

	public static void setPrimoEntrate(final String[][] primo) {
		Model.primoUscite = primo;
	}

	public static INCOMETYPE[] getNomiColonneEntrate() {
		return nomiColonneEntrate();
	}

	// *************************************CATEGORIE-PERCOMBOBOX***********************************

	public ICatSpese[] getCategorieCombo(final boolean ricarica) {
		final CacheCategorie cache = CacheCategorie.getSingleton();
		cache.setCaricata(!ricarica);
		List<ICatSpese> listCategoriePerCombo = cache.getVettoreCategorie();
		return listCategoriePerCombo.toArray(new ICatSpese[listCategoriePerCombo.size()]);
	}

	// *************************************GRUPPI-PERCOMBOBOX***********************************

	private Map<String, IGruppi> getGruppiPerCombo(final boolean ricarica) {
		final CacheGruppi cache = CacheGruppi.getSingleton();
		cache.setCaricata(!ricarica);
		return cache.getAllGruppi();
	}

	public Object[] getGruppiCombo(final boolean ricarica) {
		final Map<String, IGruppi> gruppi = getGruppiPerCombo(ricarica);
		return gruppi.values().toArray();
	}

	// *************************************MOVIMENTI-ENTRATE***********************************

	/**
	 * Valorizza una matrice utile per i pannelli movimenti in entrata. crea il
	 * numero di righe specificato in parametro con le entita' della tabella
	 * passata in parametro
	 *
	 * }
	 *
	 * @param numEntry
	 * @param tabella
	 * @return String[][]
	 */
	public static String[][] movimentiEntrate(final int numEntry, final String tabella) {
		final List<Entrate> entry1 = Model.getSingleton().modelEntrate.dieciEntrate(numEntry);
		return movimentiFiltratiEntratePerNumero(tabella, entry1, numEntry);
	}

	/**
	 * Valorizza una matrice utile per i pannelli movimenti in entrata. crea il
	 * numero di righe specificato in parametro con le entita' della tabella
	 * passata in parametro
	 *
	 * }
	 *
	 * @param numEntry
	 * @param tabella
	 * @param numMovimenti
	 * @return String[][]
	 */
	public static String[][] movimentiFiltratiEntratePerNumero(final String tabella, final List<Entrate> entry1,
			int numMovimenti) {
		final List<String> nomi = Database.getSingleton().nomiColonne(tabella);

		final int numEntry = numMovimenti;

		if (!entry1.isEmpty()) {
			popolaArrayMovimentiEntrata(entry1, nomi, numEntry);
			if (entry1.size() < numEntry) {
				for (int y = entry1.size(); y < numEntry; y++) {
					riempiArrayMovEntrateConZeri(nomi, y);
				}
			}
		} else {
			movimentiEntrate = new String[numEntry][nomi.size()];
			for (int x = 0; x < numEntry; x++) {
				riempiArrayMovEntrateConZeri(nomi, x);
			}
		}
		return movimentiEntrate;
	}

	private static void riempiArrayMovEntrateConZeri(final List<String> nomi, int x) {
		for (int z = 0; z < nomi.size(); z++) {
			movimentiEntrate[x][z] = "0";
		}
	}

	private static void popolaArrayMovimentiEntrata(final List<Entrate> entry1, final List<String> nomi,
			final int numEntry) {
		movimentiEntrate = new String[numEntry][nomi.size()];
		for (int x = 0; x < entry1.size(); x++) {
			final Entrate entrate = entry1.get(x);
			movimentiEntrate[x][0] = entrate.getdata();
			movimentiEntrate[x][1] = entrate.getnome();
			movimentiEntrate[x][2] = entrate.getdescrizione();
			movimentiEntrate[x][3] = Double.toString(entrate.getinEuro());
			int fisseoVar = Integer.parseInt(entrate.getFisseoVar());
			INCOMETYPE[] values = INCOMETYPE.values();
			movimentiEntrate[x][4] = values[fisseoVar].toString();
			movimentiEntrate[x][5] = Integer.toString(entrate.getidEntrate());
			movimentiEntrate[x][6] = entrate.getDataIns();

		}
	}

	// *************************************MOVIMENTI-USCITE***********************************

	public static String[][] movimentiFiltratiUscitePerNumero(final String tabella, final List<ISingleSpesa> uscite, int nUscite) {
		final List<String> nomi = Database.getSingleton().nomiColonne(tabella);

		final int numUscite = nUscite;

		if (!uscite.isEmpty()) {
			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < numUscite; x++) {
				popolaArrayMovUsciteConSingleSpesa(uscite, x);
				
				if (uscite.size() < numUscite) {

						for (int y = uscite.size(); y < numUscite; y++) {
							riempiArrayMovUsciteConZeri(nomi, y);
						}
					}
				
			}
		} else {
			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < numUscite; x++) {
				riempiArrayMovUsciteConZeri(nomi, x);
			}

		}
		return movimentiUscite;

	}

	private static void popolaArrayMovUsciteConSingleSpesa(final List<ISingleSpesa> uscite, int x) {
		final ISingleSpesa uscita = uscite.get(x);
		movimentiUscite[x][0] = uscita.getData();
		movimentiUscite[x][1] = uscita.getNome();
		movimentiUscite[x][2] = uscita.getdescrizione();
		movimentiUscite[x][3] = Double.toString(uscita.getinEuro());
		movimentiUscite[x][4] = uscita.getCatSpese() != null ? uscita.getCatSpese().getnome() : "Nessuna";
		movimentiUscite[x][5] = Integer.toString(uscita.getidSpesa());
		movimentiUscite[x][6] = uscita.getDataIns();
	}

	/**
	 * Valorizza una matrice utile per i pannelli movimenti in uscita. crea il
	 * numero di righe specificato in parametro con le entita' della tabella
	 * passata in parametro
	 *
	 * @param numUscite
	 * @param tabella
	 * @return String[][]
	 */
	public static String[][] movimentiUscite(final int numUscite, final String tabella) {
		final List<String> nomi = Database.getSingleton().nomiColonne(tabella);
		final List<ISingleSpesa> uscite = Model.getSingleton().modelUscita.dieciUscite(numUscite);

		if (!uscite.isEmpty()) {
			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < uscite.size(); x++) {
				popolaArrayMovUsciteConSingleSpesa(uscite, x);

			}
			if (uscite.size() < numUscite) {

				for (int y = uscite.size(); y < numUscite; y++) {
					riempiArrayMovUsciteConZeri(nomi, y);
				}
			}
		} else {
			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < numUscite; x++) {
				riempiArrayMovUsciteConZeri(nomi, x);
			}

		}
		return movimentiUscite;

	}

	private static void riempiArrayMovUsciteConZeri(final List<String> nomi, int x) {
		for (int z = 0; z < nomi.size(); z++) {
			movimentiUscite[x][z] = "0";
		}
	}

	public void setModelLookAndFeel(final WrapLookAndFeel modelLookAndFeel) {
		this.modelLookAndFeel = modelLookAndFeel;
	}

	public WrapLookAndFeel getModelLookAndFeel() {
		return modelLookAndFeel;
	}

}
