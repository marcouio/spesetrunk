package view;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import business.Controllore;

public class MyWindowListener extends WindowAdapter implements ComponentListener {

	MyWindowListener() {
		super();
	}

	@Override
	public void windowActivated(WindowEvent e) {
		if (Controllore.getWindowVisibile() != null) {
			Controllore.getWindowVisibile().setVisible(true);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// ArrayList<JFrame> finestre = Controllore.getFinestre();
		// for (Iterator<JFrame> iterator = finestre.iterator();
		// iterator.hasNext();) {
		// JFrame jFrame = iterator.next();
		// jFrame.setVisible(false);
		// }
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		if (Controllore.getWindowVisibile() != null) {
			Controllore.getWindowVisibile().setVisible(true);
		}
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		ArrayList<JFrame> finestre = Controllore.getFinestre();
		for (Iterator<JFrame> iterator = finestre.iterator(); iterator.hasNext();) {
			JFrame jFrame = iterator.next();
			jFrame.setVisible(false);
		}
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		if (Controllore.getWindowVisibile() != null) {
			Controllore.getWindowVisibile().setVisible(true);
		}
		GeneralFrame.relocateFinestreLaterali();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		Controllore.getSingleton().quit();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		ArrayList<JFrame> finestre = Controllore.getFinestre();
		for (Iterator<JFrame> iterator = finestre.iterator(); iterator.hasNext();) {
			JFrame jFrame = iterator.next();
			jFrame.setVisible(false);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		GeneralFrame.resizeView();
		GeneralFrame.relocateFinestreLaterali();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		GeneralFrame.relocateFinestreLaterali();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}
}
