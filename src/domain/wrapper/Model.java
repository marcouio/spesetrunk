package domain.wrapper;

import java.util.Map;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import view.componenti.movimenti.AscoltatoreMouseMovEntrate;
import view.componenti.movimenti.AscoltatoreMouseMovUscite;
import view.componenti.movimenti.ListaMovEntrat;
import view.componenti.movimenti.ListaMovUscite;
import view.font.TableF;
import business.DBUtil;
import business.Database;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import domain.AbstractOggettoEntita;
import domain.CatSpese;
import domain.Entrate;
import domain.Gruppi;
import domain.SingleSpesa;

public class Model {

	private WrapCatSpese modelCategorie;
	private WrapGruppi modelGruppi;
	private WrapBudget1 modelBudget;
	private WrapEntrate modelEntrate;
	private WrapRisparmio modelRisparmio;
	private WrapSingleSpesa modelUscita;
	private WrapUtenti modelUtenti;
	private WrapLookAndFeel modelLookAndFeel;
	private static Model singleton;
	private static String[][] primoUscite;
	private static String[][] primoEntrate;
	private static String[] nomiColonneUscite;
	private static String[] nomiColonneEntrate;
	private static String[][] movimentiEntrate;
	private static String[][] movimentiUscite;
	
	private static Vector<CatSpese> catSpese = CacheCategorie.getSingleton().getVettoreCategorie();
	
	
	public static Model getSingleton(){
		if (singleton == null) {
			synchronized (CacheCategorie.class) {
				if (singleton == null) {
					singleton = new Model();
				}
			} // if
		} // if
		return singleton;
	}
	
	private Model(){
		modelCategorie = new WrapCatSpese();
		modelGruppi = new WrapGruppi();
		modelBudget = new WrapBudget1();
		modelEntrate = new WrapEntrate();
		modelRisparmio = new WrapRisparmio();
		modelUscita = new WrapSingleSpesa();
		modelUtenti = new WrapUtenti();
	}

	public WrapCatSpese getModelCategorie() {
		return modelCategorie;
	}

	public void setModelCategorie(WrapCatSpese modelCategorie) {
		this.modelCategorie = modelCategorie;
	}

	public WrapGruppi getModelGruppi() {
		return modelGruppi;
	}

	public void setModelGruppi(WrapGruppi modelGruppi) {
		this.modelGruppi = modelGruppi;
	}

	public WrapBudget1 getModelBudget() {
		return modelBudget;
	}

	public void setModelBudget(WrapBudget1 modelBudget) {
		this.modelBudget = modelBudget;
	}

	public WrapEntrate getModelEntrate() {
		return modelEntrate;
	}

	public void setModelEntrate(WrapEntrate modelEntrate) {
		this.modelEntrate = modelEntrate;
	}

	public WrapRisparmio getModelRisparmio() {
		return modelRisparmio;
	}

	public void setModelRisparmio(WrapRisparmio modelRisparmio) {
		this.modelRisparmio = modelRisparmio;
	}

	public WrapSingleSpesa getModelUscita() {
		return modelUscita;
	}

	public void setModelUscita(WrapSingleSpesa modelUscita) {
		this.modelUscita = modelUscita;
	}

	public WrapUtenti getModelUtenti() {
		return modelUtenti;
	}

	public void setModelUtenti(WrapUtenti modelUtenti) {
		this.modelUtenti = modelUtenti;
	}

//	public void setSingleton(Model singleton) {
//		Model.singleton = singleton;
//	}
	
//	*************************************TABELLA-USCITE******************************************
	
	private static String[][] setTabellaUscitaPrimo(){
		 
	    int numColonne = catSpese.size();
	    String[] nomiColonne = new String[numColonne];
	    
	    for(int i=0; i<catSpese.size(); i++){
	    	nomiColonne[i] = catSpese.get(i).getnome(); 
	    }
	    
	    primoUscite = new String[12][numColonne];
	
	    for(int i=0; i<12; i++){
	    	for(int x=0; x<catSpese.size(); x++){
	    		try{ 
					primoUscite[i][x]= Double.toString(Database.speseMeseCategoria(i+1, catSpese.get(x).getidCategoria()));
	    		}catch (Exception e){ 
					e.printStackTrace();
	    		}
	    	}        	
	    }
		return primoUscite;
	}
	
	private static String[] nomiColonneUscite(){
		
		int numColonne = catSpese.size();
        nomiColonneUscite = new String[numColonne];
        
        for(int i=0; i<catSpese.size(); i++){
        	nomiColonneUscite[i] = catSpese.get(i).getnome(); 
        }
		return nomiColonneUscite;
	}

	public static String[][] getPrimoUscite() {
		return setTabellaUscitaPrimo();
	}

