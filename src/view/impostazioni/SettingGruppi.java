package view.impostazioni;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.Database;
import business.cache.CacheGruppi;
import domain.Gruppi;
import domain.wrapper.Model;

public class SettingGruppi extends JDialog {

	private Gruppi         gruppi = null;
	private final Database db     = Database.getSingleton();
	private JComboBox      comboGruppi;

	public SettingGruppi() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Gruppi");
		getContentPane().setLayout(null);
		this.setPreferredSize(new Dimension(260, 405));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		LabelTesto lbltstGruppo = new LabelTesto();
		lbltstGruppo.setText("Gruppo");
		lbltstGruppo.setBounds(25, 24, 100, 25);
		getContentPane().add(lbltstGruppo);

		final TextFieldF nome = new TextFieldF();
		nome.setBounds(25, 49, 206, 26);
		getContentPane().add(nome);

		LabelTesto labelTesto_1 = new LabelTesto();
		labelTesto_1.setText("Descrizione");
		labelTesto_1.setBounds(25, 77, 90, 25);
		getContentPane().add(labelTesto_1);

		final TextAreaF descrizione = new TextAreaF("Inserisci la descrizione della spesa", 50, 25);
		descrizione.setWrapStyleWord(true);
		descrizione.setLineWrap(true);
		descrizione.setAutoscrolls(true);
		descrizione.setBounds(25, 103, 206, 88);
		getContentPane().add(descrizione);

		ButtonF btnfInserisciGruppo = new ButtonF();
		btnfInserisciGruppo.setText("Inserisci Gruppo");
		btnfInserisciGruppo.setBounds(26, 214, 206, 25);
		getContentPane().add(btnfInserisciGruppo);

		Vector<Gruppi> vettoreGruppi = CacheGruppi.getSingleton().getVettoreGruppi();
		comboGruppi = new JComboBox();
		comboGruppi.addItem("");

		for (int i = 0; i < vettoreGruppi.size(); i++) {
			comboGruppi.addItem(vettoreGruppi.get(i));
		}

		comboGruppi.setBounds(25, 279, 206, 25);
		getContentPane().add(comboGruppi);
		comboGruppi.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (comboGruppi.getSelectedIndex() != 0 && comboGruppi.getSelectedItem() != null) {
					gruppi = (Gruppi) comboGruppi.getSelectedItem();
					nome.setText(gruppi.getnome());
					descrizione.setText(gruppi.getdescrizione());
				}
			}
		});

		LabelTesto lbltstListaGruppi = new LabelTesto();
		lbltstListaGruppi.setText("Lista Gruppi");
		lbltstListaGruppi.setBounds(25, 251, 100, 25);
		getContentPane().add(lbltstListaGruppi);

		ButtonF aggiorna = new ButtonF();
		aggiorna.setText("Aggiorna");
		aggiorna.setBounds(25, 320, 100, 25);
		getContentPane().add(aggiorna);

		ButtonF cancella = new ButtonF();
		cancella.setText("Cancella");
		cancella.setBounds(131, 320, 100, 25);
		getContentPane().add(cancella);

		aggiorna.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<String, String> campi = new HashMap<String, String>();
				HashMap<String, String> clausole = new HashMap<String, String>();
				if (comboGruppi.getSelectedIndex() != 0 && comboGruppi.getSelectedItem() != null) {
					if (gruppi != null)
						clausole.put(Gruppi.ID, Integer.toString(gruppi.getidGruppo()));
					campi.put(Gruppi.NOME, nome.getText());
					if (descrizione != null)
						campi.put(Gruppi.DESCRIZIONE, descrizione.getText());

					try {
						if (db.eseguiIstruzioneSql("UPDATE", Gruppi.NOME_TABELLA, campi, clausole))
							JOptionPane.showMessageDialog(null, "Operazione eseguita correttamente", "Perfetto!", JOptionPane.INFORMATION_MESSAGE);
						// TODO verificare se ricarica deve essere true
						Model.getSingleton().getGruppiCombo(true);
						// Database.aggiornaCategorie(spese);
						// Database.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
					} catch (Exception e22) {
						JOptionPane.showMessageDialog(null, "Inserisci i dati correttamente: " + e22.getMessage(), "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
					}
				} else {
					JOptionPane.showMessageDialog(null, "Impossibile aggiornare una categoria inesistente!", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				}

			}
		});

		cancella.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboGruppi.getSelectedIndex() != 0 && comboGruppi.getSelectedItem() != null && gruppi != null) {
					Model.getSingleton().getModelUscita().delete(gruppi.getidGruppo());
				} else {
					JOptionPane.showMessageDialog(null, "Impossibile cancellare una categoria inesistente!", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				}
				// log.fine("Cancellata categoria inserita spesa" + gruppi);
				comboGruppi.removeItem(gruppi);
				// Database.aggiornamentoComboBox(new Database().Spese());
			}
		});

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SettingGruppi dialog = new SettingGruppi();
		dialog.pack();
		dialog.setBounds(10, 10, 500, 500);
		dialog.setVisible(true);
	}

	public JComboBox getComboGruppi() {
		return comboGruppi;
	}

	public void setComboGruppi(JComboBox comboGruppi) {
		this.comboGruppi = comboGruppi;
	}
}
