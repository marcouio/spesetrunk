package view.componenti.movimenti;

import java.awt.Dimension;

import javax.swing.JTable;

import business.AltreUtil;
import domain.Entrate;
import domain.wrapper.Model;
import domain.wrapper.WrapEntrate;

public class ListaMovimentiEntrate extends AbstractListaMov {

	private static final long serialVersionUID = 1L;

	@Override
	public String[][] createMovimenti() {
		return Model.getSingleton().movimentiEntrate(numMovimenti, Entrate.NOME_TABELLA);
	}

	@Override
	public String[] createNomiColonne() {
		return (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
	}

	@Override
	public DialogEntrateMov createDialog() {
		return new DialogEntrateMov(new WrapEntrate());
	}

	@Override
	public void impostaTable(JTable table) {
		table.setPreferredScrollableViewportSize(new Dimension(800,
		                 450));
		table.getColumn("idEntrate").setPreferredWidth(70);
		table.getColumn("euro").setPreferredWidth(90);
		table.getColumn("nome").setPreferredWidth(120);
		table.getColumn("data").setPreferredWidth(120);
		table.getColumn("descrizione").setPreferredWidth(250);
		table.getColumn("inserimento").setPreferredWidth(120);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(26);
		table.setFillsViewportHeight(true);
		table.setBounds(30, 100, 500, 200);
		table.addMouseListener(new AscoltatoreMouseMovEntrate(table));
	}

}
