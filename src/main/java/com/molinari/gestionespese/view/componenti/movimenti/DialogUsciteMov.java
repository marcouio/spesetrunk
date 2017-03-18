package com.molinari.gestionespese.view.componenti.movimenti;

import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.business.comandi.singlespese.CommandDeleteSpesa;
import com.molinari.gestionespese.business.comandi.singlespese.CommandUpdateSpesa;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.Model;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.gestionespese.view.entrateuscite.AbstractUsciteView;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.TextFieldF;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;

public class DialogUsciteMov extends AbstractUsciteView {

	private final JLabel labelEuro = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("eur"));
	private final JLabel labelData = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("date"));
	private final JLabel labelCategoria = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("category"));
	private final JLabel labelDescrizione = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("descr"));
	private final JLabel labelNome = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("name"));
	private final JLabel labelDataIns = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("insertdate"));
	private final JLabel labelIdSpesa = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("key"));

	private JTextField tfEuro = new TextFieldF();
	private JTextField tfData = new TextFieldF();
	private JComboBox<ICatSpese> cbCategorie;
	private JTextField taDescrizione = new TextFieldF();
	private JTextField tfNome = new TextFieldF();
	private final JTextField tfDataIns = new TextFieldF();
	private JTextField idSpesa = new TextFieldF();
	private final JButton update = new ButtonF(I18NManager.getSingleton().getMessaggio("update"));
	private final JButton delete = new ButtonF(I18NManager.getSingleton().getMessaggio("delete"));

	public DialogUsciteMov(final WrapSingleSpesa uscita) {
		super(uscita);
		initGUI();
	}

	private void initGUI() {
		try {
			// questo permette di mantenere il focus sulla dialog
			getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
			cbCategorie = new JComboBox<>(Model.getSingleton().getCategorieCombo(false));
			idSpesa.setEditable(false);
			getDialog().setLayout(new GridLayout(0, 2));
			update.setSize(60, 40);
			delete.setSize(60, 40);
			labelData.setSize(100, 40);
			labelDescrizione.setSize(100, 40);
			labelEuro.setSize(100, 40);
			labelIdSpesa.setSize(100, 40);
			labelNome.setSize(100, 40);
			labelCategoria.setSize(100, 40);
			labelDataIns.setSize(100, 40);

			update.addActionListener(new AscoltatoreDialogUscite(this));
			delete.addActionListener(new AscoltatoreDialogUscite(this));

			getDialog().add(labelIdSpesa);
			getDialog().add(idSpesa);
			getDialog().add(labelNome);
			getDialog().add(tfNome);
			getDialog().add(labelDescrizione);
			getDialog().add(taDescrizione);
			getDialog().add(labelData);
			getDialog().add(tfData);
			getDialog().add(labelCategoria);
			getDialog().add(cbCategorie);
			getDialog().add(labelEuro);
			getDialog().add(tfEuro);
			getDialog().add(labelDataIns);
			getDialog().add(tfDataIns);
			getDialog().add(update);
			getDialog().add(delete);
			getDialog().setSize(300, 500);
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void aggiornaModelDaVista() {
		if (AltreUtil.checkInteger(idSpesa.getText())) {
			getModelUscita().setidSpesa(idSpesa.getText() != "" ? Integer.parseInt(idSpesa.getText()) : 0);
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("idintero");
			Alert.segnalazioneErroreGrave(messaggio);
		}
		setcNome(tfNome.getText());
		setcDescrizione(taDescrizione.getText());
		setCategoria((CatSpese) cbCategorie.getSelectedItem());
		if (AltreUtil.checkData(tfData.getText())) {
			setcData(tfData.getText());
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("datainformat");
			Alert.errore(messaggio, Alert.TITLE_ERROR);
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			final Double euro = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		} else {
			final String messaggio = I18NManager.getSingleton().getMessaggio("valorenotcorrect");
			Alert.errore(messaggio, Alert.TITLE_ERROR);
		}
		setUtenti((Utenti) Controllore.getSingleton().getUtenteLogin());
		setDataIns(tfDataIns.getText());
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

	public void setCategoria(final JComboBox<ICatSpese> categoria) {
		this.cbCategorie = categoria;
	}

	public JComboBox<ICatSpese> getComboCategoria() {
		return cbCategorie;
	}

	public void setNome(final JTextField nome) {
		this.tfNome = nome;
	}

	public JTextField getNome() {
		return tfNome;
	}

	public void setIdSpesa(final JTextField idSpesa) {
		this.idSpesa = idSpesa;
	}

	public JTextField getIdSpesa() {
		return idSpesa;
	}

	public JTextField getTfDataIns() {
		return tfDataIns;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		//do nothing
	}

	public class AscoltatoreDialogUscite implements ActionListener {

		DialogUsciteMov dialog;

		public AscoltatoreDialogUscite(final DialogUsciteMov dialog) {
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			if (e.getActionCommand().equals(I18NManager.getSingleton().getMessaggio("update"))) {
				update();

			} else if ("Cancella".equals(e.getActionCommand())) {
				cancella();
			}
		}

		private void cancella() {
			aggiornaModelDaVista();
			try {
				if (!Controllore.invocaComando(new CommandDeleteSpesa(getModelUscita()))) {
					final String msg = I18NManager.getSingleton().getMessaggio("charge")+ getModelUscita().getNome() + " non aggiornata: tutti i dati devono essere valorizzati";
					Alert.segnalazioneErroreGrave(msg);
				}
			} catch (final Exception e1) {
				Alert.segnalazioneEccezione(e1, null);
			}
			// chiude la dialog e rilascia le risorse
			getDialog().dispose();
		}

		private void update() {
			aggiornaModelDaVista();
			final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(SingleSpesa.NOME_TABELLA);
			final JTextField campo = Controllore.getSingleton().getGeneralFrame().getPannelTabs().getTabMovUscite().getCampo();
			final ISingleSpesa oldSpesa = CacheUscite.getSingleton().getSingleSpesa(idSpesa.getText());

			if (dialog.nonEsistonoCampiNonValorizzati()) {
				try {
					if (!Controllore.invocaComando(new CommandUpdateSpesa(oldSpesa, (ISingleSpesa) getModelUscita().getEntitaPadre()))) {
						Alert.segnalazioneErroreGrave("Spesa " + oldSpesa.getNome() + " non aggiornata");
					}
				} catch (final Exception e1) {
					Alert.segnalazioneEccezione(e1, null);
				}
				AggiornatoreManager.aggiornaMovimentiUsciteDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));

				// chiude la dialog e rilascia le risorse
				getDialog().dispose();
			} else {
				final String msg = I18NManager.getSingleton().getMessaggio("charge")+ oldSpesa.getNome() + " non aggiornata: tutti i dati devono essere valorizzati";
				Alert.segnalazioneErroreGrave(msg);
			}
		}
	}

}
