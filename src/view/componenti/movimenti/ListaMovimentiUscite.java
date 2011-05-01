package view.componenti.movimenti;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JTable;

import business.AltreUtil;
import domain.SingleSpesa;
import domain.wrapper.Model;

public class ListaMovimentiUscite extends AbstractListaMov {

	private static final long serialVersionUID = 1L;

	@Override
	public String[][] createMovimenti() {
		return Model.getSingleton().movimentiUscite(numMovimenti, SingleSpesa.NOME_TABELLA);
	}

	@Override
	public String[] createNomiColonne() {
		return (String[]) AltreUtil.generaNomiColonne(SingleSpesa.NOME_TABELLA);
	}

	@Override
	public JDialog createDialog() {
		return new DialogUsciteMov();
	}

	@Override
	public void impostaTable(JTable table) {
		table.setPreferredScrollableViewportSize(new Dimension(800, 450));
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumn("idSpesa").setPreferredWidth(70);
		table.getColumn("euro").setPreferredWidth(90);
		table.getColumn("nome").setPreferredWidth(120);
		table.getColumn("data").setPreferredWidth(120);
		table.getColumn("descrizione").setPreferredWidth(250);
		table.setRowHeight(26);
		table.setBounds(30, 100, 500, 200);
		table.addMouseListener(new AscoltatoreMouseMovUscite(table));

	}

}
