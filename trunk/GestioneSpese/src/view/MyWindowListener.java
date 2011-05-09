package view;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import business.Controllore;

public class MyWindowListener extends WindowAdapter implements ComponentListener {

	MyWindowListener() {
		super();
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		Controllore.getFinestraHistory().setVisible(true);
		GeneralFrame.relocateFinestreLaterali();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		Controllore.getSingleton().quit();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		Controllore.getFinestraHistory().setVisible(false);
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
