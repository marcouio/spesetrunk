package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.logging.Logger;

import javax.swing.JPanel;


public abstract class OggettoVistaBase extends JPanel{
	protected static Logger log;
	protected Font titolo;
	public OggettoVistaBase(GridLayout gridLayout) {
		super(gridLayout);
	}
	public OggettoVistaBase() {
		super();
		titolo = new Font("Tahoma", Font.BOLD | Font.ITALIC, 14);
//		log = AltreUtil.getLog();
	
	}


	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	
	
}
