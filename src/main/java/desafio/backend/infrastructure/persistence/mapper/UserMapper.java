package desafio.backend.infrastructure.persistence.mapper;

import desafio.backend.domain.user.User;
import desafio.backend.infrastructure.persistence.entities.UserEntity;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .type(user.getType())
                .fullName(user.getFullName())
                .cpfCnpj(user.getCpfCnpj())
                .email(user.getEmail())
                .password(user.getPassword())
                .wallet(WalletMapper.toEntity(user.getWallet()))
                .build();
    }
    public static User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .type(entity.getType())
                .fullName(entity.getFullName())
                .cpfCnpj(entity.getCpfCnpj())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .wallet(WalletMapper.toDomain(entity.getWallet()))
                .build();
    }
}