	public static void setPrimoUscite(String[][] primo) {
		Model.primoUscite = primo;
	}

	public static void setNomiColonneUscite(String[] nomiColonneUscite) {
		Model.nomiColonneUscite = nomiColonneUscite;
	}

	public static String[] getNomiColonneUscite() {
		return nomiColonneUscite();
	}
	

//	*************************************TABELLA-ENTRATE******************************************
	
	
	private static String[][] setTabellaEntratePrimo(){
		 
		String[] nomiColonneEntrate = nomiColonneEntrate();
	    int numColonne = nomiColonneEntrate.length;
	    
	    primoEntrate = new String[12][numColonne];
	
	    for(int i=0; i<12; i++){
	    	for(int x=0; x<numColonne; x++){
	    		try{ 
					primoEntrate[i][x]= Double.toString(Database.getSingleton().entrateMeseTipo(i+1, nomiColonneEntrate[x]));
	    		}catch (Exception e){ 
					e.printStackTrace();
	    		}
	    	}        	
	    }
		return primoEntrate;
	}
	
	private static String[] nomiColonneEntrate(){
		
		 nomiColonneEntrate = new String[2];
		 nomiColonneEntrate[0] = "Fisse";
		 nomiColonneEntrate[1] = "Variabili";
		 
		 return nomiColonneEntrate;
	}

	public static String[][] getPrimoEntrate() {
		return setTabellaEntratePrimo();
	}

	public static void setPrimoEntrate(String[][] primo) {
		Model.primoUscite = primo;
	}

	public static void setNomiColonneEntrate(String[] nomiColonneEntrate) {
		Model.nomiColonneEntrate = nomiColonneEntrate;
	}

	public static String[] getNomiColonneEntrate() {
		return nomiColonneEntrate();
	}
	
	
//	*************************************CATEGORIE-PERCOMBOBOX***********************************
	
	private Map<String, AbstractOggettoEntita> getCatPerCombo(boolean ricarica){
		CacheCategorie cache = CacheCategorie.getSingleton();
		cache.setCaricata(!ricarica);
		return cache.getAllCategorie();
	}
	public Object[] getCategorieCombo(boolean ricarica){
		Map<String, AbstractOggettoEntita> cat = getCatPerCombo(ricarica);
		return cat.values().toArray();
	}
	
	
//	*************************************GRUPPI-PERCOMBOBOX***********************************
	
	private Map<String, AbstractOggettoEntita> getGruppiPerCombo(boolean ricarica){
		CacheGruppi cache = CacheGruppi.getSingleton();
		cache.setCaricata(!ricarica);
		return cache.getAllGruppi();
	}
	public Object[] getGruppiCombo(boolean ricarica){
		Map<String, AbstractOggettoEntita> gruppi = getGruppiPerCombo(ricarica);
		return gruppi.values().toArray();
	}
	
	
//	*************************************MOVIMENTI-ENTRATE***********************************
	
	/**
	 * Il metodo genera una matrice di movimenti in entrata con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 * 
	 * @param nomiColonne
	 * @param numEntry
	 */
	public static void aggiornaMovimentiEntrateDaEsterno(Object[] nomiColonne,
			int numEntry) {

		String[][] movimenti = Model.getSingleton().movimentiEntrate(numEntry,Entrate.NOME_TABELLA);
		JTable table1 = ListaMovEntrat.getTable1();
		table1 = new TableF(movimenti, nomiColonne);
		JScrollPane scrollPane = ListaMovEntrat.getScrollPane();
		scrollPane.setViewportView(table1);
		table1.addMouseListener(new AscoltatoreMouseMovEntrate(table1));
	}

	
	/**
	 * Valorizza una matrice utile per i pannelli movimenti in entrata. crea il numero di righe 
	 * specificato in parametro con le entita' della tabella passata in parametro
	 * 
	 * @param numEntry
	 * @param tabella
	 * @return String[][]
	 */
	public String[][] movimentiEntrate(int numEntry, String tabella) {
		Vector<String> nomi = Database.getSingleton().nomiColonne(tabella);
		Vector<Entrate> entry1 = Model.getSingleton().modelEntrate.dieciEntrate(numEntry);

		if(entry1.size()>0 && (entry1.size()==numEntry || entry1.size()>=numEntry)){
			movimentiEntrate = new String[numEntry][nomi.size()];
			for(int x = 0; x<entry1.size(); x++){
				Entrate entrate = entry1.get(x);
				movimentiEntrate[x][0] = entrate.getdata().toString();
				movimentiEntrate[x][1] = entrate.getnome();
				movimentiEntrate[x][2] = entrate.getdescrizione();
				movimentiEntrate[x][3] = Double.toString(entrate.getinEuro());
				movimentiEntrate[x][4] = entrate.getFisseoVar();
				movimentiEntrate[x][5] = Integer.toString(entrate.getidEntrate());
				movimentiEntrate[x][6] = entrate.getDataIns();
				
			}
		}else if(entry1.size()>0 && entry1.size()<numEntry){
			movimentiEntrate = new String[numEntry][nomi.size()];
			for (int x = 0; x < entry1.size(); x++) {
				Entrate entrate = entry1.get(x);
				movimentiEntrate[x][0] = entrate.getdata().toString();
				movimentiEntrate[x][1] = entrate.getnome();
				movimentiEntrate[x][2] = entrate.getdescrizione();
				movimentiEntrate[x][3] = Double.toString(entrate.getinEuro());
				movimentiEntrate[x][4] = entrate.getFisseoVar();
				movimentiEntrate[x][5] = Integer.toString(entrate.getidEntrate());
				movimentiEntrate[x][6] = entrate.getDataIns();
			}
			for(int y = entry1.size(); y<numEntry; y++){
				for(int z =0; z<nomi.size(); z++)
					movimentiEntrate[y][z]="0";
			}
		}else{
			movimentiEntrate = new String[numEntry][nomi.size()];
			for (int x = 0; x < numEntry; x++) {
				for(int z =0; z<nomi.size(); z++)
					movimentiEntrate[x][z]="0";
			}
		}
		DBUtil.closeConnection();
		return movimentiEntrate;
	}
	
	
//	*************************************MOVIMENTI-USCITE***********************************
	
	

