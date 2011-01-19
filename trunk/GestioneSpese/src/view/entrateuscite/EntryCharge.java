package view.entrateuscite;

import java.awt.Dimension;
import java.awt.GridLayout;
import  view.entrateuscite.Uscite;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class EntryCharge extends JPanel{
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame inst = new JFrame();	
				inst.setSize(850, 700);
				inst.getContentPane().add(new EntryCharge());
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				
			}
		});
	}
	
	
	public EntryCharge() {
		
		this.setPreferredSize(new Dimension(900, 700));
		this.setLayout(new GridLayout(2,1));
		Uscite uscita = Uscite.getSingleton();
		uscita.setBounds(10, 10, 830, 300);
		EntrateView entrata = EntrateView.getSingleton();
		entrata.setBounds(10, -80, 830, 340);
		this.add(uscita);
		this.add(entrata);	
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
