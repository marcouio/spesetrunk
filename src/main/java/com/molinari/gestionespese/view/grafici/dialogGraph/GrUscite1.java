package com.molinari.gestionespese.view.grafici.dialogGraph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.view.componenti.movimenti.DialogHandler;
import com.molinari.gestionespese.view.font.ButtonF;

public class GrUscite1 extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 *
	 * @throws SQLException
	 * @throws IOException
	 */
	public GrUscite1() throws SQLException, IOException {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		final List<CatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();

		// Grafico a barre
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i = 0; i < categorie.size(); i++) {
			final CatSpese categoria = categorie.get(i);
			final double uscita = Database.totaleUscitaAnnoCategoria(categoria.getidCategoria());
			dataset.setValue(uscita, "Euro", categoria.getnome());
		}

		final JFreeChart chart = ChartFactory.createBarChart("Uscite", "Categorie di spesa", "Euro", dataset,
				PlotOrientation.VERTICAL, true, true, true);
		final GregorianCalendar data = new GregorianCalendar();

		final String dataMinuti = Integer.toString(data.get(Calendar.HOUR_OF_DAY) + data.get(Calendar.MINUTE));

		ChartUtilities.saveChartAsPNG(new java.io.File("./immagini/barUscite" + dataMinuti + ".png"), chart, 550, 550);
		getContentPane().setLayout(null);
		final ImageIcon image = new ImageIcon("./immagini/barUscite" + dataMinuti + ".png");
		final JLabel immagine = new JLabel(image);
		dispose();
		final JButton chiudi = new ButtonF("Chiudi");
		chiudi.setActionCommand("chiudi");

		immagine.setBounds(12, 22, 618, 546);
		chiudi.setBounds(269, 580, 97, 30);
		setBounds(100, 100, 650, 650);

		getContentPane().add(immagine);
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
