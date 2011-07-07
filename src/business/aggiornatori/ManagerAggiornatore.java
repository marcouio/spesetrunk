package business.aggiornatori;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import view.componenti.componentiPannello.SottoPannelloDatiEntrate;
import view.componenti.componentiPannello.SottoPannelloDatiSpese;
import view.componenti.movimenti.AscoltatoreBottoniEntrata;
import view.componenti.movimenti.AscoltatoreBottoniUscita;
import view.font.TableF;
import view.impostazioni.CategorieView;
import view.tabelle.TabellaEntrata;
import view.tabelle.TabellaUscita;
import view.tabelle.TabellaUscitaGruppi;
import business.AltreUtil;
import business.Controllore;
import business.DBUtil;
import business.Database;
import business.cache.CacheCategorie;
import business.generatori.GeneratoreDatiTabellaEntrate;
import business.generatori.GeneratoreDatiTabellaUscite;
import domain.CatSpese;
import domain.Entrate;
import domain.Gruppi;
import domain.SingleSpesa;
import domain.wrapper.Model;

public class ManagerAggiornatore {

	public static final String AGGIORNA_ENTRATE = "entrate";
	public static final String AGGIORNA_USCITE = "uscite";
	public static final String AGGIORNA_ALL = "all";
	public static final String AGGIORNA_NULLA = "nulla";

	private static ManagerAggiornatore singleton;

	public static ManagerAggiornatore getSingleton() {
		if (singleton == null) {
			singleton = new ManagerAggiornatore();
		}
		return singleton;
	}

	private ManagerAggiornatore() {
	}

	public IAggiornatore creaAggiornatore(final String cosaAggiornare) {
		IAggiornatore aggiornatore = null;
		if (cosaAggiornare.equals(AGGIORNA_ENTRATE)) {
			aggiornatore = new AggiornatoreEntrate();
		} else if (cosaAggiornare.equals(AGGIORNA_USCITE)) {
			aggiornatore = new AggiornatoreUscite();
		} else if (cosaAggiornare.equals(AGGIORNA_ALL)) {

		} else if (cosaAggiornare.equals(AGGIORNA_NULLA)) {

		}
		return aggiornatore;
	}

	// ***************************************** METODI AGGIORNAMENTO

	/**
	 * Il metodo genera una matrice di movimenti in uscita con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 * 
	 * @param nomiColonne
	 * @param numUscite
	 */
	public static void aggiornaMovimentiUsciteDaFiltro(final Object[] nomiColonne, final String[][] movimenti) {

		TableF table1 = Controllore.getSingleton().getView().getTabMovimenti().getTabMovUscite().getTable();
		table1 = new TableF(movimenti, nomiColonne);
		final JScrollPane scrollPane = Controllore.getSingleton().getView().getTabMovimenti().getTabMovUscite().getScrollPane();
		scrollPane.setViewportView(table1);
		table1.addMouseListener(new AscoltatoreBottoniUscita(table1));
	}

	/**
	 * Il metodo genera una matrice di movimenti in uscita con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 * 
	 * @param nomiColonne
	 * @param numUscite
	 */
	public static void aggiornaMovimentiUsciteDaEsterno(final Object[] nomiColonne, final int numUscite) {

		final String[][] movimenti = Model.getSingleton().movimentiUscite(numUscite, SingleSpesa.NOME_TABELLA);
		TableF table1 = Controllore.getSingleton().getView().getTabMovimenti().getTabMovUscite().getTable();
		table1 = new TableF(movimenti, nomiColonne);
		final JScrollPane scrollPane = Controllore.getSingleton().getView().getTabMovimenti().getTabMovUscite().getScrollPane();
		scrollPane.setViewportView(table1);
		table1.addMouseListener(new AscoltatoreBottoniUscita(table1));
	}

	/**
	 * Il metodo genera una matrice di movimenti in entrata con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 * 
	 * @param nomiColonne
	 * @param numEntry
	 */
	public static void aggiornaMovimentiEntrateDaFiltro(final Object[] nomiColonne, final String[][] movimenti) {

		TableF table1 = Controllore.getSingleton().getView().getTabMovimenti().getTabMovEntrate().getTable();
		table1 = new TableF(movimenti, nomiColonne);
		final JScrollPane scrollPane = Controllore.getSingleton().getView().getTabMovimenti().getTabMovEntrate().getScrollPane();
		scrollPane.setViewportView(table1);
		table1.addMouseListener(new AscoltatoreBottoniEntrata(table1));
	}

