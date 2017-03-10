package com.molinari.gestionespese.business.cache;

import java.util.HashMap;
import java.util.Map;

import com.molinari.gestionespese.domain.IBudget;
import com.molinari.gestionespese.domain.wrapper.WrapBudget;

public class CacheBudget extends AbstractCacheBase<IBudget> {

	private static CacheBudget singleton;
	WrapBudget budgetDAO = new WrapBudget();

	private CacheBudget() {
		setCache(new HashMap<String, IBudget>());
	}

	public static CacheBudget getSingleton() {
		if (singleton == null) {
			singleton = new CacheBudget();
		}
		return singleton;
	}

	/**
	 * Viene cercato il budget nella cache, se non lo trova lo carica dal
	 * database. Se non presente viene inserito nella cache
	 *
	 * @param id
	 * @return budget
	 */
	public IBudget getBudget(String id) {

		return getObjectById(budgetDAO, id);

	}


	/**
	 * Carica tutti i budget dal database e poi li inserisce nella cache
	 *
	 * @return
	 */
	public Map<String, IBudget> chargeAllBudget() {
		return chargeAllObject(budgetDAO);
	}

	public Map<String, IBudget> getAllBudget() {
		return getAll(budgetDAO);
	}

}
