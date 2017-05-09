package com.molinari.gestionespese.domain2;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-09T21:52:42.947+0200")
@StaticMetamodel(Gruppi.class)
public class Gruppi_ {
	public static volatile SingularAttribute<Gruppi, String> descrizione;
	public static volatile SingularAttribute<Gruppi, Integer> idGruppo;
	public static volatile SingularAttribute<Gruppi, String> nome;
	public static volatile ListAttribute<Gruppi, CatSpese> catSpeses;
}
