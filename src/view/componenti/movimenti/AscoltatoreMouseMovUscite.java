package view.componenti.movimenti;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import business.cache.CacheCategorie;
import domain.CatSpese;
import domain.wrapper.WrapSingleSpesa;

public class AscoltatoreMouseMovUscite implements MouseListener {

	private DialogUsciteMov dialog;
	private final JTable    tabella;

	public AscoltatoreMouseMovUscite(final JTable tabella) {
		super();
		this.tabella = tabella;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {

		JTable table = tabella;
		setDialog(new DialogUsciteMov(new WrapSingleSpesa()));
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1
		                && e.getSource() == table) {
			JTable tabella = (JTable) e.getSource();
			int row = tabella.getSelectedRow();
			JTextField idSpesa = dialog.getIdSpesa();
			idSpesa.setText((String) tabella.getValueAt(row, 5));
			System.out.println((dialog).getIdSpesa().getText());
			JTextField nome = dialog.getNome();
			nome.setText((String) tabella.getValueAt(row, 1));
			JTextField descrizione = dialog.getDescrizione();
			descrizione.setText((String) tabella.getValueAt(row, 2));

			JComboBox categoria = dialog.getComboCategoria();
			Vector<CatSpese> cate = CacheCategorie.getSingleton().getVettoreCategorie();
			for (int i = 0; i < cate.size(); i++) {
				if (cate.get(i).getnome().equals(tabella.getValueAt(row, 4)))
					categoria.setSelectedIndex(i);
			}
			JTextField data = dialog.getData();
			data.setText((String) tabella.getValueAt(row, 0));
			JTextField euro = dialog.getEuro();
			euro.setText((String) tabella.getValueAt(row, 3));
			JTextField dataIns = dialog.getTfDataIns();
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

	protected DialogUsciteMov getDialog() {
		return dialog;
	}

	protected void setDialog(final DialogUsciteMov dialog) {
		this.dialog = dialog;
	}

}
