package com.molinari.gestionespese.view.grafici.dialog;

import java.awt.Rectangle;
import java.io.IOException;
import java.sql.SQLException;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import com.molinari.gestionespese.business.Database;

public class GrEntrate2 extends ChartCategory {

	/**
	 * Create the dialog.
	 *
	 * @throws SQLException
	 * @throws IOException
	 */
	public GrEntrate2() throws SQLException, IOException {
		super("Entrate Mensili", "Mesi", "Euro", new Rectangle(100, 100, 700, 700));
	}

	@Override
	protected Dataset createDataset() {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 1; i <= 12; i++) {
			dataset.setValue(Database.getSingleton().totaleEntrateMese(i), "Euro", Integer.toString(i));
		}
		return dataset;
	}

}
