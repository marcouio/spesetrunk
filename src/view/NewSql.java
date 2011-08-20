package view;

import java.awt.BorderLayout;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import view.font.ButtonF;
import view.font.LabelTitolo;
import view.font.TextAreaF;
import business.Controllore;
import business.DBUtil;
import business.Database;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import domain.CatSpese;
import domain.Entrate;
import domain.Gruppi;
import domain.Note;
import domain.SingleSpesa;
import domain.Utenti;

public class NewSql extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;
	private JTextArea         areaSql;
	private JTextArea         result;
	private JLabel            labelResult;
	private JButton           bottone;
	private JButton           bottoneSvuota;
	private JScrollPane       jsp              = null;
	private String            totale           = "";

	private String            riga;

	public static void main(final String[] args) {
		final JFrame frame = new JFrame();
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
			final JScrollPane scroll = new JScrollPane(result);
			scroll.setBounds(37, 161, 889, 350);
			this.add(scroll);
			labelResult = new LabelTitolo();
			labelResult.setBounds(420, 135, 70, 21);
			labelResult.setFont(new Font("Eras Light ITC", Font.PLAIN, 14));
			this.add(labelResult);
			labelResult.setText("Result");

			final ButtonF buttonF = new ButtonF();
			buttonF.setBackground(Color.WHITE);
			final ImageIcon image = new ImageIcon("imgUtil/info.gif");
			buttonF.setIcon(image);
			buttonF.setBounds(870, 80, 56, 48);
			add(buttonF);
			buttonF.addActionListener(new AscoltatoreAggiornatoreNiente() {

				@Override
				protected void actionPerformedOverride(ActionEvent e) {
					super.actionPerformedOverride(e);
					final StringBuffer sb = new StringBuffer();
					sb.append("Tabelle principali: \n");
					sb.append(Entrate.NOME_TABELLA + ", " + SingleSpesa.NOME_TABELLA + ", ");
					sb.append(CatSpese.NOME_TABELLA + ", " + Utenti.NOME_TABELLA + ", " + Gruppi.NOME_TABELLA);
					sb.append(", " + Note.NOME_TABELLA);
					sb.append("\n\n");
					sb.append("La consolle supporta sia operazioni di select, sia operazioni di modifica");
					sb.append("\n\n");
					sb.append("Per maggiori informazioni e istruzioni, consultare il manuale nella sezione di help");
					final JDialog d = new JDialog();
					d.setLayout(new BorderLayout());
					d.setSize(400, 180);
					final TextAreaF lt = new TextAreaF();

					// specifica se �true� di andare a capo automaticamente a
					// fine riga
					lt.setLineWrap(true);
					// va a capo con la parola se �true� o col singolo carattere
					// se �false�
					lt.setWrapStyleWord(true);
					lt.setAutoscrolls(true);

					lt.setText(sb.toString());
					lt.setEditable(false);
					d.add(lt, BorderLayout.NORTH);
					final ButtonF b = new ButtonF();
					d.add(b, BorderLayout.CENTER);
					b.setText("Chiudi");
					b.setBounds(0, 150, d.getWidth(), 30);
					b.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(final ActionEvent e) {
							d.dispose();
						}
					});
					d.setVisible(true);
				}
			});
			bottoneSvuota.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					riga = "";
					totale = "";
					result.setText("");

				}
			});

			bottone.addActionListener(new ActionListener() {

				@SuppressWarnings("rawtypes")
				@Override
				public void actionPerformed(final ActionEvent e) {
					final String sql = areaSql.getText();
					try {
						final HashMap<String, ArrayList> nomi = Database.getSingleton().terminaleSql(sql);

						final Iterator<String> chiavi = nomi.keySet().iterator();

						while (chiavi.hasNext()) {
							final String chiave = chiavi.next();
							final ArrayList lista = nomi.get(chiave);
							riga = "";
							String finale = "";
							int i = 0;
							for (i = 0; i < lista.size(); i++) {
								final String porzione = DBUtil.creaStringStessaDimensione((String) lista.get(i), 30);
								riga = riga + porzione;
							}
							String trattini = "";

							for (int x = 0; x < (i * 35); x++) {
								trattini = trattini + "-";
								finale = finale + "*";
							}
							riga = riga + "\n" + trattini + "\n";
							totale = totale + riga;
						}
						result.setFont(new Font("Courier", Font.ITALIC, 10));
						totale = totale + "\n" + "\n";
						result.setText(totale + "\n");

					} catch (final Exception e1) {
						Controllore.getLog().severe(e1.getMessage());
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
				}
			});
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}