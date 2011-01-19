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
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import view.componenti.movimenti.DialogHandler;
import view.font.ButtonF;
import business.Database;

public class GrEntrate2 extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GrEntrate2 dialog = new GrEntrate2();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public GrEntrate2() throws SQLException, IOException {

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		final JFreeChart chart = ChartFactory.createLineChart(
				"Entrate Mensili", "Mesi", "Euro", dataset,
				PlotOrientation.VERTICAL, true, true, true);
		
		getContentPane().setLayout(null);
		for (int i = 1; i <= 12; i++) {
			dataset.setValue(Database.totaleEntrateMese(i), "Euro",
					Integer.toString(i));
		}
		GregorianCalendar data = new GregorianCalendar();
		String dataMinuti = "" + data.get(Calendar.HOUR_OF_DAY)
				+ data.get(Calendar.MINUTE);
		try {
			ChartUtilities.saveChartAsPNG(new java.io.File(
					"./immagini/Entrate2" + dataMinuti + ".png"), chart, 550,
					550);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageIcon image = new ImageIcon("./immagini/Entrate2" + dataMinuti
				+ ".png");
		JLabel immagine = new JLabel(image);
		JButton chiudi = new ButtonF("Chiudi");
		immagine.setBounds(12, 22, 618, 546);
		chiudi.setBounds(269, 580, 97, 30);
		setBounds(100, 100, 650, 650);
		getContentPane().add(immagine);
		getContentPane().add(chiudi);
		chiudi.setActionCommand("chiudi");
		chiudi.addActionListener(new DialogHandler(this));
		chiudi.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("chiudi")) {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			// AltreUtil.deleteFileDaDirectory2("./immagini/");
			this.dispose();
		}
	}

}
