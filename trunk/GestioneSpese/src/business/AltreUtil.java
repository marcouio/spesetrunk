package business;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class AltreUtil {

	static Logger      log;
	static FileHandler fileLog;

	public static boolean checkDouble(String Doble) {
		boolean ok = true;
		try {
			AltreUtil.arrotondaDecimaliDouble(Double.parseDouble(Doble));
		} catch (Exception e) {
			ok = false;
		}
		return ok;
	}

	public static boolean checkData(String data) {
		boolean ok = true;
		if (data != null) {
			try {
				int anno = Integer.parseInt(data.substring(0, 4));
				int mese = Integer.parseInt(data.substring(5, 7));
				int giorno = Integer.parseInt(data.substring(8, 10));
				new GregorianCalendar(anno, mese, giorno);

			} catch (NumberFormatException e2) {
				ok = false;
				JOptionPane.showMessageDialog(null, "Inserire la data con valori numerici e con il formato suggerito: AAAA/MM/GG",
				                            "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				log.severe("La data non e' inserita in maniera corretta: " + e2.getMessage());
			} catch (IllegalArgumentException e1) {
				ok = false;
				JOptionPane.showMessageDialog(null, "Non hai inserito una data!", "Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("immgUtil/index.jpeg"));
				log.severe("La data non e' inserita in maniera corretta: " + e1.getMessage());
			} catch (StringIndexOutOfBoundsException e3) {
				ok = false;
				JOptionPane.showMessageDialog(null, "Numero di caratteri errato per una data: " + e3.getMessage(), "Non ci siamo!",
				                JOptionPane.ERROR_MESSAGE, new ImageIcon("imgUtil/index.jpeg"));
				log.severe("La data non e' inserita in maniera corretta: "
				                + e3.getMessage());
			}
		} else {
			ok = false;
		}
		return ok;
	}

	/**
	 * Prende l'intero del double e lo sottrae al double stesso per ottenere i
	 * decimali. Quindi trasformo i decimali in long moltiplicando per
	 * 100(verificando che la moltiplicazione non dia 0). In questo modo posso
	 * utilizzare il metodo Math.round che permette l'arrotondamento. Divido
	 * nuovamente per 100 e riottengo i decimali arrotondati a due cifre. A
	 * questo punto posso aggiungerli nuovamente agli interi
	 * 
	 * @param d
	 * @return un double arrotondato a due cifre
	 */
	public static double arrotondaDecimaliDouble(double d) {
		String arrotondato = null;
		double decimaleArrotondato = 0;
		String stringaDouble = Double.toString(d);
		String interi = stringaDouble.substring(0, stringaDouble.indexOf("."));
		double parteIntera = Double.parseDouble(interi);
		double parteDecimali = d - parteIntera;
		if (parteDecimali * 100 != 0) {
			double decimaliDaArrotondare = parteDecimali * 100;
			arrotondato = Long.toString(Math.round(decimaliDaArrotondare));
			decimaleArrotondato = (Double.parseDouble(arrotondato)) / 100;
		}
		return parteIntera + (decimaleArrotondato);
	}

	/**
	 * Restituisce il logger dell'applicazione. Viene creato se chiamato per la
	 * prima volta.
	 * 
	 * @return
	 */
	public static Logger getLog() {
		if (log == null)
			setLog();
		else {
			log = Logger.getLogger("com.entrateUscite2.0");
		}
		return log;
	}

	public static void setLog(Logger log) {
		AltreUtil.log = log;
	}

	public AltreUtil() {

	}

	public static String[] deleteFileDaDirectory2(String Dir) {
		File dir = new File(Dir);
		String[] files = dir.list();

		for (int i = 0; i < files.length; i++) {
			File f = new File(dir, files[i]);
			f.delete();
		}
		return files;
	}

	public static String[] deleteFileDaDirectory(String Dir, String treCharIniziali) {
		File dir = new File(Dir);
		String[] files = dir.list();

		for (int i = 0; i < files.length; i++) {
			File f = new File(dir, files[i]);
			if (f.isDirectory() == false
			                && f.getName().substring(0, 3).equals(treCharIniziali)) {
				f.delete();
			}

		}
		return files;
	}

	/**
	 * Crea un Logger
	 * 
	 * @return Logger
	 */
	public static Logger setLog() {

		log = Logger.getLogger("com.entrateUscite2.0");
		try {
			deleteFileDaDirectory("./", "MyL");
			fileLog = new FileHandler("MyLog.txt", 50000, 1, true);
			// fileLog = new FileHandler("Log/MyLog.txt",50000,1,true);
			fileLog.setFormatter(new SimpleFormatter());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.addHandler(fileLog);
		log.setLevel(Level.ALL);
		log.info("Start programma!");
		return log;
	}

	/**
	 * Genera per una tabella specifica un array con tutti i nomi delle colonne.
	 * Sfrutta il metodo nomiColonne(tabella) che crea un vettore con i nomi
	 * delle colonne
	 * 
	 * @param tabella
	 * @return Object[]
	 */
	public static Object[] generaNomiColonne(String tabella) {
		// nomi delle colonne
		Vector<String> nomi = Database.getSingleton().nomiColonne(tabella);
		final String[] nomiColonne = new String[nomi.size()];
		for (int i = 0; i < nomi.size(); i++)
			nomiColonne[i] = nomi.get(i);
		return nomiColonne;
	}

}
