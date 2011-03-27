package view.grafici;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import view.OggettoVistaBase;
import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.grafici.dialogGraph.GrGenerale;
import view.grafici.dialogGraph.GrGenerale2;
import business.AltreUtil;

public class Grafico3 extends OggettoVistaBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	public Grafico3() throws SQLException, IOException {
		super();
		setSize(500, 500);
		setPreferredSize(new Dimension(380, 300));
		setLayout(null);

		JButton btnVisualizza = new ButtonF("Visualizza");
		btnVisualizza.setBounds(102, 93, 91, 23);
		btnVisualizza.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AltreUtil.deleteFileDaDirectory2("./immagini/");
				final GrGenerale dialog = new GrGenerale();
				dialog.setSize(700, 700);
		        dialog.setVisible(true);
		        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});
		add(btnVisualizza);
		
		LabelTitolo labelq = new LabelTitolo("Grafici Generali");
		labelq.setBounds(130, 45, 127, 17);
		add(labelq);
		
		JLabel lblGrafico = new JLabel("Grafico1: ");
		lblGrafico.setForeground(Color.RED);
		lblGrafico.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblGrafico.setBounds(10, 94, 82, 17);
		add(lblGrafico);
		
		JLabel lblIlGraficoMostra = new LabelTesto("Il saldo con il confronto fra entrate e uscite");
		lblIlGraficoMostra.setBounds(20, 127, 395, 25);
		add(lblIlGraficoMostra);
		
		JLabel lblGrafico_1 = new JLabel("Grafico2: ");
		lblGrafico_1.setForeground(Color.RED);
		lblGrafico_1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblGrafico_1.setBounds(10, 191, 82, 17);
		add(lblGrafico_1);
		
		JButton button = new ButtonF("Visualizza");
		button.setBounds(102, 190, 91, 23);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AltreUtil.deleteFileDaDirectory2("./immagini/");
				final GrGenerale2 dialog2 = new GrGenerale2();
				dialog2.setSize(700, 700);
		        dialog2.setVisible(true);
		        dialog2.setModalityType(ModalityType.APPLICATION_MODAL);
				
			}
		});
		add(button);
		
		JLabel label_1 = new LabelTesto("Le uscite mensili suddivise per categorie");
		label_1.setBounds(20, 227, 395, 25);
		add(label_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 162, 291, 17);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(20, 262, 291, 17);
		add(separator_1);
	
	
		
	}
}
