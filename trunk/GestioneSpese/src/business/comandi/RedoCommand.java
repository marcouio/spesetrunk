package business.comandi;

public class RedoCommand extends AbstractCommand {

	@Override
	public boolean execute() {
		return true;
	}

	@Override
	public boolean unExecute() {
		return true;
	}

	@Override
	public String toString() {
		return "Effettuato comando 'Avanti'";
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {

	}

}
