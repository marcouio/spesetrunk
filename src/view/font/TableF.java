package view.font;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class TableF extends JTable {

	/**
	 */
	private static final long serialVersionUID = 1L;

	public TableF() {
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(TableModel dm) {
		super(dm);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(int numRows, int numColumns) {
		super(numRows, numColumns);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
	}

	public TableF(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		// TODO Auto-generated constructor stub
	}

}
