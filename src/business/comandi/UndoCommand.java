package business.comandi;

public class UndoCommand extends AbstractCommand {

	@Override
	public boolean execute() {
		return true;
	}

	@Override
	public boolean unExecute() {
		return true;
	}

}