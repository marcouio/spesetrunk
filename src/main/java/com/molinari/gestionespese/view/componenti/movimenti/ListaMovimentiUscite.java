package com.molinari.gestionespese.view.componenti.movimenti;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.wrapper.Model;

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
	public void impostaTableSpecifico() {

	}

	@Override
	public ActionListener getListener() {
		return e -> {
			try {
				final FiltraDialog dialog = new FiltraDialog() {
					private static final long serialVersionUID = 1L;

					@Override
					public String[][] getMovimenti() {
						final List<SingleSpesa> uscite = Model.getSingleton().getModelUscita()
								.movimentiUsciteFiltrate(getDataDa(), getDataA(), getNome(), getEuro(), getCategoria());
						final String[][] mov = Model.getSingleton().movimentiFiltratiUscitePerNumero(Entrate.NOME_TABELLA, uscite);
						AggiornatoreManager.aggiornaMovimentiUsciteDaFiltro(createNomiColonne(), mov);
						return mov;
					}

					{
						List<CatSpese> listCategoriePerCombo = CacheCategorie.getSingleton().getListCategoriePerCombo();
						comboBoxCat = new JComboBox(new Vector<CatSpese>(listCategoriePerCombo));
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
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (final Exception e1) {
				e1.printStackTrace();
			}

		};
	}

	@Override
	protected String getTipo() {
		return SingleSpesa.NOME_TABELLA;
	}
}
