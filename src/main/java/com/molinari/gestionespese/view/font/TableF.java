package com.molinari.gestionespese.view.font;

import java.awt.Font;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * TableF non è altro che una JTable con la modifica dei font e dell'altezza
 * delle righe, celle non editabili
 * 
 * @author marco.molinari
 * 
 */
public class TableF extends JTable {

	private static final Font ERAS_LIGHT_ITC_FONT = new Font("Eras Light ITC", Font.PLAIN, 12);
	private static final long serialVersionUID = 1L;

	public TableF() {
		setFont(ERAS_LIGHT_ITC_FONT);
	}

	public TableF(final TableModel dm) {
		super(dm);
		setFont(ERAS_LIGHT_ITC_FONT);
	}

	public TableF(final TableModel dm, final TableColumnModel cm) {
		super(dm, cm);
		setFont(ERAS_LIGHT_ITC_FONT);
	}

	public TableF(final int numRows, final int numColumns) {
		super(numRows, numColumns);
		setFont(ERAS_LIGHT_ITC_FONT);
	}

	public TableF(@SuppressWarnings("rawtypes") final List rowData, @SuppressWarnings("rawtypes") final List columnNames) {
		super(new Vector(rowData), new Vector(columnNames));
		setFont(ERAS_LIGHT_ITC_FONT);
	}

	public TableF(final Object[][] rowData, final Object[] columnNames) {
		super(rowData, columnNames);
		setFont(ERAS_LIGHT_ITC_FONT);
	}

	public TableF(final TableModel dm, final TableColumnModel cm, final ListSelectionModel sm) {
		super(dm, cm, sm);
	}

	@Override
	public int getRowHeight() {
		return 26;
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return false;
	}

}
