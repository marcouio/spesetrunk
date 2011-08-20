package business;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.Vector;

import view.Alert;

public class AltreUtil {

	// static Logger log;
	// static FileHandler fileLog;

	public static boolean checkInteger(final String integer) {
		boolean ok = true;
		try {
			new Integer(integer);
		} catch (final Exception e) {
			ok = false;
		}
		return ok;
	}

	public static boolean checkDouble(final String Doble) {
		boolean ok = true;
		try {
			AltreUtil.arrotondaDecimaliDouble(Double.parseDouble(Doble));
		} catch (final Exception e) {
			ok = false;
		}
		return ok;
	}

	public static boolean checkData(final String data) {
		boolean ok = true;
		if (data != null) {
			try {
				final int anno = Integer.parseInt(data.substring(0, 4));
				final int mese = Integer.parseInt(data.substring(5, 7));
				final int giorno = Integer.parseInt(data.substring(8, 10));
				new GregorianCalendar(anno, mese, giorno);

			} catch (final NumberFormatException e2) {
				ok = false;
				Alert.operazioniSegnalazioneErroreGrave("Inserire la data con valori numerici e con il formato suggerito: AAAA/MM/GG");
			} catch (final IllegalArgumentException e1) {
				ok = false;
				Alert.operazioniSegnalazioneErroreGrave(Alert.getMessaggioErrore("Non hai inserito una data, " + e1.getMessage()));
			} catch (final StringIndexOutOfBoundsException e3) {
				ok = false;
				Alert.operazioniSegnalazioneErroreGrave(Alert.getMessaggioErrore("Numero di caratteri errato per una data, " + e3.getMessage()));
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
	public static double arrotondaDecimaliDouble(final double d) {
		String arrotondato = null;
		double decimaleArrotondato = 0;
		final String stringaDouble = Double.toString(d);
		final String interi = stringaDouble.substring(0, stringaDouble.indexOf("."));
		final double parteIntera = Double.parseDouble(interi);
		final double parteDecimali = d - parteIntera;
		if (parteDecimali * 100 != 0) {
			final double decimaliDaArrotondare = parteDecimali * 100;
			arrotondato = Long.toString(Math.round(decimaliDaArrotondare));
			decimaleArrotondato = (Double.parseDouble(arrotondato)) / 100;
		}
		return parteIntera + (decimaleArrotondato);
	}

	public AltreUtil() {

	}

	/**
	 * Cancella tutti i file all'interno della directory passato come parametro
	 * 
	 * @param Dir
	 * @return
	 */
	public static String[] deleteFileDaDirectory2(final String Dir) {
		final File dir = new File(Dir);
		final String[] files = dir.list();

		for (int i = 0; i < files.length; i++) {
			final File f = new File(dir, files[i]);
			f.delete();
		}
		return files;
	}

	public static String[] deleteFileDaDirectory(final String Dir, final String treCharIniziali) {
		final File dir = new File(Dir);
		final String[] files = dir.list();

		for (int i = 0; i < files.length; i++) {
			final File f = new File(dir, files[i]);
			if (f.isDirectory() == false && f.getName().substring(0, 3).equals(treCharIniziali)) {
				f.delete();
			}

		}
		return files;
	}

	/**
	 * Genera per una tabella specifica un array con tutti i nomi delle colonne.
	 * Sfrutta il metodo nomiColonne(tabella) che crea un vettore con i nomi
	 * delle colonne
	 * 
	 * @param tabella
	 * @return Object[]
	 */
	public static Object[] generaNomiColonne(final String tabella) {
		// nomi delle colonne
		final Vector<String> nomi = Database.getSingleton().nomiColonne(tabella);
		final String[] nomiColonne = new String[nomi.size()];
		for (int i = 0; i < nomi.size(); i++) {
			nomiColonne[i] = nomi.get(i);
		}
		return nomiColonne;
	}

}
