package com.molinari.gestionespese.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-09T21:42:04.789+0200")
@StaticMetamodel(Budget.class)
public class Budget_ {
	public static volatile SingularAttribute<Budget, Integer> idBudget;
	public static volatile SingularAttribute<Budget, Integer> idCategorie;
	public static volatile SingularAttribute<Budget, Double> percSulTot;
	public static volatile SingularAttribute<Budget, ICatSpese> catSpese;
}
