package business.ascoltatori;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AscoltatoreCaricaDatabase implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileopen = new JFileChooser();
	    FileFilter filter = new FileNameExtensionFilter("db files", "*.sqlite");
	    fileopen.addChoosableFileFilter(filter);

	    int ret = fileopen.showDialog(null, "Open file");

	    if (ret == JFileChooser.APPROVE_OPTION) {
	      File file = fileopen.getSelectedFile();
//	      caricaDatabase.setText(file.getName());
	    }

	}

}
