package view.componenti.movimenti;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;

import business.AltreUtil;
import business.cache.CacheCategorie;
import domain.CatSpese;
import domain.Entrate;
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
							Vector<SingleSpesa> uscite = Model.getSingleton().getModelUscita().movimentiUsciteFiltrate(getDataDa(), getDataA(), getNome(), getEuro(), getCategoria());
							return Model.getSingleton().movimentiFiltratiUscite(25, Entrate.NOME_TABELLA, uscite);
						}

						{
							comboBoxCat = new JComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo());
							comboBoxCat.setBounds(512, 26, 89, 25);
							getContentPane().add(comboBoxCat);

						}

						@Override
						protected String getCategoria() {
							if (comboBoxCat.getSelectedItem() != null) {
								int idCat = ((CatSpese) comboBoxCat.getSelectedItem()).getidCategoria();
								categoria = Integer.toString(idCat);
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
