package view.grafici;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.grafici.dialogGraph.GrEntrate1;
import view.grafici.dialogGraph.GrEntrate2;
import business.AltreUtil;

public class Grafico1 extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws SQLException {
		JFrame frame = new JFrame();
		try {
			frame.getContentPane().add(new Grafico1());
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.pack();
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
	}

	
	public Grafico1() throws SQLException, IOException {
		super();
		setSize(380, 300);
		setLayout(null);
		
		
		JButton btnVisualizza = new ButtonF("Visualizza");
		btnVisualizza.setBounds(102, 81, 91, 23);
		btnVisualizza.addActionListener(new ActionListener() {
			JFrame f = new JFrame();			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					AltreUtil.deleteFileDaDirectory2("./immagini/");
					final GrEntrate1 dialog = new GrEntrate1(f, null, true);
					dialog.setSize(700, 700);
			        dialog.setVisible(true);
			        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				}catch (Exception e1) {
					e1.printStackTrace();
				}finally{
					f.dispose();
				}
			}
		});
		add(btnVisualizza);
		
		JButton btnVisualizza_1 = new ButtonF("Visualizza");
		btnVisualizza_1.setBounds(102, 178, 91, 23);
		btnVisualizza_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 
				try {
					AltreUtil.deleteFileDaDirectory2("./immagini/");
					GrEntrate2 dialog2 = new GrEntrate2();
					dialog2.setSize(700, 700);
			        dialog2.setVisible(true);
			        dialog2.setModalityType(ModalityType.APPLICATION_MODAL);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			
			}
		});
		add(btnVisualizza_1);
		
		JLabel lblLentrateDiviseFra = new LabelTesto("Grafico con le entrate divise fra fisse e variabili");
		lblLentrateDiviseFra.setVerticalAlignment(SwingConstants.TOP);
		lblLentrateDiviseFra.setBounds(20, 115, 395, 25);
		add(lblLentrateDiviseFra);
		
		JLabel label_1 = new LabelTesto("Grafico 1: ");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		label_1.setBounds(10, 82, 82, 17);
		add(label_1);
		
		JLabel label_2 = new LabelTesto("Grafico 2: ");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		label_2.setBounds(10, 179, 82, 17);
		add(label_2);
		
		JLabel lblGraficoABarre = new LabelTesto("Grafico a barre dell'andamento delle entrate annuali divise per mesi");
		lblGraficoABarre.setVerticalAlignment(SwingConstants.TOP);
		lblGraficoABarre.setBounds(20, 215, 395, 25);
		add(lblGraficoABarre);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 150, 291, 17);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(20, 250, 291, 17);
		add(separator_1);
		
		LabelTitolo label = new LabelTitolo("Grafici Entrate");
		label.setBounds(129, 36, 126, 14);
		add(label);
		
	}
}
