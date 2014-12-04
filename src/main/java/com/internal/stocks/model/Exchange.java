package com.internal.stocks.model;

public enum Exchange {

	A("NYSE MKT"), N(" New York Stock Exchange (NYSE)"), P(" NYSE ARCA"), Z(
			" BATS Global Markets (BATS)"), U("undefined"), Q("Q");

	private String description;

	private Exchange(String expansion) {
		this.description = expansion;
	}
	
	public String getDescription(){
		return description;
	}
	
}
