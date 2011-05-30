package view.componenti.movimenti;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;

import business.AltreUtil;
import business.Database;
import domain.Entrate;
import domain.wrapper.Model;
import domain.wrapper.WrapEntrate;

public class ListaMovimentiEntrate extends AbstractListaMov {

	private static final long          serialVersionUID = 1L;
	private AscoltatoreMouseMovEntrate ascoltatore;

	public ListaMovimentiEntrate() {
		super();
		pulsanteNMovimenti.addActionListener(new AscoltatoreNumeroMovimenti(Entrate.NOME_TABELLA, createNomiColonne(), campo));
	}

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
	public void impostaTableSpecifico(final JTable table) {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		ascoltatore = new AscoltatoreMouseMovEntrate(table);
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
							final Vector<Entrate> entrate = Model.getSingleton().getModelEntrate()
							                .movimentiEntrateFiltrati(getDataDa(), getDataA(), getNome(), getEuro(), getCategoria());
							final String[][] mov = Model.getSingleton().movimentiFiltratiEntratePerNumero(Entrate.NOME_TABELLA, entrate);
							Model.aggiornaMovimentiEntrateDaFiltro(createNomiColonne(), mov);
							return mov;
						}

						{
							// array per Categori

							final ArrayList<String> lista = new ArrayList<String>();
							lista.add("");
							lista.add("Variabili");
							lista.add("Fisse");
							comboBoxCat = new JComboBox(lista.toArray());

							comboBoxCat.setBounds(512, 26, 89, 25);
							getContentPane().add(comboBoxCat);
						}

						@Override
						protected String getCategoria() {
							if (comboBoxCat.getSelectedItem() != null && comboBoxCat.getSelectedIndex() != 0) {
								categoria = (String) comboBoxCat.getSelectedItem();
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
					dialog = ascoltatore.getDialog();
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
				dialog = ascoltatore.getDialog();
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
		return dialog = createDialog();
	}

	@Override
	protected String getTipo() {
		return Entrate.NOME_TABELLA;
	}

	protected AscoltatoreMouseMovEntrate getAscoltatore() {
		return ascoltatore;
	}

	protected void setAscoltatore(final AscoltatoreMouseMovEntrate ascoltatore) {
		this.ascoltatore = ascoltatore;
	}
}
