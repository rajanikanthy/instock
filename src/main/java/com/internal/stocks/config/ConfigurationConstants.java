package com.internal.stocks.config;

public enum ConfigurationConstants {
	INSTOCK_JDBC_URL("instock.ds.jdbcUrl"),
	INSTOCK_DS_USER("instock.ds.user"),
	INSTOCK_DS_PASSWORD("instock.ds.password"),
	INSTOCK_DS_MAXSTATEMENTS("instock.ds.maxStatements"),
	INSTOCK_DS_MINPOOLSIZE("instock.ds.minPoolSize"),
	INSTOCK_DS_MAXPOOLSIZE("instock.ds.maxPoolSize"),
	INSTOCK_DS_ACQUIREINCREMENTS("instock.ds.acquireIncrements"),
	INSTOCK_DS_DRIVERCLASS("instock.ds.driverClass"),
	INSTOCK_BATCH_DS_JDBC_URL("instock.batch.ds.jdbcUrl"),
	INSTOCK_BATCH_DS_USER("instock.batch.ds.user"),
	INSTOCK_BATCH_DS_PASSWORD("instock.batch.ds.password"),
	INSTOCK_BATCH_DS_MAXSTATEMENTS("instock.batch.ds.maxStatements"),
	INSTOCK_BATCH_DS_MINPOOLSIZE("instock.batch.ds.minPoolSize"),
	INSTOCK_BATCH_DS_MAXPOOLSIZE("instock.batch.ds.maxPoolSize"),
	INSTOCK_BATCH_DS_ACQUIREINCREMENTS("instock.batch.ds.acquireIncrements"),
	INSTOCK_BATCH_DS_DRIVERCLASS("instock.batch.ds.driverClass");
	
	
	private String name;
	
	ConfigurationConstants(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