	/**
	 * Il metodo genera una matrice di movimenti in entrata con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 * 
	 * @param nomiColonne
	 * @param numEntry
	 */
	public static void aggiornaMovimentiEntrateDaEsterno(final Object[] nomiColonne, final int numEntry) {

		final String[][] movimenti = Model.getSingleton().movimentiEntrate(numEntry, Entrate.NOME_TABELLA);
		TableF table1 = Controllore.getSingleton().getView().getTabMovimenti().getTabMovEntrate().getTable();
		table1 = new TableF(movimenti, nomiColonne);
		final JScrollPane scrollPane = Controllore.getSingleton().getView().getTabMovimenti().getTabMovEntrate().getScrollPane();
		scrollPane.setViewportView(table1);
		table1.addMouseListener(new AscoltatoreBottoniEntrata(table1));
	}

	/**
	 * Dopo una variazione o inserimento di un movimento permette
	 * l'aggiornamento di tutti i pannelli rispetto al tipo (Uscita,
	 * 'SingleSpesa.NOME_TABELLA', e Entrata, 'Entrate.NOME_TABELLA')
	 * 
	 * @param tipo
	 * @throws Exception
	 */
	public static boolean aggiornamentoGenerale(final String tipo) throws Exception {

		if (tipo.equals(SingleSpesa.NOME_TABELLA)) {
			final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(SingleSpesa.NOME_TABELLA);
			if (aggiornaTabellaUscite() && aggiornaTabellaGruppi() && aggiornaMovimentiUsciteDaEsterno(nomiColonne, 25) && aggiornaPannelloDatiSpese()) {
				return true;
			}
		} else if (tipo.equals(Entrate.NOME_TABELLA)) {
			final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
			aggiornaMovimentiEntrateDaEsterno(nomiColonne, 25);
			aggiornaTabellaEntrate();
			aggiornaTabellaGruppi();
			aggiornaPannelloDatiEntrate();
		} else {
			throw new Exception("Aggiornamento non gestito: " + tipo);
		}
	}

