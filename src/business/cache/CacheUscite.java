package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import business.Controllore;
import domain.SingleSpesa;
import domain.Utenti;
import domain.wrapper.WrapSingleSpesa;
import view.impostazioni.Impostazioni;

public class CacheUscite extends AbstractCacheBase<SingleSpesa> {

	private static CacheUscite singleton;
	private WrapSingleSpesa usciteDAO = new WrapSingleSpesa();
	
	private CacheUscite() {
		cache = new HashMap<String, SingleSpesa>();
	}

	public static CacheUscite getSingleton() {

		if (singleton == null) {
			singleton = new CacheUscite();
		}
		return singleton;
	}

	public SingleSpesa getSingleSpesa(final String id) {
		SingleSpesa uscita = (SingleSpesa) cache.get(id);
		if (uscita == null) {
			uscita = caricaSingleSpesa(id);
			if (uscita != null) {
				cache.put(id, uscita);
			}
		}
		return (SingleSpesa) cache.get(id);
	}

	private SingleSpesa caricaSingleSpesa(final String id) {
		return (SingleSpesa) new WrapSingleSpesa().selectById(Integer.parseInt(id));
	}

	private Map<String, SingleSpesa> chargeAllUscite() {
		final List<Object> uscite = usciteDAO.selectAll();
		if (uscite != null) {
			for (int i = 0; i < uscite.size(); i++) {
				final SingleSpesa uscita = (SingleSpesa) uscite.get(i);
				final int id = uscita.getidSpesa();
				if (cache.get(id) == null) {
					cache.put(Integer.toString(id), uscita);
				}
			}
			caricata = true;
		} else {
			cache = new HashMap<String, SingleSpesa>();
		}
		return cache;
	}

	public Map<String, SingleSpesa> getAllUscite() {
		if (caricata) {
			return cache;
		} else {
			return chargeAllUscite();
		}
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
		final String annoDaText = Impostazioni.getSingleton().getAnnotextField().getText();
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
