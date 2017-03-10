package com.molinari.gestionespese.domain.wrapper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;

import controller.ControlloreBase;
import db.ConnectionPool;

public class WrapBase implements Serializable {

	private static final long serialVersionUID = 1L;

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