	public static boolean aggiornaPannelloDatiEntrate() {
		try {
			if (SottoPannelloDatiEntrate.getEnAnCorso() != null) {
				SottoPannelloDatiEntrate.getEnAnCorso().setText(Double.toString(Database.EAnnuale()));
				SottoPannelloDatiEntrate.getEnMeCorso().setText(Double.toString(Database.EMensileInCorso()));
				SottoPannelloDatiEntrate.getEntrateMesePrec().setText(Double.toString(Database.Emensile()));
				DBUtil.closeConnection();
			}
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean aggiornaPannelloDatiSpese() {
		try {
			if (SottoPannelloDatiSpese.getMeseInCors() != null) {
				SottoPannelloDatiSpese.getMeseInCors().setText(Double.toString(Database.MensileInCorso()));
				SottoPannelloDatiSpese.getMesePrecUsc().setText(Double.toString(Database.Mensile()));
				SottoPannelloDatiSpese.getSpeseAnnuali().setText(Double.toString(Database.Annuale()));
				DBUtil.closeConnection();
			}
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Il metodo aggiorna la combobox dei gruppi nel pannello Categorie
	 * passandogli come parametro una entita 'Gruppi'. L'aggiornamento avviene
	 * scorrendo gli elementi della combo: quando id del gruppo passato come
	 * parametro e' lo stesso di quello nella combo, quest'ultimo viene
	 * eliminato. Quindi aggiunge il nuovo gruppo nella stessa posizione di
	 * quello eliminato
	 * 
	 * @param gruppo
	 */
	public static void aggiornaGruppi(final Gruppi gruppo, final CategorieView categoria) {
		int max = 0;
		final String sql = "SELECT MAX(" + Gruppi.ID + ") FROM " + Gruppi.NOME_TABELLA;
		final Connection cn = DBUtil.getConnection();

		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			max = rs.getInt(1);
			DBUtil.closeConnection();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		final JComboBox gruppi = categoria.getComboGruppi();

		gruppi.setSelectedIndex(0);
		int i = 1;
		for (i = 1; i <= max; i++) {

			Gruppi gruppo1 = (Gruppi) gruppi.getItemAt(i);
			if (gruppo1 == null) {
				gruppo1 = new Gruppi();
				gruppo1.setidGruppo(-1);
			}
			if (gruppo.getidGruppo() == gruppo1.getidGruppo()) {
				gruppi.removeItemAt(i);
				final CatSpese categoriaPresa = CacheCategorie.getSingleton().getCatSpese(Integer.toString(gruppo.getidGruppo()));
				// non è possibile sostituirlo la categoria presa dal database
				// con quella passata nel parametro
				// perché il parametro mantiene i vecchi settaggi e non si
				// aggiorna
				gruppi.insertItemAt(categoriaPresa, i);
				DBUtil.closeConnection();
			}
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();

	}

	// aggiorna le categorie nel pannello di uscite
	/**
	 * Il metodo aggiorna la combobox delle categorie nel pannello uscite
	 * passandogli come parametro una entita CatSpese. L'aggiornamento avviene
	 * scorrendo gli elementi della combo: quando id della categoria passata
	 * come parametro e' lo stesso di quello nella combo, quest'ultima viene
	 * eliminata. Quindi aggiunge la nuova categoria nella stessa posizione di
	 * quella eliminata
	 * 
	 * @param CatSpese
	 */
	public static void aggiornaCategorie(final CatSpese categoria, final JComboBox comboCategorie) {
		int max = 0;
		final String sql = "SELECT MAX(" + CatSpese.ID + ") FROM " + CatSpese.NOME_TABELLA;
		final Connection cn = DBUtil.getConnection();

		try {
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			max = rs.getInt(1);
			DBUtil.closeConnection();
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		final JComboBox categorie1 = comboCategorie;

		int i = 1;
		for (i = 1; i <= max; i++) {

			CatSpese catspese1 = (CatSpese) categorie1.getItemAt(i);
			if (catspese1 == null) {
				catspese1 = new CatSpese();
				catspese1.setidCategoria(-1);
			}
			if (categoria.getidCategoria() == catspese1.getidCategoria()) {
				categorie1.removeItemAt(i);
				final CatSpese categoriaPresa = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categoria.getidCategoria()));
				// non è possibile sostituirlo la categoria presa dal database
				// con quella passata nel parametro
				// perché il parametro mantiene i vecchi settaggi e non si
				// aggiorna
				categorie1.insertItemAt(categoriaPresa, i);
				DBUtil.closeConnection();
			}
		}
		try {
			cn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
	}

	// aggiorno tabella uscite/mese in seguito a variazioni di altre tabelle
	/**
	 * il metodo aggiorna la matrice primo[][] che rappresenta i dati della
	 * tabella uscite. Utile nel caso in cui vengano aggiornte altre tabelle e
	 * si vogliano aggiornare anche questi dati.
	 * 
	 * @throws Exception
	 */
	public static boolean aggiornaTabellaGruppi() {
		try {
			final JTable table = TabellaUscitaGruppi.getDatiPerTabella();
			final JScrollPane pane = TabellaUscitaGruppi.getScrollPane();
			pane.setViewportView(table);
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	// aggiorno tabella uscite/mese in seguito a variazioni di altre tabelle
	/**
	 * il metodo aggiorna la matrice primo[][] che rappresenta i dati della
	 * tabella uscite. Utile nel caso in cui vengano aggiornte altre tabelle e
	 * si vogliano aggiornare anche questi dati.
	 * 
	 * @throws Exception
	 */
	public static boolean aggiornaTabellaUscite() {

		try {
			final GeneratoreDatiTabellaUscite dati = new GeneratoreDatiTabellaUscite();
			final TableF table = GeneratoreDatiTabellaUscite.createTable(dati.getMatrice(), dati.getNomiColonna());
			final JScrollPane pane = TabellaUscita.getScrollPane();
			pane.setViewportView(table);
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	// aggiorno tabella entrate/mese in seguito a variazioni di altre tabelle
	/**
	 * Metodo che serve per aggiornare la matrice entrate/mese dopo variazioni
	 * avvenute in altri pannelli
	 * 
	 * @throws Exception
	 */
	public static void aggiornaTabellaEntrate() {

		final GeneratoreDatiTabellaEntrate dati = new GeneratoreDatiTabellaEntrate();
		final TableF table = GeneratoreDatiTabellaEntrate.createTable(dati.getMatrice(), dati.getNomiColonna());
		final JScrollPane pane = TabellaEntrata.getScrollPane();
		pane.setViewportView(table);
	}

}
