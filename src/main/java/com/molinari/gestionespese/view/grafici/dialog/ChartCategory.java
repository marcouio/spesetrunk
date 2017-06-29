package com.molinari.gestionespese.view.grafici.dialog;

import java.awt.Rectangle;

import org.jfree.data.general.Dataset;

import com.molinari.utility.graphic.chart.UtilChart;
import com.molinari.utility.graphic.component.alert.Alert;

public abstract class ChartCategory extends ChartBase {

	private String catAxisLabel;
	private String valAxisLabel;
	
	public ChartCategory(String name, String catAxisLabel, String valAxisLabel, Rectangle rectangle) {
		super(name, rectangle);
		this.setCatAxisLabel(catAxisLabel);
		this.valAxisLabel = valAxisLabel;
	}
	
	@Override
	protected void saveChart(String name) {
		
		boolean savePieChart = UtilChart.saveChart("./immagini/", name, catAxisLabel, valAxisLabel, getDataset(), getDialog().getWidth(), getDialog().getHeight());
		if(!savePieChart){
			Alert.segnalazioneErroreWarning("Salvataggio grafico non riuscito: guardare i log");
		}

	}
	
	@Override
	protected byte[] exportFromPieChart(Dataset dataset, String name, int width, int height) {
		return UtilChart.exportFromChart(dataset, name, catAxisLabel, valAxisLabel, width, height);
	}

	public String getValAxisLabel() {
		return valAxisLabel;
	}

	public void setValAxisLabel(String valAxisLabel) {
		this.valAxisLabel = valAxisLabel;
	}

	public String getCatAxisLabel() {
		return catAxisLabel;
	}

	public void setCatAxisLabel(String catAxisLabel) {
		this.catAxisLabel = catAxisLabel;
	}

}
