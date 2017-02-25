package com.molinari.gestionespese.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-07-11T09:25:57.341+0200")
@StaticMetamodel(Gruppi.class)
public class Gruppi_ {
	public static volatile SingularAttribute<Gruppi, String> descrizione;
	public static volatile SingularAttribute<Gruppi, Integer> idGruppo;
	public static volatile SingularAttribute<Gruppi, String> nome;
	public static volatile SetAttribute<Gruppi, CatSpese> catSpeses;
}
