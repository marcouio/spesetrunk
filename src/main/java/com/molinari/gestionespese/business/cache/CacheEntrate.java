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
import com.molinari.gestionespese.domain.IEntrate;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;

public class CacheEntrate extends AbstractCacheBase<IEntrate> {

	private static CacheEntrate singleton;
	private final WrapEntrate entrateDAO = new WrapEntrate();

	private CacheEntrate() {
		setCache(new HashMap<String, IEntrate>());
		setCaricata(false);
	}

	public static synchronized CacheEntrate getSingleton() {

		if (singleton == null) {
			singleton = new CacheEntrate();
		}
		return singleton;
	}

	public IEntrate getEntrate(final String id) {
		return getObjectById(entrateDAO, id);
	}

	public Map<String, IEntrate> getAllEntrate() {
		return getAll(entrateDAO);
	}

	public List<IEntrate> getAllEntrateForUtente() {
		final Map<String, IEntrate> mappa = getAllEntrate();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		if (mappa != null && utente != null) {

			return mappa.values().stream().filter(getFilterForUser(utente)).collect(Collectors.toList());

		}
		return new ArrayList<>();
	}

	private Predicate<? super IEntrate> getFilterForUser(final Utenti utente) {
		return entrata ->
		{
			final Utenti utenti = entrata.getUtenti();
			final int idUtente = utenti.getidUtente();
			return entrata != null && utenti != null && idUtente != 0 && idUtente == utente.getidUtente();
		};
	}

	public List<IEntrate> getAllEntrateForUtenteEAnno() {

		final ArrayList<IEntrate> listaEntrate = new ArrayList<>();
		final Map<String, IEntrate> mappa = getAllEntrate();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		final String annoDaText = Integer.toString(Impostazioni.getAnno());

		if (mappa != null && utente != null) {

			final Stream<IEntrate> streamEntrate = mappa.values().stream().filter(getFilterUserAndYear(utente, annoDaText));
			return streamEntrate.collect(Collectors.toList());
		}

		return listaEntrate;

	}

	private Predicate<? super IEntrate> getFilterUserAndYear(final Utenti utente, final String annoDaText) {
		return e -> {
			if(e != null && e.getUtenti() != null && e.getUtenti().getidUtente() != 0){
				final String annoEntrata = e.getdata().substring(0, 4);
				return e.getUtenti().getidUtente() == utente.getidUtente() && annoEntrata.equals(annoDaText);
			}
			return false;
		};
	}

	public int getMaxId() {
		final Map<String, IEntrate> mappa = getAllEntrate();
		final Optional<IEntrate> max = mappa.values().stream().max((e1,e2) -> Integer.compare(e1.getidEntrate(), e2.getidEntrate()));
		return max.isPresent() ? max.get().getidEntrate() : 0;
	}

}
