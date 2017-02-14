package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import business.Controllore;
import domain.Note;
import domain.Utenti;
import domain.wrapper.WrapNote;
import view.impostazioni.Impostazioni;

public class CacheNote extends AbstractCacheBase<Note> {

	private static CacheNote singleton;
	private WrapNote noteDAO = new WrapNote();

	private CacheNote() {
		setCache(new HashMap<String, Note>());
	}

	public static CacheNote getSingleton() {
		if (singleton == null) {
			singleton = new CacheNote();
		}
		return singleton;
	}


	public Note getNote(final String id) {
		return getObjectById(noteDAO, id);
	}

	public Map<String, Note> getAllNote() {
		return getAll(noteDAO);
	}

	public List<Note> getAllNoteForUtente() {
		final ArrayList<Note> listaNote = new ArrayList<Note>();
		final Map<String, Note> mappa = getAllNote();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		if (mappa != null && utente != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();

			while (chiavi.hasNext()) {
				final Note nota = (Note) mappa.get(chiavi.next());
				if (nota != null && (nota.getUtenti() != null || nota.getUtenti().getnome().equals("guest"))) {
					if (nota.getUtenti().getidUtente() == utente.getidUtente()) {
						listaNote.add(nota);
					}
				}
			}
		}
		return listaNote;
	}

	public ArrayList<Note> getAllNoteForUtenteEAnno() {
		final ArrayList<Note> listaNote = new ArrayList<Note>();
		final Map<String, Note> mappa = getAllNote();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		final String annoDaText = Impostazioni.getSingleton().getAnnotextField().getText();

		if (mappa != null && utente != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();

			while (chiavi.hasNext()) {
				final Note nota = (Note) mappa.get(chiavi.next());
				if (nota != null && (nota.getUtenti() != null || nota.getUtenti().getnome().equals("guest"))) {
					final String annoNota = nota.getData().substring(0, 4);
					if (nota.getUtenti().getidUtente() == utente.getidUtente() && annoNota.equals(annoDaText)) {
						listaNote.add(nota);
					}
				}
			}
		}
		return listaNote;
	}

}
