package business.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import domain.Budget;
import domain.wrapper.WrapBudget1;

public class CacheBudget extends AbstractCacheBase{
	
	private boolean caricata=false;
	private static CacheBudget singleton;
	private static Map<String, Budget> cache;
	
	private CacheBudget(){
		cache = new HashMap<String, Budget>();
	}
	public static CacheBudget getSingleton(){
		if (singleton == null) {
			synchronized (CacheBudget.class) {
				if (singleton == null) {
					singleton = new CacheBudget();
				}
			} // if
		} // if
		return singleton;
	}

	WrapBudget1 budgetDAO = new WrapBudget1();
	
	
	
	public Budget getBudget(String id){
		Budget budget = (Budget) cache.get(id); 
		if(budget == null){
			budget = caricaBudget(id);
			if(budget!=null){
				cache.put(id, budget);
			}
		}
	return cache.get(id);
	}
	
	private Budget caricaBudget(String id){
		return (Budget) new WrapBudget1().selectById(Integer.parseInt(id));
	}
	
	public Map<String, Budget> chargeAllBudget(){
		Vector<Object>budgets =budgetDAO.selectAll();
		if(budgets!=null && budgets.size()>0){
			for(int i=0; i<budgets.size();i++){
				Budget budget = (Budget) budgets.get(i);
				int id = budget.getidBudget();
				if(cache.get(id) == null){
					cache.put(Integer.toString(id), budget);
				}
			}
		}
	return cache;
	}
	
	public Map<String, Budget> getAllBudget(){
		if(caricata)
			return cache;
		else
			return chargeAllBudget();
	}
	
	/**
	 * @return the cache
	 */
	public static Map<String, Budget> getCache() {
		return cache;
	}
	/**
	 * @param cache the cache to set
	 */
	public static void setCache(Map<String, Budget> cache) {
		CacheBudget.cache = cache;
	}

}
