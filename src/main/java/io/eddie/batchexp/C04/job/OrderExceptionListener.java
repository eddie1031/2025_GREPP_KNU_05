package io.eddie.batchexp.C04.job;

import io.eddie.batchexp.C04.exception.InvalidOrderDataException;
import io.eddie.batchexp.C04.model.OrderDataEx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.FlatFileParseException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderExceptionListener
    implements SkipListener<OrderDataEx, OrderDataEx>, StepExecutionListener {

    private final List<InvalidOrderDataException> queue = new ArrayList<>();
    private final Path outputFilePath = Path.of("src/main/resources/out/order_ng_list.txt");

    @Override
    public void beforeStep(StepExecution stepExecution) {
        queue.clear();
    }

    @Override
    public void onSkipInRead(Throwable t) {

        if ( t instanceof FlatFileParseException ffpe ) {

            Throwable cause = ffpe.getCause();

            if ( cause instanceof InvalidOrderDataException ioe ) {
                queue.add(ioe);
            } else {
                InvalidOrderDataException invalidOrderDataException = new InvalidOrderDataException("예외가 발생했습니다..");
                queue.add(invalidOrderDataException);
            }

            return;
        }

        log.warn("예외가 감지되어 처리되었습니다!");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        if ( !queue.isEmpty() ) {
            try {
                writeFile();
            } catch ( IOException e ) {
                e.printStackTrace();
                log.error("파일 쓰기 실패");
            }
        }

        return stepExecution.getExitStatus();
    }

    private void writeFile() throws IOException {

        Path out = outputFilePath.isAbsolute()
                ? outputFilePath
                : outputFilePath.toAbsolutePath();

        try (BufferedWriter bw = Files.newBufferedWriter(
                out,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        )) {

            bw.write("오류가 발생한 이유 :");
            bw.newLine();

            for ( int i = 0; i < queue.size(); i++ ) {
                bw.write(queue.get(i).getMessage());
                bw.newLine();
            }


        }

    }

}
