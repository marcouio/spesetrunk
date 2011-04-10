package business.comandi;

public interface ICommand {

	public boolean execute();
	public boolean unExecute();
	boolean undoCommand(String tipo);
	boolean doCommand(String tipo);
}
