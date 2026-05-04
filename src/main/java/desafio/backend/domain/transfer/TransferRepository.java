package desafio.backend.domain.transfer;

import java.util.UUID;

public interface TransferRepository {
        Transfer save(Transfer transfer);
        Transfer findById(UUID id);
        void update(Transfer transfer);
}
