package desafio.backend.infrastructure.persistence.entities;

import desafio.backend.domain.transfer.TransferStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "transfers")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private UserEntity originUser;
    @ManyToOne
    private UserEntity destinationUser;
    private BigDecimal value;
    @Enumerated(EnumType.STRING)
    private TransferStatus status;
    private LocalDateTime transferTime;
}
