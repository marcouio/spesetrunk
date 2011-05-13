package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import view.font.ButtonF;
import view.font.LabelTitolo;
import view.font.TextAreaF;
import business.DBUtil;
import business.Database;

public class NewSql extends OggettoVistaBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea         areaSql;
	private JTextArea         result;
	private JLabel            labelResult;
	private JButton           bottone;
	private JButton           bottoneSvuota;
	private JScrollPane       jsp              = null;
	private String            totale           = "";

	private String            riga;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new NewSql());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public NewSql() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			setPreferredSize(new Dimension(950, 750));
			setLayout(null);
			areaSql = new TextAreaF();
			areaSql.setBounds(1, 1, 727, 71);
			this.add(areaSql);
			areaSql.setText("Inserisci qui il tuo codice sql");
			areaSql.setWrapStyleWord(true);
			jsp = new JScrollPane(areaSql);
			jsp.setBounds(134, 54, 715, 74);
			this.add(jsp);

			bottoneSvuota = new ButtonF();
			bottoneSvuota.setBounds(37, 94, 75, 34);
			this.add(bottoneSvuota);
			bottoneSvuota.setText("Svuota");

			bottone = new ButtonF();
			bottone.setBounds(37, 54, 75, 34);
			this.add(bottone);
			bottone.setText("Esegui");

			result = new TextAreaF();
			result.setBounds(1, 245, 847, 103);
			this.add(result);
			JScrollPane scroll = new JScrollPane(result);
			scroll.setBounds(37, 161, 889, 350);
			this.add(scroll);
			labelResult = new LabelTitolo();
			labelResult.setBounds(420, 135, 70, 21);
			labelResult.setFont(new Font("Eras Light ITC", Font.PLAIN, 14));
			this.add(labelResult);
			labelResult.setText("Result");

			ButtonF buttonF = new ButtonF();
			buttonF.setBackground(Color.WHITE);
			ImageIcon image = new ImageIcon("imgUtil/info.gif");
			// BufferedImage bi = new BufferedImage(56, 48,
			// BufferedImage.TYPE_INT_RGB);
			// Graphics2D g = (Graphics2D) bi.getGraphics();
			// g.setColor(buttonF.getBackground());
			// g.fillRoundRect(0, 0, 56, 48, 7, 7);
			// g.drawImage(image.getImage(), 3, 0, null);
			//
			// ImageIcon icon = new ImageIcon(bi.getSubimage(0, 0, 56, 48));
			buttonF.setIcon(image);
			buttonF.setBounds(870, 80, 56, 48);
			add(buttonF);
			bottoneSvuota.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					riga = "";
					totale = "";
					result.setText("");

				}
			});

			bottone.addActionListener(new ActionListener() {

				@SuppressWarnings("rawtypes")
				@Override
				public void actionPerformed(ActionEvent e) {
					String sql = areaSql.getText();
					try {
						HashMap<String, ArrayList> nomi = Database.getSingleton().terminaleSql(sql);

						Iterator<String> chiavi = nomi.keySet().iterator();

						while (chiavi.hasNext()) {
							String chiave = chiavi.next();
							ArrayList lista = nomi.get(chiave);
							riga = "";
							String finale = "";
							int i = 0;
							for (i = 0; i < lista.size(); i++) {
								String porzione = DBUtil.creaStringStessaDimensione((String) lista.get(i), 14);
								riga = riga + porzione;
							}
							String trattini = "";

							for (int x = 0; x < (i * 15); x++) {
								trattini = trattini + "-";
								finale = finale + "*";
							}
							riga = riga + "\n" + trattini + "\n";
							totale = totale + riga;
						}
						result.setFont(new Font("Courier", Font.ITALIC, 10));
						totale = totale + "\n" + "\n";
						result.setText(totale + "\n");

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}