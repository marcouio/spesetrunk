package view.componenti.componentiPannello;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.font.LabelTestoPiccolo;
import view.font.TextFieldF;
import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;

public class SottoPannelloCategorie {

	private static final long serialVersionUID = 1L;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		SottoPannelloCategorie pan = new SottoPannelloCategorie();
		frame.getContentPane().add(pan.getPannello());
		frame.setBounds(0, 0, (pan.getPannello().getWidth() +
		                pan.getPannello().distanzaDalBordoX * 2) * 3, (pan.getPannello().getHeight() +
		                pan.getPannello().distanzaDalBordoY * 2) * 2);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private JLabel            jLabel5;

	private JLabel            jLabel11;
	private JLabel            jLabel6;

	private static JComboBox  categorieCombo;
	private static JTextField totaleMeseCategoria;
	private static JTextField totaleAnnualeCateg;

	JComponent[]              componenti = new JComponent[3];
	JLabel[]                  labels     = new JLabel[3];
	CostruttoreSottoPannello  pannello;

	public SottoPannelloCategorie() {
		super();
		initGUI();
		pannello = new CostruttoreSottoPannello(componenti, labels, CostruttoreSottoPannello.VERTICAL);
	}

	private void initGUI() {
		try {

			jLabel5 = new LabelTestoPiccolo();
			// this.add(jLabel5);
			jLabel5.setText("Categorie");
			jLabel5.setBounds(177, 25, 90, 19);
			labels[0] = jLabel5;

			jLabel11 = new LabelTestoPiccolo();
			// this.add(jLabel11);
			jLabel11.setText("Totale Anno");
			jLabel11.setBounds(135, 67, 78, 14);
			labels[1] = jLabel11;

			jLabel6 = new LabelTestoPiccolo();
			// this.add(jLabel6);
			jLabel6.setText("Totale Mese");
			jLabel6.setBounds(253, 67, 106, 14);
			labels[2] = jLabel6;

			totaleAnnualeCateg = new TextFieldF();
			// this.add(totaleAnnualeCateg);
			totaleAnnualeCateg.setColumns(10);
			totaleAnnualeCateg.setText("0.0");
			totaleAnnualeCateg.setBounds(135, 83, 106, 27);
			componenti[1] = totaleAnnualeCateg;

			totaleMeseCategoria = new TextFieldF();
			// this.add(totaleMeseCategoria);
			totaleMeseCategoria.setColumns(10);
			totaleMeseCategoria.setText("0.0");
			totaleMeseCategoria.setBounds(253, 83, 106, 27);
			componenti[2] = totaleMeseCategoria;

			// CategoriaSpese
			categorieCombo = new JComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo());

			// this.add(categorieCombo);
			categorieCombo.setBounds(16, 85, 106, 27);

			categorieCombo.setSelectedIndex(0);
			categorieCombo.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					CatSpese spese = null;
					if (categorieCombo.getSelectedIndex() != 0) {
						spese = (CatSpese) categorieCombo.getSelectedItem();
						int mese = new GregorianCalendar().get(Calendar.MONTH) + 1;
						double spesa = 0;
						try {
							spesa = Database.speseMeseCategoria(mese, spese.getidCategoria());
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						totaleAnnualeCateg.setText(Double.toString(Database.totaleUscitaAnnoCategoria(spese.getidCategoria())));
						totaleMeseCategoria.setText(Double.toString(spesa));
					}
				}
			});
			componenti[0] = categorieCombo;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JComboBox getCategorieCombo() {
		return categorieCombo;
	}

	public static void setCategorieCombo(JComboBox categorieCombo) {
		SottoPannelloCategorie.categorieCombo = categorieCombo;
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

	public static void azzeraCampi() {
		getCategorieCombo().setSelectedIndex(0);
		categorieCombo.setSelectedIndex(0);
		totaleAnnualeCateg.setText("0.0");
		totaleMeseCategoria.setText("0.0");
	}

	protected CostruttoreSottoPannello getPannello() {
		return pannello;
	}

	protected void setPannello(CostruttoreSottoPannello pannello) {
		this.pannello = pannello;
	}

	protected JComponent[] getComponenti() {
		return componenti;
	}

	protected void setComponenti(JComponent[] componenti) {
		this.componenti = componenti;
	}

	protected JLabel[] getLabels() {
		return labels;
	}

	protected void setLabels(JLabel[] labels) {
		this.labels = labels;
	}

}
