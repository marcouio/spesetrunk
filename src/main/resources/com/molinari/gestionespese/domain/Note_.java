package com.molinari.gestionespese.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-09T21:42:04.977+0200")
@StaticMetamodel(Note.class)
public class Note_ {
	public static volatile SingularAttribute<Note, String> data;
	public static volatile SingularAttribute<Note, String> dataIns;
	public static volatile SingularAttribute<Note, String> descrizione;
	public static volatile SingularAttribute<Note, Integer> idNote;
	public static volatile SingularAttribute<Note, Integer> idUtente;
	public static volatile SingularAttribute<Note, String> nome;
	public static volatile SingularAttribute<Note, IUtenti> utenti;
}
