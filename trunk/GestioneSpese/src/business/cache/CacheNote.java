package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import view.impostazioni.Impostazioni;
import business.Controllore;
import domain.AbstractOggettoEntita;
import domain.Note;
import domain.Utenti;
import domain.wrapper.WrapNote;

public class CacheNote extends AbstractCacheBase {

	private static CacheNote singleton;

	private CacheNote() {
		cache = new HashMap<String, AbstractOggettoEntita>();
		caricata = false;
	}

	public static CacheNote getSingleton() {
		if (singleton == null) {
			synchronized (CacheNote.class) {
				if (singleton == null) {
					singleton = new CacheNote();
				}
			} // if
		} // if
		return singleton;
	}

	WrapNote noteDAO = new WrapNote();

	public Note getNote(String id) {
		Note note = (Note) cache.get(id);
		if (note == null) {
			note = caricaNota(id);
			if (note != null) {
				cache.put(id, note);
			}
		}
		return (Note) cache.get(id);
	}

	private Note caricaNota(String id) {
		return (Note) new WrapNote().selectById(Integer.parseInt(id));
	}

	public Map<String, AbstractOggettoEntita> chargeAllNote() {
		Vector<Object> note = noteDAO.selectAll();
		if (note != null && note.size() > 0) {
			for (int i = 0; i < note.size(); i++) {
				Note nota = (Note) note.get(i);
				int id = nota.getIdNote();
				if (cache.get(id) == null) {
					cache.put(Integer.toString(id), nota);
				}
			}
		}
		caricata = true;
		return cache;
	}

	public Map<String, AbstractOggettoEntita> getAllNote() {
		if (caricata)
			return cache;
		else
			return chargeAllNote();
	}

	public ArrayList<Note> getAllNoteForUtente() {
		ArrayList<Note> listaNote = new ArrayList<Note>();
		Map<String, AbstractOggettoEntita> mappa = getAllNote();
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		if (mappa != null && utente != null) {
			Iterator<String> chiavi = mappa.keySet().iterator();

			while (chiavi.hasNext()) {
				Note nota = (Note) mappa.get(chiavi.next());
				// TODO con l'id == 0 non fuziona, cambiare per nome
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
		ArrayList<Note> listaNote = new ArrayList<Note>();
		Map<String, AbstractOggettoEntita> mappa = getAllNote();
		Utenti utente = Controllore.getSingleton().getUtenteLogin();
		String annoDaText = Impostazioni.getSingleton().getAnnotextField().getText();

		if (mappa != null && utente != null) {
			Iterator<String> chiavi = mappa.keySet().iterator();

			while (chiavi.hasNext()) {
				Note nota = (Note) mappa.get(chiavi.next());
				if (nota != null && (nota.getUtenti() != null || nota.getUtenti().getnome().equals("guest"))) {
					String annoNota = nota.getData().substring(0, 4);
					if (nota.getUtenti().getidUtente() == utente.getidUtente() && annoNota.equals(annoDaText)) {
						listaNote.add(nota);
					}
				}
			}
		}
		return listaNote;
	}

}
