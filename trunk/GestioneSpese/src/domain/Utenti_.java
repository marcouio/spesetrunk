package domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-02-23T23:28:32.239+0100")
@StaticMetamodel(Utenti.class)
public class Utenti_ {
	public static volatile SingularAttribute<Utenti, Integer> idUtente;
	public static volatile SingularAttribute<Utenti, String> password;
	public static volatile SingularAttribute<Utenti, String> username;
	public static volatile SingularAttribute<Utenti, String> nome;
	public static volatile SingularAttribute<Utenti, String> cognome;
	public static volatile SetAttribute<Utenti, Entrate> entrates;
	public static volatile SetAttribute<Utenti, SingleSpesa> singleSpesas;
}
