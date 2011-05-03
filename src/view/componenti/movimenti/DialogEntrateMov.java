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

	private static JLabel     labelEuro        = new LabelTesto("Euro");
	private static JLabel     labelData        = new LabelTesto("Data");
	private static JLabel     labelTipoEntrate = new LabelTesto("Tipo Entrata");
	private static JLabel     labelDescrizione = new LabelTesto("Descrizione");
	private static JLabel     labelNome        = new LabelTesto("Nome");
	private static JLabel     labelDataIns     = new LabelTesto("Data Inserimento");
	private static JLabel     labelIdEntrate   = new LabelTesto("Chiave Entrata");

	private static JTextField tfEuro           = new TextFieldF();
	private static JTextField tfDataIns        = new TextFieldF();
	private static JTextField tfData           = new TextFieldF();
	// TODO verificare se � necessario sostituire campo tipoEntrata
	private static JComboBox  cbTipoEntrata    = new JComboBox(Model.getNomiColonneEntrate());
	// private static JTextField tipoEntrata = new TextFieldF();
	private static JTextField taDescrizione    = new TextFieldF();
	private static JTextField tfNome           = new TextFieldF();
	private static JTextField idEntrate        = new TextFieldF();
	private final JButton     update           = new ButtonF("Aggiorna");
	private final JButton     delete           = new ButtonF("Cancella");

	private static final long serialVersionUID = 1L;

	/**
	 * Auto-generated main method to display this JDialog
	 */

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DialogEntrateMov inst = new DialogEntrateMov(new WrapEntrate());
				inst.setVisible(true);
			}
		});
	}

	public DialogEntrateMov(WrapEntrate entrate) {
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
				public void actionPerformed(ActionEvent e) {
					setEntrate();
					String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(Entrate.NOME_TABELLA);
					JTextField campo = Controllore.getSingleton().getView().getTabMovimenti().getTabMovEntrate().getCampo();

					Entrate oldEntrata = CacheEntrate.getSingleton().getEntrate(idEntrate.getText());

					if (nonEsistonoCampiNonValorizzati()) {
						if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandUpdateEntrata(oldEntrata, (IEntrate) modelEntrate.getentitaPadre()), Entrate.NOME_TABELLA)) {
							JOptionPane.showMessageDialog(null, "Ok, entrata inserita correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
							try {
								Model.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
							} catch (Exception e22) {
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
				public void actionPerformed(ActionEvent e) {
					JTextField campo = Controllore.getSingleton().getView().getTabMovimenti().getTabMovEntrate().getCampo();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean nonEsistonoCampiNonValorizzati() {
		return getcNome() != null && getcDescrizione() != null && getcData() != null
		                && getFisseOVar() != null && getdEuro() != 0.0 && getUtenti() != null;
	}

	public static void setEuro(JTextField euro) {
		DialogEntrateMov.tfEuro = euro;
	}

	public static JTextField getEuro() {
		return tfEuro;
	}

	public static void setData(JTextField data) {
		DialogEntrateMov.tfData = data;
	}

	public static JTextField getData() {
		return tfData;
	}

	public static void setDescrizione(JTextField descrizione) {
		DialogEntrateMov.taDescrizione = descrizione;
	}

	public static JTextField getDescrizione() {
		return taDescrizione;
	}

	public static void setTipoEntrata(JComboBox tipoEntrata) {
		DialogEntrateMov.cbTipoEntrata = tipoEntrata;
	}

	public static JComboBox getTipoEntrata() {
		return cbTipoEntrata;
	}

	public static void setNome(JTextField nome) {
		DialogEntrateMov.tfNome = nome;
	}

	public static JTextField getNome() {
		return tfNome;
	}

	public static void setIdEntrate(JTextField idEntrate) {
		DialogEntrateMov.idEntrate = idEntrate;
	}

	public static JTextField getIdEntrate() {
		return idEntrate;
	}

	@Override
	public void update(Observable o, Object arg) {
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
			String messaggio = "La data va inserita con il seguente formato: aaaa/mm/gg";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			Double euro1 = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro1));
		} else {
			String messaggio = "Valore in Euro inserito non correttamente";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		setUtenti(Controllore.getSingleton().getUtenteLogin());
	}

	protected static JTextField getTfDataIns() {
		return tfDataIns;
	}

	protected static void setTfDataIns(JTextField tfDataIns) {
		DialogEntrateMov.tfDataIns = tfDataIns;
	}

}
