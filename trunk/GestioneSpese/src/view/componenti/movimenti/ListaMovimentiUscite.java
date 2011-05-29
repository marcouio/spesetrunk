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

	public ListaMovimentiUscite() {
		super();
		pulsanteNMovimenti.addActionListener(new AscoltatoreNumeroMovimenti(SingleSpesa.NOME_TABELLA, createNomiColonne(), campo));
	}

	@Override
	public String[][] createMovimenti() {
		return Model.getSingleton().movimentiUscite(numMovimenti, SingleSpesa.NOME_TABELLA);
	}

	@Override
	public String[] createNomiColonne() {
		return (String[]) AltreUtil.generaNomiColonne(SingleSpesa.NOME_TABELLA);
	}

	@Override
	public DialogUsciteMov createDialog() {
		return new DialogUsciteMov(new WrapSingleSpesa());
	}

	@Override
	public void impostaTableSpecifico(final JTable table) {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// table.getColumn("idSpesa").setPreferredWidth(70);
		// table.getColumn("euro").setPreferredWidth(90);
		// table.getColumn("nome").setPreferredWidth(120);
		// table.getColumn("data").setPreferredWidth(120);
		// table.getColumn("descrizione").setPreferredWidth(250);
		// table.getColumn("inserimento").setPreferredWidth(120);
		// this.dialog = createDialog();
		table.addMouseListener(new AscoltatoreMouseMovUscite(table, this.getDialog()));

	}

	@Override
	public ActionListener getListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					final FiltraDialog dialog = new FiltraDialog() {
						private static final long serialVersionUID = 1L;

						@Override
						public String[][] getMovimenti() {
							final Vector<SingleSpesa> uscite = Model.getSingleton().getModelUscita().movimentiUsciteFiltrate(getDataDa(), getDataA(), getNome(), getEuro(), getCategoria());
							final String[][] mov = Model.getSingleton().movimentiFiltratiUscitePerNumero(Entrate.NOME_TABELLA, uscite);
							Model.aggiornaMovimentiUsciteDaFiltro(createNomiColonne(), mov);
							return mov;
						}

						{
							comboBoxCat = new JComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo());
							comboBoxCat.setBounds(512, 26, 89, 25);
							getContentPane().add(comboBoxCat);

						}

						@Override
						protected String getCategoria() {
							if (comboBoxCat.getSelectedItem() != null) {
								final int idCat = ((CatSpese) comboBoxCat.getSelectedItem()).getidCategoria();
								categoria = Integer.toString(idCat);
							}
							return categoria;
						}
					};
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}

			}
		};
	}

	@Override
	public JDialog getDialog() {
		return dialog;
	}
}
