package com.molinari.gestionespese.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

import com.molinari.gestionespese.business.AltreUtil;
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
import com.molinari.gestionespese.view.font.TextAreaF;

import controller.ControlloreBase;
import grafica.componenti.alert.Alert;
import grafica.componenti.button.ButtonBase;
import grafica.componenti.contenitori.PannelloBase;
import grafica.componenti.textarea.TextAreaBase;
import math.UtilMath;

public class NewSql extends PannelloBase {



	private static final long serialVersionUID = 1L;
	private TextAreaBase         areaSql;
	private TextAreaBase         result;
	private String            totale           = "";

	public NewSql(Container contenitore) {
		super(contenitore);
		initGUI();
	}

	private void initGUI() {
		try {

			setPreferredSize(new Dimension(getContenitorePadre().getWidth(), getContenitorePadre().getHeight()));
			setLayout(null);

			final PannelloBase headerPane = createHeaderPanel();

			createContentPane(headerPane);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void createContentPane(PannelloBase headerPane) {
		final PannelloBase contentPane = new PannelloBase(this);
		contentPane.posizionaSottoA(headerPane, 0, 0);
		contentPane.setSize(getContenitorePadre().getWidth(), getContenitorePadre().getHeight() - headerPane.getHeight());

		result = new TextAreaBase(contentPane);
		result.setSize(contentPane.getWidth(), contentPane.getHeight());
		contentPane.add(result);
		final JScrollPane scroll = new JScrollPane(result);
		scroll.setBounds(result.getBounds());
		contentPane.add(scroll);
	}

	private PannelloBase createHeaderPanel() {
		final int widthButton = (int) UtilMath.getPercentage(getContenitorePadre().getWidth(), 10);
		final int widthInfoButton = (int) UtilMath.getPercentage(getContenitorePadre().getWidth(), 5);
		final PannelloBase headerPane = new PannelloBase(this);
		headerPane.setSize(getContenitorePadre().getWidth(), (int) UtilMath.getPercentage(getContenitorePadre().getHeight(), 20));

		final ButtonBase bottone = new ButtonBase(headerPane);
		bottone.addActionListener(getRunListener());
		final int heightButton = headerPane.getHeight() / 2;
		bottone.setSize(widthButton, heightButton);
		headerPane.add(bottone);
		bottone.setText(I18NManager.getSingleton().getMessaggio("esegui"));

		final ButtonBase bottoneSvuota = new ButtonBase(this);
		bottoneSvuota.posizionaSottoA(bottone, 0, 0);
		bottoneSvuota.setSize(widthButton, heightButton);
		headerPane.add(bottoneSvuota);
		bottoneSvuota.setText(I18NManager.getSingleton().getMessaggio("svuota"));

		areaSql = new TextAreaBase(this);
		areaSql.posizionaADestraDi(bottone, 0, 0);
		areaSql.setSize(getContenitorePadre().getWidth()-widthButton-widthInfoButton, headerPane.getHeight());
		headerPane.add(areaSql);
		areaSql.setText(I18NManager.getSingleton().getMessaggio("inssql"));
		areaSql.setWrapStyleWord(true);

		final JScrollPane jsp = new JScrollPane(areaSql);
		jsp.setBounds(areaSql.getBounds());
		headerPane.add(jsp);


		final ButtonBase buttonF = new ButtonBase(this);
		buttonF.posizionaADestraDi(areaSql, 0, 0);
		buttonF.setBackground(Color.WHITE);
		final ImageIcon image = new ImageIcon(AltreUtil.IMGUTILPATH+"info.gif");
		buttonF.setIcon(image);
		buttonF.setSize(widthInfoButton, headerPane.getHeight());
		headerPane.add(buttonF);
		buttonF.addActionListener(new InfoListener());
		bottoneSvuota.addActionListener(getCleanListener());
		return headerPane;
	}

	private ActionListener getCleanListener() {
		return e -> {
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
					final Map<String, ArrayList<String>> nomi = Database.getSingleton().terminaleSql(sql);

					final Iterator<String> chiavi = nomi.keySet().iterator();

					while (chiavi.hasNext()) {
						final String chiave = chiavi.next();
						final ArrayList lista = nomi.get(chiave);
						String finale = "";
						for (int i = 0; i < lista.size(); i++) {
							final StringBuilder stringBuilder = new StringBuilder();
							final String porzione = DBUtil.creaStringStessaDimensione((String) lista.get(i), 30);
							stringBuilder.append(porzione);
						}
						String trattini = "";

						for (int x = 0; x < lista.size() * 35; x++) {
							StringBuilder stringBuilder2 = new StringBuilder();
							stringBuilder2.append(trattini);
							stringBuilder2.append("-");
							trattini = stringBuilder2.toString();
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(finale);
							stringBuilder.append("*");
							finale = stringBuilder.toString();
						}
						final StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append("\n");
						stringBuilder.append(trattini);
						stringBuilder.append("\n");
						final StringBuilder stringBuilder2 = new StringBuilder();
						stringBuilder2.append(totale);
						stringBuilder2.append(stringBuilder.toString());
						totale = stringBuilder.toString();
					}
					result.setFont(new Font("Courier", Font.ITALIC, 10));
					totale = totale + "\n" + "\n";
					result.setText(totale + "\n");

				} catch (final Exception e1) {
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
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