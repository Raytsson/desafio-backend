package desafio.backend.adapters.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
    BigDecimal value,
    UUID payer,
    UUID payee
) {
}
