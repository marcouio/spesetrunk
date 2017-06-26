package com.molinari.gestionespese.view.grafici.dialog;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.view.entrateuscite.EntrateView.INCOMETYPE;

public class GrEntrate1 extends ChartBase implements ActionListener {

	public GrEntrate1() throws SQLException, IOException {
		super("Entrate1", new Rectangle(100, 100, 650, 650));
	}

	@Override
	protected Dataset createDataset() {
		
		double entrateFisse = Database.totaleEntrateAnnoCategoria(Integer.toString(INCOMETYPE.FIXITY.ordinal()));
		double enrateVariabili = Database.totaleEntrateAnnoCategoria(Integer.toString(INCOMETYPE.VARIABLES.ordinal()));
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Fisse", entrateFisse);
		dataset.setValue("Variabili", enrateVariabili);
		return dataset;
	}

}
