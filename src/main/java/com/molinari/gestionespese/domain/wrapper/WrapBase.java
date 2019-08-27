package com.molinari.gestionespese.domain.wrapper;

import java.sql.SQLException;
import java.util.logging.Level;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.ConnectionPool;
import com.molinari.utility.database.dao.GenericDAO;

public class WrapBase<T extends AbstractOggettoEntita> extends GenericDAO<T> {

	public WrapBase(T entita) {
		super(entita);
	}

	public boolean executeUpdate(String sql){
		boolean ok = true;

		try {
			ConnectionPool.getSingleton().executeUpdate(sql);

		} catch (final SQLException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
			ok = false;
		}
		return ok;
	}
}
