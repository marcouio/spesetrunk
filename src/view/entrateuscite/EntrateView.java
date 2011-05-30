package view.entrateuscite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.AltreUtil;
import business.CorreggiTesto;
import business.Controllore;
import business.DBUtil;
import business.Database;
import business.cache.CacheEntrate;
import business.comandi.CommandDeleteEntrata;
import domain.Entrate;
import domain.wrapper.WrapEntrate;

public class EntrateView extends AbstractEntrateView {

	private static final long        serialVersionUID = 1L;

	static private ArrayList<String> lista;

	private final TextFieldF         tfNome;
	private final TextAreaF          taDescrizione;
	private final JComboBox          cbTipo;
	private final TextFieldF         tfData;
	private final TextFieldF         tfEuro;

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final EntrateView dialog = new EntrateView(new WrapEntrate());
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(0, 0, 347, 318);
				dialog.setVisible(true);
			}
		});
	}

	/**
	 * Create the panel.
	 */
	public EntrateView(final WrapEntrate entrate) {
		super(entrate);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Inserimento Entrate");
		modelEntrate.addObserver(this);
		getContentPane().setLayout(null);

		initLabel();

		taDescrizione = new TextAreaF("Inserisci qui la descrizione dell'entrata");
		taDescrizione.setBounds(13, 89, 318, 75);
		getContentPane().add(taDescrizione);

		// specifica se �true� di andare a capo automaticamente a fine riga
		taDescrizione.setLineWrap(true);
		// va a capo con la parola se �true� o col singolo carattere se �false�
		taDescrizione.setWrapStyleWord(true);
		taDescrizione.setAutoscrolls(true);

		tfNome = new TextFieldF();
		tfNome.setBounds(12, 38, 150, 27);
		getContentPane().add(tfNome);
		tfNome.setColumns(10);

		// array per Categoria
		lista = new ArrayList<String>();
		lista.add("");
		lista.add("Variabili");
		lista.add("Fisse");

		cbTipo = new JComboBox(lista.toArray());
		cbTipo.setBounds(181, 38, 150, 27);
		getContentPane().add(cbTipo);

		final GregorianCalendar gc = new GregorianCalendar();
		tfData = new TextFieldF(DBUtil.dataToString(gc.getTime(), "yyyy/MM/dd"));
		tfData.setColumns(10);
		tfData.setBounds(13, 191, 150, 27);
		getContentPane().add(tfData);

		tfEuro = new TextFieldF("0.0");
		tfEuro.setColumns(10);
		tfEuro.setBounds(182, 191, 150, 27);
		getContentPane().add(tfEuro);

		final ButtonF inserisci = new ButtonF();
		inserisci.setText("Inserisci");
		inserisci.setBounds(13, 238, 149, 27);
		getContentPane().add(inserisci);

		final ButtonF eliminaUltima = new ButtonF();
		eliminaUltima.setText("Elimina Ultima");
		eliminaUltima.setBounds(184, 238, 144, 27);
		getContentPane().add(eliminaUltima);

		eliminaUltima.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					setEntrate();
					if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteEntrata(modelEntrate), Entrate.NOME_TABELLA)) {
						// log.fine("Cancellata ultima spesa inserita");
						Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
						JOptionPane.showMessageDialog(null, "Ok, ultima entrata eliminata correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (final Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
					// TODO log.severe(e2.getMessage());
					DBUtil.closeConnection();
				}
			}
		});

		inserisci.addActionListener(new AscoltaInserisciEntrate(this));

	}

	public boolean nonEsistonoCampiNonValorizzati() {
		return getcNome() != null && getcDescrizione() != null && getcData() != null && getDataIns() != null
		                && getFisseOVar() != null && getdEuro() != 0.0 && getUtenti() != null;
	}

	private void initLabel() {
		final LabelTesto lblNomeSpesa = new LabelTesto("Nome Spesa");
		lblNomeSpesa.setText("Nome Entrata");
		lblNomeSpesa.setBounds(13, 12, 97, 27);
		getContentPane().add(lblNomeSpesa);

		final LabelTesto lblEuro = new LabelTesto("Euro");
		lblEuro.setBounds(184, 165, 77, 27);
		getContentPane().add(lblEuro);

		final LabelTesto lblCategorie = new LabelTesto("Categorie");
		lblCategorie.setText("Tipo");
		lblCategorie.setBounds(181, 12, 77, 27);
		getContentPane().add(lblCategorie);

		final LabelTesto lblData = new LabelTesto("Data");
		lblData.setBounds(13, 165, 77, 27);
		getContentPane().add(lblData);

		final LabelTesto lblDescrizione = new LabelTesto("Descrizione Spesa");
		lblDescrizione.setText("Descrizione Entrata");
		lblDescrizione.setBounds(14, 64, 123, 25);
		getContentPane().add(lblDescrizione);
	}

	/**
	 * @return the lista
	 */
	public static ArrayList<String> getLista() {
		return EntrateView.lista;
	}

	public void setEntrate() {
		final int idEntrate = (CacheEntrate.getSingleton().getMaxId()) + 1;
		getModelEntrate().setidEntrate(idEntrate);

		final CorreggiTesto checkTesto = new CorreggiTesto(tfNome.getText());

		final String nome = checkTesto.getTesto();
		setcNome(nome);

		checkTesto.setTesto(taDescrizione.getText());
		final String descri = checkTesto.getTesto();
		setcDescrizione(descri);

		setFisseOVar((String) cbTipo.getSelectedItem());
		if (AltreUtil.checkData(tfData.getText())) {
			setcData(tfData.getText());
		} else {
			final String messaggio = "La data va inserita con il seguente formato: aaaa/mm/gg";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			final Double euro = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		} else {
			final String messaggio = "Valore in Euro inserito non correttamente";
			JOptionPane.showMessageDialog(null, messaggio, "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
		}
		setUtenti(Controllore.getSingleton().getUtenteLogin());
		setDataIns(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
	}

	/**
	 * @param lista
	 *            the lista to set
	 */
	public static void setLista(final ArrayList<String> lista) {
		EntrateView.lista = lista;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		tfNome.setText(getcNome());
		taDescrizione.setText(getcDescrizione());
		cbTipo.setSelectedItem(getFisseOVar());
		tfData.setText(getcData());
		tfEuro.setText(getdEuro().toString());

	}

}
