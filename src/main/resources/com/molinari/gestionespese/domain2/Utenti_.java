package com.molinari.gestionespese.domain2;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-09T21:52:42.962+0200")
@StaticMetamodel(Utenti.class)
public class Utenti_ {
	public static volatile SingularAttribute<Utenti, String> cognome;
	public static volatile SingularAttribute<Utenti, Integer> idUtente;
	public static volatile SingularAttribute<Utenti, String> nome;
	public static volatile SingularAttribute<Utenti, String> password;
	public static volatile SingularAttribute<Utenti, String> username;
	public static volatile ListAttribute<Utenti, SingleSpesa> singleSpesas;
	public static volatile ListAttribute<Utenti, Entrate> entrates;
	public static volatile ListAttribute<Utenti, Note> notes;
}
