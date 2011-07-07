package business.ascoltatoriMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.impostazioni.Impostazioni;
import business.Database;

public class AscoltatoreCaricaDatabase implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent e) {
		final JFileChooser fileopen = new JFileChooser();
		final FileFilter filter = new FileNameExtensionFilter("sqlite", "sqlite");
		fileopen.addChoosableFileFilter(filter);

		final int ret = fileopen.showDialog(null, "Open file");

		if (ret == JFileChooser.APPROVE_OPTION) {
			final File file = fileopen.getSelectedFile();
			Impostazioni.setPosDatabase(file.getAbsolutePath());
			Impostazioni.getCaricaDatabase().setText(Impostazioni.getPosDatabase());
			Database.aggiornamentoPerImpostazioni();
		}

	}
}
