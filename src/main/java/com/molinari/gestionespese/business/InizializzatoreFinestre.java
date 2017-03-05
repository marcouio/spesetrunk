package com.molinari.gestionespese.business;

import java.awt.Container;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.UIManager;

import com.molinari.gestionespese.view.FinestraListaComandi;
import com.molinari.gestionespese.view.GeneralFrame;
import com.molinari.gestionespese.view.componenti.componentipannello.PannelloAScomparsa;
import com.molinari.gestionespese.view.note.MostraNoteView;
import com.molinari.gestionespese.view.report.ReportView;

import controller.ControlloreBase;
import grafica.componenti.alert.Alert;
import grafica.componenti.contenitori.PannelloBase;
import math.UtilMath;

public class InizializzatoreFinestre {

	public static final int INDEX_PANNELLODATI = 0;
	public static final int INDEX_HISTORY = 1;
	public static final int INDEX_NOTE = 2;
	public static final int INDEX_REPORT = 3;

	protected static Finestra pannelloDati;
	protected static Finestra historyCommands;
	protected static Finestra pannelloNote;
	protected static Finestra report;

	Finestra[] finestre;
	ArrayList<Class<? extends Finestra>> finestreClass;
	private PannelloBase pannello;

	Finestra finestraVisibile;

	public InizializzatoreFinestre() {
		
		GeneralFrame generalFrame = Controllore.getSingleton().getGeneralFrame();
		pannello = new PannelloBase(generalFrame);
		pannello.setSize((int) UtilMath.getPercentage(generalFrame.getWidth(), 17), generalFrame.getHeight());
		// inizializzo l'array list con le class per evitare di caricare tutto
		// all'avvio del programma
		this.finestreClass = new ArrayList<>();
		finestreClass.add(PannelloAScomparsa.class);
		finestreClass.add(FinestraListaComandi.class);
		finestreClass.add(MostraNoteView.class);
		finestreClass.add(ReportView.class);
		this.finestre = new Finestra[finestreClass.size()];

	}

	/**
	 * Restituisce la finestra chiamata all'indice specifico. Se la finestra è
	 * stata già creata la prende altrimenti crea l'istanza
	 *
	 * @param index
	 * @param view
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Finestra getFinestra(final int index, final Container view) {
		Finestra window = finestre[index];
		try {

			if (finestre[index] == null) {
				window = creaIstanza(index, view);
				if(window != null){
					getPannello().add(window.getContainer());
					window.getContainer().setBounds(0, 0, getPannello().getWidth(), getPannello().getHeight());
				}
			}

			UIManager.setLookAndFeel(Controllore.getSingleton().getLookUsato());

			
		} catch (final Exception e) {
			Alert.segnalazioneErroreGrave(Alert.getMessaggioErrore(e.getMessage()));
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return window;
	}

	/**
	 * crea una nuova istanza della finestra chiamata all'indice dell'arraylist
	 * finestreClass
	 *
	 * @param index
	 * @param view
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Finestra creaIstanza(final int index, final Container view) throws InstantiationException, IllegalAccessException {
		final Class<? extends Finestra> finestra = finestreClass.get(index);
		Finestra newFinestra = null;
		try {
			newFinestra = finestra.getConstructor(Container.class).newInstance(view);
			newFinestra.getContainer().setVisible(false);
		} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		finestre[index] = newFinestra;
		return newFinestra;
	}

	/**
	 * Gestisce la visibilita: è visibile mai più di una finestra. Se la
	 * finestra scelta è già visibile viene tolta la visibilità anche a quella
	 *
	 * @param finestraVisibile
	 * @param menu
	 * @param menuItem
	 */
	public void setVisibilitaFinestre(final Finestra finestraVisibile, final JMenu menu, final JCheckBoxMenuItem menuItem) {
		final boolean visibile = finestraVisibile.getContainer().isVisible();
		// oscura tutte le finestre
		for (final Finestra finestra : finestre) {
			if (finestra != null) {
				finestra.getContainer().setVisible(false);
			}
		}
		// gestisce il check del menu
		for (int i = 0; i < menu.getMenuComponents().length; i++) {
			final JCheckBoxMenuItem item = (JCheckBoxMenuItem) menu.getMenuComponents()[i];
			item.setSelected(false);
		}
		// se già visibile oscura la finestra passata come parametro, altrimenti la visualizza
		if (visibile) {
			menuItem.setSelected(false);
			finestraVisibile.getContainer().setVisible(false);
			getPannello().setVisible(false);
		} else {
			menuItem.setSelected(true);
			finestraVisibile.getContainer().setVisible(true);
			setFinestraVisibile(finestraVisibile);
			getPannello().setVisible(true);
		}
	}

	public void quitFinestre() {
		for (final Finestra finestra : finestre) {
			finestra.getContainer().setVisible(false);
		}
	}

	public Finestra getFinestraVisibile() {
		return finestraVisibile;
	}

	public void setFinestraVisibile(final Finestra finestraVisibile) {
		this.finestraVisibile = finestraVisibile;
	}

	public Finestra[] getFinestre() {
		return finestre;
	}

	public void setFinestre(final Finestra[] finestre) {
		this.finestre = finestre;
	}

	public PannelloBase getPannello() {
		return pannello;
	}

	public void setPannello(PannelloBase pannello) {
		this.pannello = pannello;
	}
}
