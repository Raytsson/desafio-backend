package desafio.backend.domain.transfer;

import desafio.backend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Transfer {
    private UUID id;
    private User originUser;
    private User destinationUser;
    private BigDecimal value;

    @Setter
    private TransferStatus status;
    private LocalDateTime transferTime;
}
