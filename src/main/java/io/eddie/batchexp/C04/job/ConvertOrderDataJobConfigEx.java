package io.eddie.batchexp.C04.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eddie.batchexp.C04.mapper.TextOrderDataMapperEx;
import io.eddie.batchexp.C04.model.OrderDataEx;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ConvertOrderDataJobConfigEx {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;

    @Bean
    public Job orderDataConvertJobEx() {
        return new JobBuilder("orderDataConvertJobEx", jobRepository)
                .start(aggregateOrderDataStepEx())
                .build();
    }

    @Bean
    public Step aggregateOrderDataStepEx() {
        return new StepBuilder("aggregateOrderDataStepEx", jobRepository)

                .<OrderDataEx, OrderDataEx>chunk(15, txManager)

                .reader(
                        new FlatFileItemReaderBuilder<OrderDataEx>()
                                .name("textOrderDataReaderEx")
                                .resource(new ClassPathResource("/in/order_data_ng.txt"))
                                .lineMapper(new TextOrderDataMapperEx())
                                .build()
                )

                .writer(
                        new JsonFileItemWriterBuilder<OrderDataEx>()
                                .name("orderJsonDataWriterEx")
                                .resource(new FileSystemResource("src/main/resources/out/order_data_ng.json"))
                                .jsonObjectMarshaller(
                                        new JacksonJsonObjectMarshaller<>() {{
                                            setObjectMapper(
                                                    new ObjectMapper()
                                                            .registerModule(new JavaTimeModule())
                                            );
                                        }}
                                )
                                .build()
                )

                .build();

    }

}
