package com.molinari.gestionespese.business.aggiornatori;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.GestioneSpeseException;
import com.molinari.gestionespese.business.InizializzatoreFinestre;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.business.generatori.TableModelEntrate;
import com.molinari.gestionespese.business.generatori.TableModelUscite;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.wrapper.Model;
import com.molinari.gestionespese.view.FinestraListaComandi;
import com.molinari.gestionespese.view.GeneralFrame;
import com.molinari.gestionespese.view.componenti.componentipannello.PannelloAScomparsa;
import com.molinari.gestionespese.view.componenti.componentipannello.SottoPannelloCategorie;
import com.molinari.gestionespese.view.componenti.componentipannello.SottoPannelloDatiEntrate;
import com.molinari.gestionespese.view.componenti.componentipannello.SottoPannelloDatiSpese;
import com.molinari.gestionespese.view.componenti.componentipannello.SottoPannelloMesi;
import com.molinari.gestionespese.view.componenti.componentipannello.SottoPannelloTotali;
import com.molinari.gestionespese.view.componenti.movimenti.AscoltatoreBottoniEntrata;
import com.molinari.gestionespese.view.componenti.movimenti.AscoltatoreBottoniUscita;
import com.molinari.gestionespese.view.componenti.movimenti.ListaMovimentiEntrate;
import com.molinari.gestionespese.view.componenti.movimenti.ListaMovimentiUscite;
import com.molinari.gestionespese.view.font.TableF;
import com.molinari.gestionespese.view.impostazioni.CategorieView;
import com.molinari.gestionespese.view.tabelleMesi.PerMesiF;
import com.molinari.gestionespese.view.tabelleMesi.TabellaEntrata;
import com.molinari.gestionespese.view.tabelleMesi.TabellaUscita;
import com.molinari.gestionespese.view.tabelleMesi.TabellaUscitaGruppi;

import controller.ControlloreBase;
import db.ConnectionPool;

public class AggiornatoreManager {

	public static final String         AGGIORNA_ENTRATE = "entrate";
	public static final String         AGGIORNA_USCITE  = "uscite";
	public static final String         AGGIORNA_ALL     = "all";
	public static final String         AGGIORNA_NULLA   = "nulla";

	private static AggiornatoreManager singleton;

	private AggiornatoreManager() {
		//do nothing
	}

	public static AggiornatoreManager getSingleton() {
		if (singleton == null) {
			singleton = new AggiornatoreManager();
		}
		return singleton;
	}

	public IAggiornatore creaAggiornatore(final String cosaAggiornare) {
		IAggiornatore aggiornatore = null;
		if (cosaAggiornare.equals(AGGIORNA_ENTRATE)) {
			aggiornatore = new AggiornatoreEntrate();
		} else if (cosaAggiornare.equals(AGGIORNA_USCITE)) {
			aggiornatore = new AggiornatoreUscite();
		} else if (cosaAggiornare.equals(AGGIORNA_ALL)) {
			aggiornatore = new AggiornatoreTotale();
		} else if (cosaAggiornare.equals(AGGIORNA_NULLA)) {
			aggiornatore = new AggiornatoreNull();
		}
		return aggiornatore;
	}

	// ***************************************** METODI AGGIORNAMENTO

