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

}
