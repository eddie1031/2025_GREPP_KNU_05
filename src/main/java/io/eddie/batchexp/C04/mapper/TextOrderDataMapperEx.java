package io.eddie.batchexp.C04.mapper;

import io.eddie.batchexp.C03.model.OrderData;
import io.eddie.batchexp.C04.exception.InvalidOrderDataException;
import io.eddie.batchexp.C04.model.OrderDataEx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.LineMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TextOrderDataMapperEx implements LineMapper<OrderDataEx> {

    @Override
    public OrderDataEx mapLine(String line, int lineNumber) throws Exception {

        log.info("{}번 째 줄 내용 : {}", lineNumber, line);

        String[] lineParts = line.split(",");

        if ( lineParts.length != 5 ) {
            throw new InvalidOrderDataException("컬럼 수가 올바르지 않습니다. 기대값 = 5, 실제값 = " + lineParts.length + ", 라인 = " + lineNumber);
        }

        return new OrderDataEx(
                lineParts[0],
                lineParts[1],
                Long.parseLong(lineParts[2]),
                Long.parseLong(lineParts[3]),
                convertStringToLocalDateTime(lineParts[4])
        );

    }

    private LocalDateTime convertStringToLocalDateTime(String dateStr) {
        return LocalDateTime.parse(
                dateStr,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }

}
