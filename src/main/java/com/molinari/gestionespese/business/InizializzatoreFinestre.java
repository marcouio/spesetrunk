package com.molinari.gestionespese.business;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.UIManager;

import com.molinari.gestionespese.view.FinestraListaComandi;
import com.molinari.gestionespese.view.componenti.componentipannello.PannelloAScomparsa;
import com.molinari.gestionespese.view.note.MostraNoteView;
import com.molinari.gestionespese.view.report.ReportView;

import controller.ControlloreBase;
import grafica.componenti.alert.Alert;
import grafica.componenti.contenitori.FrameBase;

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

	Finestra finestraVisibile;

	public InizializzatoreFinestre() {
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
	public Finestra getFinestra(final int index, final FrameBase view) {
		Finestra window = null;
		try {

			if (finestre[index] == null) {
				return creaIstanza(index, view);
			} else {
				window = finestre[index];
				if (view != null) {
					window.getContainer().setBounds(view.getX() + view.getWidth(), view.getY(), 250, 425);
				}

				UIManager.setLookAndFeel(Controllore.getSingleton().getLookUsato());

			}
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
	private Finestra creaIstanza(final int index, final FrameBase view) throws InstantiationException, IllegalAccessException {
		final Class<? extends Finestra> finestra = finestreClass.get(index);
		Finestra newFinestra = null;
		try {
			newFinestra = finestra.getConstructor(FrameBase.class).newInstance(view);
		} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		finestre[index] = newFinestra;
		
		if (view != null && newFinestra != null) {
			newFinestra.getContainer().setBounds(view.getX() + view.getWidth(), view.getY(), 250, 425);
			newFinestra.getContainer().setVisible(false);
		}
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
		// se già visibile oscura la finestra passata come parametro, altrimenti
		// la visualizza
		if (visibile) {
			menuItem.setSelected(false);
			finestraVisibile.getContainer().setVisible(false);
		} else {
			menuItem.setSelected(true);
			finestraVisibile.getContainer().setVisible(true);
			setFinestraVisibile(finestraVisibile);
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
}
