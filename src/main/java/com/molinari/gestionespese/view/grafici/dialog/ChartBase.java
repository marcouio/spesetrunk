package com.molinari.gestionespese.view.grafici.dialog;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.jfree.data.general.Dataset;

import com.molinari.gestionespese.view.componenti.movimenti.DialogHandler;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.utility.graphic.chart.UtilChart;
import com.molinari.utility.graphic.component.alert.Alert;

public abstract class ChartBase implements ActionListener{

	private static final String CHIUDI2 = "chiudi";
	private static final String SAVE = "salva";
	private JDialog dialog = new JDialog();
	private Dataset dataset;
	private String name;
	
	public ChartBase(String name, Rectangle rectangle) {
		this.name = name;
		dialog.setBounds(rectangle);
		
		dataset = createDataset();
		
		final byte[] imageByte = exportFromPieChart(dataset, "Entrate", (int)rectangle.getWidth()-15, (int)rectangle.getHeight()-75);
		final ImageIcon image = new ImageIcon(imageByte);
		final JLabel immagine = new JLabel();
		immagine.setIcon(image);
		dialog.getContentPane().add(immagine);
		immagine.setBounds(15, 15, (int)rectangle.getWidth()-15, (int)rectangle.getHeight()-75);
		createButton("Chiudi", CHIUDI2, 15, (int)rectangle.getHeight()-50);
		createButton("Salva", SAVE, 110 , (int)rectangle.getHeight()-50);
		
	}

	private byte[] exportFromPieChart(Dataset dataset, String name, int width, int height) {
		
		dialog.getContentPane().setLayout(null);
		return UtilChart.exportFromChart(dataset, name, width, height);
	}

	private void createButton(String name, String actionCommand, int x, int y) {
		final JButton chiudi = new ButtonF(name);
		chiudi.setActionCommand(actionCommand);
		chiudi.setBounds(x, y, 97, 30);
		dialog.getContentPane().add(chiudi);
		chiudi.addActionListener(new DialogHandler(this.dialog));
		chiudi.addActionListener(this);
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
	
	private void saveChart(String name) {
		
		boolean savePieChart = UtilChart.saveChart("./immagini/", name, dataset, 550, 550);
		if(!savePieChart){
			Alert.segnalazioneErroreWarning("Salvataggio grafico non riuscito: guardare i log");
		}

	}

	public JDialog getDialog() {
		return dialog;
	}

	public void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}
	
}
