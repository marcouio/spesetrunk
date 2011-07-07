package view.impostazioni;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import view.FinestraListaComandi;
import view.GeneralFrame;
import business.Controllore;
import business.Database;
import business.InizializzatoreFinestre;
import domain.Lookandfeel;

public class AscoltatoreLook implements ActionListener {

	JComboBox comboLook;
	Vector<Lookandfeel> vettore;

	public AscoltatoreLook(final JComboBox comboLook, final Vector<Lookandfeel> vettore) {
		this.comboLook = comboLook;
		this.vettore = vettore;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		String look = "";
		final Lookandfeel valoreLook = (Lookandfeel) comboLook.getSelectedItem();
		if (valoreLook != null && !valoreLook.getnome().equals("")) {
			look = valoreLook.getvalore();

			for (int i = 0; i < vettore.size(); i++) {
				final Lookandfeel lookAnd = vettore.get(i);
				lookAnd.setusato(0);
				final HashMap<String, String> campi = new HashMap<String, String>();
				final HashMap<String, String> clausole = new HashMap<String, String>();
				campi.put(Lookandfeel.USATO, "0");
				clausole.put(Lookandfeel.ID, Integer.toString(lookAnd.getidLook()));
				Database.getSingleton().eseguiIstruzioneSql("update", Lookandfeel.NOME_TABELLA, campi, clausole);
			}

			valoreLook.setusato(1);
			final HashMap<String, String> campi = new HashMap<String, String>();
			final HashMap<String, String> clausole = new HashMap<String, String>();
			campi.put(Lookandfeel.USATO, "1");
			clausole.put(Lookandfeel.ID, Integer.toString(valoreLook.getidLook()));
			Database.getSingleton().eseguiIstruzioneSql("update", Lookandfeel.NOME_TABELLA, campi, clausole);
		} else {
			look = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		}
		try {
			UIManager.setLookAndFeel(look);
		} catch (final ClassNotFoundException e1) {
			comboLook.setSelectedIndex(0);
			e1.printStackTrace();
		} catch (final InstantiationException e1) {
			comboLook.setSelectedIndex(0);
			e1.printStackTrace();
		} catch (final IllegalAccessException e1) {
			comboLook.setSelectedIndex(0);
			e1.printStackTrace();
		} catch (final UnsupportedLookAndFeelException e1) {
			comboLook.setSelectedIndex(0);
			e1.printStackTrace();
		} catch (final ClassCastException e1) {
			comboLook.setSelectedIndex(0);
			e1.printStackTrace();
		}

		FinestraListaComandi lista = null;
		try {
			lista = ((FinestraListaComandi) Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_HISTORY, null));
		} catch (final Exception e1) {
			e1.printStackTrace();
		}
		final GeneralFrame frame = GeneralFrame.getSingleton();
		SwingUtilities.updateComponentTreeUI(lista);
		SwingUtilities.updateComponentTreeUI(frame);
		frame.setBounds(0, 0, 1000, 650);

	}

}
