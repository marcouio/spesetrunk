package com.molinari.gestionespese.view.componenti.movimenti;

import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTable;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.utility.graphic.component.alert.Alert;

public class AscoltatoreBottoniUscita extends MouseAdapter {

	private final JTable table;

	private Object[] arrayUtil = new Object[7];

	public static final int INDEX_IDSPESA = 5;
	public static final int INDEX_NOME = 1;
	public static final int INDEX_DESCRIZIONE = 2;
	public static final int INDEX_EURO = 3;
	public static final int INDEX_CATEGORIA = 4;
	public static final int INDEX_DATA = 0;
	public static final int INDEX_DATAINS = 6;

	public AscoltatoreBottoniUscita(final JTable table) {
		this.table = table;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {

		final JTable tableLoc = this.table;
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1 && e.getSource() == tableLoc) {
			final JTable tabella = (JTable) e.getSource();
			final int row = tabella.getSelectedRow();
			arrayUtil[INDEX_DATA] = tabella.getValueAt(row, 0);
			arrayUtil[INDEX_NOME] = tabella.getValueAt(row, 1);
			arrayUtil[INDEX_DESCRIZIONE] = tabella.getValueAt(row, 2);
			arrayUtil[INDEX_EURO] = tabella.getValueAt(row, 3);
			arrayUtil[INDEX_CATEGORIA] = tabella.getValueAt(row, 4);
			arrayUtil[INDEX_IDSPESA] = tabella.getValueAt(row, 5);
			arrayUtil[INDEX_DATAINS] = tabella.getValueAt(row, 6);

			final DialogUsciteMov dialogNew = new DialogUsciteMov(new WrapSingleSpesa());

			settaDialog(dialogNew);

		}
	}

	/**
	 * Valorizza la dialog con i valori catturati dalla riga selezionata dalla
	 * tabella
	 *
	 * @param row
	 * @param dialogNew
	 */
	private void settaDialog(final DialogUsciteMov dialogNew) {
		dialogNew.getIdSpesa().setText((String) arrayUtil[INDEX_IDSPESA]);
		dialogNew.getNome().setText((String) arrayUtil[INDEX_NOME]);
		dialogNew.getDescrizione().setText((String) arrayUtil[INDEX_DESCRIZIONE]);
		dialogNew.getData().setText((String) arrayUtil[INDEX_DATA]);
		dialogNew.getTfDataIns().setText((String) arrayUtil[INDEX_DATAINS]);
		dialogNew.getEuro().setText((String) arrayUtil[INDEX_EURO]);

		final List<ICatSpese> cate = CacheCategorie.getSingleton().getVettoreCategorie();
		for (int i = 0; i < cate.size(); i++) {
			if (cate.get(i).getnome().equals(arrayUtil[INDEX_CATEGORIA])) {
				dialogNew.getComboCategoria().setSelectedIndex(i);
				break;
			}
		}

		if (arrayUtil[INDEX_IDSPESA] != "0") {
			dialogNew.getDialog().setSize(400, 220);
			dialogNew.getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
			dialogNew.aggiornaModelDaVista();
			dialogNew.getDialog().setVisible(true);
		}
		try {
			AggiornatoreManager.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
		} catch (final Exception e1) {
			Alert.segnalazioneEccezione(e1, null);
		}
		dialogNew.getDialog().dispose();
	}

	public Object[] getArrayUtil() {
		return arrayUtil;
	}

	public void setArrayUtil(final Object[] arrayUtil) {
		this.arrayUtil = arrayUtil;
	}
}
