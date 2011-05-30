package business.comandi;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import view.FinestraListaComandi;
import view.font.TableF;
import business.Controllore;
import business.InizializzatoreFinestre;

public class CommandManager {

	private ArrayList<AbstractCommand> history  = new ArrayList<AbstractCommand>();
	private int                        indiceCorrente;
	private static CommandManager      instance = new CommandManager();

	private CommandManager() {

	}

	public static CommandManager getIstance() {
		return instance;
	}

	public boolean undo(final String tipo) {
		if (history.size() > 0 && indiceCorrente > -1
		                && indiceCorrente < history.size()) {
			final AbstractCommand comando = history.get(indiceCorrente);
			if (indiceCorrente > 0) {
				if (comando.undoCommand(tipo)) {
					System.out
					                .println("operazione undo su comando all'indice: "
					                                + indiceCorrente);
					indiceCorrente--;
					System.out.println("indice spostato a: " + indiceCorrente);
					return true;
				}
			}
		}
		return false;
	}

	public boolean redo(final String tipo) {
		if (history.size() > 0 && indiceCorrente > -1
		                && indiceCorrente < history.size()) {
			final AbstractCommand comando = history.get(indiceCorrente);
			if (indiceCorrente < history.size() - 1) {
				if (comando.doCommand(tipo)) {
					System.out.println("operazione undo su comando all'indice: " + indiceCorrente);
					indiceCorrente++;
					System.out.println("indice spostato a: " + indiceCorrente);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * con il tipo si decide di aggiornare i pannelli riguardanti l'entrata o
	 * l'uscita. altrimenti aggiorna tutto
	 * 
	 * @param comando
	 * @param tipo
	 * @return
	 */
	public boolean invocaComando(final AbstractCommand comando, final String tipo) {
		if (comando instanceof UndoCommand) {
			if (undo(tipo)) {
				aggiornaFinestraHistory();
				return true;
			}
		} else if (comando instanceof RedoCommand) {
			if (redo(tipo)) {
				aggiornaFinestraHistory();
				return true;
			}
		} else {
			if (comando.doCommand(tipo)) {
				history.add(comando);
				indiceCorrente = history.size() - 1;
				aggiornaFinestraHistory();
				return true;
			}
		}
		return false;
	}

	public AbstractCommand getLast(final Class<AbstractCommand> nomeClasse) {
		AbstractCommand ultimoCommand = null;
		if (history.size() > 0) {
			for (int i = history.size() - 1; i <= 0; i--) {
				if (history.get(i).getClass().equals(nomeClasse)) {
					ultimoCommand = history.get(i);
					break;
				}
			}
		}
		return ultimoCommand;
	}

	public List<AbstractCommand> getHistory() {
		return history;
	}

	private void aggiornaFinestraHistory() {
		TableF table;
		JScrollPane scrollPane = null;
		try {
			table = ((FinestraListaComandi)Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_HISTORY, null)).getTable();
			scrollPane = ((FinestraListaComandi)Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_HISTORY, null)).getScrollPane();
		} catch (Exception e) {
			e.printStackTrace();
		}
		table = new TableF(generaDati(), new String[] { "ListaComandi" });
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(false);
	}

	// TODO da spostare...
	public Object[][] generaDati() {
		final int numeroColonne = 1;
		final ArrayList<AbstractCommand> listaComandi = (ArrayList<AbstractCommand>) getHistory();
		final Object[][] dati = new Object[listaComandi.size()][numeroColonne];
		for (int i = 0; i < listaComandi.size(); i++) {
			dati[i][0] = listaComandi.get(i);

		}
		return dati;
	}
}
