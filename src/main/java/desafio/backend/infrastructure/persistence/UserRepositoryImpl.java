package desafio.backend.infrastructure.persistence;

import desafio.backend.adapters.exception.UserNotFoundException;
import desafio.backend.domain.user.User;
import desafio.backend.domain.user.UserRepository;
import desafio.backend.infrastructure.persistence.entities.UserEntity;
import desafio.backend.infrastructure.persistence.jpa.UserJpaRepository;
import desafio.backend.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity saved = userJpaRepository.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public User findById(UUID id) {
        return userJpaRepository.findById(id)
                .map(UserMapper::toDomain)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
