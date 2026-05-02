package desafio.backend.domain.user;

import desafio.backend.domain.wallet.Wallet;

import java.util.UUID;

public class User {
    private UUID id;
    private UserType type;
    private String fullName;
    private String cpfCnpj;
    private String email;
    private String password;
    private Wallet wallet;

    public void transferValidadtion(){
        if (this.type == UserType.SHOPKEEPER) {
            throw new IllegalArgumentException("Shopkeepers cannot be the origin of a transfer.");
        }
    }
}
