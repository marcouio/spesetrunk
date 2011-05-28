package business;

public class CheckTesto {

	private String testo;

	public CheckTesto(String testo) {
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
