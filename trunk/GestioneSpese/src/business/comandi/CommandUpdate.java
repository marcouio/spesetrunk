package business.comandi;

import domain.AbstractOggettoEntita;
import domain.wrapper.IWrapperEntity;

public class CommandUpdate extends AbstractCommand{

	final private AbstractOggettoEntita newEntita;
	final private AbstractOggettoEntita oldEntita;
	final private IWrapperEntity wrap;
	
	public CommandUpdate(AbstractOggettoEntita oldEntita,AbstractOggettoEntita newEntita, IWrapperEntity wrap) {
		this.newEntita = newEntita;
		this.oldEntita = oldEntita;
		this.wrap = wrap;
	}

	@Override
	public boolean execute() {
		if(wrap.update(newEntita)){
			return true;
		}else return false;
	}

	@Override
	public boolean unExecute() {
		if(wrap.update(oldEntita)){
			return true;
		}else return false;
	}
	
	
	
}
