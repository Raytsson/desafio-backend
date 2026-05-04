package desafio.backend.domain.user;

import desafio.backend.adapters.exception.TransferValidationForbidenException;
import desafio.backend.domain.wallet.Wallet;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class User {
    private UUID id;
    private UserType type;
    private String fullName;
    private String cpfCnpj;
    private String email;
    private String password;
    private Wallet wallet;

    public void transferValidation(){
        if (this.type == UserType.SHOPKEEPER) {
            throw new TransferValidationForbidenException("Shopkeepers cannot be the origin of a transfer.");
        }
    }
}
