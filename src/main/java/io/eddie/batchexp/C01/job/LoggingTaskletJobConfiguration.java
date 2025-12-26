package io.eddie.batchexp.C01.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class LoggingTaskletJobConfiguration {

    @Bean
    public Job loggingJob(
            JobRepository jobRepository,
            @Qualifier("printHelloStep") Step printHelloStep
    ) {
        return new JobBuilder("loggingJob", jobRepository)
                .start(printHelloStep)
                .build();
    }

    @Bean(name = "printHelloStep")
    public Step printHelloStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager
    ) {
        return new StepBuilder("printHelloStep", jobRepository)
                .tasklet(, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet printHelloTasklet() {
        return (contribution, chunkContext) -> {

        };
    }

}
