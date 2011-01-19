package view.grafici.dialogGraph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import view.font.ButtonF;
import business.Database;
import view.componenti.movimenti.DialogHandler;

public class GrGenerale2 extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GrGenerale2 dialog = new GrGenerale2();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setSize(700, 700);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GrGenerale2() {
		getContentPane().setLayout(null);
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i = 1; i <= 12; i++) {
			double entrate = Database.totaleEntrateMese(i);
			double uscite = Database.totaleUsciteMese(i);
			double valore = entrate - uscite;

			dataset.addValue(valore, "Saldo", Integer.toString(i));
			dataset.addValue(entrate, "Entrate", Integer.toString(i));
			dataset.addValue(uscite, "Uscite", Integer.toString(i));
		}
		final JFreeChart chart = ChartFactory.createLineChart("Saldo", "Mesi",
				"Euro", dataset, PlotOrientation.VERTICAL, true, true, true);
		CategoryPlot plot = chart.getCategoryPlot();
		LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, true);
		renderer.setSeriesShapesFilled(0, true);
		plot.setRenderer(renderer);

		GregorianCalendar data = new GregorianCalendar();
		String dataMinuti = "" + data.get(Calendar.HOUR_OF_DAY)
				+ data.get(Calendar.MINUTE);

		try {
			ChartUtilities.saveChartAsPNG(new java.io.File(
					"./immagini/LineChartGen2" + dataMinuti + ".png"), chart,
					550, 550);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageIcon image = new ImageIcon("./immagini/LineChartGen2" + dataMinuti
				+ ".png");
		JLabel label = new JLabel(image);
		
		
		JButton chiudi = new ButtonF("Chiudi");
		chiudi.setActionCommand("chiudi");
		
		label.setBounds(12, 22, 618, 546);
		chiudi.setBounds(269, 580, 97, 30);
		setBounds(100, 100, 650, 650);
		
		getContentPane().add(label);
		getContentPane().add(chiudi);
		chiudi.addActionListener(new DialogHandler(this));
		chiudi.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("chiudi")) {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.dispose();
		}

	}

}
