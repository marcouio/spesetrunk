package view.componenti.movimenti;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;

import business.AltreUtil;
import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;
import domain.Entrate;
import domain.SingleSpesa;
import domain.wrapper.Model;
import domain.wrapper.WrapSingleSpesa;

public class ListaMovimentiUscite extends AbstractListaMov {

	private static final long         serialVersionUID = 1L;
	private AscoltatoreMouseMovUscite ascoltatore;

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
		ascoltatore = new AscoltatoreMouseMovUscite(table, dialog);
		table.addMouseListener(ascoltatore);

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
							final Vector<SingleSpesa> uscite = Model.getSingleton().getModelUscita()
							                .movimentiUsciteFiltrate(getDataDa(), getDataA(), getNome(), getEuro(), getCategoria());
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

	{
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					dialog = getDialog();
					dialog.setSize(400, 220);
					dialog.setVisible(true);
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				dialog = getDialog();
				dialog.setSize(400, 220);
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				try {
					Database.aggiornamentoGenerale(getTipo());
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	@Override
	public JDialog getDialog() {
		return dialog;
	}

	@Override
	protected String getTipo() {
		return SingleSpesa.NOME_TABELLA;
	}

	protected AscoltatoreMouseMovUscite getAscoltatore() {
		return ascoltatore;
	}

	protected void setAscoltatore(final AscoltatoreMouseMovUscite ascoltatore) {
		this.ascoltatore = ascoltatore;
	}
}
