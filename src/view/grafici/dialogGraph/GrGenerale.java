package view.grafici.dialogGraph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import view.componenti.movimenti.DialogHandler;
import view.font.ButtonF;
import business.AltreUtil;
import business.DBUtil;
import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;

public class GrGenerale extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static HashMap<Integer, ArrayList<Double>> mappaGennaio;
	private static HashMap<Integer, ArrayList<Double>> mappaFebbraio;
	private static HashMap<Integer, ArrayList<Double>> mappaMarzo;
	private static HashMap<Integer, ArrayList<Double>> mappaAprile;
	private static HashMap<Integer, ArrayList<Double>> mappaMaggio;
	private static HashMap<Integer, ArrayList<Double>> mappaGiugno;
	private static HashMap<Integer, ArrayList<Double>> mappaLuglio;
	private static HashMap<Integer, ArrayList<Double>> mappaAgosto;
	private static HashMap<Integer, ArrayList<Double>> mappaSettembre;
	private static HashMap<Integer, ArrayList<Double>> mappaOttobre;
	private static HashMap<Integer, ArrayList<Double>> mappaNovembre;
	private static HashMap<Integer, ArrayList<Double>> mappaDicembre;
	Vector<CatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();

	/**
	 * Uscite mensili per categoria Launch the application.
	 */
	public static void main(final String[] args) {
		try {

			final GrGenerale dialog = new GrGenerale();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

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

		final ArrayList<Double> ListaValori1 = mappaGennaio.get(1);
		final ArrayList<Double> ListaValori2 = mappaFebbraio.get(2);
		final ArrayList<Double> ListaValori3 = mappaMarzo.get(3);
		final ArrayList<Double> ListaValori4 = mappaAprile.get(4);
		final ArrayList<Double> ListaValori5 = mappaMaggio.get(5);
		final ArrayList<Double> ListaValori6 = mappaGiugno.get(6);
		final ArrayList<Double> ListaValori7 = mappaLuglio.get(7);
		final ArrayList<Double> ListaValori8 = mappaAgosto.get(8);
		final ArrayList<Double> ListaValori9 = mappaSettembre.get(9);
		final ArrayList<Double> ListaValori10 = mappaOttobre.get(10);
		final ArrayList<Double> ListaValori11 = mappaNovembre.get(11);
		final ArrayList<Double> ListaValori12 = mappaDicembre.get(12);

		for (int i = 0; i < ListaValori1.size(); i++) {
			dataset.addValue(ListaValori1.get(i), "Gennaio", categorie.get(i).getnome());
			dataset.addValue(ListaValori2.get(i), "Febbraio", categorie.get(i).getnome());
			dataset.addValue(ListaValori3.get(i), "Marzo", categorie.get(i).getnome());
			dataset.addValue(ListaValori4.get(i), "Aprile", categorie.get(i).getnome());
			dataset.addValue(ListaValori5.get(i), "Maggio", categorie.get(i).getnome());
			dataset.addValue(ListaValori6.get(i), "Giugno", categorie.get(i).getnome());
			dataset.addValue(ListaValori7.get(i), "Luglio", categorie.get(i).getnome());
			dataset.addValue(ListaValori8.get(i), "Agosto", categorie.get(i).getnome());
			dataset.addValue(ListaValori9.get(i), "Settembre", categorie.get(i).getnome());
			dataset.addValue(ListaValori10.get(i), "Ottobre", categorie.get(i).getnome());
			dataset.addValue(ListaValori11.get(i), "Novembre", categorie.get(i).getnome());
			dataset.addValue(ListaValori12.get(i), "Dicembre", categorie.get(i).getnome());
		}

		final GregorianCalendar data = new GregorianCalendar();
		final String dataMinuti = "" + data.get(Calendar.HOUR_OF_DAY) + data.get(Calendar.MINUTE);

		try {
			ChartUtilities.saveChartAsPNG(new java.io.File("immagini/LineChartGen1" + dataMinuti + ".png"), chart, 550, 510);
		} catch (final IOException e) {
			e.printStackTrace();
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

	public HashMap<Integer, ArrayList<Double>> creaMappaMese(final int mese) {
		final HashMap<Integer, ArrayList<Double>> mappaMese = new HashMap<Integer, ArrayList<Double>>();

		final ArrayList<Double> listaSpeseMeseCategoria = new ArrayList<Double>();
		for (int i = 0; i < categorie.size(); i++) {
			final CatSpese cat = categorie.get(i);
			double speseMeseCategoria;
			try {
				speseMeseCategoria = AltreUtil.arrotondaDecimaliDouble(Database.speseMeseCategoria(mese, cat.getidCategoria()));
				listaSpeseMeseCategoria.add(speseMeseCategoria);
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}
		mappaMese.put(mese, listaSpeseMeseCategoria);
		return mappaMese;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equals("chiudi")) {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.dispose();
		}

	}

}
