package com.molinari.gestionespese.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-09T21:42:04.946+0200")
@StaticMetamodel(CatSpese.class)
public class CatSpese_ {
	public static volatile SingularAttribute<CatSpese, String> descrizione;
	public static volatile SingularAttribute<CatSpese, Integer> idCategoria;
	public static volatile SingularAttribute<CatSpese, String> importanza;
	public static volatile SingularAttribute<CatSpese, String> nome;
	public static volatile SingularAttribute<CatSpese, Budget> budget;
	public static volatile SingularAttribute<CatSpese, IGruppi> gruppi;
	public static volatile SetAttribute<CatSpese, SingleSpesa> singleSpesas;
}
