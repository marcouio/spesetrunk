package business.comandi;

import java.util.LinkedList;
import java.util.List;

public class CommandManager {

	private List<AbstractCommand> history = new LinkedList<AbstractCommand>();
	private int indiceCorrente;
	private static CommandManager instance = new CommandManager();
	
	private CommandManager(){
		
	}
	public static CommandManager getIstance(){
		return instance;
	}
	
	public void undo() {
		if(history.size()>0 && indiceCorrente<1){
			AbstractCommand comando = history.get(indiceCorrente);
			comando.unExecute();
			
			indiceCorrente--;
		}
	}
	public void redo() {
		if(history.size()>0 && indiceCorrente<1){
			AbstractCommand comando = history.get(indiceCorrente);
			comando.execute();
			
			indiceCorrente++;
		}
	}
	public void invocaComando(AbstractCommand comando) {
		if(comando instanceof UndoCommand){
			undo();
			return;
		}
		else if(comando instanceof UndoCommand){
			redo();
			return;
		}
		else{
			comando.execute();
			history.add(comando);
			indiceCorrente = history.size()-1;
		}
		
	}
	public AbstractCommand getLast(Class nomeClasse) {
		AbstractCommand ultimoCommand = null;
		for(int i=history.size()-1;i<=0;i--){
			if(history.get(i).getClass().equals(nomeClasse)){
				ultimoCommand = history.get(i);
				break;
			}
		}
		return ultimoCommand;
	}
}
