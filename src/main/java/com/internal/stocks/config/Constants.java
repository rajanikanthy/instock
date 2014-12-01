package com.internal.stocks.config;

public enum Constants {
	
	INSTOCK_JDBC_URL("jdbcUrl"), 
	INSTOCK_DRIVER_CLASSNAME("driverClassName"),
	INSTOCK_USER("user"),
	INSTOCK_PASSWORD("password"),
	INSTOCK_MIN_POOL_SIZE("minPoolSize"),
	INSTOCK_MAX_POOL_SIZE("maxPoolSize"),
	INSTOCK_ACQUIRE_INCREMENT("acquireIncrement");
	
	
	
	private String name;
	
	Constants(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
