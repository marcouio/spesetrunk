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

import org.apache.commons.math3.util.MathUtils;

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
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.TextFieldF;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.messages.I18NManager;

public class DialogEntrateMov extends AbstractEntrateView {

	private final JLabel labelEuro = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("eur"));
	private final JLabel labelData = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("date"));
	private final JLabel labelTipoEntrate = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("incometype"));
	private final JLabel labelDescrizione = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("descr"));
	private final JLabel labelNome = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("name"));
	private final JLabel labelDataIns = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("insertdate"));
	private final JLabel labelIdEntrate = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("key"));

	private JTextField tfEuro = new TextFieldF();
	private JTextField tfDataIns = new TextFieldF();
	private JTextField tfData = new TextFieldF();
	private JComboBox<INCOMETYPE> cbTipoEntrata = new JComboBox<>(Model.getNomiColonneEntrate());
	private JTextField taDescrizione = new TextFieldF();
	private JTextField tfNome = new TextFieldF();
	private JTextField idEntrate = new TextFieldF();
	private final JButton update = new ButtonF(I18NManager.getSingleton().getMessaggio("update"));
	private final JButton delete = new ButtonF(I18NManager.getSingleton().getMessaggio("delete"));

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
		
		if (AltreUtil.checkData(tfData.getText())) {
			setcData(tfData.getText());
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("datainformat") ;
			Alert.segnalazioneErroreGrave(Alert.getMessaggioErrore(messaggio));
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			final Double euro1 = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro1));
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("valorenotcorrect");
			Alert.segnalazioneErroreGrave(Alert.getMessaggioErrore(messaggio));
		}
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

			if (nonEsistonoCampiNonValorizzati()) {
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
				Alert.segnalazioneErroreGrave(I18NManager.getSingleton().getMessaggio("fillinall"));
			}
			dialog.dispose();
		}
	}
	
	@Override
	public boolean nonEsistonoCampiNonValorizzati() {
		final boolean nomeDescrDataOk = getcNome() != null && getcDescrizione() != null && getcData() != null;
		boolean eurozero = MathUtils.equals(getdEuro(), 0);
		return nomeDescrDataOk && getFisseOVar() != null && !eurozero && getUtenti() != null;
	}
}
