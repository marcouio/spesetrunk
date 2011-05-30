package business;

public class CorreggiTesto {

	private String testo;

	public CorreggiTesto(String testo) {
		this.testo = testo;
		checkApici();
	}

	private void checkApici() {
		if (testo.contains("'")) {
			testo.replace("'", "\'");
		}
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

}
