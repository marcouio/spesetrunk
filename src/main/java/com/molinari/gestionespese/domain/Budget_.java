package com.molinari.gestionespese.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-07-11T09:25:55.544+0200")
@StaticMetamodel(Budget.class)
public class Budget_ {
	public static volatile SingularAttribute<Budget, Integer> idBudget;
	public static volatile SingularAttribute<Budget, Integer> idCategorie;
	public static volatile SingularAttribute<Budget, Double> percSulTot;
	public static volatile SingularAttribute<Budget, CatSpese> catSpese;
}
