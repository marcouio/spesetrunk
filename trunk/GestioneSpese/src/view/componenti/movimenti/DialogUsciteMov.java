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

import view.entrateuscite.AbstractUsciteView;
import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Controllore;
import business.cache.CacheUscite;
import business.comandi.CommandDeleteSpesa;
import business.comandi.CommandUpdateSpesa;
import domain.CatSpese;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.wrapper.Model;
import domain.wrapper.WrapSingleSpesa;

public class DialogUsciteMov extends AbstractUsciteView {

	private static final long serialVersionUID = 1L;
	private JLabel            labelEuro        = new LabelTesto("Euro");
	private JLabel            labelData        = new LabelTesto("Data");
	private JLabel            labelCategoria   = new LabelTesto("Categoria");
	private JLabel            labelDescrizione = new LabelTesto("Descrizione");
	private JLabel            labelNome        = new LabelTesto("Nome");
	private JLabel            labelDataIns     = new LabelTesto("Data Inserimento");
	private JLabel            labelIdSpesa     = new LabelTesto("Chiave Uscita");

	private JTextField        tfEuro           = new TextFieldF();
	private JTextField        tfData           = new TextFieldF();
	private JComboBox         cbCategorie;
	// private JTextField categoria = new TextFieldF();
	private JTextField        taDescrizione    = new TextFieldF();
	private JTextField        tfNome           = new TextFieldF();
	private JTextField        tfDataIns        = new TextFieldF();
	private JTextField        idSpesa          = new TextFieldF();
	private final JButton     update           = new ButtonF("Aggiorna");
	private final JButton     delete           = new ButtonF("Cancella");

	/**
	 * Auto-generated main method to display this JDialog
	 */

	public void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final DialogUsciteMov inst = new DialogUsciteMov(new WrapSingleSpesa());
				inst.setVisible(true);
			}
		});
	}

	public DialogUsciteMov(final WrapSingleSpesa uscita) {
		super(uscita);
		initGUI();
	}

	private void initGUI() {
		try {
			// questo permette di mantenere il focus sulla dialog
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			cbCategorie = new JComboBox(Model.getSingleton().getCategorieCombo(
			                false));
			idSpesa.setEditable(false);
			this.setLayout(new GridLayout(0, 2));
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

			this.add(labelIdSpesa);
			this.add(idSpesa);
			this.add(labelNome);
			this.add(tfNome);
			this.add(labelDescrizione);
			this.add(taDescrizione);
			this.add(labelData);
			this.add(tfData);
			this.add(labelCategoria);
			this.add(cbCategorie);
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

	public boolean nonEsistonoCampiNonValorizzati() {
		return getcNome() != null && getcDescrizione() != null
		                && getcData() != null && getDataIns() != null
		                && getCategoria() != null && getdEuro() != 0.0
		                && getUtenti() != null;
	}

	private void setUscite() {
		getModelUscita().setidSpesa(Integer.parseInt(idSpesa.getText()));
		setcNome(tfNome.getText());
		setcDescrizione(taDescrizione.getText());
		setCategoria((CatSpese) cbCategorie.getSelectedItem());
		if (AltreUtil.checkData(tfData.getText())) {
			setcData(tfData.getText());
		} else {
			final String messaggio = "La data va inserita con il seguente formato: aaaa/mm/gg";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!",
			                JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			final Double euro = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		} else {
			final String messaggio = "Valore in Euro inserito non correttamente";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!",
			                JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		setUtenti(Controllore.getSingleton().getUtenteLogin());
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

	public void setCategoria(final JComboBox categoria) {
		this.cbCategorie = categoria;
	}

	public JComboBox getComboCategoria() {
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
		// TODO Auto-generated method stub

	}

	public class AscoltatoreDialogUscite implements ActionListener {

		DialogUsciteMov dialog;

		public AscoltatoreDialogUscite(final DialogUsciteMov dialog) {
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			if (e.getActionCommand().equals("Aggiorna")) {
				setUscite();
				final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(SingleSpesa.NOME_TABELLA);
				final JTextField campo = Controllore.getSingleton().getView().getTabMovimenti().getTabMovUscite().getCampo();
				final SingleSpesa oldSpesa = CacheUscite.getSingleton().getSingleSpesa(idSpesa.getText());

				if (dialog.nonEsistonoCampiNonValorizzati()) {
					if (Controllore.getSingleton().getCommandManager().invocaComando(
					                                new CommandUpdateSpesa(oldSpesa, (ISingleSpesa) modelUscita.getentitaPadre()), SingleSpesa.NOME_TABELLA)) {
						JOptionPane.showMessageDialog(null, "Ok, operazione eseguita correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
					}
					Model.aggiornaMovimentiUsciteDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
					// chiude la dialog e rilascia le risorse
					dispose();
				} else
					JOptionPane.showMessageDialog(null,
					                "Tutti i dati devono essere valorizzati", "Non ci siamo!", JOptionPane.ERROR_MESSAGE,
					                new ImageIcon("imgUtil/index.jpeg"));

			} else if (e.getActionCommand().equals("Cancella")) {
				setUscite();
				if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteSpesa(modelUscita), SingleSpesa.NOME_TABELLA)) {
					JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente", "Perfetto!",
					                JOptionPane.INFORMATION_MESSAGE);
				} else
					JOptionPane.showMessageDialog(null, "Inserisci i dati correttamente",
					                "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				// chiude la dialog e rilascia le risorse
				dispose();
			}
		}
	}

}
