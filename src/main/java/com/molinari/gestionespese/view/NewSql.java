package com.molinari.gestionespese.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelTitolo;
import com.molinari.gestionespese.view.font.TextAreaF;

import grafica.componenti.alert.Alert;

public class NewSql extends OggettoVistaBase {

	

	private static final long serialVersionUID = 1L;
	private JTextArea         areaSql;
	private JTextArea         result;
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
			areaSql.setBounds(134, 54, 715, 74);
			this.add(areaSql);
			areaSql.setText(I18NManager.getSingleton().getMessaggio("inssql"));
			areaSql.setWrapStyleWord(true);

			final JScrollPane jsp = new JScrollPane(areaSql);
			jsp.setBounds(134, 54, 715, 74);
			this.add(jsp);

			final ButtonF bottoneSvuota = new ButtonF();
			bottoneSvuota.setBounds(37, 94, 75, 34);
			this.add(bottoneSvuota);
			bottoneSvuota.setText(I18NManager.getSingleton().getMessaggio("svuota"));

			final ButtonF bottone = new ButtonF();
			bottone.setBounds(37, 54, 75, 34);
			this.add(bottone);
			bottone.setText(I18NManager.getSingleton().getMessaggio("esegui"));

			result = new TextAreaF();
			result.setBounds(1, 245, 847, 103);
			this.add(result);
			final JScrollPane scroll = new JScrollPane(result);
			scroll.setBounds(37, 161, 889, 350);
			this.add(scroll);
			LabelTitolo labelResult = new LabelTitolo();
			labelResult.setBounds(420, 135, 70, 21);
			labelResult.setFont(new Font("Eras Light ITC", Font.PLAIN, 14));
			this.add(labelResult);
			labelResult.setText(I18NManager.getSingleton().getMessaggio("result"));

			final ButtonF buttonF = new ButtonF();
			buttonF.setBackground(Color.WHITE);
			final ImageIcon image = new ImageIcon(AltreUtil.IMGUTILPATH+"info.gif");
			buttonF.setIcon(image);
			buttonF.setBounds(870, 80, 56, 48);
			add(buttonF);
			buttonF.addActionListener(new InfoListener());
			bottoneSvuota.addActionListener(getCleanListener());

			bottone.addActionListener(getRunListener());
		} catch (final Exception e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private ActionListener getCleanListener() {
		return e -> {
			riga = "";
			totale = "";
			result.setText("");

		};
	}

	private ActionListener getRunListener() {
		return new ActionListener() {

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
						String finale = "";
						for (int i = 0; i < lista.size(); i++) {
							StringBuilder stringBuilder = new StringBuilder();
							final String porzione = DBUtil.creaStringStessaDimensione((String) lista.get(i), 30);
							stringBuilder.append(porzione);
						}
						String trattini = "";

						for (int x = 0; x < (lista.size() * 35); x++) {
							trattini = trattini + "-";
							finale = finale + "*";
						}
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append("\n");
						stringBuilder.append(trattini);
						stringBuilder.append("\n");
						StringBuilder stringBuilder2 = new StringBuilder();
						stringBuilder2.append(totale);
						stringBuilder2.append(stringBuilder.toString());
						totale = stringBuilder.toString();
					}
					result.setFont(new Font("Courier", Font.ITALIC, 10));
					totale = totale + "\n" + "\n";
					result.setText(totale + "\n");

				} catch (final Exception e1) {
					Alert.segnalazioneErroreGrave(e1.getMessage());
				}
			}
		};
	}
	
	private static final class InfoListener extends AscoltatoreAggiornatoreNiente {
		@Override
		protected void actionPerformedOverride(ActionEvent e) throws Exception {
			super.actionPerformedOverride(e);
			final StringBuilder sb = new StringBuilder();
			sb.append(I18NManager.getSingleton().getMessaggio("tables")+": \n");
			sb.append(Entrate.NOME_TABELLA + ", " + SingleSpesa.NOME_TABELLA + ", ");
			sb.append(CatSpese.NOME_TABELLA + ", " + Utenti.NOME_TABELLA + ", " + Gruppi.NOME_TABELLA);
			sb.append(", " + Note.NOME_TABELLA);
			sb.append("\n\n");
			sb.append(I18NManager.getSingleton().getMessaggio("infoconsolle"));
			sb.append("\n\n");
			sb.append(I18NManager.getSingleton().getMessaggio("otherinfosql"));
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
			b.setText(I18NManager.getSingleton().getMessaggio("close"));
			b.setBounds(0, 150, d.getWidth(), 30);
			b.addActionListener(e1 -> d.dispose());
			d.setVisible(true);
		}
	}
}