package io.eddie.batchexp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        excludeFilters = {
                // C01
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "io\\.eddie\\.batchexp\\.C01\\..*"
                ),
                // C02
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "io\\.eddie\\.batchexp\\.C02\\..*"
                ),
                // C03
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "io\\.eddie\\.batchexp\\.C03\\..*"
                )
        }
)
public class BatchExpApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchExpApplication.class, args);
    }

}
