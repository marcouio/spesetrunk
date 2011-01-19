package view.grafici;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Grafici extends view.OggettoVistaBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Grafici());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setSize(
				700, 700);
		frame.setVisible(true);
	}

	private Grafico1 tabGrafico1;
	private Grafico2 tabGrafico2;
	private Grafico3 tabGrafico3;
	
	public Grafici() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			tabGrafico3 = new Grafico3();
			tabGrafico3.setBounds(26, 11, 380, 300);
//			tabGrafico3.setSize(700, 700);
			setLayout(null);
			this.add(tabGrafico3);
			
			tabGrafico1 = new Grafico1();
			tabGrafico1.setBounds(427, 11, 380, 300);
			add(tabGrafico1);
			tabGrafico2 = new Grafico2();
			tabGrafico2.setBounds(26, 287, 380, 300);
			add(tabGrafico2);
//			tabGenerale = new JTabbedPane();
//			tabGenerale.setBounds(65, 65, 550, 573);
//			tabGenerale.setSize(700, 700);
//			tabGenerale.addTab("Entrate", tabGrafico1);
//			tabGenerale.addTab("Uscite", tabGrafico2);
//			tabGenerale.addTab("Generali", tabGrafico3);
			
//			this.add(tabGenerale);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

}
