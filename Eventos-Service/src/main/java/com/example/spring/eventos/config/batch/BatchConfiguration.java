package com.example.spring.eventos.config.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.spring.eventos.batch.EventoItemProcessor;
import com.example.spring.eventos.batch.EventoItemReader;
import com.example.spring.eventos.batch.EventoItemWriter;
import com.example.spring.eventos.model.Evento;

@Configuration
public class BatchConfiguration {
	
	
    @Bean
    public Job myJob(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
        return new JobBuilder("myJob",jobRepository)
        		.incrementer(new RunIdIncrementer())
                .flow(myStep(jobRepository,transactionManager))
                .end()
                .build();
    }
    
    
    @Bean
    public Step myStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("myStep",jobRepository).
                <String[], Evento>chunk(100,transactionManager)
                .reader(new EventoItemReader())
                .processor(new EventoItemProcessor())
                .writer(new EventoItemWriter())
                .build();
    }
    
	

}