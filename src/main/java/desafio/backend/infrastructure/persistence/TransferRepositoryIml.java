package desafio.backend.infrastructure.persistence;

import desafio.backend.domain.transfer.Transfer;
import desafio.backend.domain.transfer.TransferRepository;
import desafio.backend.infrastructure.persistence.entities.TransferEntity;
import desafio.backend.infrastructure.persistence.jpa.TransferJpaRepository;
import desafio.backend.infrastructure.persistence.mapper.TransferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TransferRepositoryIml implements TransferRepository {

    private final TransferJpaRepository transferJpaRepository;

    @Override
    public Transfer save(Transfer transfer) {
        TransferEntity entity = TransferMapper.toEntity(transfer);
        TransferEntity saved = transferJpaRepository.save(entity);
        return TransferMapper.toDomain(saved);
    }

    @Override
    public Transfer findById(UUID id) {
        return transferJpaRepository.findById(id)
                .map(TransferMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Transfer not found"));
    }

    @Override
    public void update(Transfer transfer) {
        TransferEntity entity = TransferMapper.toEntity(transfer);
        transferJpaRepository.save(entity);
    }
}