	/**
	 * Il metodo genera una matrice di movimenti in uscita con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 * 
	 * @param nomiColonne
	 * @param numUscite
	 */
	public static void aggiornaMovimentiUsciteDaEsterno(Object[] nomiColonne,
			int numUscite) {

		String[][] movimenti = Model.getSingleton().movimentiUscite(numUscite,
				SingleSpesa.NOME_TABELLA);
		JTable table1 = ListaMovUscite.getTable1();
		table1 = new TableF(movimenti, nomiColonne);
		JScrollPane scrollPane = ListaMovUscite.getScrollPane();
		scrollPane.setViewportView(table1);
		table1.addMouseListener(new AscoltatoreMouseMovUscite(table1));
	}
	
	
	/**
	 * Valorizza una matrice utile per i pannelli movimenti in uscita. crea il numero di righe specificato in parametro
	 * con le entita' della tabella passata in parametro
	 * 
	 * @param numUscite
	 * @param tabella
	 * @return String[][]
	 */
	public String[][] movimentiUscite(int numUscite, String tabella) {
		Vector<String> nomi = Database.getSingleton().nomiColonne(tabella);
		Vector<SingleSpesa> uscite = Model.getSingleton().modelUscita.dieciUscite(numUscite);
		
		if(uscite.size()>0 && (uscite.size()==numUscite || uscite.size()>=numUscite)){
			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < numUscite; x++) {
				SingleSpesa uscita = uscite.get(x);
				movimentiUscite[x][0] = uscita.getData();
				movimentiUscite[x][1] = uscita.getnome();
				movimentiUscite[x][2] = uscita.getdescrizione();
				movimentiUscite[x][3] = Double.toString(uscita.getinEuro());
				movimentiUscite[x][4] = uscita.getCatSpese().getnome();
				movimentiUscite[x][5] = Integer.toString(uscita.getidSpesa());
				movimentiUscite[x][6] = uscita.getDataIns();
				
			}
		}else if(uscite.size()>0 && uscite.size()<numUscite){
			
			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < uscite.size(); x++) {
				
				SingleSpesa uscita = uscite.get(x);
				movimentiUscite[x][0] = uscita.getData();
				movimentiUscite[x][1] = uscita.getnome();
				movimentiUscite[x][2] = uscita.getdescrizione(); 
				movimentiUscite[x][3] = Double.toString(uscita.getinEuro());
				movimentiUscite[x][4] = uscita.getCatSpese()!=null?uscita.getCatSpese().getnome():"Nessuna";
				movimentiUscite[x][5] = Integer.toString(uscita.getidSpesa());
				movimentiUscite[x][6] = uscita.getDataIns();
				
				for(int y = uscite.size(); y<numUscite; y++){
					for(int z =0; z<nomi.size(); z++)
						movimentiUscite[y][z]="0";
				}
			}
		}else{
			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < numUscite; x++) {
				for(int z =0; z<nomi.size(); z++)
					movimentiUscite[x][z]="0";
			}
			
		}
		DBUtil.closeConnection();
		return movimentiUscite;
		
	}

	public void setModelLookAndFeel(WrapLookAndFeel modelLookAndFeel) {
		this.modelLookAndFeel = modelLookAndFeel;
	}

	public WrapLookAndFeel getModelLookAndFeel() {
		return modelLookAndFeel;
	}
	
	
}

