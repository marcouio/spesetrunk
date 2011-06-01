package view;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

import business.Controllore;

public class MyWindowListener extends WindowAdapter implements WindowFocusListener, ComponentListener, MouseListener {

	private GeneralFrame view;

	public MyWindowListener(GeneralFrame view) {
		super();
		this.view = view;
	}

	//
	@Override
	public void windowDeiconified(WindowEvent e) {
		if (Controllore.getSingleton().getInitFinestre().getFinestraVisibile() != null) {
			Controllore.getSingleton().getInitFinestre().getFinestraVisibile().setVisible(true);
		}
		view.relocateFinestreLaterali();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		Controllore.getSingleton().quit();
		Controllore.getSingleton().getInitFinestre().quitFinestre();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		JFrame[] finestre = Controllore.getSingleton().getInitFinestre().getFinestre();
		for (int i = 0; i < finestre.length; i++) {

			JFrame jFrame = finestre[i];
			if (jFrame != null) {
				jFrame.setVisible(false);
			}
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		GeneralFrame.resizeView();
		view.relocateFinestreLaterali();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		if (Controllore.getSingleton().getInitFinestre().getFinestraVisibile() != null) {
			Controllore.getSingleton().getInitFinestre().getFinestraVisibile().setVisible(true);
			Controllore.getSingleton().getInitFinestre().getFinestraVisibile().setState(WindowEvent.WINDOW_DEICONIFIED);
		}
		view.relocateFinestreLaterali();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		view.relocateFinestreLaterali();

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		// if
		// (Controllore.getSingleton().getInitFinestre().getFinestraVisibile()
		// != null) {
		// Controllore.getSingleton().getInitFinestre().getFinestraVisibile().setVisible(true);
		// }
		// view.relocateFinestreLaterali();
		//

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		JFrame finVisibile = Controllore.getSingleton().getInitFinestre().getFinestraVisibile();
		if (finVisibile != null) {
			finVisibile.setVisible(true);
			finVisibile.invalidate();
			finVisibile.repaint();
		}

	}

}
