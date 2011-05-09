package view.componenti.movimenti;

import javax.swing.JDialog;
import javax.swing.JTable;

import business.AltreUtil;
import domain.SingleSpesa;
import domain.wrapper.Model;
import domain.wrapper.WrapSingleSpesa;

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
		return new DialogUsciteMov(new WrapSingleSpesa());
	}

	@Override
	public void impostaTableSpecifico(JTable table) {

		table.getColumn("idSpesa").setPreferredWidth(70);
		table.getColumn("euro").setPreferredWidth(90);
		table.getColumn("nome").setPreferredWidth(120);
		table.getColumn("data").setPreferredWidth(120);
		table.getColumn("descrizione").setPreferredWidth(250);
		table.getColumn("inserimento").setPreferredWidth(120);

		table.addMouseListener(new AscoltatoreMouseMovUscite(table));

	}

}
