package com.molinari.gestionespese.view.grafici.dialog;

import java.awt.Rectangle;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import com.molinari.gestionespese.business.Database;

public class GrGenerale2 extends ChartCategory {

	@Override
	protected Dataset createDataset() {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i = 1; i <= 12; i++) {
			final double entrate = Database.getSingleton().totaleEntrateMese(i);
			final double uscite = Database.getSingleton().totaleUsciteMese(i);
			final double valore = entrate - uscite;

			dataset.addValue(valore, "Saldo", Integer.toString(i));
			dataset.addValue(entrate, "Entrate", Integer.toString(i));
			dataset.addValue(uscite, "Uscite", Integer.toString(i));
		}

		return dataset;
	}
	
	/**
	 * Create the dialog.
	 */
	public GrGenerale2() {
		super("Saldo", "Mesi", "Euro", new Rectangle(100, 100, 700, 700));

	}
}
