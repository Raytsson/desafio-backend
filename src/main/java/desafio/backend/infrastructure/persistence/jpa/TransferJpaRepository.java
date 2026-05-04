package desafio.backend.infrastructure.persistence.jpa;

import desafio.backend.infrastructure.persistence.entities.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferJpaRepository extends JpaRepository<TransferEntity, UUID> {
}
