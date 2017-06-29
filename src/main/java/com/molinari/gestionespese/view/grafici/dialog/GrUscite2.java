package com.molinari.gestionespese.view.grafici.dialog;

import java.awt.Rectangle;
import java.awt.event.ActionListener;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import com.molinari.gestionespese.business.Database;

public class GrUscite2 extends ChartCategory implements ActionListener {


	/**
	 * Create the dialog.
	 */
	public GrUscite2() {
		super("Uscite Mensili", "Mesi", "Euro", new Rectangle(100, 100, 700, 900));
	}
	
	@Override
	protected Dataset createDataset() {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (int i = 1; i <= 12; i++) {
			dataset.setValue(Database.getSingleton().totaleUsciteMese(i), "Euro", Integer.toString(i));
		}
		return dataset;
	}

}
