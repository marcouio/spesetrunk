package view.componenti.movimenti;

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
	public void impostaTableSpecifico(JTable table) {
		table.getColumn("idEntrate").setPreferredWidth(70);
		table.getColumn("euro").setPreferredWidth(90);
		table.getColumn("nome").setPreferredWidth(120);
		table.getColumn("data").setPreferredWidth(120);
		table.getColumn("descrizione").setPreferredWidth(250);
		table.getColumn("inserimento").setPreferredWidth(120);
		table.addMouseListener(new AscoltatoreMouseMovEntrate(table));
	}

}
