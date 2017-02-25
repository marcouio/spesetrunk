package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;

public class CacheEntrate extends AbstractCacheBase<Entrate> {

	private static CacheEntrate singleton;
	private WrapEntrate entrateDAO = new WrapEntrate();

	private CacheEntrate() {
		setCache(new HashMap<String, Entrate>());
		setCaricata(false);
	}

	public static synchronized CacheEntrate getSingleton() {

		if (singleton == null) {
			singleton = new CacheEntrate();
		}
		return singleton;
	}

	public Entrate getEntrate(final String id) {
		return getObjectById(entrateDAO, id);
	}

	public Map<String, Entrate> getAllEntrate() {
		return getAll(entrateDAO);
	}

	public List<Entrate> getAllEntrateForUtente() {
		final Map<String, Entrate> mappa = getAllEntrate();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		if (mappa != null && utente != null) {
			
			return mappa.values().stream().filter(getFilterForUser(utente)).collect(Collectors.toList());
			
		}
		return new ArrayList<>();
	}

	private Predicate<? super Entrate> getFilterForUser(final Utenti utente) {
		return entrata ->
			{
				Utenti utenti = entrata.getUtenti();
				int idUtente = utenti.getidUtente();
				return entrata != null && (utenti != null && idUtente != 0) && idUtente == utente.getidUtente();
			};
	}

	public List<Entrate> getAllEntrateForUtenteEAnno() {
		
		final ArrayList<Entrate> listaEntrate = new ArrayList<>();
		final Map<String, Entrate> mappa = getAllEntrate();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		final String annoDaText = Integer.toString(Impostazioni.getAnno());

		if (mappa != null && utente != null) {

			Stream<Entrate> streamEntrate = mappa.values().stream().filter(getFilterUserAndYear(utente, annoDaText));
			return streamEntrate.collect(Collectors.toList());
		}
		
		return listaEntrate;
				
	}

	private Predicate<? super Entrate> getFilterUserAndYear(final Utenti utente, final String annoDaText) {
		return e -> {
			if(e != null && (e.getUtenti() != null && e.getUtenti().getidUtente() != 0)){
				final String annoEntrata = e.getdata().substring(0, 4);
				return e.getUtenti().getidUtente() == utente.getidUtente() && annoEntrata.equals(annoDaText);
			}
			return false;
		};
	}

	public int getMaxId() {
		final Map<String, Entrate> mappa = getAllEntrate();
		Optional<Entrate> max = mappa.values().stream().max((e1,e2) -> Integer.compare(e1.getidEntrate(), e2.getidEntrate()));
		return max.isPresent() ? max.get().getidEntrate() : 0;
	}

}
