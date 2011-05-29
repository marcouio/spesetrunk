package view.componenti.movimenti;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import view.entrateuscite.AbstractEntrateView;
import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Controllore;
import business.cache.CacheEntrate;
import business.comandi.CommandDeleteEntrata;
import business.comandi.CommandUpdateEntrata;
import domain.Entrate;
import domain.IEntrate;
import domain.wrapper.Model;
import domain.wrapper.WrapEntrate;

public class DialogEntrateMov extends AbstractEntrateView {

	private JLabel        labelEuro        = new LabelTesto("Euro");
	private JLabel        labelData        = new LabelTesto("Data");
	private JLabel        labelTipoEntrate = new LabelTesto("Tipo Entrata");
	private JLabel        labelDescrizione = new LabelTesto("Descrizione");
	private JLabel        labelNome        = new LabelTesto("Nome");
	private JLabel        labelDataIns     = new LabelTesto("Data Inserimento");
	private JLabel        labelIdEntrate   = new LabelTesto("Chiave Entrata");

	private JTextField    tfEuro           = new TextFieldF();
	private JTextField    tfDataIns        = new TextFieldF();
	private JTextField    tfData           = new TextFieldF();
	// TODO verificare se � necessario sostituire campo tipoEntrata
	private JComboBox     cbTipoEntrata    = new JComboBox(Model.getNomiColonneEntrate());
	// private JTextField tipoEntrata = new TextFieldF();
	private JTextField    taDescrizione    = new TextFieldF();
	private JTextField    tfNome           = new TextFieldF();
	private JTextField    idEntrate        = new TextFieldF();
	private final JButton update           = new ButtonF("Aggiorna");
	private final JButton delete           = new ButtonF("Cancella");

	private final long    serialVersionUID = 1L;

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

			update.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					setEntrate();
					final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
					final JTextField campo = Controllore.getSingleton().getView().getTabMovimenti().getTabMovEntrate().getCampo();

					final Entrate oldEntrata = CacheEntrate.getSingleton().getEntrate(idEntrate.getText());

					if (nonEsistonoCampiNonValorizzati()) {
						if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandUpdateEntrata(oldEntrata, (IEntrate) modelEntrate.getentitaPadre()), Entrate.NOME_TABELLA)) {
							JOptionPane.showMessageDialog(null, "Ok, entrata inserita correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
							try {
								Model.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
							} catch (final Exception e22) {
								JOptionPane.showMessageDialog(null, "Inserisci i dati correttamente", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
							}
							// chiude la dialog e rilascia le risorse
							dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "E' necessario riempire tutti i campi", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
					}
				}

			});
			delete.addActionListener(new ActionListener() {
				String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);

				@Override
				public void actionPerformed(final ActionEvent e) {
					final JTextField campo = Controllore.getSingleton().getView().getTabMovimenti().getTabMovEntrate().getCampo();
					setEntrate();
					if (idEntrate.getText() != null)
						if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteEntrata(modelEntrate), "tutto"))
							JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente", "Perfetto!", JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "Inserisci i dati correttamente", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));

					if (campo != null)
						Model.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
					else {
						Model.aggiornaMovimentiEntrateDaEsterno(nomiColonne, 10);
					}
					// chiude la dialog e rilascia le risorse
					dispose();
				}
			});

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
		return getcNome() != null && getcDescrizione() != null && getcData() != null
		                && getFisseOVar() != null && getdEuro() != 0.0 && getUtenti() != null;
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
		tfNome.setText(getcNome());
		taDescrizione.setText(getcDescrizione());
		cbTipoEntrata.setSelectedItem(getFisseOVar());
		tfData.setText(getcData());
		tfEuro.setText(getdEuro().toString());

	}

	private void setEntrate() {
		setDataIns(tfDataIns.getText());
		setnEntrate(idEntrate.getText());
		setcNome(tfNome.getText());
		setcDescrizione(taDescrizione.getText());
		setFisseOVar((String) cbTipoEntrata.getSelectedItem());
		if (AltreUtil.checkData(tfData.getText())) {
			setcData(tfData.getText());
		} else {
			final String messaggio = "La data va inserita con il seguente formato: aaaa/mm/gg";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			final Double euro1 = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro1));
		} else {
			final String messaggio = "Valore in Euro inserito non correttamente";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		setUtenti(Controllore.getSingleton().getUtenteLogin());
	}

	protected JTextField getTfDataIns() {
		return tfDataIns;
	}

	protected void setTfDataIns(final JTextField tfDataIns) {
		this.tfDataIns = tfDataIns;
	}

}
