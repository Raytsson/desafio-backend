package desafio.backend.domain.transfer;

import desafio.backend.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transfer {
    private UUID id;
    private User originUser;
    private User destinationUser;
    private BigDecimal value;
    private TransferStatus status;
    private LocalDateTime transferTime;


}
