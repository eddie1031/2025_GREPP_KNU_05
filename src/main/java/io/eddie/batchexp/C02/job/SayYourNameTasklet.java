package io.eddie.batchexp.C02.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope
@Component
public class SayYourNameTasklet implements Tasklet {

    private final String name;

    public SayYourNameTasklet(
            @Value("#{jobParameters['name']}") String name
    ) {
        this.name = name;
    }

    @Override
    public RepeatStatus execute(
            StepContribution contribution, ChunkContext chunkContext
    ) throws Exception {
        log.info("안녕하세요! {}님, 반갑습니다!", name);
        return RepeatStatus.FINISHED;
    }

}
