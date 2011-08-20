package view.componenti.movimenti;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import view.Alert;
import view.entrateuscite.AbstractEntrateView;
import view.font.ButtonF;
import view.font.LabelListaGruppi;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Controllore;
import business.aggiornatori.AggiornatoreManager;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import business.cache.CacheEntrate;
import business.comandi.entrate.CommandDeleteEntrata;
import business.comandi.entrate.CommandUpdateEntrata;
import domain.Entrate;
import domain.IEntrate;
import domain.wrapper.Model;
import domain.wrapper.WrapEntrate;

public class DialogEntrateMov extends AbstractEntrateView {

	private static final long serialVersionUID = 1L;
	private JLabel labelEuro = new LabelListaGruppi("Euro");
	private JLabel labelData = new LabelListaGruppi("Data");
	private JLabel labelTipoEntrate = new LabelListaGruppi("Tipo Entrata");
	private JLabel labelDescrizione = new LabelListaGruppi("Descrizione");
	private JLabel labelNome = new LabelListaGruppi("Nome");
	private JLabel labelDataIns = new LabelListaGruppi("Data Inserimento");
	private JLabel labelIdEntrate = new LabelListaGruppi("Chiave Entrata");

	private JTextField tfEuro = new TextFieldF();
	private JTextField tfDataIns = new TextFieldF();
	private JTextField tfData = new TextFieldF();
	private JComboBox cbTipoEntrata = new JComboBox(Model.getNomiColonneEntrate());
	private JTextField taDescrizione = new TextFieldF();
	private JTextField tfNome = new TextFieldF();
	private JTextField idEntrate = new TextFieldF();
	private final JButton update = new ButtonF("Aggiorna");
	private final JButton delete = new ButtonF("Cancella");

	/**
	 * Auto-generated main method to display this JDialog
	 */

	public void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final DialogEntrateMov inst = new DialogEntrateMov(new WrapEntrate());
				inst.setVisible(true);
			}
		});
	}

	public DialogEntrateMov(final WrapEntrate entrate) {
		super(entrate);
		initGUI();
	}

	private void initGUI() {
		try {
			// questo permette di mantenere il focus sulla dialog
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			idEntrate.setEditable(false);
			this.setLayout(new GridLayout(0, 2));
			update.setSize(60, 40);
			delete.setSize(60, 40);
			labelData.setSize(100, 40);
			labelDescrizione.setSize(100, 40);
			labelEuro.setSize(100, 40);
			labelIdEntrate.setSize(100, 40);
			labelNome.setSize(100, 40);
			labelTipoEntrate.setSize(100, 40);
			labelDataIns.setSize(100, 40);

			update.addActionListener(new AscoltatoreDialogEntrate(this));
			delete.addActionListener(new AscoltatoreDialogEntrate(this));

			this.add(labelIdEntrate);
			this.add(idEntrate);
			this.add(labelNome);
			this.add(tfNome);
			this.add(labelDescrizione);
			this.add(taDescrizione);
			this.add(labelData);
			this.add(tfData);
			this.add(labelTipoEntrate);
			this.add(cbTipoEntrata);
			this.add(labelEuro);
			this.add(tfEuro);
			this.add(labelDataIns);
			this.add(tfDataIns);
			this.add(update);
			this.add(delete);
			setSize(300, 500);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private boolean nonEsistonoCampiNonValorizzati() {
		return getcNome() != null && getcDescrizione() != null && getcData() != null && getFisseOVar() != null && getdEuro() != 0.0 && getUtenti() != null;
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

	public void setTipoEntrata(final JComboBox tipoEntrata) {
		this.cbTipoEntrata = tipoEntrata;
	}

	public JComboBox getTipoEntrata() {
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
		// tfNome.setText(getcNome());
		// taDescrizione.setText(getcDescrizione());
		// cbTipoEntrata.setSelectedItem(getFisseOVar());
		// tfData.setText(getcData());
		// tfEuro.setText(getdEuro().toString());

	}

	public void setEntrate() {
		setDataIns(tfDataIns.getText());
		setnEntrate(idEntrate.getText());
		setcNome(tfNome.getText());
		setcDescrizione(taDescrizione.getText());
		setFisseOVar((String) cbTipoEntrata.getSelectedItem());
		if (AltreUtil.checkData(tfData.getText())) {
			setcData(tfData.getText());
		} else {
			final String messaggio = "La data va inserita con il seguente formato: aaaa/mm/gg";
			Alert.operazioniSegnalazioneErroreGrave(Alert.getMessaggioErrore(messaggio));
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			final Double euro1 = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro1));
		} else {
			final String messaggio = "Valore in Euro inserito non correttamente";
			Alert.operazioniSegnalazioneErroreGrave(Alert.getMessaggioErrore(messaggio));
		}
		setUtenti(Controllore.getSingleton().getUtenteLogin());
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
			if (e.getActionCommand().equals("Aggiorna")) {
				setEntrate();
				final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
				final JTextField campo = Controllore.getSingleton().getView().getTabMovimenti().getTabMovEntrate().getCampo();

				final Entrate oldEntrata = CacheEntrate.getSingleton().getEntrate(idEntrate.getText());

				if (nonEsistonoCampiNonValorizzati()) {
					if (Controllore.invocaComando(new CommandUpdateEntrata(oldEntrata, (IEntrate) modelEntrate.getentitaPadre()))) {
						try {
							AggiornatoreManager.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
						} catch (final Exception e22) {
							Alert.operazioniSegnalazioneErroreGrave(Alert.getMessaggioErrore(e22.getMessage()));
						}
						// chiude la dialog e rilascia le risorse
						dispose();
					}
				} else {
					Alert.operazioniSegnalazioneErroreGrave("E' necessario riempire tutti i campi");
				}
				dialog.dispose();
			} else if (e.getActionCommand().equals("Cancella")) {
				final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
				final JTextField campo = Controllore.getSingleton().getView().getTabMovimenti().getTabMovEntrate().getCampo();
				setEntrate();
				if (idEntrate.getText() != null) {
					if (!Controllore.invocaComando(new CommandDeleteEntrata(modelEntrate))) {
						Alert.operazioniSegnalazioneErroreGrave(Alert.getMessaggioErrore("Inserisci i dati correttamente"));
					}
				}

				if (campo != null) {
					AggiornatoreManager.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
				} else {
					AggiornatoreManager.aggiornaMovimentiEntrateDaEsterno(nomiColonne, 10);
				}
				// chiude la dialog e rilascia le risorse
				dialog.dispose();
			}
		}
	}

}