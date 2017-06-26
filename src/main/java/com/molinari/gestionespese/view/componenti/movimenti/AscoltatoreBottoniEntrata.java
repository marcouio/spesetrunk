package com.molinari.gestionespese.view.componenti.movimenti;

import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.wrapper.Model;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.gestionespese.view.entrateuscite.EntrateView.INCOMETYPE;
import com.molinari.utility.graphic.component.alert.Alert;

public class AscoltatoreBottoniEntrata extends MouseAdapter {

	private final JTable table;

	private Object[] arrayUtil = new Object[7];

	public static final int INDEX_DATA = 0;
	public static final int INDEX_NOME = 1;
	public static final int INDEX_DESCRIZIONE = 2;
	public static final int INDEX_EURO = 3;
	public static final int INDEX_TIPO = 4;
	public static final int INDEX_IDENTRATA = 5;
	public static final int INDEX_DATAINS = 6;

	public AscoltatoreBottoniEntrata(final JTable table) {
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
			arrayUtil[INDEX_TIPO] = tabella.getValueAt(row, 4);
			arrayUtil[INDEX_IDENTRATA] = tabella.getValueAt(row, 5);
			arrayUtil[INDEX_DATAINS] = tabella.getValueAt(row, 6);

			final DialogEntrateMov dialogNew = new DialogEntrateMov(new WrapEntrate());

			settaDialog(dialogNew);

		}
	}

	public Object[] getArrayUtil() {
		return arrayUtil;
	}

	public void setArrayUtil(final Object[] arrayUtil) {
		this.arrayUtil = arrayUtil;
	}

	/**
	 * Valorizza la dialog con i valori catturati dalla riga selezionata dalla
	 * tabella
	 *
	 * @param row
	 * @param dialogNew
	 */
	private void settaDialog( final DialogEntrateMov dialogNew) {
		dialogNew.getIdEntrate().setText((String) arrayUtil[INDEX_IDENTRATA]);
		dialogNew.getNome().setText((String) arrayUtil[INDEX_NOME]);
		dialogNew.getDescrizione().setText((String) arrayUtil[INDEX_DESCRIZIONE]);
		dialogNew.getData().setText((String) arrayUtil[INDEX_DATA]);
		dialogNew.getTfDataIns().setText((String) arrayUtil[INDEX_DATAINS]);
		dialogNew.getEuro().setText((String) arrayUtil[INDEX_EURO]);
		final String tipoEntry = (String) arrayUtil[INDEX_TIPO];
		final INCOMETYPE[] listaEntr = Model.getNomiColonneEntrate();
		for (int i = 0; i < listaEntr.length; i++) {
			if (listaEntr[i].equals(tipoEntry)) {
				dialogNew.getTipoEntrata().setSelectedIndex(i);
			}
		}
		// se si è fatto clic su una riga con valori reali (non tutti 0) viene
		// visualizzata la dialog e settata l'entita associata
		if (arrayUtil[INDEX_IDENTRATA] != "0") {

			dialogNew.getDialog().setSize(400, 220);
			dialogNew.getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
			dialogNew.getDialog().setVisible(true);
			dialogNew.aggiornaModelDaVista();
		}
		try {
			AggiornatoreManager.aggiornamentoGenerale(Entrate.NOME_TABELLA);
		} catch (final Exception e1) {
			Alert.segnalazioneEccezione(e1, null);
		}
		dialogNew.getDialog().dispose();

	}

}
