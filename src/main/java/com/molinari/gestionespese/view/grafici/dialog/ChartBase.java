package com.molinari.gestionespese.view.grafici.dialog;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.border.BevelBorder;

import org.jfree.data.general.Dataset;

import com.molinari.gestionespese.view.componenti.movimenti.DialogHandler;
import com.molinari.utility.graphic.chart.UtilChart;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.alert.DialogoBase;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.label.LabelBase;

public abstract class ChartBase implements ActionListener{

	private static final String CHIUDI2 = "chiudi";
	private static final String SAVE = "salva";
	private DialogoBase dialog = new DialogoBase();
	private Dataset dataset;
	private String name;
	
	public ChartBase(String name, Rectangle rectangle) {
		this.name = name;
		dialog.setBounds(rectangle);
		dialog.getContentPane().setLayout(null);
		dataset = createDataset();
		
		final LabelBase containerImage = new LabelBase(dialog);
		containerImage.setBorder(new BevelBorder(BevelBorder.LOWERED));
		containerImage.setSize((int)rectangle.getWidth()-20, (int)rectangle.getHeight()-100);
		final byte[] imageByte = exportFromPieChart(dataset, name, containerImage.getWidth(), containerImage.getHeight());
		final ImageIcon image = new ImageIcon(imageByte);
		containerImage.setIcon(image);
		ButtonBase chiudi = createButton("Chiudi", CHIUDI2);
		chiudi.posizionaSottoA(containerImage, 0, 0);
		ButtonBase salva = createButton("Salva", SAVE);
		salva.posizionaADestraDi(chiudi, 10, 0);
	}

	protected byte[] exportFromPieChart(Dataset dataset, String name, int width, int height) {
		return UtilChart.exportFromChart(dataset, name, null, null, width, height);
	}

	private ButtonBase createButton(String name, String actionCommand) {
		final ButtonBase chiudi = new ButtonBase(name, dialog);
		chiudi.setActionCommand(actionCommand);
		chiudi.setSize(97, 30);
		chiudi.addActionListener(new DialogHandler(this.dialog));
		chiudi.addActionListener(this);
		return chiudi;
	}
	
	protected abstract Dataset createDataset();

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (CHIUDI2.equals(e.getActionCommand())) {
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dialog.dispose();
		}else if(SAVE.equals(e.getActionCommand())){
			saveChart(name);
		}
	}
	
	protected void saveChart(String name) {
		
		boolean savePieChart = UtilChart.saveChart("./immagini/", name, null, null, dataset, 550, 550);
		if(!savePieChart){
			Alert.segnalazioneErroreWarning("Salvataggio grafico non riuscito: guardare i log");
		}

	}

	public JDialog getDialog() {
		return dialog;
	}

	public void setDialog(DialogoBase dialog) {
		this.dialog = dialog;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	
}
