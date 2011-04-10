package domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-02-23T23:28:32.238+0100")
@StaticMetamodel(SingleSpesa.class)
public class SingleSpesa_ {
	public static volatile SingularAttribute<SingleSpesa, String> Data;
	public static volatile SingularAttribute<SingleSpesa, String> descrizione;
	public static volatile SingularAttribute<SingleSpesa, Integer> idCategorie;
	public static volatile SingularAttribute<SingleSpesa, Integer> idSpesa;
	public static volatile SingularAttribute<SingleSpesa, Integer> idUtente;
	public static volatile SingularAttribute<SingleSpesa, Double> inEuro;
	public static volatile SingularAttribute<SingleSpesa, String> nome;
	public static volatile SingularAttribute<SingleSpesa, String> dataIns;
	public static volatile SingularAttribute<SingleSpesa, CatSpese> catSpese;
	public static volatile SingularAttribute<SingleSpesa, Utenti> utenti;
}
