package desafio.backend.infrastructure.persistence.mapper;

import desafio.backend.domain.wallet.Wallet;
import desafio.backend.infrastructure.persistence.entities.WalletEntity;

public class WalletMapper {
    public static WalletEntity toEntity(Wallet wallet) {
        return WalletEntity.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .build();
    }

    public static Wallet toDomain(WalletEntity entity) {
        return Wallet.builder()
                .id(entity.getId())
                .balance(entity.getBalance())
                .build();
    }
}
