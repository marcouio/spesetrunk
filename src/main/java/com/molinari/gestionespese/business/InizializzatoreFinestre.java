package com.molinari.gestionespese.business;

import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.UIManager;

import com.molinari.gestionespese.view.FinestraListaComandi;
import com.molinari.gestionespese.view.componenti.componentipannello.PannelloAScomparsa;
import com.molinari.gestionespese.view.note.MostraNoteView;
import com.molinari.gestionespese.view.report.ReportView;

import grafica.componenti.alert.Alert;

public class InizializzatoreFinestre {

	public static final int INDEX_PANNELLODATI = 0;
	public static final int INDEX_HISTORY = 1;
	public static final int INDEX_NOTE = 2;
	public static final int INDEX_REPORT = 3;

	protected static PannelloAScomparsa pannelloDati;
	protected static FinestraListaComandi historyCommands;
	protected static MostraNoteView pannelloNote;
	protected static ReportView report;

	JFrame[] finestre;
	@SuppressWarnings("rawtypes")
	ArrayList<Class> finestreClass;

	JFrame finestraVisibile;

	@SuppressWarnings("rawtypes")
	public InizializzatoreFinestre() {
		// inizializzo l'array list con le class per evitare di caricare tutto
		// all'avvio del programma
		this.finestreClass = new ArrayList<>();
		finestreClass.add(PannelloAScomparsa.class);
		finestreClass.add(FinestraListaComandi.class);
		finestreClass.add(MostraNoteView.class);
		finestreClass.add(ReportView.class);
		this.finestre = new JFrame[finestreClass.size()];

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
	public JFrame getFinestra(final int index, final JFrame view) throws InstantiationException, IllegalAccessException {
		if (finestre[index] == null) {
			return creaIstanza(index, view);
		} else {
			final JFrame window = finestre[index];
			if (view != null) {
				window.setBounds(view.getX() + view.getWidth(), view.getY(), 250, 425);
			}
			try {
				UIManager.setLookAndFeel(Controllore.getSingleton().getLookUsato());

			} catch (final Exception e) {
				Alert.segnalazioneErroreGrave(Alert.getMessaggioErrore(e.getMessage()));
			}
			return window;
		}
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
	private JFrame creaIstanza(final int index, final JFrame view) throws InstantiationException, IllegalAccessException {
		@SuppressWarnings("unchecked")
		final Class<JFrame> finestra = finestreClass.get(index);
		final JFrame newFinestra = finestra.newInstance();
		finestre[index] = newFinestra;

		if (view != null) {
			newFinestra.setBounds(view.getX() + view.getWidth(), view.getY(), 250, 425);
			newFinestra.setVisible(false);
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
	public void setVisibilitaFinestre(final JFrame finestraVisibile, final JMenu menu, final JCheckBoxMenuItem menuItem) {
		final boolean visibile = finestraVisibile.isVisible();
		// oscura tutte le finestre
		for (final JFrame finestra : finestre) {
			if (finestra != null) {
				finestra.setVisible(false);
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
			finestraVisibile.setVisible(false);
		} else {
			menuItem.setSelected(true);
			finestraVisibile.setVisible(true);
			setFinestraVisibile(finestraVisibile);
		}
	}

	public void quitFinestre() {
		for (final JFrame finestra : finestre) {
			finestra.setVisible(false);
			finestra.dispose();
		}
	}

	public JFrame getFinestraVisibile() {
		return finestraVisibile;
	}

	public void setFinestraVisibile(final JFrame finestraVisibile) {
		this.finestraVisibile = finestraVisibile;
	}

	public JFrame[] getFinestre() {
		return finestre;
	}

	public void setFinestre(final JFrame[] finestre) {
		this.finestre = finestre;
	}
}
