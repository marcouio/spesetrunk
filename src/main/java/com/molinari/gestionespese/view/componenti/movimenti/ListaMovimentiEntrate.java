package com.molinari.gestionespese.view.componenti.movimenti;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.wrapper.Model;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.gestionespese.view.entrateuscite.AbstractEntrateView.INCOMETYPE;
import com.molinari.utility.controller.ControlloreBase;

public class ListaMovimentiEntrate extends AbstractListaMov {

	private static final long serialVersionUID = 1L;

	public ListaMovimentiEntrate(Container contenitore) {
		super(contenitore);
		pulsanteNMovimenti.addActionListener(new AscoltatoreNumeroMovimenti(Entrate.NOME_TABELLA, createNomiColonne(), campo));
	}

	@Override
	public String[][] createMovimenti() {
		return Model.movimentiEntrate(numMovimenti, Entrate.NOME_TABELLA);
	}

	@Override
	public String[] createNomiColonne() {
		return (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
	}

	@Override
	public void impostaTableSpecifico() {
		//do nothing
	}

	@Override
	public ActionListener getListener() {
		return e -> {
			try {
				final FiltraDialog dialog = new FiltraDialog() {

					private static final long serialVersionUID = 1L;

					@Override
					public String[][] getMovimenti() {
						try {
							final WrapEntrate modelEntrate = Model.getSingleton().getModelEntrate();
							final List<Entrate> entrate = modelEntrate.movimentiEntrateFiltrati(getDataDa(), getDataA(), getNome(), getEuro(), getCategoria());
							final String[][] mov = Model.movimentiFiltratiEntratePerNumero(Entrate.NOME_TABELLA, entrate, numMovimenti);
							AggiornatoreManager.aggiornaMovimentiEntrateDaFiltro(createNomiColonne(), mov);
							return mov;
						} catch (SQLException e) {
							ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
						}
						return new String[][]{};
					}

					{
						// array per Categori

						comboBoxCat = new JComboBox<>(INCOMETYPE.values());

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
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (final Exception e1) {
				ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
			}

		};
	}

	@Override
	protected String getTipo() {
		return Entrate.NOME_TABELLA;
	}

}
