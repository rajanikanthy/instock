package com.internal.stocks.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableBatchProcessing
@Import(InStockContextConfiguration.class)
public class QuoteImportConfiguration {
	
}
