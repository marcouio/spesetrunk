package view.grafici.dialogGraph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

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
import business.DBUtil;
import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;

public class GrUscite1 extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Uscite per categoria Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GrUscite1 dialog = new GrUscite1();
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
	public GrUscite1() throws SQLException, IOException {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		Vector<CatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();
		Connection cn = DBUtil.getConnection();
		// Grafico a barre
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i = 0; i < categorie.size(); i++) {
			CatSpese categoria = categorie.get(i);
			double uscita = Database.totaleUscitaAnnoCategoria(categoria
			                .getidCategoria());
			dataset.setValue(uscita, "Euro", categoria.getnome());
		}

		JFreeChart chart = ChartFactory.createBarChart("Uscite",
		                "Categorie di spesa", "Euro", dataset,
		                PlotOrientation.VERTICAL, true, true, true);
		GregorianCalendar data = new GregorianCalendar();

		String dataMinuti = "" + data.get(Calendar.HOUR_OF_DAY)
		                + data.get(Calendar.MINUTE);

		ChartUtilities.saveChartAsPNG(new java.io.File("./immagini/barUscite"
		                + dataMinuti + ".png"), chart, 550, 550);
		getContentPane().setLayout(null);
		cn.close();
		ImageIcon image = new ImageIcon("./immagini/barUscite" + dataMinuti
		                + ".png");
		JLabel immagine = new JLabel(image);
		dispose();
		JButton chiudi = new ButtonF("Chiudi");
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
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("chiudi")) {
			if (e.getActionCommand().equals("chiudi")) {
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				// AltreUtil.deleteFileDaDirectory("./immagini/");
				this.dispose();
			}
		}
	}

}
