package business;

public class CorreggiTesto {

	private String testo;

	public CorreggiTesto() {
	}

	public CorreggiTesto(final String testo) {
		this.testo = testo;
		checkApici();
	}

	/**
	 * Non so come gestire gli apici che mi creano problemi con istuzioni SQL, li elimino
	 * 
	 * @return testo corretto
	 */
	public String checkApici() {
		if (testo.contains("'")) {
			final String nuovoTesto = testo.replace("'", "");
			testo = nuovoTesto;
		}
		return testo;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(final String testo) {
		this.testo = testo;
	}

}
