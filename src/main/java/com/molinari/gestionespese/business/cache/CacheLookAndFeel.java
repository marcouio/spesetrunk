package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.molinari.gestionespese.domain.ILookandfeel;
import com.molinari.gestionespese.domain.Lookandfeel;
import com.molinari.gestionespese.domain.wrapper.WrapLookAndFeel;

public class CacheLookAndFeel extends AbstractCacheBase<ILookandfeel> {

	private static CacheLookAndFeel singleton;
	private final WrapLookAndFeel lookDAO = new WrapLookAndFeel();

	private CacheLookAndFeel() {
		setCache(new HashMap<String, ILookandfeel>());
	}

	public static CacheLookAndFeel getSingleton() {
		if (singleton == null) {
			singleton = new CacheLookAndFeel();
		}

		return singleton;
	}

	public Map<String, ILookandfeel> getAllLooks() {
		return getAll(lookDAO);
	}

	public List<ILookandfeel> getVettoreLooksPerCombo() {
		final List<ILookandfeel> looks = new ArrayList<>();
		final Map<String, ILookandfeel> mappa = this.getAllLooks();
		final Object[] lista = mappa.values().toArray();
		final Lookandfeel look = new Lookandfeel();
		look.setnome("");
		looks.add(look);
		for (final Object element : lista) {
			looks.add((ILookandfeel) element);
		}
		return looks;
	}
}
