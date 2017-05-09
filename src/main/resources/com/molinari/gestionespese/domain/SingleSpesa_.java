package com.molinari.gestionespese.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-09T21:42:04.977+0200")
@StaticMetamodel(SingleSpesa.class)
public class SingleSpesa_ {
	public static volatile SingularAttribute<SingleSpesa, String> data;
	public static volatile SingularAttribute<SingleSpesa, String> descrizione;
	public static volatile SingularAttribute<SingleSpesa, Integer> idSpesa;
	public static volatile SingularAttribute<SingleSpesa, Double> inEuro;
	public static volatile SingularAttribute<SingleSpesa, String> nome;
	public static volatile SingularAttribute<SingleSpesa, String> dataIns;
	public static volatile SingularAttribute<SingleSpesa, ICatSpese> catSpese;
	public static volatile SingularAttribute<SingleSpesa, IUtenti> utenti;
}
