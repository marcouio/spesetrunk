package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Lookandfeel;
import domain.wrapper.WrapLookAndFeel;

public class CacheLookAndFeel extends AbstractCacheBase<Lookandfeel> {

	private static CacheLookAndFeel singleton;
	private WrapLookAndFeel lookDAO = new WrapLookAndFeel();

	private CacheLookAndFeel() {
		setCache(new HashMap<String, Lookandfeel>());
	}

	public static CacheLookAndFeel getSingleton() {
		if (singleton == null) {
			singleton = new CacheLookAndFeel();
		}

		return singleton;
	}

	public Map<String, Lookandfeel> getAllLooks() {
		return getAll(lookDAO);
	}

	public List<Lookandfeel> getVettoreLooksPerCombo() {
		List<Lookandfeel> looks = new ArrayList<>();
		Map<String, Lookandfeel> mappa = this.getAllLooks();
		Object[] lista = mappa.values().toArray();
		Lookandfeel look = new Lookandfeel();
		look.setnome("");
		looks.add(look);
		for (int i = 0; i < lista.length; i++) {
			looks.add((Lookandfeel) lista[i]);
		}
		return looks;
	}
}
