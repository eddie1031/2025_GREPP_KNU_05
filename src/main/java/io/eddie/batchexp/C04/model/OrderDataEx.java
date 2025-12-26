package io.eddie.batchexp.C04.model;

import java.time.LocalDateTime;

public record OrderDataEx(
        String orderCode,
        String productName,
        Long quantity,
        Long price,
        LocalDateTime orderDate
) {
}
