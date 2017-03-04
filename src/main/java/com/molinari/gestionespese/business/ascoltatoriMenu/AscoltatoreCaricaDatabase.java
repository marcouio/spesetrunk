package com.molinari.gestionespese.business.ascoltatoriMenu;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;

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
