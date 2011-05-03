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

	private static JLabel     labelEuro        = new LabelTesto("Euro");
	private static JLabel     labelData        = new LabelTesto("Data");
	private static JLabel     labelCategoria   = new LabelTesto("Categoria");
	private static JLabel     labelDescrizione = new LabelTesto("Descrizione");
	private static JLabel     labelNome        = new LabelTesto("Nome");
	private static JLabel     labelDataIns     = new LabelTesto("Data Inserimento");
	private static JLabel     labelIdSpesa     = new LabelTesto("Chiave Uscita");

	private static JTextField tfEuro           = new TextFieldF();
	private static JTextField tfData           = new TextFieldF();
	private static JComboBox  cbCategorie;
	// private static JTextField categoria = new TextFieldF();
	private static JTextField taDescrizione    = new TextFieldF();
	private static JTextField tfNome           = new TextFieldF();
	private static JTextField tfDataIns        = new TextFieldF();
	private static JTextField idSpesa          = new TextFieldF();
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
				DialogUsciteMov inst = new DialogUsciteMov(new WrapSingleSpesa());
				inst.setVisible(true);
			}
		});
	}

	public DialogUsciteMov(WrapSingleSpesa uscita) {
		super(uscita);
		initGUI();
	}

	private void initGUI() {
		try {
			// questo permette di mantenere il focus sulla dialog
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			cbCategorie = new JComboBox(Model.getSingleton().getCategorieCombo(false));
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

			update.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setUscite();
					String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(SingleSpesa.NOME_TABELLA);
					JTextField campo = Controllore.getSingleton().getView().getTabMovimenti().getTabMovUscite().getCampo();
					SingleSpesa oldSpesa = CacheUscite.getSingleton().getSingleSpesa(idSpesa.getText());

					if (nonEsistonoCampiNonValorizzati()) {
						if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandUpdateSpesa(oldSpesa, (ISingleSpesa) modelUscita.getentitaPadre()), SingleSpesa.NOME_TABELLA)) {
							JOptionPane.showMessageDialog(null, "Ok, operazione eseguita correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
						}
						Model.aggiornaMovimentiUsciteDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
						// chiude la dialog e rilascia le risorse
						dispose();
					} else
						JOptionPane.showMessageDialog(null, "Tutti i dati devono essere valorizzati", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				}

				private boolean nonEsistonoCampiNonValorizzati() {
					return getcNome() != null && getcDescrizione() != null && getcData() != null && getDataIns() != null && getCategoria() != null && getdEuro() != 0.0 && getUtenti() != null;
				}
			});

			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setUscite();
					if (idSpesa.getText() != null) {
						SingleSpesa ss = CacheUscite.getSingleton().getSingleSpesa(idSpesa.getText());
						if (ss != null) {
							if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteSpesa(ss), SingleSpesa.NOME_TABELLA)) {
								JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente", "Perfetto!", JOptionPane.INFORMATION_MESSAGE);
							} else
								JOptionPane.showMessageDialog(null, "Inserisci i dati correttamente", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
						}
					}
					// chiude la dialog e rilascia le risorse
					dispose();
				}
			});

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setUscite() {
		setcNome(tfNome.getText());
		setcDescrizione(taDescrizione.getText());
		setCategoria((CatSpese) cbCategorie.getSelectedItem());
		if (AltreUtil.checkData(tfData.getText())) {
			setcData(tfData.getText());
		} else {
			String messaggio = "La data va inserita con il seguente formato: aaaa/mm/gg";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			Double euro = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		} else {
			String messaggio = "Valore in Euro inserito non correttamente";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		setUtenti(Controllore.getSingleton().getUtenteLogin());
		setDataIns(tfDataIns.getText());
	}

	public static void setEuro(JTextField euro) {
		DialogUsciteMov.tfEuro = euro;
	}

	public static JTextField getEuro() {
		return tfEuro;
	}

	public static void setData(JTextField data) {
		DialogUsciteMov.tfData = data;
	}

	public static JTextField getData() {
		return tfData;
	}

	public static void setDescrizione(JTextField descrizione) {
		DialogUsciteMov.taDescrizione = descrizione;
	}

	public static JTextField getDescrizione() {
		return taDescrizione;
	}

	public static void setCategoria(JComboBox categoria) {
		DialogUsciteMov.cbCategorie = categoria;
	}

	public static JComboBox getComboCategoria() {
		return cbCategorie;
	}

	public static void setNome(JTextField nome) {
		DialogUsciteMov.tfNome = nome;
	}

	public static JTextField getNome() {
		return tfNome;
	}

	public static void setIdSpesa(JTextField idSpesa) {
		DialogUsciteMov.idSpesa = idSpesa;
	}

	public static JTextField getIdSpesa() {
		return idSpesa;
	}

	public static JTextField getTfDataIns() {
		return tfDataIns;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
