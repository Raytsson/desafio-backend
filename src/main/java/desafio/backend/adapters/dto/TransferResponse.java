package desafio.backend.adapters.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferResponse(
        UUID id,
        BigDecimal value,
        String status,
        LocalDateTime transferTime
) {
}
