package com.molinari.gestionespese.business.comandi;

public interface ICommand {

	/**
	 * Metodo implementato dai singoli comandi per eseguire la specifica
	 * operazione
	 * 
	 * @return true se il comando è andato a buon fine
	 */
	public boolean execute();

	/**
	 * Metodo implementato dai singoli comandi per eseguire l'undo della
	 * specifica operazione
	 * 
	 * @return true se il comando è andato a buon fine
	 */
	public boolean unExecute();

	/**
	 * Metodo implementato all'interno di AbstractCommand, richiama l'unExecute
	 * del comando
	 * 
	 * @return
	 */
	public boolean undoCommand();

	/**
	 * Metodo implementato all'interno di AbstractCommand, richiama l'execute
	 * del comando
	 * 
	 * @return
	 */
	public boolean doCommand();

	/**
	 * Metodo implementato all'interno dei singoli comandi, scrive il log
	 * generato dall'esecuzione del comando
	 * 
	 * @param isComandoEseguito
	 */
	public void scriviLogExecute(boolean isComandoEseguito);

	/**
	 * Metodo implementato all'interno dei singoli comandi, scrive il log
	 * generato dall'esecuzione del comando
	 * 
	 * @param isComandoEseguito
	 */
	public void scriviLogUnExecute(boolean isComandoEseguito);
}
