package com.molinari.gestionespese.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Database;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.TableF;
import com.molinari.gestionespese.view.font.TextAreaF;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.container.ScrollPaneBase;
import com.molinari.utility.graphic.component.table.TableModel;
import com.molinari.utility.graphic.component.textarea.TextAreaBase;
import com.molinari.utility.math.UtilMath;
import com.molinari.utility.messages.I18NManager;

public class NewSql extends PannelloBase {

	private static final long serialVersionUID = 1L;
	private TextAreaBase         areaSql;
	private ScrollPaneBase       result;
	private PannelloBase headerPane;

	public NewSql(Container contenitore) {
		super(contenitore);
		initGUI();
	}

	private void initGUI() {
		try {

			setPreferredSize(new Dimension(getContenitorePadre().getWidth(), getContenitorePadre().getHeight()));
			setLayout(null);

			final PannelloBase headerPaneLoc = createHeaderPanel();

			createContentPane(headerPaneLoc);

		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void createContentPane(PannelloBase headerPane) {
		final PannelloBase contentPane = new PannelloBase(this);
		contentPane.posizionaSottoA(headerPane, 0, 0);
		contentPane.setSize(getContenitorePadre().getWidth(), getContenitorePadre().getHeight() - headerPane.getHeight());

		result = new ScrollPaneBase(contentPane);
		result.setSize(contentPane.getWidth(), contentPane.getHeight());
		contentPane.add(result);
		final JScrollPane scroll = new JScrollPane(result);
		scroll.setBounds(result.getBounds());
		contentPane.add(scroll);
	}
	

	private PannelloBase createHeaderPanel() {
		headerPane = new PannelloBase(this);
		headerPane.setSize(getContenitorePadre().getWidth(), (int) UtilMath.getPercentage(getContenitorePadre().getHeight(), 20));

		addComponentToHeader(headerPane);
		return headerPane;
	}

	public void addComponentToHeader(final PannelloBase headerPane) {
		final int widthInfoButton = (int) UtilMath.getPercentage(getContenitorePadre().getWidth(), 5);
		final int widthButton = (int) UtilMath.getPercentage(getContenitorePadre().getWidth(), 10);
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
	}

	private ActionListener getCleanListener() {
		return e -> {
			// do nothing
		};
	}

	private ActionListener getRunListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final String sql = areaSql.getText();
				try {
					final Map<String, ArrayList<String>> nomi = Database.getSingleton().terminaleSql(sql);
					if(!nomi.isEmpty()){
						addTable(nomi);
					}

				} catch (final Exception e1) {
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
					Alert.segnalazioneErroreGrave(e1.getMessage());
				}
			}

			private void addTable(final Map<String, ArrayList<String>> nomi) {
				TableModel tableModel = new TableModel(nomi) {
					
					private static final long serialVersionUID = 1L;

					@Override
					protected void preBuild(Object parametro) {
						@SuppressWarnings("unchecked")
						Map<String, ArrayList<String>> mappa = (Map<String, ArrayList<String>>) parametro;
						Consumer<? super String> action = this::addColumn;
						mappa.get("nomiColonne").stream().forEachOrdered(action);
						mappa.remove("nomiColonne");
						mappa.values().stream().forEach(this::addRiga);
					}
				};
				
				final List<String> listaCelle = tableModel.getNomiColonne().getListaCelle();
				final TableF table = new TableF(tableModel.getMatrice(), listaCelle.toArray(new String[listaCelle.size()]));
				table.setFillsViewportHeight(true);
				result.setViewportView(table);
			}
		};
	}

	private static final class InfoListener extends AscoltatoreAggiornatoreNiente {
		@Override
		protected void actionPerformedOverride(ActionEvent e) {
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

	public PannelloBase getHeaderPane() {
		return headerPane;
	}

	public void setHeaderPane(PannelloBase headerPane) {
		this.headerPane = headerPane;
	}
}