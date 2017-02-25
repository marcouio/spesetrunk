package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;

public class CacheUscite extends AbstractCacheBase<SingleSpesa> {

	private static CacheUscite singleton;
	private WrapSingleSpesa usciteDAO = new WrapSingleSpesa();
	
	private CacheUscite() {
		setCache(new HashMap<String, SingleSpesa>());
	}

	public static CacheUscite getSingleton() {

		if (singleton == null) {
			singleton = new CacheUscite();
		}
		return singleton;
	}

	public SingleSpesa getSingleSpesa(final String id) {
		return getObjectById(usciteDAO, id);
	}

	public Map<String, SingleSpesa> getAllUscite() {
		return getAll(usciteDAO);
	}

	public List<SingleSpesa> getAllUsciteForUtente() {
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		
		final Map<String, SingleSpesa> mappa = getAllUscite();
		
		Stream<SingleSpesa> filter = mappa.values().stream().filter(ss -> 
		{
			Utenti utenti = ss.getUtenti();
			return ss != null && utenti != null && utenti.getidUtente() == utente.getidUtente();
		});
		
		return filter.collect(Collectors.toList());
		
	}

	public List<SingleSpesa> getAllUsciteForUtenteEAnno() {
		final Map<String, SingleSpesa> mappa = getAllUscite();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
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
		final Map<String, SingleSpesa> mappa = getAllUscite();
		Optional<SingleSpesa> max = mappa.values().stream().max((s1, s2) -> Integer.compare(s1.getidSpesa(), s2.getidSpesa()));
		return max.isPresent() ? max.get().getidSpesa() : 0;
	}

}
