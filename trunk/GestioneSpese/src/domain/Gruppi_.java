package domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-02-23T23:28:32.236+0100")
@StaticMetamodel(Gruppi.class)
public class Gruppi_ {
	public static volatile SingularAttribute<Gruppi, String> descrizione;
	public static volatile SingularAttribute<Gruppi, Integer> idGruppo;
	public static volatile SingularAttribute<Gruppi, String> nome;
	public static volatile SetAttribute<Gruppi, CatSpese> catSpeses;
}
