package com.molinari.gestionespese.business;

import com.molinari.utility.database.ConnectionPool;

public class ConnectionPoolGGS extends ConnectionPool {

	@Override
	protected String getPassword() {
		return null;
	}

	@Override
	protected String getUser() {
		return null;
	}

	@Override
	protected String getDriver() {
		return DBUtil.DRIVERCLASSNAME;
	}

	@Override
	protected String getDbUrl() {
		return DBUtil.getUrl();
	}

	@Override
	protected int getNumeroConnessioniDisponibili() {
		return 5;
	}

}
