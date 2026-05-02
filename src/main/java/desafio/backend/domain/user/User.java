package desafio.backend.domain.user;

import desafio.backend.domain.wallet.Wallet;
import lombok.Getter;

import java.util.UUID;

@Getter
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
            throw new IllegalArgumentException("Shopkeepers cannot be the origin of a transfer.");
        }
    }
}
