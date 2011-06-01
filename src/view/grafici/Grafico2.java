package view.grafici;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import view.OggettoVistaBase;
import view.font.ButtonF;
import view.font.LabelListaGruppi;
import view.font.LabelTitolo;
import view.grafici.dialogGraph.GrUscite1;
import view.grafici.dialogGraph.GrUscite2;
import business.AltreUtil;

public class Grafico2 extends OggettoVistaBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Grafico2());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
	
	public Grafico2() {
		super();
		try{
			setSize(380, 300);
			
			setLayout(null);
			
			JButton btnVisualizza = new ButtonF("Visualizza");
			btnVisualizza.setBounds(102, 97, 91, 23);
			btnVisualizza.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						AltreUtil.deleteFileDaDirectory2("./immagini/");
						final GrUscite1 dialog = new GrUscite1();
						dialog.setSize(700, 700);
				        dialog.setVisible(true);
				        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					}catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			});
			
			this.add(btnVisualizza);
			
			LabelTitolo labelq = new LabelTitolo("Grafici Uscite");
			labelq.setBounds(130, 36, 127, 17);
			add(labelq);
			
			JLabel lblGrafico_1 = new JLabel("Grafico 2: ");
			lblGrafico_1.setForeground(Color.RED);
			lblGrafico_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
			lblGrafico_1.setBounds(10, 195, 82, 17);
			add(lblGrafico_1);
			
			JLabel lblQuestoGraficoVisualizza = new LabelListaGruppi("Grafico a barre delle uscite annuali divise per categoria");
			lblQuestoGraficoVisualizza.setVerticalAlignment(SwingConstants.TOP);
			lblQuestoGraficoVisualizza.setBounds(20, 131, 395, 25);
			add(lblQuestoGraficoVisualizza);
			
			JLabel label = new JLabel("Grafico 1: ");
			label.setForeground(Color.RED);
			label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
			label.setBounds(10, 98, 82, 17);
			add(label);
			
			JLabel lblGraficoABarre = new LabelListaGruppi("Grafico a barre dell'andamento delle uscite annuali divise per mesi");
			lblGraficoABarre.setVerticalAlignment(SwingConstants.TOP);
			lblGraficoABarre.setBounds(20, 231, 395, 25);
			add(lblGraficoABarre);
			
			JButton btnVisualizza_1 = new ButtonF("Visualizza");
			btnVisualizza_1.setBounds(102, 194, 91, 23);
			btnVisualizza_1.addActionListener(new ActionListener() {
				
				

				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						AltreUtil.deleteFileDaDirectory2("./immagini/");
						final GrUscite2 dialog2 = new GrUscite2();
						dialog2.setSize(700, 700);
						dialog2.setVisible(true);
						dialog2.setModalityType(ModalityType.APPLICATION_MODAL);
					}catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			});
			add(btnVisualizza_1);
			
			JSeparator separator = new JSeparator();
			separator.setBounds(20, 166, 291, 17);
			add(separator);
			
			JSeparator separator_1 = new JSeparator();
			separator_1.setBounds(20, 266, 291, 17);
			add(separator_1);
		
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
}
