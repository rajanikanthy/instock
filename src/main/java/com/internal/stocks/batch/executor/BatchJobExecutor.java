package com.internal.stocks.batch.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

public class BatchJobExecutor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobExecutor.class);
	
	@Autowired
	private JobOperator jobOperator;
	
	@Async
	public String runJob(String jobName, String parameters) {
		ExitStatus jobExitStatus = ExitStatus.UNKNOWN;
		try {
			jobOperator.start(jobName, parameters);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		return jobExitStatus.getExitDescription();
	}
	
}
