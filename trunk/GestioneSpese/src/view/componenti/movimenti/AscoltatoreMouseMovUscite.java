package view.componenti.movimenti;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import business.cache.CacheCategorie;
import domain.CatSpese;

public class AscoltatoreMouseMovUscite implements MouseListener {

	private final JTable tabella;

	public AscoltatoreMouseMovUscite(JTable tabella) {
		super();
		this.tabella = tabella;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// JTable table = ListaMovEntrat.getTable();
		JTable table = tabella;

		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1
		                && e.getSource() == table) {
			JTable tabella = (JTable) e.getSource();
			int row = tabella.getSelectedRow();
			JTextField idSpesa = DialogUsciteMov.getIdSpesa();
			idSpesa.setText((String) tabella.getValueAt(row, 5));
			JTextField nome = DialogUsciteMov.getNome();
			nome.setText((String) tabella.getValueAt(row, 1));
			JTextField descrizione = DialogUsciteMov.getDescrizione();
			descrizione.setText((String) tabella.getValueAt(row, 2));

			JComboBox categoria = DialogUsciteMov.getComboCategoria();
			Vector<CatSpese> cate = CacheCategorie.getSingleton().getVettoreCategorie();
			for (int i = 0; i < cate.size(); i++) {
				if (cate.get(i).getnome().equals(tabella.getValueAt(row, 4)))
					categoria.setSelectedIndex(i);
			}
			JTextField data = DialogUsciteMov.getData();
			data.setText((String) tabella.getValueAt(row, 0));
			JTextField euro = DialogUsciteMov.getEuro();
			euro.setText((String) tabella.getValueAt(row, 3));
			JTextField dataIns = DialogUsciteMov.getTfDataIns();
			dataIns.setText((String) tabella.getValueAt(row, 6));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
