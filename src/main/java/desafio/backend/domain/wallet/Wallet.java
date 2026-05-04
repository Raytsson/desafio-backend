package desafio.backend.domain.wallet;

import desafio.backend.adapters.exception.HasEnoughBalanceException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class Wallet {
    private UUID id;
    private BigDecimal balance;

    public void hasEnoughBalance(BigDecimal value) {
        if (this.balance.compareTo(value) < 0) {
            throw new HasEnoughBalanceException("Insufficient funds.");
        }
    }

    public void debit(BigDecimal value) {
        hasEnoughBalance(value);
        this.balance = this.balance.subtract(value);
    }

    public void credit(BigDecimal value) {
        this.balance = this.balance.add(value);
    }


}
