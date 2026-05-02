package desafio.backend.domain.user;

import java.util.UUID;

public interface UserRepository {
    User save(User user);
    User findById(UUID id);
}
