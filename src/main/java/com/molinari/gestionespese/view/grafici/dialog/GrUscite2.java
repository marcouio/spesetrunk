package com.molinari.gestionespese.view.grafici.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.view.componenti.movimenti.DialogHandler;
import com.molinari.gestionespese.view.font.ButtonF;

import controller.ControlloreBase;

public class GrUscite2 extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public GrUscite2() {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		final JFreeChart chart = ChartFactory.createLineChart("Uscite Mensili",
				"Mesi", "Euro", dataset, PlotOrientation.VERTICAL, true, true,
				true);
		getContentPane().setLayout(null);
		for (int i = 1; i <= 12; i++) {
			dataset.setValue(Database.getSingleton().totaleUsciteMese(i), "Euro", Integer.toString(i));
		}

		final GregorianCalendar data = new GregorianCalendar();

		final String dataMinuti = Integer.toString(data.get(Calendar.HOUR_OF_DAY))
		+ data.get(Calendar.MINUTE);

		try {
			ChartUtilities.saveChartAsPNG(new java.io.File("./immagini/LineChartUscite2"
					+ dataMinuti + ".png"), chart, 550, 600);
		} catch (final IOException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

		final ImageIcon image = new ImageIcon("./immagini/LineChartUscite2"
				+ dataMinuti + ".png");
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

	public GrUscite2(final JPanel owner) {

	}

	public GrUscite2(final JFrame frame) {
		super(frame);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if ("chiudi".equals(e.getActionCommand())) {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.dispose();
		}

	}

}
