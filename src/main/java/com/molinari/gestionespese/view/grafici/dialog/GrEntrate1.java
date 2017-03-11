package com.molinari.gestionespese.view.grafici.dialog;

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
import org.jfree.data.general.DefaultPieDataset;

import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.view.componenti.movimenti.DialogHandler;
import com.molinari.gestionespese.view.font.ButtonF;

public class GrEntrate1 extends JDialog implements ActionListener {

	private static final String CHIUDI2 = "chiudi";
	private static final long serialVersionUID = 1L;
	private final DefaultPieDataset dataset;
	private final double entrateFisse = Database.totaleEntrateAnnoCategoria(Entrate.IMPORTANZA_FISSE);
	private final double enrateVariabili = Database.totaleEntrateAnnoCategoria(Entrate.IMPORTANZA_VARIABILI);

	public GrEntrate1() throws SQLException, IOException {
		super();

		dataset = new DefaultPieDataset();
		dataset.setValue("Fisse", entrateFisse);
		dataset.setValue("Variabili", enrateVariabili);
		setBounds(100, 100, 650, 650);
		final JFreeChart chart = ChartFactory.createPieChart("Entrate", dataset, true, true, true);
		final GregorianCalendar data = new GregorianCalendar();

		final String dataMinuti = Integer.toString(data.get(Calendar.HOUR_OF_DAY)) + data.get(Calendar.MINUTE);

		ChartUtilities.saveChartAsPNG(new java.io.File("./immagini/torta" + dataMinuti + ".png"), chart, 560, 530);
		getContentPane().setLayout(null);

		DBUtil.closeConnection();
		final ImageIcon image = new ImageIcon("./immagini/torta" + dataMinuti + ".png");
		final JLabel immagine = new JLabel();
		immagine.setIcon(image);
		getContentPane().add(immagine);
		final JButton chiudi = new ButtonF("Chiudi");
		chiudi.setActionCommand(CHIUDI2);
		immagine.setBounds(12, 22, 618, 546);
		chiudi.setBounds(269, 580, 97, 30);
		setBounds(100, 100, 650, 650);
		getContentPane().add(chiudi);
		chiudi.addActionListener(new DialogHandler(this));
		chiudi.addActionListener(this);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (CHIUDI2.equals(e.getActionCommand())) {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.dispose();
		}

	}

}
