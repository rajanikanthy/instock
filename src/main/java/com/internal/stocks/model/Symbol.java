package com.internal.stocks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author RAJANIKANTHA This class hold symbol and its attributes.
 * @see <a
 *      href="http://www.nasdaqtrader.com/trader.aspx?id=symboldirdefs">NASDAQ
 *      Symbol Directory</a>
 */
@Entity
@Table(name = "symbols")
public class Symbol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "nasdaq_traded")
	private Boolean nasdaqTraded;

	@Column(name = "symbol")
	private String symbol;

	@Column(name = "security_name", length = 500)
	private String securityName;

	@Column(name = "listing_exchange")
	private String exchange;

	@Column(name = "market_category")
	private String marketCategory;

	@Column(name = "etf")
	private Boolean etf;

	@Column(name = "round_lot_size")
	private Integer roundLotSize;

	@Column(name = "test_issue")
	private Boolean testIssue;

	@Column(name = "financial_status")
	private String financialStatus;

	@Column(name = "cqs_symbol")
	private String cqsSymbol;

	@Column(name = "nasdaq_symbol")
	private String nasdaqSymbol;

	@SequenceGenerator(name = "SYMBOL_SEQ", allocationSize = 25)
	@Id
	@GeneratedValue(generator = "SYMBOL_SEQ")
	private Integer Id;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Boolean getNasdaqTraded() {
		return nasdaqTraded;
	}

	public void setNasdaqTraded(Boolean nasdaqTraded) {
		this.nasdaqTraded = nasdaqTraded;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getMarketCategory() {
		return marketCategory;
	}

	public void setMarketCategory(String marketCategory) {
		this.marketCategory = marketCategory;
	}

	public Boolean getEtf() {
		return etf;
	}

	public void setEtf(Boolean etf) {
		this.etf = etf;
	}

	public Integer getRoundLotSize() {
		return roundLotSize;
	}

	public void setRoundLotSize(Integer roundLotSize) {
		this.roundLotSize = roundLotSize;
	}

	public Boolean getTestIssue() {
		return testIssue;
	}

	public void setTestIssue(Boolean testIssue) {
		this.testIssue = testIssue;
	}

	public String getFinancialStatus() {
		return financialStatus;
	}

	public void setFinancialStatus(String financialStatus) {
		this.financialStatus = financialStatus;
	}

	public String getCqsSymbol() {
		return cqsSymbol;
	}

	public void setCqsSymbol(String cqsSymbol) {
		this.cqsSymbol = cqsSymbol;
	}

	public String getNasdaqSymbol() {
		return nasdaqSymbol;
	}

	public void setNasdaqSymbol(String nasdaqSymbol) {
		this.nasdaqSymbol = nasdaqSymbol;
	}
}
