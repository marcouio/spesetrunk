package business.comandi;

import java.util.HashMap;

import domain.AbstractOggettoEntita;
import domain.wrapper.IWrapperEntity;

public class AbstractCommand implements ICommand{

	protected AbstractOggettoEntita entita;
	protected IWrapperEntity wrap;
	protected HashMap<String, AbstractOggettoEntita> mappaCache;
	
	@Override
	public void execute() {
		
	}

	@Override
	public void unExecute() {
		
	}
	
	

}
