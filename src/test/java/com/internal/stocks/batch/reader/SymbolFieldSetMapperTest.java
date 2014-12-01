package com.internal.stocks.batch.reader;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DefaultFieldSet;

import com.internal.stocks.model.Symbol;

public class SymbolFieldSetMapperTest {
	@Test
	public void testMapFieldSet() throws Exception {
		String[] tokens = "Y|A|Agilent Technologies, Inc. Common Stock|N| |N|100|N||A|A".split("\\|");
		DefaultFieldSet defaultFieldSet = new DefaultFieldSet(tokens);
		FieldSetMapper<Symbol> s = new SymbolFieldSetMapper();
		Symbol symbol = s.mapFieldSet(defaultFieldSet);
		Assert.assertNotNull(symbol);
		Assert.assertEquals(symbol.getSymbol(), "A");
	}
}