	public static boolean aggiornamentoPerImpostazioni() {
		try {
			if (SottoPannelloMesi.getComboMese() != null) {
				SottoPannelloMesi.azzeraCampi();
			}
			SottoPannelloCategorie.azzeraCampi();
			if (aggiornamentoGenerale(Entrate.NOME_TABELLA) && aggiornamentoGenerale(SingleSpesa.NOME_TABELLA) && aggiornaPannelloTotali()) {
				return true;
			}
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
		return false;
	}

	public static boolean aggiornaPannelloTotali() {
		try {
			if (SottoPannelloTotali.getPercentoFutili() != null) {
				SottoPannelloTotali.getPercentoFutili().setText(Double.toString(Database.percentoUscite(CatSpese.IMPORTANZA_FUTILE)));
				SottoPannelloTotali.getPercentoVariabili().setText(Double.toString(Database.percentoUscite(CatSpese.IMPORTANZA_VARIABILE)));
				SottoPannelloTotali.getAvanzo().setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(Database.eAnnuale() - Database.uAnnuale())));
			}
			return true;
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Il metodo genera una matrice di movimenti in uscita con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 *
	 * @param nomiColonne
	 * @param numUscite
	 */
	public static boolean aggiornaMovimentiUsciteDaFiltro(final Object[] nomiColonne, final String[][] movimenti) {
		try {
			final ListaMovimentiUscite tabMovimenti = Controllore.getSingleton().getGeneralFrame().getPannelTabs().getTabMovUscite();
			if(tabMovimenti != null){
				final TableF table1 = new TableF(movimenti, nomiColonne);
				final JScrollPane scrollPane = tabMovimenti.getScrollPane();
				scrollPane.setViewportView(table1);
				table1.addMouseListener(new AscoltatoreBottoniUscita(table1));
			}
			return true;
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			return false;
		}

	}

	/**
	 * Il metodo genera una matrice di movimenti in uscita con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 *
	 * @param nomiColonne
	 * @param numUscite
	 */
	public static boolean aggiornaMovimentiUsciteDaEsterno(final Object[] nomiColonne, final int numUscite) {
		try {
			final String[][] movimenti = Model.movimentiUscite(numUscite, SingleSpesa.NOME_TABELLA);
			final ListaMovimentiUscite tabMovimenti = Controllore.getSingleton().getGeneralFrame().getPannelTabs().getTabMovUscite();
			if(tabMovimenti != null){
				final TableF table1 = new TableF(movimenti, nomiColonne);
				final JScrollPane scrollPane = tabMovimenti.getScrollPane();
				scrollPane.setViewportView(table1);
				table1.addMouseListener(new AscoltatoreBottoniUscita(table1));
			}
			return true;
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Il metodo genera una matrice di movimenti in entrata con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 *
	 * @param nomiColonne
	 * @param numEntry
	 */
	public static boolean aggiornaMovimentiEntrateDaFiltro(final Object[] nomiColonne, final String[][] movimenti) {
		try {
			final ListaMovimentiEntrate tabMovimenti = Controllore.getSingleton().getGeneralFrame().getPannelTabs().getTabMovEntrate();
			if(tabMovimenti != null){
				final TableF table1 = new TableF(movimenti, nomiColonne);
				final JScrollPane scrollPane = tabMovimenti.getScrollPane();
				scrollPane.setViewportView(table1);
				table1.addMouseListener(new AscoltatoreBottoniEntrata(table1));
			}
			return true;
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Il metodo genera una matrice di movimenti in entrata con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 *
	 * @param nomiColonne
	 * @param numEntry
	 */
	public static boolean aggiornaMovimentiEntrateDaEsterno(final Object[] nomiColonne, final int numEntry) {
		try {
			final String[][] movimenti = Model.movimentiEntrate(numEntry, Entrate.NOME_TABELLA);
			if(Controllore.getSingleton().getGeneralFrame()!=null){
				final TableF table1 = new TableF(movimenti, nomiColonne);
				final ListaMovimentiEntrate tabMovimenti = Controllore.getSingleton().getGeneralFrame().getPannelTabs().getTabMovEntrate();
				if(tabMovimenti != null){
					final JScrollPane scrollPane = tabMovimenti.getScrollPane();
					scrollPane.setViewportView(table1);
					table1.addMouseListener(new AscoltatoreBottoniEntrata(table1));
				}
				return true;
			}
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return false;
	}

	public static boolean aggiornaListaComandi(){
		GeneralFrame generalFrame = Controllore.getSingleton().getGeneralFrame();
		final InizializzatoreFinestre initFinestre = generalFrame.getInitFinestre();
		final FinestraListaComandi finestra = (FinestraListaComandi) initFinestre.getFinestra(InizializzatoreFinestre.INDEX_HISTORY, initFinestre.getPannello());
		finestra.insertDati();
		return true;
	}

	/**
	 * Dopo una variazione o inserimento di un movimento permette
	 * l'aggiornamento di tutti i pannelli rispetto al tipo (Uscita,
	 * 'SingleSpesa.NOME_TABELLA', e Entrata, 'Entrate.NOME_TABELLA')
	 *
	 * @param tipo
	 * @throws Exception
	 */
	public static boolean aggiornamentoGenerale(final String tipo) {

		aggiornaListaComandi();

		if (tipo.equals(SingleSpesa.NOME_TABELLA)) {
			final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(SingleSpesa.NOME_TABELLA);
			if (aggiornaTabellaUscite() && aggiornaTabellaGruppi() && aggiornaMovimentiUsciteDaEsterno(nomiColonne, 25) && aggiornaPannelloDatiSpese()) {
				return true;
			}
		} else if (tipo.equals(Entrate.NOME_TABELLA)) {
			final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
			if (aggiornaMovimentiEntrateDaEsterno(nomiColonne, 25) && aggiornaTabellaEntrate() && aggiornaTabellaGruppi() && aggiornaPannelloDatiEntrate()) {
				return true;
			}
		} else {
			throw new GestioneSpeseException("Aggiornamento non gestito: " + tipo);
		}
		return false;
	}

	public static boolean aggiornaPannelloDatiEntrate() {
		try {
			GeneralFrame generalFram = Controllore.getSingleton().getGeneralFrame();
			final InizializzatoreFinestre initFinestre = generalFram.getInitFinestre();
			final PannelloAScomparsa finestra = (PannelloAScomparsa) initFinestre.getFinestra(InizializzatoreFinestre.INDEX_PANNELLODATI, initFinestre.getPannello());


			final SottoPannelloDatiEntrate pannelloEntrate = finestra.getPannelloEntrate();
			if (pannelloEntrate != null) {
				pannelloEntrate.getEnAnCorso().setText(Double.toString(Database.eAnnuale()));
				pannelloEntrate.getEnMeCorso().setText(Double.toString(Database.eMensileInCorso()));
				pannelloEntrate.getEntrateMesePrec().setText(Double.toString(Database.eMensile()));
			}
			return true;
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	public static boolean aggiornaPannelloDatiSpese() {
		try {
			if (SottoPannelloDatiSpese.getMeseInCors() != null) {
				SottoPannelloDatiSpese.getMeseInCors().setText(Double.toString(Database.uMensileInCorso()));
				SottoPannelloDatiSpese.getMesePrecUsc().setText(Double.toString(Database.uMensile()));
				SottoPannelloDatiSpese.getSpeseAnnuali().setText(Double.toString(Database.uAnnuale()));
			}
			return true;
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
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


		try {
			final Connection cn = ConnectionPool.getSingleton().getConnection();
			final ResultSet rs = ConnectionPool.getSingleton().getResulSet(cn, sql);
			max = rs.getInt(1);
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		final JComboBox<IGruppi> gruppi = categoria.getComboGruppi();

		gruppi.setSelectedIndex(0);

		for (int i = 1; i <= max; i++) {

			Gruppi gruppo1 = (Gruppi) gruppi.getItemAt(i);
			if (gruppo1 == null) {
				gruppo1 = new Gruppi();
				gruppo1.setidGruppo(-1);
			}
			if (gruppo.getidGruppo() == gruppo1.getidGruppo()) {
				gruppi.removeItemAt(i);
				IGruppi gruppinew = CacheGruppi.getSingleton().getGruppo(Integer.toString(gruppo.getidGruppo()));
				// non è possibile sostituirlo la categoria presa dal database
				// con quella passata nel parametro
				// perché il parametro mantiene i vecchi settaggi e non si
				// aggiorna
				gruppi.insertItemAt(gruppinew, i);
			}
		}

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
	public static void aggiornaCategorie(final ICatSpese categoria, final JComboBox<ICatSpese> comboCategorie) {
		int max = 0;
		final String sql = "SELECT MAX(" + CatSpese.ID + ") FROM " + CatSpese.NOME_TABELLA;

		try {

			final Connection cn = ConnectionPool.getSingleton().getConnection();
			final ResultSet rs = ConnectionPool.getSingleton().getResulSet(cn, sql);

			max = rs.getInt(1);
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		final JComboBox<ICatSpese> categorie1 = comboCategorie;

		for (int i = 1; i <= max; i++) {

			ICatSpese catspese1 = (ICatSpese) categorie1.getItemAt(i);
			if (catspese1 == null) {
				catspese1 = new CatSpese();
				catspese1.setidCategoria(-1);
			}
			if (categoria.getidCategoria() == catspese1.getidCategoria()) {
				categorie1.removeItemAt(i);
				final ICatSpese categoriaPresa = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categoria.getidCategoria()));
				// non è possibile sostituirlo la categoria presa dal database
				// con quella passata nel parametro
				// perché il parametro mantiene i vecchi settaggi e non si
				// aggiorna
				categorie1.insertItemAt(categoriaPresa, i);
			}
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
	public static boolean aggiornaTabellaGruppi() {
		try {
			final JTable table = TabellaUscitaGruppi.getDatiPerTabella();
			final JScrollPane pane = TabellaUscitaGruppi.getScrollPane();
			if(pane != null){
				pane.setViewportView(table);
			}
			return true;
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
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
			final TableModelUscite model = new TableModelUscite(null);

			final TableF table = TabellaUscita.createTable(model);
			final JScrollPane pane = TabellaUscita.getScrollPane();
			if(pane != null){
				pane.setViewportView(table);
			}
			return true;
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
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
	public static boolean aggiornaTabellaEntrate() {
		try {

			final TableModelEntrate model = new TableModelEntrate(null);
			final GeneralFrame generalFrame = Controllore.getSingleton().getGeneralFrame();
			final PerMesiF tabPermesi = generalFrame.getPannelTabs().getTabPermesi();
			if(tabPermesi != null){
				final TableF table = tabPermesi.getTabEntrate().createTable(model);
				final JScrollPane pane = TabellaEntrata.getScrollPane();
				pane.setViewportView(table);
			}
			return true;
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	public static void aggiornamentoComboBox(final List<ICatSpese> categorie) {
		ICatSpese[] v = categorie.toArray(new ICatSpese[categorie.size()]);
		final DefaultComboBoxModel<ICatSpese> model = new DefaultComboBoxModel<>(v);
		if (SottoPannelloCategorie.getCategorieCombo() != null) {
			SottoPannelloCategorie.getCategorieCombo().setModel(model);
			SottoPannelloCategorie.getCategorieCombo().validate();
			SottoPannelloCategorie.getCategorieCombo().repaint();
		}
	}
}
