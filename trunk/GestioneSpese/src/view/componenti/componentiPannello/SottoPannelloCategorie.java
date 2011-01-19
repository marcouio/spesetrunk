package view.componenti.componentiPannello;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;

public class SottoPannelloCategorie extends OggettoVistaBase {

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
		frame.getContentPane().add(new SottoPannelloCategorie());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private JLabel jLabel5;
	
	private JLabel jLabel9Categorie;
	private JLabel jLabel11;
	private JLabel jLabel6;
	private JSeparator jSeparator2;
	
	private static JComboBox CategorieCombo;
	private static JTextField totaleMeseCategoria;
	private static JTextField totaleAnnualeCateg;
	
	


	public SottoPannelloCategorie() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			
			jLabel5 = new LabelTitolo();
			this.add(jLabel5);
			jLabel5.setText("Categorie");
			jLabel5.setBounds(177, 25, 90, 19);
			
			jLabel9Categorie = new LabelTesto();
			this.add(jLabel9Categorie);
			jLabel9Categorie.setText("Categorie");
			jLabel9Categorie.setBounds(16, 67, 67, 14);
			
			jLabel11 = new LabelTesto();
			this.add(jLabel11);
			jLabel11.setText("Totale Anno");
			jLabel11.setBounds(164, 66, 78, 14);
			
			jLabel6 = new LabelTesto();
			this.add(jLabel6);
			jLabel6.setText("Totale Mese");
			jLabel6.setBounds(317, 67, 106, 14);
			
			totaleAnnualeCateg = new TextFieldF();
			this.add(totaleAnnualeCateg);
			totaleAnnualeCateg.setColumns(10);
			totaleAnnualeCateg.setText("0.0");
			totaleAnnualeCateg.setBounds(164, 84, 106, 27);
			
			totaleMeseCategoria = new TextFieldF();
			this.add(totaleMeseCategoria);
			totaleMeseCategoria.setColumns(10);
			totaleMeseCategoria.setText("0.0");
			totaleMeseCategoria.setBounds(317, 83, 106, 27);

			jSeparator2 = new JSeparator();
			this.add(jSeparator2);
			jSeparator2.setBounds(10, 128, 420, 16);
			
			// CategoriaSpese
			Object[]cate = CacheCategorie.getSingleton().arrayCategorie();
			CategorieCombo = new JComboBox();
			this.add(CategorieCombo);
			CategorieCombo.setBounds(16, 85, 106, 27);
			CategorieCombo.addItem("");
			
			for(int i=0; i<cate.length; i++){
				CategorieCombo.addItem(cate[i]);
			}
			CategorieCombo.setSelectedIndex(0);
			CategorieCombo.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					CatSpese spese = null;
					if (CategorieCombo.getSelectedIndex() != 0) {
						spese = (CatSpese) CategorieCombo.getSelectedItem();
						int mese = new GregorianCalendar().get(Calendar.MONTH);
						double spesa = 0;
						try {
							spesa = Database.speseMeseCategoria(mese, spese.getidCategoria());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
						//TODO
						totaleAnnualeCateg.setText(Double.toString(Database.totaleUscitaAnnoCategoria(spese.getidCategoria())));
						totaleMeseCategoria.setText(Double.toString(spesa));
					}
				}
			});
		
			this.setPreferredSize(new Dimension(440, 150));
			this.setLayout(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JComboBox getCategorieCombo() {
		return CategorieCombo;
	}

	public static void setCategorieCombo(JComboBox categorieCombo) {
		CategorieCombo = categorieCombo;
	}

	public static JTextField getTotaleMeseCategoria() {
		return totaleMeseCategoria;
	}

	public static void setTotaleMeseCategoria(JTextField totaleMeseCategoria) {
		SottoPannelloCategorie.totaleMeseCategoria = totaleMeseCategoria;
	}

	public static JTextField getTotaleAnnualeCateg() {
		return totaleAnnualeCateg;
	}

	public static void setTotaleAnnualeCateg(JTextField totaleAnnualeCateg) {
		SottoPannelloCategorie.totaleAnnualeCateg = totaleAnnualeCateg;
	}
	
	public static void azzeraCampi(){
		getCategorieCombo().setSelectedIndex(0);
		CategorieCombo.setSelectedIndex(0);
		getTotaleAnnualeCateg().setText("0.0");
		totaleAnnualeCateg.setText("0.0");
		getTotaleMeseCategoria().setText("0.0");
		totaleMeseCategoria.setText("0.0");
	}
	
}
