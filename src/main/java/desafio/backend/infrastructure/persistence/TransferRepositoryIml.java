package desafio.backend.infrastructure.persistence;

import desafio.backend.domain.transfer.Transfer;
import desafio.backend.domain.transfer.TransferRepository;

import java.util.UUID;

public class TransferRepositoryIml implements TransferRepository {
    @Override
    public void save(Transfer transfer) {

    }

    @Override
    public Transfer findById(UUID id) {
        return null;
    }

    @Override
    public void update(Transfer transfer) {

    }
}
