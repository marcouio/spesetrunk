package com.molinari.gestionespese.view.componenti.movimenti;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.wrapper.Model;
import com.molinari.utility.math.UtilMath;

public class ListaMovimentiUscite extends AbstractListaMov {

	private final class FiltraDialogExtension extends FiltraDialog {
		
		private static final long serialVersionUID = 1L;

		private FiltraDialogExtension(){
			final List<ICatSpese> listCategoriePerCombo = CacheCategorie.getSingleton().getListCategoriePerCombo();
			comboBoxCat = new JComboBox<ICatSpese>(new Vector<>(listCategoriePerCombo));
			comboBoxCat.setBounds(512, 26, 89, 25);
			getContentPane().add(comboBoxCat);
			if(getCampo().getText() != null && UtilMath.isNumber(getCampo().getText())){
				setNumEntry(Integer.parseInt(getCampo().getText()));
			}
		}

		@Override
		public String[][] getMovimenti() {
			
			final List<ISingleSpesa> uscite = Model.getSingleton().getModelUscita().movimentiUsciteFiltrate(getDataDa(), getDataA(), getNome(), getEuro(), getCategoria());
			final String[][] mov = Model.movimentiFiltratiUscitePerNumero(Entrate.NOME_TABELLA, uscite, getNumEntry());
			AggiornatoreManager.aggiornaMovimentiUsciteDaFiltro(createNomiColonne(), mov);
			return mov;
		}

		@Override
		protected String getCategoria() {
			if (comboBoxCat.getSelectedItem() != null) {
				final int idCat = ((CatSpese) comboBoxCat.getSelectedItem()).getidCategoria();
				categoria = Integer.toString(idCat);
			}
			return categoria;
		}
	}

	private static final long serialVersionUID = 1L;

	public ListaMovimentiUscite(Container contenitore) {
		super(contenitore);
		pulsanteNMovimenti.addActionListener(new AscoltatoreNumeroMovimenti(SingleSpesa.NOME_TABELLA, createNomiColonne(), campo));
	}

	@Override
	public String[][] createMovimenti() {
		return Model.movimentiUscite(numMovimenti, SingleSpesa.NOME_TABELLA);
	}

	@Override
	public String[] createNomiColonne() {
		return (String[]) AltreUtil.generaNomiColonne(SingleSpesa.NOME_TABELLA);
	}

	@Override
	public void impostaTableSpecifico() {
		//do nothing
	}

	@Override
	public ActionListener getListener() {
		return e -> {
			try {
				final FiltraDialog dialog = new FiltraDialogExtension();
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (final Exception e1) {
				Controllore.getLog().log(Level.SEVERE, e1.getMessage(), e1);
			}

		};
	}

	@Override
	protected String getTipo() {
		return SingleSpesa.NOME_TABELLA;
	}
}
