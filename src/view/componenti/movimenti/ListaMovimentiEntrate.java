package view.componenti.movimenti;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import business.AltreUtil;
import business.aggiornatori.AggiornatoreManager;
import business.internazionalizzazione.I18NManager;
import domain.Entrate;
import domain.wrapper.Model;

public class ListaMovimentiEntrate extends AbstractListaMov {

	private static final long serialVersionUID = 1L;

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
	public void impostaTableSpecifico() {
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
							AggiornatoreManager.aggiornaMovimentiEntrateDaFiltro(createNomiColonne(), mov);
							return mov;
						}

						{
							// array per Categori

							final ArrayList<String> lista = new ArrayList<String>();
							lista.add("");
							lista.add(I18NManager.getSingleton().getMessaggio("variables"));
							lista.add(I18NManager.getSingleton().getMessaggio("fixity"));
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

	@Override
	protected String getTipo() {
		return Entrate.NOME_TABELLA;
	}

}
