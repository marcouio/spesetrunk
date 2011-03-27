package view.componenti.movimenti;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import domain.wrapper.Model;

public class AscoltatoreMouseMovEntrate implements MouseListener{

	private JTable tabella;

	public AscoltatoreMouseMovEntrate(JTable tabella) {
		super();
		this.tabella=tabella;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
//		JTable table = ListaMovEntrat.getTable();
		JTable table = tabella;
		if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1
				&& (e.getSource()==table)){
				JTable tabella = (JTable)e.getSource();
				int row = tabella.getSelectedRow();
				JTextField idEntrate = DialogEntrateMov.getIdEntrate();
				idEntrate.setText((String) tabella.getValueAt(row, 5));
				JTextField nome = DialogEntrateMov.getNome();
				nome.setText((String) tabella.getValueAt(row, 1));
				JTextField descrizione = DialogEntrateMov.getDescrizione();
				descrizione.setText((String) tabella.getValueAt(row, 2));
				JComboBox tipoEntrata = DialogEntrateMov.getTipoEntrata();
				String tipoEntry = (String) tabella.getValueAt(row, 4);
				
				String[]listaEntr = Model.getNomiColonneEntrate();
				for(int i=0; i<listaEntr.length; i++){
					if(listaEntr[i].equals(tipoEntry))
						tipoEntrata.setSelectedIndex(i);
				}
				JTextField data = DialogEntrateMov.getData();
				data.setText((String) tabella.getValueAt(row, 0));
				JTextField euro = DialogEntrateMov.getEuro();
				euro.setText((String) tabella.getValueAt(row, 3));
				
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
