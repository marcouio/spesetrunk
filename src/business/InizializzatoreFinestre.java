package business;

import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;

import view.FinestraListaComandi;
import view.Report;
import view.componenti.componentiPannello.PannelloAScomparsa2;
import view.note.MostraNoteView;

public class InizializzatoreFinestre {

	public static final int INDEX_PANNELLODATI = 0;
	public static final int INDEX_HISTORY = 1;
	public static final int INDEX_NOTE = 2;
	public static final int INDEX_REPORT = 3;

	protected static PannelloAScomparsa2 pannelloDati;
	protected static FinestraListaComandi historyCommands;
	protected static MostraNoteView pannelloNote;
	protected static Report report;

	JFrame[] finestre;
	ArrayList<Class> finestreClass;

	JFrame finestraVisibile;

	public InizializzatoreFinestre() {
		this.finestreClass = new ArrayList<Class>();
		finestreClass.add(PannelloAScomparsa2.class);
		finestreClass.add(FinestraListaComandi.class);
		finestreClass.add(MostraNoteView.class);
		finestreClass.add(Report.class);
		this.finestre = new JFrame[finestreClass.size()];

	}

	public JFrame getFinestra(final int index, final JFrame view)
			throws InstantiationException, IllegalAccessException {
		if (finestre[index] == null) {
			return creaIstanza(index, view);
		} else {
			final JFrame window = finestre[index];
			if (view != null) {
				window.setBounds(view.getX() + view.getWidth(), view.getY(),
						250, 425);
			}
			return window;
		}
	}

	private JFrame creaIstanza(final int index, final JFrame view)
			throws InstantiationException, IllegalAccessException {
		final Class<JFrame> finestra = finestreClass.get(index);
		final JFrame newFinestra = finestra.newInstance();
		finestre[index] = newFinestra;

		if (view != null) {
			newFinestra.setBounds(view.getX() + view.getWidth(), view.getY(),
					250, 425);
			newFinestra.setVisible(false);
		}
		return newFinestra;
	}

	public void setVisibilitaFinestre(final JFrame finestraVisibile,
			final JMenu menu, final JCheckBoxMenuItem menuItem) {
		final boolean visibile = finestraVisibile.isVisible();
		for (final JFrame finestra : finestre) {
			if (finestra != null) {
				finestra.setVisible(false);
			}
		}
		for (int i = 0; i < menu.getMenuComponents().length; i++) {
			final JCheckBoxMenuItem item = (JCheckBoxMenuItem) menu
					.getMenuComponents()[i];
			item.setSelected(false);
		}
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
		for (int i = 0; i < finestre.length; i++) {
			final JFrame finestra = finestre[i];
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
