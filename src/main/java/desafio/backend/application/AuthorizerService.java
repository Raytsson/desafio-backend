package desafio.backend.application;

import desafio.backend.domain.user.User;

import java.math.BigDecimal;

public interface AuthorizerService {
    boolean authorize(User payer, BigDecimal value);
}
