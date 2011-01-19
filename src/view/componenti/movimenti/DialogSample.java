package view.componenti.movimenti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogSample extends WindowAdapter implements ActionListener {
	/** Punto d'entrata dell'applicazione. Affida all'AWTEventDispatcher
	l'esecuzione del metodo startApp di un DialogSample.*/
	public static void main(String...args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new DialogSample().startApp();
			}
		});
	}
	
	private JFrame frame = new JFrame("DialogSample");
	private JMenuItem closeApp, openDialog;
	private DialogHandler2 dialogHandler = new DialogHandler2(frame);
	
	/** Avvia l'applicazione. Non awt-safe*/
	public void startApp() {
		JMenuBar menuBar = new JMenuBar();
		JMenu mainMenu = menuBar.add(new JMenu("Main"));
		openDialog = mainMenu.add("Apri finestra di dialogo");
		closeApp = mainMenu.add("Chiudi applicazione");
		closeApp.addActionListener(this);
		openDialog.addActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(this);
		frame.setJMenuBar(menuBar);
		frame.setSize(640, 480);
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeApp) {
			killApp();
		} else if(e.getSource() == openDialog) {
			dialogHandler.showDialog();
		}
	}
	
	public void windowClosing(WindowEvent e) {
		if(e.getSource() == frame) {
			killApp();
		}
	}
	
	/** Chiude l'applicazione */
	public void killApp() {
		dialogHandler.disposeDialog();
		frame.dispose();
	}
}