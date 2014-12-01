package com.internal.stocks.batch.reader;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.internal.stocks.model.Exchange;
import com.internal.stocks.model.Symbol;

public class SymbolFieldSetMapper implements FieldSetMapper<Symbol> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SymbolFieldSetMapper.class);

	@Override
	public Symbol mapFieldSet(FieldSet fieldSet) throws BindException {
		Symbol stockSymbol = new Symbol();
		try {
			if (fieldSet.readString(0).contains(":")) {
				return null;
			}
			String nasdaqTraded = fieldSet.readString(0);
			setBooleanAttributes(stockSymbol, "nasdaqTraded", nasdaqTraded);
			stockSymbol.setSymbol(fieldSet.readString(1));
			stockSymbol.setSecurityName(fieldSet.readString(2));
			String exchange = fieldSet.readString(3);
			if (StringUtils.isNotEmpty(exchange)) {
				Exchange e = Exchange.valueOf(exchange.toUpperCase());
				stockSymbol.setExchange(e.getDescription());
			} else {
				stockSymbol.setExchange(Exchange.U.getDescription());
			}
			stockSymbol.setMarketCategory(fieldSet.readString(4));
			setBooleanAttributes(stockSymbol, "etf", fieldSet.readString(5));
			String roundLotSize = fieldSet.readString(6);
			if (StringUtils.isNotEmpty(roundLotSize)) {
				stockSymbol.setRoundLotSize(fieldSet.readInt(6));
			} else {
				stockSymbol.setRoundLotSize(-1);
			}
			String testIssue = fieldSet.readString(6);
			setBooleanAttributes(stockSymbol, "testIssue", testIssue);
			stockSymbol.setFinancialStatus(fieldSet.readString(7));
			stockSymbol.setCqsSymbol(fieldSet.readString(8));
			stockSymbol.setNasdaqSymbol(fieldSet.readString(9));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			stockSymbol = null;
		}
		return stockSymbol;
	}

	private void setBooleanAttributes(Symbol stockSymbol, String attrib,
			String value) throws Exception {
		if (StringUtils.isNotEmpty(value) && value.equalsIgnoreCase("Y")) {
			BeanUtils.setProperty(stockSymbol, attrib, Boolean.TRUE);
		} else {
			BeanUtils.setProperty(stockSymbol, attrib, Boolean.FALSE);
		}
	}

}
