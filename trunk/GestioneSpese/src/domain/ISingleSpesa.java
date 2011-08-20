package domain;

public interface ISingleSpesa {

	public String getData();

	public void setData(String Data);

	public String getdescrizione();

	public void setdescrizione(String descrizione);

	public int getidSpesa();

	public void setidSpesa(int idSpesa);

	public double getinEuro();

	public void setinEuro(double d);

	public String getnome();

	public void setnome(String nome);

	public CatSpese getCatSpese();

	public void setCatSpese(CatSpese catSpese);
	
	public Utenti getUtenti();

	public void setUtenti(Utenti utenti);

	public void setDataIns(String dataIns);

	public String getDataIns();
	
	@Override
	public String toString();

}
