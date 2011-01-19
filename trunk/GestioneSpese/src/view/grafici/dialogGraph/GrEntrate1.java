package view.grafici.dialogGraph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import domain.Entrate;

import view.componenti.movimenti.DialogHandler;
import view.font.ButtonF;
import business.DBUtil;
import business.Database;

public class GrEntrate1 extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultPieDataset dataset;
	private double entrateFisse = Database.totaleEntrateAnnoCategoria(Entrate.IMPORTANZA_FISSE);
	private double enrateVariabili = Database.totaleEntrateAnnoCategoria(Entrate.IMPORTANZA_VARIABILI);

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	
	public static void main(String[] args) {
		try {
			GrEntrate1 dialog = new GrEntrate1(new JFrame(), "Entrate", true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public GrEntrate1(JFrame frame, String title, boolean modal)
			throws SQLException, IOException {
		super();

		dataset = new DefaultPieDataset();
		dataset.setValue("Fisse", entrateFisse);
		dataset.setValue("Variabili", enrateVariabili);
		setBounds(100, 100, 650, 650);
		JFreeChart chart = ChartFactory.createPieChart("Entrate", dataset,
				true, true, true);
		GregorianCalendar data = new GregorianCalendar();

		String dataMinuti = "" + data.get(Calendar.HOUR_OF_DAY)
				+ data.get(Calendar.MINUTE);

		ChartUtilities.saveChartAsPNG(new java.io.File("./immagini/torta"
				+ dataMinuti + ".png"), chart, 560, 530);
		getContentPane().setLayout(null);

		DBUtil.closeConnection();
		ImageIcon image = new ImageIcon("./immagini/torta" + dataMinuti
				+ ".png");
		JLabel immagine = new JLabel();		
		immagine.setIcon(image);
		getContentPane().add(immagine);
		JButton chiudi = new ButtonF("Chiudi");
		chiudi.setActionCommand("chiudi");
		immagine.setBounds(12, 22, 618, 546);
		chiudi.setBounds(269, 580, 97, 30);
		setBounds(100, 100, 650, 650);
		getContentPane().add(chiudi);
		chiudi.addActionListener(new DialogHandler(this));
		chiudi.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("chiudi")) {
			if (e.getActionCommand().equals("chiudi")) {
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.dispose();
			}
		}

	}

}
