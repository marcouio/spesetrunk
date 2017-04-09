package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;

public class CacheUscite extends AbstractCacheBase<ISingleSpesa> {

	private static CacheUscite singleton;
	private final WrapSingleSpesa usciteDAO = new WrapSingleSpesa();

	private CacheUscite() {
		setCache(new HashMap<String, ISingleSpesa>());
	}

	public static CacheUscite getSingleton() {

		if (singleton == null) {
			singleton = new CacheUscite();
		}
		return singleton;
	}

	public ISingleSpesa getSingleSpesa(final String id) {
		return getObjectById(usciteDAO, id);
	}

	public Map<String, ISingleSpesa> getAllUscite() {
		return getAll(usciteDAO);
	}

	public List<ISingleSpesa> getAllUsciteForUtente() {
		final Utenti utente = (Utenti) Controllore.getUtenteLogin();

		final Map<String, ISingleSpesa> mappa = getAllUscite();

		final Stream<ISingleSpesa> filter = mappa.values().stream().filter(ss ->
		{
			final IUtenti utenti = ss.getUtenti();
			return ss != null && utenti != null && utenti.getidUtente() == utente.getidUtente();
		});

		return filter.collect(Collectors.toList());

	}

	public List<ISingleSpesa> getAllUsciteForUtenteEAnno() {
		final Map<String, ISingleSpesa> mappa = getAllUscite();
		final Utenti utente = (Utenti) Controllore.getUtenteLogin();
		final String annoDaText = Integer.toString(Impostazioni.getAnno());
		if (utente != null) {
			return mappa.values().stream().filter(ss -> {
				final String annoUscita = ss.getData().substring(0, 4);
				return ss != null && ss.getUtenti() != null && annoUscita.equals(annoDaText);
			}).collect(Collectors.toList());
		}

		return new ArrayList<>();
	}

	public int getMaxId() {
		final Map<String, ISingleSpesa> mappa = getAllUscite();
		final Optional<ISingleSpesa> max = mappa.values().stream().max((s1, s2) -> Integer.compare(s1.getidSpesa(), s2.getidSpesa()));
		return max.isPresent() ? max.get().getidSpesa() : 0;
	}

}
