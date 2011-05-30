package view.componenti.movimenti;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;

import domain.wrapper.Model;

public class AscoltatoreMouseMovEntrate implements MouseListener {

	private DialogEntrateMov dialog;
	private final JTable     tabella;

	public AscoltatoreMouseMovEntrate(final JTable tabella, final JDialog dialog) {
		super();
		this.tabella = tabella;
		this.dialog = (DialogEntrateMov) dialog;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {

		// JTable table = ListaMovEntrat.getTable();
		final JTable table = tabella;
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1
		                && (e.getSource() == table)) {
			final JTable tabella = (JTable) e.getSource();
			final int row = tabella.getSelectedRow();
			final JTextField idEntrate = dialog.getIdEntrate();
			idEntrate.setText((String) tabella.getValueAt(row, 5));
			final JTextField nome = dialog.getNome();
			nome.setText((String) tabella.getValueAt(row, 1));
			final JTextField descrizione = dialog.getDescrizione();
			descrizione.setText((String) tabella.getValueAt(row, 2));
			final JComboBox tipoEntrata = dialog.getTipoEntrata();
			final String tipoEntry = (String) tabella.getValueAt(row, 4);

			final String[] listaEntr = Model.getNomiColonneEntrate();
			for (int i = 0; i < listaEntr.length; i++) {
				if (listaEntr[i].equals(tipoEntry))
					tipoEntrata.setSelectedIndex(i);
			}
			final JTextField data = dialog.getData();
			data.setText((String) tabella.getValueAt(row, 0));
			final JTextField euro = dialog.getEuro();
			euro.setText((String) tabella.getValueAt(row, 3));
			final JTextField dataIns = dialog.getTfDataIns();
			dataIns.setText((String) tabella.getValueAt(row, 6));

		}

	}

	@Override
	public void mousePressed(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	protected DialogEntrateMov getDialog() {
		return dialog;
	}

	protected void setDialog(final DialogEntrateMov dialog) {
		this.dialog = dialog;
	}

}
