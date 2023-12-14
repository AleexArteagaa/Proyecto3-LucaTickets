package com.example.spring.eventos.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;


@Component
public class EventoJobExecutionListener implements JobExecutionListener{
	
	private static final Logger logger = LoggerFactory.getLogger(EventoJobExecutionListener.class);

	
    @Override
    public void beforeJob(JobExecution jobExecution) {
    	logger.info("--- Executing evento job with id {}", jobExecution.getId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
        	logger.info("--- Evento job with id {} execution completed", jobExecution.getId());
        	logger.info("--- Has terminado : "+ jobExecution.getEndTime());
        }
    }

}
