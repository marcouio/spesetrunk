package com.molinari.gestionespese.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-09T21:42:04.961+0200")
@StaticMetamodel(Gruppi.class)
public class Gruppi_ {
	public static volatile SingularAttribute<Gruppi, String> descrizione;
	public static volatile SingularAttribute<Gruppi, Integer> idGruppo;
	public static volatile SingularAttribute<Gruppi, String> nome;
	public static volatile SetAttribute<Gruppi, ICatSpese> catSpeses;
}
