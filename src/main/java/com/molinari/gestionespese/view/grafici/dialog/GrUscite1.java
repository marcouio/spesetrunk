package com.molinari.gestionespese.view.grafici.dialog;

import java.awt.Rectangle;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.ICatSpese;

public class GrUscite1 extends ChartCategory {

	/**
	 * Create the dialog.
	 *
	 * @throws SQLException
	 * @throws IOException
	 */
	public GrUscite1() throws SQLException, IOException {
		super("Uscite", "Categorie di Spesa", "Euro", new Rectangle(100, 100, 700, 700));
	}
	
	@Override
	protected Dataset createDataset() {

		// Grafico a barre
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		final List<ICatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();
		
		for (int i = 0; i < categorie.size(); i++) {
			final ICatSpese categoria = categorie.get(i);
			final double uscita = Database.totaleUscitaAnnoCategoria(categoria.getidCategoria());
			dataset.setValue(uscita, "Euro", categoria.getnome());
		}

		return null;
	}

}
