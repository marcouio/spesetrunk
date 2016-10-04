package business;

import db.ConnectionPool;

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
		return DBUtil.URL;
	}

	@Override
	protected int getNumeroConnessioniDisponibili() {
		return 5;
	}

}
