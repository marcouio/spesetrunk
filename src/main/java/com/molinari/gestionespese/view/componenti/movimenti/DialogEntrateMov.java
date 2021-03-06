package com.molinari.gestionespese.view.componenti.movimenti;

import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.cache.CacheEntrate;
import com.molinari.gestionespese.business.comandi.entrate.CommandDeleteEntrata;
import com.molinari.gestionespese.business.comandi.entrate.CommandUpdateEntrata;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.IEntrate;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.Model;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.gestionespese.view.PannelTabs;
import com.molinari.gestionespese.view.entrateuscite.AbstractEntrateView;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;
import com.molinari.utility.messages.I18NManager;

public class DialogEntrateMov extends AbstractEntrateView {

	private final JLabel labelEuro = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("eur"), this.getDialog());
	private final JLabel labelData = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("date"), this.getDialog());
	private final JLabel labelTipoEntrate = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("incometype"), this.getDialog());
	private final JLabel labelDescrizione = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("descr"), this.getDialog());
	private final JLabel labelNome = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("name"), this.getDialog());
	private final JLabel labelDataIns = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("insertdate"), this.getDialog());
	private final JLabel labelIdEntrate = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("key"), this.getDialog());

	private JTextField tfEuro = new TextFieldBase(this.getDialog());
	private JTextField tfDataIns = new TextFieldBase(this.getDialog());
	private JTextField tfData = new TextFieldBase(this.getDialog());
	private JComboBox<INCOMETYPE> cbTipoEntrata = new JComboBox<>(Model.getNomiColonneEntrate());
	private JTextField taDescrizione = new TextFieldBase(this.getDialog());
	private JTextField tfNome = new TextFieldBase(this.getDialog());
	private JTextField idEntrate = new TextFieldBase(this.getDialog());
	private final JButton update = new ButtonBase(I18NManager.getSingleton().getMessaggio("update"), this.getDialog());
	private final JButton delete = new ButtonBase(I18NManager.getSingleton().getMessaggio("delete"), this.getDialog());

	public DialogEntrateMov(final WrapEntrate entrate) {
		super(entrate);
		initGUI();
	}

	private void initGUI() {
		try {
			// questo permette di mantenere il focus sulla dialog
			this.getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
			idEntrate.setEditable(false);
			this.getDialog().setLayout(new GridLayout(0, 2));
			update.setSize(60, 40);
			delete.setSize(60, 40);
			labelData.setSize(100, 40);
			labelDescrizione.setSize(100, 40);
			labelEuro.setSize(100, 40);
			labelIdEntrate.setSize(100, 40);
			labelNome.setSize(100, 40);
			labelTipoEntrate.setSize(100, 40);
			labelDataIns.setSize(100, 40);

			update.addActionListener(new AscoltatoreDialogEntrate(this.getDialog()));
			delete.addActionListener(new AscoltatoreDialogEntrate(this.getDialog()));

			this.getDialog().add(labelIdEntrate);
			this.getDialog().add(idEntrate);
			this.getDialog().add(labelNome);
			this.getDialog().add(tfNome);
			this.getDialog().add(labelDescrizione);
			this.getDialog().add(taDescrizione);
			this.getDialog().add(labelData);
			this.getDialog().add(tfData);
			this.getDialog().add(labelTipoEntrate);
			this.getDialog().add(cbTipoEntrata);
			this.getDialog().add(labelEuro);
			this.getDialog().add(tfEuro);
			this.getDialog().add(labelDataIns);
			this.getDialog().add(tfDataIns);
			this.getDialog().add(update);
			this.getDialog().add(delete);
			getDialog().setSize(300, 500);
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void setEuro(final JTextField euro) {
		this.tfEuro = euro;
	}

	public JTextField getEuro() {
		return tfEuro;
	}

	public void setData(final JTextField data) {
		this.tfData = data;
	}

	public JTextField getData() {
		return tfData;
	}

	public void setDescrizione(final JTextField descrizione) {
		this.taDescrizione = descrizione;
	}

	public JTextField getDescrizione() {
		return taDescrizione;
	}

	public void setTipoEntrata(final JComboBox<INCOMETYPE> tipoEntrata) {
		this.cbTipoEntrata = tipoEntrata;
	}

	public JComboBox<INCOMETYPE> getTipoEntrata() {
		return cbTipoEntrata;
	}

	public void setNome(final JTextField nome) {
		this.tfNome = nome;
	}

	public JTextField getNome() {
		return tfNome;
	}

	public void setIdEntrate(final JTextField idEntrate) {
		this.idEntrate = idEntrate;
	}

	public JTextField getIdEntrate() {
		return idEntrate;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		//do nothing

	}

	@Override
	public void aggiornaModelDaVista() {
		setDataIns(tfDataIns.getText());
		setnEntrate(idEntrate.getText());
		setcNome(tfNome.getText());
		setcDescrizione(taDescrizione.getText());
		
		int ordinal = ((INCOMETYPE) cbTipoEntrata.getSelectedItem()).ordinal();
		setFisseOVar(Integer.toString(ordinal));
		
		setcData(tfData.getText());
		
		final Double euro1 = Double.parseDouble(tfEuro.getText());
		setdEuro(AltreUtil.arrotondaDecimaliDouble(euro1));
		
		setUtenti((Utenti) Controllore.getUtenteLogin());
	}

	protected JTextField getTfDataIns() {
		return tfDataIns;
	}

	protected void setTfDataIns(final JTextField tfDataIns) {
		this.tfDataIns = tfDataIns;
	}

	public class AscoltatoreDialogEntrate extends AscoltatoreAggiornatoreNiente {

		JDialog dialog;

		public AscoltatoreDialogEntrate(final JDialog dialog) {
			this.dialog = dialog;
		}
		
		@Override
		protected void actionPerformedOverride(final ActionEvent e) {
			super.actionPerformedOverride(e);
			if (e.getActionCommand().equals(I18NManager.getSingleton().getMessaggio("update"))) {
				update();
			} else if (e.getActionCommand().equals(I18NManager.getSingleton().getMessaggio("delete"))) {
				delete();
			}
		}

		private void delete() {
			final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
			final JTextField campo = Controllore.getGeneralFrame().getPannelTabs().getTabMovEntrate().getCampo();
			aggiornaModelDaVista();
			if (idEntrate.getText() != null && !Controllore.invocaComando(new CommandDeleteEntrata(getModelEntrate()))) {
				Alert.segnalazioneErroreGrave(Alert.getMessaggioErrore(I18NManager.getSingleton().getMessaggio("insertcorrect")));
			}

			if (campo != null) {
				AggiornatoreManager.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
			} else {
				AggiornatoreManager.aggiornaMovimentiEntrateDaEsterno(nomiColonne, 10);
			}
			// chiude la dialog e rilascia le risorse
			dialog.dispose();
		}

		private void update()  {
			aggiornaModelDaVista();
			final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
			final PannelTabs pannelTabs = Controllore.getGeneralFrame().getPannelTabs();
			final JTextField campo = pannelTabs.getTabMovEntrate().getCampo();

			final IEntrate oldEntrata = CacheEntrate.getSingleton().getEntrate(idEntrate.getText());

			String checkFields = nonEsistonoCampiNonValorizzati();
			if (checkFields == null) {
				if (Controllore.invocaComando(new CommandUpdateEntrata(oldEntrata, getModelEntrate().getEntitaPadre()))) {
					try {
						AggiornatoreManager.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
					} catch (final Exception e22) {
						ControlloreBase.getLog().log(Level.SEVERE, e22.getMessage(), e22);
						Alert.segnalazioneErroreGrave(Alert.getMessaggioErrore(e22.getMessage()));
					}
					// chiude la dialog e rilascia le risorse
					getDialog().dispose();
				}
			} else {
				Alert.segnalazioneErroreGrave(checkFields);
			}
			dialog.dispose();
		}
	}
}
