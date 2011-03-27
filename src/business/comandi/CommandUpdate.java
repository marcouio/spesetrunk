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
	public void execute() {
		super.execute();
		wrap.update(newEntita);
	}

	@Override
	public void unExecute() {
		super.unExecute();
		wrap.update(oldEntita);
	}
	
	
	
}
