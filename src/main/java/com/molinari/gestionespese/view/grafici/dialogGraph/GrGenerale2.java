package com.molinari.gestionespese.view.grafici.dialogGraph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

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

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.view.componenti.movimenti.DialogHandler;
import com.molinari.gestionespese.view.font.ButtonF;

import controller.ControlloreBase;

public class GrGenerale2 extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public GrGenerale2() {
		getContentPane().setLayout(null);
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i = 1; i <= 12; i++) {
			final double entrate = Database.getSingleton().totaleEntrateMese(i);
			final double uscite = Database.getSingleton().totaleUsciteMese(i);
			final double valore = entrate - uscite;

			dataset.addValue(valore, "Saldo", Integer.toString(i));
			dataset.addValue(entrate, "Entrate", Integer.toString(i));
			dataset.addValue(uscite, "Uscite", Integer.toString(i));
		}
		final JFreeChart chart = ChartFactory.createLineChart("Saldo", "Mesi",
				"Euro", dataset, PlotOrientation.VERTICAL, true, true, true);
		final CategoryPlot plot = chart.getCategoryPlot();
		final LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, true);
		renderer.setSeriesShapesFilled(0, true);
		plot.setRenderer(renderer);

		final GregorianCalendar data = new GregorianCalendar();
		final String dataMinuti = Integer.toString(data.get(Calendar.HOUR_OF_DAY))
		+ data.get(Calendar.MINUTE);

		try {
			java.io.File image = new java.io.File("./immagini/LineChartGen2" + dataMinuti + ".png");
			ChartUtilities.saveChartAsPNG(image, chart, 550, 550);
		} catch (final IOException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		final ImageIcon image = new ImageIcon("./immagini/LineChartGen2" + dataMinuti + ".png");
		final JLabel label = new JLabel(image);


		final JButton chiudi = new ButtonF("Chiudi");
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
	public void actionPerformed(final ActionEvent e) {
		if ("chiudi".equals(e.getActionCommand())) {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.dispose();
		}

	}

}
