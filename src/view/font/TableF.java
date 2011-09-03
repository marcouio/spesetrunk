package view.font;

import java.awt.Font;
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

	private static final long serialVersionUID = 1L;

	public TableF() {
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(final TableModel dm) {
		super(dm);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(final TableModel dm, final TableColumnModel cm) {
		super(dm, cm);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(final int numRows, final int numColumns) {
		super(numRows, numColumns);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(@SuppressWarnings("rawtypes") final Vector rowData, @SuppressWarnings("rawtypes") final Vector columnNames) {
		super(rowData, columnNames);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(final Object[][] rowData, final Object[] columnNames) {
		super(rowData, columnNames);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(final TableModel dm, final TableColumnModel cm, final ListSelectionModel sm) {
		super(dm, cm, sm);
		// TODO Auto-generated constructor stub
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
