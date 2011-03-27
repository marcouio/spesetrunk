package view.componenti.componentiPannello;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Database;
import java.awt.Dimension;


public class SottoPannelloDatiEntrate extends view.OggettoVistaBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new SottoPannelloDatiEntrate());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private static JTextField EntrateMesePrec;
	private static JTextField EnAnCorso;
	private static JTextField EnMeCorso;
	
	public SottoPannelloDatiEntrate() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			JLabel label_1 = new LabelTesto("Anno in corso");
			label_1.setBounds(164, 66, 141, 14);
			this.add(label_1);

			JLabel label_2 = new LabelTesto("Mese precedente");
			label_2.setBounds(16, 67, 136, 14);
			this.add(label_2);

			JLabel label_3 = new LabelTesto("Mese in corso");
			label_3.setBounds(317, 67, 113, 14);
			this.add(label_3);
			JLabel label_4 = new LabelTitolo("Entrate Dati");
			label_4.setBounds(177, 25, 164, 19);
			this.add(label_4);
			
			EntrateMesePrec = new TextFieldF();
	        double Emensile = Database.Emensile();
	        EntrateMesePrec.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(Emensile)));
			EntrateMesePrec.setBounds(16, 85, 106, 27);
			this.add(EntrateMesePrec);
			EntrateMesePrec.setColumns(10);
	
			EnAnCorso = new TextFieldF();
			
	        double EAnnuale = Database.EAnnuale();
	        EnAnCorso.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(EAnnuale)));
			EnAnCorso.setBounds(164, 84, 106, 27);
			this.add(EnAnCorso);
			EnAnCorso.setColumns(10);

			EnMeCorso = new TextFieldF();
	        double EMensile = Database.EMensileInCorso();
	        EnMeCorso.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(EMensile)));
			EnMeCorso.setBounds(317, 85, 106, 27);
			this.add(EnMeCorso);
			EnMeCorso.setColumns(10);
			
			JSeparator separator2 = new JSeparator();
			separator2.setBounds(10, 128, 420, 16);
			this.add(separator2);
			
			this.setPreferredSize(new Dimension(440, 150));
			this.setLayout(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static JTextField getEntrateMesePrec() {	
		return SottoPannelloDatiEntrate.EntrateMesePrec;
	  
}

public static void setEntrateMesePrec(JTextField entrateMesePrec) {
	EntrateMesePrec = entrateMesePrec;
}

public static JTextField getEnAnCorso() {
	return EnAnCorso;
}

public static void setEnAnCorso(JTextField enAnCorso) {
	EnAnCorso = enAnCorso;
}

public static JTextField getEnMeCorso() {
	return EnMeCorso;
}

public static void setEnMeCorso(JTextField enMeCorso) {
	EnMeCorso = enMeCorso;
}

}
