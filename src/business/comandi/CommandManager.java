package business.comandi;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JScrollPane;

import view.font.TableF;
import business.Controllore;

public class CommandManager {

	private static final List<AbstractCommand> history  = new LinkedList<AbstractCommand>();
	private int                                indiceCorrente;
	private static CommandManager              instance = new CommandManager();

	private CommandManager() {

	}

	public static CommandManager getIstance() {
		return instance;
	}

	public boolean undo(String tipo) {
		if (history.size() > 0 && indiceCorrente > -1 && indiceCorrente < history.size()) {
			AbstractCommand comando = history.get(indiceCorrente);
			if (indiceCorrente > 0) {
				if (comando.undoCommand(tipo)) {
					System.out.println("operazione undo su comando all'indice: " + indiceCorrente);
					indiceCorrente--;
					System.out.println("indice spostato a: " + indiceCorrente);
					return true;
				}
			}
		}
		return false;
	}

	public boolean redo(String tipo) {
		if (history.size() > 0 && indiceCorrente > -1 && indiceCorrente < history.size()) {
			AbstractCommand comando = history.get(indiceCorrente);
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
	public boolean invocaComando(AbstractCommand comando, String tipo) {
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

	public AbstractCommand getLast(Class<AbstractCommand> nomeClasse) {
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
		TableF table = Controllore.getFinestraHistory().getTable();
		table = new TableF(generaDati(), new String[] { "ListaComandi" });
		JScrollPane scrollPane = Controllore.getFinestraHistory().getScrollPane();
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(false);
	}

	// TODO da spostare...
	public Object[][] generaDati() {
		int numeroColonne = 1;
		LinkedList<AbstractCommand> listaComandi = (LinkedList<AbstractCommand>) getHistory();
		Object[][] dati = new Object[listaComandi.size()][numeroColonne];
		for (int i = 0; i < listaComandi.size(); i++) {
			dati[i][0] = listaComandi.get(i);

		}
		return dati;
	}
}
