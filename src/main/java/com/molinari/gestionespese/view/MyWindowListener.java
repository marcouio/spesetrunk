package com.molinari.gestionespese.view;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.business.InizializzatoreFinestre;

public class MyWindowListener extends WindowAdapter implements WindowFocusListener, ComponentListener, MouseListener {

	private final GeneralFrame view;

	public MyWindowListener(final GeneralFrame view) {
		super();
		this.view = view;
	}


	@Override
	public void windowDeiconified(final WindowEvent e) {
		if (Controllore.getSingleton().getInitFinestre().getFinestraVisibile() != null) {
			Controllore.getSingleton().getInitFinestre().getFinestraVisibile().getContainer().setVisible(true);
		}
		view.relocateFinestreLaterali();
	}

	@Override
	public void windowClosed(final WindowEvent e) {
		Controllore.getSingleton().quit();
		Controllore.getSingleton().getInitFinestre().quitFinestre();
	}

	@Override
	public void windowIconified(final WindowEvent e) {
		final Finestra[] finestre = Controllore.getSingleton().getInitFinestre().getFinestre();
		for (final Finestra jFrame : finestre) {

			if (jFrame != null) {
				jFrame.getContainer().setVisible(false);
			}
		}
	}

	@Override
	public void componentResized(final ComponentEvent e) {
		view.relocateFinestreLaterali();
	}

	@Override
	public void componentMoved(final ComponentEvent e) {
		final InizializzatoreFinestre initFinestre = Controllore.getSingleton().getInitFinestre();
		if (initFinestre.getFinestraVisibile() != null) {
			initFinestre.getFinestraVisibile().getContainer().setVisible(true);
		}
		view.relocateFinestreLaterali();
	}

	@Override
	public void componentShown(final ComponentEvent e) {
		view.relocateFinestreLaterali();

	}

	@Override
	public void componentHidden(final ComponentEvent e) {
		//do nothing
	}

	@Override
	public void windowGainedFocus(final WindowEvent e) {
		//do nothing
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		//do nothing
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		final Finestra finVisibile = Controllore.getSingleton().getInitFinestre().getFinestraVisibile();
		if (finVisibile != null) {
			finVisibile.getContainer().setVisible(true);
			finVisibile.getContainer().invalidate();
			finVisibile.getContainer().repaint();
		}

	}

}
