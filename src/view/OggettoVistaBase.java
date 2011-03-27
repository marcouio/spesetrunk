package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import business.AltreUtil;


public class OggettoVistaBase extends JPanel{
	protected static Logger log;
	protected Font titolo;
	
	public OggettoVistaBase(GridLayout gridLayout) {
		super(gridLayout);
	}
	
	public OggettoVistaBase() {
		super();
		titolo = new Font("Tahoma", Font.BOLD | Font.ITALIC, 14);
		log = AltreUtil.getLog();
	
	}


	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;

	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame inst = new JFrame();	
				inst.setSize(950, 700);
				inst.getContentPane().add(new OggettoVistaBase());
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				
			}
		});
	}
	
}
