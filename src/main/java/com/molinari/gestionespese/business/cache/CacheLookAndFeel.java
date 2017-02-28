package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.molinari.gestionespese.domain.Lookandfeel;
import com.molinari.gestionespese.domain.wrapper.WrapLookAndFeel;

public class CacheLookAndFeel extends AbstractCacheBase<Lookandfeel> {

	private static CacheLookAndFeel singleton;
	private final WrapLookAndFeel lookDAO = new WrapLookAndFeel();

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
		final List<Lookandfeel> looks = new ArrayList<>();
		final Map<String, Lookandfeel> mappa = this.getAllLooks();
		final Object[] lista = mappa.values().toArray();
		final Lookandfeel look = new Lookandfeel();
		look.setnome("");
		looks.add(look);
		for (final Object element : lista) {
			looks.add((Lookandfeel) element);
		}
		return looks;
	}
}
