package com.internal.stocks.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableJpaRepositories
public class InStockContextConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(InStockContextConfiguration.class);

	@Autowired
	private DataSource dataSource;

	@Bean
	public Properties propertiesFactory() throws IOException {
		Resource resource = new ClassPathResource("datasource.properties");
		LOGGER.info("Set configuration file location to => " + resource.getFile().getAbsolutePath());
		PropertiesFactoryBean propertiesFactory = new PropertiesFactoryBean();
		propertiesFactory.setLocation(resource);
		propertiesFactory.afterPropertiesSet();
		return (Properties)propertiesFactory.getObject();
	}

	@Bean
	public FactoryBean<EntityManagerFactory> entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(this.dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter());
		return emfb;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		txManager.setDataSource(this.dataSource);
		return txManager;
	}

	@Bean(name = "dataSource")
	public DataSource dataSource() throws IOException, Exception {
		Properties properties = propertiesFactory();
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(properties.getProperty(ConfigurationConstants.INSTOCK_DS_DRIVERCLASS.getName()));
		dataSource.setJdbcUrl(properties.getProperty(ConfigurationConstants.INSTOCK_JDBC_URL.getName()));
		dataSource.setUser(properties.getProperty(ConfigurationConstants.INSTOCK_DS_USER.getName()));
		dataSource.setPassword(properties.getProperty(ConfigurationConstants.INSTOCK_DS_PASSWORD.getName()));
		dataSource.setMaxPoolSize(Integer.parseInt(properties.getProperty(ConfigurationConstants.INSTOCK_DS_MAXPOOLSIZE.getName())));
		dataSource.setAcquireIncrement(Integer.parseInt(properties.getProperty(ConfigurationConstants.INSTOCK_DS_ACQUIREINCREMENTS.getName())));
		dataSource.setMaxStatements(Integer.parseInt(properties.getProperty(ConfigurationConstants.INSTOCK_DS_MAXSTATEMENTS.getName())));
		dataSource.setMaxStatements(Integer.parseInt(properties.getProperty(ConfigurationConstants.INSTOCK_DS_MINPOOLSIZE.getName())));
		return dataSource;
	}
	
	@Bean
	public SimpleDateFormat simpleDateFormat() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return simpleDateFormat;
	}
	
	@Bean
	public JobRepository jobRepository() throws Exception {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource());
		//jobRepositoryFactoryBean.setTransactionManager(batchTransactionManager());
		return jobRepositoryFactoryBean.getJobRepository();
	}
	
	@Bean
	public JobLauncher jobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository());
		return jobLauncher;
	}
}
