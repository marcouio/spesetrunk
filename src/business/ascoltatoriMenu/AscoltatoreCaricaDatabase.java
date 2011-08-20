package business.ascoltatoriMenu;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.impostazioni.Impostazioni;
import business.aggiornatori.AggiornatoreManager;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;

public class AscoltatoreCaricaDatabase extends AscoltatoreAggiornatoreTutto {

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {

		super.actionPerformedOverride(e);

		final JFileChooser fileopen = new JFileChooser();
		final FileFilter filter = new FileNameExtensionFilter("sqlite", "sqlite");
		fileopen.addChoosableFileFilter(filter);

		final int ret = fileopen.showDialog(null, "Open file");

		if (ret == JFileChooser.APPROVE_OPTION) {
			final File file = fileopen.getSelectedFile();
			Impostazioni.setPosDatabase(file.getAbsolutePath());
			Impostazioni.getCaricaDatabase().setText(Impostazioni.getPosDatabase());
			AggiornatoreManager.aggiornamentoPerImpostazioni();
		}
	}
}
