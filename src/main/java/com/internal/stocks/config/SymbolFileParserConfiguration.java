package com.internal.stocks.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.internal.stocks.batch.executor.BatchJobExecutor;
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
	private JobRegistry jobRegistry;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobExplorer jobExplorer;
	
	@Autowired
	private JobRepository jobRepository;

	@Bean 
	public PlatformTransactionManager batchTransactionManager() {
		return new JpaTransactionManager(entityManagerFactory);
	}
	@Bean
	public LineTokenizer symbolLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter("|");
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
	public ItemReader<Symbol> flatFileItemReader(@Value("#{jobParameters[symbolFile]}") String path,@Value("#{stepExecution.jobExecution}") JobExecution jobExecution) throws Exception{
		FlatFileItemReader<Symbol> reader = new FlatFileItemReader<Symbol>();
		Resource resource = new FileSystemResource(path);
		reader.setLineMapper(symbolLineMapper());
		reader.setLinesToSkip(1);
		reader.setResource(resource);
		reader.open(jobExecution.getExecutionContext());
		return reader;
	}
	
	@Bean
	public ItemWriter<? extends Symbol> jpaItemWriter() {
		JpaItemWriter<Symbol> jpaItemWriter = new JpaItemWriter<Symbol>();
		jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
		return jpaItemWriter;
	}
	
	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("stocks-import").start(processFile()).build();
	}
	
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Step processFile() throws Exception {
		return stepBuilderFactory.get("import").transactionManager(batchTransactionManager()).chunk(1000).reader(flatFileItemReader(null, null)).writer((ItemWriter)jpaItemWriter()).build();
	}
	
	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
		JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
		jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
		return jobRegistryBeanPostProcessor;
	}
	
	
	@Bean
	public BatchJobExecutor batchJobExecutor() {
		BatchJobExecutor batchJobExecutor = new BatchJobExecutor();
		return batchJobExecutor;
	}
	
	@Bean
	public JobOperator jobOperator() {
		SimpleJobOperator jobOperator = new SimpleJobOperator();
		jobOperator.setJobExplorer(this.jobExplorer);
		jobOperator.setJobLauncher(this.jobLauncher);
		jobOperator.setJobRegistry(this.jobRegistry);
		jobOperator.setJobRepository(this.jobRepository);
		return jobOperator;
	}
	
}
