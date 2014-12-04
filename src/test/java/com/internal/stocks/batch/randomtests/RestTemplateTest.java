package com.internal.stocks.batch.randomtests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {
	@Test
	public void testQuoteImport() {
		RestTemplate rt = new RestTemplate();
		Map<String,String> params = new HashMap<String,String>();
		params.put("symbols", "AAPL,BAC");
		String response = rt.getForObject("http://download.finance.yahoo.com/d/quotes.csv?s={symbols}&f=snl1d1t1ohgdrp4jka2x&e=.csv", String.class, params);
		String[] quotes = response.split("\r\n");
		for(String q : quotes) {
			System.out.println("### " + q.trim());
		}
	}
}
