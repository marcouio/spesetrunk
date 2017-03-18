package com.molinari.gestionespese.view.componenti.movimenti;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.molinari.utility.messages.I18NManager;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.TableF;
import com.molinari.gestionespese.view.font.TextFieldF;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.math.UtilMath;

public abstract class AbstractListaMov extends PannelloBase {
	private static final long serialVersionUID = 1L;
	int numMovimenti = 10;
	TableF table;
	private JScrollPane scrollPane;
	protected JTextField campo;
	String[][] movimenti;
	protected ButtonF pulsanteNMovimenti;
	protected JDialog dialog;
	protected ButtonF updateButton;
	protected ButtonF deleteButton;

	private AscoltatoreBottoniEntrata ascoltatore;

	public AbstractListaMov(Container contenitore) {
		super(contenitore);
		initGUI();
	}

	protected void setMovimenti(final String[][] movimenti) {
		this.movimenti = movimenti;
	}


	private void initGUI() {
		try {
			this.setLayout(null);

			final PannelloBase filterPanel = createFilterPanel();

			final String[] nomiColonne = createNomiColonne();

			movimenti = createMovimenti();

			table = new TableF(movimenti, nomiColonne);
			impostaTable(table);
			if (this instanceof ListaMovimentiEntrate) {
				table.addMouseListener(new AscoltatoreBottoniEntrata(this.getTable()));
			} else if (this instanceof ListaMovimentiUscite) {
				table.addMouseListener(new AscoltatoreBottoniUscita(this.getTable()));
			}

			// Create the scroll pane and add the table to it.
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(table);

			final PannelloBase contentPanel = new PannelloBase(this);
			contentPanel.setSize(getContenitorePadre().getWidth(), getContenitorePadre().getHeight()- filterPanel.getHeight());
			contentPanel.posizionaSottoA(filterPanel, 0, 0);
			contentPanel.add(scrollPane);
			scrollPane.setBounds(0, 0, getContenitorePadre().getWidth(), getContenitorePadre().getHeight()- filterPanel.getHeight());


		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}

	}

	private PannelloBase createFilterPanel() {
		final PannelloBase filterPan = new PannelloBase(this);
		final double height = UtilMath.getPercentage(getContenitorePadre().getHeight(), 10);
		filterPan.setSize(getContenitorePadre().getWidth(), (int) height);
		final JLabel movim = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("transactions")+":");
		movim.setBounds(24, 5, 89, 30);
		filterPan.add(movim);
		campo = new TextFieldF();
		campo.setBounds(116, 7, 43, 25);
		campo.setText("20");
		numMovimenti = Integer.parseInt(campo.getText());
		filterPan.add(campo);
		pulsanteNMovimenti = new ButtonF();
		pulsanteNMovimenti.setText(I18NManager.getSingleton().getMessaggio("change"));
		pulsanteNMovimenti.setBounds(178, 7, 89, 25);
		filterPan.add(pulsanteNMovimenti);

		final ButtonF btnFiltraMovimenti = new ButtonF();
		btnFiltraMovimenti.setText(I18NManager.getSingleton().getMessaggio("filtertrans"));
		btnFiltraMovimenti.setBounds(292, 6, 179, 25);
		btnFiltraMovimenti.addActionListener(getListener());
		filterPan.add(btnFiltraMovimenti);
		return filterPan;
	}

	protected abstract String getTipo();

	public abstract ActionListener getListener();

	private void impostaTable(final JTable table2) {
		final int heightTable = getContenitorePadre().getHeight();
		final int widthTable = getContenitorePadre().getWidth();
		table2.setPreferredScrollableViewportSize(new Dimension(widthTable, heightTable));
		table2.setFillsViewportHeight(true);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table2.setRowHeight(56);
		table2.setBounds(0, 0, widthTable, heightTable);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table2.addMouseListener(ascoltatore);
		impostaTableSpecifico();
	}

	public abstract void impostaTableSpecifico();

	public abstract String[][] createMovimenti();

	public abstract String[] createNomiColonne();

	public JTextField getCampo() {
		return campo;
	}

	public void setCampo(final JTextField campo) {
		this.campo = campo;
	}

	public int getNumEntry() {
		return this.numMovimenti;
	}

	public void setNumEntry(final int numEntry) {
		this.numMovimenti = numEntry;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(final JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public TableF getTable() {
		return table;
	}

	public void setTable(final TableF table) {
		this.table = table;
	}

}