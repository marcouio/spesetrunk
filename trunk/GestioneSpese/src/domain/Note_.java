package domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-07-11T09:25:58.060+0200")
@StaticMetamodel(Note.class)
public class Note_ {
	public static volatile SingularAttribute<Note, String> data;
	public static volatile SingularAttribute<Note, String> dataIns;
	public static volatile SingularAttribute<Note, String> descrizione;
	public static volatile SingularAttribute<Note, Integer> idNote;
	public static volatile SingularAttribute<Note, Integer> idUtente;
	public static volatile SingularAttribute<Note, String> nome;
	public static volatile SingularAttribute<Note, Utenti> utenti;
}
