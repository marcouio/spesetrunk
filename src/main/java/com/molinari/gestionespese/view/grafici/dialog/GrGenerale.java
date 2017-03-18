package com.molinari.gestionespese.view.grafici.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.view.componenti.movimenti.DialogHandler;
import com.molinari.gestionespese.view.font.ButtonF;

import com.molinari.utility.controller.ControlloreBase;

public class GrGenerale extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static Map<Integer, List<Double>> mappaGennaio;
	private static Map<Integer, List<Double>> mappaFebbraio;
	private static Map<Integer, List<Double>> mappaMarzo;
	private static Map<Integer, List<Double>> mappaAprile;
	private static Map<Integer, List<Double>> mappaMaggio;
	private static Map<Integer, List<Double>> mappaGiugno;
	private static Map<Integer, List<Double>> mappaLuglio;
	private static Map<Integer, List<Double>> mappaAgosto;
	private static Map<Integer, List<Double>> mappaSettembre;
	private static Map<Integer, List<Double>> mappaOttobre;
	private static Map<Integer, List<Double>> mappaNovembre;
	private static Map<Integer, List<Double>> mappaDicembre;
	private List<ICatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();

	/**
	 * Create the dialog.
	 */
	public GrGenerale() {
		setBounds(100, 100, 650, 600);
		mappaGennaio = creaMappaMese(1);
		mappaFebbraio = creaMappaMese(2);
		mappaMarzo = creaMappaMese(3);
		mappaAprile = creaMappaMese(4);
		mappaMaggio = creaMappaMese(5);
		mappaGiugno = creaMappaMese(6);
		mappaLuglio = creaMappaMese(7);
		mappaAgosto = creaMappaMese(8);
		mappaSettembre = creaMappaMese(9);
		mappaOttobre = creaMappaMese(10);
		mappaNovembre = creaMappaMese(11);
		mappaDicembre = creaMappaMese(12);

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		final JFreeChart chart = ChartFactory.createLineChart("Uscite Mensili per categoria", "Categorie", "Euro", dataset, PlotOrientation.VERTICAL, true, true, true);
		final CategoryPlot plot = chart.getCategoryPlot();
		final LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, true);
		renderer.setSeriesShapesFilled(0, true);
		plot.setRenderer(renderer);

		final List<Double> listaValori1 = mappaGennaio.get(1);
		final List<Double> listaValori2 = mappaFebbraio.get(2);
		final List<Double> listaValori3 = mappaMarzo.get(3);
		final List<Double> listaValori4 = mappaAprile.get(4);
		final List<Double> listaValori5 = mappaMaggio.get(5);
		final List<Double> listaValori6 = mappaGiugno.get(6);
		final List<Double> listaValori7 = mappaLuglio.get(7);
		final List<Double> listaValori8 = mappaAgosto.get(8);
		final List<Double> listaValori9 = mappaSettembre.get(9);
		final List<Double> listaValori10 = mappaOttobre.get(10);
		final List<Double> listaValori11 = mappaNovembre.get(11);
		final List<Double> listaValori12 = mappaDicembre.get(12);

		for (int i = 0; i < listaValori1.size(); i++) {
			dataset.addValue(listaValori1.get(i), "Gennaio", categorie.get(i).getnome());
			dataset.addValue(listaValori2.get(i), "Febbraio", categorie.get(i).getnome());
			dataset.addValue(listaValori3.get(i), "Marzo", categorie.get(i).getnome());
			dataset.addValue(listaValori4.get(i), "Aprile", categorie.get(i).getnome());
			dataset.addValue(listaValori5.get(i), "Maggio", categorie.get(i).getnome());
			dataset.addValue(listaValori6.get(i), "Giugno", categorie.get(i).getnome());
			dataset.addValue(listaValori7.get(i), "Luglio", categorie.get(i).getnome());
			dataset.addValue(listaValori8.get(i), "Agosto", categorie.get(i).getnome());
			dataset.addValue(listaValori9.get(i), "Settembre", categorie.get(i).getnome());
			dataset.addValue(listaValori10.get(i), "Ottobre", categorie.get(i).getnome());
			dataset.addValue(listaValori11.get(i), "Novembre", categorie.get(i).getnome());
			dataset.addValue(listaValori12.get(i), "Dicembre", categorie.get(i).getnome());
		}

		final GregorianCalendar data = new GregorianCalendar();
		final String dataMinuti = Integer.toString(data.get(Calendar.HOUR_OF_DAY)) + data.get(Calendar.MINUTE);

		try {
			ChartUtilities.saveChartAsPNG(new java.io.File("immagini/LineChartGen1" + dataMinuti + ".png"), chart, 550, 510);
		} catch (final IOException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		DBUtil.closeConnection();
		final ImageIcon image = new ImageIcon("immagini/LineChartGen1" + dataMinuti + ".png");
		getContentPane().setLayout(null);
		final JLabel label = new JLabel(image);
		label.setBounds(12, 12, 630, 498);

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

	public Map<Integer, List<Double>> creaMappaMese(final int mese) {
		final Map<Integer, List<Double>> mappaMese = new HashMap<>();

		final ArrayList<Double> listaSpeseMeseCategoria = new ArrayList<>();
		for (int i = 0; i < categorie.size(); i++) {
			final ICatSpese cat = categorie.get(i);
			double speseMeseCategoria;
			try {
				speseMeseCategoria = AltreUtil.arrotondaDecimaliDouble(Database.speseMeseCategoria(mese, cat.getidCategoria()));
				listaSpeseMeseCategoria.add(speseMeseCategoria);
			} catch (final Exception e) {
				ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			}

		}
		mappaMese.put(mese, listaSpeseMeseCategoria);
		return mappaMese;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if ("chiudi".equals(e.getActionCommand())) {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.dispose();
		}

	}

}
