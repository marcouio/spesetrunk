package com.molinari.gestionespese.view.grafici.dialog;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.utility.controller.ControlloreBase;

public class GrGenerale extends ChartCategory {

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

	/**
	 * Create the dialog.
	 */
	public GrGenerale() {
		super("Uscite Mensili per categoria", "Categorie", "Euro", new Rectangle(100, 100, 900, 900));

	}

	public Map<Integer, List<Double>> creaMappaMese(final int mese) {
		final Map<Integer, List<Double>> mappaMese = new HashMap<>();

		List<ICatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();
		
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
	protected Dataset createDataset() {
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

		List<ICatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();

		for (int i = 0; i < listaValori1.size(); i++) {
			dataset.addValue(listaValori1.get(i), categorie.get(i).getnome(), "Gennaio" );
			dataset.addValue(listaValori2.get(i), categorie.get(i).getnome(), "Febbraio");
			dataset.addValue(listaValori3.get(i), categorie.get(i).getnome(), "Marzo"    );
			dataset.addValue(listaValori4.get(i), categorie.get(i).getnome(), "Aprile"   );
			dataset.addValue(listaValori5.get(i), categorie.get(i).getnome(), "Maggio"   );
			dataset.addValue(listaValori6.get(i), categorie.get(i).getnome(), "Giugno"   );
			dataset.addValue(listaValori7.get(i), categorie.get(i).getnome(), "Luglio"   );
			dataset.addValue(listaValori8.get(i), categorie.get(i).getnome(), "Agosto"   );
			dataset.addValue(listaValori9.get(i), categorie.get(i).getnome(), "Settembre");
			dataset.addValue(listaValori10.get(i),categorie.get(i).getnome(), "Ottobre"  );
			dataset.addValue(listaValori11.get(i),categorie.get(i).getnome(), "Novembre" );
			dataset.addValue(listaValori12.get(i),categorie.get(i).getnome(), "Dicembre" );
		}

		return dataset;
	}

}
