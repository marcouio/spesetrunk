package business.comandi;

import domain.AbstractOggettoEntita;
import domain.wrapper.IWrapperEntity;

public class CommandUpdate extends AbstractCommand {

	final private AbstractOggettoEntita newEntita;
	final private AbstractOggettoEntita oldEntita;
	final private IWrapperEntity wrap;

	public CommandUpdate(final AbstractOggettoEntita oldEntita, final AbstractOggettoEntita newEntita, final IWrapperEntity wrap) {
		this.newEntita = newEntita;
		this.oldEntita = oldEntita;
		this.wrap = wrap;
	}

	@Override
	public boolean execute() {
		if (wrap.update(newEntita)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean unExecute() {
		if (wrap.update(oldEntita)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {

	}

}
