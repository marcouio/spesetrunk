package view.componenti.componentiPannello;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.OggettoVistaBase;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Database;



public class SottoPannelloMesi extends OggettoVistaBase {

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
		frame.getContentPane().add(new SottoPannelloMesi());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private JLabel jLabel8;
	private JLabel jLabel9;
	private static JTextField totaleMeseUscite;
	private JLabel jLabel12;
	private static JComboBox ComboMese;
	private static JTextField totaleMeseEntrate;
	private JSeparator jSeparator1;
	
	public SottoPannelloMesi() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			jLabel8 = new LabelTitolo();
			this.add(jLabel8);
			jLabel8.setText("Mesi");
			jLabel8.setBounds(177, 25, 90, 19);
			
			jLabel9 = new LabelTesto();
			this.add(jLabel9);
			jLabel9.setText("Mese");
			jLabel9.setBounds(16, 67, 67, 14);

			totaleMeseUscite = new TextFieldF();
			this.add(totaleMeseUscite);
			totaleMeseUscite.setColumns(10);
			totaleMeseUscite.setText("0.0");
			totaleMeseUscite.setBounds(164, 84, 106, 27);
			
			totaleMeseEntrate = new TextFieldF();
			this.add(totaleMeseEntrate);
			totaleMeseEntrate.setColumns(10);
			totaleMeseEntrate.setText("0.0");
			totaleMeseEntrate.setBounds(317, 85, 106, 27);
	
			jLabel12 = new LabelTesto();
			this.add(jLabel12);
			jLabel12.setText("Uscite per mese");
			jLabel12.setBounds(164, 66, 114, 14);
			
			jLabel12 = new LabelTesto();
			this.add(jLabel12);
			jLabel12.setText("Entrate per mese");
			jLabel12.setBounds(317, 67, 109, 14);
			
			jSeparator1 = new JSeparator();
			this.add(jSeparator1);
			jSeparator1.setBounds(10, 128, 420, 16);
			
			
			//Combo Mesi
			ComboMese = new JComboBox();
			this.add(ComboMese);
			ComboMese.setBounds(16, 85, 106, 27);
			ComboMese.addItem("");
			for(int i=0; i<12; i++)
				ComboMese.addItem(i+1);
			ComboMese.setSelectedIndex(0);
			ComboMese.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					Object mounth = ComboMese.getSelectedItem();
					
					if (!mounth.equals("")) {
						int mese = Integer.parseInt(mounth.toString());
						double totaleMese= AltreUtil.arrotondaDecimaliDouble(Database.getSingleton().totaleUsciteMese(mese));
						double totaleMeseE = AltreUtil.arrotondaDecimaliDouble(Database.getSingleton().totaleEntrateMese(mese));
						totaleMeseUscite.setText(Double.toString(totaleMese));
						totaleMeseEntrate.setText(Double.toString(totaleMeseE));
					}
				}
			});
			
			
			this.setPreferredSize(new Dimension(440, 150));
			this.setLayout(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void azzeraCampi(){
		ComboMese.setSelectedIndex(0);
		totaleMeseUscite.setText("0.0");
		totaleMeseEntrate.setText("0.0");
	}

	public static JComboBox getComboMese() {
		return ComboMese;
	}

	public static void setComboMese(JComboBox comboMese) {
		ComboMese = comboMese;
	}

}
