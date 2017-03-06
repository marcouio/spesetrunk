package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapNote;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;

public class CacheNote extends AbstractCacheBase<INote> {

	private static CacheNote singleton;
	private final WrapNote noteDAO = new WrapNote();

	private CacheNote() {
		setCache(new HashMap<String, INote>());
	}

	public static CacheNote getSingleton() {
		if (singleton == null) {
			singleton = new CacheNote();
		}
		return singleton;
	}


	public INote getNote(final String id) {
		return getObjectById(noteDAO, id);
	}

	public Map<String, INote> getAllNote() {
		return getAll(noteDAO);
	}

	public List<INote> getAllNoteForUtente() {
		final ArrayList<INote> listaNote = new ArrayList<>();
		final Map<String, INote> mappa = getAllNote();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		if (mappa != null && utente != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();

			while (chiavi.hasNext()) {
				addNota(listaNote, mappa, utente, chiavi);
			}
		}
		return listaNote;
	}

	private void addNota(final ArrayList<INote> listaNote, final Map<String, INote> mappa, final Utenti utente,
			final Iterator<String> chiavi) {
		final INote nota = mappa.get(chiavi.next());
		if(nota !=null){
			boolean utenteValued = nota.getUtenti() != null || "guest".equals(nota.getUtenti().getnome());
			if (utenteValued && nota.getUtenti().getidUtente() == utente.getidUtente()) {
				listaNote.add(nota);
			}
		}
	}

	public List<INote> getAllNoteForUtenteEAnno() {
		final ArrayList<INote> listaNote = new ArrayList<>();
		final Map<String, INote> mappa = getAllNote();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		final String annoDaText = Integer.toString(Impostazioni.getAnno());

		if (mappa != null && utente != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();

			while (chiavi.hasNext()) {
				addNote(listaNote, mappa, utente, annoDaText, chiavi);
			}
		}
		return listaNote;
	}

	private void addNote(final ArrayList<INote> listaNote, final Map<String, INote> mappa, final Utenti utente,
			final String annoDaText, final Iterator<String> chiavi) {
		final INote nota = mappa.get(chiavi.next());
		if (nota != null && (nota.getUtenti() != null || "guest".equals(nota.getUtenti().getnome()))) {
			final String annoNota = nota.getData().substring(0, 4);
			if (nota.getUtenti().getidUtente() == utente.getidUtente() && annoNota.equals(annoDaText)) {
				listaNote.add(nota);
			}
		}
	}

}
