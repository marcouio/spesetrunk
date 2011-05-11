package view.componenti.movimenti;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;
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

	@Override
	public ActionListener getListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FiltraDialog dialog = new FiltraDialog() {

						private static final long serialVersionUID = 1L;

						@Override
						public String[][] getMovimenti() {
							Vector<Entrate> entrate = Model.getSingleton().getModelEntrate().movimentiEntrateFiltrati(getDataDa(), getDataA(), getNome(), Double.toString(getEuro()), getCategoria());
							return Model.getSingleton().movimentiFiltratiEntrate(25, Entrate.NOME_TABELLA, entrate);
						}

						{
							// array per Categori

							ArrayList<String> lista = new ArrayList<String>();
							lista.add("");
							lista.add("Variabili");
							lista.add("Fisse");
							comboBoxCat = new JComboBox(lista.toArray());

							comboBoxCat.setBounds(512, 26, 89, 25);
							getContentPane().add(comboBoxCat);
						}

						@Override
						protected String getCategoria() {
							if (comboBoxCat.getSelectedItem() != null) {
								categoria = (String) comboBoxCat.getSelectedItem();
							}
							return categoria;
						}

					};
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		};
	}
}
