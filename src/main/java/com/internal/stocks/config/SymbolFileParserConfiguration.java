package com.internal.stocks.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.internal.stocks.batch.reader.SymbolFieldSetMapper;
import com.internal.stocks.model.Symbol;

@Configuration
@EnableBatchProcessing
@Import(InStockContextConfiguration.class)
public class SymbolFileParserConfiguration {
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private ItemReader<Symbol> flatFileItemReader;
	
	@Bean
	public LineTokenizer symbolLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter("\\|");
		return lineTokenizer;
	}
	
	@Bean
	public FieldSetMapper<Symbol> symbolFieldSetMapper() {
		FieldSetMapper<Symbol> fieldSetMapper = new SymbolFieldSetMapper();
		return fieldSetMapper;
	}
	
	@Bean
	public LineMapper<Symbol> symbolLineMapper() {
		DefaultLineMapper<Symbol> defaultLineMapper = new DefaultLineMapper<Symbol>();
		defaultLineMapper.setFieldSetMapper(symbolFieldSetMapper());
		defaultLineMapper.setLineTokenizer(symbolLineTokenizer());
		return defaultLineMapper;
	}
	
	@Bean
	@StepScope
	public ItemReader<Symbol> flatFileItemReader(@Value("#{jobParameters[symbolFile]}") String path) {
		FlatFileItemReader<Symbol> reader = new FlatFileItemReader<Symbol>();
		Resource resource = new FileSystemResource(path);
		reader.setLineMapper(symbolLineMapper());
		reader.setLinesToSkip(1);
		reader.setResource(resource);
		return reader;
	}
	
	@Bean
	public ItemWriter<Symbol> jpaItemWriter() {
		JpaItemWriter<Symbol> jpaItemWriter = new JpaItemWriter<Symbol>();
		jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
		return jpaItemWriter;
	}
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("stocks-import").start(processFile(flatFileItemReader, jpaItemWriter())).build();
	}
	
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Step processFile(ItemReader reader, ItemWriter writer) {
		return stepBuilderFactory.get("import").chunk(1000).reader(reader).writer(writer).build();
	}
	
	
}
