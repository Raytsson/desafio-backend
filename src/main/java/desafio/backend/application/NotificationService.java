package desafio.backend.application;

import desafio.backend.domain.user.User;

public interface NotificationService {
    void notify(User user);
}